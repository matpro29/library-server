package matpro29.library.Controller;


import com.google.gson.Gson;
import matpro29.library.Entity.Search;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;



@RestController
public class ItemController {

    @RequestMapping("/item/search")
    @CrossOrigin(origins = "http://localhost:4200")
    public Search search() throws IOException, URISyntaxException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";

        URIBuilder builder = new URIBuilder("http://www.omdbapi.com/");
        builder.setParameter("s", "Batman");
        builder.setParameter("apikey", "dfb6eb94");

        try {
            HttpGet request = new HttpGet(builder.build());
            CloseableHttpResponse response = httpClient.execute(request);

            try {
                // Get HttpResponse Status
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                    result = EntityUtils.toString(entity);
                    System.out.println(result);
                }
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }

        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        return gson.fromJson(result, Search.class);
    }
}
