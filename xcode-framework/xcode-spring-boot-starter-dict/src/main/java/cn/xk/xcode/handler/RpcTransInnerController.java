package cn.xk.xcode.handler;

import cn.xk.xcode.cache.DictCacheStrategy;
import cn.xk.xcode.entity.DictDataEntity;
import cn.xk.xcode.pojo.CommonResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author xuk
 * @Date 2024/6/3 09:29
 * @Version 1.0
 * @Description RpcTransInnerController
 */
@ResponseBody
@RequiredArgsConstructor
public class RpcTransInnerController {

    private final DictCacheStrategy dictCacheStrategy;

    @PostMapping("/rpc/trans")
    public CommonResult<String> rpcTrans(@RequestBody DictDataEntity dictDataEntity){
        return CommonResult.success(dictCacheStrategy.getDictName(dictDataEntity.getCode(), dictDataEntity.getDictType()));
    }
}
