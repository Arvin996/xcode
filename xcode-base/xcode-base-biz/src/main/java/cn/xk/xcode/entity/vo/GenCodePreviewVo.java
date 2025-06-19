package cn.xk.xcode.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/11/26 15:35
 * @Version 1.0.0
 * @Description GenCodePreviewVo
 **/
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "代码预览返回dto")
public class GenCodePreviewVo {

    Map<String, Object> data;
}
