package cn.xk.xcode.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.entity.dto.leave.AddLeaveDto;
import cn.xk.xcode.entity.dto.leave.QueryLeaveDto;
import cn.xk.xcode.entity.dto.leave.SubmitLeaveDto;
import cn.xk.xcode.entity.dto.leave.UpdateLeaveDto;
import cn.xk.xcode.entity.vo.leave.FlowOaLeaveResultVo;
import cn.xk.xcode.enums.LeaveTypeEnum;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.pojo.StpType;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.FlowOaLeavePo;
import cn.xk.xcode.mapper.FlowOaLeaveMapper;
import cn.xk.xcode.service.FlowOaLeaveService;
import org.apache.commons.lang3.StringUtils;
import org.dromara.warm.flow.core.FlowFactory;
import org.dromara.warm.flow.core.dto.FlowParams;
import org.dromara.warm.flow.core.entity.Instance;
import org.dromara.warm.flow.core.entity.User;
import org.dromara.warm.flow.core.enums.SkipType;
import org.dromara.warm.flow.core.service.InsService;
import org.dromara.warm.flow.core.service.TaskService;
import org.dromara.warm.flow.core.utils.IdUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static cn.xk.xcode.BpmGlobalConstants.INSTANCE_NOT_EXISTS;
import static cn.xk.xcode.BpmGlobalConstants.LEAVE_NOT_EXISTS;
import static cn.xk.xcode.entity.def.FlowOaLeaveTableDef.FLOW_OA_LEAVE_PO;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.UNAUTHORIZED;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2024-11-21
 */
@Service
public class FlowOaLeaveServiceImpl extends ServiceImpl<FlowOaLeaveMapper, FlowOaLeavePo> implements FlowOaLeaveService {

    @Resource
    private InsService insService;

    @Resource
    private TaskService taskService;

