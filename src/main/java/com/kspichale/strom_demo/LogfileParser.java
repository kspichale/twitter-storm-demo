package com.kspichale.strom_demo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogfileParser implements Serializable {

	public static final String LOGFILE_FORMAT = "^([\\d.]+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"";

	public static final String DATE_FORMAT = "dd/MMM/yyyy:HH:mm:ss Z";

	public static final int NUM_FIELDS = 9;

	private final Pattern pattern;

	public LogfileParser() {
		super();
		this.pattern = Pattern.compile(LOGFILE_FORMAT);
	}

	public LoggingValues parseLine(final String line) {

		final Matcher matcher = pattern.matcher(line);

		if (!matcher.matches() || NUM_FIELDS != matcher.groupCount()) {
			System.err.println("Bad log entry (or problem with RE?):");
			System.err.println(line);
			return null;
		}

		System.out.println("IP Address: " + matcher.group(1));
		System.out.println("Date&Time: " + matcher.group(4));
		System.out.println("Request: " + matcher.group(5));
		System.out.println("Response: " + matcher.group(6));
		System.out.println("Bytes Sent: " + matcher.group(7));
		if (!matcher.group(8).equals("-"))
			System.out.println("Referer: " + matcher.group(8));
		System.out.println("Browser: " + matcher.group(9));

		final String dateTime = matcher.group(4);

		final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Date parsedDate;
		try {
			parsedDate = sdf.parse(dateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		final String request = matcher.group(5);

		StringTokenizer st = new StringTokenizer(request);
		st.nextToken();
		final String url = st.nextToken();
		System.out.println(url);

		return new LoggingValues(parsedDate, url);
	}
}
