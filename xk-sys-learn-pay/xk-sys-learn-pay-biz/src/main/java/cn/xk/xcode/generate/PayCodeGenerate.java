package cn.xk.xcode.generate;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;

/**
 * @Author xuk
 * @Date 2024/9/20 15:34
 * @Version 1.0.0
 * @Description PayCodeGenerate
 **/
public class PayCodeGenerate implements ICodeGenerate{

    @Override
    public CodeGen createCodeGen() {
        return CodeGen.builder()
                .basePackage("cn.xk.xcode")
                .entityWithLombok(true)
                .mapperXmlGenerateEnable(true)
                .serviceGenerateEnable(true)
                .mapperGenerateEnable(true)
                .entityGenerateEnable(true)
                .serviceImplGenerateEnable(true)
                .tableDefGenerateEnable(true)
                .sourceDir(System.getProperty("user.dir") + "/own_module/xk-sys-learn/xk-sys-learn-pay/xk-sys-learn-pay-biz/src/main/java")
                .tables(new String[]{"pay_app", "pay_app_channel", "pay_merchant", "pay_channel", "pay_notify_log", "pay_notify_task"
                        , "pay_order", "pay_refund"})
                .mapperXmlPackage(System.getProperty("user.dir") + "/own_module/xk-sys-learn/xk-sys-learn-pay/xk-sys-learn-pay-biz/src/main/resources/mapper")
                .entityPackage("cn.xk.xcode.entity.po")
                .tableDefPackage("cn.xk.xcode.entity.def")
                .entityClassSuffix("Po").build();
    }

    @Override
    public DataSourceEntity createDataSourceEntity() {
        return DataSourceEntity
                .builder()
                .dbIp("127.0.0.1")
                .dbPort("3306")
                .dbName("xk-learn-pay")
                .username("root")
                .password("123456")
                .build();
    }

    public static void main(String[] args) {
        PayCodeGenerate payCodeGenerate = new PayCodeGenerate();
        payCodeGenerate.doGenerate();
    }
}
