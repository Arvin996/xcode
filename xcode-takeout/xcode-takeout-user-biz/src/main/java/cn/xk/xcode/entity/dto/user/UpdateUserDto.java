package cn.xk.xcode.entity.dto.user;

import cn.xk.xcode.typehandler.SexTypeHandler;
import cn.xk.xcode.validation.Mobile;
import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/10/30 11:37
 * @Version 1.0.0
 * @Description UpdateUserDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "更新用户 dto")
public class UpdateUserDto extends UserBaseDto{

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    @NotBlank(message = "姓名不能为空")
    private String name;


    /**
     * 性别 0 男 1 女
     */
    @Schema(description = "性别 0 男 1 女")
    @NotNull(message = "性别不能为空")
    @Column(typeHandler = SexTypeHandler.class)
    private String sex;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 头像
     */
    private String avatar;
}
