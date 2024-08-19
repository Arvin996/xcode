package cn.xk.xcode.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/8/16 14:27
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnumTransDto {
    private String enumType;
    private String key;
}
