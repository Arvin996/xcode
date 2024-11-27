package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.UpdateFileDto;
import cn.xk.xcode.entity.dto.UploadFileDto;
import cn.xk.xcode.entity.vo.FileResultVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.SysFilesPo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  服务层。
 *
 * @author lenovo
 * @since 2024-06-26
 */
public interface SysFilesService extends IService<SysFilesPo> {

    FileResultVo uploadFile(UploadFileDto uploadFileDto);

    String delFile(String fileId);

    String updateFile(UpdateFileDto updateFileDto);

    void downloadFile(String fileId, HttpServletResponse response) throws IOException;
}
