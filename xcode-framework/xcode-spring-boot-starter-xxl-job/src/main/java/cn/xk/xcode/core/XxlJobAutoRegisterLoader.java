package cn.xk.xcode.core;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.annotation.AutoRegisterXxlJob;
import cn.xk.xcode.entity.XxlJobGroup;
import cn.xk.xcode.entity.XxlJobInfo;
import cn.xk.xcode.utils.collections.CollectionUtil;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.handler.impl.MethodJobHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

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
        addJobGroup();
        // 注册任务
        addJobInfo();
    }

    @SuppressWarnings("all")
    private void addJobInfo() {
        XxlJobGroup xxlJobGroup = enhanceXxlJobService.getJobGroupList().get(0);
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
                        Method initMethod = null;
                        Method destroyMethod = null;
                        // 没有@xxl-job 注解 但是有@AutoRegisterXxlJob 注解 自动注册 如果
                        String handler = autoRegisterXxlJob.executorHandler();
                        if (StringUtils.isEmpty(handler)) {
                            log.warn("【警告】 方法{}没有@XxlJob注解，也没有@AutoRegisterXxlJob注解中的executorHandler属性, 将会以类名_方法名称作为任务名称", method.getName());
                            handler = bean.getClass().getSimpleName() + "_" + method.getName();
                        }
                        String init = autoRegisterXxlJob.init();
                        if (StringUtils.isNotEmpty(init)) {
                            try {
                                initMethod = bean.getClass().getDeclaredMethod(init);
                                initMethod.setAccessible(true);
                            } catch (NoSuchMethodException e) {
                                log.error("xxl-job method-jobhandler initMethod invalid, for[" + bean.getClass() + "#" + method.getName() + "] .");
                            }
                        }
                        String destroy = autoRegisterXxlJob.destroy();
                        if (StringUtils.isNotEmpty(destroy)) {
                            try {
                                destroyMethod = bean.getClass().getDeclaredMethod(destroy);
                                destroyMethod.setAccessible(true);
                            } catch (NoSuchMethodException e) {
                                log.error("xxl-job method-jobhandler initMethod invalid, for[" + bean.getClass() + "#" + method.getName() + "] .");
                            }
                        }
                        // 1. 内存注册处理器
                        if (Objects.isNull(XxlJobSpringExecutor.loadJobHandler(handler))) {
                            XxlJobSpringExecutor.registJobHandler(handler, new MethodJobHandler(bean, method, initMethod, destroyMethod));
                        }else {
                            log.warn("xxl-job 任务{}已经存在，请检查是否有重复注册", handler);
                        }
                        // 2. 数据库注册任务
                        registerJobToDb(xxlJobGroup, handler, autoRegisterXxlJob);
                    }
                }
            }
        }
    }

    private void registerJobToDb(XxlJobGroup xxlJobGroup, String executorHandler, AutoRegisterXxlJob autoRegisterXxlJob) {
        List<XxlJobInfo> jobInfoList = enhanceXxlJobService.getJobInfoList(xxlJobGroup.getId(), executorHandler);
        if (CollectionUtil.isNotEmpty(jobInfoList)) {
            if (jobInfoList.stream().anyMatch(o -> o.getExecutorHandler().equals(executorHandler))) {
                log.info("xxl-job 任务{}已经存在，不需要再次注册", executorHandler);
                return;
            }
        }
        XxlJobInfo xxlJobInfo = createXxlJobInfo(xxlJobGroup, executorHandler, autoRegisterXxlJob);
        Integer registerJob = enhanceXxlJobService.registerJob(xxlJobInfo);
        log.info("xxl-job 任务{}注册成功，任务ID为{}", executorHandler, registerJob);
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

    private void addJobGroup() {
        if (enhanceXxlJobService.preciselyCheck()) {
            log.info("xxl-job 执行器已经存在，不需要再次注册");
            return;
        }
        if (enhanceXxlJobService.autoRegisterJobGroup()) {
            log.info("xxl-job 执行器注册成功");
        }
    }
}
