package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.search.SearchDto;
import cn.xk.xcode.entity.vo.search.SearchVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.TakeoutDishService;
import cn.xk.xcode.service.TakeoutSetmealService;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.List;

import static cn.xk.xcode.entity.def.TakeoutDishTableDef.TAKEOUT_DISH_PO;
import static cn.xk.xcode.entity.def.TakeoutSetmealTableDef.TAKEOUT_SETMEAL_PO;

/**
 * @Author xuk
 * @Date 2024/11/6 10:58
 * @Version 1.0.0
 * @Description TakeoutSearchController
 **/
@RestController
@RequestMapping("/takeout/search")
public class TakeoutSearchController {

    @Resource
    private TakeoutDishService takeoutDishService;

    @Resource
    private TakeoutSetmealService takeoutSetmealService;

    @PostMapping("/search")
    @Operation(summary = "查询")
    public CommonResult<SearchVo> search(@Validated @RequestBody SearchDto searchDto){
        List<Long> dishes = takeoutDishService.listAs(QueryWrapper.create().where(TAKEOUT_DISH_PO.NAME.like(searchDto.getName())), Long.class);
        List<Long> setmeals = takeoutSetmealService.listAs(QueryWrapper.create().where(TAKEOUT_SETMEAL_PO.NAME.like(searchDto.getName())), Long.class);
        return CommonResult.success(SearchVo.builder().dishList(takeoutDishService.getDishes(dishes)).setmealList(takeoutSetmealService.getSetmeals(setmeals)).build());
    }

}
