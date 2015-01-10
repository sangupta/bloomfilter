/**
 *
 * bloomfilter - Bloom filters for Java
 * Copyright (c) 2014-2015, Sandeep Gupta
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

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;

import com.sangupta.bloomfilter.core.BitArray;
import com.sangupta.bloomfilter.core.FastBitArray;
import com.sangupta.bloomfilter.core.FileBackedBitArray;
import com.sangupta.bloomfilter.core.JavaBitSetArray;
import com.sangupta.bloomfilter.core.MMapFileBackedBitArray;

/**
 * JUnit tests for various implementations of {@link BitArray}s like 
 * {@link FileBackedBitArray}, {@link JavaBitSetArray} and {@link FastBitArray}
 * 
 * @author sangupta
 * @since 1.0
 */
public class TestBitArrays {
	
	private static final int MILLION_ELEMENTS = 1 * 1000 * 1000;
	
	@Test
	public void testJavaBitArray() {
		BitArray bitArray = new JavaBitSetArray(MILLION_ELEMENTS);
		testArray(bitArray, MILLION_ELEMENTS);
	}
	
	@Test
	public void testFileBackedBitArray() {
		FileBackedBitArray bitArray = null;
		try {
			File file = File.createTempFile("bitarray", ".bits");
			file.deleteOnExit();
			
			bitArray = new FileBackedBitArray(file, MILLION_ELEMENTS);
			
			testArray(bitArray, MILLION_ELEMENTS / 1000);
		} catch(Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} finally {
			if(bitArray != null) {
				try {
					bitArray.close();
				} catch (IOException e) {
					// eat up
				}
			}
		}
	}
	
	@Test
	public void testMMapFileBackedBitArray() {
		MMapFileBackedBitArray bitArray = null;
		try {
			File file = File.createTempFile("bitarray", ".bits");
			file.deleteOnExit();
			
			bitArray = new MMapFileBackedBitArray(file, MILLION_ELEMENTS);
			
			testArray(bitArray, MILLION_ELEMENTS);
		} catch(Exception e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		} finally {
			if(bitArray != null) {
				try {
					bitArray.close();
				} catch (IOException e) {
					// eat up
				}
			}
		}
	}
	
	private void testArray(BitArray bitArray, int maxElements) {
		// start iterating
		for(int index = 0; index < maxElements; index++) {
			Assert.assertFalse(bitArray.getBit(index));
			bitArray.setBit(index);
			Assert.assertTrue(bitArray.getBit(index));
			bitArray.clearBit(index);
			Assert.assertFalse(bitArray.getBit(index));
		}
	}

}