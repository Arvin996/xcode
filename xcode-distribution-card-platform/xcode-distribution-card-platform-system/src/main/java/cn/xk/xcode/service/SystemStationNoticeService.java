package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.notice.PublishSystemStationNoticeDto;
import cn.xk.xcode.entity.dto.notice.QuerySystemStationNoticeDto;
import cn.xk.xcode.entity.vo.notice.SystemStationNoticeVo;
import cn.xk.xcode.pojo.PageResult;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.SystemStationNoticePo;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2025-05-09
 */
public interface SystemStationNoticeService extends IService<SystemStationNoticePo> {

    Boolean publishSystemNotice(PublishSystemStationNoticeDto publishSystemStationNoticeDto);

    PageResult<SystemStationNoticeVo> queryAllNotice(QuerySystemStationNoticeDto querySystemStationNoticeDto);
}
