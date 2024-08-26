package cn.xk.xcode.convert;

import cn.xk.xcode.entity.dto.user.AddUserDto;
import cn.xk.xcode.entity.dto.user.UpdateUserDto;
import cn.xk.xcode.entity.po.UserPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/21 20:37
 * @description
 */
@Mapper(componentModel = "spring")
public interface UserConvert {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    UserPo userUpdateDtoToPo(UpdateUserDto updateUserDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    UserPo userAddDtoToPo(AddUserDto userAddDto);
}
