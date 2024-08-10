package cn.xk.xcode.typehandler;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author xuk
 * @Date 2024/8/6 08:39
 * @Version 1.0
 * @Description ArrayStringTypeHandler 数据库为string 读取到实体类中为数组
 */
public class ArrayIntTypeHandler extends BaseTypeHandler<int[]> {

    public static final String COMMA = ",";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, int[] parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, ArrayUtil.join(parameter, COMMA));
    }

    @Override
    public int[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getResult(rs.getString(columnName));
    }

    @Override
    public int[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getResult(rs.getString(rs.getString(columnIndex)));
    }

    @Override
    public int[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getResult(cs.getString(columnIndex));
    }

    private int[] getResult(String str) {
        if (!StringUtils.hasLength(str)) {
            return null;
        }
        return StrUtil.splitToInt(str, COMMA);
    }
}
