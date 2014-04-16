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

package com.sangupta.bloomfilter.hash;

/**
 * A contract for all implementation that want to provide a hash
 * function for use inside the bloom filters.
 * 
 * @author sangupta
 *
 */
public interface HashFunction {
	
	/**
	 * Whether the hash function returns a single long hash or multiple long
	 * values.
	 * 
	 * @return whether the hash function returns a single value of multiple
	 *         values
	 */
	public boolean isSingleValued();
	
	/**
	 * Return the hash of the bytes as long.
	 * 
	 * @param bytes
	 *            the bytes to be hashed
	 * 
	 * @return the generated hash value
	 */
	public long hash(byte[] bytes);
	
	/**
	 * Return the hash of the bytes as a long array.
	 * 
	 * @param bytes
	 *            the bytes to be hashed
	 * 
	 * @return the generated hash value
	 */
	public long[] hashMultiple(byte[] bytes);

}
