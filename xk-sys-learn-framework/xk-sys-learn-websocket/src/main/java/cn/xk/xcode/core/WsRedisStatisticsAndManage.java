package cn.xk.xcode.core;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2024/7/2 15:02
 * @Version 1.0
 * @Description WsRedisStatisticsAndManage
 */
@Component
public class WsRedisStatisticsAndManage
{
    public final static String WEBSOCKET_ONLINE_SID = "socket:online:sid";
    public final static String WEBSOCKET_ONLINE_USER = "socket:online:user";

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 用户上线
     *
     * @param loginId
     */
    public void addUserToOnlineChat(String sessionId, String loginId) {
        String onlineUserKey = WEBSOCKET_ONLINE_USER;
        // 检查用户名是否已存在于在线用户哈希表中
        if (redisTemplate.opsForHash().hasKey(onlineUserKey, loginId)) {
            // 如果存在，获取sid列表并更新
            List<String> sids = (List<String>) redisTemplate.opsForHash().get(onlineUserKey, loginId);
            sids.add(sessionId);
            redisTemplate.opsForHash().put(onlineUserKey, loginId, sids);
        } else {
            // 如果不存在，创建一个包含当前sid的新列表
            List<String> sids = new ArrayList<>(1);
            sids.add(sessionId);
            redisTemplate.opsForHash().put(onlineUserKey, loginId, sids);
        }

        // 更新sid映射
        redisTemplate.opsForHash().put(WEBSOCKET_ONLINE_SID, sessionId, loginId);
    }

    /**
     * 用户下线
     *
     */
    public void removeUser(String sessionId, String loginId) {
        String onlineUserKey = WEBSOCKET_ONLINE_USER;

        // 检查用户名是否存在于在线用户哈希表中
        if (redisTemplate.opsForHash().hasKey(onlineUserKey, loginId)) {
            // 如果存在，获取sid列表并移除当前sessionId
            List<String> sids = (List<String>) redisTemplate.opsForHash().get(onlineUserKey, loginId);
            sids.remove(sessionId);

            // 如果用户不再有任何会话，则从在线用户列表中删除
            if (sids.isEmpty()) {
                redisTemplate.opsForHash().delete(onlineUserKey, loginId);
            } else {
                redisTemplate.opsForHash().put(onlineUserKey, loginId, sids);
            }
        }

        // 删除sessionId映射
        redisTemplate.opsForHash().delete(WEBSOCKET_ONLINE_SID, sessionId);
    }

    /**
     * 用户下线 - 根据sessionId
     *
     * @param sessionId
     */
    public void removeUserBySessionId(String sessionId) {
        // 查找sessionId对应的用户名
        String loginId = (String) redisTemplate.opsForHash().get(WEBSOCKET_ONLINE_SID, sessionId);
        if (loginId != null) {
            // 从在线用户列表中删除sessionId
            if (redisTemplate.opsForHash().hasKey(WEBSOCKET_ONLINE_USER, loginId)) {
                List<String> sids = (List<String>) redisTemplate.opsForHash().get(WEBSOCKET_ONLINE_USER, loginId);
                System.out.println("sids =="+ sids);
                sids.remove(sessionId);
                // 如果用户不再有任何会话，则从在线用户列表中删除该用户
                if (sids.isEmpty()) {
                    redisTemplate.opsForHash().delete(WEBSOCKET_ONLINE_USER, loginId);
                } else {
                    redisTemplate.opsForHash().put(WEBSOCKET_ONLINE_USER, loginId, sids);
                }
            }

            // 删除sessionId映射
            redisTemplate.opsForHash().delete(WEBSOCKET_ONLINE_SID, sessionId);
        }
    }

    /**
     * 用户下线 - 根据sessionId
     *
     * @param loginId
     */
    public void removeUserByUsername(String loginId) {
        String onlineUserKey = WEBSOCKET_ONLINE_USER;

        // 检查用户名是否存在于在线用户哈希表中
        if (redisTemplate.opsForHash().hasKey(onlineUserKey, loginId)) {
            // 获取该用户的所有连接sessionId
            List<String> sids = (List<String>) redisTemplate.opsForHash().get(onlineUserKey, loginId);

            // 从在线用户列表中删除该用户
            redisTemplate.opsForHash().delete(onlineUserKey, loginId);

            // 删除与该用户相关的所有sessionId映射
            for (String sid : sids) {
                redisTemplate.opsForHash().delete(WEBSOCKET_ONLINE_SID, sid);
            }
        }
    }

    /**
     * 根据sessionId查询loginId
     *
     * @param sessionId
     */
    public String getUserBySessionId(String sessionId) {
        // 查找sessionId对应的用户名
        return (String) redisTemplate.opsForHash().get(WEBSOCKET_ONLINE_SID, sessionId);
    }


    /**
     * 查询在线用户数
     *
     * @return
     */
    public long getOnlineUserCount() {

        // 获取在线用户列表的大小，即在线用户数

        return redisTemplate.opsForHash().size(WEBSOCKET_ONLINE_USER);
    }

    /**
     * 查询websocket连接数
     *
     * @return
     */
    public long getConnectionCount() {

        // 获取在线连接数，即sessionId映射的数量

        return redisTemplate.opsForHash().size(WEBSOCKET_ONLINE_SID);
    }

    /**
     * 查询某个用户的连接数（在几处登陆）
     *
     */
    public long getUserConnectionCount(String loginId) {
        String onlineUserKey = WEBSOCKET_ONLINE_USER;

        // 检查用户名是否存在于在线用户哈希表中
        if (redisTemplate.opsForHash().hasKey(onlineUserKey, loginId)) {
            // 如果存在，获取该用户的连接数
            List<String> sids = (List<String>) redisTemplate.opsForHash().get(onlineUserKey, loginId);
            return sids.size();
        } else {
            // 如果用户不存在或没有连接，返回 0
            return 0;
        }
    }

    /**
     * 获取所有在线用户名列表
     *
     * @return
     */
    public List<String> getAllOnlineUsernames() {
        Set<Object> loginIds = redisTemplate.opsForHash().keys(WEBSOCKET_ONLINE_USER);
        return loginIds.stream().map(Object::toString).collect(Collectors.toList());
    }

    /**
     * 获取用户的所有连接 sessionId 列表
     *
     * @param loginId
     * @return
     */
    public List<String> getUserSessionIds(String loginId) {
        String onlineUserKey = WEBSOCKET_ONLINE_USER;

        if (redisTemplate.opsForHash().hasKey(onlineUserKey, loginId)) {
            List<String> sids = (List<String>) redisTemplate.opsForHash().get(onlineUserKey, loginId);
            return new ArrayList<>(sids);
        } else {
            return new ArrayList<>();
        }
    }
}
