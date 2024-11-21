package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.entity.dto.def.QueryDefDto;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dromara.warm.flow.core.entity.Definition;
import org.dromara.warm.flow.core.orm.agent.WarmQuery;
import org.dromara.warm.flow.core.service.DefService;
import org.dromara.warm.flow.core.utils.page.Page;
import org.dromara.warm.flow.orm.entity.FlowDefinition;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author xuk
 * @Date 2024/11/20 10:01
 * @Version 1.0.0
 * @Description FlowDefController
 **/
@RestController
@RequestMapping("/flow/def")
@Tag(name = "流程定义接口")
public class FlowDefController {

    @Resource
    private DefService defService;

    @PostMapping("/list")
    @Operation(summary = "分页查询流程定义")
    @SaCheckLogin
    public CommonResult<PageResult<Definition>> listDefPage(@Validated @RequestBody QueryDefDto queryDefDto) {
        // flow组件自带分页功能
        Page<Definition> page = Page.pageOf(queryDefDto.getPageNo().intValue(), queryDefDto.getPageSize().intValue());
        FlowDefinition flowDefinition = new FlowDefinition();
        BeanUtils.copyProperties(queryDefDto, flowDefinition);
        page = defService.orderByCreateTime().desc().page(flowDefinition, page);
        PageResult<Definition> pageResult = new PageResult<>(queryDefDto, page.getTotal(), page.getList());
        return CommonResult.success(pageResult);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "根据id查询流程定义")
    @SaCheckLogin
    public CommonResult<Definition> getDefById(@PathVariable("id") Long id) {
        Definition definition = defService.getById(id);
        return CommonResult.success(definition);
    }

    @PostMapping("/add")
    @Operation(summary = "添加流程定义")
    @SaCheckPermission("flow:def:add")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Boolean> addDef(@RequestBody FlowDefinition flowDefinition) {
        return CommonResult.success(defService.checkAndSave(flowDefinition));
    }

    @GetMapping("/publish/{id}")
    @Operation(summary = "发布流程定义")
    @SaCheckPermission("flow:def:publish")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Boolean> publishDef(@PathVariable("id") Long id) {
        return CommonResult.success(defService.publish(id));
    }

    @GetMapping("/unPublish/{id}")
    @Operation(summary = "取消发布流程定义")
    @SaCheckPermission("flow:def:unPublish")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Boolean> unPublishDef(@PathVariable("id") Long id) {
        return CommonResult.success(defService.unPublish(id));
    }

    @PostMapping("/update")
    @Operation(summary = "更新流程定义")
    @SaCheckPermission("flow:def:update")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Boolean> updateDef(@RequestBody FlowDefinition flowDefinition) {
        return CommonResult.success(defService.updateById(flowDefinition));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除流程定义")
    @SaCheckPermission("flow:def:delete")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Boolean> deleteDef(@PathVariable("id") Long id) {
        return CommonResult.success(defService.removeById(id));
    }

    @GetMapping("/copy/{id}")
    @Operation(summary = "复制流程定义")
    @SaCheckPermission("flow:def:copy")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Boolean> copyDef(@PathVariable("id") Long id) {
        return CommonResult.success(defService.copyDef(id));
    }

    @PostMapping("/importDefXml")
    @Operation(summary = "通过xml文件导入流程定义")
    @SaCheckPermission("flow:def:importDefXml")
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Boolean> importDefXml(@RequestParam("file") MultipartFile file) throws Exception{
        defService.importXml(file.getInputStream());
        return CommonResult.success(true);
    }

    @GetMapping("/exportDefXml/{id}")
    @Operation(summary = "通过id导出流程定义xml文件")
    @SaCheckPermission("flow:def:exportDefXml")
    public void exportDefXml(@PathVariable("id") Long id, HttpServletResponse response) throws Exception{
        Document document = defService.exportXml(id);
        // 设置生成xml的格式
        OutputFormat of = OutputFormat.createPrettyPrint();
        // 设置编码格式
        of.setEncoding("UTF-8");
        of.setIndent(true);
        of.setIndent("    ");
        of.setNewlines(true);
        // 创建一个xml文档编辑器
        XMLWriter writer = new XMLWriter(response.getOutputStream(), of);
        writer.setEscapeText(false);
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment;");
        writer.write(document);
        writer.close();
    }

    @GetMapping("/queryDefFlowChart/{id}")
    @Operation(summary = "通过id查询流程定义图")
    @SaCheckLogin
    public CommonResult<String> queryDefFlowChart(@PathVariable("id") Long id) throws IOException {
        return CommonResult.success(defService.flowChartNoColor(id));
    }

    @GetMapping("/queryDefFlowChartWithColor/{instanceId}")
    @Operation(summary = "通过实例id查询流程定义图(带颜色)")
    @SaCheckLogin
    public CommonResult<String> queryDefFlowChartWithColor(@PathVariable("instanceId") Long instanceId) throws IOException {
        return CommonResult.success(defService.flowChart(instanceId));
    }

    @GetMapping("/active/{id}")
    @Operation(summary = "通过id激活流程")
    @SaCheckPermission("flow:def:active")
    public CommonResult<Boolean> active(@PathVariable("id") Long id) {
        return CommonResult.success(defService.active(id));
    }

    @GetMapping("/unActive/{id}")
    @Operation(summary = "通过id取消激活流程")
    @SaCheckPermission("flow:def:unActive")
    public CommonResult<Boolean> unActive(@PathVariable("id") Long id) {
        return CommonResult.success(defService.unActive(id));
    }

}
