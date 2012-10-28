package com.kspichale.counter;

import java.text.ParseException;

import org.junit.Test;

import com.kspichale.strom_demo.LogfileParser;

public class LogfileParserTest {

	@Test
	public void parsingTest() throws ParseException {
		final String line = "123.45.67.89 - - [27/Nov/2000:09:27:09 -0400] \"GET /java/javaResources.html HTTP/1.0\" 200 10450 \"-\" \"Mozilla/4.6 [en] (X11; U; OpenBSD 2.8 i386; Nav)\"";
		new LogfileParser().parseLine(line);
	}

}
