package cn.xk.xcode.rpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 *
 */
@javax.annotation.Generated(
        value = "by gRPC proto compiler (version 1.52.1)",
        comments = "Source: SysFileProto.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SysFileServiceGrpc {

    private SysFileServiceGrpc() {
    }

    public static final String SERVICE_NAME = "SysFileService";

    // Static method descriptors that strictly reflect the proto.
    private static volatile io.grpc.MethodDescriptor<cn.xk.xcode.rpc.SysFileProto.UploadFileRequest,
            cn.xk.xcode.rpc.SysFileProto.CommonUploadFileResponse> getUploadFileMethod;

    @io.grpc.stub.annotations.RpcMethod(
            fullMethodName = SERVICE_NAME + '/' + "uploadFile",
            requestType = cn.xk.xcode.rpc.SysFileProto.UploadFileRequest.class,
            responseType = cn.xk.xcode.rpc.SysFileProto.CommonUploadFileResponse.class,
            methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
    public static io.grpc.MethodDescriptor<cn.xk.xcode.rpc.SysFileProto.UploadFileRequest,
            cn.xk.xcode.rpc.SysFileProto.CommonUploadFileResponse> getUploadFileMethod() {
        io.grpc.MethodDescriptor<cn.xk.xcode.rpc.SysFileProto.UploadFileRequest, cn.xk.xcode.rpc.SysFileProto.CommonUploadFileResponse> getUploadFileMethod;
        if ((getUploadFileMethod = SysFileServiceGrpc.getUploadFileMethod) == null) {
            synchronized (SysFileServiceGrpc.class) {
                if ((getUploadFileMethod = SysFileServiceGrpc.getUploadFileMethod) == null) {
                    SysFileServiceGrpc.getUploadFileMethod = getUploadFileMethod =
                            io.grpc.MethodDescriptor.<cn.xk.xcode.rpc.SysFileProto.UploadFileRequest, cn.xk.xcode.rpc.SysFileProto.CommonUploadFileResponse>newBuilder()
                                    .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
                                    .setFullMethodName(generateFullMethodName(SERVICE_NAME, "uploadFile"))
                                    .setSampledToLocalTracing(true)
                                    .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            cn.xk.xcode.rpc.SysFileProto.UploadFileRequest.getDefaultInstance()))
                                    .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                                            cn.xk.xcode.rpc.SysFileProto.CommonUploadFileResponse.getDefaultInstance()))
                                    .setSchemaDescriptor(new SysFileServiceMethodDescriptorSupplier("uploadFile"))
                                    .build();
                }
            }
        }
        return getUploadFileMethod;
    }

    /**
     * Creates a new async stub that supports all call types for the service
     */
    public static SysFileServiceStub newStub(io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<SysFileServiceStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<SysFileServiceStub>() {
                    @java.lang.Override
                    public SysFileServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new SysFileServiceStub(channel, callOptions);
                    }
                };
        return SysFileServiceStub.newStub(factory, channel);
    }

    /**
     * Creates a new blocking-style stub that supports unary and streaming output calls on the service
     */
    public static SysFileServiceBlockingStub newBlockingStub(
            io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<SysFileServiceBlockingStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<SysFileServiceBlockingStub>() {
                    @java.lang.Override
                    public SysFileServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new SysFileServiceBlockingStub(channel, callOptions);
                    }
                };
        return SysFileServiceBlockingStub.newStub(factory, channel);
    }

    /**
     * Creates a new ListenableFuture-style stub that supports unary calls on the service
     */
    public static SysFileServiceFutureStub newFutureStub(
            io.grpc.Channel channel) {
        io.grpc.stub.AbstractStub.StubFactory<SysFileServiceFutureStub> factory =
                new io.grpc.stub.AbstractStub.StubFactory<SysFileServiceFutureStub>() {
                    @java.lang.Override
                    public SysFileServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
                        return new SysFileServiceFutureStub(channel, callOptions);
                    }
                };
        return SysFileServiceFutureStub.newStub(factory, channel);
    }

    /**
     *
     */
    public static abstract class SysFileServiceImplBase implements io.grpc.BindableService {

        /**
         *
         */
        public void uploadFile(cn.xk.xcode.rpc.SysFileProto.UploadFileRequest request,
                               io.grpc.stub.StreamObserver<cn.xk.xcode.rpc.SysFileProto.CommonUploadFileResponse> responseObserver) {
            io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUploadFileMethod(), responseObserver);
        }

        @java.lang.Override
        public final io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                    .addMethod(
                            getUploadFileMethod(),
                            io.grpc.stub.ServerCalls.asyncUnaryCall(
                                    new MethodHandlers<
                                            cn.xk.xcode.rpc.SysFileProto.UploadFileRequest,
                                            cn.xk.xcode.rpc.SysFileProto.CommonUploadFileResponse>(
                                            this, METHODID_UPLOAD_FILE)))
                    .build();
        }
    }

    /**
     *
     */
    public static final class SysFileServiceStub extends io.grpc.stub.AbstractAsyncStub<SysFileServiceStub> {
        private SysFileServiceStub(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected SysFileServiceStub build(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new SysFileServiceStub(channel, callOptions);
        }

        /**
         *
         */
        public void uploadFile(cn.xk.xcode.rpc.SysFileProto.UploadFileRequest request,
                               io.grpc.stub.StreamObserver<cn.xk.xcode.rpc.SysFileProto.CommonUploadFileResponse> responseObserver) {
            io.grpc.stub.ClientCalls.asyncUnaryCall(
                    getChannel().newCall(getUploadFileMethod(), getCallOptions()), request, responseObserver);
        }
    }

    /**
     *
     */
    public static final class SysFileServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<SysFileServiceBlockingStub> {
        private SysFileServiceBlockingStub(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected SysFileServiceBlockingStub build(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new SysFileServiceBlockingStub(channel, callOptions);
        }

        /**
         *
         */
        public cn.xk.xcode.rpc.SysFileProto.CommonUploadFileResponse uploadFile(cn.xk.xcode.rpc.SysFileProto.UploadFileRequest request) {
            return io.grpc.stub.ClientCalls.blockingUnaryCall(
                    getChannel(), getUploadFileMethod(), getCallOptions(), request);
        }
    }

    /**
     *
     */
    public static final class SysFileServiceFutureStub extends io.grpc.stub.AbstractFutureStub<SysFileServiceFutureStub> {
        private SysFileServiceFutureStub(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
        }

        @java.lang.Override
        protected SysFileServiceFutureStub build(
                io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new SysFileServiceFutureStub(channel, callOptions);
        }

        /**
         *
         */
        public com.google.common.util.concurrent.ListenableFuture<cn.xk.xcode.rpc.SysFileProto.CommonUploadFileResponse> uploadFile(
                cn.xk.xcode.rpc.SysFileProto.UploadFileRequest request) {
            return io.grpc.stub.ClientCalls.futureUnaryCall(
                    getChannel().newCall(getUploadFileMethod(), getCallOptions()), request);
        }
    }

    private static final int METHODID_UPLOAD_FILE = 0;

    private static final class MethodHandlers<Req, Resp> implements
            io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final SysFileServiceImplBase serviceImpl;
        private final int methodId;

        MethodHandlers(SysFileServiceImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_UPLOAD_FILE:
                    serviceImpl.uploadFile((cn.xk.xcode.rpc.SysFileProto.UploadFileRequest) request,
                            (io.grpc.stub.StreamObserver<cn.xk.xcode.rpc.SysFileProto.CommonUploadFileResponse>) responseObserver);
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

    private static abstract class SysFileServiceBaseDescriptorSupplier
            implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
        SysFileServiceBaseDescriptorSupplier() {
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
            return cn.xk.xcode.rpc.SysFileProto.getDescriptor();
        }

        @java.lang.Override
        public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
            return getFileDescriptor().findServiceByName("SysFileService");
        }
    }

    private static final class SysFileServiceFileDescriptorSupplier
            extends SysFileServiceBaseDescriptorSupplier {
        SysFileServiceFileDescriptorSupplier() {
        }
    }

    private static final class SysFileServiceMethodDescriptorSupplier
            extends SysFileServiceBaseDescriptorSupplier
            implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
        private final String methodName;

        SysFileServiceMethodDescriptorSupplier(String methodName) {
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
            synchronized (SysFileServiceGrpc.class) {
                result = serviceDescriptor;
                if (result == null) {
                    serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
                            .setSchemaDescriptor(new SysFileServiceFileDescriptorSupplier())
                            .addMethod(getUploadFileMethod())
                            .build();
                }
            }
        }
        return result;
    }
}
