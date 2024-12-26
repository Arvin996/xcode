package cn.xk.xcode.entity;

/**
 * @Author xuk
 * @Date 2024/7/4 15:59
 * @Version 1.0
 * @Description IBasedDictEnum 要使用本模块提供的字典管理 所有的字典都要实现这个接口
 * 如果不实现则不管
 */
public interface IEnumDict {

    String getCode();

    String getName();

    String getDesc();

}
