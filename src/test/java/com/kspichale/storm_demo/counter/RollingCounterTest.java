package com.kspichale.storm_demo.counter;

import org.joda.time.DateTime;
import org.junit.Test;

import com.kspichale.storm_demo.counter.RollingCounter;

import static org.fest.assertions.Assertions.assertThat;

public class RollingCounterTest {

	@Test
	public void testCounter() {
		final DateTime now = DateTime.now();
		final RollingCounter rollingCounter = new RollingCounter();
		rollingCounter.increment(now.plusMinutes(5));
		rollingCounter.increment(now.plusMinutes(20));
		rollingCounter.increment(now.plusMinutes(185));
		assertThat(rollingCounter.getSum()).isEqualTo(2);
	}
	
}
