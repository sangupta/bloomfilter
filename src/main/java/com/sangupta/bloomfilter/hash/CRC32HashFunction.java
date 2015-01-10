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

import java.util.zip.CRC32;

/**
 * A CRC32 hash function.
 * 
 * @author sangupta
 * @since 1.0
 */
public class CRC32HashFunction implements HashFunction {

	@Override
	public boolean isSingleValued() {
		return true;
	}

	@Override
	public long hash(byte[] bytes) {
		CRC32 crc32 = new CRC32();
		crc32.update(bytes);
		return crc32.getValue();
	}

	@Override
	public long[] hashMultiple(byte[] bytes) {
		return null;
	}

}