package cn.xk.xcode.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.core.StpMemberUtil;
import cn.xk.xcode.entity.dto.spu.AddSpuDto;
import cn.xk.xcode.entity.dto.spu.QuerySpuDto;
import cn.xk.xcode.entity.dto.spu.UpdateSpuDto;
import cn.xk.xcode.entity.po.ProductAuditPo;
import cn.xk.xcode.entity.po.ProductSpuAttributePo;
import cn.xk.xcode.entity.po.ProductStorePo;
import cn.xk.xcode.entity.vo.spu.ProductSpuVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.*;
import cn.xk.xcode.utils.PageUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.ProductSpuPo;
import cn.xk.xcode.mapper.ProductSpuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static cn.xk.xcode.config.DistributionCardProductErrorCode.*;
import static cn.xk.xcode.entity.def.ProductSkuTableDef.PRODUCT_SKU_PO;
import static cn.xk.xcode.entity.def.ProductSpuAttributeTableDef.PRODUCT_SPU_ATTRIBUTE_PO;
import static cn.xk.xcode.entity.def.ProductSpuTableDef.PRODUCT_SPU_PO;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2025-05-30
 */
@Service
public class ProductSpuServiceImpl extends ServiceImpl<ProductSpuMapper, ProductSpuPo> implements ProductSpuService {

    @Resource
    private SensitiveWordBs sensitiveWordBs;

    @Resource
    private ProductAuditService productAuditService;

    @Resource
    private ProductSpuAttributeService productSpuAttributeService;

    @Resource
    private ProductStoreService productStoreService;

    @Resource
    private ProductSkuService productSkuService;

