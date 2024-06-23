package cn.xk.xcode.convert;

import cn.xk.xcode.dict.entity.DataTableDict;
import cn.xk.xcode.entity.dto.dict.UpdateDictDto;
import cn.xk.xcode.entity.po.DictPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/6/21 10:57
 * @Version 1.0
 * @Description DictConvert
 */
@Mapper(componentModel = "spring")
public interface DictConvert {


    @Mapping(target = "pad3", ignore = true)
    @Mapping(target = "pad2", ignore = true)
    @Mapping(target = "pad1", ignore = true)
    List<DataTableDict> dictDoToTableDict(List<DictPo> dictPoList);

    DictPo dictDtoToPo(UpdateDictDto dictDto);

}
