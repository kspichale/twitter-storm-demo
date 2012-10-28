package com.kspichale.storm_demo.counter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;


public class CounterMap implements Serializable {

	private final Map<String, RollingCounter> counters = new HashMap<String, RollingCounter>();

	public long increment(final String url, final Date date) {
		final RollingCounter counter = getCounter(url);
		counter.increment(new DateTime(date.getTime()));
		return counter.getSum();
	}

	private RollingCounter getCounter(final String url) {
		final RollingCounter counter = counters.get(url);
		if (counter != null) {
			return counter;
		}

		final RollingCounter newCounter = new RollingCounter();
		counters.put(url, newCounter);
		return newCounter;
	}
}
