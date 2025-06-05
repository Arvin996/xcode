package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.store.AddStoreDto;
import cn.xk.xcode.entity.dto.store.QueryStoreDto;
import cn.xk.xcode.entity.dto.store.UpdateStoreDto;
import cn.xk.xcode.entity.vo.store.ProductStoreVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.ProductStorePo;

import java.util.List;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2025-05-30
 */
public interface ProductStoreService extends IService<ProductStorePo> {

    Boolean addProductStore(AddStoreDto addStoreDto);

    Boolean updateProductStore(UpdateStoreDto updateStoreDto);

    Boolean delProductStore(Long storeId);

    List<ProductStoreVo> queryProductStores(QueryStoreDto queryStoreDto);
}
