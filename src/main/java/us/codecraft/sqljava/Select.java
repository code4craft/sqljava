package us.codecraft.sqljava;

import java.util.List;

/**
 * @author yihua.huang@dianping.com
 * @date 2013-1-6
 */
public interface Select<T> {

	public Select<T> where(String exp);

	public Select<T> orderby(String field);

	public Select<T> orderbydesc(String field);

	public Select<T> limit(int limit);

	public Select<T> limit(int limit, int offset);

	public List<T> commit();

}
