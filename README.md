# bloomfilter

[![Travis](https://img.shields.io/travis/sangupta/bloomfilter.svg)]()
[![Coveralls](https://img.shields.io/coveralls/sangupta/bloomfilter.svg)]()
[![license](https://img.shields.io/github/license/sangupta/bloomfilter.svg)]()
[![Maven Central](https://img.shields.io/maven-central/v/com.sangupta/gather.svg)]()

`bloomfilter` is a pure Java Bloom Filter implementation that provides simple persistable bloom filters. The
entire bloom filter is abstracted into various layers so that the same can be changed by pure plug-and-play implementations
such as decomposing an object to a byte-stream, or the hash function to be used, or the serialization strategy to
be used.

## Why another Bloom Filter implementation?


`bloomfilter` was developed as I was looking for a fast persistable bloom filter implementation that could
be customized to suit needs. The `Google Guava` bloom filter for few reasons cannot be persisted well, does not
provide for a disk-backed bit array implementation, missing a counting bloom filter and last not the least 
the size of the payload. Many of my modules/projects did not need `Guava` and adding it just for using the 
bloom filter was coming out to be expensive. Thus, `bloomfilter` was born.

The `bloomfilter` is inspired by the `Guava` bloom filter implementation and uses a similar approach, with 
more extensions baked in.

## Features

* Uses pure Java murmur hash implementation as default hash function
* Various persisting methodologies - Java serialization, disk file etc
* No dependencies

## Usage

```java
// the maximum number of elements that the filter will contain
int numberOfElements = 1000 * 1000;

// the max false positive probability that is desired
// the lower the value - the more will be the memory usage
double fpp = 0.01d;

// this creates an in-memory bloom filter - useful when you need to dispose off the
// filter at the end of application, and the memory consumption will not be too huge
BloomFilter<String> filter = new InMemoryBloomFilter<String>(numberOfElements, fpp);

// you can roll your own implementations based on file-backed, or memory-mapped 
// file-backed implementations that can provide persistence too
filter = new AbstractBloomFilter<String>(numberOfElements, fpp) {

	/**
	 * Used a {@link FileBackedBitArray} to allow for file persistence.
	 * 
	 * @returns a {@link BitArray} that will take care of storage of bloom filter
	 */
	@Override
	protected BitArray createBitArray(int numBits) {
		return new FileBackedBitArray(new File("/tmp/test.bloom.filter"), numBits);
	}
	
};
```

`BitArray` is an interface defined in `com.sangupta.bloomfilter.core` package. This provides methods that
any implementation can be provide and thus be used as a bloom-filter implementation. This allows for rolling
out bloom filter implementations backed by file based persistence, Redis server or similar. The following
implementations are available for the `interface`:

* FastBitArray - faster than the default Java one
* JavaBitSetArray - uses Java BitSet as backing array
* FileBackedBitArray - uses normal file backing object in random mode
* MMapFileBackedBitArray - uses memory-mapped file, much faster than FileBackedBitArray


## Builds

**0.9.0** (17 Jun 2017)

* First release with Murmur 1/2/3 hashes

## Downloads

The library can be downloaded from Maven Central using:

```xml
<dependency>
    <groupId>com.sangupta</groupId>
    <artifactId>bloomfilter</artifactId>
    <version>0.9.0</version>
</dependency>
```

## Other Similar Projects

Other similar bloom filter implementations include:

### Google Guava
Read more at http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/hash/BloomFilter.html

* As explained before, is heavy.

### Orestes-Bloomfilter
https://github.com/DivineTraube/Orestes-Bloomfilter

* Does not have a persisted version of a BloomFilter
* Does not have a Murmur3 implementation

### Greplin-bloom-filter 
https://github.com/Cue/greplin-bloom-filter

* The persisted bloom filter does not use memory-mapped files, rather the slower file-seek-change-repeat workflow. 
* No Murmur3 implementation

## Continuous Integration

The library is continuously integrated and unit tested using the *Travis CI system*.

Current status of branch `MASTER`: [![Build Status](https://secure.travis-ci.org/sangupta/bloomfilter.png?branch=master)](http://travis-ci.org/sangupta/bloomfilter)

The library is tested against

* Oracle JDK 7
* Open JDK 7

Note: JDK6 has been removed from the list due to end-of-life from Oracle, and as support has been dropped by
http://travis-ci.org as well.

## Versioning

For transparency and insight into our release cycle, and for striving to maintain backward compatibility, 
`bloomfilter` will be maintained under the Semantic Versioning guidelines as much as possible.

Releases will be numbered with the follow format:

`<major>.<minor>.<patch>`

And constructed with the following guidelines:

* Breaking backward compatibility bumps the major
* New additions without breaking backward compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on SemVer, please visit http://semver.org/.

## License

```
bloomfilter: Bloom filters for Java
Copyright (c) 2014-2017, Sandeep Gupta

https://sangupta.com/projects/bloomfilter

Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
      http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 ```
