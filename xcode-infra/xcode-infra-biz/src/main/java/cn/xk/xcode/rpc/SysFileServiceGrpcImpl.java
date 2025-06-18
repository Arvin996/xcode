package cn.xk.xcode.rpc;

import cn.xk.xcode.entity.vo.FileResultVo;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.service.SysFilesService;
import cn.xk.xcode.utils.file.FileUtil;
import com.google.protobuf.ByteString;
import com.google.protobuf.UnknownFieldSet;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.SUCCESS;

/**
 * @Author xuk
 * @Date 2025/6/12 10:34
 * @Version 1.0.0
 * @Description SysFileServiceGrpcImpl
 **/
@GrpcService
@Slf4j
public class SysFileServiceGrpcImpl extends SysFileServiceGrpc.SysFileServiceImplBase {

    @Resource
    private SysFilesService sysFilesService;

    @Override
    public void uploadFile(SysFileProto.UploadFileRequest request, StreamObserver<SysFileProto.CommonUploadFileResponse> responseObserver) {
        String originalFileName = request.getOriginalFileName();
        ByteString file = request.getFile();
        String contentType = request.getContentType();
        String bucket = request.getBucket();
        String username = request.getUsername();
        boolean isNeedConvertToMp4 = request.getIsNeedConvertToMp4();
        SysFileProto.CommonUploadFileResponse.Builder builder = SysFileProto.CommonUploadFileResponse.newBuilder();
        try {
            MultipartFile multipartFile = FileUtil.convertByteToMultipartFile(file.toByteArray(), originalFileName, contentType);
            FileResultVo fileResultVo = sysFilesService.uploadFile(multipartFile, bucket, username, isNeedConvertToMp4);
            builder.setCode((Integer) SUCCESS.getCode());
            builder.setMessage(SUCCESS.getMessage());
            SysFileProto.UploadFileResponse.Builder builder1 = SysFileProto.UploadFileResponse.newBuilder();
            builder1.setFileId(fileResultVo.getFileId());
            builder1.setFilePath(fileResultVo.getFilePath());
            builder.setData(builder1.build());
        } catch (Exception e) {
            if (e instanceof ServerException) {
                ServerException serverException = (ServerException) e;
                builder.setCode((Integer) serverException.getCode());
                builder.setMessage(serverException.getMsg());
            } else if (e instanceof ServiceException) {
                ServiceException serviceException = (ServiceException) e;
                builder.setCode((Integer) serviceException.getCode());
                builder.setMessage(serviceException.getMsg());
            } else {
                log.error("uploadFile error: {}", e.getMessage());
                builder.setCode((Integer) INTERNAL_SERVER_ERROR.getCode());
                builder.setMessage(INTERNAL_SERVER_ERROR.getMessage());
            }
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
