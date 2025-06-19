package cn.xk.xcode.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.DatabaseConnInfoPo;
import cn.xk.xcode.mapper.DatabaseConnInfoMapper;
import cn.xk.xcode.service.DatabaseConnInfoService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author lenovo
 * @since 2024-06-25
 */
@Service
public class DatabaseConnInfoServiceImpl extends ServiceImpl<DatabaseConnInfoMapper, DatabaseConnInfoPo> implements DatabaseConnInfoService {

}
