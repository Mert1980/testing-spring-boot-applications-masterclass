package de.rieckpil.courses.book;

import com.jayway.jsonpath.JsonPath;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class JsonTest {

  @Test
  void testWithJSONAssert() throws JSONException {
    String result = "{\"name\": \"duke\", \"age\":\"42\", \"hobbies\": [\"soccer\", \"java\"]}";
    String resultWithTextBlok = """
        {"name": "duke", "age":"42", "hobbies": ["soccer", "java"]}
      """;

    JSONAssert.assertEquals("{\"name\": \"duke\"}", result, false);
    JSONAssert.assertEquals("{\"name\": \"duke\"}", resultWithTextBlok, false);

    JSONAssert.assertEquals("{\"hobbies\": [\"java\", \"soccer\"]}", result, false);
    JSONAssert.assertEquals("{\"hobbies\": [\"java\", \"soccer\"]}", resultWithTextBlok, false);

  }

  @Test
  void testWithJsonPath() {
    String result = "{\"age\":\"42\", \"name\": \"duke\", \"tags\":[\"java\", \"jdk\"], \"orders\": [42, 42, 16]}";
    String resultWithTextBlock = """
       {"age":"42", "name": "duke", "tags":["java", "jdk"], "orders": [42, 42, 16]}
      """;

    Assertions.assertEquals(2, JsonPath.parse(result).read("$.tags.length()", Long.class));
    Assertions.assertEquals("duke", JsonPath.parse(result).read("$.name", String.class));
    Assertions.assertEquals(100, JsonPath.parse(result).read("$.orders.sum()", Long.class));

    Assertions.assertEquals(2, JsonPath.parse(resultWithTextBlock).read("$.tags.length()", Long.class));
    Assertions.assertEquals("duke", JsonPath.parse(resultWithTextBlock).read("$.name", String.class));
    Assertions.assertEquals(100, JsonPath.parse(resultWithTextBlock).read("$.orders.sum()", Long.class));

    // access to the first street in addresses array and take the name attribute of it
    // Assertions.assertEquals("Leuvenstraat", JsonPath.parse(result).read("$.adress.adresses[0].name", String.class));
  }
}
