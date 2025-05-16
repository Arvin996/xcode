package cn.xk.xcode.entity.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/5/12 8:51
 * @Version 1.0.0
 * @Description SystemMenuVo
 **/
@Data
@Schema(name = "SystemMenuVo", description = "系统菜单vo")
public class SystemMenuVo {

    /**
     * 菜单ID
     */
    @Schema(description = "菜单id")
    private Long id;

    /**
     * 菜单名称
     */
    @Schema(description = "菜单名")
    private String name;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sort;

    /**
     * 父菜单ID
     */
    @Schema(description = "父菜单id 如没有父菜单 则默认为0")
    private Long parentId;

    /**
     * 路由地址
     */
    @Schema(description = "路由地址")
    private String path;

    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String icon;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    private String component;

    /**
     * 组件名
     */
    @Schema(description = "组件名")
    private String componentName;

    /**
     * 是否可见 0可见 1不可见
     */
    @Schema(description = "是否可见 0可见 1不可见")
    private String visible;

    private List<SystemMenuVo> children;
}
