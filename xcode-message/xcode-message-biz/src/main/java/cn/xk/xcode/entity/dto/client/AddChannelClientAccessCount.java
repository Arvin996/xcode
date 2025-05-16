package cn.xk.xcode.entity.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/5/15 16:07
 * @Version 1.0.0
 * @Description UpdateChannelClientAccessCount
 **/
@Data
@Schema(name = "AddChannelClientAccessCount", description = "增加渠道接入商配额")
public class AddChannelClientAccessCount {

    @Schema(description = "id")
    @NotNull(message = "id 不能为空")
    private Integer id;

    @Schema(description = "新增accessCount配额")
    @NotNull(message = "accessCount 不能为空")
    private Integer accessCount;
}
