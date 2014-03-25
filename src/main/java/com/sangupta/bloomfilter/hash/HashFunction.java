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
	 * Whether the hash function returns a single long hash
	 * or multiple long values.
	 *  
	 * @return
	 */
	public boolean isSingleValued();
	
	/**
	 * Return the hash of the bytes as long.
	 * 
	 * @param bytes
	 * 
	 * @return
	 */
	public long hash(byte[] bytes);
	
	/**
	 * Return the hash of the bytes as a long array.
	 * 
	 * @param bytes
	 * 
	 * @return
	 */
	public long[] hashMultiple(byte[] bytes);

}
