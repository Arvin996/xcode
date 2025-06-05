package cn.xk.xcode.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.core.StpMemberUtil;
import cn.xk.xcode.entity.dto.store.AddStoreDto;
import cn.xk.xcode.entity.dto.store.QueryStoreDto;
import cn.xk.xcode.entity.dto.store.UpdateStoreDto;
import cn.xk.xcode.entity.po.ProductAuditPo;
import cn.xk.xcode.entity.po.ProductMerchantPo;
import cn.xk.xcode.entity.vo.store.ProductStoreVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.ProductAuditService;
import cn.xk.xcode.service.ProductMerchantService;
import cn.xk.xcode.service.ProductSpuService;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.ProductStorePo;
import cn.xk.xcode.mapper.ProductStoreMapper;
import cn.xk.xcode.service.ProductStoreService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Collections;
import java.util.List;

import static cn.xk.xcode.config.DistributionCardProductErrorCode.*;
import static cn.xk.xcode.entity.def.ProductSpuTableDef.PRODUCT_SPU_PO;
import static cn.xk.xcode.entity.def.ProductStoreTableDef.PRODUCT_STORE_PO;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2025-05-30
 */
@Service
public class ProductStoreServiceImpl extends ServiceImpl<ProductStoreMapper, ProductStorePo> implements ProductStoreService {

    @Resource
    private ProductAuditService productAuditService;

    @Resource
    private ProductMerchantService productMerchantService;

    @Resource
    private SensitiveWordBs sensitiveWordBs;

    @Resource
    private ProductSpuService productSpuService;

    @Override
    public Boolean addProductStore(AddStoreDto addStoreDto) {
        if (sensitiveWordBs.contains(addStoreDto.getName())) {
            ExceptionUtil.castServiceException(STORE_NAME_CONTAINS_SENSITIVE_WORDS);
        }
        if (sensitiveWordBs.contains(addStoreDto.getDesc())) {
            ExceptionUtil.castServiceException(STORE_DESC_CONTAINS_SENSITIVE_WORDS);
        }
        if (this.exists(PRODUCT_STORE_PO.NAME.eq(addStoreDto.getName())
                .and(PRODUCT_STORE_PO.MERCHANT_ID.eq(addStoreDto.getMerchantId())))) {
            ExceptionUtil.castServiceException(STORE_HAS_EXISTS);
        }
        Long merchantId = addStoreDto.getMerchantId();
        if (this.count(PRODUCT_STORE_PO.MERCHANT_ID.eq(merchantId)) >= MAX_STORE_NUM) {
            ExceptionUtil.castServiceException(STORE_HAS_MAX_NUM);
        }
        ProductMerchantPo productMerchantPo = productMerchantService.getById(merchantId);
        if (ObjectUtil.isNull(productMerchantPo)) {
            ExceptionUtil.castServiceException(MERCHANT_NOT_EXISTS);
        }
        if (CommonStatusEnum.isDisable(productMerchantPo.getStatus())) {
            ExceptionUtil.castServiceException(MERCHANT_IS_DISABLED);
        }
        if (StrUtil.isBlank(addStoreDto.getContactQq()) && StrUtil.isBlank(addStoreDto.getContactWx())) {
            ExceptionUtil.castServiceException(STORE_CONTACT_QQ_AND_WX_MUST_NOT_EMPTY);
        }
        ProductStorePo productStorePo = new ProductStorePo();
        BeanUtil.copyProperties(addStoreDto, productStorePo);
        productStorePo.setAuditStatus("00");
        productStorePo.setStatus("0");
        this.save(productStorePo);
        ProductAuditPo productAuditPo = new ProductAuditPo();
        productAuditPo.setBizId(productStorePo.getId());
        productAuditPo.setStatus("00");
        productAuditPo.setType("2");
        productAuditPo.setCreateUser(StpMemberUtil.getLoginId().toString());
        return productAuditService.save(productAuditPo);
    }

