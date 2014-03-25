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
