package com.kspichale.strom_demo;

import java.util.List;

import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class RankingTopology {

	private static final String RANKING_LIST_BOLT = "rankingListBolt";
	private static final String COUNTING_BOLT = "countingBolt";
	private static final String DISPATCHER_BOLT = "dispatcherBolt";
	private static final String LOGGING_SPOUT = "loggingSpout";

	public static StormTopology buildTopology(final List<String> urls) {
		final TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout(LOGGING_SPOUT, new UrlSpout(urls), 1);
		builder.setBolt(DISPATCHER_BOLT, new DispatcherBolt(), 2).shuffleGrouping(LOGGING_SPOUT);
		builder.setBolt(COUNTING_BOLT, new CountingBolt(), 2).fieldsGrouping(DISPATCHER_BOLT, new Fields("url"));
		builder.setBolt(RANKING_LIST_BOLT, new RankingListBolt(), 1).globalGrouping(COUNTING_BOLT);
		return builder.createTopology();
	}
}
