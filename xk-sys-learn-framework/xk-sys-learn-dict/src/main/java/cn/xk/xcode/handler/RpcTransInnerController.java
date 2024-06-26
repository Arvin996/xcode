package cn.xk.xcode.handler;

import cn.xk.xcode.context.DictContext;
import cn.xk.xcode.entity.DataTableDict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/6/3 09:29
 * @Version 1.0
 * @Description RpcTransInnerController
 */
@RestController
public class RpcTransInnerController
{
    @Resource
    private DictContext dictContext;

    @PostMapping("/rpc/trans")
    public String rpcTrans(@RequestBody DataTableDict dataTableDict){
        return dictContext.getValue(dataTableDict.getParId(), dataTableDict.getCode());
    }
}
