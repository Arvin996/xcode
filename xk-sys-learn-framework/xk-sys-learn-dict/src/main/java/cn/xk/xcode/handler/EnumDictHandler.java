package cn.xk.xcode.handler;

import cn.hutool.core.util.ClassUtil;
import cn.xk.xcode.annotation.DictType;
import cn.xk.xcode.config.DictEnumPackages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author xuk
 * @Date 2024/7/4 16:34
 * @Version 1.0
 * @Description EnumDictHandler
 */
@Slf4j
public class EnumDictHandler extends AbstractDataDictHandler{

    private final DictEnumPackages dictEnumPackages;

    public EnumDictHandler(DictEnumPackages dictEnumPackages) {
        super();
        this.dictEnumPackages = dictEnumPackages;
    }

    // 这里只能做成配置类 做成list
    @Override
    void loadDictCache() {
        // 扫描当前工程中的枚举类
        List<String> packages = dictEnumPackages.getPackages();
        Set<Class<?>> classes = new HashSet<>();
        for (String pack : packages) {
            classes.addAll(ClassUtil.scanPackage(pack));
        }
        // 将枚举类的数据加载到缓存中
    }
}
