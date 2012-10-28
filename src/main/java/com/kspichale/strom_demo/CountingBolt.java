package com.kspichale.strom_demo;

import java.util.Date;
import java.util.Map;

import com.kspichale.storm_demo.counter.CounterMap;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

@SuppressWarnings("rawtypes")
public class CountingBolt implements IRichBolt {

	private static final long serialVersionUID = 1L;
	private OutputCollector collector;

	private CounterMap counterManager = new CounterMap();

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		final String url = input.getString(0);
		final Date date = (Date) input.getValue(1);
		final long count = counterManager.increment(url, date);
		try {
			collector.emit(new Values(url, date, count));
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
		declarer.declare(new Fields("url", "date", "count"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
