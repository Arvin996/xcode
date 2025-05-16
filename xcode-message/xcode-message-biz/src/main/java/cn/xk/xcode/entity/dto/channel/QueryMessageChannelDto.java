package cn.xk.xcode.entity.dto.channel;

import cn.xk.xcode.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author xuk
 * @Date 2025/3/10 9:48
 * @Version 1.0.0
 * @Description QueryMessageChannelDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "QueryMessageChannelDto", description = "渠道查询dto")
public class QueryMessageChannelDto extends PageParam {

    @Schema(description = "渠道码")
    private String code;

    @Schema(description = "渠道名")
    private String name;
}
