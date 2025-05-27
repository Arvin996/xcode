package cn.xk.xcode.config;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author xuk
 * @Date 2025/2/5 11:34
 * @Version 1.0.0
 * @Description XxlJobGlobalConstants
 **/
public interface XxlJobGlobalConstants {

    int GET_COOKIE_COUNT = 3;

    ErrorCode XXL_JOB_ADMIN_ERROR = new IntErrorCode(1400_0_500, "调用xxl-job 调度中心错误,粗票务信息：{}");
    ErrorCode XXL_JOB_COOKIE_ERROR  =  new IntErrorCode(1400_0_501, "xxl-job cookie 获取失败");
    ErrorCode REGISTER_XXL_JOB_ERROR =  new IntErrorCode(1400_0_502, "xxl-job 任务注册失败");
}
