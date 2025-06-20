package cn.xk.xcode.config;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author xuk
 * @Date 2025/5/30 13:29
 * @Version 1.0.0
 * @Description DistributionCardProductErrorCode
 **/
public interface DistributionCardProductErrorCode {

    ErrorCode MERCHANT_USERNAME_ALREADY_EXISTS = new IntErrorCode(4005_1_500, "用户名{}已存在");
    ErrorCode PASSWORD_AND_CONFIRM_PASSWORD_NOT_EQUALS = new IntErrorCode(4005_1_501, "密码和确认密码不一致");
    ErrorCode MERCHANT_NOT_EXISTS = new IntErrorCode(4005_1_502, "用户不存在");
    ErrorCode MERCHANT_IS_DISABLED = new IntErrorCode(4005_1_503, "用户已被禁用");
    ErrorCode MERCHANT_USERNAME_OR_PASSWORD_ERROR = new IntErrorCode(4005_1_504, "用户名或密码错误");
    ErrorCode MERCHANT_LOGIN_EMAIL_MUST_NOT_EMPTY = new IntErrorCode(4005_1_505, "邮箱不能为空");
    ErrorCode STORE_HAS_EXISTS = new IntErrorCode(4005_1_506, "店铺已存在");
    ErrorCode STORE_CONTACT_QQ_AND_WX_MUST_NOT_EMPTY = new IntErrorCode(4005_1_507, "店铺QQ和WX联系方式不能同时为空");
    ErrorCode STORE_NAME_CONTAINS_SENSITIVE_WORDS = new IntErrorCode(4005_1_508, "店铺名称包含敏感词");
    ErrorCode STORE_DESC_CONTAINS_SENSITIVE_WORDS = new IntErrorCode(4005_1_509, "店铺描述包含敏感词");
    ErrorCode STORE_NOT_EXISTS = new IntErrorCode(4005_1_510, "店铺不存在");
    ErrorCode STORE_IS_DISABLED = new IntErrorCode(4005_1_511, "店铺已被禁用");
    ErrorCode STORE_IN_AUDIT_NOT_SUPPORT_UPDATE = new IntErrorCode(4005_1_512, "店铺审核中不支持更新");
    ErrorCode STORE_IN_AUDIT_NOT_SUPPORT_DELETE = new IntErrorCode(4005_1_513, "店铺审核中不支持删除");
    ErrorCode STORE_HAS_MAX_NUM = new IntErrorCode(4005_1_514, "店铺数量已达上限");
    ErrorCode STORE_NOT_IN_AUDIT_SUCCESS_NOT_SUPPORT_ADD_SPU = new IntErrorCode(4005_1_515, "店铺未审核通过不支持添加商品");
    ErrorCode SPU_NAME_CONTAINS_SENSITIVE_WORDS = new IntErrorCode(4005_1_516, "商品spu名称包含敏感词");
    ErrorCode SPU_INTRODUCTION_CONTAINS_SENSITIVE_WORDS = new IntErrorCode(4005_1_517, "商品spu简介包含敏感词");
    ErrorCode SPU_KEYWORDS_CONTAINS_SENSITIVE_WORDS = new IntErrorCode(4005_1_518, "商品spu关键词包含敏感词");
    ErrorCode SPU_DESCRIPTION_CONTAINS_SENSITIVE_WORDS = new IntErrorCode(4005_1_519, "商品spu详情包含敏感词");
    ErrorCode SPU_ATTRIBUTE_CONTAINS_SENSITIVE_WORDS = new IntErrorCode(4005_1_520, "商品spu属性包含敏感词");
    ErrorCode SPU_HAS_EXISTS = new IntErrorCode(4005_1_521, "商品spu已存在");
    ErrorCode SPU_NOT_EXISTS = new IntErrorCode(4005_1_522, "商品spu不存在");
    ErrorCode SPU_IS_DISABLED = new IntErrorCode(4005_1_523, "商品spu已被禁用");
    ErrorCode SPU_NOT_IN_AUDIT_SUCCESS_NOT_SUPPORT_UPDATE_SPU = new IntErrorCode(4005_1_524, "商品spu未审核通过不支持更新");
    ErrorCode STORE_HAS_SPU = new IntErrorCode(4005_1_525, "店铺下有商品spu上架，不能删除");
    ErrorCode SPU_IN_AUDIT_NOT_SUPPORT_DELETE = new IntErrorCode(4005_1_526, "商品spu审核中不支持删除");
    ErrorCode SPU_HAS_SKU = new IntErrorCode(4005_1_527, "商品spu下有sku上架，不能删除");
    ErrorCode SPU_NOT_IN_AUDIT_SUCCESS_NOT_SUPPORT_ADD_SKU = new IntErrorCode(4005_1_528, "商品spu未审核通过不支持添加sku");
    ErrorCode SKU_CODE_HAS_EXISTS = new IntErrorCode(4005_1_529, "sku编码已存在");
    ErrorCode SKU_ATTRIBUTE_VALUE_NOT_EQUAL_SPU_ATTRIBUTE_NUM = new IntErrorCode(4005_1_530, "sku属性值个数不等于spu属性个数");
    ErrorCode SKU_ATTRIBUTE_VALUE_CONTAINS_SENSITIVE_WORDS  = new IntErrorCode(4005_1_531, "sku属性值包含敏感词");
    ErrorCode SKU_NOT_EXISTS = new IntErrorCode(4005_1_532, "商品sku不存在");
    ErrorCode SKU_NOT_IN_AUDIT_SUCCESS_NOT_SUPPORT_UPDATE = new IntErrorCode(4005_1_533, "商品sku未审核通过不支持更新");
    ErrorCode SKU_IS_DISABLED = new IntErrorCode(4005_1_534, "商品sku已被禁用");
    ErrorCode SKU_PROMOTION_PRICE_CANNOT_GREATER_THAN_PRICE = new IntErrorCode(4005_1_535, "商品sku促销价格不能大于价格");
    ErrorCode SKU_LOW_STOCK_CANNOT_GREATER_THAN_STOCK = new IntErrorCode(4005_1_536, "商品sku预警库存不能大于库存");
    ErrorCode SKU_IN_AUDIT_NOT_SUPPORT_DELETE = new IntErrorCode(4005_1_537, "商品sku审核中不支持删除");
    ErrorCode CATEGORY_NAME_ALREADY_EXISTS = new IntErrorCode(4005_1_538, "商品分类名称已存在");
    ErrorCode PARENT_CATEGORY_NOT_EXISTS = new IntErrorCode(4005_1_539, "父分类不存在");
    ErrorCode CATEGORY_NOT_EXISTS = new IntErrorCode(4005_1_540, "商品分类不存在");
    ErrorCode CATEGORY_HAS_SKU = new IntErrorCode(4005_1_541, "商品分类下有sku上架，不能删除");
    ErrorCode CATEGORY_HAS_CHILDREN = new IntErrorCode(4005_1_542, "商品分类下有子分类，不能删除");
    ErrorCode AUDIT_NOT_EXISTS = new IntErrorCode(4005_1_543, "审核不存在");
    ErrorCode AUDIT_RESULT_NOT_VALID = new IntErrorCode(4005_1_544, "审核结果不合法");
    ErrorCode AUDIT_NOT_PASS_REASON_MUST_NOT_EMPTY = new IntErrorCode(4005_1_545, "审核不通过原因不能为空");
    ErrorCode LOGIN_ERROR_THEN_REGISTER = new IntErrorCode(4005_1_546, "登录失败，获取用户信息失败请再次注册");
    ErrorCode LOGIN_PARAMS_ERROR = new IntErrorCode(4005_1_547, "登录参数错误, 缺少登录类型信息");
    ErrorCode MERCHANT_EMAIL_ALREADY_EXISTS = new IntErrorCode(4005_1_548, "邮箱{}已被注册");
    ErrorCode LOGIN_ERROR_COUNT_FIVE_DISABLE = new IntErrorCode(4005_1_549, "登录错误次数超过五次，账号已被禁用");
    ErrorCode SEND_EMAIL_ERROR = new IntErrorCode(4005_1_550, "发送邮件失败");

    int MAX_STORE_NUM = 5;
    String USER_LOGIN_ERROR_PREFIX = "user_login_error_";
    String LOGIN_LOCKED_USER_EMAIL_KEY_PREFIX = "login_locked_user_email_";
    String LOGIN_LOCKED_USER_USERNAME_KEY_PREFIX = "login_locked_user_username_";
    String FIND_PASSWORD_TOKEN_PREFIX = "find_password_token_";
    int MAX_ERROR_LOGIN_COUNT = 5;
    int MAX_ERROR_TO_CAPTCHA = 3;
    int ERROR_COUNT_EXPIRE_TIME = 60 * 60;
    int LOCK_DISABLED_TIME = 30 * 60;
    int FIND_PASSWORD_TOKEN_EXPIRE_TIME = 3 * 60;
}
