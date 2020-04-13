# Zip Builder

The Simplest way to archive files at zip archive

[![Build Status](https://travis-ci.com/isadounikau/zipbuilder.svg?branch=master)](https://travis-ci.com/isadounikau/zipbuilder) [![codecov](https://codecov.io/gh/isadounikau/zipbuilder/branch/master/graph/badge.svg)](https://codecov.io/gh/isadounikau/zipbuilder)

### Usage Example 
```
ZipArchiveBuilder testSource = new ZipArchiveBuilder();
testSource.addUrlItem(File.separator, UUID.randomUUID().toString(), "https://github.com/isadounikau/zipbuilder");
testSource.addFileItem(File.separator, UUID.randomUUID().toString(), File.createTempFile("test", ""));
File file = testSource.buildTempArchive();
```
