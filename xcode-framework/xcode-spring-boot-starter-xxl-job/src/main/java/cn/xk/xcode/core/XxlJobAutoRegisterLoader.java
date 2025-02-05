package cn.xk.xcode.core;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.annotation.AutoRegisterXxlJob;
import cn.xk.xcode.entity.XxlJobGroup;
import cn.xk.xcode.entity.XxlJobInfo;
import cn.xk.xcode.utils.collections.CollectionUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/2/5 14:41
 * @Version 1.0.0
 * @Description XxlJobAutoRegisterLoader
 **/
@Slf4j
@RequiredArgsConstructor
public class XxlJobAutoRegisterLoader implements ApplicationRunner, BeanPostProcessor {

    private final EnhanceXxlJobService enhanceXxlJobService;

    @Override
    public void run(ApplicationArguments args) {
        // 注册执行器
        addJobGroup();
        // 注册任务
        addJobInfo();
    }

    private void addJobInfo() {
        XxlJobGroup xxlJobGroup = enhanceXxlJobService.getJobGroupList().get(0);
        // 获取所有bean
        String[] beanNamesForType = SpringUtil.getBeanNamesForType(Object.class);
        for (String beanName : beanNamesForType) {
            Object bean = SpringUtil.getBean(beanName);
            List<Method> methods = MethodUtils.getMethodsListWithAnnotation(bean.getClass(), XxlJob.class);
            if (CollectionUtil.isNotEmpty(methods)) {
                for (Method method : methods) {
                    if (method.isAnnotationPresent(AutoRegisterXxlJob.class)) {
                        XxlJob xxlJob = method.getAnnotation(XxlJob.class);
                        AutoRegisterXxlJob autoRegisterXxlJob = method.getAnnotation(AutoRegisterXxlJob.class);
                        List<XxlJobInfo> jobInfoList = enhanceXxlJobService.getJobInfoList(xxlJobGroup.getId(), xxlJob.value());
                        if (CollectionUtil.isNotEmpty(jobInfoList)) {
                            if (jobInfoList.stream().anyMatch(o -> o.getExecutorHandler().equals(xxlJob.value()))) {
                                log.info("xxl-job 任务{}已经存在，不需要再次注册", xxlJob.value());
                                continue;
                            }
                        }
                        // 注册
                        XxlJobInfo xxlJobInfo = createXxlJobInfo(xxlJobGroup, xxlJob, autoRegisterXxlJob);
                        Integer registerJob = enhanceXxlJobService.registerJob(xxlJobInfo);
                        log.info("xxl-job 任务{}注册成功，任务ID为{}", xxlJob.value(), registerJob);
                    }
                }
            }
        }
    }

    private XxlJobInfo createXxlJobInfo(XxlJobGroup xxlJobGroup, XxlJob xxlJob, AutoRegisterXxlJob autoRegisterXxlJob) {
        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setJobGroup(xxlJobGroup.getId());
        xxlJobInfo.setJobDesc(autoRegisterXxlJob.jobDesc());
        xxlJobInfo.setAddTime(new Date());
        xxlJobInfo.setScheduleConf(autoRegisterXxlJob.cron());
        xxlJobInfo.setScheduleType("CRON");
        xxlJobInfo.setGlueType("BEAN");
        xxlJobInfo.setAuthor(autoRegisterXxlJob.author());
        xxlJobInfo.setAlarmEmail(StrUtil.isBlank(autoRegisterXxlJob.warnEmail()) ? null : autoRegisterXxlJob.warnEmail());
        xxlJobInfo.setExecutorHandler(xxlJob.value());
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
