package cn.xk.xcode.controller;

import cn.xk.xcode.annotation.DictTrans;
import cn.xk.xcode.annotation.SaSystemCheckRole;
import cn.xk.xcode.entity.dto.notice.PublishSystemStationNoticeDto;
import cn.xk.xcode.entity.dto.notice.QuerySystemStationNoticeDto;
import cn.xk.xcode.entity.vo.notice.SystemStationNoticeVo;
import cn.xk.xcode.handler.WebSocketMessageHandler;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.SystemStationNoticeService;
import cn.xk.xcode.service.SystemUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/5/13 13:28
 * @Version 1.0.0
 * @Description SystemStationNoticeController
 **/
@RestController
@RequestMapping("/manage/notice")
@Validated
@Tag(name = "SystemStationNoticeController", description = "SystemStationNoticeController 站内信 系统通知接口")
@SaSystemCheckRole("root")
public class SystemStationNoticeController {

    @Resource
    private SystemStationNoticeService systemStationNoticeService;

    @PostMapping("/publishSystemNotice")
    @Operation(summary = "发布系统通知")
    public CommonResult<Boolean> publishSystemNotice(@RequestBody PublishSystemStationNoticeDto publishSystemStationNoticeDto) {
        return CommonResult.success(systemStationNoticeService.publishSystemNotice(publishSystemStationNoticeDto));
    }

    @PostMapping("/queryAllNotice")
    @Operation(summary = "查询所有通知")
    @DictTrans
    public CommonResult<PageResult<SystemStationNoticeVo>> queryAllNotice(@RequestBody QuerySystemStationNoticeDto querySystemStationNoticeDto) {
        return CommonResult.success(systemStationNoticeService.queryAllNotice(querySystemStationNoticeDto));
    }
}

