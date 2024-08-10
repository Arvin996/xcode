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
 * @Date 2024/8/6 08:40
 * @Version 1.0
 * @Description ArrayStringTypeHandler
 */
public class ArrayStringTypeHandler extends BaseTypeHandler<String[]> {

    public static final String COMMA = ",";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String[] parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, ArrayUtil.join(parameter, COMMA));
    }

    @Override
    public String[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getResult(rs.getString(columnName));
    }

    @Override
    public String[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getResult(rs.getString(rs.getString(columnIndex)));
    }

    @Override
    public String[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getResult(cs.getString(columnIndex));
    }

    private String[] getResult(String str) {
        if (!StringUtils.hasLength(str)) {
            return null;
        }
        return StrUtil.splitToArray(str, COMMA);
    }
}
