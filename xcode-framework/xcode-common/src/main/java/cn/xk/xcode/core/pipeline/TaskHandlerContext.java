package cn.xk.xcode.core.pipeline;

import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author xuk
 * @Date 2025/3/6 16:58
 * @Version 1.0.0
 * @Description TaskHandlerContext
 **/
@SuppressWarnings("all")
@Component
public class TaskHandlerContext implements InitializingBean {

    private Map<String, PriorityQueue<TaskHandler>> taskHandlerMap = new ConcurrentHashMap<>();

    public List<TaskHandler> getTaskHandlerList(String code) {
        PriorityQueue<TaskHandler> taskHandlers = taskHandlerMap.get(code);
        if (CollectionUtil.isNotEmpty(taskHandlers)) {
            ArrayList<TaskHandler> list = new ArrayList<>();
            for (TaskHandler taskHandler : taskHandlers) {
                list.add(taskHandler);
            }
            return list;
        }
        return CollectionUtil.createEmptyList();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Collection<TaskHandler> collection = SpringUtil.getBeansOfType(TaskHandler.class).values();
        if (CollectionUtil.isNotEmpty(collection)) {
            Map<String, List<TaskHandler>> map = CollectionUtil.groupByKey(collection, TaskHandler::getCode);
            map.forEach((k, v) -> {
                if (!taskHandlerMap.containsKey(k)) {
                    PriorityQueue<TaskHandler> pq = new PriorityQueue<>((o1, o2) -> {
                        if (o1.getOrder() == o2.getOrder()) {
                            return 0;
                        }
                        return o1.getOrder() > o2.getOrder() ? 1 : -1;
                    });
                    taskHandlerMap.put(k, pq);
                }
                v.forEach(taskHandler -> {
                    taskHandlerMap.get(k).add(taskHandler);
                });
            });
        }
    }
}
