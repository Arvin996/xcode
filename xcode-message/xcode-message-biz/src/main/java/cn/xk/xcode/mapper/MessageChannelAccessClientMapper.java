package cn.xk.xcode.mapper;

import cn.xk.xcode.entity.dto.client.QueryChannelClientDto;
import cn.xk.xcode.entity.po.MessageChannelAccessClientPo;
import cn.xk.xcode.entity.vo.client.MessageChannelClientVo;
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
public interface MessageChannelAccessClientMapper extends BaseMapper<MessageChannelAccessClientPo> {

    List<MessageChannelClientVo> queryAllMessageChannelAccessClients(@Param("queryChannelClientDto") QueryChannelClientDto queryChannelClientDto);
}
