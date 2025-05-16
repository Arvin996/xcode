package cn.xk.xcode.service.backend;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.core.StpSystemUtil;
import cn.xk.xcode.entity.dto.user.QueryUserDto;
import cn.xk.xcode.entity.dto.user.UpdateUserRoleDto;
import cn.xk.xcode.entity.po.SystemUserPo;
import cn.xk.xcode.entity.vo.user.SystemUserVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.handler.WebSocketMessageHandler;
import cn.xk.xcode.message.Header;
import cn.xk.xcode.message.MessageEntity;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.SystemUserService;
import cn.xk.xcode.utils.PageUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static cn.xk.xcode.config.DistributionCardSystemErrorCode.USER_NOT_EXISTS;
import static cn.xk.xcode.entity.def.SystemUserTableDef.SYSTEM_USER_PO;

/**
 * @Author xuk
 * @Date 2025/5/13 10:33
 * @Version 1.0.0
 * @Description BackendService
 **/
@Service
public class BackendService {

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private WebSocketMessageHandler webSocketMessageHandler;

    public Boolean lockUser(String username) {
        SystemUserPo systemUserPo = systemUserService.getOne(SYSTEM_USER_PO.USERNAME.eq(username));
        if (ObjectUtil.isNull(systemUserPo)) {
            ExceptionUtil.castServiceException(USER_NOT_EXISTS);
        }
        String status = systemUserPo.getStatus();
        if (CommonStatusEnum.isDisable(status)) {
            return true;
        }
        systemUserPo.setStatus(CommonStatusEnum.DISABLE.getStatus());
        return systemUserService.updateById(systemUserPo);
    }

    public PageResult<SystemUserVo> queryAllUsers(QueryUserDto queryUserDto) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SYSTEM_USER_PO.ALL_COLUMNS)
                .from(SYSTEM_USER_PO)
                .where("1=1")
                .and(SYSTEM_USER_PO.USERNAME.like(queryUserDto.getUsername()).when(StrUtil.isNotBlank(queryUserDto.getUsername())))
                .and(SYSTEM_USER_PO.NICKNAME.like(queryUserDto.getNickname()).when(StrUtil.isNotBlank(queryUserDto.getNickname())));
        Page<SystemUserPo> page = new Page<>(queryUserDto.getPageNo(), queryUserDto.getPageSize());
        Page<SystemUserPo> systemUserPoPage = systemUserService.page(page, queryWrapper);
        return PageUtil.toPageResult(systemUserPoPage, SystemUserVo.class);
    }

    public Boolean unlockUser(String username) {
        SystemUserPo systemUserPo = systemUserService.getOne(SYSTEM_USER_PO.USERNAME.eq(username));
        if (ObjectUtil.isNull(systemUserPo)) {
            ExceptionUtil.castServiceException(USER_NOT_EXISTS);
        }
        String status = systemUserPo.getStatus();
        if (CommonStatusEnum.isEnable(status)) {
            return true;
        }
        systemUserPo.setStatus(CommonStatusEnum.ENABLE.getStatus());
        return systemUserService.updateById(systemUserPo);
    }

    public Boolean kickout(String username) {
        String token = StpSystemUtil.getTokenValueByLoginId(username);
        if (StrUtil.isNotBlank(token)) {
            StpSystemUtil.kickoutByTokenValue(token);
        }
        MessageEntity messageEntity = new MessageEntity();
        Header header = new Header();
        header.setType("system_station_notice");
        messageEntity.setHeader(header);
        messageEntity.setToUsers(CollectionUtil.createSingleList(username));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "系统警告");
        jsonObject.put("message", "您的账号已被踢下线，请联系管理员");
        jsonObject.put("createTime", LocalDateTimeUtil.formatNormal(LocalDateTimeUtil.now()));
        jsonObject.put("from", "系统管理员");
        messageEntity.setData(jsonObject);
        messageEntity.setFromUser("系统管理员");
        webSocketMessageHandler.sendMessage(messageEntity);
        return true;
    }

    public Boolean updateUserRole(UpdateUserRoleDto updateUserRoleDto) {
        SystemUserPo systemUserPo = systemUserService.getOne(SYSTEM_USER_PO.USERNAME.eq(updateUserRoleDto.getUsername()));
        if (ObjectUtil.isNull(systemUserPo)) {
            ExceptionUtil.castServiceException(USER_NOT_EXISTS);
        }
        systemUserPo.setRoleId(updateUserRoleDto.getNewRoleId());
        return systemUserService.updateById(systemUserPo);
    }
}
