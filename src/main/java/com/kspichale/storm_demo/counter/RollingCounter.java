package com.kspichale.storm_demo.counter;

import org.joda.time.DateTime;

public class RollingCounter {

	final long intervalMillis = 10 * 60 * 1000;
	final int SIZE = 18;

	final int[] counters = new int[SIZE];
	int pos = 0;
	long startTime;

	public RollingCounter() {
		super();
		startTime = DateTime.now().getMillis();
	}

	public void increment(final DateTime currentCall) {
		long minus = currentCall.getMillis() - startTime;
		long steps = (minus / intervalMillis) % SIZE;
		for (int i = 0; i < steps; i++) {
			counters[pos] = 0;
			pos = (pos + 1) % SIZE;
		}
		counters[pos]++;
	}

	public long getSum() {
		long sum = 0;
		for (int i : counters) {
			sum += i;
		}
		return sum;
	}

}
