package cn.xk.xcode.config;

import cn.xk.xcode.entity.DataLongObjectBaseEntity;
import cn.xk.xcode.entity.DataStringObjectBaseEntity;
import cn.xk.xcode.listener.BaseLongEntityChangeListener;
import cn.xk.xcode.listener.BaseStringEntityChangeListener;
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
        BaseStringEntityChangeListener baseStringEntityChangeListener = new BaseStringEntityChangeListener();
        config.registerInsertListener(baseStringEntityChangeListener, DataStringObjectBaseEntity.class);
        config.registerUpdateListener(baseStringEntityChangeListener, DataStringObjectBaseEntity.class);
        config.registerSetListener(baseStringEntityChangeListener, DataStringObjectBaseEntity.class);
        BaseLongEntityChangeListener baseLongEntityChangeListener = new BaseLongEntityChangeListener();
        config.registerInsertListener(baseLongEntityChangeListener, DataLongObjectBaseEntity.class);
        config.registerUpdateListener(baseLongEntityChangeListener, DataLongObjectBaseEntity.class);
        config.registerSetListener(baseLongEntityChangeListener, DataLongObjectBaseEntity.class);
    }
}
