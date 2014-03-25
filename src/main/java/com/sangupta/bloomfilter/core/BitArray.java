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

package com.sangupta.bloomfilter.core;

import java.io.Closeable;

/**
 * A contract for all implementations of bit-arrays. This provides
 * specific methods that will be needed for working with bloom filters.
 *  
 * @author sangupta
 *
 */
public interface BitArray extends Closeable {

	/**
	 * Get the bit at index
	 * 
	 * @param index
	 *            the index of the bit in the array
	 * 
	 * @return <code>true<code> if the but is set, <code>false</code> otherwise
	 */
	public boolean getBit(int index);
	
	/**
	 * Set the bit at index
	 * 
	 * @param index
	 *            the index of the bit in the array
	 * 
	 * @return <code>true</code> if the bit was updated, <code>false</code>
	 *         otherwise.
	 * 
	 */
	public boolean setBit(int index);
	
	/**
	 * Clear all bits in the array.
	 * 
	 */
	public void clear();
	
	/**
	 * Clear a given bit at the index.
	 * 
	 * @param index
	 *            the index of the bit in the array
	 */
	public void clearBit(int index);
	
	/**
	 * Set the bit at index if the bit is unset.
	 * 
	 * @param index
	 *            the index of the bit in the array
	 * 
	 * @return <code>true</code> if the bit was updated, <code>false</code>
	 *         otherwise.
	 */
	public boolean setBitIfUnset(int index);

	/**
	 * Do a Boolean OR with the second {@link BitArray}.
	 * 
	 * @param bitArray
	 *            the bitArray to OR with
	 */
	public void or(BitArray bitArray);
	
	/**
	 * Do a Boolean AND with the second {@link BitArray}.
	 * 
	 * @param bitArray
	 *            the bitArray to AND with
	 */
	public void and(BitArray bitArray);
	
	/**
	 * The space used by this {@link BitArray} in number of bytes.
	 * 
	 * @return
	 */
	public int bitSize();

}
