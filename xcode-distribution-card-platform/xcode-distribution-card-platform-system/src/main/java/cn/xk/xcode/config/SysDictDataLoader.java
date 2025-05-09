package cn.xk.xcode.config;

import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.entity.DictDataEntity;
import cn.xk.xcode.entity.dto.dict.QueryDictDto;
import cn.xk.xcode.entity.po.SystemDictDataPo;
import cn.xk.xcode.entity.vo.dict.SysDictDataResultVo;
import cn.xk.xcode.entity.vo.dict.SysDictDataVo;
import cn.xk.xcode.handler.DataSourceDictLoader;
import cn.xk.xcode.service.SystemDictTypeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/5/9 9:10
 * @Version 1.0.0
 * @Description SysDictDataLoader
 **/
@Component
public class SysDictDataLoader implements DataSourceDictLoader {

    @Resource
    private SystemDictTypeService systemDictTypeService;

    @Override
    public List<DictDataEntity> loadDataBaseDict() {
        List<SysDictDataVo> list = systemDictTypeService.selectAllDictDate(new QueryDictDto());
        List<DictDataEntity> dictDataEntityList = new ArrayList<>();
        for (SysDictDataVo sysDictDataVo : list) {
            if (CommonStatusEnum.isEnable(sysDictDataVo.getStatus())) {
                for (SysDictDataResultVo sysDictDataResultVo : sysDictDataVo.getDictDataList()) {
                    if (CommonStatusEnum.isEnable(sysDictDataResultVo.getStatus())) {
                        DictDataEntity dictDataEntity = new DictDataEntity();
                        dictDataEntity.setCode(sysDictDataResultVo.getValue());
                        dictDataEntity.setName(sysDictDataResultVo.getLabel());
                        dictDataEntity.setDictType(sysDictDataResultVo.getDictType());
                        dictDataEntity.setDesc(sysDictDataResultVo.getColor());
                    }
                }
            }
        }
        return dictDataEntityList;
    }
}
