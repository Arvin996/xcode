package cn.xk.xcode.core.tuple;

/**
 * @Author xuk
 * @Date 2024/11/27 14:57
 * @Version 1.0.0
 * @Description ThreeTuple
 **/
public class ThreeTuple<K, V, T> {

    private K k;
    private V v;
    private T t;

    public ThreeTuple(K k, V v, T t) {
        this.k = k;
        this.v = v;
        this.t = t;
    }

    public K getFirstValue() {
        return k;
    }

    public void setFirstValue(K k) {
        this.k = k;
    }

    public V getSecondValue() {
        return v;
    }

    public void setSecondValue(V v) {
        this.v = v;
    }

    public T getThirdValue() {
        return t;
    }

    public void setThirdValue(T t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "(" +
                k +
                v +
                t +
                ')';
    }


}
