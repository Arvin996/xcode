package cn.xk.xcode.controller;

import cn.xk.xcode.annotation.SaMemberCheckRole;
import cn.xk.xcode.entity.dto.menu.AddMenuDto;
import cn.xk.xcode.entity.dto.menu.QueryMenuDto;
import cn.xk.xcode.entity.dto.menu.UpdateMenuDto;
import cn.xk.xcode.entity.vo.menu.SystemMenuVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.SystemMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/5/12 11:40
 * @Version 1.0.0
 * @Description MenuController
 **/
@RestController
@RequestMapping("/manage/menu")
@Tag(name = "MenuController", description = "系统菜单接口")
@Validated
@SaMemberCheckRole("root")
public class MenuController {

    @Resource
    private SystemMenuService systemMenuService;

    @PostMapping("/queryAllMenu")
    @Operation(summary = "查询所有菜单")
    public CommonResult<List<SystemMenuVo>> queryAllMenu(@RequestBody QueryMenuDto queryMenuDto) {
        return CommonResult.success(systemMenuService.queryAllMenu(queryMenuDto));
    }

    @PostMapping("/addSystemMenu")
    @Operation(summary = "添加系统菜单")
    public CommonResult<Boolean> addSystemMenu(@RequestBody AddMenuDto addMenuDto) {
        return CommonResult.success(systemMenuService.addSystemMenu(addMenuDto));
    }

    @DeleteMapping("/delSystemMenu/{id}")
    @Operation(summary = "删除系统菜单")
    public CommonResult<Boolean> delSystemMenu(@PathVariable("id") Long id) {
        return CommonResult.success(systemMenuService.delSystemMenu(id));
    }

    @PostMapping("/updateSystemMenu")
    @Operation(summary = "更新系统菜单")
    public CommonResult<Boolean> updateSystemMenu(@RequestBody UpdateMenuDto updateMenuDto) {
        return CommonResult.success(systemMenuService.updateSystemMenu(updateMenuDto));
    }

    @PutMapping("/moveUpSystemMenu/{id}")
    @Operation(summary = "上移系统菜单")
    public CommonResult<Boolean> moveUpSystemMenu(@PathVariable("id") Long id) {
        return CommonResult.success(systemMenuService.moveUpSystemMenu(id));
    }

    @PutMapping("/moveDownSystemMenu/{id}")
    @Operation(summary = "下移系统菜单")
    public CommonResult<Boolean> moveDownSystemMenu(@PathVariable("id") Long id) {
        return CommonResult.success(systemMenuService.moveDownSystemMenu(id));
    }
}
