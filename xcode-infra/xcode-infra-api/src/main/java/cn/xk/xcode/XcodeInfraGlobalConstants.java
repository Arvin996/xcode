package cn.xk.xcode;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author xuk
 * @Date 2024/11/25 17:15
 * @Version 1.0.0
 * @Description XcodeInfraGlobalConstants
 **/
public interface XcodeInfraGlobalConstants {

    ErrorCode SQL_PARAMS_NOT_VALID = new IntErrorCode(204_0_500, "参数不符合规范，不能进行查询");
    ErrorCode SQL_PARAM_TOO_LONG = new IntErrorCode(204_0_501, "参数已超过最大限制，不能进行查询");
    ErrorCode SQL_INJECT_DANGER = new IntErrorCode(204_0_502, "参数存在SQL注入危险");
    ErrorCode CREATE_TABLE_SCHEMA_ERROR = new IntErrorCode(204_0_503, "创建表结构失败");
    ErrorCode IMPORT_TABLE_ERROR = new IntErrorCode(204_0_504, "导入表失败");
    ErrorCode TREE_ENCODE_IS_EMPTY = new IntErrorCode(204_0_505, "树编码字段不能为空");
    ErrorCode TREE_PARENT_ID_IS_EMPTY = new IntErrorCode(204_0_506, "树父编码字段不能为空");
    ErrorCode TREE_NAME_IS_NULL = new IntErrorCode(204_0_507, "树名称字段不能为空");
    ErrorCode SUB_TABLE_NAME_IS_EMPTY = new IntErrorCode(204_0_508, "关联子表的表名不能为空");
    ErrorCode SUB_TABLE_FK_IS_NULL = new IntErrorCode(204_0_509, "关联子表的外键不能为空");
    ErrorCode RENDER_TABLE_ERROR = new IntErrorCode(204_0_510, "渲染表失败");
    ErrorCode TABLE_SCHEMA_NOT_EXIST = new IntErrorCode(204_0_511, "表结构不存在");
}
