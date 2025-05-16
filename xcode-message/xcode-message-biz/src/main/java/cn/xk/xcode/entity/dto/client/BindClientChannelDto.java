package cn.xk.xcode.entity.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/5/16 13:56
 * @Version 1.0.0
 * @Description BindClientChannelDto
 **/
@Data
@Schema(name = "BindClientChannelDto", description = "BindClientChannelDto 接入商绑定渠道dto")
public class BindClientChannelDto {

    @Schema(description = "id")
    @NotNull(message = "接入商id不能为空")
    private Integer id;

    @Schema(description = "channel id list")
    private List<Integer> channelIdList;
}
