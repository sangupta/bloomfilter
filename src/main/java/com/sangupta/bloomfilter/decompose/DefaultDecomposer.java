/**
 *
 * bloomfilter - Bloom filters for Java
 * Copyright (c) 2014, Sandeep Gupta
 * 
 * http://sangupta.com/projects/bloomfilter
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

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
		
		if(object instanceof String) {
			sink.putBytes(((String) object).getBytes(DEFAULT_CHARSET));
			return;
		}
		
		sink.putBytes(object.toString().getBytes(DEFAULT_CHARSET));
	}

}
