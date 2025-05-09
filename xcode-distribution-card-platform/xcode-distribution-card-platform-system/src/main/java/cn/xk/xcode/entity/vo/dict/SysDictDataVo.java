package cn.xk.xcode.entity.vo.dict;

import cn.xk.xcode.entity.po.SystemDictDataPo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/5/9 9:25
 * @Version 1.0.0
 * @Description SysDictDataVo
 **/
@Data
@Schema(name = "SysDictDataVo 字典Vo")
public class SysDictDataVo {

    /**
     * 字典主键
     */
    @Schema(description = "id 字典主键", example = "1")
    private Long id;

    /**
     * 字典类型名称
     */
    @Schema(description = "name 字典类型名称", example = "sex")
    private String name;

    /**
     * 字典类型码
     */
    @Schema(description = "type 字典类型码", example = "sex")
    private String type;

    /**
     * 状态（0正常 1停用）
     */
    @Schema(description = "status 状态（0正常 1停用）", example = "0")
    private String status;

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

    private List<SysDictDataResultVo> dictDataList;
}
