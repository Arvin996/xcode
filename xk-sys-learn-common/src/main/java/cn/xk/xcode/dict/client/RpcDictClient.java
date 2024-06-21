package cn.xk.xcode.dict.client;

import cn.xk.xcode.dict.entity.DataTableDict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author xuk
 * @Date 2024/6/3 09:37
 * @Version 1.0
 * @Description RpcDictClient
 */
public interface RpcDictClient
{
    @PostMapping("/rpc/trans")
    public String rpcTrans(@RequestBody DataTableDict dataTableDict);
}
