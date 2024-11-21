package cn.xk.xcode.entity.dto.task;

import cn.xk.xcode.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dromara.warm.flow.orm.entity.FlowTask;

import javax.validation.Valid;

/**
 * @Author xuk
 * @Date 2024/11/20 16:31
 * @Version 1.0.0
 * @Description QueryTaskDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "流程分页查询dto")
public class QueryTaskDto extends FlowTask {
    @Valid
    private PageParam pageParam;
}
