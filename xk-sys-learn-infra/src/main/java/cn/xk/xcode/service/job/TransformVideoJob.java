package cn.xk.xcode.service.job;

import cn.hutool.core.io.IoUtil;
import cn.xk.xcode.entity.po.SysFilesProcessPo;
import cn.xk.xcode.service.MinioService;
import cn.xk.xcode.service.SysFilesProcessService;
import cn.xk.xcode.service.SysFilesService;
import cn.xk.xcode.utils.file.Mp4VideoUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/7/4 19:36
 * @description
 */
@Slf4j
@Component
public class TransformVideoJob {
    @Resource
    private SysFilesService sysFilesService;

    @Resource
    private SysFilesProcessService sysFilesProcessService;

    @Resource
    private MinioService minioService;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Value("${sys.infra.ffmpegPath:default}")
    String ffmpegPath;

    @XxlJob("videoJobHandler")
    public void videoJobHandler() throws Exception {
        // 分片参数
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();
        List<SysFilesProcessPo> sysFilesProcessPoList = new ArrayList<>();
        int size = 0;

        //取出cpu核心数作为一次处理数据的条数
        int processors = Runtime.getRuntime().availableProcessors();
        // 获取待处理的任务
        sysFilesProcessPoList = sysFilesProcessService.getSysFilesProcessList(shardIndex, shardTotal, processors);
        size = sysFilesProcessPoList.size();
        log.info("当前分片：{}，总分片：{}，本次处理数据条数：{}", shardIndex, shardTotal, size);
        CountDownLatch countDownLatch = new CountDownLatch(size);
        sysFilesProcessPoList.forEach(
                sysFilesProcessPo -> {
                    // 处理数据
                    threadPoolExecutor.execute(() -> {
                        try {
                            int taskId = sysFilesProcessPo.getId();
                            boolean b = sysFilesProcessService.startTask(taskId);
                            if (!b){
                                log.warn("抢占失败，任务已经开始处理，任务id：{}", taskId);
                                return;
                            }
                            log.info("开始处理任务，任务id：{}", taskId);
                            String bucket = sysFilesProcessPo.getBucket();
                            String filePath = sysFilesProcessPo.getFilePath();
                            String originalFileId = sysFilesProcessPo.getFileId();
                            InputStream stream = minioService.downloadFile(bucket, filePath);
                            if (stream == null){
                                log.error("下载文件失败，文件路径：{}", bucket + ":" + filePath);
                                // todo 记录到数据库
                                return;
                            }
                            File mp4File = null;
                            File mp3File = null;
                            FileOutputStream outputStream;
                            try {
                                // todo 由于是多线程 这里还需要防止创建重复的文件
                                mp4File = File.createTempFile("mp4", ".mp4");
                                mp3File = File.createTempFile("mp3", ".mp3");
                                outputStream = new FileOutputStream(mp3File);
                                IoUtil.copy(stream, outputStream);
                            }catch (Exception e){
                                log.error("创建临时文件失败，信息：{}", e.getMessage());
                                // todo 记录到数据库
                                return;
                            }
                            // 开始转码
                            String result = null;
                            try {
                                Mp4VideoUtil videoUtil = new Mp4VideoUtil(ffmpegPath, mp3File.getAbsolutePath()
                                        , mp4File.getName(), mp4File.getAbsolutePath());
                                result = videoUtil.generateMp4();
                                if (!result.equals("success")){
                                    log.error("转码失败, 文件路径{}", filePath);
                                    // todo 记录到数据库
                                    return;
                                }
                            }catch (Exception e){
                                log.error("转码失败，信息：{}, 文件路径{}", e.getMessage(), filePath);
                                // todo 记录到数据库
                                return;
                            }
                            // 上传minio todo
                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally{

                        }

                    });
                }
        );

    }
}
