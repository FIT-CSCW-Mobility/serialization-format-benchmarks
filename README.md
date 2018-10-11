# Description

This project benchmarks common data serialization formats using [JMH](http://openjdk.java.net/projects/code-tools/jmh/).

The sample data is taken from [here](https://github.com/maximn/SerializationPerformanceTest_CSharp/blob/master/SerializationPerformanceTest/TestData/BelgianBeer/Data/beers.xml).

# Formats

- EXI
- FastInfoSet
- Json (using Jackson)
- MessagePack
- Protocol Buffers
- Apache Thrift
- XML (using Jaxb and Jackson)

# Thrift preparation

Thrift is a special case, because its code generator maven plugin expects the path of an installed Thrift library.


## System Requirements

* JDK 11
* Maven 

# How to use?

1. Build the project (a self-contained executable JAR, which holds the benchmarks):

    ```
    # mvn package
    ```

2. Run the benchmarks:

    ```
    # java -jar target/serialization-format-benchmarks.jar
    ```
