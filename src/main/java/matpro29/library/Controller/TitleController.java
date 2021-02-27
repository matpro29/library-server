package matpro29.library.Controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import matpro29.library.Entity.Find;
import matpro29.library.Entity.Season;
import matpro29.library.Entity.Title;
import matpro29.library.Entity.UserTitle;
import matpro29.library.Entity.api.ApiSeason;
import matpro29.library.Entity.api.FindSeasons;
import matpro29.library.Entity.api.FindTitle;
import matpro29.library.Entity.api.Result;
import matpro29.library.Mapper.FindMapper;
import matpro29.library.Repository.SeasonRepository;
import matpro29.library.Repository.UserTitleRepository;
import org.apache.catalina.User;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/title")
public class TitleController {

    @Autowired
    private FindMapper findMapper;

    @Autowired
    private UserTitleRepository userTitleRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    private String TITLE_API = "https://imdb8.p.rapidapi.com/title";

    @RequestMapping("/find")
    @CrossOrigin(origins = "http://localhost:4200")
    public Find find(@RequestBody Result title) throws IOException, URISyntaxException {
        HashMap<String, String> params = new HashMap<>();
        params.put("q", title.getTitle());

        String result = apiRequest("/find", params);

        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        return findMapper.findMapper(gson.fromJson(result, FindTitle.class).getResults());
    }

    @RequestMapping("/add")
    @CrossOrigin(origins = "http://localhost:4200")
    public UserTitle add(@RequestBody Title title) throws IOException, URISyntaxException {
        String titleId = title.getId().split("/")[2];

        UserTitle userTitle = new UserTitle();
        userTitle.setTitleId(titleId);
        userTitle.setImageUrl(title.getImage().getUrl());

        UserTitle addedUserTitle = userTitleRepository.findByTitleId(titleId);
        if (addedUserTitle != null) {
            userTitle.setUserTitleId(addedUserTitle.getUserTitleId());
        }

        addSeasons(titleId);

        return userTitleRepository.save(userTitle);
    }

    private void addSeasons(String titleId) throws IOException, URISyntaxException {
        List<Season> addedSeasons = seasonRepository.findAllByTitleId(titleId);
        Map<Integer, Season> addedSeasonMap = addedSeasons.stream()
                .collect(Collectors.toMap(Season::getSeason, Function.identity()));

        HashMap<String, String> params = new HashMap<>();
        params.put("tconst", titleId);
        String result = apiRequest("/get-seasons", params);
        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        Type type = new TypeToken<List<ApiSeason>>() {}.getType();
        List<Season> seasons = findMapper.seasonsMapper(gson.fromJson(result, type), addedSeasonMap, titleId);

        seasonRepository.saveAll(seasons);
    }

    @RequestMapping("/my")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<UserTitle> my() {
        return userTitleRepository.findAll();
    }

    private String apiRequest(String url, HashMap<String, String> params) throws IOException, URISyntaxException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";

        URIBuilder builder = new URIBuilder(TITLE_API + url);
        params.forEach(builder::setParameter);

        try {
            HttpGet request = new HttpGet(builder.build());
            request.setHeader("x-rapidapi-key", "1a20dde199msh186c5b1565aae85p1f3b51jsnc48629c58339");
            request.setHeader("x-rapidapi-host", "imdb8.p.rapidapi.com");
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

        return result;
    }
}
