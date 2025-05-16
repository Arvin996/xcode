package cn.xk.xcode.entity.dto.client;

import cn.xk.xcode.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author xuk
 * @Date 2025/5/15 11:30
 * @Version 1.0.0
 * @Description QueryChannelClientDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "QueryChannelClientDto", description = "查询渠道接入商dto")
public class QueryChannelClientDto extends PageParam {

    /**
     * 接入商名称
     */
    @Schema(description = "接入商名称")
    private String name;

    /**
     * 接入商邮箱
     */
    private String email;

    /**
     * 接入商手机号
     */
    private String mobile;

    /**
     * 0启用 1禁用
     */
    private String status;
}
