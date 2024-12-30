package cn.xk.xcode.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/8/16 14:20
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlexTransDto {
    private Class<? extends TransPo> targetClazz;
    private Serializable id;
    private String conditionField;
}
