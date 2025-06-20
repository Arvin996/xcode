package cn.xk.xcode.rpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
        value = "by gRPC proto compiler (version 1.52.1)",
        comments = "Source: SendMessageProto.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SendMessageTaskServiceGrpc {

    private SendMessageTaskServiceGrpc() {
    }

    public static final String SERVICE_NAME = "SendMessageTaskService";

    // Static method descriptors that strictly reflect the proto.
    private static volatile io.grpc.MethodDescriptor<cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskRequest,
            cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskResponse> getSendMessageTaskMethod;

    @io.grpc.stub.annotations.RpcMethod(
            fullMethodName = SERVICE_NAME + '/' + "sendMessageTask",
            requestType = cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskRequest.class,
            responseType = cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskResponse.class,
            methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
    public static io.grpc.MethodDescriptor<cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskRequest,
            cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskResponse> getSendMessageTaskMethod() {
        io.grpc.MethodDescriptor<cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskRequest, cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskResponse> getSendMessageTaskMethod;
        if ((getSendMessageTaskMethod = SendMessageTaskServiceGrpc.getSendMessageTaskMethod) == null) {
            synchronized (SendMessageTaskServiceGrpc.class) {
                if ((getSendMessageTaskMethod = SendMessageTaskServiceGrpc.getSendMessageTaskMethod) == null) {
                    SendMessageTaskServiceGrpc.getSendMessageTaskMethod = getSendMessageTaskMethod =
                            io.grpc.MethodDescriptor.<cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskRequest, cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskResponse>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(SERVICE_NAME, "sendMessageTask"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskRequest.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskResponse.getDefaultInstance()))
                                    .setSchemaDescriptor(new SendMessageTaskServiceMethodDescriptorSupplier("sendMessageTask"))
                                    .build();
                }
            }
        }
        return getSendMessageTaskMethod;
    }

    /**
     * Creates a new async stub that supports all call types for the service
     */
    public static SendMessageTaskServiceStub newStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<SendMessageTaskServiceStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<SendMessageTaskServiceStub>() {
                    @java.lang.Override
                    public SendMessageTaskServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new SendMessageTaskServiceStub(channel, callOptions);
                    }
                };
        return SendMessageTaskServiceStub.newStub(factory, channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the service
     */
    public static SendMessageTaskServiceBlockingStub newBlockingStub(
            io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<SendMessageTaskServiceBlockingStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<SendMessageTaskServiceBlockingStub>() {
                    @java.lang.Override
                    public SendMessageTaskServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new SendMessageTaskServiceBlockingStub(channel, callOptions);
                    }
                };
        return SendMessageTaskServiceBlockingStub.newStub(factory, channel);
    }

    /**
     * Creates a new ListenableFuture-style stub that supports unary calls on the service
     */
    public static SendMessageTaskServiceFutureStub newFutureStub(
            io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<SendMessageTaskServiceFutureStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<SendMessageTaskServiceFutureStub>() {
                    @java.lang.Override
                    public SendMessageTaskServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new SendMessageTaskServiceFutureStub(channel, callOptions);
                    }
                };
        return SendMessageTaskServiceFutureStub.newStub(factory, channel);
    }

    /**
     *
     */
    public static abstract class SendMessageTaskServiceImplBase implements io.grpc.BindableService {

        /**
         *
         */
        public void sendMessageTask(cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskRequest request,
                                    io.grpc.stub.StreamObserver<cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskResponse> responseObserver) {
            io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendMessageTaskMethod(), responseObserver);
        }

        @java.lang.Override
        public final io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                    .addMethod(
                            getSendMessageTaskMethod(),
                            io.grpc.stub.ServerCalls.asyncUnaryCall(
                                    new MethodHandlers<
                                            cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskRequest,
                                            cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskResponse>(
                                            this, METHODID_SEND_MESSAGE_TASK)))
                    .build();
        }
    }

    /**
     *
     */
    public static final class SendMessageTaskServiceStub extends io.grpc.stub.AbstractAsyncStub<SendMessageTaskServiceStub> {
        private SendMessageTaskServiceStub(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected SendMessageTaskServiceStub build(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new SendMessageTaskServiceStub(channel, callOptions);
        }

        /**
         *
         */
        public void sendMessageTask(cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskRequest request,
                                    io.grpc.stub.StreamObserver<cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskResponse> responseObserver) {
            io.grpc.stub.ClientCalls.asyncUnaryCall(
                    getChannel().newCall(getSendMessageTaskMethod(), getCallOptions()), request, responseObserver);
        }
    }

    /**
     *
     */
    public static final class SendMessageTaskServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<SendMessageTaskServiceBlockingStub> {
        private SendMessageTaskServiceBlockingStub(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected SendMessageTaskServiceBlockingStub build(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new SendMessageTaskServiceBlockingStub(channel, callOptions);
        }

        /**
         *
         */
        public cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskResponse sendMessageTask(cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskRequest request) {
            return io.grpc.stub.ClientCalls.blockingUnaryCall(
                    getChannel(), getSendMessageTaskMethod(), getCallOptions(), request);
        }
    }

    /**
     *
     */
    public static final class SendMessageTaskServiceFutureStub extends io.grpc.stub.AbstractFutureStub<SendMessageTaskServiceFutureStub> {
        private SendMessageTaskServiceFutureStub(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected SendMessageTaskServiceFutureStub build(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new SendMessageTaskServiceFutureStub(channel, callOptions);
        }

        /**
         *
         */
        public com.google.common.util.concurrent.ListenableFuture<cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskResponse> sendMessageTask(
                cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskRequest request) {
            return io.grpc.stub.ClientCalls.futureUnaryCall(
                    getChannel().newCall(getSendMessageTaskMethod(), getCallOptions()), request);
        }
    }

    private static final int METHODID_SEND_MESSAGE_TASK = 0;

    private static final class MethodHandlers<Req, Resp> implements
            io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final SendMessageTaskServiceImplBase serviceImpl;
        private final int methodId;

        MethodHandlers(SendMessageTaskServiceImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_SEND_MESSAGE_TASK:
                    serviceImpl.sendMessageTask((cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskRequest) request,
                            (io.grpc.stub.StreamObserver<cn.xk.xcode.rpc.SendMessageProto.SendMessageTaskResponse>) responseObserver);
                    break;
                default:
                    throw new AssertionError();
            }
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(
                io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                default:
                    throw new AssertionError();
            }
        }
    }

    private static abstract class SendMessageTaskServiceBaseDescriptorSupplier
            implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
        SendMessageTaskServiceBaseDescriptorSupplier() {
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
            return cn.xk.xcode.rpc.SendMessageProto.getDescriptor();
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
            return getFileDescriptor().findServiceByName("SendMessageTaskService");
        }
    }

    private static final class SendMessageTaskServiceFileDescriptorSupplier
            extends SendMessageTaskServiceBaseDescriptorSupplier {
        SendMessageTaskServiceFileDescriptorSupplier() {
        }
    }

    private static final class SendMessageTaskServiceMethodDescriptorSupplier
            extends SendMessageTaskServiceBaseDescriptorSupplier
            implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
        private final String methodName;

        SendMessageTaskServiceMethodDescriptorSupplier(String methodName) {
            this.methodName = methodName;
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
            return getServiceDescriptor().findMethodByName(methodName);
        }
    }

    private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

    public static io.grpc.ServiceDescriptor getServiceDescriptor() {
        io.grpc.ServiceDescriptor result = serviceDescriptor;
        if (result == null) {
            synchronized (SendMessageTaskServiceGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                            .setSchemaDescriptor(new SendMessageTaskServiceFileDescriptorSupplier())
                            .addMethod(getSendMessageTaskMethod())
                            .build();
                }
            }
        }
        return result;
    }
}
