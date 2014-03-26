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

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

/**
 * JUnit tests for {@link FileBackedBitArray}.
 * 
 * @author sangupta
 *
 */
public class TestFileBackedBitArray {
	
	private static final int MAX = 1 * 1000 * 1000;
	
	@Test
	public void testFileBackedBitArray() {
		FileBackedBitArray ba = null;
		try {
			File file = File.createTempFile("bitarray", ".bits");
			file.deleteOnExit();
			
			long start = System.currentTimeMillis();
			ba = new FileBackedBitArray(file, MAX);
			long end = System.currentTimeMillis();
			
			System.out.println("Initialized in " + (end - start) + " millis");
			
			// start iterating
			start = System.currentTimeMillis();
			for(int index = 0; index < MAX; index++) {
				Assert.assertFalse(ba.getBit(index));
				ba.setBit(index);
				Assert.assertTrue(ba.getBit(index));
				ba.clearBit(index);
				Assert.assertFalse(ba.getBit(index));
			}
			end = System.currentTimeMillis();
			
			System.out.println("Test complete in " + (end - start) + " millis");
		} catch(Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} finally {
			if(ba != null) {
				try {
					ba.close();
				} catch (IOException e) {
					// eat up
				}
			}
		}
	}

}
