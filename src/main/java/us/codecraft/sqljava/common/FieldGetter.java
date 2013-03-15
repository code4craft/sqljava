package us.codecraft.sqljava.common;

/**
 * @author yihua.huang@dianping.com
 * @date 2013-3-15
 */
public interface FieldGetter<T, V> {

	public V get(T object);

}
