package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.xk.xcode.entity.dto.client.ClientDto;
import cn.xk.xcode.entity.po.ClientPo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.LoginInfoDto;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.service.ClientService;
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

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/25 19:20
 * @description
 */
@RestController
@Tag(name = "登录类型客户端")
@RequestMapping("/client")
public class ClientController
{
    @Resource
    private ClientService clientService;

    @SaCheckPermission("client:add")
    @Operation(summary = "新增client")
    @PostMapping("/add")
    public CommonResult<Boolean> addClient(@Validated @RequestBody ClientDto clientDto) {
        ClientPo clientPo = BeanUtil.toBean(clientDto, ClientPo.class);
        clientPo.setCreateUser(StpUtil.getLoginIdAsString());
        return CommonResult.success(clientService.save(clientPo));
    }

    @SaCheckPermission("client:del")
    @Operation(summary = "删除client")
    @PostMapping("/del")
    public CommonResult<Boolean> delClient(@Validated @RequestBody ClientDto clientDto) {
        ClientPo clientPo = BeanUtil.toBean(clientDto, ClientPo.class);
        return CommonResult.success(clientService.removeById(clientPo));
    }

    @SaCheckPermission("client:update")
    @Operation(summary = "更新client")
    @PostMapping("/update")
    public CommonResult<Boolean> updateClient(@Validated @RequestBody ClientDto clientDto) {
        ClientPo clientPo = BeanUtil.toBean(clientDto, ClientPo.class);
        return CommonResult.success(clientService.updateById(clientPo));
    }

    @SaCheckPermission("client:query")
    @Operation(summary = "查询所有client")
    @PostMapping("/queryClients")
    public CommonResult<List<ClientPo>> queryClients() {
        return CommonResult.success(clientService.list());
    }

}
