package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.entity.dto.tag.MemberListQueryDto;
import cn.xk.xcode.entity.dto.tag.MemberTagAddDto;
import cn.xk.xcode.entity.dto.tag.MemberTagBaseDto;
import cn.xk.xcode.entity.dto.tag.MemberTagUpdateDto;
import cn.xk.xcode.entity.vo.tag.MemberTagVo;
import cn.xk.xcode.entity.vo.tag.MemberUserTagsVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MemberTagService;
import cn.xk.xcode.utils.collections.CollectionUtil;
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
 * @Date 2024/8/4 20:23
 * @Version 1.0
 * @Description TagController
 */
@RestController
@RequestMapping("/tag")
@Tag(name = "会员用户标签接口")
public class MemberTagController
{
    @Resource
    private MemberTagService memberTagService;

    @SaCheckPermission("addTag")
    @PostMapping("/addTag")
    @Operation(summary = "添加用户标签")
    public CommonResult<Boolean> addTag(@Validated @RequestBody MemberTagAddDto memberTagAddDto) {
        return CommonResult.success(memberTagService.addTag(memberTagAddDto));
    }

    @SaCheckPermission("deleteTag")
    @PostMapping("/deleteTag")
    @Operation(summary = "删除用户标签")
    public CommonResult<Boolean> deleteTag(@Validated @RequestBody MemberTagBaseDto memberTagBaseDto) {
        return CommonResult.success(memberTagService.deleteTag(memberTagBaseDto));
    }

    @SaCheckPermission("updateTage")
    @PostMapping("/updateTage")
    @Operation(summary = "更新用户标签")
    public CommonResult<Boolean> updateTage(@Validated @RequestBody MemberTagUpdateDto memberTagUpdateDto) {
        return CommonResult.success(memberTagService.updateTage(memberTagUpdateDto));
    }

    @SaCheckPermission("getUserTagList")
    @PostMapping("/getUserTagList")
    @Operation(summary = "获取用户标签列表")
    public CommonResult<MemberUserTagsVo> getUserTagList(@Validated @RequestBody MemberListQueryDto memberListQueryDto) {
        return CommonResult.success(memberTagService.getUserTagList(memberListQueryDto));
    }

    @SaCheckPermission("getTag")
    @PostMapping("/getTag")
    @Operation(summary = "获取用户标签列表")
    public CommonResult<MemberTagVo> getTag(@Validated @RequestBody MemberTagBaseDto memberTagBaseDto) {
        return CommonResult.success(BeanUtil.toBean(memberTagService.getById(memberTagBaseDto.getId()), MemberTagVo.class));
    }

    @SaCheckPermission("getTagList")
    @PostMapping("/getTagList")
    @Operation(summary = "获取所有标签列表")
    public CommonResult<List<MemberTagVo>> getTagList() {
        return CommonResult.success(CollectionUtil.convertList(memberTagService.list(), bean -> BeanUtil.toBean(bean, MemberTagVo.class)));
    }
}
