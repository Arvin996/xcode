package cn.xk.xcode.service;

import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.entity.po.BizMessagePo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author xuk
 * @Date 2024/7/16 10:36
 * @Version 1.0
 * @Description AbstractBizMessageProcess todo
 */
@Slf4j
public abstract class AbstractBizMessageProcess {

    public abstract boolean execute(BizMessagePo bizMessagePo);

    /**
     * @param shardIndex 分片序号
     * @param shardTotal 分片总数
     * @param messageType 消息类型
     * @param count 一次性取出的消息数量
     * @param timeout 预估任务执行时间,到此时间如果任务还没有结束则强制结束 单位秒
     */
    public void process(int shardIndex, int shardTotal, String messageType, int count, long timeout) {
        try {
            BizMessageService bizMessageService = SpringUtil.getBean(BizMessageService.class);
            List<BizMessagePo> bizMessagePoList = bizMessageService.getBizMessageList(shardIndex, shardTotal, messageType, count);
            int size = bizMessagePoList.size();
            log.info("取出的消息任务条数:{}", size);
            if (size < 1){
                return;
            }
            ExecutorService threadPool = Executors.newFixedThreadPool(size);
            CountDownLatch countDownLatch = new CountDownLatch(size);
            bizMessagePoList.forEach(bizMessagePo -> {
                threadPool.execute(() -> {
                    log.info("开始处理消息:{}", bizMessagePo.getBizKey() + "-" + bizMessagePo.getBizType());
                    try{
                        boolean res = bizMessageService.startWork(bizMessagePo.getId());
                        if (!res){
                            log.info("任务已经被处理过:{}", bizMessagePo);
                            return;
                        }
                        res = execute(bizMessagePo);
                        if (res){
                            log.info("消息处理成功:{}", bizMessagePo);
                            boolean b = bizMessageService.complete(bizMessagePo.getId());
                            if (b){
                                log.info("任务处理成功:{}", bizMessagePo);
                            }else {
                                log.info("任务处理失败:{}", bizMessagePo);
                                bizMessageService.handlerFailMessage(bizMessagePo.getId(), "消息状态改变失败");
                            }
                        }else {
                            log.info("消息处理失败:{}", bizMessagePo);
                            bizMessageService.handlerFailMessage(bizMessagePo.getId(), "业务处理失败");
                        }
                    }catch(Exception e){
                        log.error("任务出现异常:{},任务:{}", e.getMessage(), bizMessagePo);
                        bizMessageService.handlerFailMessage(bizMessagePo.getId(), e.getMessage());
                    }finally {
                        countDownLatch.countDown();
                    }
                    log.debug("结束任务:{}", bizMessagePo);
                });
            });
            countDownLatch.await(timeout, TimeUnit.SECONDS);
            log.info("任务处理完成");
        } catch (Exception e) {
            log.error("任务处理出现异常:{}", e.getMessage());
        }
    }
}
