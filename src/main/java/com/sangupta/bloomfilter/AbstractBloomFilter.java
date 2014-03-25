package com.sangupta.bloomfilter;

import java.nio.charset.Charset;
import java.util.Collection;

import com.sangupta.bloomfilter.core.BitArray;
import com.sangupta.bloomfilter.decompose.ByteSink;
import com.sangupta.bloomfilter.decompose.Decomposable;
import com.sangupta.bloomfilter.decompose.Decomposer;
import com.sangupta.bloomfilter.decompose.DefaultDecomposer;
import com.sangupta.bloomfilter.hash.HashFunction;
import com.sangupta.bloomfilter.hash.Murmur3HashFunction;

/**
 * An abstract implementation for the bloom filter.
 * 
 * @author sangupta
 *
 * @param <T>
 */
public abstract class AbstractBloomFilter<T> implements BloomFilter<T> {
	
	/**
	 * The decomposer to use when there is none specified at construction
	 */
	protected static final Decomposer<Object> DEFAULT_COMPOSER = new DefaultDecomposer();
	
	/**
	 * The default hasher to use if one is not specified
	 */
	protected static final HashFunction DEFAULT_HASHER = new Murmur3HashFunction();
	
	/**
	 * Constant
	 */
	public static final double LOG_2 = Math.log(2);
	
	/**
	 * Constant
	 */
	public static final double LOG_2_SQUARE = LOG_2 * LOG_2;
	
	/**
	 * The default {@link Charset} is the platform encoding charset
	 */
	protected transient Charset currentCharset = Charset.defaultCharset();
	
	/**
	 * The {@link BitArray} instance that holds the entire data
	 */
	protected final BitArray bitArray;
	
	/**
	 * Number of hash functions needed
	 */
	protected final int kOrNumberOfHashFunctions;
	
	/**
	 * Holds the custom decomposer that should be used for this bloom filter
	 * 
	 */
	protected final Decomposer<T> customDecomposer;
	
	/**
	 * The hashing method to be used for hashing
	 */
	protected final HashFunction hasher;
	
	// Various construction mechanisms
	
	/**
	 * Create a new bloom filter.
	 * 
	 * @param expectedInsertions
	 *            the number of max expected insertions
	 * 
	 * @param falsePositiveProbability
	 *            the max false positive probability rate that the bloom filter
	 *            can give
	 */
	protected AbstractBloomFilter(int expectedInsertions, double falsePositiveProbability) {
		this(expectedInsertions, falsePositiveProbability, null, null);
	}
	
	/**
	 * Create a new bloom filter.
	 * 
	 * @param expectedInsertions
	 *            the number of max expected insertions
	 * 
	 * @param falsePositiveProbability
	 *            the max false positive probability rate that the bloom filter
	 *            can give
	 * 
	 * @param decomposer
	 *            a {@link Decomposer} that helps decompose the given object
	 * 
	 */
	protected AbstractBloomFilter(int expectedInsertions, double falsePositiveProbability, Decomposer<T> decomposer) {
		this(expectedInsertions, falsePositiveProbability, decomposer, null);
	}
	
	/**
	 * Create a new bloom filter.
	 * 
	 * @param expectedInsertions
	 *            the number of max expected insertions
	 * 
	 * @param falsePositiveProbability
	 *            the max false positive probability rate that the bloom filter
	 *            can give
	 * 
	 * @param decomposer
	 *            a {@link Decomposer} that helps decompose the given object
	 * 
	 * @param hasher
	 *            the hash function to use. If <code>null</code> is specified
	 *            the {@link AbstractBloomFilter#DEFAULT_HASHER} will be used as
	 *            the hashing function
	 */
	protected AbstractBloomFilter(int expectedInsertions, double falsePositiveProbability, Decomposer<T> decomposer, HashFunction hasher) {
		int numBits = optimalBitSizeOrM(expectedInsertions, falsePositiveProbability);
		this.kOrNumberOfHashFunctions = optimalNumberofHashFunctionsOrK(expectedInsertions, numBits);
		this.bitArray = createBitArray(numBits);
		
		this.customDecomposer = decomposer;
		
		if(hasher != null) {
			this.hasher = hasher;
		} else {
			this.hasher = DEFAULT_HASHER;
		}
	}
	
	// Default bloom filter functions follow
	
	/**
	 * Compute the optimal size <code>m</code> of the bloom filter in bits.
	 * 
	 * @param n
	 *            the number of expected insertions, or <code>n</code>
	 * 
	 * @param p
	 *            the maximum false positive rate expected, or <code>p</code>
	 * 
	 * @return the optimal size in bits for the filter, or <code>m</code>
	 */
	public static int optimalBitSizeOrM(final double n, final double p) {
		return (int) (-n * Math.log(p) / (LOG_2_SQUARE));
        // return (int) Math.ceil(-1 * n * Math.log(p) / LOG_2_SQUARE);
	}
	
