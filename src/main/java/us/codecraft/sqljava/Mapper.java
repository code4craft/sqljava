package us.codecraft.sqljava;

/**
 * @author yihua.huang@dianping.com
 * @date 2013-1-6
 */
public interface Mapper<T, F> {

	public void map(T target, F from);

}
