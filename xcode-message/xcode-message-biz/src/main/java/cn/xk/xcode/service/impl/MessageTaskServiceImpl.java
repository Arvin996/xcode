package cn.xk.xcode.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.EnhanceXxlJobService;
import cn.xk.xcode.entity.dto.task.QueryMessageTaskDetailDto;
import cn.xk.xcode.entity.dto.task.QueryMessageTaskDto;
import cn.xk.xcode.entity.po.MessageChannelPo;
import cn.xk.xcode.entity.po.MessageTaskDetailPo;
import cn.xk.xcode.entity.po.MessageTaskPo;
import cn.xk.xcode.entity.vo.task.MessageTaskDetailVo;
import cn.xk.xcode.entity.vo.task.MessageTaskVo;
import cn.xk.xcode.enums.MessageSendType;
import cn.xk.xcode.enums.MessageTaskStatusEnum;
import cn.xk.xcode.enums.ShieldType;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.mapper.MessageTaskMapper;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.MessageChannelService;
import cn.xk.xcode.service.MessageTaskDetailService;
import cn.xk.xcode.service.MessageTaskService;
import cn.xk.xcode.utils.PageUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.time.LocalDateTime;

import static cn.xk.xcode.config.GlobalMessageConstants.*;
import static cn.xk.xcode.entity.def.MessageChannelAccessClientTableDef.MESSAGE_CHANNEL_ACCESS_CLIENT;
import static cn.xk.xcode.entity.def.MessageChannelAccountTableDef.MESSAGE_CHANNEL_ACCOUNT;
import static cn.xk.xcode.entity.def.MessageChannelTableDef.MESSAGE_CHANNEL;
import static cn.xk.xcode.entity.def.MessageTaskDetailTableDef.MESSAGE_TASK_DETAIL;
import static cn.xk.xcode.entity.def.MessageTaskTableDef.MESSAGE_TASK;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2025-05-15
 */
@Service
public class MessageTaskServiceImpl extends ServiceImpl<MessageTaskMapper, MessageTaskPo> implements MessageTaskService {

    @Resource
    private MessageTaskDetailService messageTaskDetailService;

    @Resource
    private MessageChannelService messageChannelService;

    @Resource
    private EnhanceXxlJobService enhanceXxlJobService;

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

    @Override
    public Boolean cancelMessage(Integer taskId) {
        MessageTaskPo messageTaskPo = this.getById(taskId);
        if (ObjectUtil.isNull(messageTaskPo)) {
            ExceptionUtil.castServiceException(MESSAGE_TASK_NOT_EXISTS);
        }
        MessageChannelPo messageChannelPo = messageChannelService.getById(messageTaskPo.getChannelId());
        if (MessageSendType.CORN.getCode().equals(messageChannelPo.getCode())) {
            ExceptionUtil.castServiceException(THIS_MESSAGE_TASK_NOT_SUPPORTS_CANCEL);
        }
        String status = messageTaskPo.getStatus();
        if (MessageSendType.NOW.getCode().equals(messageChannelPo.getCode())) {
            if (ShieldType.NIGHT_NO_SHIELD.getCode().equals(messageTaskPo.getShieldType())){
                ExceptionUtil.castServiceException(THIS_MESSAGE_TASK_NOT_SUPPORTS_CANCEL);
            }else {
                if (!MessageTaskStatusEnum.WAITING.getStatus().equals(status)) {
                    ExceptionUtil.castServiceException(SHIELD_TASK_NOT_WAITING);
                }
            }
        }
        if (!MessageTaskStatusEnum.WAITING.getStatus().equals(status)) {
            ExceptionUtil.castServiceException(DELAY_TASK_NOT_WAITING);
        }
        messageTaskPo.setStatus(MessageTaskStatusEnum.CANCEL.getStatus());
        return this.updateById(messageTaskPo);
    }

    @Override
    public Boolean pauseMessage(Integer taskId) {
        MessageTaskPo messageTaskPo = this.getById(taskId);
        if (ObjectUtil.isNull(messageTaskPo)) {
            ExceptionUtil.castServiceException(MESSAGE_TASK_NOT_EXISTS);
        }
        MessageChannelPo messageChannelPo = messageChannelService.getById(messageTaskPo.getChannelId());
        if (!MessageSendType.CORN.getCode().equals(messageChannelPo.getCode())) {
            ExceptionUtil.castServiceException(ONLY_CORN_TASK_SUPPORTS_PAUSE);
        }
        // 暂停任务 调用xxl-job暂停任务接口
        Long taskCornId = messageTaskPo.getTaskCornId();
        enhanceXxlJobService.stopXxlJob(Math.toIntExact(taskCornId));
        messageTaskPo.setStatus(MessageTaskStatusEnum.PAUSE.getStatus());
        return this.updateById(messageTaskPo);
    }

    @Override
    public Boolean resumeMessage(Integer taskId) {
        MessageTaskPo messageTaskPo = this.getById(taskId);
        if (ObjectUtil.isNull(messageTaskPo)) {
            ExceptionUtil.castServiceException(MESSAGE_TASK_NOT_EXISTS);
        }
        MessageChannelPo messageChannelPo = messageChannelService.getById(messageTaskPo.getChannelId());
        if (!MessageSendType.NOW.getCode().equals(messageChannelPo.getCode())) {
            ExceptionUtil.castServiceException(NOW_TASK_NOT_SUPPORTS_RESUME);
        }
        if (MessageSendType.CORN.getCode().equals(messageChannelPo.getCode())) {
            // 定时
            messageTaskPo.setStatus(MessageTaskStatusEnum.WAITING.getStatus());
            enhanceXxlJobService.startXxlJob(Math.toIntExact(messageTaskPo.getTaskCornId()));
        } else {
            // 延时
            LocalDateTime scheduleTime = messageTaskPo.getScheduleTime();
            if (LocalDateTime.now().isAfter(scheduleTime)) {
                ExceptionUtil.castServiceException(DEALY_MESSAGE_TASK_SCHEDULE_TIME_ALREADY_PASS);
            } else {
                messageTaskPo.setStatus(MessageTaskStatusEnum.WAITING.getStatus());
            }
        }
        return updateById(messageTaskPo);
    }
}
