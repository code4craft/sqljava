package us.codecraft.sqljava.tools;

import java.lang.reflect.Method;

/**
 * @author yihua.huang@dianping.com
 * @date 2013-1-6
 */
public class RefctorBenchmark {

	private int round = 5;
	private int count = 10000000;

	private static class TestClass {
		private String name;

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

	}

	public void refctorGet(TestClass[] objects) throws Exception {
		for (int i = 0; i < count; i++) {
			Method declaredMethod = TestClass.class
					.getDeclaredMethod("getName");
			declaredMethod.invoke(objects[i]);
		}
	}

	public void refctorGetCache(TestClass[] objects) throws Exception {
		Method declaredMethod = TestClass.class.getDeclaredMethod("getName");
		for (int i = 0; i < count; i++) {
			declaredMethod.invoke(objects[i]);
		}
	}

	public void directGet(TestClass[] objects) throws Exception {
		for (int i = 0; i < count; i++) {
			objects[i].getName();
		}
	}

	public TestClass[] constructObjects() {
		TestClass[] objects = new TestClass[count];
		for (int i = 0; i < count; i++) {
			objects[i] = new TestClass();
			objects[i].setName("a");
		}
		return objects;
	}

	public void benchmark() throws Exception {
		TestClass[] objects = constructObjects();
		PerformanceTimer refctorGetTimer = new PerformanceTimer(
				"refctorGetTimer", round, true);
		PerformanceTimer directGetTimer = new PerformanceTimer(
				"directGetTimer", round, true);
		PerformanceTimer refctorGetCacheTimer = new PerformanceTimer(
				"refctorGetCacheTimer", round, true);
		for (int i = 0; i < round; i++) {
			refctorGetTimer.start(i);
			refctorGet(objects);
			refctorGetTimer.end(i);
			directGetTimer.start(i);
			directGet(objects);
			directGetTimer.end(i);
			refctorGetCacheTimer.start(i);
			refctorGetCache(objects);
			refctorGetCacheTimer.end(i);
		}
		refctorGetCacheTimer.showAverage(round);
		refctorGetTimer.showAverage(round);
		directGetTimer.showAverage(round);
	}

	public static void main(String[] args) throws Exception {
		RefctorBenchmark benchmark = new RefctorBenchmark();
		benchmark.benchmark();
	}
}
