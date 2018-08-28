package connections.module.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Optional;

public class Request {

    private HttpsURLConnection conn;

   public void create(String url) {
       try {
           this.conn = (HttpsURLConnection) new URL(url).openConnection();
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
        InputStream is = conn.getInputStream();
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
            this.addHeader("Content-Type", "application/json");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
    }

    public <T> void body(T obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter stringEmp = new StringWriter();
            objectMapper.writeValue(stringEmp, obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHeader(String key, String value) {
        this.conn.setRequestProperty(key, value);
    }

}
