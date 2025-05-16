package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.menu.AddMenuDto;
import cn.xk.xcode.entity.dto.menu.QueryMenuDto;
import cn.xk.xcode.entity.dto.menu.UpdateMenuDto;
import cn.xk.xcode.entity.vo.menu.SystemMenuVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.SystemMenuPo;

import java.util.List;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2025-05-09
 */
public interface SystemMenuService extends IService<SystemMenuPo> {

    List<SystemMenuVo> queryAllMenu(QueryMenuDto queryMenuDto);

    Boolean addSystemMenu(AddMenuDto addMenuDto);

    Boolean delSystemMenu(Long id);

    Boolean updateSystemMenu(UpdateMenuDto updateMenuDto);

    Boolean moveUpSystemMenu(Long id);

    Boolean moveDownSystemMenu(Long id);
}
