package com.sangupta.bloomfilter.core;

import java.math.RoundingMode;
import java.util.Arrays;

import com.google.common.math.LongMath;
import com.google.common.primitives.Ints;

public class FastBitSet {

	private final long[] data;
	
	private int bitCount;

	public FastBitSet(long bits) {
		this(new long[Ints.checkedCast(LongMath.divide(bits, 64, RoundingMode.CEILING))]);
	}

	// Used by serialization
	public FastBitSet(long[] data) {
		if(data == null || data.length == 0) {
			throw new IllegalArgumentException("Data is either null or zero-length");
		}
		
		this.data = data;
		int bitCount = 0;
		for (long value : data) {
			bitCount += Long.bitCount(value);
		}
		
		this.bitCount = bitCount;
	}

	/** Returns true if the bit changed value. */
	boolean set(int index) {
		if (!get(index)) {
			data[index >> 6] |= (1L << index);
			bitCount++;
			return true;
		}
		
		return false;
	}

	boolean get(int index) {
		return (data[index >> 6] & (1L << index)) != 0;
	}

	/**
	 * Number of bits
	 * 
	 * @return
	 */
	public int bitSize() {
		return data.length * Long.SIZE;
	}

	/**
	 * Number of set bits (1s)
	 * 
	 * @return
	 */
	public int bitCount() {
		return this.bitCount;
	}

	/**
	 * Copy the bitset.
	 * 
	 * @return
	 */
	public FastBitSet copy() {
		return new FastBitSet(data.clone());
	}

	/** Combines the two BitArrays using bitwise OR. */
	void putAll(FastBitSet array) {
		if(array == null) {
			throw new IllegalArgumentException("Array to be combined with cannot be null");
		}
		
		if(this.data.length != array.data.length) {
			throw new IllegalArgumentException("Array to be combined with must be of equal length");
		}
		
		bitCount = 0;
		
		for (int i = 0; i < data.length; i++) {
			data[i] |= array.data[i];
			bitCount += Long.bitCount(data[i]);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof FastBitSet) {
			FastBitSet bitArray = (FastBitSet) o;
			return Arrays.equals(data, bitArray.data);
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(data);
	}

}
