package cn.xk.xcode.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.entity.dto.flavor.AddFlavorDto;
import cn.xk.xcode.entity.dto.flavor.FlavorBaseDto;
import cn.xk.xcode.entity.dto.flavor.QueryFlavorDto;
import cn.xk.xcode.entity.dto.flavor.UpdateFlavorDto;
import cn.xk.xcode.entity.vo.flavor.FlavorResultVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.TakeoutDishFlavorService;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.TakeoutFlavorPo;
import cn.xk.xcode.mapper.TakeoutFlavorMapper;
import cn.xk.xcode.service.TakeoutFlavorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.xk.xcode.constant.GlobalTakeoutProductConstants.*;
import static cn.xk.xcode.entity.def.TakeoutDishFlavorTableDef.TAKEOUT_DISH_FLAVOR_PO;
import static cn.xk.xcode.entity.def.TakeoutFlavorTableDef.TAKEOUT_FLAVOR_PO;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2024-10-31
 */
@Service
public class TakeoutFlavorServiceImpl extends ServiceImpl<TakeoutFlavorMapper, TakeoutFlavorPo> implements TakeoutFlavorService {

    @Resource
    private TakeoutDishFlavorService takeoutDishFlavorService;

    @Override
    public Boolean addTakeoutFlavor(AddFlavorDto addFlavorDto) {
        validate(addFlavorDto);
        return this.save(BeanUtil.toBean(addFlavorDto, TakeoutFlavorPo.class));
    }

    @Override
    public Boolean delTakeoutFlavor(FlavorBaseDto flavorBaseDto) {
        if (takeoutDishFlavorService.count(TAKEOUT_DISH_FLAVOR_PO.FLAVOR_ID.eq(flavorBaseDto.getId())) > 0) {
            ExceptionUtil.castServiceException(FLAVOR_HAS_USED);
        }
        return this.removeById(flavorBaseDto.getId());
    }

    @Override
    public Boolean updateTakeoutFlavor(UpdateFlavorDto updateFlavorDto) {
        if (takeoutDishFlavorService.count(TAKEOUT_FLAVOR_PO.NAME.eq(updateFlavorDto.getName()).and(TAKEOUT_FLAVOR_PO.ID.ne(updateFlavorDto.getId()))) > 0) {
            ExceptionUtil.castServiceException(FLAVOR_HAS_ALREADY_EXISTS);
        }
        validate(updateFlavorDto.getValue(), updateFlavorDto.getName());
        return this.updateById(BeanUtil.toBean(updateFlavorDto, TakeoutFlavorPo.class));
    }

    @Override
    public List<FlavorResultVo> listTakeoutFlavor(QueryFlavorDto queryFlavorDto) {
        QueryWrapper queryWrapper = QueryWrapper
                .create()
                .where(TAKEOUT_FLAVOR_PO.NAME.like(queryFlavorDto.getName()).when(StrUtil.isNotBlank(queryFlavorDto.getName())));
        return this.listAs(queryWrapper, FlavorResultVo.class);
    }

    private void validate(AddFlavorDto addFlavorDto) {
        if (this.count(TAKEOUT_FLAVOR_PO.NAME.eq(addFlavorDto.getName())) > 0) {
            ExceptionUtil.castServiceException(FLAVOR_HAS_ALREADY_EXISTS);
        }
        List<String> value = addFlavorDto.getValue();
        // 校验口味是否重复
        validate(value, addFlavorDto.getName());
    }

    private void validate(List<String> value, String name) {
        Map<String, String> map = new HashMap<>();
        for (String s : value) {
            if (map.containsKey(s)) {
                ExceptionUtil.castServiceException(FLAVOR_PROPERTY_HAS_EXISTS, name, s);
            } else {
                map.put(s, s);
            }
        }
    }
}
