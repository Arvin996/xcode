package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.audit.AuditResultDto;
import cn.xk.xcode.entity.dto.audit.QueryProductAuditDto;
import cn.xk.xcode.entity.vo.audit.ProductAuditVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.ProductAuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/6/5 15:41
 * @Version 1.0.0
 * @Description ProductAuditController
 **/
@RestController
@RequestMapping("/manage/product/audit")
@Tag(name = "ProductAuditController", description = "ProductAuditController 商品审核controller")
public class ProductAuditController {

    @Resource
    private ProductAuditService productAuditService;

    @PostMapping("/queryProductAuditList")
    @Operation(summary = "查询商品审核列表")
    public CommonResult<PageResult<ProductAuditVo>> queryProductAuditList(@RequestBody QueryProductAuditDto queryProductAuditDto) {
        return CommonResult.success(productAuditService.queryProductAuditList(queryProductAuditDto));
    }

    @PostMapping("/auditResult")
    public CommonResult<Boolean> auditResult(@Validated @RequestBody AuditResultDto auditResultDto) {
        return CommonResult.success(productAuditService.auditResult(auditResultDto));
    }


}
