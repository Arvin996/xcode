package cn.xk.xcode.core.service;

import cn.xk.xcode.core.entity.TransVo;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.mybatis.Mappers;
import com.mybatisflex.core.query.QueryWrapper;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/8/16 11:31
 * @description
 */
public class FlexTransService {

    public List<? extends TransVo> findList(Serializable id, Class<? extends TransVo> targetClazz, String conditionField){
        BaseMapper<? extends TransVo> baseMapper = getMapper(targetClazz);
        Map<String, Object> map = new HashMap<>();
        map.put(conditionField, id);
        return baseMapper.selectListByQuery(QueryWrapper.create().where(map));
    }

    public TransVo findById(Serializable id, Class<? extends TransVo> targetClazz, String conditionField) {
        BaseMapper<? extends TransVo> baseMapper = getMapper(targetClazz);
        Map<String, Object> map = new HashMap<>();
        map.put(conditionField, id);
        return baseMapper.selectOneByQuery(QueryWrapper.create().where(map));
    }

    public BaseMapper<? extends TransVo> getMapper(Class<? extends TransVo> clazz) {
        return Mappers.ofEntityClass(clazz);
    }
}
