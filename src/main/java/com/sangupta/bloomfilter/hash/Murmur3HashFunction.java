package com.sangupta.bloomfilter.hash;

import com.sangupta.murmur.Murmur3;

public class Murmur3HashFunction implements HashFunction {
	
	private static final long SEED = 0x7f3a21eal;
	
	@Override
	public boolean isSingleValued() {
		return false;
	}

	@Override
	public long hash(byte[] bytes) {
		return Murmur3.hash_x86_32(bytes, 0, SEED);
	}

	@Override
	public long[] hashMultiple(byte[] bytes) {
		return Murmur3.hash_x64_128(bytes, 0, SEED);
	}

}
