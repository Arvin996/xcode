package cn.xk.xcode.typehandler;

import cn.hutool.core.util.StrUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2024/8/6 08:38
 * @Version 1.0
 * @Description ListValueTypeHandler list 存到数据库为字符串
 */
public class ListIntTypeHandler extends BaseTypeHandler<List<Integer>> {

    public static final String COMMA = ",";

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<Integer> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, StrUtil.join(COMMA, parameter));
    }

    @Override
    public List<Integer> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getResult(rs.getString(columnName));
    }

    @Override
    public List<Integer> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getResult(rs.getString(columnIndex));
    }

    @Override
    public List<Integer> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getResult(cs.getString(columnIndex));
    }

    private List<Integer> getResult(String str) {
        if (str.isEmpty()) {
            return null;
        }
        return Arrays.stream(StrUtil.splitToInt(str, COMMA)).boxed().collect(Collectors.toList());
    }
}
