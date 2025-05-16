package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author xuk
 * @since 2025-05-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("system_api")
public class SystemApiPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 服务所属服务名
     */
    private String productName;

    /**
     * 接口权限标识
     */
    private String apiCode;

    /**
     * 接口路径
     */
    private String apiPath;

    /**
     * 接口描述
     */
    private String apiDesc;

}
