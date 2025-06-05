package cn.xk.xcode.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/7/2 18:14
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Area {
    /**
     * 编号 - 全球，即根目录
     */
    public static final Integer ID_GLOBAL = 0;
    /**
     * 编号 - 中国
     */
    public static final Integer ID_CHINA = 1;

    private Integer id;

    private String name;

    private Integer type;

    /**
     * 父节点
     */
    private Area parentArea;

    private List<Area> children;


}
