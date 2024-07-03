package com.xk.xcode.core.utils;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.ObjectUtil;
import com.xk.xcode.core.entity.Area;
import com.xk.xcode.core.entity.AreaTypeEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static cn.xk.xcode.utils.collections.CollectionUtil.convertList;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/7/2 18:16
 * @description
 */
public class AreaUtils {

    private final static AreaUtils INSTANCE = new AreaUtils();

    private final static Map<Integer, Area> areas = new HashMap<>();

    private AreaUtils() {
        // 在内存中读取文件生成数据
        areas.put(Area.ID_GLOBAL, new Area(Area.ID_GLOBAL, "全球", 0, null, new ArrayList<>()));
        // 从 csv 中加载数据
        List<CsvRow> rows = CsvUtil.getReader().read(ResourceUtil.getUtf8Reader("area.csv")).getRows();
        rows.remove(0); // 删除 header
        for (CsvRow row : rows) {
            Area area = new Area(Integer.parseInt(row.get(0)), row.get(1), Integer.valueOf(row.get(2)),
                    null, new ArrayList<>());
            // 添加到 areas 中
            areas.put(area.getId(), area);
        }
        // 构建父子关系
        for (CsvRow row : rows) {
            Area area = areas.get(Integer.parseInt(row.get(0)));
            Area parentArea = areas.get(Integer.valueOf(row.get(3)));
            area.setParentArea(parentArea);
            parentArea.getChildren().add(area);
        }
    }

    public static Area getArea(Integer id){
        return areas.get(id);
    }

    /**
     * 获得指定区域对应的编号
     *
     * @param pathStr 区域路径，例如说：河南省/石家庄市/新华区
     * @return 区域
     */
    public static Area parseArea(String pathStr){
        String[] paths = pathStr.split("/");
        Area area = null;
        for (String path : paths) {
            if (ObjectUtil.isNull(area)){
                area =  areas.values().stream().filter(area1 -> area1.getName().equals(path)).findFirst().orElse(null);
            }else {
                area = area.getChildren().stream().filter(area1 -> area1.getName().equals(path)).findFirst().orElse(null);
            }
        }
        return area;
    }

    private static void getAreaNodePathList(Area node, String path, List<String> paths){
        if (ObjectUtil.isNull(node)){
            return;
        }
        // 构建当前节点的路径
        String currentPath = path.isEmpty() ? node.getName() : path + "/" + node.getName();
        // 添加到路径列表中
        paths.add(currentPath);
        // 递归调用
        for (Area child : node.getChildren()) {
            getAreaNodePathList(child, currentPath, paths);
        }
    }
    /**
     * 获取所有节点的全路径名称如：河南省/石家庄市/新华区
     *
     * @param areas 地区树
     * @return 所有节点的全路径名称
     */
    public static List<String> getAreaNodePathList(List<Area> areas) {
        List<String> paths = new ArrayList<>();
        areas.forEach(area -> getAreaNodePathList(area, "", paths));
        return paths;
    }

    public static <T> List<T> getByType(AreaTypeEnum type, Function<Area, T> func) {
        return convertList(areas.values(), func, area -> type.getType().equals(area.getType()));
    }
}
