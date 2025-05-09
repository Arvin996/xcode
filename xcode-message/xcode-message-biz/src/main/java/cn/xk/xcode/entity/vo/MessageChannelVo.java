package cn.xk.xcode.entity.vo;

import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.entity.po.MessageChannelPo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/10 9:46
 * @Version 1.0.0
 * @Description MessageChannelVo
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "message channel vo")
public class MessageChannelVo extends MessageChannelPo {

    private List<MessageChannelAccountPo> channelAccountPoList;
}
