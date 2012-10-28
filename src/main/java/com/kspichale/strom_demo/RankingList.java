package com.kspichale.strom_demo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;

public class RankingList implements Iterable<ListItem>, Serializable {

	private static final long serialVersionUID = -1842234226746416412L;

	private final int SIZE = 100;

	private List<ListItem> items = new ArrayList<ListItem>();

	public void add(final ListItem item) {
		items.add(item);
	}

	public void sortAndTrim() {
		Collections.sort(items);
		final DateTime theshold = DateTime.now().minusHours(3);
		final List<ListItem> result = new ArrayList<ListItem>();
		for (final ListItem item : items) {
			if (theshold.isBefore(item.getDateTime())) {
				result.add(item);
				if (result.size() == SIZE) {
					break;
				}
			}
		}
		items = result;
	}

	@Override
	public Iterator<ListItem> iterator() {
		return items.iterator();
	}
}
