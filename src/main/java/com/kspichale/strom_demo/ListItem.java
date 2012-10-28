package com.kspichale.strom_demo;

import java.io.Serializable;
import java.util.Date;

import org.joda.time.DateTime;

public class ListItem implements Comparable<ListItem>, Serializable {

	private final String url;
	private final DateTime dateTime;
	private final Long count;

	public ListItem(final String url, final Date date, final long count) {
		super();
		this.url = url;
		this.dateTime = new DateTime(date.getTime());
		this.count = count;
	}

	@Override
	public int compareTo(ListItem o) {
		return count.compareTo(o.count);
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public String getUrl() {
		return url;
	}

	public long getCount() {
		return count;
	}

	public String toString() {
		return url + " was called " + count + "times";
	}
}
