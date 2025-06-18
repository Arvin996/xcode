package cn.xk.xcode.entity.dto.sku;

import lombok.Data;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/6/6 17:04
 * @Version 1.0.0
 * @Description QuerySkuDto
 **/
@Data
public class QuerySkuDto {

    private Long spuId;
    private List<Long> skuIds;
    private Boolean orderBySale = null;
    private Boolean orderByPrice = null;
    private Integer limit;
}
