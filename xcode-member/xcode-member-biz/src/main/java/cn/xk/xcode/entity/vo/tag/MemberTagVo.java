package cn.xk.xcode.entity.vo.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @Author Administrator
 * @Date 2024/8/20 19:27
 * @Version V1.0.0
 * @Description MemberTageVo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员tag返回")
public class MemberTagVo {

    @Schema(description = "标签id")
    private Integer id;

    @Schema(description = "标签名称")
    private String name;
}
