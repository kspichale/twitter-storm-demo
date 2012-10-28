package com.kspichale.strom_demo;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

@SuppressWarnings("rawtypes")
public class DispatcherBolt implements IRichBolt {

	private static final long serialVersionUID = 1L;
	private OutputCollector collector;
	private LogfileParser parser = new LogfileParser();

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		final String feedUrl = input.getStringByField("feed");
		final LoggingValues parseLine = parser.parseLine(feedUrl);
		try {
			collector.emit(new Values(parseLine.getUrl(), parseLine.getDate()));
			collector.ack(input);
		} catch (Throwable t) {
			t.printStackTrace();
			collector.fail(input);
		}
	}

	@Override
	public void cleanup() {
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("url", "date"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
