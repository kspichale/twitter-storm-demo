package com.kspichale.strom_demo;

import java.util.Date;
import java.util.Map;

import org.mortbay.log.Log;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

@SuppressWarnings("rawtypes")
public class RankingListBolt implements IRichBolt {

	private static final long serialVersionUID = 1202638526061367283L;
	private RankingList rankingList = new RankingList();
	OutputCollector collector;

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		// A thread will periodically report the latest ranking list
		Thread reporter = new Thread() {
			public void run() {
				while (true) {
					Log.info("Reporting start.");
					synchronized (rankingList) {
						rankingList.sortAndTrim();
						for (final ListItem item : rankingList) {
							System.out.println(item);
							RankingListBolt.this.collector.emit(new Values(item.getUrl(), item.getDateTime(), item.getCount()));
						}
					}
					Log.info("Reporting finished.");
					Utils.sleep(1000);
				}
			};
		};
		reporter.start(); // we don't care about properly finalizing this thread
							// in this toy example...
	}

	@Override
	public void execute(Tuple input) {
		final String url = input.getString(0);
		final Date date = (Date) input.getValue(1);
		final long count = input.getLong(2);
		final ListItem item = new ListItem(url, date, count);
		synchronized (rankingList) {
			rankingList.add(item);
		}
	}

	@Override
	public void cleanup() {
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("url", "date", "count"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