    @Override
    public Boolean updateProductStore(UpdateStoreDto updateStoreDto) {
        ProductStorePo productStorePo = getById(updateStoreDto.getId());
        if (ObjectUtil.isNull(productStorePo)) {
            ExceptionUtil.castServiceException(STORE_NOT_EXISTS);
        }
        String auditStatus = productStorePo.getAuditStatus();
        if ("00".equals(auditStatus)) {
            ExceptionUtil.castServiceException(STORE_IN_AUDIT_NOT_SUPPORT_UPDATE);
        }
        if (CommonStatusEnum.isDisable(productStorePo.getStatus())) {
            ExceptionUtil.castServiceException(STORE_IS_DISABLED);
        }
        if (sensitiveWordBs.contains(updateStoreDto.getDesc())) {
            ExceptionUtil.castServiceException(STORE_DESC_CONTAINS_SENSITIVE_WORDS);
        }
        if (StrUtil.isBlank(updateStoreDto.getContactQq()) && StrUtil.isBlank(updateStoreDto.getContactWx())) {
            ExceptionUtil.castServiceException(STORE_CONTACT_QQ_AND_WX_MUST_NOT_EMPTY);
        }
        productStorePo.setDesc(updateStoreDto.getDesc());
        productStorePo.setContactQq(updateStoreDto.getContactQq());
        productStorePo.setContactWx(updateStoreDto.getContactWx());
        return this.updateById(productStorePo);
    }

    @Override
    public Boolean delProductStore(Long storeId) {
        ProductStorePo productStorePo = getById(storeId);
        if (ObjectUtil.isNull(productStorePo)) {
            return true;
        }
        if ("00".equals(productStorePo.getAuditStatus())) {
            ExceptionUtil.castServiceException(STORE_IN_AUDIT_NOT_SUPPORT_DELETE);
        }
        if (productSpuService.exists(PRODUCT_SPU_PO.STORE_ID.eq(storeId).and(PRODUCT_SPU_PO.STATUS.eq("0")))) {
            ExceptionUtil.castServiceException(STORE_HAS_SPU);
        }
        return this.removeById(storeId);
    }

    @Override
    public List<ProductStoreVo> queryProductStores(QueryStoreDto queryStoreDto) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(PRODUCT_STORE_PO.ALL_COLUMNS)
                .from(PRODUCT_STORE_PO)
                .where(PRODUCT_STORE_PO.IS_DELETED.eq(0))
                .and(PRODUCT_STORE_PO.NAME.like(queryStoreDto.getName()).when(StrUtil.isNotBlank(queryStoreDto.getName())))
                .and(PRODUCT_STORE_PO.CLOSE_RATE.ge(queryStoreDto.getMinCloseRate()).when(ObjectUtil.isNotNull(queryStoreDto.getMinCloseRate())))
                .and(PRODUCT_STORE_PO.CLOSE_RATE.le(queryStoreDto.getMaxCloseRate()).when(ObjectUtil.isNotNull(queryStoreDto.getMaxCloseRate())))
                .and(PRODUCT_STORE_PO.SALE_COUNT.ge(queryStoreDto.getMinSaleCount()).when(ObjectUtil.isNotNull(queryStoreDto.getMinSaleCount())))
                .and(PRODUCT_STORE_PO.SALE_COUNT.le(queryStoreDto.getMaxSaleCount()).when(ObjectUtil.isNotNull(queryStoreDto.getMaxSaleCount())))
                .and(PRODUCT_STORE_PO.AUDIT_STATUS.eq(queryStoreDto.getAuditStatus()).when(StrUtil.isNotBlank(queryStoreDto.getAuditStatus())))
                .and(PRODUCT_STORE_PO.STATUS.eq(queryStoreDto.getStatus()).when(StrUtil.isNotBlank(queryStoreDto.getStatus())));
        return this.listAs(queryWrapper, ProductStoreVo.class);
    }
}
