package com.goekay;

import com.example.myschema.ArrayOfBeer;
import com.goekay.json.JacksonJsonStringMapper;
import com.goekay.msgpack.MessagePackByteArrayMapper;
import com.goekay.xml.JacksonXmlStringMapper;
import com.goekay.xml.JaxbXmlStringMapper;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

/**
 * @author Sevket Goekay <goekay@dbis.rwth-aachen.de>
 * @since 09.10.2018
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class Benchmarks {

    private JacksonJsonStringMapper jacksonJsonMapper;

    private JaxbXmlStringMapper jaxbXmlMapper;
    private JacksonXmlStringMapper jacksonXmlMapper;

    private MessagePackByteArrayMapper messagePackMapper;

    private String dataAsXmlString;
    private String dataAsJsonString;
    private byte[] dataAsMsgPack;

    private ArrayOfBeer dataAsObject;

    /**
     * Main method to run benchmark
     */
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(Benchmarks.class.getSimpleName())
                                          .warmupIterations(4)
                                          .measurementIterations(4)
                                          .forks(1)
                                          .threads(1)
                                          .build();

        new Runner(opt).run();
    }

    @Setup
    public final void prepare() throws Exception {
        String tmp = readFile(Paths.get("src/main/resources", "beers.xml"));

        jaxbXmlMapper = new JaxbXmlStringMapper(false);
        jacksonJsonMapper = new JacksonJsonStringMapper();
        jacksonXmlMapper = new JacksonXmlStringMapper();
        messagePackMapper = new MessagePackByteArrayMapper();

        dataAsObject = jaxbXmlMapper.readNoThrow(tmp);
        dataAsXmlString = jaxbXmlMapper.writeNoThrow(dataAsObject);
        dataAsMsgPack = messagePackMapper.write(dataAsObject);

        dataAsJsonString = jacksonJsonMapper.writeNoThrow(dataAsObject);
        ArrayOfBeer dataAsObject2 = jacksonJsonMapper.readNoThrow(dataAsJsonString);
        ArrayOfBeer dataAsObject3 = messagePackMapper.readNoThrow(dataAsMsgPack);

        Files.write(Paths.get("target", "data-jaxb.xml"), dataAsXmlString.getBytes(), StandardOpenOption.CREATE);
        Files.write(Paths.get("target", "data-jackson.json"), dataAsJsonString.getBytes(), StandardOpenOption.CREATE);
        Files.write(Paths.get("target", "data-jackson.xml"), jacksonXmlMapper.writeNoThrow(dataAsObject2).getBytes(), StandardOpenOption.CREATE);
        Files.write(Paths.get("target", "data-msgpack"), messagePackMapper.writeNoThrow(dataAsObject3), StandardOpenOption.CREATE);
    }

    @Benchmark
    public void jaxbParserToObject(Blackhole bh) {
        bh.consume(jaxbXmlMapper.readNoThrow(dataAsXmlString));
    }

    @Benchmark
    public void jaxbParserToString(Blackhole bh) {
        bh.consume(jaxbXmlMapper.writeNoThrow(dataAsObject));
    }

    @Benchmark
    public void jacksonXmlMapperToObject(Blackhole bh) {
        bh.consume(jacksonXmlMapper.readNoThrow(dataAsXmlString));
    }

    @Benchmark
    public void jacksonXmlMapperToString(Blackhole bh) {
        bh.consume(jacksonXmlMapper.writeNoThrow(dataAsObject));
    }

    @Benchmark
    public void jacksonJsonMapperToObject(Blackhole bh) {
        bh.consume(jacksonJsonMapper.readNoThrow(dataAsJsonString));
    }

    @Benchmark
    public void jacksonJsonMapperToString(Blackhole bh) {
        bh.consume(jacksonJsonMapper.writeNoThrow(dataAsObject));
    }

    @Benchmark
    public void msgPackToObject(Blackhole bh) {
        bh.consume(messagePackMapper.readNoThrow(dataAsMsgPack));
    }

    @Benchmark
    public void msgPackToByteArray(Blackhole bh) {
        bh.consume(messagePackMapper.writeNoThrow(dataAsObject));
    }

    private String readFile(Path path) throws IOException {
        byte[] encoded = Files.readAllBytes(path);
        return new String(encoded, StandardCharsets.UTF_8);
    }
}
