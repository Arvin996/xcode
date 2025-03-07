package cn.xk.xcode.mq;

import com.xxl.mq.client.message.XxlMqMessage;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2025/3/6 8:17
 * @Version 1.0.0
 * @Description XxlMqExtraMessageBuilder
 **/
@Getter
public class XxlMqExtraMessageBuilder {

    private String group;
    private Integer retryCount;
    private Long shardingId;
    private Integer timeout;

    public static XxlMqExtraMessageBuilder newBuilder(){
        return new XxlMqExtraMessageBuilder();
    }

    public XxlMqExtraMessageBuilder withGroup(String group){
        this.group = group;
        return this;
    }

    public XxlMqExtraMessageBuilder withRetryCount(Integer retryCount){
        this.retryCount = retryCount;
        return this;
    }

    public XxlMqExtraMessageBuilder withShardingId(Long shardingId){
        this.shardingId = shardingId;
        return this;
    }

    public XxlMqExtraMessageBuilder withTimeout(Integer timeout){
        this.timeout = timeout;
        return this;
    }


    public void buildXxlMqMessage(XxlMqMessage xxlMqMessage){
        if(this.getGroup() != null){
            xxlMqMessage.setGroup(this.getGroup());
        }
        if(this.getRetryCount() != null){
            xxlMqMessage.setRetryCount(this.getRetryCount());
        }
        if(this.getShardingId() != null){
            xxlMqMessage.setShardingId(this.getShardingId());
        }
        if(this.getTimeout() != null){
            xxlMqMessage.setTimeout(this.getTimeout());
        }
    }
}
