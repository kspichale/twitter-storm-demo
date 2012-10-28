package com.kspichale.strom_demo;

import java.util.Date;

public class LoggingValues {

	public final Date date;

	public final String url;

	public LoggingValues(final Date date, final String url) {
		super();
		this.date = date;
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public Date getDate() {
		return date;
	}
}
