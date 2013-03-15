package us.codecraft.sqljava.colletions;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import us.codecraft.sqljava.TableTest.A;
import us.codecraft.sqljava.TableTest.B;
import us.codecraft.sqljava.common.FieldGetter;
import us.codecraft.sqljava.common.Mapper;

/**
 * @author yihua.huang@dianping.com
 * @date 2013-3-15
 */
public class CollectionUtilsTest {

	@Test
	public void testFilter() {
		List<A> lista = new ArrayList<A>();
		A e = new A();
		e.setAid(1);
		lista.add(e);
		List<Integer> filter = CollectionConvertUtils.<Integer, A> filter(
				lista, "aid");
		Assert.assertEquals(new Integer(1), filter.get(0));
	}

	@Test
	public void testInnerJoin() {
		List<A> listA = new ArrayList<A>();
		A a = new A();
		a.setAid(1);
		listA.add(a);
		List<B> listB = new ArrayList<B>();
		B b = new B();
		b.setBid(1);
		b.setBname("hah");
		listB.add(b);
		List<A> innerJoin = CollectionConvertUtils.innerJoin(listA,
				new FieldGetter<A, Integer>() {
					@Override
					public Integer get(A object) {
						return object.getAid();
					}
				}, listB, new FieldGetter<B, Integer>() {
					@Override
					public Integer get(B object) {
						return object.getBid();
					}
				}, new Mapper<B, A>() {
					@Override
					public A map(B from, A target) {
						target.setBame(from.getBname());
						return target;
					}
				});
		Assert.assertEquals(b.getBname(), innerJoin.get(0).getBame());
		innerJoin = CollectionConvertUtils.innerJoin(listA, listB,
				"a.aid=b.bid", new Mapper<B, A>() {
					@Override
					public A map(B from, A target) {
						target.setBame(from.getBname());
						return target;
					}
				});
		Assert.assertEquals(b.getBname(), innerJoin.get(0).getBame());
	}
}
