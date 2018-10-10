package com.goekay.protobuf;

import com.example.myproto.Protobuf.ArrayOfBeerType;
import com.goekay.ByteArrayMapper;

/**
 * @author Fabian Ohler <fabian.ohler1@rwth-aachen.de>
 */
public class ProtobufByteArrayMapper implements ByteArrayMapper<ArrayOfBeerType> {

    @Override
    public ArrayOfBeerType read(final byte[] data) throws Exception {
        return ArrayOfBeerType.parseFrom(data);
    }

    @Override
    public byte[] write(final ArrayOfBeerType obj) throws Exception {
        return obj.toByteArray();
    }
}
