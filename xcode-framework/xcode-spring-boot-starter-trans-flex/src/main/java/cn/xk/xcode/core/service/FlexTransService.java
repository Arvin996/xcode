package cn.xk.xcode.core.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.entity.TransVo;
import cn.xk.xcode.utils.collections.CollectionUtil;
import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.constant.SqlOperator;
import com.mybatisflex.core.mybatis.Mappers;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.query.SqlOperators;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/8/16 11:31
 * @description
 */
public class FlexTransService {

    public List<? extends TransVo> findList(Serializable condition, Class<? extends TransVo> targetClazz, String conditionField) {
        return findList(CollectionUtil.createSingleList(condition), targetClazz, conditionField);
    }

    public List<? extends TransVo> findList(Collection<Serializable> conditions, Class<? extends TransVo> targetClazz, String conditionField) {
        BaseMapper<? extends TransVo> baseMapper = getMapper(targetClazz);
        QueryCondition queryCondition = QueryCondition.create(new QueryColumn(conditionField), SqlOperator.IN, conditions);
        return baseMapper.selectListByQuery(QueryWrapper.create().where(queryCondition));
    }

    public TransVo findById(Serializable condition, Class<? extends TransVo> targetClazz, String conditionField) {
        BaseMapper<? extends TransVo> baseMapper = getMapper(targetClazz);
        Map<String, Object> map = new HashMap<>();
        map.put(conditionField, condition);
        return baseMapper.selectOneByMap(map);
    }

    public BaseMapper<? extends TransVo> getMapper(Class<? extends TransVo> clazz) {
        return Mappers.ofEntityClass(clazz);
    }
}
