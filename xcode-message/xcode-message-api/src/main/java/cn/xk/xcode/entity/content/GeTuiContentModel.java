package cn.xk.xcode.entity.content;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author xuk
 * @Date 2025/3/11 8:38
 * @Version 1.0.0
 * @Description GeTuiContentModel
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class GeTuiContentModel extends ContentModel{

    private String title;
    private String content;
    private String url;
}
