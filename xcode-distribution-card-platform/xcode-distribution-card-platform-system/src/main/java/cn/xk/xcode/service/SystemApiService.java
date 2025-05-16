package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.api.AddSystemApiDto;
import cn.xk.xcode.entity.dto.api.BindRoleApiDto;
import cn.xk.xcode.entity.dto.api.QuerySystemApiDto;
import cn.xk.xcode.entity.dto.api.UpdateSystemApiDto;
import cn.xk.xcode.entity.vo.api.SystemApiVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.SystemApiPo;

import java.util.List;
import java.util.Map;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2025-05-09
 */
public interface SystemApiService extends IService<SystemApiPo> {

    Map<String, List<SystemApiVo>> selectAllApi(QuerySystemApiDto querySystemApiDto);

    Boolean addSystemApi(AddSystemApiDto addSystemApiDto);

    Boolean delSystemApi(Integer id);

    Boolean updateSystemApi(UpdateSystemApiDto updateSystemApiDto);

    Boolean bindRoleApi(BindRoleApiDto bindRoleApiDto);
}
