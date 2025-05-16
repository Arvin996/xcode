//package cn.xk.xcode.service.task;
//
//import cn.xk.xcode.core.EnhanceXxlJobService;
//import cn.xk.xcode.entity.*;
//import cn.xk.xcode.entity.po.MessageTaskPo;
//import cn.xk.xcode.pojo.CommonResult;
//import cn.xk.xcode.service.MessageTaskService;
//import cn.xk.xcode.utils.collections.CollectionUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.List;
//
/// **
// * @Author xuk
// * @Date 2025/4/1 14:30
// * @Version 1.0.0
// * @Description RegisterCsvCornTaskService
// **/
//@Service
//@Slf4j
//@SuppressWarnings("all")
//public class RegisterCsvCornTaskService {
//
//    @Resource
//    private CsvReceiversCronTask csvReceiversCronTask;
//
//    @Resource
//    private EnhanceXxlJobService enhanceXxlJobService;
//
//    @Resource
//    private MessageTaskService messageTaskService;
//
//    public boolean registerCsvReceiversCronTask(MessageTaskPo messageTaskPo) throws NoSuchMethodException {
//        String msgCorn = messageTaskPo.getMsgCorn();
//        CommonResult<List<XxlJobGroup>> jobGroupList = enhanceXxlJobService.getJobGroupList();
//        if (!jobGroupList.isSuccess()) {
//            log.error("xxl-job 获取执行器信息失败，请检查是否配置正确");
//            return false;
//        }
//        if (CollectionUtil.isEmpty(jobGroupList.getData())) {
//            log.error("xxl-job 执行器不存在，请检查是否配置正确");
//            return false;
//        }
//        XxlJobGroup xxlJobGroup = jobGroupList.getData().get(0);
//        XxlJobInfo xxlJobInfo = createXxlJobInfo(xxlJobGroup, "csvReceiversCronTask--" + messageTaskPo.getId(), messageTaskPo);
//        CommonResult<Integer> integerCommonResult = enhanceXxlJobService.registerJob(xxlJobInfo);
//        messageTaskPo.setTaskCornId(Long.valueOf(integerCommonResult.getData()));
//        messageTaskService.updateById(messageTaskPo);
//        enhanceXxlJobService.registerJobHandlerToLocalCahce(xxlJobInfo.getExecutorHandler(), csvReceiversCronTask, csvReceiversCronTask.getClass().getDeclaredMethod("executeCsvReceiversCronTask"), null, null);
//        return true;
//    }
//
//    private XxlJobInfo createXxlJobInfo(XxlJobGroup xxlJobGroup, String executorHandler, MessageTaskPo messageTaskPo) {
//        XxlJobInfo xxlJobInfo = new XxlJobInfo();
//        xxlJobInfo.setJobGroup(xxlJobGroup.getId());
//        xxlJobInfo.setJobDesc("message corn csv 定时消息发送");
//        xxlJobInfo.setAuthor("xuk");
//        xxlJobInfo.setAlarmEmail(null);
//        xxlJobInfo.setScheduleType("CRON");
//        xxlJobInfo.setScheduleConf(messageTaskPo.getMsgCorn());
//        xxlJobInfo.setMisfireStrategy(MisfireStrategyEnum.DO_NOTHING.getValue());
//        xxlJobInfo.setExecutorRouteStrategy(ExecutorRouteStrategyEnum.FIRST.getValue());
//        xxlJobInfo.setExecutorHandler(executorHandler);
//        xxlJobInfo.setExecutorParam(messageTaskPo.getId().toString());
//        xxlJobInfo.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.SERIAL_EXECUTION.getValue());
//        xxlJobInfo.setExecutorTimeout(10);
//        xxlJobInfo.setExecutorFailRetryCount(0);
//        xxlJobInfo.setGlueType("BEAN");
//        xxlJobInfo.setGlueSource("");
//        xxlJobInfo.setGlueRemark("初始化");
//        xxlJobInfo.setChildJobId("");
//        xxlJobInfo.setTriggerStatus(1);
//        xxlJobInfo.setTriggerLastTime(0);
//        xxlJobInfo.setTriggerNextTime(0);
//        return xxlJobInfo;
//    }
//}
