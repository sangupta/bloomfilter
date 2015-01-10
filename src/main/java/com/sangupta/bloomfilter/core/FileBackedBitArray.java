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
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * An implementation of {@link BitArray} that uses a normal random
 * file to persist all changes synchronously for the underlying bit
 * array. This is useful for stateful bit-arrays which are expensive
 * to construct yet need a good overall performance. This class is
 * not thread-safe.
 * 
 * @author sangupta
 * @since 1.0
 */
public class FileBackedBitArray implements BitArray {
	
	/**
	 * Underlying file that represents the state of the
	 * {@link BitArray}.
	 * 
	 */
	protected final RandomAccessFile backingFile;
	
	/**
	 * The maximum number of elements this file will store
	 */
	protected final int maxElements;
	
	/**
	 * The number of bytes being used for this byte-array
	 * 
	 */
	protected final int numBytes;
	
	/**
	 * Construct a {@link BitArray} that is backed by the given file. Ensure
	 * that the file is a local file and not on a network share for performance
	 * reasons.
	 * 
	 * @param backingFile
	 * @throws IOException 
	 */
	public FileBackedBitArray(File backingFile, int maxElements) throws IOException {
		if(backingFile == null) {
			throw new IllegalArgumentException("Backing file cannot be empty/null");
		}
		
		if(!backingFile.isFile()) {
			throw new IllegalArgumentException("Backing file does not represent a valid file");
		}
		
		if(maxElements <= 0) {
			throw new IllegalArgumentException("Max elements in array cannot be less than or equal to zero");
		}
		
		// we open in "rwd" mode, to save one i/o operation
		// than in "rws" mode
		this.backingFile = new RandomAccessFile(backingFile, "rwd");
		
		this.numBytes = (maxElements >> 3) + 1;
		extendFile(this.numBytes);
		
		// initialize the rest
		this.maxElements = maxElements;
	}

	/**
	 * @see BitArray#getBit(int)
	 */
	@Override
	public boolean getBit(int index) {
		if(index > maxElements) {
			throw new IndexOutOfBoundsException("Index is greater than max elements permitted");
		}
		
		int pos = index >> 3; // div 8
		int bit = 1 << (index & 0x7);
		
		try {
			this.backingFile.seek(pos);
			byte bite = this.backingFile.readByte();
			return (bite & bit) != 0;
		} catch(IOException e) {
			throw new RuntimeException("Unable to read bitset from disk");
		}
	}

	/**
	 * @see BitArray#setBit(int)
	 */
	@Override
	public boolean setBit(int index) {
		if(index > maxElements) {
			throw new IndexOutOfBoundsException("Index is greater than max elements permitted");
		}
		
		int pos = index >> 3; // div 8
		int bit = 1 << (index & 0x7);
		try {
			this.backingFile.seek(pos);
			byte bite = this.backingFile.readByte();
			bite = (byte) (bite | bit);
			
			this.backingFile.seek(pos);
			this.backingFile.writeByte(bite);
			return true;
		} catch(IOException e) {
			throw new RuntimeException("Unable to read bitset from disk");
		}
	}

	/**
	 * @see BitArray#clear()
	 */
	@Override
	public void clear() {
		byte[] bytes = new byte[this.numBytes];
		Arrays.fill(bytes, (byte) 0);
		
		try {
			this.backingFile.seek(0);
			this.backingFile.write(bytes);
		} catch(IOException e) {
			throw new RuntimeException("Unable to read bitset from disk");
		}
	}

	/**
	 * @see BitArray#clearBit(int)
	 */
	@Override
	public void clearBit(int index) {
		if(index > maxElements) {
			throw new IndexOutOfBoundsException("Index is greater than max elements permitted");
		}
		
		int pos = index >> 3; // div 8
		int bit = 1 << (index & 0x7);
		bit = ~bit;
		
		try {
			this.backingFile.seek(pos);
			byte bite = this.backingFile.readByte();
			bite = (byte) (bite & bit);
			
			
			this.backingFile.seek(pos);
			this.backingFile.writeByte(bite);
		} catch(IOException e) {
			throw new RuntimeException("Unable to read bitset from disk");
		}
	}

	/**
	 * @see BitArray#setBitIfUnset(int)
	 */
	@Override
	public boolean setBitIfUnset(int index) {
		if(this.getBit(index)) {
			return this.setBit(index);
		}
		
		return false;
	}

	/**
	 * @see BitArray#or(BitArray)
	 */
	@Override
	public void or(BitArray bitArray) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see BitArray#and(BitArray)
	 */
	@Override
	public void and(BitArray bitArray) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see BitArray#bitSize()
	 */
	@Override
	public int bitSize() {
		return this.numBytes;
	}
	
	/**
	 * 
	 * @param newLength
	 * @throws IOException
	 */
	protected void extendFile(final long newLength) throws IOException {
		long current = this.backingFile.length();
		int delta = (int) (newLength - current) + 1;
		if(delta <= 0) {
			return;
		}
		
		this.backingFile.setLength(newLength);
		this.backingFile.seek(current);
		byte[] bytes = new byte[delta];
		Arrays.fill(bytes, (byte) 0);
		this.backingFile.write(bytes);
	}

	@Override
	public void close() throws IOException {
		this.backingFile.close();
	}
	
}