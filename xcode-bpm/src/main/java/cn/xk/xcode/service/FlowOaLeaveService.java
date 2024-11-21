package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.leave.AddLeaveDto;
import cn.xk.xcode.entity.dto.leave.QueryLeaveDto;
import cn.xk.xcode.entity.dto.leave.SubmitLeaveDto;
import cn.xk.xcode.entity.dto.leave.UpdateLeaveDto;
import cn.xk.xcode.entity.vo.leave.FlowOaLeaveResultVo;
import cn.xk.xcode.pojo.PageResult;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.FlowOaLeavePo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2024-11-21
 */
public interface FlowOaLeaveService extends IService<FlowOaLeavePo> {

    List<FlowOaLeaveResultVo> getFlowOaLeaveList(QueryLeaveDto queryLeaveDto);

    void exportFlowOaLeaveList(HttpServletResponse response, QueryLeaveDto queryLeaveDto);

    FlowOaLeaveResultVo getLeaveDetail(Long id);

    Boolean addLeave(AddLeaveDto addLeaveDto, String flowStatus);

    Boolean updateLeave(UpdateLeaveDto updateLeaveDto);

    Boolean deleteLeave(String[] ids);

    Boolean submitLeave(SubmitLeaveDto submitLeaveDto);

    Boolean handlerLeave(UpdateLeaveDto updateLeaveDto, Long taskId, String skipType, String message, String nodeCode, String flowStatus);

    Boolean terminationLeave(UpdateLeaveDto updateLeaveDto);
}
