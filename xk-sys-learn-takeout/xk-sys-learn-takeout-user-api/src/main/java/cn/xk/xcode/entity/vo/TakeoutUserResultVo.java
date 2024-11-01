package cn.xk.xcode.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/10/29 15:29
 * @Version 1.0.0
 * @Description TakeoutUserResultVo
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户信息返回")
public class TakeoutUserResultVo {

    /**
     * 主键
     */
    @Schema(description = "用户id")
    private Long id;

    /**
     * 姓名
     */
    @Schema(description = "用户名")
    private String name;

    /**
     * 角色id
     */
    @Schema(description = "角色id")
    private Integer roleId;


    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String mobile;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private String sex;

    /**
     * 身份证号
     */
    @Schema(description = "身份证号")
    private String idNumber;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 状态 0:正常，1:禁用
     */
    @Schema(description = "状态 ")
    private Integer status;
}
