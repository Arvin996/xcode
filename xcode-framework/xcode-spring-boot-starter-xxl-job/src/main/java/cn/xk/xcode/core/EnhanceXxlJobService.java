package cn.xk.xcode.core;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.xk.xcode.config.XxlJobProperties;
import cn.xk.xcode.entity.XxlJobGroup;
import cn.xk.xcode.entity.XxlJobInfo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;

import java.net.HttpCookie;
import java.util.List;
import java.util.Map;
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
public class EnhanceXxlJobService {

    public static final ConcurrentHashMap<String, String> LOGIN_COOKIE_MAP = new ConcurrentHashMap<>();

    private final XxlJobProperties xxlJobProperties;

    private static final String XXL_JOB_LOGIN_URL = "/login";
    private static final String XXL_JOB_COOKIE_NAME = "XXL_JOB_LOGIN_IDENTITY";
    private static final String XXL_JOB_GROUPS_URL = "/jobgroup/pageList";
    private static final String SAVE_XXL_JOB_GROUP_URL = "/jobgroup/save";
    private static final String GET_XXL_JOB_INFO = "/jobinfo/pageList";
    private static final String ADD_XXL_JOB_INFO = "/jobinfo/add";

    private void getAndSetJobCookie() {
        String loginUrl = xxlJobProperties.getAdminAddresses() + XXL_JOB_LOGIN_URL;
        HttpResponse httpResponse = HttpRequest.post(loginUrl)
                .form("username", xxlJobProperties.getUsername())
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
                return cookie;
            }
            getAndSetJobCookie();
        }
        throw new ServerException(XXL_JOB_COOKIE_ERROR);
    }

    public List<XxlJobGroup> getJobGroupList() {
        String title = xxlJobProperties.getExecutorTitle();
        String appname = xxlJobProperties.getAppname();
        String jobGroupUrl = xxlJobProperties.getAdminAddresses() + XXL_JOB_GROUPS_URL;
        HttpResponse httpResponse = HttpRequest.post(jobGroupUrl)
                .form("title", title)
                .form("appname", appname)
                .cookie(getJobCookie())
                .execute();
        if (!httpResponse.isOk()) {
            ExceptionUtil.castServerException(XXL_JOB_ADMIN_ERROR);
        }
        String body = httpResponse.body();
        JSONArray jsonArray = JSON.parseObject(body).getJSONArray("data");
        return jsonArray.stream().map(o -> JSON.parseObject(o.toString(), XxlJobGroup.class)).collect(Collectors.toList());
    }

    public boolean preciselyCheck() {
        String title = xxlJobProperties.getExecutorTitle();
        String appname = xxlJobProperties.getAppname();
        return getJobGroupList().stream().anyMatch(o -> o.getAppname().equals(appname) && o.getTitle().equals(title));
    }

    // 自动注册执行器
    public boolean autoRegisterJobGroup() {
        String saveJobGroupUrl = xxlJobProperties.getAdminAddresses() + SAVE_XXL_JOB_GROUP_URL;
        HttpResponse httpResponse = HttpRequest.post(saveJobGroupUrl)
                .form("appname", xxlJobProperties.getAppname())
                .form("title", xxlJobProperties.getExecutorTitle())
                .cookie(getJobCookie())
                .execute();
        if (!httpResponse.isOk()) {
            ExceptionUtil.castServerException(XXL_JOB_ADMIN_ERROR);
        }
        return httpResponse.isOk();
    }

    /**
     * @param jobGroupId      执行器ID
     * @param executorHandler 执行器处理器名称 也就是@XxlJob("xxx)中的xxx
     * @return 返回符合条件的任务
     */
    public List<XxlJobInfo> getJobInfoList(Integer jobGroupId, String executorHandler) {
        String jobInfoUrl = xxlJobProperties.getAdminAddresses() + GET_XXL_JOB_INFO;
        HttpResponse httpResponse = HttpRequest.post(jobInfoUrl)
                .form("jobGroup", jobGroupId)
                .form("executorHandler", executorHandler)
                .cookie(getJobCookie())
                .execute();
        if (!httpResponse.isOk()) {
            ExceptionUtil.castServerException(XXL_JOB_ADMIN_ERROR);
        }
        String body = httpResponse.body();
        JSONArray jsonArray = JSON.parseObject(body).getJSONArray("data");
        return jsonArray.stream().map(o -> JSON.parseObject(o.toString(), XxlJobInfo.class)).collect(Collectors.toList());
    }

    /**
     * 注册一个新的XxlJob任务。
     *
     * @param xxlJobInfo 要注册的任务信息
     * @return 返回注册成功后返回的任务ID
     */
    public Integer registerJob(XxlJobInfo xxlJobInfo) {
        // 获取添加任务信息的URL
        String addJobInfoUrl = xxlJobProperties.getAdminAddresses() + ADD_XXL_JOB_INFO;

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
            ExceptionUtil.castServerException(XXL_JOB_ADMIN_ERROR);
        }

        // 将HTTP响应体解析为JSON对象
        JSONObject jsonObject = JSON.parseObject(httpResponse.body());

        // 获取响应中的状态码
        String code = jsonObject.getString("code");

        // 如果状态码为200，表示任务注册成功
        if ("200".equals(code)) {
            // 返回任务ID
            ExceptionUtil.castServerException(REGISTER_XXL_JOB_ERROR);
        }

        // 返回任务ID
        return jsonObject.getIntValue("content");
    }


}
