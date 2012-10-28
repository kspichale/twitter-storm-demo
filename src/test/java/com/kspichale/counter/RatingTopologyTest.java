package com.kspichale.counter;

import org.junit.Test;

import com.kspichale.strom_demo.RatingTopology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.utils.Utils;

public class RatingTopologyTest {

	@Test
	public void test() {
		Config conf = new Config();
		conf.setDebug(false);
		conf.setNumWorkers(4);

		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("rating", conf, RatingTopology.buildTopology(LoggingData.createTestData()));
		Utils.sleep(10000);
		cluster.killTopology("rating");
		cluster.shutdown();
	}
}
