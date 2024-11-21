package cn.xk.xcode.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author xuk
 * @Date 2024/11/18 15:44
 * @Version 1.0.0
 * @Description SystemDictResultVo
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(defaultValue = "字典返回Vo")
public class SystemDictResultVo {

    /**
     * 字典id
     */
    @Schema(description = "字典id")
    private String code;

    /**
     * 字典值
     */
    @Schema(description = "字典值")
    private String name;

    /**
     * 父字典id,一级为##
     */
    @Schema(description = "父字典id,一级为##")
    private String parId;

    /**
     * 字典表备注，如排序
     */
    @Schema(description = "字典表备注，如排序")
    private String note;

    /**
     * 填充字段
     */
    @Schema(description = "填充字段")
    private String pad;
}
