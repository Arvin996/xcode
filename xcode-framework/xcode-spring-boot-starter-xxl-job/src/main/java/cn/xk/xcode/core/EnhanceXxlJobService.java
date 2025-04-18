package cn.xk.xcode.core;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.xk.xcode.config.XxlJobProperties;
import cn.xk.xcode.entity.XxlJobGroup;
import cn.xk.xcode.entity.XxlJobInfo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.xxl.job.core.handler.impl.MethodJobHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static cn.xk.xcode.config.XxlJobGlobalConstants.*;

/**
 * @Author xuk
 * @Date 2025/2/5 11:25
 * @Version 1.0.0
 * @Description EnhanceXxlJobService
 **/
@RequiredArgsConstructor
@SuppressWarnings("all")
@Slf4j
public class EnhanceXxlJobService {

    public static final ConcurrentHashMap<String, String> LOGIN_COOKIE_MAP = new ConcurrentHashMap<>();

    private final XxlJobProperties xxlJobProperties;

    private static final String XXL_JOB_LOGIN_URL = "/login";
    private static final String XXL_JOB_COOKIE_NAME = "XXL_JOB_LOGIN_IDENTITY";
    /**
     * 执行器组接口路径
     */
    private static final String XXL_JOB_GROUPS_URL = "/jobgroup/pageList";
    private static final String SAVE_XXL_JOB_GROUP_URL = "/jobgroup/save";
    /**
     * 任务信息接口路径
     */
    private static final String GET_XXL_JOB_INFO = "/jobinfo/pageList";
    private static final String ADD_XXL_JOB_INFO = "/jobinfo/add";
    public static final String UPDATE_URL = "/jobinfo/update";
    public static final String DELETE_URL = "/jobinfo/remove";
    public static final String RUN_URL = "/jobinfo/start";
    public static final String STOP_URL = "/jobinfo/stop";

    public CommonResult<Boolean> updateXxlJob(XxlJobInfo xxlJobInfo) {
        String updateUrl = xxlJobProperties.getAdminAddress() + UPDATE_URL;
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(xxlJobInfo);
        HttpResponse httpResponse = HttpRequest.post(updateUrl).form(stringObjectMap).cookie(getJobCookie()).execute();
        if (!httpResponse.isOk()) {
            return CommonResult.error(XXL_JOB_ADMIN_ERROR);
        }
        ReturnT<?> returnT = JSON.parseObject(httpResponse.body(), ReturnT.class);
        if (ReturnT.SUCCESS_CODE == returnT.getCode()) {
            return CommonResult.success(true);
        } else {
            return CommonResult.error(XXL_JOB_ADMIN_ERROR);
        }
    }

    public CommonResult<Boolean> deleteXxlJob(Integer jobId) {
        String deleteUrl = xxlJobProperties.getAdminAddress() + DELETE_URL;
        HashMap<String, Object> params = MapUtil.newHashMap();
        params.put("id", jobId);
        HttpResponse httpResponse = HttpRequest.post(deleteUrl).form(params).cookie(getJobCookie()).execute();
        if (!httpResponse.isOk()) {
            return CommonResult.error(XXL_JOB_ADMIN_ERROR);
        }
        ReturnT<?> returnT = JSON.parseObject(httpResponse.body(), ReturnT.class);
        if (ReturnT.SUCCESS_CODE == returnT.getCode()) {
            return CommonResult.success(true);
        } else {
            return CommonResult.error(XXL_JOB_ADMIN_ERROR);
        }
    }

    public CommonResult<Boolean> startXxlJob(Integer jobId) {
        String startUrl = xxlJobProperties.getAdminAddress() + RUN_URL;
        HashMap<String, Object> params = MapUtil.newHashMap();
        params.put("id", jobId);
        HttpResponse httpResponse = HttpRequest.post(startUrl).form(params).cookie(getJobCookie()).execute();
        if (!httpResponse.isOk()) {
            return CommonResult.error(XXL_JOB_ADMIN_ERROR);
        }
        ReturnT<?> returnT = JSON.parseObject(httpResponse.body(), ReturnT.class);
        if (ReturnT.SUCCESS_CODE == returnT.getCode()) {
            return CommonResult.success(true);
        } else {
            return CommonResult.error(XXL_JOB_ADMIN_ERROR);
        }
    }

