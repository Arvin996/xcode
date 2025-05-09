package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.dto.dict.QueryDictDto;
import cn.xk.xcode.entity.vo.dict.SysDictDataVo;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.SystemDictTypePo;
import cn.xk.xcode.mapper.SystemDictTypeMapper;
import cn.xk.xcode.service.SystemDictTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *  服务层实现。
 *
 * @author xuk
 * @since 2025-05-09
 */
@Service
public class SystemDictTypeServiceImpl extends ServiceImpl<SystemDictTypeMapper, SystemDictTypePo> implements SystemDictTypeService {

    @Resource
    private SystemDictTypeMapper systemDictTypeMapper;


    @Override
    public List<SysDictDataVo> selectAllDictDate(QueryDictDto queryDictDto) {
        return systemDictTypeMapper.selectAllDictDate(queryDictDto);
    }
}