    @Transactional
    @Override
    public Boolean addProductSpu(AddSpuDto addSpuDto) {
        ProductStorePo productStorePo = productStoreService.getById(addSpuDto.getStoreId());
        if (Objects.isNull(productStorePo)) {
            ExceptionUtil.castServiceException(STORE_NOT_EXISTS);
        }
        if (CommonStatusEnum.isDisable(productStorePo.getStatus())) {
            ExceptionUtil.castServiceException(STORE_IS_DISABLED);
        }
        if (!"10".equals(productStorePo.getAuditStatus())) {
            ExceptionUtil.castServiceException(STORE_NOT_IN_AUDIT_SUCCESS_NOT_SUPPORT_ADD_SPU);
        }
        String name = addSpuDto.getName();
        if (sensitiveWordBs.contains(name)) {
            ExceptionUtil.castServiceException(SPU_NAME_CONTAINS_SENSITIVE_WORDS);
        }
        String introduction = addSpuDto.getIntroduction();
        if (sensitiveWordBs.contains(introduction)) {
            ExceptionUtil.castServiceException(SPU_INTRODUCTION_CONTAINS_SENSITIVE_WORDS);
        }
        String keywords = addSpuDto.getKeywords();
        if (sensitiveWordBs.contains(keywords)) {
            ExceptionUtil.castServiceException(SPU_KEYWORDS_CONTAINS_SENSITIVE_WORDS);
        }
        String description = addSpuDto.getDescription();
        if (sensitiveWordBs.contains(description)) {
            ExceptionUtil.castServiceException(SPU_DESCRIPTION_CONTAINS_SENSITIVE_WORDS);
        }
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(PRODUCT_SPU_PO.NAME.eq(name).and(PRODUCT_SPU_PO.STORE_ID.eq(addSpuDto.getStoreId()))))) {
            ExceptionUtil.castServiceException(SPU_HAS_EXISTS);
        }
        Set<String> productSpuAttributeList = addSpuDto.getProductSpuAttributeList();
        ProductSpuPo productSpuPo = new ProductSpuPo();
        BeanUtil.copyProperties(addSpuDto, productSpuPo);
        productSpuPo.setAuditStatus("00");
        productSpuPo.setStatus("1");
        save(productSpuPo);
        productSpuPo.setSort(Math.toIntExact(productSpuPo.getId()));
        save(productSpuPo);
        List<ProductSpuAttributePo> list = new ArrayList<>();
        for (String attribute : productSpuAttributeList) {
            if (sensitiveWordBs.contains(attribute)) {
                ExceptionUtil.castServiceException(SPU_ATTRIBUTE_CONTAINS_SENSITIVE_WORDS);
            }
            ProductSpuAttributePo productSpuAttributePo = new ProductSpuAttributePo();
            productSpuAttributePo.setName(attribute);
            productSpuAttributePo.setSpuId(productSpuPo.getId());
            list.add(productSpuAttributePo);
        }
        productSpuAttributeService.saveBatch(list);
        ProductAuditPo productAuditPo = new ProductAuditPo();
        productAuditPo.setBizId(productSpuPo.getId());
        productAuditPo.setStatus("00");
        productAuditPo.setType("0");
        productAuditPo.setCreateUser(StpMemberUtil.getLoginIdAsString());
        return productAuditService.save(productAuditPo);
    }

    @Override
    public Boolean updateProductSpu(UpdateSpuDto updateSpuDto) {
        ProductSpuPo productSpuPo = getById(updateSpuDto.getId());
        if (ObjectUtil.isNull(productSpuPo)) {
            ExceptionUtil.castServiceException(SPU_NOT_EXISTS);
        }
        if (CommonStatusEnum.isDisable(productSpuPo.getStatus())) {
            ExceptionUtil.castServiceException(SPU_IS_DISABLED);
        }
        if (!"10".equals(productSpuPo.getAuditStatus())) {
            ExceptionUtil.castServiceException(SPU_NOT_IN_AUDIT_SUCCESS_NOT_SUPPORT_UPDATE_SPU);
        }
        Long storeId = productSpuPo.getStoreId();
        ProductStorePo productStorePo = productStoreService.getById(storeId);
        if (ObjectUtil.isNull(productStorePo)) {
            ExceptionUtil.castServiceException(STORE_NOT_EXISTS);
        }
        if (CommonStatusEnum.isDisable(productStorePo.getStatus())) {
            ExceptionUtil.castServiceException(STORE_IS_DISABLED);
        }
        if (!"10".equals(productStorePo.getAuditStatus())) {
            ExceptionUtil.castServiceException(STORE_NOT_IN_AUDIT_SUCCESS_NOT_SUPPORT_ADD_SPU);
        }
        String name = updateSpuDto.getName();
        if (sensitiveWordBs.contains(name)) {
            ExceptionUtil.castServiceException(SPU_NAME_CONTAINS_SENSITIVE_WORDS);
        }
        String introduction = updateSpuDto.getIntroduction();
        if (sensitiveWordBs.contains(introduction)) {
            ExceptionUtil.castServiceException(SPU_INTRODUCTION_CONTAINS_SENSITIVE_WORDS);
        }
        String keywords = updateSpuDto.getKeywords();
        if (sensitiveWordBs.contains(keywords)) {
            ExceptionUtil.castServiceException(SPU_KEYWORDS_CONTAINS_SENSITIVE_WORDS);
        }
        String description = updateSpuDto.getDescription();
        if (sensitiveWordBs.contains(description)) {
            ExceptionUtil.castServiceException(SPU_DESCRIPTION_CONTAINS_SENSITIVE_WORDS);
        }
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(PRODUCT_SPU_PO.NAME.eq(name)
                .and(PRODUCT_SPU_PO.STORE_ID.eq(storeId))
                .and(PRODUCT_SPU_PO.ID.ne(updateSpuDto.getId()))))) {
            ExceptionUtil.castServiceException(SPU_HAS_EXISTS);
        }
        Set<String> productSpuAttributeList = updateSpuDto.getProductSpuAttributeList();
        List<ProductSpuAttributePo> list = new ArrayList<>();
        productSpuAttributeService.remove(PRODUCT_SPU_ATTRIBUTE_PO.SPU_ID.eq(productSpuPo.getId()));
        for (String attribute : productSpuAttributeList) {
            if (sensitiveWordBs.contains(attribute)) {
                ExceptionUtil.castServiceException(SPU_ATTRIBUTE_CONTAINS_SENSITIVE_WORDS);
            }
            ProductSpuAttributePo productSpuAttributePo = new ProductSpuAttributePo();
            productSpuAttributePo.setName(attribute);
            productSpuAttributePo.setSpuId(productSpuPo.getId());
            list.add(productSpuAttributePo);
        }
        productSpuAttributeService.saveBatch(list);
        BeanUtil.copyProperties(updateSpuDto, productSpuPo);
        productSpuPo.setAuditStatus("00");
        productSpuPo.setStatus("1");
        updateById(productSpuPo);
        ProductAuditPo productAuditPo = new ProductAuditPo();
        productAuditPo.setBizId(productSpuPo.getId());
        productAuditPo.setStatus("00");
        productAuditPo.setType("0");
        productAuditPo.setCreateUser(StpMemberUtil.getLoginIdAsString());
        return productAuditService.save(productAuditPo);
    }

    @Override
    public Boolean delProductSpu(Long spuId) {
        ProductSpuPo productSpuPo = getById(spuId);
        if (ObjectUtil.isNull(productSpuPo)) {
            return true;
        }
        if ("00".equals(productSpuPo.getAuditStatus())) {
            ExceptionUtil.castServiceException(SPU_IN_AUDIT_NOT_SUPPORT_DELETE);
        }
        if (productSkuService.exists(PRODUCT_SKU_PO.SPU_ID.eq(spuId).and(PRODUCT_SKU_PO.STATUS.eq("0")))) {
            ExceptionUtil.castServiceException(SPU_HAS_SKU);
        }
        return this.removeById(spuId);
    }

    @Override
    public PageResult<ProductSpuVo> queryProductSpu(QuerySpuDto querySpuDto) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(PRODUCT_SPU_PO.ALL_COLUMNS)
                .from(PRODUCT_SPU_PO)
                .where(PRODUCT_SPU_PO.STORE_ID.eq(querySpuDto.getStoreId()))
                .and(PRODUCT_SPU_PO.NAME.like(querySpuDto.getName()).when(StrUtil.isNotBlank(querySpuDto.getName())))
                .and(PRODUCT_SPU_PO.KEYWORDS.like(querySpuDto.getKeywords()).when(StrUtil.isNotBlank(querySpuDto.getKeywords())))
                .and(PRODUCT_SPU_PO.RECOMMEND_HOT.eq(querySpuDto.getRecommendHot()).when(StrUtil.isNotBlank(querySpuDto.getRecommendHot())))
                .and(PRODUCT_SPU_PO.RECOMMEND_BENEFIT.eq(querySpuDto.getRecommendBenefit()).when(StrUtil.isNotBlank(querySpuDto.getRecommendBenefit())))
                .and(PRODUCT_SPU_PO.RECOMMEND_BEST.eq(querySpuDto.getRecommendBest()).when(StrUtil.isNotBlank(querySpuDto.getRecommendBest())))
                .and(PRODUCT_SPU_PO.RECOMMEND_NEW.eq(querySpuDto.getRecommendNew()).when(StrUtil.isNotBlank(querySpuDto.getRecommendNew())))
                .and(PRODUCT_SPU_PO.RECOMMEND_GOOD.eq(querySpuDto.getRecommendGood()).when(StrUtil.isNotBlank(querySpuDto.getRecommendGood())))
                .orderBy(PRODUCT_SPU_PO.SORT, true);
        Page<ProductSpuPo> flexPage = PageUtil.toFlexPage(querySpuDto);
        Page<ProductSpuPo> page = this.page(flexPage, queryWrapper);
        return PageUtil.toPageResult(page, ProductSpuVo.class);
    }
}
