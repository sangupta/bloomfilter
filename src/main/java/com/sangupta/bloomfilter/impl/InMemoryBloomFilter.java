package com.sangupta.bloomfilter.impl;

import com.sangupta.bloomfilter.AbstractBloomFilter;
import com.sangupta.bloomfilter.core.BitArray;
import com.sangupta.bloomfilter.core.JavaBitSetArray;

/**
 * An in-memory implementation of the bloom filter. Not suitable for persistence.
 * 
 * @author sangupta
 *
 * @param <T>
 */
public class InMemoryBloomFilter<T> extends AbstractBloomFilter<T> {
	
	public InMemoryBloomFilter(int n, double fpp) {
		super(n, fpp);
	}

	@Override
	protected BitArray createBitArray(int numBits) {
		return new JavaBitSetArray(numBits);
	}
	
}
