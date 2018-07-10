package com.cybertaotao.talkhome.filter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFilter<T> implements Filter<T> {
	public List<Filter<T>> filters = null;

	public T apply(T message) {
		message = this.filter(message);
		if (this.filters != null) {
			for (Filter<T> f : filters) {
				message = f.filter(message);
			}
		}
		return message;
		// filters.stream().forEach(x->x.filter(message));
	}

	public void appendFilter(Filter<T> apFilter) {
		if (this.filters == null)
			this.filters = new ArrayList<>();
		filters.add(apFilter);
	}
}
