package cn.xk.xcode.enums;

import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author xuk
 * @Date 2024/8/6 12:12
 * @Version 1.0
 * @Description MemberIntErrorCodeConstants
 */
public interface MemberErrorCodeConstants
{
    // ========== 用户相关  1-004-001-000 ============
    IntErrorCode USER_NOT_EXISTS = new IntErrorCode(1_004_001_000, "用户不存在");
    IntErrorCode USER_MOBILE_NOT_EXISTS = new IntErrorCode(1_004_001_001, "手机号未注册用户");
    IntErrorCode USER_MOBILE_USED = new IntErrorCode(1_004_001_002, "修改手机失败，该手机号({})已经被使用");
    IntErrorCode USER_POINT_NOT_ENOUGH = new IntErrorCode(1_004_001_003, "用户积分余额不足");

    // ========== AUTH 模块 1-004-003-000 ==========
    IntErrorCode AUTH_LOGIN_BAD_CREDENTIALS = new IntErrorCode(1_004_003_000, "登录失败，账号密码不正确");
    IntErrorCode AUTH_LOGIN_USER_DISABLED = new IntErrorCode(1_004_003_001, "登录失败，账号被禁用");
    IntErrorCode AUTH_SOCIAL_USER_NOT_FOUND = new IntErrorCode(1_004_003_005, "登录失败，解析不到三方登录信息");
    IntErrorCode AUTH_MOBILE_USED = new IntErrorCode(1_004_003_007, "手机号已经被使用");

    // ========== 用户收件地址 1-004-004-000 ==========
    IntErrorCode ADDRESS_NOT_EXISTS = new IntErrorCode(1_004_004_000, "用户收件地址不存在");

    //========== 用户标签 1-004-006-000 ==========
    IntErrorCode TAG_NOT_EXISTS = new IntErrorCode(1_004_006_000, "用户标签不存在");
    IntErrorCode TAG_NAME_EXISTS = new IntErrorCode(1_004_006_001, "用户标签已经存在");
    IntErrorCode TAG_HAS_USER = new IntErrorCode(1_004_006_002, "用户标签下存在用户，无法删除");

    //========== 积分配置 1-004-007-000 ==========

    //========== 积分记录 1-004-008-000 ==========
    IntErrorCode POINT_RECORD_BIZ_NOT_SUPPORT = new IntErrorCode(1_004_008_000, "用户积分记录业务类型不支持");

    //========== 签到配置 1-004-009-000 ==========
    IntErrorCode SIGN_IN_CONFIG_NOT_EXISTS = new IntErrorCode(1_004_009_000, "签到天数规则不存在");
    IntErrorCode SIGN_IN_CONFIG_EXISTS = new IntErrorCode(1_004_009_001, "签到天数规则已存在");

    //========== 签到配置 1-004-010-000 ==========
    IntErrorCode SIGN_IN_RECORD_TODAY_EXISTS = new IntErrorCode(1_004_010_000, "今日已签到，请勿重复签到");

    //========== 用户等级 1-004-011-000 ==========
    IntErrorCode LEVEL_NOT_EXISTS = new IntErrorCode(1_004_011_000, "用户等级不存在");
    IntErrorCode LEVEL_NAME_EXISTS = new IntErrorCode(1_004_011_001, "用户等级名称[{}]已被使用");
    IntErrorCode LEVEL_VALUE_EXISTS = new IntErrorCode(1_004_011_002, "用户等级值[{}]已被[{}]使用");
    IntErrorCode LEVEL_EXPERIENCE_MIN = new IntErrorCode(1_004_011_003, "升级经验必须大于上一个等级[{}]设置的升级经验[{}]");
    IntErrorCode LEVEL_EXPERIENCE_MAX = new IntErrorCode(1_004_011_004, "升级经验必须小于下一个等级[{}]设置的升级经验[{}]");
    IntErrorCode LEVEL_HAS_USER = new IntErrorCode(1_004_011_005, "用户等级下存在用户，无法删除");
    IntErrorCode EXPERIENCE_BIZ_NOT_SUPPORT = new IntErrorCode(1_004_011_201, "用户经验业务类型不支持");

    //========== 用户分组 1-004-012-000 ==========
    IntErrorCode GROUP_NOT_EXISTS = new IntErrorCode(1_004_012_000, "用户分组不存在");
    IntErrorCode GROUP_HAS_USER = new IntErrorCode(1_004_012_001, "用户分组下存在用户，无法删除");

}
