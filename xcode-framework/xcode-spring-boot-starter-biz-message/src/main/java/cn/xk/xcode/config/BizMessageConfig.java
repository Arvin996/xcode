package cn.xk.xcode.config;
import cn.xk.xcode.utils.TableSqlUtils;
import cn.xk.xcode.utils.collections.CollectionUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/7/16 15:01
 * @Version 1.0
 * @Description BizMessageConfig 项目启动的时候自动检测创建表
 */
@Configuration
public class BizMessageConfig implements InitializingBean {
    public static final String  BIZ_MESSAGE_TABLE_SQL;

    public static final String HIS_MESSAGE_TABLE_SQL;

    public static final String BIZ_MESSAGE_TABLE_NAME = "biz_message";

    public static final String HIS_MESSAGE_TABLE_NAME = "his_biz_message";

    static {
        BIZ_MESSAGE_TABLE_SQL = "CREATE TABLE `biz_message`  (\n" +
                "  `id` bigint(20) NOT NULL COMMENT '消息id',\n" +
                "  `biz_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息类型',\n" +
                "  `biz_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息主键',\n" +
                "  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息状态 0待处理 1处理成功 2处理失败 3处理中',\n" +
                "  `fail_count` int(6) NULL DEFAULT NULL COMMENT '失败次数',\n" +
                "  `max_fail_count` int(6) NULL DEFAULT NULL COMMENT '最大失败次数',\n" +
                "  `err_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '错误信息',\n" +
                "  `create_user` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',\n" +
                "  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',\n" +
                "  `finish_time` datetime NULL DEFAULT NULL COMMENT '完成时间',\n" +
                "  PRIMARY KEY (`id`) USING BTREE\n" +
                ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;";


        HIS_MESSAGE_TABLE_SQL = "CREATE TABLE `his_biz_message`  (\n" +
                "  `id` bigint(20) NOT NULL COMMENT '消息id',\n" +
                "  `biz_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息类型',\n" +
                "  `biz_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息主键',\n" +
                "  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息状态 0待处理 1处理成功 2处理失败 3处理中',\n" +
                "  `fail_count` int(6) NULL DEFAULT NULL COMMENT '失败次数',\n" +
                "  `max_fail_count` int(6) NULL DEFAULT NULL COMMENT '最大失败次数',\n" +
                "  `err_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '错误信息',\n" +
                "  `create_user` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',\n" +
                "  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',\n" +
                "  `finish_time` datetime NULL DEFAULT NULL COMMENT '完成时间',\n" +
                "  PRIMARY KEY (`id`) USING BTREE\n" +
                ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;";
    }

    public BizMessageConfig(BizMessageProperties bizMessageProperties){
        TableSqlUtils.init(bizMessageProperties);
    }

    @Override
    public void afterPropertiesSet() {
        // 这里进行创建表
        String bizTable = TableSqlUtils.getSpecificTableName(BIZ_MESSAGE_TABLE_NAME);
        if (!StringUtils.hasLength(bizTable)){
            TableSqlUtils.executeSql(CollectionUtil.createSingleList(BIZ_MESSAGE_TABLE_SQL));
        }
        String hisBizTable =  TableSqlUtils.getSpecificTableName(HIS_MESSAGE_TABLE_NAME);
        if (!StringUtils.hasLength(hisBizTable)){
            TableSqlUtils.executeSql(CollectionUtil.createSingleList(HIS_MESSAGE_TABLE_SQL));
        }
    }
}
