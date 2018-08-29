package connections.module.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import static spark.Spark.*;

@Slf4j
public class Server {
    public static void main(String[] args) {
        post("/", (req, res) -> {
            log.info("Body" + req.body());

            log.info("Head" + req.headers().stream().collect(Collectors.joining(", ")));

            return new ObjectMapper().createObjectNode();
        });
    }
}
