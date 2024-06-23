package cn.xk.xcode.convert;

import cn.xk.xcode.entity.dto.role.AddRoleDto;
import cn.xk.xcode.entity.po.RolePo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/21 21:04
 * @description
 */
@Mapper(componentModel = "spring")
public interface RoleConvert
{
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "status", ignore = true)
    RolePo addRoleDtoToPo(AddRoleDto addRoleDto);
}
