package com.kspichale.counter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.kspichale.strom_demo.LogfileParser;

public class LoggingData {

	public static final String URL1 = "/foo/resource1";
	public static final String URL2 = "/foo/resource2";
	public static final String URL3 = "/foo/resource3";

	public static List<String> createTestData() {
		final List<String> data = new ArrayList<String>();
		data.addAll(createTestData(4, URL1));
		data.addAll(createTestData(4, URL2));
		data.addAll(createTestData(4, URL3));
		return data;
	}

	public static List<String> createTestData(final int count, final String url) {
		final List<String> data = new ArrayList<String>(count);
		final DateTime now = DateTime.now();

		for (int i = 0; i < count; i++) {
			DateTime date = now.minusHours(i);
			SimpleDateFormat sdf = new SimpleDateFormat(LogfileParser.DATE_FORMAT);
			String formattedDate = sdf.format(date.toDate());
			data.add("123.45.67.89 - - [" + formattedDate + "] \"GET " + url
					+ " HTTP/1.0\" 200 10450 \"-\" \"Mozilla/4.6 [en] (X11; U; OpenBSD 2.8 i386; Nav)\"");
		}
		return data;
	}
}
