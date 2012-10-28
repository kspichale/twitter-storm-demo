package com.kspichale.strom_demo;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

@SuppressWarnings("rawtypes")
public class UrlSpout implements IRichSpout {

	private SpoutOutputCollector collector;
	private Map conf;
	private TopologyContext context;

	private final Queue<String> queue = new LinkedList<String>();
	private final List<String> urls;

	public UrlSpout(List<String> urls) {
		this.urls = urls;
	}

	@Override
	public void open(final Map conf, final TopologyContext context,
			final SpoutOutputCollector collector) {
		this.collector = collector;
		this.conf = conf;
		this.context = context;
		for (String url : urls)
			queue.add(url);
	}

	@Override
	public void nextTuple() {
		String nextFeed = queue.poll();
		if (nextFeed != null) {
			System.out.println("FeedSpout: emitting = " + nextFeed);
			collector.emit(new Values(nextFeed), nextFeed);
		}
	}

	@Override
	public void ack(Object feedId) {
		// queue.add((String) feedId);
	}

	@Override
	public void fail(Object feedId) {
		// queue.add((String) feedId);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("feed"));
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
}
