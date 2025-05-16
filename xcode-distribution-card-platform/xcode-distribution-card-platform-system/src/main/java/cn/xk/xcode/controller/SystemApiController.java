package cn.xk.xcode.controller;

import cn.xk.xcode.annotation.SaSystemCheckRole;
import cn.xk.xcode.entity.dto.api.AddSystemApiDto;
import cn.xk.xcode.entity.dto.api.BindRoleApiDto;
import cn.xk.xcode.entity.dto.api.QuerySystemApiDto;
import cn.xk.xcode.entity.dto.api.UpdateSystemApiDto;
import cn.xk.xcode.entity.vo.api.SystemApiVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.SystemApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2025/5/12 9:18
 * @Version 1.0.0
 * @Description SystemApiController
 **/
@RestController
@RequestMapping("/manage/systemApi")
@Tag(name = "SystemApiController", description = "Api接口")
@Validated
public class SystemApiController {

    @Resource
    private SystemApiService systemApiService;

    @PostMapping("/queryAll")
    @Operation(summary = "查询所有Api")
    @SaSystemCheckRole("root")
    public CommonResult<Map<String, List<SystemApiVo>>> queryAllApi(@RequestBody QuerySystemApiDto querySystemApiDto) {
        return CommonResult.success(systemApiService.selectAllApi(querySystemApiDto));
    }

    @PostMapping("/add")
    @Operation(summary = "添加Api")
    @SaSystemCheckRole("root")
    public CommonResult<Boolean> addSystemApi(@RequestBody AddSystemApiDto addSystemApiDto) {
        return CommonResult.success(systemApiService.addSystemApi(addSystemApiDto));
    }

    @DeleteMapping("/del/{id}")
    @Operation(summary = "删除Api")
    @SaSystemCheckRole("root")
    public CommonResult<Boolean> delSystemApi(@PathVariable("id") Integer id) {
        return CommonResult.success(systemApiService.delSystemApi(id));
    }

    @PostMapping("/update")
    @Operation(summary = "更新Api")
    @SaSystemCheckRole("root")
    public CommonResult<Boolean> updateSystemApi(@RequestBody UpdateSystemApiDto updateSystemApiDto) {
        return CommonResult.success(systemApiService.updateSystemApi(updateSystemApiDto));
    }

    @PostMapping("/bindRoleApi")
    @Operation(summary = "绑定角色Api")
    @SaSystemCheckRole("root")
    public CommonResult<Boolean> bindRoleApi(@RequestBody BindRoleApiDto bindRoleApiDto) {
        return CommonResult.success(systemApiService.bindRoleApi(bindRoleApiDto));
    }

}