	/**
	 * Compute the optimal number of hash functions, <code>k</code>
	 * 
	 * @param n
	 *            the number of expected insertions or <code>n</code>
	 * 
	 * @param m
	 *            the number of bits in the filter
	 * 
	 * @return the optimal number of hash functions to be used also known as
	 *         <code>k</code>
	 */
	public static int optimalNumberofHashFunctionsOrK(final long n, final long m) {
		return Math.max(1, (int) Math.round(m / n * Math.log(2)));
		// return Math.max(1, (int) Math.round(m / n * LOG_2));
	}
	
	// Abstraction layer
	
	/**
	 * Create a new {@link BitArray} instance for the given number of bits.
	 * 
	 * @param numBits
	 * @return
	 */
	protected abstract BitArray createBitArray(int numBits);
	
	// Main functions that govern the bloom filter
	
	@Override
	public final boolean add(byte[] bytes) {
		long hash64 = getLongHash64(bytes);
		
		// apply the less hashing technique
		int hash1 = (int) hash64;
		int hash2 = (int) (hash64 >>> 32);
		
		boolean bitsChanged = false;
		for (int i = 1; i <= this.kOrNumberOfHashFunctions; i++) {
			int nextHash = hash1 + i * hash2;
			if (nextHash < 0) {
				nextHash = ~nextHash;
			}
			bitsChanged |= this.bitArray.setBit(nextHash % this.bitArray.bitSize());
		}
		
		return bitsChanged;
	}
	
	@Override
	public final boolean contains(byte[] bytes) {
		long hash64 = getLongHash64(bytes);
		
		int hash1 = (int) hash64;
		int hash2 = (int) (hash64 >>> 32);
		for (int i = 1; i <= this.kOrNumberOfHashFunctions; i++) {
			int nextHash = hash1 + i * hash2;
			if (nextHash < 0) {
				nextHash = ~nextHash;
			}
			if (!this.bitArray.getBit(nextHash % this.bitArray.bitSize())) {
				return false;
			}
		}
		return true;
	}
	
	// Helper functions for functionality within
	
	protected long getLongHash64(byte[] bytes) {
		if(this.hasher.isSingleValued()) {
			return this.hasher.hash(bytes);
		}
		
		return this.hasher.hashMultiple(bytes)[0];
	}
	
	/**
	 * Given the value object, decompose it into a byte-array so that hashing can
	 * be done over the returned bytes.
	 * 
	 * @param value
	 * @return
	 */
	protected byte[] decomposedValue(T value) {
		ByteSink sink = new ByteSink();
		
		if(value instanceof Decomposable) {
			((Decomposable) value).decompose(sink);
			
			return sink.getByteArray();
		}
		
		if(this.customDecomposer != null) {
			this.customDecomposer.decompose(value, sink);
			return sink.getByteArray();
		}
		
		DEFAULT_COMPOSER.decompose(value, sink);
		return sink.getByteArray();
	}
	
	// Overridden helper functions follow
	
	@Override
	public boolean add(T value) {
		if(value == null) {
			return false;
		}
		
		return add(decomposedValue(value));
	}

	@Override
	public boolean addAll(Collection<T> values) {
		if(values == null || values.isEmpty()) {
			return false;
		}
		
		boolean success = true;
		for(T value : values) {
			success = add(value) && success; 
		}
		
		return success;
	}
	
	@Override
	public boolean contains(T value) {
		if(value == null) {
			return false;
		}
		
		return contains(value.toString().getBytes(this.currentCharset));
	}
	
	@Override
	public boolean containsAll(Collection<T> values) {
		if(values == null || values.isEmpty()) {
			return false;
		}
		
		for(T value : values) {
			if(!contains(value)) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public void setCharset(String charsetName) {
		if(charsetName == null) {
			throw new IllegalArgumentException("Charset to be changed to cannot be null");
		}
		
		setCharset(Charset.forName(charsetName));
	}

	@Override
	public void setCharset(Charset charset) {
		if(charset == null) {
			throw new IllegalArgumentException("Charset to be changed to cannot be null");
		}
		
		this.currentCharset = charset;
	}
	
	/**
	 * Get the current custom decomposer that is being used. If no custom
	 * decomposer is specified, <code>null</code> is returned to signify that we
	 * are using the {@link AbstractBloomFilter#DEFAULT_HASHER} hash function.
	 * 
	 * @return the current custom decomposer being used, if any
	 */
	@Override
	public Decomposer<T> getObjectDecomposer() {
		if(this.customDecomposer != null) {
			return this.customDecomposer;
		}
		
		return null;
	}
	
}
