package cn.xk.xcode.config;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author Administrator
 * @Date 2024/8/29 11:14
 * @Version V1.0.0
 * @Description InfraGlobalErrorCodeConstants
 */
public interface InfraGlobalErrorCodeConstants {
    ErrorCode GEN_CODE_FILE_FAILED = new IntErrorCode(4004_1_500, "生成二维码错误");
    ErrorCode UPLOAD_FILE_ERROR =  new IntErrorCode(4004_1_501, "上传文件错误");
    ErrorCode UPLOAD_FILE_IS_EMPTY =  new IntErrorCode(4004_1_502, "上传文件为空");
    ErrorCode UPLOAD_FILE_NAME_IS_EMPTY =  new IntErrorCode(4004_1_503, "上传文件名为空");
    ErrorCode FILE_NOT_EXISTS =  new IntErrorCode(4004_1_504, "文件不存在，已被删除");
    ErrorCode FILE_HAS_PROCESSING = new IntErrorCode(4004_1_505, "文件还在处理中 删除失败");
    ErrorCode UPDATE_FILE_ERROR =  new IntErrorCode(4004_1_506, "更新文件失败");
    ErrorCode DOWNLOAD_FILE_ERROR =  new IntErrorCode(4004_1_507, "下载文件失败");
    ErrorCode DELETE_FILE_FAIL = new IntErrorCode(4004_1_508, "删除文件失败");
}
