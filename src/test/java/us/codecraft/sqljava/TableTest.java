package us.codecraft.sqljava;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author yihua.huang@dianping.com
 * @date 2013-1-6
 */
@SuppressWarnings("unused")
public class TableTest {

	private static class A {
		private int aid;
		private String name;
		private String bame;

		public int getAid() {
			return aid;
		}

		public void setAid(int aid) {
			this.aid = aid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getBame() {
			return bame;
		}

		public void setBame(String bame) {
			this.bame = bame;
		}

	}

	private static class B {
		private int bid;
		private String bname;

		public int getBid() {
			return bid;
		}

		public void setBid(int bid) {
			this.bid = bid;
		}

		public String getBname() {
			return bname;
		}

		public void setBname(String bname) {
			this.bname = bname;
		}

	}

	@Test
	public void testJoin() {
		ArrayList<A> collectionA = new ArrayList<A>();
		Table<A> tableA = Tables.newTable(collectionA);
		ArrayList<B> collectionB = new ArrayList<B>();
		Table<B> tableB = Tables.newTable(collectionB);
		List<A> on = tableA.<A, B> join(tableB)
				.map(new Mapper<A, TableTest.B>() {

					public void map(A a, B object) {
						a.setBame(object.getBname());
						return;
					}
				}).on("aid=bid");
	}
}
