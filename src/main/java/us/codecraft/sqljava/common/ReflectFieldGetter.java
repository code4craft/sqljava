package us.codecraft.sqljava.common;

import java.lang.reflect.Method;

/**
 * Using reflection in Java (to avoid explosion of class). It may cause slightly
 * performance drawback and is not suggested to use in
 * high-performance-requiring circumstance.
 * 
 * @author yihua.huang@dianping.com
 * @date 2013-3-15
 */
public class ReflectFieldGetter<T, V> implements FieldGetter<T, V> {

	private final Method method;

	/**
	 * @param method
	 */
	public ReflectFieldGetter(Method method) {
		super();
		this.method = method;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see us.codecraft.sqljava.common.FieldGetter#get(java.lang.Object)
	 */
	@Override
	public V get(T object) {
		return ClassUtils.invokeGetter(object, method);
	}
}
