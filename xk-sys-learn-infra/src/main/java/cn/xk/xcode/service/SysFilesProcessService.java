package cn.xk.xcode.service;

import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.SysFilesProcessPo;

import java.util.List;

/**
 *  服务层。
 *
 * @author lenovo
 * @since 2024-06-26
 */
public interface SysFilesProcessService extends IService<SysFilesProcessPo> {

    List<SysFilesProcessPo> getSysFilesProcessList(int shardIndex, int shardTotal, int processors);

    boolean startTask(int id);
}
