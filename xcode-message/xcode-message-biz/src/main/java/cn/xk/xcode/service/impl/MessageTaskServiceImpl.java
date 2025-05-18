package cn.xk.xcode.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.entity.dto.task.QueryMessageTaskDetailDto;
import cn.xk.xcode.entity.dto.task.QueryMessageTaskDto;
import cn.xk.xcode.entity.po.MessageTaskDetailPo;
import cn.xk.xcode.entity.po.MessageTaskPo;
import cn.xk.xcode.entity.vo.task.MessageTaskDetailVo;
import cn.xk.xcode.entity.vo.task.MessageTaskVo;
import cn.xk.xcode.mapper.MessageTaskMapper;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.MessageTaskDetailService;
import cn.xk.xcode.service.MessageTaskService;
import cn.xk.xcode.utils.PageUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static cn.xk.xcode.entity.def.MessageChannelAccessClientTableDef.MESSAGE_CHANNEL_ACCESS_CLIENT;
import static cn.xk.xcode.entity.def.MessageChannelAccountTableDef.MESSAGE_CHANNEL_ACCOUNT;
import static cn.xk.xcode.entity.def.MessageChannelTableDef.MESSAGE_CHANNEL;
import static cn.xk.xcode.entity.def.MessageTaskDetailTableDef.MESSAGE_TASK_DETAIL;
import static cn.xk.xcode.entity.def.MessageTaskTableDef.MESSAGE_TASK;

/**
 *  服务层实现。
 *
 * @author Administrator
 * @since 2025-05-15
 */
@Service
public class MessageTaskServiceImpl extends ServiceImpl<MessageTaskMapper, MessageTaskPo> implements MessageTaskService {

    @Resource
    private MessageTaskDetailService messageTaskDetailService;

    @Override
    public PageResult<MessageTaskVo> queryMessageTasks(QueryMessageTaskDto queryMessageTaskDto) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(MESSAGE_TASK.ALL_COLUMNS, MESSAGE_CHANNEL_ACCESS_CLIENT.NAME.as("clientName")
                        , MESSAGE_CHANNEL_ACCOUNT.ACCOUNT_NAME.as("accountName"),
                        MESSAGE_CHANNEL.NAME.as("channelName"), MESSAGE_CHANNEL.CODE.as("channelCode"))
                .from(MESSAGE_TASK)
                .leftJoin(MESSAGE_CHANNEL_ACCESS_CLIENT)
                .on(MESSAGE_TASK.CLIENT_ID.eq(MESSAGE_CHANNEL_ACCESS_CLIENT.ID))
                .leftJoin(MESSAGE_CHANNEL)
                .on(MESSAGE_TASK.CHANNEL_ID.eq(MESSAGE_CHANNEL.ID))
                .leftJoin(MESSAGE_CHANNEL_ACCOUNT)
                .on(MESSAGE_TASK.ACCOUNT_ID.eq(MESSAGE_CHANNEL_ACCOUNT.ID))
                .where("1=1")
                .and(MESSAGE_TASK.STATUS.eq(queryMessageTaskDto.getStatus()).when(StrUtil.isNotBlank(queryMessageTaskDto.getStatus())))
                .and(MESSAGE_TASK.MSG_TYPE.eq(queryMessageTaskDto.getMsgType()).when(StrUtil.isNotBlank(queryMessageTaskDto.getMsgType())))
                .and(MESSAGE_CHANNEL.CODE.eq(queryMessageTaskDto.getChannelCode()).when(StrUtil.isNotBlank(queryMessageTaskDto.getChannelCode())))
                .and(MESSAGE_TASK.CREATE_TIME.ge(queryMessageTaskDto.getStartTime()).when(ObjectUtil.isNotNull(queryMessageTaskDto.getStartTime())))
                .and(MESSAGE_TASK.CREATE_TIME.le(queryMessageTaskDto.getEndTime()).when(ObjectUtil.isNotNull(queryMessageTaskDto.getEndTime())));
        Page<MessageTaskPo> flexPage = PageUtil.toFlexPage(queryMessageTaskDto);
        Page<MessageTaskPo> page = this.page(flexPage, queryWrapper);
        return PageUtil.toPageResult(page, MessageTaskVo.class);
    }

    @Override
    public PageResult<MessageTaskDetailVo> queryMessageTaskDetail(QueryMessageTaskDetailDto queryMessageTaskDetailDto) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(MESSAGE_TASK_DETAIL.ALL_COLUMNS)
                .from(MESSAGE_TASK_DETAIL)
                .where(MESSAGE_TASK_DETAIL.TASK_ID.eq(queryMessageTaskDetailDto.getTaskId()))
                .where(MESSAGE_TASK_DETAIL.STATUS.eq(queryMessageTaskDetailDto.getStatus()).when(StrUtil.isNotBlank(queryMessageTaskDetailDto.getStatus())))
                .where(MESSAGE_TASK_DETAIL.RETRY_TIMES.eq(queryMessageTaskDetailDto.getRetryTimes()).when(ObjectUtil.isNotNull(queryMessageTaskDetailDto.getRetryTimes())))
                .where(MESSAGE_TASK_DETAIL.EXEC_TIME.ge(queryMessageTaskDetailDto.getStartTime()).when(ObjectUtil.isNotNull(queryMessageTaskDetailDto.getStartTime())))
                .where(MESSAGE_TASK_DETAIL.EXEC_TIME.le(queryMessageTaskDetailDto.getEndTime()).when(ObjectUtil.isNotNull(queryMessageTaskDetailDto.getEndTime())));
        Page<MessageTaskDetailPo> flexPage = PageUtil.toFlexPage(queryMessageTaskDetailDto);
        Page<MessageTaskDetailPo> page = messageTaskDetailService.page(flexPage, queryWrapper);
        return PageUtil.toPageResult(page, MessageTaskDetailVo.class);
    }
}
