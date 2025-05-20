package cn.xk.xcode.rpc;

import cn.hutool.core.bean.BeanUtil;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.handler.message.response.SendMessageResponse;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.message.SendMessageServiceHolder;
import cn.xk.xcode.utils.collections.CollectionUtil;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/5/20 10:40
 * @Version 1.0.0
 * @Description SendMessageTaskServiceGrpcImpl
 **/
@GrpcService
public class SendMessageTaskServiceGrpcImpl extends SendMessageTaskServiceGrpc.SendMessageTaskServiceImplBase {

    @Resource
    private SendMessageServiceHolder sendMessageServiceHolder;

    @Override
    public void sendMessageTask(SendMessageProto.SendMessageTaskRequest request, StreamObserver<SendMessageProto.SendMessageTaskResponse> responseObserver) {
        String msgChannel = request.getMsgChannel();
        MessageTask messageTask = new MessageTask();
        BeanUtil.copyProperties(request, messageTask);
        CommonResult<?> commonResult = sendMessageServiceHolder.routeSendMessageService(msgChannel).sendMessage(messageTask);
        SendMessageProto.SendMessageTaskResponse.Builder builder = SendMessageProto.SendMessageTaskResponse.newBuilder();
        builder.setCode((Integer) commonResult.getCode());
        Object data = commonResult.getData();
        if (data instanceof SendMessageResponse) {
            SendMessageProto.SendMessageResponse.Builder rBuilder = SendMessageProto.SendMessageResponse.newBuilder();
            SendMessageResponse sendMessageResponse = (SendMessageResponse) data;
            rBuilder.setSuccessCount(sendMessageResponse.getSuccessCount());
            rBuilder.setFailCount(sendMessageResponse.getFailCount());
            List<SendMessageResponse.FailMessageDetail> failMessageDetailList = sendMessageResponse.getFailMessageDetailList();
            if (CollectionUtil.isNotEmpty(failMessageDetailList)) {
                for (SendMessageResponse.FailMessageDetail failMessageDetail : failMessageDetailList) {
                    SendMessageProto.FailMessageDetail.Builder builder1 = SendMessageProto.FailMessageDetail.newBuilder();
                    builder1.setReceiver(failMessageDetail.getReceiver());
                    builder1.setFailMsg(failMessageDetail.getFailMsg());
                    rBuilder.addFailMessageDetailList(builder1);
                }
            }
            builder.setSendMessageResponse(rBuilder);
        } else {
            builder.setData(data.toString());
        }
        builder.setMsg(commonResult.getMsg());
        SendMessageProto.SendMessageTaskResponse sendMessageTaskResponse = builder.build();
        responseObserver.onNext(sendMessageTaskResponse);
        responseObserver.onCompleted();
    }
}
