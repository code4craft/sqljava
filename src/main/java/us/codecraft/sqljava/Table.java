package us.codecraft.sqljava;

import java.util.List;

import us.codecraft.sqljava.common.Mapper;

/**
 * @author yihua.huang@dianping.com
 * @date 2013-1-6
 */
public interface Table<T> {

	public Table<T> index(String field);

	public Table<T> orderBy(String field);

	public Select<T> select();

	public <U, V> Join<U, T, V> join(Table<V> table);

	public <U, V> Join<U, T, V> map(Mapper<U, T> mapper);

	public List<T> toList();
}
