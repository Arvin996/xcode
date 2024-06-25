package cn.xk.xcode.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.xk.xcode.entity.dto.AddDatabaseConnInfoPoDto;
import cn.xk.xcode.entity.dto.UpdateDatabaseConnInfoPoDto;
import cn.xk.xcode.entity.po.DatabaseConnInfoPo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.DatabaseConnInfoService;
import cn.xk.xcode.utils.object.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/6/25 09:39
 * @Version 1.0
 * @Description DataBaseInfoController
 */
@RestController
@Tag(name = "数据库信息接口")
@RequestMapping("/infra/dbinfo")
public class DataBaseInfoController {

    @Resource
    private DatabaseConnInfoService databaseConnInfoService;

    @Operation(description = "查询所有的数据库信息")
    @PostMapping("/queryAll")
    public CommonResult<List<DatabaseConnInfoPo>> queryAll() {
        return CommonResult.success(databaseConnInfoService.list());
    }

    @Operation(description = "新增数据库配置信息")
    @PostMapping("/add")
    public CommonResult<Boolean> addDatabaseInfo(@Validated @RequestBody AddDatabaseConnInfoPoDto addDatabaseConnInfoPoDto) {
        DatabaseConnInfoPo databaseConnInfoPo = BeanUtil.toBean(addDatabaseConnInfoPoDto, DatabaseConnInfoPo.class);
        return CommonResult.success(databaseConnInfoService.save(databaseConnInfoPo));
    }

    @Operation(description = "删除数据库配置信息")
    @PostMapping("/delete")
    public CommonResult<Boolean> deleteDatabaseInfo(@Validated @RequestBody UpdateDatabaseConnInfoPoDto updateDatabaseConnInfoPoDto) {
        // todo 删除表信息还没做
        return CommonResult.success(databaseConnInfoService.removeById(updateDatabaseConnInfoPoDto.getId()));
    }

    @Operation(description = "修改数据库配置信息")
    @PostMapping("/update")
    public CommonResult<Boolean> updateDatabaseInfo(@Validated @RequestBody UpdateDatabaseConnInfoPoDto updateDatabaseConnInfoPoDto) {
        DatabaseConnInfoPo databaseConnInfoPo = BeanUtil.toBean(updateDatabaseConnInfoPoDto, DatabaseConnInfoPo.class);
        databaseConnInfoPo.setCreateUser(StpUtil.getLoginIdAsString());
        return CommonResult.success(databaseConnInfoService.updateById(databaseConnInfoPo));
    }
}
