package us.codecraft.sqljava;

import java.util.List;

/**
 * @author yihua.huang@dianping.com
 * @date 2013-1-6
 */
public interface Table<T> {

	public Table<T> index(String field);

	public List<T> orderBy(String field);

	public List<T> where(String exp);

	public Select<T> select();

	public int insert(T t, String exp);

	public <U, V> Join<U, T, V> join(Table<V> table);

	public <U, V> Join<U, T, V> map(Mapper<U, T> mapper);
}
