package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.service.MemberTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/8/4 20:23
 * @Version 1.0
 * @Description TagController
 */
@RestController
@RequestMapping("/tag")
@Tag(name = "会员用户标签接口")
public class TagController
{
    @Resource
    private MemberTagService memberTagService;

    @SaCheckPermission("addTag")
    @PostMapping("/addTag")
    @Operation(summary = "添加用户标签")
    public void addTag()
    {
        memberTagService.addTag();
    }
}
