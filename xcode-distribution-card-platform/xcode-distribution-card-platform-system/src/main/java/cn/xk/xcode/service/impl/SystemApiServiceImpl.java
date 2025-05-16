package cn.xk.xcode.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.entity.dto.api.AddSystemApiDto;
import cn.xk.xcode.entity.dto.api.BindRoleApiDto;
import cn.xk.xcode.entity.dto.api.QuerySystemApiDto;
import cn.xk.xcode.entity.dto.api.UpdateSystemApiDto;
import cn.xk.xcode.entity.po.SystemRoleApiPo;
import cn.xk.xcode.entity.vo.api.SystemApiVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.SystemRoleApiService;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.SystemApiPo;
import cn.xk.xcode.mapper.SystemApiMapper;
import cn.xk.xcode.service.SystemApiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.xk.xcode.config.DistributionCardSystemErrorCode.*;
import static cn.xk.xcode.entity.def.SystemApiTableDef.SYSTEM_API_PO;
import static cn.xk.xcode.entity.def.SystemRoleApiTableDef.SYSTEM_ROLE_API_PO;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2025-05-09
 */
@Service
public class SystemApiServiceImpl extends ServiceImpl<SystemApiMapper, SystemApiPo> implements SystemApiService {

    @Resource
    private SystemRoleApiService systemRoleApiService;

    @Override
    public Map<String, List<SystemApiVo>> selectAllApi(QuerySystemApiDto querySystemApiDto) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SYSTEM_API_PO.ALL_COLUMNS)
                .from(SYSTEM_API_PO)
                .where("1=1")
                .and(SYSTEM_API_PO.PRODUCT_NAME.eq(querySystemApiDto.getProductName()).when(StrUtil.isNotBlank(querySystemApiDto.getProductName())))
                .and(SYSTEM_API_PO.API_CODE.likeRight(querySystemApiDto.getApiCode()).when(StrUtil.isNotBlank(querySystemApiDto.getApiCode())))
                .and(SYSTEM_API_PO.API_PATH.likeRight(querySystemApiDto.getApiPath()).when(StrUtil.isNotBlank(querySystemApiDto.getApiPath())));
        List<SystemApiVo> systemApiVos = this.listAs(queryWrapper, SystemApiVo.class);
        if (CollectionUtil.isEmpty(systemApiVos)) {
            return Collections.emptyMap();
        } else {
            return CollectionUtil.groupByKey(systemApiVos, SystemApiVo::getProductName);
        }
    }

    @Override
    public Boolean addSystemApi(AddSystemApiDto addSystemApiDto) {
        if (this.exists(SYSTEM_API_PO.API_CODE.eq(addSystemApiDto.getApiCode()).and(SYSTEM_API_PO.PRODUCT_NAME.eq(addSystemApiDto.getProductName())))) {
            ExceptionUtil.castServiceException(SERVICE_API_CODE_ALREADY_EXISTS, addSystemApiDto.getProductName(), addSystemApiDto.getApiCode());
        }
        if (this.exists(SYSTEM_API_PO.API_PATH.eq(addSystemApiDto.getApiPath()).and(SYSTEM_API_PO.PRODUCT_NAME.eq(addSystemApiDto.getProductName())))) {
            ExceptionUtil.castServiceException(SERVICE_API_PATH_ALREADY_EXISTS, addSystemApiDto.getProductName(), addSystemApiDto.getApiPath());
        }
        return this.save(BeanUtil.toBean(addSystemApiDto, SystemApiPo.class));
    }

    @Override
    public Boolean delSystemApi(Integer id) {
        if (systemRoleApiService.exists(SYSTEM_ROLE_API_PO.API_ID.eq(id))) {
            ExceptionUtil.castServiceException(API_HAS_BINDING_ROLE);
        }
        return this.removeById(id);
    }

    @Override
    public Boolean updateSystemApi(UpdateSystemApiDto updateSystemApiDto) {
        if (this.exists(SYSTEM_API_PO.API_CODE.eq(updateSystemApiDto.getApiCode()).and(SYSTEM_API_PO.PRODUCT_NAME.eq(updateSystemApiDto.getProductName()).and(SYSTEM_API_PO.ID.ne(updateSystemApiDto.getId()))))) {
            ExceptionUtil.castServiceException(SERVICE_API_CODE_ALREADY_EXISTS, updateSystemApiDto.getProductName(), updateSystemApiDto.getApiCode());
        }
        if (this.exists(SYSTEM_API_PO.API_PATH.eq(updateSystemApiDto.getApiPath()).and(SYSTEM_API_PO.PRODUCT_NAME.eq(updateSystemApiDto.getProductName()).and(SYSTEM_API_PO.ID.ne(updateSystemApiDto.getId()))))) {
            ExceptionUtil.castServiceException(SERVICE_API_PATH_ALREADY_EXISTS, updateSystemApiDto.getProductName(), updateSystemApiDto.getApiPath());
        }
        return this.updateById(BeanUtil.toBean(updateSystemApiDto, SystemApiPo.class));
    }

    @Transactional
    @Override
    public Boolean bindRoleApi(BindRoleApiDto bindRoleApiDto) {
        if (CollectionUtil.isEmpty(bindRoleApiDto.getApiIdList())) {
            return systemRoleApiService.remove(SYSTEM_ROLE_API_PO.ROLE_ID.eq(bindRoleApiDto.getRoleId()));
        }
        systemRoleApiService.remove(SYSTEM_ROLE_API_PO.ROLE_ID.eq(bindRoleApiDto.getRoleId()));
        List<SystemRoleApiPo> collect = bindRoleApiDto.getApiIdList().stream().map(o -> {
            SystemRoleApiPo systemRoleApiPo = new SystemRoleApiPo();
            systemRoleApiPo.setRoleId(bindRoleApiDto.getRoleId());
            systemRoleApiPo.setApiId(o);
            return systemRoleApiPo;
        }).collect(Collectors.toList());
        return systemRoleApiService.saveBatch(collect);
    }
}
