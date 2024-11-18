package cn.xk.xcode.entity.vo.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户所有tag返回")
public class MemberUserTagsVo {
    @Schema(description = "用户标签列表")
    private List<MemberTagVo> tags;
}
