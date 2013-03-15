package us.codecraft.sqljava.common;

import java.lang.reflect.Method;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author yihua.huang@dianping.com
 * @date 2013-3-15
 */
public class ClassUtilsTest {

	@Test
	public void testGetGetter() throws NoSuchMethodException, SecurityException {
		Method getterMethod = ClassUtils.getGetterMethod(String.class, "bytes");
		Assert.assertNotNull(getterMethod);
	}

}
