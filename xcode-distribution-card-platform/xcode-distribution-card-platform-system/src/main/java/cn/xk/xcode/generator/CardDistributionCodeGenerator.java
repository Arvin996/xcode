package cn.xk.xcode.generator;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;

/**
 * @Author xuk
 * @Date 2025/5/8 14:51
 * @Version 1.0.0
 * @Description CardDistributionCodeGenerator
 **/
public class CardDistributionCodeGenerator implements ICodeGenerator {

    @Override
    public CodeGen createCodeGen() {
        return null;
    }

    @Override
    public DataSourceEntity createDataSourceEntity() {
        return null;
    }

    public static void main(String[] args) {
        ICodeGenerator codeGenerator = new CardDistributionCodeGenerator();
        codeGenerator.doGenerate();
    }
}
