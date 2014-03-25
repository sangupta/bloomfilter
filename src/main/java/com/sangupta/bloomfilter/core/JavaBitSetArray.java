package com.sangupta.bloomfilter.core;

import java.util.BitSet;

public class JavaBitSetArray implements BitArray {
	
	final BitSet bitSet;
	
	public JavaBitSetArray(int numBits) {
		this.bitSet = new BitSet(numBits);
	}

	@Override
	public void clear() {
		this.bitSet.clear();
	}

	@Override
	public boolean getBit(int index) {
		return this.bitSet.get(index);
	}

	@Override
	public boolean setBit(int index) {
		this.bitSet.set(index);
		return true;
	}

	@Override
	public void clearBit(int index) {
		this.bitSet.clear(index);
	}

	@Override
	public boolean setBitIfUnset(int index) {
		if(!this.bitSet.get(index)) {
			return this.setBit(index);
		}
		
		return false;
	}

	@Override
	public void or(BitArray bitArray) {
		// TODO Auto-generated method stub
	}

	@Override
	public void and(BitArray bitArray) {
		// TODO Auto-generated method stub
	}

	@Override
	public int bitSize() {
		return this.bitSet.size();
	}

}
