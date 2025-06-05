package cn.xk.xcode.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.core.StpMemberUtil;
import cn.xk.xcode.entity.dto.sku.AddSkuDto;
import cn.xk.xcode.entity.dto.sku.UpdateSkuDto;
import cn.xk.xcode.entity.po.ProductAuditPo;
import cn.xk.xcode.entity.po.ProductSkuAttributeValuePo;
import cn.xk.xcode.entity.po.ProductSpuPo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.*;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.ProductSkuPo;
import cn.xk.xcode.mapper.ProductSkuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.xk.xcode.config.DistributionCardProductErrorCode.*;
import static cn.xk.xcode.entity.def.ProductSkuAttributeValueTableDef.PRODUCT_SKU_ATTRIBUTE_VALUE_PO;
import static cn.xk.xcode.entity.def.ProductSkuTableDef.PRODUCT_SKU_PO;
import static cn.xk.xcode.entity.def.ProductSpuAttributeTableDef.PRODUCT_SPU_ATTRIBUTE_PO;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2025-05-30
 */
@Service
public class ProductSkuServiceImpl extends ServiceImpl<ProductSkuMapper, ProductSkuPo> implements ProductSkuService {

    @Resource
    private ProductSpuService productSpuService;

    @Resource
    private ProductSpuAttributeService productSpuAttributeService;

    @Resource
    private SensitiveWordBs sensitiveWordBs;

    @Resource
    private ProductSkuAttributeValueService productSkuAttributeValueService;

    @Resource
    private ProductAuditService productAuditService;

    @Transactional
    @Override
    public Boolean addProductSku(AddSkuDto addSkuDto) {
        Long spuId = addSkuDto.getSpuId();
        ProductSpuPo productSpuPo = productSpuService.getById(spuId);
        if (ObjectUtil.isNull(productSpuPo)) {
            ExceptionUtil.castServiceException(SPU_NOT_EXISTS);
        }
        if (CommonStatusEnum.isDisable(productSpuPo.getStatus())) {
            ExceptionUtil.castServiceException(SPU_IS_DISABLED);
        }
        if (!"10".equals(productSpuPo.getAuditStatus())) {
            ExceptionUtil.castServiceException(SPU_NOT_IN_AUDIT_SUCCESS_NOT_SUPPORT_ADD_SKU);
        }
        String skuCode = addSkuDto.getSkuCode();
        if (this.exists(PRODUCT_SKU_PO.SKU_CODE.eq(skuCode).and(PRODUCT_SKU_PO.SPU_ID.eq(spuId)))) {
            ExceptionUtil.castServiceException(SKU_CODE_HAS_EXISTS);
        }
        if (ObjectUtil.isNotNull(addSkuDto.getPromotionPrice())){
            if (addSkuDto.getPromotionPrice().compareTo(addSkuDto.getPrice()) > 0){
                ExceptionUtil.castServiceException(SKU_PROMOTION_PRICE_CANNOT_GREATER_THAN_PRICE);
            }
        }
        if (addSkuDto.getLowStock() > addSkuDto.getStock()){
            ExceptionUtil.castServiceException(SKU_LOW_STOCK_CANNOT_GREATER_THAN_STOCK);
        }
        List<AddSkuDto.ProductSkuAttributeValue> productSkuAttributeValues = addSkuDto.getProductSkuAttributeValues();
        int size = productSkuAttributeValues.size();
        long count = productSpuAttributeService.count(PRODUCT_SPU_ATTRIBUTE_PO.SPU_ID.eq(spuId));
        if (size != count) {
            ExceptionUtil.castServiceException(SKU_ATTRIBUTE_VALUE_NOT_EQUAL_SPU_ATTRIBUTE_NUM);
        }
        ProductSkuPo productSkuPo = new ProductSkuPo();
        BeanUtil.copyProperties(addSkuDto, productSkuPo);
        productSkuPo.setSale(0);
        productSkuPo.setStatus(CommonStatusEnum.DISABLE.getStatus());
        productSkuPo.setAuditStatus("00");
        if (ObjectUtil.isNull(addSkuDto.getPromotionPrice())) {
            productSkuPo.setPromotionPrice(addSkuDto.getPrice());
        }
        save(productSkuPo);
        List<ProductSkuAttributeValuePo> list = new ArrayList<>();
        for (AddSkuDto.ProductSkuAttributeValue productSkuAttributeValue : productSkuAttributeValues) {
            if (sensitiveWordBs.contains(productSkuAttributeValue.getValue())) {
                ExceptionUtil.castServiceException(SKU_ATTRIBUTE_VALUE_CONTAINS_SENSITIVE_WORDS);
            }
            ProductSkuAttributeValuePo productSkuAttributeValuePo = new ProductSkuAttributeValuePo();
            productSkuAttributeValuePo.setSkuId(productSkuPo.getId());
            productSkuAttributeValuePo.setAttributeId(productSkuAttributeValue.getAttributeId());
            productSkuAttributeValuePo.setValue(productSkuAttributeValue.getValue());
            productSkuAttributeValuePo.setPic(productSkuAttributeValue.getPic());
        }
        productSkuAttributeValueService.saveBatch(list);
        ProductAuditPo productAuditPo = new ProductAuditPo();
        productAuditPo.setBizId(productSkuPo.getId());
        productAuditPo.setStatus("00");
        productAuditPo.setType("1");
        productAuditPo.setCreateUser(StpMemberUtil.getLoginIdAsString());
        return productAuditService.save(productAuditPo);
    }

