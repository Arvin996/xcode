package cn.xk.xcode.mapper;

import cn.xk.xcode.entity.dto.template.QueryMessageTemplateDto;
import cn.xk.xcode.entity.po.MessageTemplatePo;
import cn.xk.xcode.entity.vo.template.MessageTemplateVo;
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
public interface MessageTemplateMapper extends BaseMapper<MessageTemplatePo> {

    List<MessageTemplateVo> getMessageTemplateList(@Param("queryMessageTemplateDto") QueryMessageTemplateDto queryMessageTemplateDto);
}
