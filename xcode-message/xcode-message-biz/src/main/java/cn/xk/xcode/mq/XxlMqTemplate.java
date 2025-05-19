//package cn.xk.xcode.mq;
//
//import com.xxl.mq.client.message.XxlMqMessage;
//import com.xxl.mq.client.producer.XxlMqProducer;
//
//import java.util.Date;
//import java.util.Objects;
//
///**
// * @Author xuk
// * @Date 2025/3/5 17:19
// * @Version 1.0.0
// * @Description XxlMqTemplate
// **/
//public class XxlMqTemplate {
//
//    public void sendMessage(String topic, String data) {
//        sendMessage(topic, data, null, null);
//    }
//
//    public void sendMessage(String topic, String data, Date effectTime) {
//        sendMessage(topic, data, effectTime, null);
//    }
//
//    public void sendMessage(String topic, String data, XxlMqExtraMessageBuilder builder) {
//        sendMessage(topic, data, null, builder);
//    }
//
//    public void sendMessage(String topic, String data, Date effectTime, XxlMqExtraMessageBuilder builder) {
//        XxlMqMessage xxlMqMessage = new XxlMqMessage(topic, data);
//        if (Objects.nonNull(effectTime)) {
//            xxlMqMessage.setEffectTime(effectTime);
//        }
//        sendMessage(xxlMqMessage, builder);
//
//    }
//
//
//    public void sendMessage(XxlMqMessage xxlMqMessage, XxlMqExtraMessageBuilder builder) {
//        if (Objects.nonNull(builder)) {
//            builder.buildXxlMqMessage(xxlMqMessage);
//        }
//        XxlMqProducer.produce(xxlMqMessage);
//    }
//
//    public void broadcastMessage(String topic, String data) {
//        XxlMqMessage xxlMqMessage = new XxlMqMessage(topic, data);
//        XxlMqProducer.broadcast(xxlMqMessage);
//    }
//
//
//}
