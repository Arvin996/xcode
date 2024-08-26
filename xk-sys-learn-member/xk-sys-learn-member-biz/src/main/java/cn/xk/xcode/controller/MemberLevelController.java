package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.client.MemberLevelClient;
import cn.xk.xcode.entity.dto.MemberBaseReqDto;
import cn.xk.xcode.entity.dto.level.MemberLevelAddDto;
import cn.xk.xcode.entity.dto.level.MemberLevelBaseDto;
import cn.xk.xcode.entity.dto.level.MemberLevelUpdateDto;
import cn.xk.xcode.entity.vo.MemberLevelResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MemberLevelService;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Administrator
 * @Date 2024/8/22 15:35
 * @Version V1.0.0
 * @Description MemberLevelController
 */
@RestController
public class MemberLevelController implements MemberLevelClient {

    @Resource
    private MemberLevelService memberLevelService;

    @Override
    public CommonResult<MemberLevelResultVo> getMemberUserLevel(MemberBaseReqDto memberBaseReqDto) {
        return CommonResult.success(memberLevelService.getMemberUserLevel(memberBaseReqDto));
    }

    @SaCheckPermission("member:level:add")
    @Operation(summary = "添加会员等级")
    @PostMapping("/addMemberLevel")
    public CommonResult<Boolean> addMemberLevel(@Validated @RequestBody MemberLevelAddDto memberLevelAddDto){
        return CommonResult.success(memberLevelService.addMemberLevel(memberLevelAddDto));
    }

    @SaCheckPermission("member:level:delete")
    @Operation(summary = "删除会员等级")
    @PostMapping("/deleteMemberLevel")
    public CommonResult<Boolean> deleteMemberLevel(@Validated @RequestBody MemberLevelBaseDto memberLevelBaseDto){
        return CommonResult.success(memberLevelService.deleteMemberLevel(memberLevelBaseDto));
    }

    @SaCheckPermission("member:level:update")
    @Operation(summary = "修改会员等级")
    @PostMapping("/updateMemberLevel")
    public CommonResult<Boolean> updateMemberLevel(@Validated @RequestBody MemberLevelUpdateDto memberLevelUpdateDto){
        return CommonResult.success(memberLevelService.updateMemberLevel(memberLevelUpdateDto));
    }

    @SaCheckPermission("member:level:list")
    @Operation(summary = "查询会员等级列表")
    @PostMapping("/getMemberLevelList")
    public CommonResult<List<MemberLevelResultVo>> getMemberLevelList(){
        return CommonResult.success(CollectionUtil.convertList(memberLevelService.list(), bean -> BeanUtil.toBean(bean, MemberLevelResultVo.class)));
    }
}
