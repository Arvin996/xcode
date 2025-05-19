package cn.xk.xcode.service.task;

import cn.xk.xcode.core.EnhanceXxlJobService;
import cn.xk.xcode.entity.*;
import cn.xk.xcode.entity.po.MessageTaskPo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MessageTaskService;
import cn.xk.xcode.utils.collections.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static cn.xk.xcode.config.GlobalMessageConstants.CORN_TASK_SUBMIT_FAILED;

/**
 * @author xukai
 * @version 1.0
 * @date 2025/5/18 11:13
 * @description RegisterCsvCornTaskService
 */
@Service
@Slf4j
@SuppressWarnings("all")
public class RegisterCsvCornTaskService {

    @Resource
    private CornReceiversTask cornReceiversTask;

    @Resource
    private EnhanceXxlJobService enhanceXxlJobService;

    @Resource
    private MessageTaskService messageTaskService;

    public CommonResult<?> registerReceiversCronTask(MessageTaskPo messageTaskPo) throws NoSuchMethodException {
        String msgCorn = messageTaskPo.getTaskCorn();
        CommonResult<List<XxlJobGroup>> jobGroupList = enhanceXxlJobService.getJobGroupList();
        if (!jobGroupList.isSuccess()) {
            log.error("xxl-job 获取执行器信息失败，请检查是否配置正确");
            return CommonResult.error(CORN_TASK_SUBMIT_FAILED, "xxl-job 获取执行器信息失败，请检查是否配置正确");
        }
        if (CollectionUtil.isEmpty(jobGroupList.getData())) {
            log.error("xxl-job 执行器不存在，请检查是否配置正确");
            return CommonResult.error(CORN_TASK_SUBMIT_FAILED, "xxl-job 执行器不存在，请检查是否配置正确");
        }
        XxlJobGroup xxlJobGroup = jobGroupList.getData().get(0);
        XxlJobInfo xxlJobInfo = createXxlJobInfo(xxlJobGroup, "csvReceiversCronTask--" + messageTaskPo.getId(), messageTaskPo);
        CommonResult<Integer> integerCommonResult = enhanceXxlJobService.registerJob(xxlJobInfo);
        messageTaskPo.setTaskCornId(Long.valueOf(integerCommonResult.getData()));
        messageTaskService.updateById(messageTaskPo);
        enhanceXxlJobService.registerJobHandlerToLocalCahce(xxlJobInfo.getExecutorHandler(), cornReceiversTask, cornReceiversTask.getClass().getDeclaredMethod("executeCsvReceiversCronTask"), null, null);
        return CommonResult.success("定时任务提交成功");
    }

    private XxlJobInfo createXxlJobInfo(XxlJobGroup xxlJobGroup, String executorHandler, MessageTaskPo messageTaskPo) {
        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setJobGroup(xxlJobGroup.getId());
        xxlJobInfo.setJobDesc("message corn csv 定时消息发送");
        xxlJobInfo.setAuthor("xuk");
        xxlJobInfo.setAlarmEmail(null);
        xxlJobInfo.setScheduleType("CRON");
        xxlJobInfo.setScheduleConf(messageTaskPo.getTaskCorn());
        xxlJobInfo.setMisfireStrategy(MisfireStrategyEnum.DO_NOTHING.getValue());
        xxlJobInfo.setExecutorRouteStrategy(ExecutorRouteStrategyEnum.FIRST.getValue());
        xxlJobInfo.setExecutorHandler(executorHandler);
        xxlJobInfo.setExecutorParam(messageTaskPo.getId().toString());
        xxlJobInfo.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.SERIAL_EXECUTION.getValue());
        xxlJobInfo.setExecutorTimeout(10);
        xxlJobInfo.setExecutorFailRetryCount(0);
        xxlJobInfo.setGlueType("BEAN");
        xxlJobInfo.setGlueSource("");
        xxlJobInfo.setGlueRemark("初始化");
        xxlJobInfo.setChildJobId("");
        xxlJobInfo.setTriggerStatus(1);
        xxlJobInfo.setTriggerLastTime(0);
        xxlJobInfo.setTriggerNextTime(0);
        return xxlJobInfo;
    }
}
