package cn.xk.xcode.convert;

import cn.xk.xcode.dict.entity.DataTableDict;
import cn.xk.xcode.entity.dto.UpdateDictDto;
import cn.xk.xcode.entity.po.DictPo;
import cn.xk.xcode.entity.vo.DictVo;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/6/21 10:57
 * @Version 1.0
 * @Description DictConvert
 */
@Mapper(componentModel = "spring")
public interface DictConvert {

    DataTableDict dictDoToTableDict(DictPo dictPo);

    List<DataTableDict> dictDoToTableDict(List<DictPo> dictPoList);

    DictPo dictDtoToPo(UpdateDictDto dictDto);

    DictVo dictPoToVo(DictPo dictPo);

    List<DictVo> dictPoToVo(List<DictPo> dictPoList);
}
