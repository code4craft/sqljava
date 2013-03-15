package us.codecraft.sqljava;

import us.codecraft.sqljava.common.Mapper;


/**
 * @author yihua.huang@dianping.com
 * @date 2013-1-6
 */
public interface Join<U, A, B> {

	public Join<U, A, B> map(Mapper<U, B> mapper);

	public Table<U> on(String exp);

}
