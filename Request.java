package connections.module.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Optional;

//  curl 'https://jsonplaceholder.typicode.com/posts' -i -X POST -H 'Content-Type: application/json' -d '{"userId": "1", "id": "101", "title": "Test Example","body": "Example Bodyxx"}'
public class Request {

    private HttpURLConnection conn;

    public void create(String url) {
        try {
            this.conn = (HttpURLConnection) new URL(url).openConnection();// OR HTTPS
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response send() {
        JsonNode jsonMap = null;
        try {
            jsonMap = this.exec(jsonMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Response(Optional.of(jsonMap));
    }

    private JsonNode exec(JsonNode jsonNode) throws IOException {
        InputStream is = this.conn.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(is);
    }

    public void toPost() {
        this.switchMethod("POST");
    }

    public void toPut() {
        this.switchMethod("PUT");
    }

    private void switchMethod(String method) {
        try {
            this.conn.setRequestMethod(method);
            this.conn.setDoOutput(true);
            this.addHeader("Content-Type","application/json; charset=UTF-8");
            this.addHeader("Accept", "application/json");

        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    public <T> void body(T obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter stringEmp = new StringWriter();

            objectMapper.writeValue(stringEmp, obj);
            this.conn.getOutputStream().write(stringEmp.toString().replace("\"", "\\\"").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHeader(String key, String value) {
        this.conn.setRequestProperty(key, value);
    }


}
