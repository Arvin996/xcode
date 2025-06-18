package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.audit.AuditResultDto;
import cn.xk.xcode.entity.dto.audit.QueryProductAuditDto;
import cn.xk.xcode.entity.vo.audit.ProductAuditVo;
import cn.xk.xcode.pojo.PageResult;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.ProductAuditPo;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2025-05-30
 */
public interface ProductAuditService extends IService<ProductAuditPo> {

    PageResult<ProductAuditVo> queryProductAuditList(QueryProductAuditDto queryProductAuditDto);

    Boolean auditResult(AuditResultDto auditResultDto);
}
