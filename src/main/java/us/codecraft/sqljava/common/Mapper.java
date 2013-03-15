package us.codecraft.sqljava.common;

/**
 * @author yihua.huang@dianping.com
 * @date 2013-1-6
 */
public interface Mapper<F, T> {

	public T map(F from, T target);

}
