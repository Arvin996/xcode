package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.QuerySysFilesDto;
import cn.xk.xcode.entity.po.SysFilesPo;
import cn.xk.xcode.entity.vo.FileResultVo;
import cn.xk.xcode.entity.vo.SysFilesVo;
import cn.xk.xcode.pojo.PageResult;
import com.mybatisflex.core.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2025-05-22
 */
public interface SysFilesService extends IService<SysFilesPo> {

    FileResultVo uploadFile(MultipartFile file, String bucket, String username, Boolean isNeedConvertToMp4);

    Boolean delFile(String fileId);

    Boolean updateFile(MultipartFile file, String fileId);

    void downloadFile(String fileId, HttpServletResponse response);

    PageResult<SysFilesVo> querySysFiles(QuerySysFilesDto querySysFilesDto);
}
