package cn.xk.xcode.entity.vo.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2025/5/9 10:35
 * @Version 1.0.0
 * @Description SysDictDataResultVo
 **/
@Data
@Schema(name = "SysDictDataResultVo 字典Vo")
public class SysDictDataResultVo {

    /**
     * 字典编码
     */
    @Schema(description = "id 字典编码", example = "1")
    private Long id;

    /**
     * 字典排序
     */
    @Schema(description = "sort 字典排序", example = "1")
    private Integer sort;

    /**
     * 字典标签
     */
    @Schema(description = "label 字典标签", example = "男")
    private String label;

    /**
     * 字典键值
     */
    @Schema(description = "value 字典键值", example = "1")
    private String value;

    /**
     * 字典类型
     */
    @Schema(description = "dictType 字典类型", example = "sex")
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    @Schema(description = "status 状态（0正常 1停用）", example = "0")
    private String status;

    /**
     * 颜色类型
     */
    @Schema(description = "color 颜色类型", example = "#000000")
    private String color;

    /**
     * 备注
     */
    @Schema(description = "remark 备注", example = "性别")
    private String remark;

    @Schema(description = "创建时间", example = "2025-05-09 10:35:00")
    private LocalDateTime createTime;

    @Schema(description = "更新时间", example = "2025-05-09 10:35:00")
    private LocalDateTime updateTime;

    @Schema(description = "创建人", example = "xuk")
    private String createUser;

    @Schema(description = "更新人", example = "xuk")
    private String updateUser;
}
