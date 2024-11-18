package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.resource.UpdateRoleResourcesDto;
import cn.xk.xcode.pojo.CommonResult;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.ResourcePo;
import org.mapstruct.Mapper;

/**
 *  服务层。
 *
 * @author Arvin
 * @since 2024-06-21
 */
public interface ResourceService extends IService<ResourcePo> {
    CommonResult<Boolean> updateRoleResources(UpdateRoleResourcesDto updateRoleResourcesDto);
}
