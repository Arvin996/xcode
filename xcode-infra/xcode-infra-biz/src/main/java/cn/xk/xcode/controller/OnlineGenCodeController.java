//package cn.xk.xcode.controller;
//
//import cn.xk.xcode.annotation.SaSystemCheckPermission;
//import cn.xk.xcode.entity.dto.*;
//import cn.xk.xcode.entity.po.GenTableColumnPo;
//import cn.xk.xcode.entity.po.GenTablePo;
//import cn.xk.xcode.entity.vo.GenCodePreviewVo;
//import cn.xk.xcode.entity.vo.GenTableColumnVo;
//import cn.xk.xcode.entity.vo.GenTableDetailVo;
//import cn.xk.xcode.entity.vo.GenTableResultVo;
//import cn.xk.xcode.pojo.CommonResult;
//import cn.xk.xcode.pojo.PageResult;
//import cn.xk.xcode.service.GenTableColumnService;
//import cn.xk.xcode.service.GenTableService;
//import cn.xk.xcode.utils.PageUtil;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
//import static cn.xk.xcode.entity.def.GenTableColumnTableDef.GEN_TABLE_COLUMN_PO;
//
///**
// * @Author xuk
// * @Date 2024/11/25 16:15
// * @Version 1.0.0
// * @Description OnlineGenCodeController
// **/
//@RestController
//@RequestMapping("/online/gen")
//@Tag(name = "在线代码生成")
//public class OnlineGenCodeController {
//
//    @Resource
//    private GenTableColumnService genTableColumnService;
//
//    @Resource
//    private GenTableService genTableService;
//
//    @Operation(summary = "查询代码生成列表")
//    @SaSystemCheckPermission("online:gen:list")
//    @PostMapping("/tableList")
//    public CommonResult<PageResult<GenTableResultVo>> genTableList(@RequestBody QueryGenTableDto queryGenTableDto) {
//        return CommonResult.success(genTableService.genTableList(queryGenTableDto));
//    }
//
//    @Operation(summary = "产看代码生成业务")
//    @SaSystemCheckPermission("online:gen:query")
//    @GetMapping("/queryTable/{tableId}")
//    public CommonResult<GenTableDetailVo> queryTable(@PathVariable("tableId") Long tableId,
//                                                     @RequestParam("startPage") Long startPage,
//                                                     @RequestParam("pageSize") Long pageSize) {
//        GenTablePo genTablePo = genTableService.getById(tableId);
//        List<GenTableColumnPo> list = genTableColumnService.list(GEN_TABLE_COLUMN_PO.TABLE_ID.eq(tableId));
//        GenTableDetailVo genTableDetailVo = new GenTableDetailVo();
//        genTableDetailVo.setGenTablePo(genTablePo);
//        genTableDetailVo.setColumnList(PageUtil.startPage(startPage, pageSize, list));
//        return CommonResult.success(genTableDetailVo);
//    }
//
//    @Operation(summary = "查询数据库表")
//    @SaSystemCheckPermission("online:gen:queryDbTableList")
//    @PostMapping("/queryDbTableList")
//    public CommonResult<PageResult<GenTableResultVo>> queryDbTableList(@RequestBody QueryGenTableDto queryGenTableDto) {
//        return CommonResult.success(PageUtil.startPage(queryGenTableDto, genTableService.queryDbTableList(queryGenTableDto)));
//    }
//
//    @Operation(summary = "查询数据库表字段")
//    @SaSystemCheckPermission("online:gen:queryDbTableColumnList")
//    @GetMapping("/queryDbTableColumnList/{tableId}")
//    public CommonResult<List<GenTableColumnVo>> queryDbTableColumnList(@PathVariable("tableId") Long tableId) {
//        return CommonResult.success(genTableService.queryDbTableColumnList(tableId));
//    }
//
//    /**
//     * 导入表结构（保存）
//     */
//    @SaSystemCheckPermission("online:gen:importTableSave")
//    @Operation(summary = "代码生成")
//    @PostMapping("/importTable")
//    public CommonResult<Boolean> importTableSave(@Validated @RequestBody ImportTableDto importTableDto) {
//        return CommonResult.success(genTableService.importTableSave(importTableDto));
//    }
//
//    @Operation(summary = "通过sql导入数据表")
//    @SaSystemCheckPermission("online:gen:createTableBySql")
//    @PostMapping("/createTableBySql")
//    public CommonResult<Boolean> createTableBySql(@Validated @RequestBody CreateGenTableDto createGenTableDto) {
//        return CommonResult.success(genTableService.createTableBySql(createGenTableDto));
//    }
//
//    @Operation(summary = "修改代码生成")
//    @SaSystemCheckPermission("online:gen:updateGenTable")
//    @PostMapping("/updateGenTable")
//    public CommonResult<Boolean> updateGenTable(@Validated @RequestBody UpdateGenTableDto updateGenTableDto) {
//        return CommonResult.success(genTableService.updateGenTable(updateGenTableDto));
//    }
//
//    @Operation(summary = "删除代码生成")
//    @SaSystemCheckPermission("online:gen:removeGenTable")
//    @PostMapping("/removeGenTable")
//    public CommonResult<Boolean> removeGenTable(@Validated @RequestBody DelGenTableDto delGenTableDto) {
//        return CommonResult.success(genTableService.removeGenTable(delGenTableDto));
//    }
//
//    @Operation(summary = "预览代码生成")
//    @SaSystemCheckPermission("online:gen:preview")
//    @GetMapping("/preview/{tableId}")
//    public CommonResult<GenCodePreviewVo>  preview(@PathVariable("tableId") Long tableId) {
//        return CommonResult.success(genTableService.preview(tableId));
//    }
//
//    @SaSystemCheckPermission("online:gen:download")
//    @Operation(summary = "代码生成")
//    @GetMapping("/download/{tableName}")
//    public void download(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
//        genTableService.download(response, tableName);
//    }
//
//    @SaSystemCheckPermission("online:gen:sync")
//    @Operation(summary = "同步数据库")
//    @GetMapping("/synchDb/{tableName}")
//    public CommonResult<Boolean> syncDb(@PathVariable("tableName") String tableName) {
//        return CommonResult.success(genTableService.syncDb(tableName));
//    }
//
//    /**
//     * 批量生成代码
//     */
//    @SaSystemCheckPermission("online:gen:batchDownload")
//    @Operation(summary = "批量代码生成")
//    @GetMapping("/batchGenCode/{tables}")
//    public void batchGenCode(HttpServletResponse response, @PathVariable("tables") String tables) throws IOException {
//        genTableService.batchGenCode(response, tables);
//    }
//
//}
