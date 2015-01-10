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

package com.sangupta.bloomfilter.decompose;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * An in-memory sink that uses a {@link ByteArrayOutputStream} to store
 * the incoming bytes.
 * 
 * @author sangupta
 * @since 1.0
 */
public class ByteSink {
	
	/**
	 * The actual storage stream
	 */
	protected ByteArrayOutputStream stream = new ByteArrayOutputStream();
	
	/**
	 * Wrapper over the byte stream
	 */
	protected DataOutputStream dataStream = new DataOutputStream(stream);
	
	/**
	 * Get the byte-array of bytes currently stored
	 * 
	 * @return
	 */
	public byte[] getByteArray() {
		return stream.toByteArray();
	}
	
	/**
	 * Store a single byte in this sink
	 * 
	 * @param b
	 * @return
	 */
	public ByteSink putByte(byte b) {
		this.stream.write(b);
		return this;
	}
	
	/**
	 * Store the given bytes in this sink.
	 * 
	 * @param bytes
	 * @return
	 */
	public ByteSink putBytes(byte[] bytes) {
		try {
			stream.write(bytes);
		} catch (IOException e) {
			throw new RuntimeException("Unable to store bytes inside the sink", e);
		}
		
		return this;
	}
	
	/**
	 * 
	 * @param bytes
	 * @param offset
	 * @param length
	 * @return
	 */
	public ByteSink putBytes(byte[] bytes, int offset, int length) {
		this.stream.write(bytes, offset, length);
		return this;
	}
	
	public ByteSink putChar(char c) {
		try {
			this.dataStream.writeChar(c);
		} catch (IOException e) {
			throw new RuntimeException("Unable to store char inside the sink", e);
		}
		
		return this;
	}
	
	public ByteSink putShort(short s) {
		try {
			this.dataStream.writeShort(s);
		} catch (IOException e) {
			throw new RuntimeException("Unable to store short inside the sink", e);
		}
		
		return this;
	}
	
	public ByteSink putInt(int i) {
		try {
			this.dataStream.writeInt(i);
		} catch (IOException e) {
			throw new RuntimeException("Unable to store int inside the sink", e);
		}
		
		return this;
	}
	
	public ByteSink putLong(long l) {
		try {
			this.dataStream.writeLong(l);
		} catch (IOException e) {
			throw new RuntimeException("Unable to store long inside the sink", e);
		}
		
		return this;
	}
	
	public ByteSink putFloat(float f) {
		try {
			this.dataStream.writeFloat(f);
		} catch (IOException e) {
			throw new RuntimeException("Unable to store float  inside the sink", e);
		}
		
		return this;
	}
	
	public ByteSink putDouble(double d) {
		try {
			this.dataStream.writeDouble(d);
		} catch (IOException e) {
			throw new RuntimeException("Unable to store double inside the sink", e);
		}
		
		return this;
	}

	public ByteSink putBoolean(boolean b) {
		try {
			this.dataStream.writeBoolean(b);
		} catch (IOException e) {
			throw new RuntimeException("Unable to store boolean inside the sink", e);
		}
		
		return this;
	}
	
	public ByteSink putChars(CharSequence charSequence) {
		try {
			this.dataStream.writeBytes(charSequence.toString());
		} catch (IOException e) {
			throw new RuntimeException("Unable to store charSequence inside the sink", e);
		}
		
		return this;
	}
	
}