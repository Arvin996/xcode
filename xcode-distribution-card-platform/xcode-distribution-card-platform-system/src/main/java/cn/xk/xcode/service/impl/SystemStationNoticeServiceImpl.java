package cn.xk.xcode.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.entity.dto.notice.PublishSystemStationNoticeDto;
import cn.xk.xcode.entity.dto.notice.QuerySystemStationNoticeDto;
import cn.xk.xcode.entity.po.SystemUserPo;
import cn.xk.xcode.entity.vo.notice.SystemStationNoticeVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.handler.WebSocketMessageHandler;
import cn.xk.xcode.message.Header;
import cn.xk.xcode.message.MessageEntity;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.SystemUserService;
import cn.xk.xcode.utils.PageUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.SystemStationNoticePo;
import cn.xk.xcode.mapper.SystemStationNoticeMapper;
import cn.xk.xcode.service.SystemStationNoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.stream.Collectors;

import static cn.xk.xcode.config.DistributionCardSystemErrorCode.NOTICE_TO_USER_MUST_NOT_EMPTY;
import static cn.xk.xcode.entity.def.SystemStationNoticeTableDef.SYSTEM_STATION_NOTICE_PO;
import static cn.xk.xcode.entity.def.SystemUserTableDef.SYSTEM_USER_PO;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2025-05-09
 */
@Service
public class SystemStationNoticeServiceImpl extends ServiceImpl<SystemStationNoticeMapper, SystemStationNoticePo> implements SystemStationNoticeService {

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private WebSocketMessageHandler webSocketMessageHandler;

    @Override
    public Boolean publishSystemNotice(PublishSystemStationNoticeDto publishSystemStationNoticeDto) {
        String type = publishSystemStationNoticeDto.getType();
        if ("1".equals(type)) {
            String toUser = publishSystemStationNoticeDto.getToUser();
            if (StrUtil.isBlank(toUser)) {
                ExceptionUtil.castServiceException(NOTICE_TO_USER_MUST_NOT_EMPTY);
            }
            MessageEntity messageEntity = new MessageEntity();
            Header header = new Header();
            header.setType("system_station_notice");
            messageEntity.setHeader(header);
            messageEntity.setToUsers(CollectionUtil.createSingleList(toUser));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", publishSystemStationNoticeDto.getTitle());
            jsonObject.put("message", publishSystemStationNoticeDto.getMessage());
            jsonObject.put("createTime", LocalDateTimeUtil.formatNormal(LocalDateTimeUtil.now()));
            jsonObject.put("from", "系统管理员");
            messageEntity.setData(jsonObject);
            messageEntity.setFromUser("系统管理员");
            webSocketMessageHandler.sendMessage(messageEntity);
            return this.save(BeanUtil.toBean(publishSystemStationNoticeDto, SystemStationNoticePo.class));
        } else {
            List<SystemUserPo> systemUserPos = systemUserService.list(SYSTEM_USER_PO.STATUS.eq(CommonStatusEnum.ENABLE.getStatus()));
            if (CollectionUtil.isEmpty(systemUserPos)) {
                return true;
            } else {
                MessageEntity messageEntity = new MessageEntity();
                Header header = new Header();
                header.setType("system_station_notice");
                messageEntity.setHeader(header);
                messageEntity.setToUsers(systemUserPos.stream().map(SystemUserPo::getUsername).collect(Collectors.toList()));
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title", publishSystemStationNoticeDto.getTitle());
                jsonObject.put("message", publishSystemStationNoticeDto.getMessage());
                jsonObject.put("createTime", LocalDateTimeUtil.formatNormal(LocalDateTimeUtil.now()));
                jsonObject.put("from", "系统管理员");
                messageEntity.setData(jsonObject);
                messageEntity.setFromUser("系统管理员");
                webSocketMessageHandler.sendMessage(messageEntity);
                List<SystemStationNoticePo> collect = systemUserPos.stream().map(o -> {
                    SystemStationNoticePo systemStationNoticePo = new SystemStationNoticePo();
                    systemStationNoticePo.setTitle(publishSystemStationNoticeDto.getTitle());
                    systemStationNoticePo.setToUser(o.getUsername());
                    systemStationNoticePo.setMessage(publishSystemStationNoticeDto.getMessage());
                    systemStationNoticePo.setType(publishSystemStationNoticeDto.getType());
                    return systemStationNoticePo;
                }).collect(Collectors.toList());
                return this.saveBatch(collect);
            }
        }
    }

    @Override
    public PageResult<SystemStationNoticeVo> queryAllNotice(QuerySystemStationNoticeDto querySystemStationNoticeDto) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SYSTEM_STATION_NOTICE_PO.ALL_COLUMNS)
                .from(SYSTEM_STATION_NOTICE_PO)
                .where("1=1")
                .and(SYSTEM_STATION_NOTICE_PO.TITLE.likeRight(querySystemStationNoticeDto.getTitle()).when(StrUtil.isBlank(querySystemStationNoticeDto.getTitle())))
                .and(SYSTEM_STATION_NOTICE_PO.TO_USER.likeRight(querySystemStationNoticeDto.getToUser()).when(StrUtil.isBlank(querySystemStationNoticeDto.getToUser())))
                .and(SYSTEM_STATION_NOTICE_PO.TYPE.eq(querySystemStationNoticeDto.getType()).when(StrUtil.isNotBlank(querySystemStationNoticeDto.getType())));
        Page<SystemStationNoticePo> page = PageUtil.toFlexPage(querySystemStationNoticeDto);
        Page<SystemStationNoticePo> systemStationNoticePoPage = this.page(page, queryWrapper);
        return PageUtil.toPageResult(systemStationNoticePoPage, SystemStationNoticeVo.class);
    }
}
