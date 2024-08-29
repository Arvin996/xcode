package cn.xk.xcode.entity.vo;

import com.xk.xcode.core.entity.Area;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/8/5 14:15
 * @Version 1.0
 * @Description MemberAddressResultVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "用户收获地址返回")
public class MemberAddressResultVo {
    @Schema(description = "收货地址id")
    private Integer id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "收件人姓名")
    private String name;

    @Schema(description = "收件人手机号")
    private String phone;

    @Schema(description = "地区id")
    private Integer areaId;

    @Schema(description = "地区详情")
    private Area area;

    @Schema(description = "详细的收件地址 如楼层+门牌号")
    private String detailAddress;

    @Schema(description = "是否是默认地址")
    private String defaultAddress;
}
