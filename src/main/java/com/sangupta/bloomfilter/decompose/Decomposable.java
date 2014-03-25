package com.sangupta.bloomfilter.decompose;

/**
 * Interface to signify that the object itself provides
 * functionality to be decomposed into a {@link ByteSink}
 * instance.
 * 
 * @author sangupta
 *
 */
public interface Decomposable {

	/**
	 * Decompose this object and render into the given {@link ByteSink} instance.
	 * 
	 * @param into
	 */
	public void decompose(ByteSink into);

}
