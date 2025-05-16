package cn.xk.xcode.mapper;

import cn.xk.xcode.entity.dto.channel.QueryMessageChannelDto;
import cn.xk.xcode.entity.po.MessageChannelPo;
import cn.xk.xcode.entity.vo.channel.MessageChannelVo;
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
public interface MessageChannelMapper extends BaseMapper<MessageChannelPo> {

    List<MessageChannelVo> getMessageChannel(@Param("queryMessageChannelDto") QueryMessageChannelDto queryMessageChannelDto);
}
