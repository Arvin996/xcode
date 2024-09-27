package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.app.AddPayAppDto;
import cn.xk.xcode.entity.dto.app.QueryPayAppDto;
import cn.xk.xcode.entity.dto.app.UpdatePayAppDto;
import cn.xk.xcode.entity.vo.app.PayAppResultVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.PayAppPo;

import javax.validation.constraints.NotNull;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2024-09-23
 */
public interface PayAppService extends IService<PayAppPo> {

    Boolean addPayApp(AddPayAppDto addPayAppDto);

    Boolean updatePayApp(UpdatePayAppDto updatePayAppDto);

    PayAppResultVo queryPayAppList(QueryPayAppDto queryPayAppDto);

    PayAppPo checkApp(Integer appId);
}
