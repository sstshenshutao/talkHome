package com.cybertaotao.talkhome.filter;

public interface Filter<T> {
	public T filter(T message);
	public T apply(T message) ;
	public void appendFilter(Filter<T> apFilter);
}