    public CommonResult<Boolean> stopXxlJob(Integer jobId) {
        String stopUrl = xxlJobProperties.getAdminAddress() + STOP_URL;
        HashMap<String, Object> params = MapUtil.newHashMap();
        params.put("id", jobId);
        HttpResponse httpResponse = HttpRequest.post(stopUrl).form(params).cookie(getJobCookie()).execute();
        if (!httpResponse.isOk()) {
            return CommonResult.error(XXL_JOB_ADMIN_ERROR);
        }
        ReturnT<?> returnT = JSON.parseObject(httpResponse.body(), ReturnT.class);
        if (ReturnT.SUCCESS_CODE == returnT.getCode()) {
            return CommonResult.success(true);
        } else {
            return CommonResult.error(XXL_JOB_ADMIN_ERROR);
        }
    }

    private void getAndSetJobCookie() {
        String loginUrl = xxlJobProperties.getAdminAddress() + XXL_JOB_LOGIN_URL;
        HttpResponse httpResponse = HttpRequest.post(loginUrl)
                .form("userName", xxlJobProperties.getUsername())
                .form("password", xxlJobProperties.getPassword())
                .execute();
        if (!httpResponse.isOk()) {
            ExceptionUtil.castServerException(XXL_JOB_ADMIN_ERROR);
        }
        HttpCookie cookie = httpResponse.getCookie(XXL_JOB_COOKIE_NAME);
        ObjectUtil.ifNullCastServerException(cookie, XXL_JOB_COOKIE_ERROR);
        LOGIN_COOKIE_MAP.putIfAbsent(XXL_JOB_COOKIE_NAME, cookie.getValue());
    }

    public String getJobCookie() {
        for (int i = 0; i < GET_COOKIE_COUNT; i++) {
            String cookie = LOGIN_COOKIE_MAP.get(XXL_JOB_COOKIE_NAME);
            if (StrUtil.isNotBlank(cookie)) {
                return XXL_JOB_COOKIE_NAME + "=" + cookie;
            }
            getAndSetJobCookie();
        }
        throw new ServerException(XXL_JOB_COOKIE_ERROR);
    }

    public CommonResult<List<XxlJobGroup>> getJobGroupList() {
        String title = xxlJobProperties.getExecutorTitle();
        String appname = xxlJobProperties.getAppname();
        String jobGroupUrl = xxlJobProperties.getAdminAddress() + XXL_JOB_GROUPS_URL;
        HttpResponse httpResponse = HttpRequest.post(jobGroupUrl)
                .form("title", title)
                .form("appname", appname)
                .cookie(getJobCookie())
                .execute();
        if (!httpResponse.isOk()) {
            return CommonResult.error(XXL_JOB_ADMIN_ERROR);
        }
        String body = httpResponse.body();
        ReturnT<?> returnT = JSON.parseObject(httpResponse.body(), ReturnT.class);
        if (ReturnT.SUCCESS_CODE != returnT.getCode()) {
            return CommonResult.error(XXL_JOB_ADMIN_ERROR);
        }
        JSONArray jsonArray = JSON.parseObject(body).getJSONArray("data");
        return CommonResult.success(jsonArray.stream().map(o -> JSON.parseObject(o.toString(), XxlJobGroup.class)).collect(Collectors.toList()));
    }

    public boolean preciselyCheck() {
        String title = xxlJobProperties.getExecutorTitle();
        String appname = xxlJobProperties.getAppname();
        CommonResult<List<XxlJobGroup>> jobGroupList = getJobGroupList();
        if (!CommonResult.isSuccess(jobGroupList)) {
            return false;
        }
        return jobGroupList.getData().stream().anyMatch(o -> o.getAppname().equals(appname) && o.getTitle().equals(title));
    }

    // 自动注册执行器
    public CommonResult<Boolean> autoRegisterJobGroup() {
        String saveJobGroupUrl = xxlJobProperties.getAdminAddress() + SAVE_XXL_JOB_GROUP_URL;
        HttpResponse httpResponse = HttpRequest.post(saveJobGroupUrl)
                .form("appname", xxlJobProperties.getAppname())
                .form("title", xxlJobProperties.getExecutorTitle())
                .cookie(getJobCookie())
                .execute();
        if (!httpResponse.isOk()) {
            return CommonResult.error(XXL_JOB_ADMIN_ERROR);
        }
        ReturnT<?> returnT = JSON.parseObject(httpResponse.body(), ReturnT.class);
        if (ReturnT.SUCCESS_CODE == returnT.getCode()) {
            return CommonResult.success(true);
        } else {
            return CommonResult.error(XXL_JOB_ADMIN_ERROR);
        }
    }

