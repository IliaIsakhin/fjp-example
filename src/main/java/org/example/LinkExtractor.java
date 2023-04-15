package org.example;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.parser.Parser;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class LinkExtractor {
    private final HttpClient client = HttpClient.newBuilder().build();
    private final Parser parser = Parser.htmlParser();

    public List<String> extract(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).build();

            var future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            return extractUrls(future.get().body(), url);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    
    private List<String> extractUrls(String bodyHtml, String pageUrl) {
        var rootUrl = extractRootUrl(pageUrl);
        
        return parser.newInstance().parseInput(bodyHtml, rootUrl).body().getAllElements()
                .stream()
                .map(e -> e.attr("abs:href"))
                .distinct()
                .filter(u -> u.startsWith(rootUrl))
                .toList();
    }
    
    private String extractRootUrl(String pageUrl) {
        var slashIdx = StringUtils.ordinalIndexOf(pageUrl, "/", 3);
        
        if (slashIdx != -1) {
            return pageUrl.substring(0, slashIdx);
        } else {
            return pageUrl;
        }
    }
}
