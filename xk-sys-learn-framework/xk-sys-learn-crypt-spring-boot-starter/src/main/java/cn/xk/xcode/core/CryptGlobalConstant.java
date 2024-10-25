package cn.xk.xcode.core;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author xuk
 * @Date 2024/10/23 17:01
 * @Version 1.0.0
 * @Description CryptGlobalConstant
 **/
public interface CryptGlobalConstant {

    ErrorCode ERROR_DECRYPT  = new IntErrorCode(200_0_502, "解密算法{}解密字符串{}错误, 错误信息：{}");
    ErrorCode ERROR_ENCRYPT  = new IntErrorCode(201_0_503, "解密算法{}加密字符串{}错误， 错误信息：{}");
    ErrorCode CRYPT_KEY_MUST_NOT_NULL  = new IntErrorCode(202_0_504, "密钥不能为空");
    ErrorCode CRYPT_IV_MUST_NOT_NULL  = new IntErrorCode(202_0_505, "填充向量不能为空");
    ErrorCode KEY_PATH_IS_ERROR  = new IntErrorCode(203_0_505, "密钥文件路径{}配置错误");
    ErrorCode READ_PUBLIC_KEY_ERROR  = new IntErrorCode(204_0_506, "读取公钥文件失败，文件路径:{},错误信息:{}");
    ErrorCode READ_PRIVATE_KEY_ERROR  = new IntErrorCode(205_0_507, "读取私钥文件失败，文件路径:{},错误信息:{}");
    ErrorCode READ_KEY_ERROR  = new IntErrorCode(206_0_507, "读取密钥文件失败");
    ErrorCode PUBLIC_KEY_IS_NULL  = new IntErrorCode(207_0_508, "公钥信息配置为空");
    ErrorCode PRIVATE_KEY_IS_NULL  = new IntErrorCode(208_0_509, "私钥信息配置位空");
    ErrorCode COSTUME_SIGN_ALG_NOT_CONFIG  = new IntErrorCode(209_0_509, "自定义签名算法未配置");
    ErrorCode COSTUME_CRYPT_ALG_NOT_CONFIG  = new IntErrorCode(209_0_510, "自定义加解密算法未配置");
    ErrorCode COSTUME_CRYPT_ALG_MUST_BE_UNIQUE  = new IntErrorCode(209_0_511, "自定义加解密算法必须唯一");
    ErrorCode SYMMETRIC_ALG_KEY_MUST_NOT_NULL  = new IntErrorCode(209_0_512, "对称加密算法密钥不能为空");
    ErrorCode COSTUME_SIGN_ALG_MUST_BE_UNIQUE =  new IntErrorCode(209_0_513, "自定义签名算法必须唯一");
}
