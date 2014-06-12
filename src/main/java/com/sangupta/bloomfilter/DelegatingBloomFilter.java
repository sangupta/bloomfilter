/*************************************************************************
 *
 * MultiPLX Confidential
 * _____________________
 *
 * Copyright (C) 2012-2014, MultiPLX Founders.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains the 
 * property of MultiPLX and its founders. The intellectual and technical 
 * concepts contained herein are proprietary to the MultiPLX owners 
 * mentioned elsewhere, and may be covered by U.S. and Foreign Patents, 
 * patents in process, and are protected by trade secret or copyright law. 
 * Dissemination of this information or reproduction of this material is 
 * strictly forbidden unless prior written permission is obtained from 
 * all persons mentioned before. Please see project license for more 
 * details.
 *
 **************************************************************************/

package com.sangupta.bloomfilter;

import java.nio.charset.Charset;
import java.util.Collection;

import com.sangupta.bloomfilter.decompose.Decomposer;

/**
 * @author sangupta
 *
 */
public abstract class DelegatingBloomFilter<T> implements BloomFilter<T> {
	
	protected final BloomFilter<T> originalBloomFilter;
	
	public DelegatingBloomFilter(BloomFilter<T> original) {
		this.originalBloomFilter = original;
	}

	/**
	 * @see com.sangupta.bloomfilter.BloomFilter#add(byte[])
	 */
	@Override
	public boolean add(byte[] bytes) {
		return this.originalBloomFilter.add(bytes);
	}

	/**
	 * @see com.sangupta.bloomfilter.BloomFilter#add(java.lang.Object)
	 */
	@Override
	public boolean add(T value) {
		return this.originalBloomFilter.add(value);
	}

	/**
	 * @see com.sangupta.bloomfilter.BloomFilter#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<T> values) {
		return this.originalBloomFilter.addAll(values);
	}

	/**
	 * @see com.sangupta.bloomfilter.BloomFilter#contains(byte[])
	 */
	@Override
	public boolean contains(byte[] bytes) {
		return this.originalBloomFilter.contains(bytes);
	}

	/**
	 * @see com.sangupta.bloomfilter.BloomFilter#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(T value) {
		return this.originalBloomFilter.contains(value);
	}

	/**
	 * @see com.sangupta.bloomfilter.BloomFilter#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<T> values) {
		return this.originalBloomFilter.containsAll(values);
	}

	/**
	 * @see com.sangupta.bloomfilter.BloomFilter#setCharset(java.lang.String)
	 */
	@Override
	public void setCharset(String charsetName) {
		this.originalBloomFilter.setCharset(charsetName);
	}

	/**
	 * @see com.sangupta.bloomfilter.BloomFilter#setCharset(java.nio.charset.Charset)
	 */
	@Override
	public void setCharset(Charset charset) {
		this.originalBloomFilter.setCharset(charset);
	}

	/**
	 * @see com.sangupta.bloomfilter.BloomFilter#getObjectDecomposer()
	 */
	@Override
	public Decomposer<T> getObjectDecomposer() {
		return this.originalBloomFilter.getObjectDecomposer();
	}

	/**
	 * @see com.sangupta.bloomfilter.BloomFilter#getNumberOfBits()
	 */
	@Override
	public int getNumberOfBits() {
		return this.originalBloomFilter.getNumberOfBits();
	}

	/**
	 * @see com.sangupta.bloomfilter.BloomFilter#getFalsePositiveProbability(int)
	 */
	@Override
	public double getFalsePositiveProbability(int numInsertedElements) {
		return this.originalBloomFilter.getFalsePositiveProbability(numInsertedElements);
	}

	/**
	 * @see com.sangupta.bloomfilter.BloomFilter#close()
	 */
	@Override
	public void close() {
		this.originalBloomFilter.close();
	}
}
