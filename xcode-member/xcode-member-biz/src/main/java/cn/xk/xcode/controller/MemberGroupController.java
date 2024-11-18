package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.entity.dto.group.MemberGroupAddDto;
import cn.xk.xcode.entity.dto.group.MemberGroupBaseDto;
import cn.xk.xcode.entity.dto.group.MemberGroupQueryDto;
import cn.xk.xcode.entity.dto.group.MemberGroupUpdateDto;
import cn.xk.xcode.entity.vo.group.MemberGroupResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MemberGroupService;
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
 * @Author Administrator
 * @Date 2024/8/21 14:54
 * @Version V1.0.0
 * @Description MemberGroupController
 */
@RestController
@RequestMapping("/group")
@Tag(name = "会员用户组接口")
public class MemberGroupController {

    @Resource
    private MemberGroupService memberGroupService;

    @Operation(summary = "新增用户组")
    @SaCheckPermission("member:group:add")
    @PostMapping("/addGroup")
    public CommonResult<Boolean> addGroup(@Validated @RequestBody MemberGroupAddDto memberGroupAddDto) {
        return CommonResult.success(memberGroupService.addGroup(memberGroupAddDto));
    }

    @Operation(summary = "更新用户组")
    @SaCheckPermission("member:group:update")
    @PostMapping("/updateGroup")
    public CommonResult<Boolean> updateGroup(@Validated @RequestBody MemberGroupUpdateDto memberGroupUpdateDto) {
        return CommonResult.success(memberGroupService.updateGroup(memberGroupUpdateDto));
    }

    @Operation(summary = "删除用户组")
    @SaCheckPermission("member:group:delete")
    @PostMapping("/deleteGroup")
    public CommonResult<Boolean> deleteGroup(@Validated @RequestBody MemberGroupBaseDto memberGroupBaseDto) {
        return CommonResult.success(memberGroupService.deleteGroup(memberGroupBaseDto));
    }


    @Operation(summary = "查询用户组")
    @SaCheckPermission("member:group:query")
    @PostMapping("/queryGroup")
    public CommonResult<List<MemberGroupResultVo>> queryGroup(@Validated @RequestBody MemberGroupQueryDto memberGroupQueryDto) {
        return CommonResult.success(memberGroupService.queryGroup(memberGroupQueryDto));
    }


}
