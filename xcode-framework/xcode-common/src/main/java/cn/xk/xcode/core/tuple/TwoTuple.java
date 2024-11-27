package cn.xk.xcode.core.tuple;

/**
 * @Author xuk
 * @Date 2024/11/27 14:51
 * @Version 1.0.0
 * @Description TwoTuple
 **/
public class TwoTuple<K, V> {

    private K k;
    private V v;

    public TwoTuple(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public K getFirstValue() {
        return this.k;
    }

    public V getSecondValue() {
        return this.v;
    }

    public void setFirstValue(K k) {
        this.k = k;
    }

    public void setSecondValue(V v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return "(" +
                k +
                ", " +
                v +
                ")";
    }
}
