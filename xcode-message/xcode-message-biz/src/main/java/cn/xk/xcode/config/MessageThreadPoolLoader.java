package cn.xk.xcode.config;

import cn.xk.xcode.core.ThreadPoolExecutorsUniqueCodeLoader;
import cn.xk.xcode.entity.po.MessageChannelPo;
import cn.xk.xcode.service.MessageChannelService;
import cn.xk.xcode.utils.collections.CollectionUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static cn.xk.xcode.config.GlobalMessageConstants.XXL_THREAD_POOL_NAME;

/**
 * @Author xuk
 * @Date 2025/3/10 16:27
 * @Version 1.0.0
 * @Description MessageThreadPoolLoader
 **/
@Component
public class MessageThreadPoolLoader implements ThreadPoolExecutorsUniqueCodeLoader {

    @Resource
    private MessageChannelService messageChannelService;

    @Override
    public List<String> loadUniqueCode() {
        List<MessageChannelPo> messageChannelPoList = messageChannelService.list();
        if (CollectionUtil.isEmpty(messageChannelPoList)){
            return CollectionUtil.createSingleList(XXL_THREAD_POOL_NAME);
        }
        List<String> collect = messageChannelPoList.stream().map(MessageChannelPo::getCode).collect(Collectors.toList());
        collect.add(XXL_THREAD_POOL_NAME);
        return collect;
    }
}
