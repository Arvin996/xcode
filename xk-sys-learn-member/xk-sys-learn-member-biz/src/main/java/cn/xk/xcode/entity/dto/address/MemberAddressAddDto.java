package cn.xk.xcode.entity.dto.address;

import cn.xk.xcode.validation.Mobile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author Administrator
 * @Date 2024/8/21 19:43
 * @Version V1.0.0
 * @Description MemberAddressAddDto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "新增会员收货地址dto")
public class MemberAddressAddDto {

    @NotBlank(message = "收件人姓名不能为空")
    @Schema(description = "收件人姓名", example = "xuk")
    private String name;

    @Mobile
    @Schema(description = "收件人手机号", example = "18888888888")
    @NotBlank(message = "收件人手机号不能为空")
    private String phone;

    @Schema(description = "地区id", example = "1")
    @NotNull(message = "地区id不能为空")
    private Integer areaId;

    @Schema(description = "详细的收件地址 如楼层+门牌号", example = "123456")
    private String detailAddress;

    @Schema(description = "是否是默认地址 0是 1不是", example = "0")
    private String defaultAddress;
}
