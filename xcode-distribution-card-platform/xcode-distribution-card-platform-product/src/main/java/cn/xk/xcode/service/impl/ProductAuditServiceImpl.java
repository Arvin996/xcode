package cn.xk.xcode.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.ProductAuditPo;
import cn.xk.xcode.mapper.ProductAuditMapper;
import cn.xk.xcode.service.ProductAuditService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author xuk
 * @since 2025-05-30
 */
@Service
public class ProductAuditServiceImpl extends ServiceImpl<ProductAuditMapper, ProductAuditPo> implements ProductAuditService {

}
