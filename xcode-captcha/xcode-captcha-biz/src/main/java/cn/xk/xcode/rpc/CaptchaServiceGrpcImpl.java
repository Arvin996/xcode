package cn.xk.xcode.rpc;

import cn.xk.xcode.entity.dto.CaptchaVerifyReqDto;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.CaptchaService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/5/9 16:05
 * @Version 1.0.0
 * @Description CaptchaServiceGrpcImpl
 **/
@GrpcService
public class CaptchaServiceGrpcImpl extends CaptchaServiceGrpc.CaptchaServiceImplBase {

    @Resource
    private CaptchaService captchaService;

    @Override
    public void verifyCode(CaptchaProto.CaptchaVerifyRequest request, StreamObserver<CaptchaProto.CaptchaVerifyResponse> responseObserver) {
        CommonResult<Boolean> commonResult = captchaService.verifyCode(CaptchaVerifyReqDto
                .builder()
                .code(request.getCode())
                .email(request.getEmail())
                .type(request.getType())
                .mobile(request.getPhone())
                .build());
        CaptchaProto.CaptchaVerifyResponse.Builder builder = CaptchaProto.CaptchaVerifyResponse.newBuilder();
        builder.setCode((Integer) commonResult.getCode());
        builder.setData(commonResult.getData());
        builder.setMsg(commonResult.getMsg());
        CaptchaProto.CaptchaVerifyResponse captchaVerifyResponse = builder.build();
        responseObserver.onNext(captchaVerifyResponse);
        responseObserver.onCompleted();
    }
}
