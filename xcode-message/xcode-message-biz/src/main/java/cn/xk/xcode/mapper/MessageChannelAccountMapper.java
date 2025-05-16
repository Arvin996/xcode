package cn.xk.xcode.mapper;

import cn.xk.xcode.entity.dto.account.QueryMessageChannelAccountDto;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.entity.vo.account.MessageChannelAccountVo;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  映射层。
 *
 * @author Administrator
 * @since 2025-05-15
 */
@Mapper
public interface MessageChannelAccountMapper extends BaseMapper<MessageChannelAccountPo> {
    List<MessageChannelAccountVo> queryAllMessageChannelAccounts(@Param("queryMessageChannelAccountDto") QueryMessageChannelAccountDto queryMessageChannelAccountDto);
}
