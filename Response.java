package http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class Response {
    private JsonNode json;

    public Response(Optional<JsonNode> optional) {

        this.json = optional.orElse(new ObjectMapper().createObjectNode());
    }

    public JsonNode getJsonNode() {
        return this.json;
    }
}
