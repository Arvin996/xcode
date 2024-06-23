package cn.xk.xcode.convert;

import cn.xk.xcode.entity.dto.resource.AddResourceDto;
import cn.xk.xcode.entity.dto.resource.UpdateResourceDto;
import cn.xk.xcode.entity.po.ResourcePo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/21 21:15
 * @description
 */
@Mapper(componentModel = "spring")
public interface ResourceConvert {

    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    ResourcePo addResourceDtoToVo(AddResourceDto addResourceVo);

    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    ResourcePo updateResourceDtoToVo(UpdateResourceDto updateResourceDto);
}
