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

package com.sangupta.bloomfilter;

import java.nio.charset.Charset;
import java.util.Collection;

import com.sangupta.bloomfilter.decompose.Decomposer;

/**
 * A simple bloom filter contract for everyone.
 *  
 * Cheat sheet:
 *
 * m: total bits
 * n: expected insertions
 * k: number of hashes per element
 * b: m/n, bits per insertion
 * p: expected false positive probability
 *
 * 1) Optimal k = b * ln2
 * 2) p = (1 - e ^ (-kn/m))^k
 * 3) For optimal k: p = 2 ^ (-k) ~= 0.6185^b
 * 4) For optimal k: m = -nlnp / ((ln2) ^ 2)
 * 
 * @author sangupta
 *
 * @param <T> the type of objects to be stored in the filter
 */
public interface BloomFilter<T> {
	
	/**
	 * Add the given value represented as bytes in to the bloom filter.
	 * 
	 * @param bytes
	 * @return
	 */
	public boolean add(byte[] bytes);
	
	/**
	 * Add the given value object to the bloom filter.
	 * 
	 * @param value
	 * @return
	 */
	public boolean add(T value);
	
	/**
	 * Add all the values represented as a collection of objects to the bloom filter.
	 * 
	 * @param values
	 * @return
	 */
	public boolean addAll(Collection<T> values);
	
	/**
	 * Check if the value represented as byte-array is present in the bloom filter or not.
	 * 
	 * @param bytes
	 * @return
	 */
	public boolean contains(byte[] bytes);
	
	/**
	 * Check if the value object is present in the bloom filter or not.
	 * 
	 * @param value
	 * @return
	 */
	public boolean contains(T value);
	
	/**
	 * Check if all the values represented as a collection of objects are present in the bloom
	 * filter or not.
	 * 
	 * @param values
	 * @return
	 */
	public boolean containsAll(Collection<T> values);

	/**
	 * Set the {@link Charset} for the given name for converting objects to byte-arrays.
	 * 
	 * @param charset
	 */
	public void setCharset(String charsetName);

	/**
	 * Set the {@link Charset} for converting objects to byte-arrays.
	 * 
	 * @param charset
	 */
	public void setCharset(Charset charset);

	/**
	 * Get the current custom object decomposer.
	 * 
	 * @return
	 */
	public Decomposer<T> getObjectDecomposer();
	
	/**
	 * Return the number of bits being used by the filter.
	 * 
	 * @return the number of bits used by the filter
	 */
	public int getNumberOfBits();
	
	/**
	 * Estimate the current false positive rate (approximated) when given number
	 * of elements have been inserted in to the filter.
	 * 
	 * @param numInsertedElements
	 *            the number of elements inserted into the filter
	 * 
	 * @return the approximated false positive rate
	 */
	public double getFalsePositiveProbability(int numInsertedElements);

}
