package cn.xk.xcode.core;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.annotation.AutoRegisterXxlJob;
import cn.xk.xcode.entity.XxlJobGroup;
import cn.xk.xcode.entity.XxlJobInfo;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.utils.collections.CollectionUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/2/5 14:41
 * @Version 1.0.0
 * @Description XxlJobAutoRegisterLoader
 **/
@Slf4j
@RequiredArgsConstructor
public class XxlJobAutoRegisterLoader implements ApplicationRunner {

    private final EnhanceXxlJobService enhanceXxlJobService;

    @Override
    public void run(ApplicationArguments args) {
        // 注册执行器
        if (!addJobGroup()){
            log.warn("xxl-job 执行器注册失败，请检查是否配置正确");
            return;
        }
        // 注册任务
        addJobInfo();
    }

    @SuppressWarnings("all")
    private void addJobInfo() {
        CommonResult<List<XxlJobGroup>> jobGroupList = enhanceXxlJobService.getJobGroupList();
        if (!jobGroupList.isSuccess()) {
            log.error("xxl-job 获取执行器信息失败，请检查是否配置正确");
            return;
        }
        if (CollectionUtil.isEmpty(jobGroupList.getData())) {
            log.error("xxl-job 执行器不存在，请检查是否配置正确");
            return;
        }
        XxlJobGroup xxlJobGroup = jobGroupList.getData().get(0);
        // 获取所有bean
        String[] beanNamesForType = SpringUtil.getBeanNamesForType(Object.class);
        for (String beanName : beanNamesForType) {
            Object bean = SpringUtil.getBean(beanName);
            List<Method> methods = MethodUtils.getMethodsListWithAnnotation(bean.getClass(), AutoRegisterXxlJob.class);
            if (CollectionUtil.isNotEmpty(methods)) {
                for (Method method : methods) {
                    AutoRegisterXxlJob autoRegisterXxlJob = method.getAnnotation(AutoRegisterXxlJob.class);
                    if (method.isAnnotationPresent(XxlJob.class)) {
                        // 此时让xxl-job core自动注册 不需要我们重复处理 我们只需要往数据库插入一下数据即可
                        XxlJob xxlJob = method.getAnnotation(XxlJob.class);
                        registerJobToDb(xxlJobGroup, xxlJob.value(), autoRegisterXxlJob);
                    } else {
                        // 首先注册到xxl内存里面
                        String handler = enhanceXxlJobService.registerJobHandlerToLocalCahce(autoRegisterXxlJob.executorHandler(), bean, method, autoRegisterXxlJob.init(), autoRegisterXxlJob.destroy());
                        // 然后放数据库里面
                        registerJobToDb(xxlJobGroup, handler, autoRegisterXxlJob);
                    }
                }
            }
        }
    }

    private void registerJobToDb(XxlJobGroup xxlJobGroup, String executorHandler, AutoRegisterXxlJob autoRegisterXxlJob) {
        CommonResult<List<XxlJobInfo>> jobInfoList = enhanceXxlJobService.getJobInfoList(xxlJobGroup.getId(), executorHandler);
        if (!jobInfoList.isSuccess()) {
            log.error("xxl-job 获取任务信息失败，请检查是否配置正确");
            return;
        }
        if (CollectionUtil.isNotEmpty(jobInfoList.getData())) {
            if (jobInfoList.getData().stream().anyMatch(o -> o.getExecutorHandler().equals(executorHandler))) {
                log.info("xxl-job 任务{}已经存在，不需要再次注册", executorHandler);
                return;
            }
        }
        XxlJobInfo xxlJobInfo = createXxlJobInfo(xxlJobGroup, executorHandler, autoRegisterXxlJob);
        CommonResult<Integer> integerCommonResult = enhanceXxlJobService.registerJob(xxlJobInfo);
        if (!integerCommonResult.isSuccess()) {
            log.error("xxl-job 任务{}注册失败，请检查是否配置正确", executorHandler);
        }
        log.info("xxl-job 任务{}注册成功，任务ID为{}", executorHandler, integerCommonResult.getData());
    }

    private XxlJobInfo createXxlJobInfo(XxlJobGroup xxlJobGroup, String executorHandler, AutoRegisterXxlJob autoRegisterXxlJob) {
        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setJobGroup(xxlJobGroup.getId());
        xxlJobInfo.setJobDesc(autoRegisterXxlJob.jobDesc());
        xxlJobInfo.setAuthor(autoRegisterXxlJob.author());
        xxlJobInfo.setAlarmEmail(autoRegisterXxlJob.warnEmail());
        xxlJobInfo.setScheduleType("CRON");
        xxlJobInfo.setScheduleConf(autoRegisterXxlJob.cron());
        xxlJobInfo.setMisfireStrategy(autoRegisterXxlJob.misfireStrategy().getValue());
        xxlJobInfo.setExecutorRouteStrategy(autoRegisterXxlJob.executorRouteStrategy().getValue());
        xxlJobInfo.setExecutorHandler(executorHandler);
        xxlJobInfo.setExecutorParam(autoRegisterXxlJob.executorParam());
        xxlJobInfo.setExecutorBlockStrategy(autoRegisterXxlJob.executorBlockStrategy().getValue());
        xxlJobInfo.setExecutorTimeout(autoRegisterXxlJob.executorTimeout());
        xxlJobInfo.setExecutorFailRetryCount(autoRegisterXxlJob.executorFailRetryCount());
        xxlJobInfo.setGlueType("BEAN");
        xxlJobInfo.setGlueSource("");
        xxlJobInfo.setGlueRemark("初始化");
        xxlJobInfo.setChildJobId(autoRegisterXxlJob.childJobIds().length == 0 ? "" : StringUtils.join(StrUtil.COMMA, autoRegisterXxlJob.childJobIds()));
        xxlJobInfo.setTriggerStatus(autoRegisterXxlJob.triggerStatus());
        xxlJobInfo.setTriggerLastTime(0);
        xxlJobInfo.setTriggerNextTime(0);
        return xxlJobInfo;
    }

    private boolean addJobGroup() {
        CommonResult<Boolean> commonResult = enhanceXxlJobService.preciselyCheck();
        if (!commonResult.isSuccess()) {
            log.info("xxl-job 执行器注册发生错误:{}", commonResult.getMsg());
            return false;
        } else {
            if (commonResult.getData()) {
                log.info("xxl-job 执行器已经注册,无需重复注册");
                return true;
            }
        }
        commonResult = enhanceXxlJobService.autoRegisterJobGroup();
        if (CommonResult.isSuccess(commonResult)) {
            log.info("xxl-job 执行器注册成功");
            return true;
        } else {
            log.error("xxl-job 执行器注册失败, 错误信息：{}", commonResult.getMsg());
            return false;
        }
    }
}
