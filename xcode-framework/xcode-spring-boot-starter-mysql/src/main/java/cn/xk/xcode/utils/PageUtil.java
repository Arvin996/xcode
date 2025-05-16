package cn.xk.xcode.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.pojo.PageParam;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mybatisflex.core.paginate.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/4 13:50
 * @Version 1.0.0
 * @Description PageUtil
 **/
public class PageUtil {

    public static <T extends PageParam, K> PageResult<K> startPage(T t, List<K> result){
        if (ObjectUtil.isEmpty(result)){
            return createEmptyPageResult(t);
        }
        long pageNo = t.getPageNo();
        long pageSize = t.getPageSize();
        long total = result.size();
        PageResult<K> pageResult = new PageResult<>(pageNo, pageSize, total);
        long totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        long start = (pageNo - 1) * pageSize;
        ArrayList<K> arrayList = new ArrayList<>();
        for (long i = start; i < start + pageSize && i < total ;i++) {
            arrayList.add(result.get((int) i));
        }
        pageResult.setData(arrayList);
        pageResult.setTotal(totalPage);
        return pageResult;
    }

    public static <T, K> PageResult<K> toPageResult(Page<T> page, Class<K> classType) {
        List<T> records = page.getRecords();
        if (CollectionUtil.isEmpty(records)) {
            return createEmptyPageResult(page.getPageNumber(), page.getPageSize());
        } else {
            return new PageResult<>(page.getPageNumber(), page.getPageSize(), page.getTotalPage(), BeanUtil.toBean(records, classType));
        }
    }

    public static <T> PageResult<T> toPageResult(PageInfo<T> pageInfo) {
        List<T> records = pageInfo.getList();
        if (CollectionUtil.isEmpty(records)) {
            return createEmptyPageResult(pageInfo.getPageNum(), pageInfo.getPageSize());
        } else {
            return new PageResult<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getTotal(), records);
        }
    }

    public static <T> PageResult<T> startPage(long pageNo, long pageSize, List<T> result){
        PageParam pageParam = new PageParam(pageNo, pageSize);
        return startPage(pageParam, result);
    }


    public static <T> PageResult<T> createEmptyPageResult(long pageNo, long pageSize){
        return createEmptyPageResult(new PageParam(pageNo, pageSize));
    }


    public static <T> PageResult<T> createEmptyPageResult(PageParam pageParam){
        return PageResult.empty(pageParam);
    }

    public static <T> Page<T> toFlexPage(PageParam pageParam) {
        return new Page<>(pageParam.getPageNo(), pageParam.getPageSize());
    }

    public static void statePage(PageParam pageParam){
        PageHelper.startPage(Math.toIntExact(pageParam.getPageNo()), Math.toIntExact(pageParam.getPageSize()));
    }

}
