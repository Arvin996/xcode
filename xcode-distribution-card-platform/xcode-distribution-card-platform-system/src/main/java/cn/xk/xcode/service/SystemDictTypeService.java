package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.dict.AddDictTypeDto;
import cn.xk.xcode.entity.dto.dict.QueryDictDto;
import cn.xk.xcode.entity.dto.dict.UpdateDictTypeDto;
import cn.xk.xcode.entity.vo.dict.SysDictDataVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.SystemDictTypePo;

import java.util.List;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2025-05-09
 */
public interface SystemDictTypeService extends IService<SystemDictTypePo> {

    List<SysDictDataVo> selectAllDictDate(QueryDictDto queryDictDto);

    Boolean addDictType(AddDictTypeDto addDictTypeDto);

    Boolean delDictType(Long id);

    Boolean updateDictType(UpdateDictTypeDto updateDictTypeDto);
}
