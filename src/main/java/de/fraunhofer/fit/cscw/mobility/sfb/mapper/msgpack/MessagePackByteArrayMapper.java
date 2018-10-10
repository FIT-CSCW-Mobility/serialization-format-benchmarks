package de.fraunhofer.fit.cscw.mobility.sfb.mapper.msgpack;

import com.example.myschema.ArrayOfBeer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fraunhofer.fit.cscw.mobility.sfb.mapper.ByteArrayMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;

/**
 * @author Sevket Goekay <goekay@dbis.rwth-aachen.de>
 * @since 10.10.2018
 */
public class MessagePackByteArrayMapper implements ByteArrayMapper<ArrayOfBeer> {

    private final ObjectMapper mapper;

    public MessagePackByteArrayMapper() {
        mapper = new ObjectMapper(new MessagePackFactory());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public ArrayOfBeer read(byte[] data) throws Exception {
        return mapper.readValue(data, ArrayOfBeer.class);
    }

    @Override
    public byte[] write(ArrayOfBeer obj) throws Exception {
        return mapper.writeValueAsBytes(obj);
    }
}
