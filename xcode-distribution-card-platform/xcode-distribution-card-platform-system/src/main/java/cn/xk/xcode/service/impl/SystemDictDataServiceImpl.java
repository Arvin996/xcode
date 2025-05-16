package cn.xk.xcode.service.impl;

import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.entity.dto.dict.AddDictDataDto;
import cn.xk.xcode.entity.dto.dict.UpdateDictDataDto;
import cn.xk.xcode.entity.po.SystemDictTypePo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.SystemDictTypeService;
import cn.xk.xcode.utils.object.BeanUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.SystemDictDataPo;
import cn.xk.xcode.mapper.SystemDictDataMapper;
import cn.xk.xcode.service.SystemDictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static cn.xk.xcode.config.DistributionCardSystemErrorCode.*;
import static cn.xk.xcode.entity.def.SystemDictDataTableDef.SYSTEM_DICT_DATA_PO;
import static cn.xk.xcode.entity.def.SystemDictTypeTableDef.SYSTEM_DICT_TYPE_PO;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2025-05-09
 */
@Service
public class SystemDictDataServiceImpl extends ServiceImpl<SystemDictDataMapper, SystemDictDataPo> implements SystemDictDataService {

    @Resource
    private SystemDictTypeService systemDictTypeService;

    @Override
    public Boolean addDictData(AddDictDataDto addDictDataDto) {
        SystemDictTypePo dictTypePo = systemDictTypeService.getOne(SYSTEM_DICT_TYPE_PO.TYPE.eq(addDictDataDto.getDictType()));
        ObjectUtil.ifNullCastServiceException(dictTypePo, DICT_TYPE_NOT_EXISTS, addDictDataDto.getDictType());
        if (CommonStatusEnum.isDisable(dictTypePo.getStatus())) {
            ExceptionUtil.castServiceException(DICT_TYPE_IS_DISABLED, addDictDataDto.getDictType());
        }
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(SYSTEM_DICT_DATA_PO.DICT_TYPE.eq(addDictDataDto.getDictType()).and(SYSTEM_DICT_DATA_PO.VALUE.eq(addDictDataDto.getValue()))))) {
            ExceptionUtil.castServiceException(DICT_TYPE_DATA_ALREADY_EXISTS, addDictDataDto.getDictType(), addDictDataDto.getValue());
        }
        SystemDictDataPo systemDictDataPo = BeanUtil.toBean(addDictDataDto, SystemDictDataPo.class);
        this.save(systemDictDataPo);
        if (ObjectUtil.isNull(addDictDataDto.getSort())) {
            systemDictDataPo.setSort(Math.toIntExact(systemDictDataPo.getId()));
        }
        return this.updateById(systemDictDataPo);
    }

    @Override
    public Boolean delDictData(Long id) {
        SystemDictDataPo systemDictDataPo = this.getById(id);
        if (ObjectUtil.isNull(systemDictDataPo)) {
            return true;
        }
        return this.removeById(id);
    }

    @Override
    public Boolean updateDictData(UpdateDictDataDto updateDictDataDto) {
        SystemDictDataPo systemDictDataPo = this.getById(updateDictDataDto.getId());
        if (ObjectUtil.isNull(systemDictDataPo)) {
            ExceptionUtil.castServiceException(DICT_DATA_NOT_EXISTS);
        }
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(SYSTEM_DICT_DATA_PO.DICT_TYPE
                .eq(systemDictDataPo.getDictType())
                .and(SYSTEM_DICT_DATA_PO.VALUE.eq(updateDictDataDto.getValue()))
                .and(SYSTEM_DICT_DATA_PO.ID.ne(updateDictDataDto.getId()))))) {
            ExceptionUtil.castServiceException(DICT_TYPE_DATA_ALREADY_EXISTS, systemDictDataPo.getDictType(), updateDictDataDto.getValue());
        }
        return this.updateById(BeanUtil.toBean(updateDictDataDto, SystemDictDataPo.class));
    }
}
