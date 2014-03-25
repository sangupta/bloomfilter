package com.sangupta.bloomfilter.decompose;

/**
 * 
 * @author sangupta
 *
 * @param <T>
 */
public interface Decomposer<T> {
	
	public void decompose(T object, ByteSink sink);

}
