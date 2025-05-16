package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.dto.dict.AddDictTypeDto;
import cn.xk.xcode.entity.dto.dict.QueryDictDto;
import cn.xk.xcode.entity.dto.dict.UpdateDictTypeDto;
import cn.xk.xcode.entity.po.SystemDictDataPo;
import cn.xk.xcode.entity.vo.dict.SysDictDataVo;
import cn.xk.xcode.event.DictDataReloadPublisher;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.SystemDictDataService;
import cn.xk.xcode.utils.object.BeanUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.SystemDictTypePo;
import cn.xk.xcode.mapper.SystemDictTypeMapper;
import cn.xk.xcode.service.SystemDictTypeService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static cn.xk.xcode.config.DistributionCardSystemErrorCode.DICT_TYPE_ALREADY_EXISTS;
import static cn.xk.xcode.entity.def.SystemDictDataTableDef.SYSTEM_DICT_DATA_PO;
import static cn.xk.xcode.entity.def.SystemDictTypeTableDef.SYSTEM_DICT_TYPE_PO;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2025-05-09
 */
@Service
public class SystemDictTypeServiceImpl extends ServiceImpl<SystemDictTypeMapper, SystemDictTypePo> implements SystemDictTypeService {

    @Resource
    private SystemDictTypeMapper systemDictTypeMapper;

    @Resource
    @Lazy
    private SystemDictDataService systemDictDataService;

    @Resource
    private DictDataReloadPublisher dictDataReloadPublisher;

    @Override
    public List<SysDictDataVo> selectAllDictDate(QueryDictDto queryDictDto) {
        return systemDictTypeMapper.selectAllDictDate(queryDictDto);
    }

    @Override
    public Boolean addDictType(AddDictTypeDto addDictTypeDto) {
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(SYSTEM_DICT_TYPE_PO.TYPE.eq(addDictTypeDto.getType())))) {
            ExceptionUtil.castServiceException(DICT_TYPE_ALREADY_EXISTS, addDictTypeDto.getType());
        }
        dictDataReloadPublisher.publish();
        return this.save(BeanUtil.toBean(addDictTypeDto, SystemDictTypePo.class));
    }

    @Transactional
    @Override
    public Boolean delDictType(Long id) {
        SystemDictTypePo systemDictTypePo = this.getById(id);
        if (ObjectUtil.isNull(systemDictTypePo)) {
            return true;
        }
        this.removeById(id);
        systemDictDataService.remove(SYSTEM_DICT_DATA_PO.DICT_TYPE.eq(systemDictTypePo.getType()));
        dictDataReloadPublisher.publish();
        return true;
    }

    @Transactional
    @Override
    public Boolean updateDictType(UpdateDictTypeDto updateDictTypeDto) {
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(SYSTEM_DICT_TYPE_PO.TYPE.eq(updateDictTypeDto.getType())
                .and(SYSTEM_DICT_TYPE_PO.ID.ne(updateDictTypeDto.getId()))))) {
            ExceptionUtil.castServiceException(DICT_TYPE_ALREADY_EXISTS, updateDictTypeDto.getType());
        }
        SystemDictTypePo systemDictTypePo = this.getById(updateDictTypeDto.getId());
        if (systemDictTypePo.getType().equals(updateDictTypeDto.getType())) {
            dictDataReloadPublisher.publish();
            return this.updateById(BeanUtil.toBean(updateDictTypeDto, SystemDictTypePo.class));
        } else {
            this.updateById(BeanUtil.toBean(updateDictTypeDto, SystemDictTypePo.class));
            dictDataReloadPublisher.publish();
            return UpdateChain.of(SystemDictDataPo.class)
                    .set(SYSTEM_DICT_DATA_PO.DICT_TYPE, updateDictTypeDto.getType())
                    .where(SYSTEM_DICT_DATA_PO.DICT_TYPE.eq(systemDictTypePo.getType()))
                    .update();
        }
    }
}
