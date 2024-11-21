package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.entity.dto.leave.AddLeaveDto;
import cn.xk.xcode.entity.dto.leave.QueryLeaveDto;
import cn.xk.xcode.entity.dto.leave.SubmitLeaveDto;
import cn.xk.xcode.entity.dto.leave.UpdateLeaveDto;
import cn.xk.xcode.entity.vo.leave.FlowOaLeaveResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.FlowOaLeaveService;
import cn.xk.xcode.utils.PageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author xuk
 * @Date 2024/11/21 11:40
 * @Version 1.0.0
 * @Description FlowOaLeaveController
 **/
@RestController
@RequestMapping("/flow/leave")
@Tag(name = "oa请假接口")
public class FlowOaLeaveController {

    @Resource
    private FlowOaLeaveService flowOaLeaveService;

    @PostMapping("/list")
    @SaCheckPermission("oa:leave:list")
    @Operation(summary = "分页查询请假列表")
    public CommonResult<PageResult<FlowOaLeaveResultVo>> getFlowOaLeaveList(@RequestBody QueryLeaveDto queryLeaveDto) {
        return CommonResult.success(PageUtil.startPage(queryLeaveDto, flowOaLeaveService.getFlowOaLeaveList(queryLeaveDto)));
    }

    @PostMapping("/export")
    @SaCheckPermission("oa:leave:export")
    @Operation(summary = "导出请假列表")
    public void exportFlowOaLeaveList(HttpServletResponse response, @RequestBody QueryLeaveDto queryLeaveDto) {
        flowOaLeaveService.exportFlowOaLeaveList(response, queryLeaveDto);
    }

    @GetMapping("/getLeaveDetail/{id}")
    @Operation(summary = "根据id获取请假详情")
    public CommonResult<FlowOaLeaveResultVo> getLeaveDetail(@PathVariable("id") Long id) {
        return CommonResult.success(flowOaLeaveService.getLeaveDetail(id));
    }

    @PostMapping("/addLeave")
    @Operation(summary = "添加请假")
    @SaCheckPermission("oa:leave:add")
    public CommonResult<Boolean> addLeave(@RequestBody AddLeaveDto addLeaveDto, String flowStatus) {
        return CommonResult.success(flowOaLeaveService.addLeave(addLeaveDto, flowStatus));
    }

    @PostMapping("/updateLeave")
    @Operation(summary = "更新请假")
    @SaCheckPermission("oa:leave:update")
    public CommonResult<Boolean> updateLeave(@RequestBody UpdateLeaveDto updateLeaveDto) {
        return CommonResult.success(flowOaLeaveService.updateLeave(updateLeaveDto));
    }

    @DeleteMapping("/deleteLeave/{ids}")
    @Operation(summary = "删除请假")
    @SaCheckPermission("oa:leave:delete")
    public CommonResult<Boolean> deleteLeave(@PathVariable String[] ids) {
        return CommonResult.success(flowOaLeaveService.deleteLeave(ids));
    }

    @PostMapping("/submit")
    @Operation(summary = "提交请假审批")
    @SaCheckPermission("oa:leave:submit")
    public CommonResult<Boolean> submitLeave(@Validated @RequestBody SubmitLeaveDto submitLeaveDto) {
        return CommonResult.success(flowOaLeaveService.submitLeave(submitLeaveDto));
    }

    @PostMapping("/handler")
    @Operation(summary = "处理请假审批")
    @Parameters({
            @Parameter(name = "taskId", description = "任务id"),
            @Parameter(name = "skipType", description = "跳过类型 ACCEPT REJECT"),
            @Parameter(name = "message", description = "审批意见"),
            @Parameter(name = "nodeCode", description = "节点编码"),
            @Parameter(name = "flowStatus", description = "流程状态")
    })
    @SaCheckPermission("oa:leave:handler")
    public CommonResult<Boolean> handlerLeave(@RequestBody UpdateLeaveDto updateLeaveDto,
                                              Long taskId,
                                              String skipType,
                                              String message,
                                              String nodeCode,
                                              String flowStatus) {
        return CommonResult.success(flowOaLeaveService.handlerLeave(updateLeaveDto, taskId, skipType, message, nodeCode, flowStatus));
    }

    @PostMapping("/termination")
    @Operation(summary = "终止请假流程")
    @SaCheckPermission("oa:leave:termination")
    public CommonResult<Boolean> terminationLeave(@RequestBody UpdateLeaveDto updateLeaveDto) {
        return CommonResult.success(flowOaLeaveService.terminationLeave(updateLeaveDto));
    }

}