    @Override
    public Boolean updateProductSku(UpdateSkuDto updateSkuDto) {
        ProductSkuPo productSkuPo = getById(updateSkuDto.getId());
        if (ObjectUtil.isNull(productSkuPo)) {
            ExceptionUtil.castServiceException(SKU_NOT_EXISTS);
        }
        if (!"10".equals(productSkuPo.getAuditStatus())) {
            ExceptionUtil.castServiceException(SKU_NOT_IN_AUDIT_SUCCESS_NOT_SUPPORT_UPDATE);
        }
        if (CommonStatusEnum.isDisable(productSkuPo.getStatus())) {
            ExceptionUtil.castServiceException(SKU_IS_DISABLED);
        }
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(PRODUCT_SKU_PO.SKU_CODE.eq(updateSkuDto.getSkuCode()).and(PRODUCT_SKU_PO.ID.ne(updateSkuDto.getId()))))) {
            ExceptionUtil.castServiceException(SKU_CODE_HAS_EXISTS);
        }
        if (updateSkuDto.getPromotionPrice().compareTo(updateSkuDto.getPrice()) > 0){
            ExceptionUtil.castServiceException(SKU_PROMOTION_PRICE_CANNOT_GREATER_THAN_PRICE);
        }
        if (updateSkuDto.getLowStock() > updateSkuDto.getStock()){
            ExceptionUtil.castServiceException(SKU_LOW_STOCK_CANNOT_GREATER_THAN_STOCK);
        }
        BeanUtil.copyProperties(updateSkuDto, productSkuPo);
        productSkuAttributeValueService.remove(PRODUCT_SKU_ATTRIBUTE_VALUE_PO.SKU_ID.eq(productSkuPo.getId()));
        List<ProductSkuAttributeValuePo> list = new ArrayList<>();
        for (AddSkuDto.ProductSkuAttributeValue productSkuAttributeValue : updateSkuDto.getProductSkuAttributeValues()) {
            if (sensitiveWordBs.contains(productSkuAttributeValue.getValue())) {
                ExceptionUtil.castServiceException(SKU_ATTRIBUTE_VALUE_CONTAINS_SENSITIVE_WORDS);
            }
            ProductSkuAttributeValuePo productSkuAttributeValuePo = new ProductSkuAttributeValuePo();
            productSkuAttributeValuePo.setSkuId(productSkuPo.getId());
            productSkuAttributeValuePo.setAttributeId(productSkuAttributeValue.getAttributeId());
            productSkuAttributeValuePo.setValue(productSkuAttributeValue.getValue());
            productSkuAttributeValuePo.setPic(productSkuAttributeValue.getPic());
        }
        productSkuAttributeValueService.saveBatch(list);
        productSkuPo.setAuditStatus("00");
        productSkuPo.setStatus(CommonStatusEnum.DISABLE.getStatus());
        updateById(productSkuPo);
        ProductAuditPo productAuditPo = new ProductAuditPo();
        productAuditPo.setBizId(productSkuPo.getId());
        productAuditPo.setStatus("00");
        productAuditPo.setType("1");
        productAuditPo.setCreateUser(StpMemberUtil.getLoginIdAsString());
        return productAuditService.save(productAuditPo);
    }

    @Override
    public Boolean delProductSku(Long skuId) {
        ProductSkuPo productSkuPo = getById(skuId);
        if (ObjectUtil.isNull(productSkuPo)) {
            return true;
        }
        if ("00".equals(productSkuPo.getAuditStatus())){
            ExceptionUtil.castServiceException(SKU_IN_AUDIT_NOT_SUPPORT_DELETE);
        }
        return null;
    }
}
