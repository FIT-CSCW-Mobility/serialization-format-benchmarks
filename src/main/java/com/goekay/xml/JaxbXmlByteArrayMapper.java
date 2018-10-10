package com.goekay.xml;

import com.example.myschema.ArrayOfBeer;
import com.goekay.ByteArrayMapper;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author Sevket Goekay <goekay@dbis.rwth-aachen.de>
 * @since 10.10.2018
 */
public class JaxbXmlByteArrayMapper extends AbstractJaxbXmlMapper implements ByteArrayMapper<ArrayOfBeer> {

    public JaxbXmlByteArrayMapper(boolean validate) {
        super(validate);
    }

    @Override
    public ArrayOfBeer read(byte[] data) throws Exception {
        try (ByteArrayInputStream in = new ByteArrayInputStream(data)) {
            return super.read(new StreamSource(in));
        }
    }

    @Override
    public byte[] write(ArrayOfBeer obj) throws Exception {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            super.write(obj, new StreamResult(out));
            return out.toByteArray();
        }
    }
}