    @Override
    public List<FlowOaLeaveResultVo> getFlowOaLeaveList(QueryLeaveDto queryLeaveDto) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .where("1=1")
                .and(FLOW_OA_LEAVE_PO.DAY.eq(queryLeaveDto.getDay()).when(Objects.nonNull(queryLeaveDto.getDay())))
                .and(FLOW_OA_LEAVE_PO.TYPE.eq(queryLeaveDto.getType()).when(Objects.nonNull(queryLeaveDto.getType())))
                .and(FLOW_OA_LEAVE_PO.FLOW_STATUS.eq(queryLeaveDto.getFlowStatus()).when(Objects.nonNull(queryLeaveDto.getFlowStatus())))
                .and(FLOW_OA_LEAVE_PO.START_TIME.ge(queryLeaveDto.getStartTime()).when(Objects.nonNull(queryLeaveDto.getStartTime())))
                .and(FLOW_OA_LEAVE_PO.END_TIME.le(queryLeaveDto.getEndTime()).when(Objects.nonNull(queryLeaveDto.getEndTime())))
                .and(FLOW_OA_LEAVE_PO.CREATE_USER.eq(queryLeaveDto.getCreateUser()).when(Objects.nonNull(queryLeaveDto.getCreateUser())));
        List<FlowOaLeavePo> list = this.list(queryWrapper);
        return BeanUtil.toBean(list, FlowOaLeaveResultVo.class);
    }

    @Override
    public void exportFlowOaLeaveList(HttpServletResponse response, QueryLeaveDto queryLeaveDto) {

    }

    @Override
    public FlowOaLeaveResultVo getLeaveDetail(Long id) {
        FlowOaLeavePo flowOaLeavePo = ObjectUtil.returnIfNotNullCastServiceEx(this.getById(id), LEAVE_NOT_EXISTS);
        FlowOaLeaveResultVo flowOaLeaveResultVo = BeanUtil.toBean(flowOaLeavePo, FlowOaLeaveResultVo.class);
        List<String> permission = FlowFactory.userService().getPermission(flowOaLeaveResultVo.getInstanceId(), "4");
        flowOaLeaveResultVo.setAdditionalHandler(CollectionUtil.isEmpty(permission) ? new ArrayList<>() : permission);
        return flowOaLeaveResultVo;
    }

    @Override
    public Boolean addLeave(AddLeaveDto addLeaveDto, String flowStatus) {
        String id = IdUtils.nextIdStr();
        // 设置流转参数
        FlowOaLeavePo flowOaLeavePo = BeanUtil.toBean(addLeaveDto, FlowOaLeavePo.class);
        flowOaLeavePo.setId(Long.parseLong(id));
        // 从字典表中获取流程编码
        String flowCode = LeaveTypeEnum.getFlowCodeByType(flowOaLeavePo.getType());
        // 传递流程编码，绑定流程定义 【必传】
        FlowParams flowParams = FlowParams.build().flowCode(flowCode);
        // 设置办理人唯一标识，保存为流程实例的创建人 【必传】
        flowParams.handler(StpUtil.getLoginIdAsString());
        // 流程变量
        Map<String, Object> variable = new HashMap<>();
        // 流程变量传递业务数据，按实际业务需求传递 【按需传】
        variable.put("businessData", flowOaLeavePo);
        // 条件表达式替换，判断是否满足某个任务的跳转条件  【按需传】就是流程图中的线条上面的条件表达式所需的变量
        variable.put("flag", String.valueOf(flowOaLeavePo.getDay()));
        // 办理人变量表达式替换  【按需传】
        flowParams.variable(variable);
        // 自定义流程状态扩展
        if (StrUtil.isNotEmpty(flowStatus)) {
            flowParams.flowStatus(flowStatus).hisStatus(flowStatus);
        }
        // 新增请假表
        Instance instance = insService.start(id, flowParams);
        flowOaLeavePo.setInstanceId(instance.getId());
        flowOaLeavePo.setNodeCode(instance.getNodeCode());
        flowOaLeavePo.setNodeName(instance.getNodeName());
        flowOaLeavePo.setNodeType(instance.getNodeType());
        flowOaLeavePo.setFlowStatus(instance.getFlowStatus());
        // 新增抄送人方法  【按需】
        if (CollectionUtil.isNotEmpty(addLeaveDto.getAdditionalHandler())) {
            List<User> users = FlowFactory.userService().structureUser(instance.getId()
                    , addLeaveDto.getAdditionalHandler(), "4");
            FlowFactory.userService().saveBatch(users);
        }
        // 此处可以发送消息通知，比如短信通知，邮件通知等，代码自己实现
        return this.save(flowOaLeavePo);
    }

    @Override
    public Boolean updateLeave(UpdateLeaveDto updateLeaveDto) {
        FlowOaLeavePo flowOaLeavePo = BeanUtil.toBean(updateLeaveDto, FlowOaLeavePo.class);
        return this.updateById(flowOaLeavePo);
    }

    @Override
    public Boolean deleteLeave(String[] ids) {
        List<FlowOaLeavePo> flowOaLeavePos = this.listByIds(Arrays.stream(ids).collect(Collectors.toList()));
        if (CollectionUtil.isNotEmpty(flowOaLeavePos)) {
            this.removeByIds((Arrays.stream(ids).collect(Collectors.toList())));
            List<Long> instanceIds = flowOaLeavePos.stream().map(FlowOaLeavePo::getInstanceId).collect(Collectors.toList());
            return insService.remove(instanceIds);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean submitLeave(SubmitLeaveDto submitLeaveDto) {
        // 设置流转参数
        FlowOaLeavePo flowOaLeavePo = this.getById(submitLeaveDto.getId());
        // 是通过流程还是退回流程  【必传】
        FlowParams flowParams = FlowParams.build().skipType(SkipType.PASS.getKey());
        // 作为办理人保存到历史记录表 【必传】
        flowParams.handler(StpUtil.getLoginIdAsString());
        // 设置办理人拥有的权限，办理中需要校验是否有权限办理 【必传】
        LoginUser loginUser = SaTokenLoginUtils.getLoginUser(StpType.SYSTEM);
        if (Objects.isNull(loginUser)) {
            ExceptionUtil.castServiceException(UNAUTHORIZED);
        }
        Set<String> roles = loginUser.getRoles();
        List<String> permissionList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(roles)) {
            permissionList = roles.stream().map(role -> "role:" + role).collect(Collectors.toList());
        }
        permissionList.add(StpUtil.getLoginIdAsString());
        flowParams.permissionFlag(permissionList);
        // 自定义流程状态扩展  【按需传】
        if (StrUtil.isNotEmpty(submitLeaveDto.getFlowStatus())) {
            flowParams.flowStatus(submitLeaveDto.getFlowStatus()).hisStatus(submitLeaveDto.getFlowStatus());
        }
        // 流程变量
        Map<String, Object> variable = new HashMap<>();
        // 流程变量传递业务数据，按实际业务需求传递  【按需传】
        variable.put("businessData", flowOaLeavePo);
        // 办理人变量表达式替换  【按需传】
        variable.put("flag", String.valueOf(flowOaLeavePo.getDay()));
        flowParams.variable(variable);

        // 更新请假表
        Instance instance = insService.skipByInsId(flowOaLeavePo.getInstanceId(), flowParams);
        flowOaLeavePo.setNodeCode(instance.getNodeCode());
        flowOaLeavePo.setNodeName(instance.getNodeName());
        flowOaLeavePo.setNodeType(instance.getNodeType());
        flowOaLeavePo.setFlowStatus(instance.getFlowStatus());
        return this.updateById(flowOaLeavePo);
    }

    @Override
    public Boolean handlerLeave(UpdateLeaveDto updateLeaveDto, Long taskId, String skipType, String message, String nodeCode, String flowStatus) {
        // 设置流转参数
        // 是通过流程还是退回流程 【必传】
        FlowParams flowParams = FlowParams.build().skipType(skipType);
        // 作为办理人保存到历史记录表 【必传】
        flowParams.handler(StpUtil.getLoginIdAsString());
        // 如果需要任意跳转流程，传入此参数  【按需传】
        flowParams.nodeCode(nodeCode);
        // 作为审批意见保存到历史记录表  【按需传】
        flowParams.message(message);

        // 设置办理人拥有的权限，办理中需要校验是否有权限办理 【必传】
        LoginUser loginUser = SaTokenLoginUtils.getLoginUser(StpType.SYSTEM);
        if (Objects.isNull(loginUser)) {
            ExceptionUtil.castServiceException(UNAUTHORIZED);
        }
        Set<String> roles = loginUser.getRoles();
        List<String> permissionList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(roles)) {
            permissionList = roles.stream().map(role -> "role:" + role).collect(Collectors.toList());

        }
        permissionList.add(StpUtil.getLoginIdAsString());
        flowParams.permissionFlag(permissionList);
        // 流程变量
        Map<String, Object> variable = new HashMap<>();
        // 流程变量传递业务数据，按实际业务需求传递  【按需传】
        variable.put("businessData", updateLeaveDto);
        // 办理人变量表达式替换  【按需传】
        variable.put("flag", String.valueOf(updateLeaveDto.getDay()));
        flowParams.variable(variable);
        // 自定义流程状态扩展  【按需传】
        if (StringUtils.isNotEmpty(flowStatus)) {
            flowParams.flowStatus(flowStatus).hisStatus(flowStatus);
        }
        // 请假信息存入flowParams,方便查看历史审批数据  【按需传】
        flowParams.hisTaskExt(JSON.toJSONString(updateLeaveDto));
        Instance instance = taskService.skip(taskId, flowParams);

        // 更新请假表
        updateLeaveDto.setNodeCode(instance.getNodeCode());
        updateLeaveDto.setNodeName(instance.getNodeName());
        updateLeaveDto.setNodeType(instance.getNodeType());
        updateLeaveDto.setFlowStatus(instance.getFlowStatus());
        return this.updateById(BeanUtil.toBean(updateLeaveDto, FlowOaLeavePo.class));
    }

    @Override
    public Boolean terminationLeave(UpdateLeaveDto updateLeaveDto) {
        // 设置流转参数
        FlowParams flowParams = new FlowParams();
        // 设置办理人拥有的权限，办理中需要校验是否有权限办理 【必传】
        LoginUser loginUser = SaTokenLoginUtils.getLoginUser(StpType.SYSTEM);
        if (Objects.isNull(loginUser)) {
            ExceptionUtil.castServiceException(UNAUTHORIZED);
        }
        // 作为审批意见保存到历史记录表  【按需传】
        flowParams.message("终止流程");
        // 作为办理人保存到历史记录表 【必传】
        flowParams.handler(StpUtil.getLoginIdAsString());
        // 设置办理人拥有的权限，办理中需要校验是否有权限办理 【必传】
        Set<String> roles = loginUser.getRoles();
        List<String> permissionList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(roles)) {
            permissionList = roles.stream().map(role -> "role:" + role).collect(Collectors.toList());

        }
        permissionList.add(StpUtil.getLoginIdAsString());
        flowParams.permissionFlag(permissionList);
        Map<String, Object> variable = new HashMap<>();
        // 流程变量传递业务数据，按实际业务需求传递  【按需传】
        variable.put("businessData", updateLeaveDto);
        flowParams.variable(variable);

        Instance instance = insService.termination(updateLeaveDto.getInstanceId(), flowParams);
        ObjectUtil.ifNullCastServiceException(instance, INSTANCE_NOT_EXISTS);
        // 更新请假表
        updateLeaveDto.setNodeCode(instance.getNodeCode());
        updateLeaveDto.setNodeName(instance.getNodeName());
        updateLeaveDto.setNodeType(instance.getNodeType());
        updateLeaveDto.setFlowStatus(instance.getFlowStatus());
        return this.updateById(BeanUtil.toBean(updateLeaveDto, FlowOaLeavePo.class));
    }
}
