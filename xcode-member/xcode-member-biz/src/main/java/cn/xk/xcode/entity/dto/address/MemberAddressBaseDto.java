package cn.xk.xcode.entity.dto.address;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Administrator
 * @Date 2024/8/21 19:59
 * @Version V1.0.0
 * @Description MemberAddressBaseDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "会员收货地址基础dto")
public class MemberAddressBaseDto {

    @Schema(description = "收货地址id")
    private Integer id;
}
