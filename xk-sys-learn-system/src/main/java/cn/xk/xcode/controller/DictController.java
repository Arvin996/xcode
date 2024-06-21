package cn.xk.xcode.controller;

import cn.xk.xcode.convert.DictConvert;
import cn.xk.xcode.dict.context.DictContext;
import cn.xk.xcode.dict.entity.DataTableDict;
import cn.xk.xcode.dict.event.DataTableReloadPublisher;
import cn.xk.xcode.dict.event.TableDictEvent;
import cn.xk.xcode.entity.dto.QueryDictDto;
import cn.xk.xcode.entity.dto.UpdateDictDto;
import cn.xk.xcode.entity.po.DictPo;
import cn.xk.xcode.entity.vo.DictVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.DictService;
import com.mybatisflex.core.query.QueryWrapper;
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
public class DictController
{
    @Resource
    private DictService dictService;

    @Resource
    private DictContext dictContext;

    @Resource
    private DictConvert dictConvert;

    @Resource
    private DataTableReloadPublisher dataTableReloadPublisher;

    @Operation(summary = "根据字典code和父code查询字典")
    @PostMapping("/queryByCodeAndPid")
    public CommonResult<String> queryByCodeAndPid(@RequestBody QueryDictDto queryDictDto)
    {
        return CommonResult.success(dictContext.getValue(queryDictDto.getParId(), queryDictDto.getCode()));
    }

    @Operation(summary = "根据字典code查询该code所有子节点的值")
    @PostMapping("/queryDictValuesByPid")
    public CommonResult<List<String>> queryDictValuesByPid(@RequestBody QueryDictDto queryDictDto)
    {
        return CommonResult.success(dictContext.getListCodeByProperty(queryDictDto.getParId(), DataTableDict::getName));
    }

    @Operation(summary = "更新字典")
    @PostMapping("/updateDict")
    public CommonResult<List<DictVo>> updateDict(@Validated @RequestBody UpdateDictDto updateDictDto)
    {
        DictPo dictPo = dictConvert.dictDtoToPo(updateDictDto);
        dictService.update(dictPo, DICT_PO.CODE.eq(dictPo.getCode()).and(DICT_PO.PAR_ID.eq(dictPo.getParId())));
        dataTableReloadPublisher.publish();
        return CommonResult.success(dictConvert.dictPoToVo(dictService.list()));
    }

    @Operation(summary = "删除字典")
    @PostMapping("/delDict")
    public CommonResult<List<DictVo>> delDict(@Validated @RequestBody UpdateDictDto updateDictDto)
    {
        dictService.remove(DICT_PO.CODE.eq(updateDictDto.getCode()).and(DICT_PO.PAR_ID.eq(updateDictDto.getParId())));
        dataTableReloadPublisher.publish();
        return CommonResult.success(dictConvert.dictPoToVo(dictService.list()));
    }

    @Operation(summary = "新增字典")
    @PostMapping("/addDict")
    public CommonResult<List<DictVo>> addDict(@Validated @RequestBody UpdateDictDto updateDictDto)
    {
        DictPo dictPo = dictConvert.dictDtoToPo(updateDictDto);
        dictService.save(dictPo);
        dataTableReloadPublisher.publish();
        return CommonResult.success(dictConvert.dictPoToVo(dictService.list()));
    }

}
