package cn.xk.xcode.config;

import cn.xk.xcode.entity.DataObjectBaseEntity;
import cn.xk.xcode.listener.BaseEntityChangeListener;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.audit.AuditManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @Author xuk
 * @Date 2024/7/15 15:53
 * @Version 1.0
 * @Description MybatisFlexConfiguration
 */
@Configuration
public class MybatisFlexConfiguration
{
    private static final Logger logger = LoggerFactory
            .getLogger("mybatis-flex-sql");

    public MybatisFlexConfiguration() {
        //开启审计功能
        AuditManager.setAuditEnable(true);

        //设置 SQL 审计收集器
        AuditManager.setMessageCollector(auditMessage -> {
                    logger.info("预执行sql: {}", auditMessage.getQuery());
                    logger.info("sql参数: {}", auditMessage.getQueryParams());
                    logger.info("执行sql: {}, 执行时长 {}ms", auditMessage.getFullSql()
                            , auditMessage.getElapsedTime());
                }

        );
        FlexGlobalConfig config = FlexGlobalConfig.getDefaultConfig();
        BaseEntityChangeListener listener = new BaseEntityChangeListener();
        config.registerInsertListener(listener, DataObjectBaseEntity.class);
        config.registerUpdateListener(listener, DataObjectBaseEntity.class);
        config.registerSetListener(listener, DataObjectBaseEntity.class);
    }
}
