package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.flavor.AddFlavorDto;
import cn.xk.xcode.entity.dto.flavor.FlavorBaseDto;
import cn.xk.xcode.entity.dto.flavor.QueryFlavorDto;
import cn.xk.xcode.entity.dto.flavor.UpdateFlavorDto;
import cn.xk.xcode.entity.vo.flavor.FlavorResultVo;
import cn.xk.xcode.pojo.CommonResult;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.TakeoutFlavorPo;

import java.util.List;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2024-10-31
 */
public interface TakeoutFlavorService extends IService<TakeoutFlavorPo> {

    Boolean addTakeoutFlavor(AddFlavorDto addFlavorDto);

    Boolean delTakeoutFlavor(FlavorBaseDto flavorBaseDto);

    Boolean updateTakeoutFlavor(UpdateFlavorDto updateFlavorDto);

    List<FlavorResultVo> listTakeoutFlavor(QueryFlavorDto queryFlavorDto);
}
