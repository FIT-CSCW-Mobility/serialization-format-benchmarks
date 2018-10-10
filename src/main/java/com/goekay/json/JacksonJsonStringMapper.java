package com.goekay.json;

import com.example.myschema.ArrayOfBeer;
import com.goekay.StringMapper;

import java.io.StringWriter;

/**
 * @author Sevket Goekay <goekay@dbis.rwth-aachen.de>
 * @since 09.10.2018
 */
public class JacksonJsonStringMapper extends AbstractJacksonJsonMapper implements StringMapper<ArrayOfBeer> {

    @Override
    public ArrayOfBeer read(String str) throws Exception {
        return mapper.readValue(str, ArrayOfBeer.class);
    }

    @Override
    public String write(ArrayOfBeer obj) throws Exception {
        StringWriter stringWriter = new StringWriter();
        mapper.writeValue(stringWriter, obj);
        return stringWriter.toString();
    }
}
