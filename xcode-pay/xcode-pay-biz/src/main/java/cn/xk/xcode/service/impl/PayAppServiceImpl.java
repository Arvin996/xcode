package cn.xk.xcode.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.convert.app.PayAppConvert;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.entity.dto.app.AddPayAppDto;
import cn.xk.xcode.entity.dto.app.QueryPayAppDto;
import cn.xk.xcode.entity.dto.app.UpdatePayAppDto;
import cn.xk.xcode.entity.po.PayAppChannelPo;
import cn.xk.xcode.entity.vo.app.PayAppResultVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.PayAppChannelService;
import cn.xk.xcode.service.PayChannelService;
import cn.xk.xcode.utils.collections.CollectionUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.PayAppPo;
import cn.xk.xcode.mapper.PayAppMapper;
import cn.xk.xcode.service.PayAppService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cn.xk.xcode.entity.def.PayAppChannelTableDef.PAY_APP_CHANNEL_PO;
import static cn.xk.xcode.entity.def.PayAppTableDef.PAY_APP_PO;
import static cn.xk.xcode.entity.def.PayChannelTableDef.PAY_CHANNEL_PO;
import static cn.xk.xcode.enums.PayModuleErrorCodeConstants.*;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2024-09-23
 */
@Service
public class PayAppServiceImpl extends ServiceImpl<PayAppMapper, PayAppPo> implements PayAppService {

    @Resource
    private PayAppChannelService payAppChannelService;


    @Resource
    private PayChannelService payChannelService;

    @Override
    public Boolean addPayApp(AddPayAppDto addPayAppDto) {
        validate1(addPayAppDto.getAppCode(), addPayAppDto.getAppName());
        return this.save(PayAppConvert.convert(addPayAppDto));
    }

    @Override
    public Boolean updatePayApp(UpdatePayAppDto updatePayAppDto) {
        validate2(updatePayAppDto.getAppCode(), updatePayAppDto.getAppName(), updatePayAppDto.getId());
        return UpdateChain
                .of(PayAppPo.class)
                .set(PAY_APP_PO.APP_CODE, updatePayAppDto.getAppCode())
                .set(PAY_APP_PO.APP_NAME, updatePayAppDto.getAppName())
                .set(PAY_APP_PO.ORDER_NOTIFY_URL, updatePayAppDto.getOrderNotifyUrl())
                .set(PAY_APP_PO.REFUND_NOTIFY_URL, updatePayAppDto.getRefundNotifyUrl())
                .set(PAY_APP_PO.REMARK, updatePayAppDto.getRemark())
                .set(PAY_APP_PO.STATUS, updatePayAppDto.getStatus())
                .where(PAY_APP_PO.ID.eq(updatePayAppDto.getId()))
                .update();
    }

    @Override
    public PayAppResultVo queryPayAppList(QueryPayAppDto queryPayAppDto) {
        PayAppResultVo payAppResultVo = new PayAppResultVo();
        List<PayAppPo> payAppPoList = this.list(QueryWrapper.create().where("1=1").and(PAY_APP_PO.APP_CODE.like(queryPayAppDto.getAppCode()).when(StringUtils.hasText(queryPayAppDto.getAppCode()))));
        payAppResultVo.setList(payAppPoList.stream().map(
                payApp -> {
                    List<Integer> channels = CollectionUtil.convertList(payAppChannelService.list(QueryWrapper.create().where(PAY_APP_CHANNEL_PO.APP_ID.eq(payApp.getId()))), PayAppChannelPo::getChannelId);
                    if (channels.isEmpty()) {
                        return PayAppResultVo.PayAppChannelResult.builder().payAppPo(payApp).list(new ArrayList<>()).build();
                    }
                    return PayAppResultVo.PayAppChannelResult.builder()
                            .payAppPo(payApp).list(payChannelService.list(PAY_CHANNEL_PO.ID.in(channels))).build();
                }
        ).collect(Collectors.toList()));
        return payAppResultVo;
    }

    @Override
    public PayAppPo checkApp(Integer appId) {
        // 校验appId
        PayAppPo payAppPo = this.getById(appId);
        if (ObjectUtil.isNull(payAppPo)){
            ExceptionUtil.castServiceException(PAY_APP_NOT_EXIST);
        }
        if (CommonStatusEnum.isDisable(payAppPo.getStatus())){
            ExceptionUtil.castServiceException(PAY_APP_IS_DISABLED, payAppPo.getAppName());
        }
        return payAppPo;
    }

    private void validate1(String appCode, String appName) {
        if (this.count(PAY_APP_PO.APP_CODE.eq(appCode)) > 0) {
            ExceptionUtil.castServiceException(APP_CODE_ALREADY_EXIST, appCode);
        }
        if (this.count(PAY_APP_PO.APP_NAME.eq(appName)) > 0) {
            ExceptionUtil.castServerException(APP_NAME_ALREADY_EXIST, appName);
        }
    }

    private void validate2(String appCode, String appName, Integer id) {
        if (this.count(PAY_APP_PO.APP_CODE.eq(appCode).and(PAY_APP_PO.ID.ne(id))) > 0) {
            ExceptionUtil.castServiceException(APP_CODE_ALREADY_EXIST, appCode);
        }
        if (this.count(PAY_APP_PO.APP_NAME.eq(appName).and(PAY_APP_PO.ID.ne(id))) > 0) {
            ExceptionUtil.castServerException(APP_NAME_ALREADY_EXIST, appName);
        }
    }
}
