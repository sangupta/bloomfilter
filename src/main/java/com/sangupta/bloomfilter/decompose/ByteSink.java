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

package com.sangupta.bloomfilter.decompose;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 
 * @author sangupta
 *
 */
public class ByteSink {
	
	ByteArrayOutputStream stream = new ByteArrayOutputStream();
	
	public byte[] getByteArray() {
		return stream.toByteArray();
	}
	
	public ByteSink putByte(byte b) {
		return this;
	}
	
	public ByteSink putBytes(byte[] bytes) {
		try {
			stream.write(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		return this;
	}
	
	public ByteSink putBytes(byte[] bytes, int offset, int length) {
		return this;
	}
	
	public ByteSink putChar(char c) {
		return this;
	}
	
	public ByteSink putShort(short s) {
		return this;
	}
	
	public ByteSink putInt(int i) {
		return this;
	}
	
	public ByteSink putLong(long l) {
		return this;
	}
	
	public ByteSink putFloat(float f) {
		return this;
	}
	
	public ByteSink putDouble(double d) {
		return this;
	}

	public ByteSink putBoolean(boolean b) {
		return this;
	}
	
	public ByteSink putChars(CharSequence charSequence) {
		return this;
	}
	
	public ByteSink putString(CharSequence charSequence, Charset charset) {
		return this;
	}
	
}
