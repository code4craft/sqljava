package us.codecraft.sqljava.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang3.StringUtils;

/**
 * @author yihua.huang@dianping.com
 * @date 2013-3-15
 */
public class ClassUtils {

	private final static Map<String, Method> INNNER_CACHE = new WeakHashMap<String, Method>();
	private final static ReentrantReadWriteLock INNER_CACHE_LOCK = new ReentrantReadWriteLock();

	@SuppressWarnings({ "rawtypes" })
	private static Method getFromCache(Class clazz, String name) {
		String key = clazz.getCanonicalName() + "_" + name;
		try {
			INNER_CACHE_LOCK.readLock().lock();
			return INNNER_CACHE.get(key);
		} finally {
			INNER_CACHE_LOCK.readLock().unlock();
		}
	}

	@SuppressWarnings({ "rawtypes" })
	private static void setToCache(Class clazz, String name, Method method) {
		String key = clazz.getCanonicalName() + "_" + name;
		try {
			INNER_CACHE_LOCK.writeLock().lock();
			INNNER_CACHE.put(key, method);
		} finally {
			INNER_CACHE_LOCK.writeLock().unlock();
		}
	}

	@SuppressWarnings("rawtypes")
	public static Method getGetterMethod(Class clazz, String name) {
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException("name must not be null");
		}
		name = "get" + StringUtils.capitalize(name);
		return getMethod(clazz, name);

	}

	@SuppressWarnings({ "rawtypes" })
	public static Method getSetterMethod(Class clazz, String name) {
		if (StringUtils.isEmpty(name)) {
			throw new IllegalArgumentException("name must not be null");
		}
		name = "set" + StringUtils.capitalize(name);
		return getMethod(clazz, name);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Method getMethod(Class clazz, String name) {
		Method method = getFromCache(clazz, name);
		if (method != null) {
			return method;
		}
		try {
			method = clazz.getDeclaredMethod(name);
			setToCache(clazz, name, method);
			return method;
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("get method \"" + name
					+ "\" failed", e);
		} catch (SecurityException e) {
			throw new IllegalArgumentException("get method \"" + name
					+ "\" failed", e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <V, T> V invokeGetter(T rawObject, Method getterMethod) {
		try {
			V resultObject = (V) getterMethod.invoke(rawObject);
			return resultObject;
		} catch (InvocationTargetException e) {
			throw new RuntimeException("get field fail for elment: "
					+ rawObject, e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("get field fail for elment: "
					+ rawObject, e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("get field fail for elment: "
					+ rawObject, e);
		}
	}
}
