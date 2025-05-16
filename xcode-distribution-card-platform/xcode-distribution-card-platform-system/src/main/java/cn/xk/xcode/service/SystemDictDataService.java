package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.dict.AddDictDataDto;
import cn.xk.xcode.entity.dto.dict.UpdateDictDataDto;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.SystemDictDataPo;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2025-05-09
 */
public interface SystemDictDataService extends IService<SystemDictDataPo> {

    Boolean addDictData(AddDictDataDto addDictDataDto);

    Boolean delDictData(Long id);

    Boolean updateDictData(UpdateDictDataDto updateDictDataDto);
}
