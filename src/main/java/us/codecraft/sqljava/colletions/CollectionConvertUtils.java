package us.codecraft.sqljava.colletions;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.codecraft.sqljava.common.ClassUtils;
import us.codecraft.sqljava.common.ConvertMapper;
import us.codecraft.sqljava.common.FieldGetter;
import us.codecraft.sqljava.common.Mapper;

/**
 * Some collection utilities used for simplify collection converts.
 * 
 * @author yihua.huang@dianping.com
 * @date 2013-3-15
 */
public class CollectionConvertUtils {

	/**
	 * Use a field of raw element to construct a new list.Using reflection in
	 * Java (to avoid explosion of class). It may cause slightly performance
	 * drawback and is not suggested to use in high-performance-requiring
	 * circumstance.
	 * 
	 * @param rawCollection
	 * @param field
	 * @return
	 */
	public static <V, T> List<V> filter(Collection<T> rawCollection,
			String field) {
		if (rawCollection == null || rawCollection.size() == 0) {
			return Collections.emptyList();
		}
		Method getterMethod = ClassUtils.getGetterMethod(rawCollection
				.iterator().next().getClass(), field);
		List<V> resultColletion = new ArrayList<V>();
		for (T rawElement : rawCollection) {
			V resultElemet = ClassUtils.<V, T> invokeGetter(rawElement,
					getterMethod);
			resultColletion.add(resultElemet);
		}
		return resultColletion;
	}

	/**
	 * Use a field of raw element to construct a new list.
	 * 
	 * @param rawCollection
	 * @param field
	 * @return
	 */
	public static <V, T> List<V> filter(Collection<T> rawCollection,
			FieldGetter<T, V> fieldGetter) {
		if (rawCollection == null || rawCollection.size() == 0) {
			return Collections.emptyList();
		}
		List<V> resultColletion = new ArrayList<V>();
		for (T rawElement : rawCollection) {
			V resultElemet = fieldGetter.get(rawElement);
			resultColletion.add(resultElemet);
		}
		return resultColletion;
	}

	/**
	 * Use a field as key, raw element as value to construct a new map.Using
	 * reflection in Java (to avoid explosion of class). It may cause slightly
	 * performance drawback and is not suggested to use in
	 * high-performance-requiring circumstance.
	 * 
	 * @param rawCollection
	 * @param field
	 * @return
	 */
	public static <K, T> Map<K, T> filterMap(Collection<T> rawCollection,
			String field) {
		if (rawCollection == null || rawCollection.size() == 0) {
			return Collections.emptyMap();
		}
		Method getterMethod = ClassUtils.getGetterMethod(rawCollection
				.iterator().next().getClass(), field);
		Map<K, T> resultMap = new HashMap<K, T>();
		for (T rawElement : rawCollection) {
			K resultElemet = ClassUtils.<K, T> invokeGetter(rawElement,
					getterMethod);
			resultMap.put(resultElemet, rawElement);
		}
		return resultMap;
	}

	/**
	 * Use a
	 * 
	 * @param rawCollection
	 * @param mapper
	 * @return
	 */
	public static <A, B> List<B> convert(Collection<A> rawCollection,
			ConvertMapper<A, B> mapper) {
		if (rawCollection == null || rawCollection.size() == 0) {
			return Collections.emptyList();
		}
		List<B> resultList = new ArrayList<B>();
		for (A rawElement : rawCollection) {
			resultList.add(mapper.map(rawElement));
		}
		return resultList;
	}

	/**
	 * Use a field as key, raw element as value to construct a new map.
	 * 
	 * @param rawCollection
	 * @param field
	 * @return
	 */
	public static <K, T> Map<K, T> filterMap(Collection<T> rawCollection,
			FieldGetter<T, K> fieldGetter) {
		if (rawCollection == null || rawCollection.size() == 0) {
			return Collections.emptyMap();
		}
		Map<K, T> resultMap = new HashMap<K, T>();
		for (T rawElement : rawCollection) {
			K resultElemet = fieldGetter.get(rawElement);
			resultMap.put(resultElemet, rawElement);
		}
		return resultMap;
	}

	/**
	 * Like inner join operation in sql.
	 * 
	 * @param listA
	 * @param listB
	 * @param fieldGetterA
	 * @param fieldGetterB
	 * @param mapper
	 */
	public static <A, B, K> List<A> innerJoin(List<A> listA,
			FieldGetter<A, K> fieldGetterA, List<B> listB,
			FieldGetter<B, K> fieldGetterB, Mapper<B, A> mapper) {
		return join0(listA, fieldGetterA, listB, fieldGetterB, mapper, false);
	}

	/**
	 * Like left join operation in sql.
	 * 
	 * @param listA
	 * @param listB
	 * @param fieldGetterA
	 * @param fieldGetterB
	 * @param mapper
	 */
	public static <A, B, K> List<A> leftJoin(List<A> listA,
			FieldGetter<A, K> fieldGetterA, List<B> listB,
			FieldGetter<B, K> fieldGetterB, Mapper<B, A> mapper) {
		return join0(listA, fieldGetterA, listB, fieldGetterB, mapper, true);
	}

	private static class JoinCheckResult<T> {
		final boolean shouldImmediateReturn;
		final List<T> result;

		/**
		 * @param immediateReturn
		 * @param result
		 */
		public JoinCheckResult(boolean immediateReturn, List<T> result) {
			super();
			this.shouldImmediateReturn = immediateReturn;
			this.result = result;
		}

		public boolean shouldImmediateReturn() {
			return shouldImmediateReturn;
		}

		public List<T> getResult() {
			return result;
		}

	}

	private static <A, B, K> JoinCheckResult<A> doJoinCheck(List<A> listA,
			FieldGetter<A, K> fieldGetterA, List<B> listB,
			FieldGetter<B, K> fieldGetterB, Mapper<B, A> mapper,
			boolean leftJoin) {
		if (listA == null || listB == null || fieldGetterA == null
				|| fieldGetterB == null || mapper == null) {
			throw new IllegalArgumentException("Paramter should not by null");
		}
		if (listA.size() == 0) {
			return new JoinCheckResult<A>(true, Collections.<A> emptyList());
		}
		if (listB.size() == 0) {
			if (leftJoin) {
				return new JoinCheckResult<A>(true, new ArrayList<A>(listA));
			} else {
				return new JoinCheckResult<A>(true, Collections.<A> emptyList());
			}
		}
		return new JoinCheckResult<A>(false, null);
	}

	private static <A, B, K> List<A> join0(List<A> listA,
			FieldGetter<A, K> fieldGetterA, List<B> listB,
			FieldGetter<B, K> fieldGetterB, Mapper<B, A> mapper,
			boolean leftJoin) {
		JoinCheckResult<A> doJoinCheck = doJoinCheck(listA, fieldGetterA,
				listB, fieldGetterB, mapper, leftJoin);
		if (doJoinCheck.shouldImmediateReturn()) {
			return doJoinCheck.getResult();
		}
		Map<K, B> filterMap = filterMap(listB, fieldGetterB);
		List<A> resultList = new ArrayList<A>();
		for (A a : listA) {
			B b = filterMap.get(fieldGetterA.get(a));
			if (b != null) {
				a = mapper.map(b, a);
				resultList.add(a);
			} else if (leftJoin) {
				resultList.add(a);
			}
		}
		return resultList;
	}
}
