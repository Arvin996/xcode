package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.cache.DictCacheStrategy;
import cn.xk.xcode.client.SystemDictClient;
import cn.xk.xcode.convert.DictConvert;
import cn.xk.xcode.entity.DictDataEntity;
import cn.xk.xcode.entity.SystemDictResultVo;
import cn.xk.xcode.entity.dto.dict.QueryDictDto;
import cn.xk.xcode.entity.dto.dict.UpdateDictDto;
import cn.xk.xcode.entity.po.DictPo;
import cn.xk.xcode.event.DictDataReloadPublisher;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.DictService;
import cn.xk.xcode.utils.object.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static cn.xk.xcode.entity.def.DictTableDef.DICT_PO;

/**
 * @Author xuk
 * @Date 2024/6/21 10:45
 * @Version 1.0
 * @Description DictController
 */
@RestController
@RequestMapping("/dict")
@Tag(name = "字典接口")
public class DictController implements SystemDictClient {

    @Resource
    private DictService dictService;

    @Resource
    private DictConvert dictConvert;

    @Resource
    private DictDataReloadPublisher dictDataReloadPublisher;

    @Resource
    private DictCacheStrategy dictCacheStrategy;

    @Operation(summary = "根据字典code和父code查询字典")
    @PostMapping("/queryByCodeAndPid")
    public CommonResult<String> queryByCodeAndPid(@RequestBody QueryDictDto queryDictDto)
    {
        return CommonResult.success(dictCacheStrategy.getDictName(queryDictDto.getParId(), queryDictDto.getCode()));
    }

    @Operation(summary = "根据字典code查询该code所有子节点的值")
    @PostMapping("/queryDictValuesByPid")
    public CommonResult<List<String>> queryDictValuesByPid(@RequestBody QueryDictDto queryDictDto)
    {
        return CommonResult.success(dictCacheStrategy.getPropertiesByDictType(queryDictDto.getParId(), DictDataEntity::getName));
    }

    @SaCheckPermission("dict::updateDict")
    @Operation(summary = "更新字典")
    @PostMapping("/updateDict")
    public CommonResult<List<DictPo>> updateDict(@Validated @RequestBody UpdateDictDto updateDictDto)
    {
        DictPo dictPo = dictConvert.dictDtoToPo(updateDictDto);
        dictService.update(dictPo, DICT_PO.CODE.eq(dictPo.getCode()).and(DICT_PO.PAR_ID.eq(dictPo.getParId())));
        dictDataReloadPublisher.publish();
        return CommonResult.success(dictService.list());
    }

    @SaCheckPermission("dict::delDict")
    @Operation(summary = "删除字典")
    @PostMapping("/delDict")
    public CommonResult<List<DictPo>> delDict(@Validated @RequestBody UpdateDictDto updateDictDto)
    {
        dictService.remove(DICT_PO.CODE.eq(updateDictDto.getCode()).and(DICT_PO.PAR_ID.eq(updateDictDto.getParId())));
        dictDataReloadPublisher.publish();
        return CommonResult.success(dictService.list());
    }

    @SaCheckPermission("dict::addDict")
    @Operation(summary = "新增字典")
    @PostMapping("/addDict")
    public CommonResult<List<DictPo>> addDict(@Validated @RequestBody UpdateDictDto updateDictDto)
    {
        DictPo dictPo = dictConvert.dictDtoToPo(updateDictDto);
        dictService.save(dictPo);
        dictDataReloadPublisher.publish();
        return CommonResult.success(dictService.list());
    }

    @Override
    public CommonResult<String> getDictValue(String dictType, String dictCode) {
        return CommonResult.success(dictCacheStrategy.getDictName(dictType, dictCode));
    }

    @Override
    public CommonResult<List<SystemDictResultVo>> getDictTypeValues(String dictType) {
        List<DictDataEntity> data = dictCacheStrategy.getDictTypeList(dictType);
        return CommonResult.success(BeanUtil.toBean(data, SystemDictResultVo.class));
    }
}
