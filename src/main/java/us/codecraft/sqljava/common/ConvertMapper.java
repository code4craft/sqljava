package us.codecraft.sqljava.common;

/**
 * @author yihua.huang@dianping.com
 * @date 2013-3-15
 */
public interface ConvertMapper<F, T> {

	public T map(F from);
}
