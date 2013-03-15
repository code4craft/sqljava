package us.codecraft.sqljava.tools;

/**
 * @author cairne huangyihua@diandian.com
 * @date 2012-6-22
 */
public class PerformanceTimer {

	private long[] time;

	private boolean silence;

	private String name;

	/**
     * 
     */
	public PerformanceTimer(String name) {
		this(name, 100, false);
	}

	public PerformanceTimer(String name, int size, boolean silence) {
		time = new long[size];
		this.silence = silence;
		this.name = name;
	}

	public PerformanceTimer(String name, int size) {
		this(name, size, false);
	}

	public void reset() {
		time[0] = System.currentTimeMillis();
	}

	public void print() {
		System.out.println(name + "time cast "
				+ (System.currentTimeMillis() - time[0]));
		time[0] = System.currentTimeMillis();
	}

	public void start(int round) {
		time[round] = System.currentTimeMillis();
	}

	public void end(int round) {
		time[round] = System.currentTimeMillis() - time[round];
		if (!silence) {
			System.out.println(name + " time cost " + time[round] + " round "
					+ round);
		}
	}

	public void showAverage(int round) {
		System.out.println(name + " average time cost for " + round
				+ " rounds is " + average(round));
	}

	public long average(int round) {
		long total = 0;
		for (int i = 0; i < round; i++) {
			total += time[i];
		}
		return total / round;

	}

}
