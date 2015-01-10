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

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

/**
 * 
 * @author sangupta
 *
 */
public class TestJavaBitSetArray {
	
	private static final int MILLION = 1000 * 1000;
	
	@Test
	public void testJavaBitSetArray() {
		JavaBitSetArray bits = null;
		
		try {
			bits = new JavaBitSetArray(MILLION);
			for(int i = 0; i < MILLION; i++) {
				Assert.assertFalse(bits.getBit(i));
				bits.setBit(i);
				Assert.assertTrue(bits.getBit(i));
				bits.clearBit(i);
				Assert.assertFalse(bits.getBit(i));
			}
		} finally {
			if(bits != null) {
				try {
					bits.close();
				} catch(IOException e) {
					// eat up
				}
			}
		}
	}

}