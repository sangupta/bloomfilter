package com.sangupta.bloomfilter.hash;

import java.util.zip.CRC32;

/**
 * A CRC32 hash function.
 * 
 * @author sangupta
 *
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
