package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.client.SystemRoleClient;
import cn.xk.xcode.client.SystemUserClient;
import cn.xk.xcode.entity.SystemRoleResultVo;
import cn.xk.xcode.entity.SystemUserResultVo;
import cn.xk.xcode.entity.dto.task.QueryTaskDto;
import cn.xk.xcode.entity.vo.task.FlowTaskVo;
import cn.xk.xcode.entity.vo.task.FlowTaskInteractiveTypeVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.LoginStpType;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.FlowExecuteTaskService;
import cn.xk.xcode.utils.PageUtil;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import cn.xk.xcode.utils.collections.CollectionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.dromara.warm.flow.core.FlowFactory;
import org.dromara.warm.flow.core.entity.*;
import org.dromara.warm.flow.core.enums.UserType;
import org.dromara.warm.flow.core.service.*;
import org.dromara.warm.flow.orm.entity.FlowHisTask;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.UNAUTHORIZED;

/**
 * @Author xuk
 * @Date 2024/11/20 11:01
 * @Version 1.0.0
 * @Description FlowExecuteController
 **/
@RestController
@RequestMapping("/flow/execute")
@Tag(name = "流程任务接口")
public class FlowExecuteController {

    @Resource
    private FlowExecuteTaskService flowExecuteTaskService;

    @Resource
    private UserService flowUserservice;

    @Resource
    private SystemUserClient systemUserClient;

    @Resource
    private SystemRoleClient systemRoleClient;

    @Resource
    private HisTaskService hisTaskService;

    @Resource
    private TaskService taskService;

    @Resource
    private NodeService nodeService;

    @Resource
    private InsService insService;

    @PostMapping("/todoTask")
    @Operation(summary = "获取待办任务列表")
    @SaCheckPermission("flow:execute:todoTask")
    public CommonResult<PageResult<FlowTaskVo>> getTodoTask(@Validated @RequestBody QueryTaskDto flowTask) {
        LoginUser loginUser = SaTokenLoginUtils.getLoginUser(LoginStpType.SYSTEM);
        if (Objects.isNull(loginUser)) {
            ExceptionUtil.castServiceException(UNAUTHORIZED);
        }
        Set<String> permissions = loginUser.getPermissions();
        flowTask.setPermissionList(CollectionUtil.convertList(permissions, Function.identity()));
        List<FlowTaskVo> list = flowExecuteTaskService.getTodoTask(flowTask);
        List<Long> taskIds = CollectionUtil.convertList(list, FlowTaskVo::getId);
        List<User> userList = flowUserservice.getByAssociateds(taskIds);
        Map<Long, List<User>> map = CollectionUtil.groupByKey(userList, User::getAssociated);
        for (FlowTaskVo taskVo : list) {
            if (Objects.nonNull(taskVo)) {
                List<User> users = map.get(taskVo.getId());
                if (CollectionUtil.isNotEmpty(users)) {
                    for (User user : users) {
                        if (UserType.APPROVAL.getKey().equals(user.getType())) {
                            if (StringUtils.isEmpty(taskVo.getApprover())) {
                                taskVo.setApprover("");
                            }
                            String name = getName(user.getProcessedBy());
                            if (StringUtils.isNotEmpty(name)) {
                                taskVo.setApprover(taskVo.getApprover().concat(name).concat(";"));
                            }
                        } else if (UserType.TRANSFER.getKey().equals(user.getType())) {
                            if (StringUtils.isEmpty(taskVo.getTransferredBy())) {
                                taskVo.setTransferredBy("");
                            }
                            String name = getName(user.getProcessedBy());
                            if (StringUtils.isNotEmpty(name)) {
                                taskVo.setTransferredBy(taskVo.getTransferredBy().concat(name).concat(";"));
                            }
                        } else if (UserType.DEPUTE.getKey().equals(user.getType())) {
                            if (StringUtils.isEmpty(taskVo.getDelegate())) {
                                taskVo.setDelegate("");
                            }
                            String name = getName(user.getProcessedBy());
                            if (StringUtils.isNotEmpty(name))
                                taskVo.setDelegate(taskVo.getDelegate().concat(name).concat(";"));
                        }
                    }
                }
            }
        }
        return CommonResult.success(PageUtil.startPage(flowTask.getPageParam(), list));
    }

    private String getName(String id) {
        if (StringUtils.isNotEmpty(id)) {
            if (id.contains("user:")) {
                SystemUserResultVo systemUserResultVo = systemUserClient.getUser(Long.valueOf(id.replace("user:", ""))).getData();
                if (Objects.nonNull(systemUserResultVo)) {
                    return "用户:" + systemUserResultVo.getUsername();
                }
            }
        } else if (id.contains("role")) {
            SystemRoleResultVo systemRoleResultVo = systemRoleClient.getRole(Integer.valueOf(id.replace("role:", ""))).getData();
            if (Objects.nonNull(systemRoleResultVo)) {
                return "角色:" + systemRoleResultVo.getName();
            }
        } else {
            try {
                long parseLong = Long.parseLong(id);
                SystemUserResultVo systemUserResultVo = systemUserClient.getUser(parseLong).getData();
                if (Objects.nonNull(systemUserResultVo)) {
                    return "用户:" + systemUserResultVo.getUsername();
                }
            } catch (NumberFormatException e) {
                return id;
            }

        }
        return "";
    }

