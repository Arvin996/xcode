package cn.xk.xcode.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.StpSystemUtil;
import cn.xk.xcode.entity.dto.audit.AuditResultDto;
import cn.xk.xcode.entity.dto.audit.QueryProductAuditDto;
import cn.xk.xcode.entity.po.ProductSkuPo;
import cn.xk.xcode.entity.po.ProductSpuPo;
import cn.xk.xcode.entity.po.ProductStorePo;
import cn.xk.xcode.entity.vo.audit.ProductAuditVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.*;
import cn.xk.xcode.utils.PageUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.ProductAuditPo;
import cn.xk.xcode.mapper.ProductAuditMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.xk.xcode.config.DistributionCardProductErrorCode.*;
import static cn.xk.xcode.entity.def.ProductAuditTableDef.PRODUCT_AUDIT_PO;
import static cn.xk.xcode.entity.def.ProductSkuAttributeValueTableDef.PRODUCT_SKU_ATTRIBUTE_VALUE_PO;
import static cn.xk.xcode.entity.def.ProductSpuAttributeTableDef.PRODUCT_SPU_ATTRIBUTE_PO;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2025-05-30
 */
@Service
public class ProductAuditServiceImpl extends ServiceImpl<ProductAuditMapper, ProductAuditPo> implements ProductAuditService {

    @Resource
    private ProductSpuService productSpuService;

    @Resource
    private ProductSkuService productSkuService;

    @Resource
    private ProductStoreService productStoreService;

    @Resource
    private ProductSpuAttributeService productSpuAttributeService;

    @Resource
    private ProductSkuAttributeValueService productSkuAttributeValueService;

    @Override
    public PageResult<ProductAuditVo> queryProductAuditList(QueryProductAuditDto queryProductAuditDto) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(PRODUCT_AUDIT_PO.ALL_COLUMNS)
                .from(PRODUCT_AUDIT_PO)
                .where("1=1")
                .and(PRODUCT_AUDIT_PO.STATUS.eq(queryProductAuditDto.getStatus()).when(StrUtil.isNotBlank(queryProductAuditDto.getStatus())))
                .and(PRODUCT_AUDIT_PO.TYPE.eq(queryProductAuditDto.getType()).when(StrUtil.isNotBlank(queryProductAuditDto.getType())))
                .and(PRODUCT_AUDIT_PO.CREATE_USER.like(queryProductAuditDto.getCreateUser()).when(StrUtil.isNotBlank(queryProductAuditDto.getCreateUser())))
                .and(PRODUCT_AUDIT_PO.CREATE_TIME.ge(queryProductAuditDto.getStartTime()).when(ObjectUtil.isNotNull(queryProductAuditDto.getStartTime()))
                        .and(PRODUCT_AUDIT_PO.CREATE_TIME.le(queryProductAuditDto.getEndTime()).when(ObjectUtil.isNotNull(queryProductAuditDto.getEndTime()))));
        Page<ProductAuditPo> flexPage = PageUtil.toFlexPage(queryProductAuditDto);
        Page<ProductAuditPo> page = this.page(flexPage, queryWrapper);
        PageResult<ProductAuditVo> pageResult = PageUtil.toPageResult(page, ProductAuditVo.class);
        List<ProductAuditVo> data = pageResult.getData();
        for (ProductAuditVo productAuditVo : data) {
            String type = productAuditVo.getType();
            switch (type) {
                case "0":
                    // spu
                    ProductSpuPo productSpuPo = productSpuService.getById(productAuditVo.getBizId());
                    if (ObjectUtil.isNotNull(productSpuPo)) {
                        queryWrapper = QueryWrapper.create()
                                .select(PRODUCT_SPU_ATTRIBUTE_PO.NAME)
                                .from(PRODUCT_SPU_ATTRIBUTE_PO)
                                .where(PRODUCT_SPU_ATTRIBUTE_PO.SPU_ID.eq(productSpuPo.getId()));
                        List<String> attribute = productSpuAttributeService.listAs(queryWrapper, String.class);
                        Map<String, Object> map = new HashMap<>();
                        map.put("spu", productSpuPo);
                        map.put("attribute", attribute);
                        productAuditVo.setAuditData(map);
                    }
                    break;
                case "1":
                    // sku
                    ProductSkuPo productSkuPo = productSkuService.getById(productAuditVo.getBizId());
                    if (ObjectUtil.isNotNull(productSkuPo)) {
                        queryWrapper = QueryWrapper.create()
                                .select(PRODUCT_SKU_ATTRIBUTE_VALUE_PO.VALUE, PRODUCT_SPU_ATTRIBUTE_PO.NAME, PRODUCT_SKU_ATTRIBUTE_VALUE_PO.PIC)
                                .from(PRODUCT_SKU_ATTRIBUTE_VALUE_PO)
                                .leftJoin(PRODUCT_SPU_ATTRIBUTE_PO)
                                .on(PRODUCT_SKU_ATTRIBUTE_VALUE_PO.ATTRIBUTE_ID
                                        .eq(PRODUCT_SPU_ATTRIBUTE_PO.ID)
                                        .and(PRODUCT_SKU_ATTRIBUTE_VALUE_PO.SKU_ID.eq(productSkuPo.getId())));
                        List<?> attributeValueMap = productSkuAttributeValueService.listAs(queryWrapper, Map.class);
                        Map<String, Object> map = new HashMap<>();
                        map.put("sku", productSkuPo);
                        map.put("attributeValueMap", attributeValueMap);
                        productAuditVo.setAuditData(map);
                    }
                    break;
                case "2":
                    // store
                    ProductStorePo productStorePo = productStoreService.getById(productAuditVo.getBizId());
                    if (ObjectUtil.isNotNull(productStorePo)) {
                        productAuditVo.setAuditData(productStorePo);
                    }
                    break;
                default:
                    break;
            }
        }
        return pageResult;
    }

    @Override
    public Boolean auditResult(AuditResultDto auditResultDto) {
        ProductAuditPo productAuditPo = this.getById(auditResultDto.getId());
        if (ObjectUtil.isNull(productAuditPo)) {
            ExceptionUtil.castServiceException(AUDIT_NOT_EXISTS);
        }
        String result = auditResultDto.getResult();
        if (!"10".equals(result) && !"20".equals(result)) {
            ExceptionUtil.castServiceException(AUDIT_RESULT_NOT_VALID);
        }
        productAuditPo.setStatus(result);
        if ("20".equals(result)) {
            if (StrUtil.isBlank(auditResultDto.getFailMsg())) {
                ExceptionUtil.castServiceException(AUDIT_NOT_PASS_REASON_MUST_NOT_EMPTY);
            }
        }
        productAuditPo.setAuditTime(LocalDateTime.now());
        productAuditPo.setAuditUser(StpSystemUtil.getLoginIdAsString());
        return updateById(productAuditPo);
    }
}
