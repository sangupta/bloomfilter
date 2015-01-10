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

/**
 * Interface to signify that the object itself provides
 * functionality to be decomposed into a {@link ByteSink}
 * instance.
 * 
 * @author sangupta
 * @since 1.0
 */
public interface Decomposable {

	/**
	 * Decompose this object and render into the given {@link ByteSink} instance.
	 * 
	 * @param into
	 */
	public void decompose(ByteSink into);

}