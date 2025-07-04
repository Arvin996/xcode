package cn.xk.xcode.generator;

import cn.xk.xcode.entity.CodeGen;
import cn.xk.xcode.entity.DataSourceEntity;

/**
 * @Author xuk
 * @Date 2024/8/4 13:35
 * @Version 1.0
 * @Description AllMemberCodeGenerator
 */
public class AllMemberCodeGenerator implements ICodeGenerator {
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
                .sourceDir(System.getProperty("user.dir") + "/own_module/xcode-parent/xcode-member/xcode-member-biz/src/main/java")
                .tables(new String[]{"member_address", "member_config", "member_experience_record", "member_group"
                        , "member_level", "member_level_change_record", "member_login_record", "member_point_record"
                        , "member_sign", "member_sign_record", "member_tag", "member_user"})
                .mapperXmlPackage(System.getProperty("user.dir") + "/own_module/xcode-parent/xcode-member/xcode-member-biz/src/main/resources/mapper")
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
                .dbName("xcode-member")
                .username("root")
                .password("123456")
                .build();

    }

    public static void main(String[] args) {
        AllMemberCodeGenerator allMemberCodeGenerator = new AllMemberCodeGenerator();
        allMemberCodeGenerator.doGenerate();
    }
}
