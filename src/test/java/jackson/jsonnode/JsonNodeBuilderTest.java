/*
 * Copyright (c) 2017 Thomson Reuters/ONESOURCE. All rights reserved.
 */

package jackson.jsonnode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple tests for the JsonNode builder.
 *
 * @author Rick Rupp
 */
public class JsonNodeBuilderTest {

    private final ObjectMapper objectMapper;
    private final Logger log = LoggerFactory.getLogger(JsonNodeBuilderTest.class);

    public JsonNodeBuilderTest() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    }

    @Test
    public void createJsonObject() throws Exception {
        final JsonNodeBuilder builder = JsonNodeBuilder.createJsonObject(objectMapper);
        builder.put("firstName", "First")
               .put("lastName", "Last")
               .pushObject("firstDocument")
               .put("firstDocumentName", "first document name")
               .pop()
               .pushObject("secondDocument")
               .pushArray("items")
               .add(1)
               .add(1.5)
               .add(2L)
               .add((short) 3)
               .add(false)
               .pop()
               .pop()
               .pushArray("pojos")
               .addPojo(Pojo.builder()
                            .pojoName("POJO 1")
                            .pojoValue("POJO value1")
                            .build())
               .addPojo(Pojo.builder()
                            .pojoName("POJO 2")
                            .pojoValue("POJO value2")
                            .build())
               .pop()
               .putPojo("pojo", Pojo.builder()
                                    .pojoName("name")
                                    .pojoValue("value")
                                    .build());
        log.info(builder.toPrettyString());
    }

    @Data
    @Builder
    public static class Pojo {
        private String pojoName;
        private String pojoValue;
    }
}