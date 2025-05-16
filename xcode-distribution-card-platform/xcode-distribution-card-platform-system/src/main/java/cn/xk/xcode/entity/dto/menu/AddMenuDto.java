package cn.xk.xcode.entity.dto.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/5/12 13:46
 * @Version 1.0.0
 * @Description AddMenuDto
 **/
@Data
@Schema(name = "AddMenuDto", description = "Menu添加实体类")
public class AddMenuDto {

    /**
     * 菜单名称
     */
    @Schema(description = "name 菜单名称")
    @NotBlank(message = "菜单name不能为空")
    private String name;

    /**
     * 显示顺序
     */
    @Schema(description = "sort 显示顺序")
    private Integer sort;

    /**
     * 父菜单ID
     */
    @Schema(description = "parentId 父菜单ID")
    @NotNull(message = "父菜单ID不能为空")
    private Long parentId;

    /**
     * 路由地址
     */
    @Schema(description = "path 路由地址")
    @NotBlank(message = "路由地址不能为空")
    private String path;

    /**
     * 菜单图标
     */
    @Schema(description = "icon 菜单图标")
    private String icon;

    /**
     * 组件路径
     */
    @Schema(description = "component 组件路径")
    private String component;

    /**
     * 组件名
     */
    @Schema(description = "componentName 组件名")
    private String componentName;

    /**
     * 是否可见 0可见 1不可见
     */
    @Schema(description = "visible 是否可见 0可见 1不可见")
    private String visible;
}
