package cn.xk.xcode.client;

import cn.xk.xcode.entity.DictDataEntity;
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
    String rpcTrans(@RequestBody DictDataEntity dictDataEntity);
}
