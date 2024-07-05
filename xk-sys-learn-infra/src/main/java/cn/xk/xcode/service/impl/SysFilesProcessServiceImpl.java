package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.def.SysFilesProcessTableDef;
import com.mybatisflex.core.query.QueryMethods;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.core.update.UpdateWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.SysFilesProcessPo;
import cn.xk.xcode.mapper.SysFilesProcessMapper;
import cn.xk.xcode.service.SysFilesProcessService;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.xk.xcode.entity.def.SysFilesProcessTableDef.SYS_FILES_PROCESS_PO;

/**
 * 服务层实现。
 *
 * @author lenovo
 * @since 2024-06-26
 */
@Service
public class SysFilesProcessServiceImpl extends ServiceImpl<SysFilesProcessMapper, SysFilesProcessPo> implements SysFilesProcessService {

    @Override
    public List<SysFilesProcessPo> getSysFilesProcessList(int shardIndex, int shardTotal, int processors) {
        QueryWrapper queryWrapper = QueryWrapper.create(SysFilesProcessPo.class).select(SYS_FILES_PROCESS_PO.ALL_COLUMNS)
                .from(SYS_FILES_PROCESS_PO)
                .where(QueryMethods.mod(SYS_FILES_PROCESS_PO.ID, shardTotal).eq(shardIndex))
                .and(SYS_FILES_PROCESS_PO.STATUS.eq("1").or(SYS_FILES_PROCESS_PO.STATUS.eq("3")))
                .and(SYS_FILES_PROCESS_PO.FAIL_COUNT.lt(3))
                .limit(processors);
        return this.list(queryWrapper);
    }

    @Override
    public boolean startTask(int id) {
        return UpdateChain.of(SYS_FILES_PROCESS_PO)
                .set(SYS_FILES_PROCESS_PO.STATUS, "4")
                .where(SYS_FILES_PROCESS_PO.STATUS.eq("1").or(SYS_FILES_PROCESS_PO.STATUS.eq("3")))
                .and(SYS_FILES_PROCESS_PO.ID.eq(id)).update();
    }

    @Override
    public void saveProcessFinishStatus(int taskId, String number, String originalFileId, String objectName, String msg) {

    }
}
