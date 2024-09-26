package cn.xk.xcode.entity.dto.app;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/9/26 15:33
 * @Version 1.0.0
 * @Description BindAppChannelDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "新增支付应用 dto")
public class BindAppChannelDto {

    @Schema(description = "应用id")
    @NotNull(message = "应用id不能为空")
    private Integer appId;

    @Schema(description = "支付渠道list")
    @NotNull(message = "支付渠道list不能为空")
    private List<Integer> channelList;
}
