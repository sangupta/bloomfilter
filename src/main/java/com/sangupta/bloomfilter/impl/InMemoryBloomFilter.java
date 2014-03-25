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

package com.sangupta.bloomfilter.impl;

import com.sangupta.bloomfilter.AbstractBloomFilter;
import com.sangupta.bloomfilter.core.BitArray;
import com.sangupta.bloomfilter.core.JavaBitSetArray;

/**
 * An in-memory implementation of the bloom filter. Not suitable for
 * persistence.
 * 
 * @author sangupta
 * 
 * @param <T>
 */
public class InMemoryBloomFilter<T> extends AbstractBloomFilter<T> {
	
	/**
	 * Constructor
	 * 
	 * @param n
	 *            the number of elements expected to be inserted in the bloom
	 *            filter
	 * 
	 * @param fpp
	 *            the expected max false positivity rate
	 */
	public InMemoryBloomFilter(int n, double fpp) {
		super(n, fpp);
	}

	/**
	 * Used a normal {@link JavaBitSetArray}.
	 * 
	 */
	@Override
	protected BitArray createBitArray(int numBits) {
		return new JavaBitSetArray(numBits);
	}
	
}
