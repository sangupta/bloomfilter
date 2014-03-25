package com.sangupta.bloomfilter.decompose;

import java.nio.charset.Charset;

/**
 * The default implementation of {@link Decomposer} that decomposes the object
 * by converting it to a {@link String} object using the
 * {@link Object#toString()} method.
 * 
 * To convert the {@link String} thus obtained into bytes, the default platform
 * {@link Charset} encoding is used.
 * 
 * @author sangupta
 * 
 */
public class DefaultDecomposer implements Decomposer<Object> {
	
	/**
	 * The default platform encoding
	 */
	private static final Charset DEFAULT_CHARSET = Charset.defaultCharset();

	/**
	 * Decompose the object
	 */
	@Override
	public void decompose(Object object, ByteSink sink) {
		if(object == null) {
			return;
		}
		
		sink.putBytes(object.toString().getBytes(DEFAULT_CHARSET));
	}

}
