package cn.xk.xcode.rpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.52.1)",
    comments = "Source: CaptchaProto.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CaptchaServiceGrpc {

  private CaptchaServiceGrpc() {}

  public static final String SERVICE_NAME = "CaptchaService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyRequest,
      cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyResponse> getVerifyCodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "verifyCode",
      requestType = cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyRequest.class,
      responseType = cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyRequest,
      cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyResponse> getVerifyCodeMethod() {
    io.grpc.MethodDescriptor<cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyRequest, cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyResponse> getVerifyCodeMethod;
    if ((getVerifyCodeMethod = CaptchaServiceGrpc.getVerifyCodeMethod) == null) {
      synchronized (CaptchaServiceGrpc.class) {
        if ((getVerifyCodeMethod = CaptchaServiceGrpc.getVerifyCodeMethod) == null) {
          CaptchaServiceGrpc.getVerifyCodeMethod = getVerifyCodeMethod =
              io.grpc.MethodDescriptor.<cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyRequest, cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "verifyCode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CaptchaServiceMethodDescriptorSupplier("verifyCode"))
              .build();
        }
      }
    }
    return getVerifyCodeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CaptchaServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CaptchaServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CaptchaServiceStub>() {
        @java.lang.Override
        public CaptchaServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CaptchaServiceStub(channel, callOptions);
        }
      };
    return CaptchaServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CaptchaServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CaptchaServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CaptchaServiceBlockingStub>() {
        @java.lang.Override
        public CaptchaServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CaptchaServiceBlockingStub(channel, callOptions);
        }
      };
    return CaptchaServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CaptchaServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CaptchaServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CaptchaServiceFutureStub>() {
        @java.lang.Override
        public CaptchaServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CaptchaServiceFutureStub(channel, callOptions);
        }
      };
    return CaptchaServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class CaptchaServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void verifyCode(cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyRequest request,
        io.grpc.stub.StreamObserver<cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getVerifyCodeMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getVerifyCodeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyRequest,
                cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyResponse>(
                  this, METHODID_VERIFY_CODE)))
          .build();
    }
  }

  /**
   */
  public static final class CaptchaServiceStub extends io.grpc.stub.AbstractAsyncStub<CaptchaServiceStub> {
    private CaptchaServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CaptchaServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CaptchaServiceStub(channel, callOptions);
    }

    /**
     */
    public void verifyCode(cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyRequest request,
        io.grpc.stub.StreamObserver<cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getVerifyCodeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class CaptchaServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<CaptchaServiceBlockingStub> {
    private CaptchaServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CaptchaServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CaptchaServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyResponse verifyCode(cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getVerifyCodeMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class CaptchaServiceFutureStub extends io.grpc.stub.AbstractFutureStub<CaptchaServiceFutureStub> {
    private CaptchaServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CaptchaServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CaptchaServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyResponse> verifyCode(
        cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getVerifyCodeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_VERIFY_CODE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CaptchaServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(CaptchaServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_VERIFY_CODE:
          serviceImpl.verifyCode((cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyRequest) request,
              (io.grpc.stub.StreamObserver<cn.xk.xcode.rpc.CaptchaProto.CaptchaVerifyResponse>) responseObserver);
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

  private static abstract class CaptchaServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CaptchaServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return cn.xk.xcode.rpc.CaptchaProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CaptchaService");
    }
  }

  private static final class CaptchaServiceFileDescriptorSupplier
      extends CaptchaServiceBaseDescriptorSupplier {
    CaptchaServiceFileDescriptorSupplier() {}
  }

  private static final class CaptchaServiceMethodDescriptorSupplier
      extends CaptchaServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    CaptchaServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (CaptchaServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CaptchaServiceFileDescriptorSupplier())
              .addMethod(getVerifyCodeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