    /**
     * @param jobGroupId      执行器ID
     * @param executorHandler 执行器处理器名称 也就是@XxlJob("xxx)中的xxx
     * @return 返回符合条件的任务
     */
    public CommonResult<List<XxlJobInfo>> getJobInfoList(Integer jobGroupId, String executorHandler) throws ServerException {
        String jobInfoUrl = xxlJobProperties.getAdminAddress() + GET_XXL_JOB_INFO;
        HttpResponse httpResponse = HttpRequest.post(jobInfoUrl)
                .form("jobGroup", jobGroupId)
                .form("executorHandler", executorHandler)
                .form("triggerStatus", -1)
                .cookie(getJobCookie())
                .execute();
        if (!httpResponse.isOk()) {
            return CommonResult.error(XXL_JOB_ADMIN_ERROR);
        }
        ReturnT<?> returnT = JSON.parseObject(httpResponse.body(), ReturnT.class);
        if (ReturnT.SUCCESS_CODE != returnT.getCode()) {
            return CommonResult.error(XXL_JOB_ADMIN_ERROR);
        }
        String body = httpResponse.body();
        JSONArray jsonArray = JSON.parseObject(body).getJSONArray("data");
        return CommonResult.success(jsonArray.stream().map(o -> JSON.parseObject(o.toString(), XxlJobInfo.class)).collect(Collectors.toList()));
    }

    /**
     * 注册一个新的XxlJob任务。
     *
     * @param xxlJobInfo 要注册的任务信息
     * @return 返回注册成功后返回的任务ID
     */
    public CommonResult<Integer> registerJob(XxlJobInfo xxlJobInfo) {
        // 获取添加任务信息的URL
        String addJobInfoUrl = xxlJobProperties.getAdminAddress() + ADD_XXL_JOB_INFO;
        // 将任务信息对象转换为Map
        Map<String, Object> map = BeanUtil.beanToMap(xxlJobInfo);
        // 发送POST请求，将任务信息提交到XxlJob管理端
        HttpResponse httpResponse = HttpRequest.post(addJobInfoUrl)
                .form(map)
                .cookie(getJobCookie())
                .execute();
        // 检查HTTP响应是否成功
        if (!httpResponse.isOk()) {
            // 如果响应不成功，抛出服务器异常
            return CommonResult.error(XXL_JOB_ADMIN_ERROR);
        }
        ReturnT<?> returnT = JSON.parseObject(httpResponse.body(), ReturnT.class);
        if (ReturnT.SUCCESS_CODE != returnT.getCode()) {
            return CommonResult.error(XXL_JOB_ADMIN_ERROR);
        }
        // 返回任务ID
        return CommonResult.success(Integer.parseInt(returnT.getContent().toString()));
    }

    public String registerJobHandlerToLocalCahce(String handler, Object target, Method targetMethod, String initMethodName, String destroyMethodName) {
        Method initMethod = null;
        Method destroyMethod = null;
        // 没有@xxl-job 注解 但是有@AutoRegisterXxlJob 注解 自动注册 如果
        if (StringUtils.isEmpty(handler)) {
            log.warn("【警告】 方法{}没有@XxlJob注解，也没有@AutoRegisterXxlJob注解中的executorHandler属性, 将会以类名_方法名称作为任务名称", targetMethod.getName());
            handler = target.getClass().getSimpleName() + "_" + targetMethod.getName();
        }
        if (StringUtils.isNotEmpty(initMethodName)) {
            try {
                initMethod = handler.getClass().getDeclaredMethod(initMethodName);
                initMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                log.error("xxl-job method-jobhandler initMethod invalid, for[" + target.getClass() + "#" + initMethodName + "] .");
            }
        }
        if (StringUtils.isNotEmpty(destroyMethodName)) {
            try {
                destroyMethod = target.getClass().getDeclaredMethod(destroyMethodName);
                destroyMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                log.error("xxl-job method-jobhandler initMethod invalid, for[" + target.getClass() + "#" + destroyMethodName + "] .");
            }
        }
        // 1. 内存注册处理器
        if (Objects.isNull(XxlJobSpringExecutor.loadJobHandler(handler))) {
            XxlJobSpringExecutor.registJobHandler(handler, new MethodJobHandler(target, targetMethod, initMethod, destroyMethod));
        } else {
            log.warn("xxl-job 任务{}已经存在，请检查是否有重复注册", handler);
        }
        return handler;
    }
}