    @PostMapping("/copyTask")
    @Operation(summary = "获取分页抄送任务列表")
    @SaCheckPermission("flow:execute:copyTask")
    public CommonResult<PageResult<FlowHisTask>> getCopyTask(@Validated @RequestBody QueryTaskDto flowTask) {
        LoginUser loginUser = SaTokenLoginUtils.getLoginUser(LoginStpType.SYSTEM);
        if (Objects.isNull(loginUser)) {
            ExceptionUtil.castServiceException(UNAUTHORIZED);
        }
        Set<String> permissions = loginUser.getPermissions();
        flowTask.setPermissionList(CollectionUtil.convertList(permissions, Function.identity()));
        List<FlowHisTask> list = flowExecuteTaskService.copyPage(flowTask);
        return CommonResult.success(PageUtil.startPage(flowTask.getPageParam(), list));
    }

    @PostMapping("/doneTask")
    @Operation(summary = "获取已办任务列表")
    @SaCheckPermission("flow:execute:doneTask")
    public CommonResult<PageResult<FlowHisTask>> getDoneTask(@RequestBody FlowHisTask flowTask, @RequestParam("pageNo") Long pageNo, @RequestParam("pageSize") Long pageSize) {
        LoginUser loginUser = SaTokenLoginUtils.getLoginUser(LoginStpType.SYSTEM);
        if (Objects.isNull(loginUser)) {
            ExceptionUtil.castServiceException(UNAUTHORIZED);
        }
        Set<String> permissions = loginUser.getPermissions();
        flowTask.setPermissionList(CollectionUtil.convertList(permissions, Function.identity()));
        List<FlowHisTask> list = flowExecuteTaskService.donePage(flowTask);
        if (CollectionUtil.isNotEmpty(list)) {
            for (FlowHisTask hisTaskVo : list) {
                if (StringUtils.isNotEmpty(hisTaskVo.getApprover())) {
                    String name = getName(hisTaskVo.getApprover());
                    hisTaskVo.setApprover(name);
                }
                if (StringUtils.isNotEmpty(hisTaskVo.getCollaborator())) {
                    hisTaskVo.setCollaborator(systemUserClient.getUser(Long.valueOf(hisTaskVo.getCollaborator())).getData().getUsername());
                }
            }
        }
        return CommonResult.success(PageUtil.startPage(pageNo, pageSize, list));
    }

    @GetMapping("/doneList/{instanceId}")
    @Operation(summary = "查询已办任务历史记录")
    @SaCheckPermission("flow:execute:doneList")
    public CommonResult<List<FlowHisTask>> getDoneList(@PathVariable("instanceId") Long instanceId) {
        List<HisTask> flowHisTasks = hisTaskService.orderById().desc().list(new FlowHisTask().setInstanceId(instanceId));
        List<FlowHisTask> flowHisTaskList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(flowHisTasks)) {
            for (HisTask hisTask : flowHisTasks) {
                if (StringUtils.isNotEmpty(hisTask.getApprover())) {
                    String name = getName(hisTask.getApprover());
                    hisTask.setApprover(name);
                }
                if (StringUtils.isNotEmpty(hisTask.getCollaborator())) {
                    hisTask.setCollaborator(systemUserClient.getUser(Long.valueOf(hisTask.getCollaborator())).getData().getUsername());
                }
                FlowHisTask flowHisTask = new FlowHisTask();
                BeanUtils.copyProperties(hisTask, flowHisTask);
                flowHisTaskList.add(flowHisTask);
            }
        }
        return CommonResult.success(flowHisTaskList);
    }

    @GetMapping("/getTaskById/{taskId}")
    @Operation(summary = "根据taskId查询代表任务")
    public CommonResult<Task> getTaskById(@PathVariable("taskId") Long taskId) {
        return CommonResult.success(taskService.getById(taskId));
    }

    @GetMapping("/anyNodeList/{instanceId}")
    @Operation(summary = "查询跳转任意节点列表")
    public CommonResult<List<Node>> anyNodeList(@PathVariable("instanceId") Long instanceId) {
        Instance instance = insService.getById(instanceId);
        List<Node> nodeList = nodeService.list(FlowFactory.newNode().setDefinitionId(instance.getDefinitionId()));
        return CommonResult.success(nodeList);
    }

    @PostMapping("/interactiveType")
    @Operation(summary = "处理非办理的流程交互类型 例如转办委派")
    public CommonResult<Boolean> interactiveType(@Validated @RequestBody FlowTaskInteractiveTypeVo flowTaskInteractiveTypeVo) {
        return CommonResult.success(flowExecuteTaskService.interactiveType(flowTaskInteractiveTypeVo));
    }

    @PostMapping("/interactiveTypeSysUser")
    @Operation(summary = "交互类型可以选择的用户")
    public CommonResult<PageResult<SystemUserResultVo>> getInteractiveTypeSysUser(@Validated @RequestBody FlowTaskInteractiveTypeVo flowTaskInteractiveTypeVo){
        return CommonResult.success(flowExecuteTaskService.getInteractiveTypeSysUser(flowTaskInteractiveTypeVo));
    }

    @GetMapping("/active/{instanceId}")
    @Operation(summary = "激活流程任务实例")
    public CommonResult<Boolean> active(@PathVariable("instanceId") Long instanceId) {
        return CommonResult.success(insService.active(instanceId));
    }

    @GetMapping("/unActive/{instanceId}")
    @Operation(summary = "挂起流程任务实例")
    public CommonResult<Boolean> unActive(@PathVariable("instanceId") Long instanceId) {
        return CommonResult.success(insService.unActive(instanceId));
    }
}