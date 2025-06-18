package cn.xk.xcode.rpc;

import cn.xk.xcode.core.pipeline.TaskChainExecutor;
import cn.xk.xcode.core.pipeline.TaskContext;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.handler.message.response.SendMessageResponse;
import cn.xk.xcode.pipe.context.SendMessageTaskContext;
import cn.xk.xcode.pipe.model.SendMessageTaskModel;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.utils.collections.CollectionUtil;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.BeanUtils;

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
    private TaskChainExecutor taskChainExecutor;

    @Override
    public void sendMessageTask(SendMessageProto.SendMessageTaskRequest request, StreamObserver<SendMessageProto.SendMessageTaskResponse> responseObserver) {
        MessageTask messageTask = new MessageTask();
        BeanUtils.copyProperties(request, messageTask);
        messageTask.setTemplateCode(request.getTemplateId());
        SendMessageTaskModel sendMessageTaskModel = new SendMessageTaskModel(messageTask);
        SendMessageTaskContext sendMessageTaskContext = new SendMessageTaskContext(sendMessageTaskModel);
        TaskContext<SendMessageTaskModel> taskContext = taskChainExecutor.execute(sendMessageTaskContext);
        CommonResult<?> commonResult = taskContext.getResult();
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
        }
        builder.setMsg(commonResult.getMsg());
        SendMessageProto.SendMessageTaskResponse sendMessageTaskResponse = builder.build();
        responseObserver.onNext(sendMessageTaskResponse);
        responseObserver.onCompleted();
    }
}
