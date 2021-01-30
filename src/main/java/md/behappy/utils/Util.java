package md.behappy.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.json.Json;
import javax.json.JsonValue;
import java.io.StringReader;
import java.io.StringWriter;

public class Util {
    public static JsonValue writeValueAsJson(ObjectMapper mapper, Object data) throws Exception {
        StringWriter sw = new StringWriter();
        JsonGenerator jsonGenerator = mapper.getFactory().createGenerator(sw);
        mapper.writeValue(jsonGenerator, data);
        return Json.createReader(new StringReader(sw.toString())).readValue();
    }
}
