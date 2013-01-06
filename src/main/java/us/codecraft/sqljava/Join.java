package us.codecraft.sqljava;

import java.util.List;

/**
 * @author yihua.huang@dianping.com
 * @date 2013-1-6
 */
public interface Join<U, A, B> {

	public Join<U, A, B> map(Mapper<U, B> mapper);

	public List<U> on(String exp);

}
