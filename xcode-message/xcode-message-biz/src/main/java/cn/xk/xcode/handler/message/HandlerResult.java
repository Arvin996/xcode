package cn.xk.xcode.handler.message;

import cn.xk.xcode.entity.po.MessageTaskDetailPo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/13 16:51
 * @Version 1.0.0
 * @Description HandlerResult
 **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandlerResult {

    private Integer successCount;
    private List<MessageTaskDetailPo> list;
}
