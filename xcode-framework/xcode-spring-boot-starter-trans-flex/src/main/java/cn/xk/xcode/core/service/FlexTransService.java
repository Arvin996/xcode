package cn.xk.xcode.core.service;

import cn.xk.xcode.core.entity.TransPo;
import cn.xk.xcode.utils.collections.CollectionUtil;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.constant.SqlOperator;
import com.mybatisflex.core.mybatis.Mappers;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;

import java.io.Serializable;
import java.util.Collection;
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

    public List<? extends TransPo> findList(Serializable condition, Class<? extends TransPo> targetClazz, String conditionField) {
        return findList(CollectionUtil.createSingleList(condition), targetClazz, conditionField);
    }

    public List<? extends TransPo> findList(Collection<Serializable> conditions, Class<? extends TransPo> targetClazz, String conditionField) {
        BaseMapper<? extends TransPo> baseMapper = getMapper(targetClazz);
        QueryCondition queryCondition = QueryCondition.create(new QueryColumn(conditionField), SqlOperator.IN, conditions);
        return baseMapper.selectListByQuery(QueryWrapper.create().where(queryCondition));
    }

    public TransPo findById(Serializable condition, Class<? extends TransPo> targetClazz, String conditionField) {
        BaseMapper<? extends TransPo> baseMapper = getMapper(targetClazz);
        Map<String, Object> map = new HashMap<>();
        map.put(conditionField, condition);
        return baseMapper.selectOneByMap(map);
    }

    public BaseMapper<? extends TransPo> getMapper(Class<? extends TransPo> clazz) {
        return Mappers.ofEntityClass(clazz);
    }
}
