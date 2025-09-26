package am.forex.demo.customer.webclient.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Author: Artyom Aroyan
 * Date: 26.09.25
 * Time: 10:08:28
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(UrlProperties.class)
public class WebClientConfiguration {
    private final UrlProperties urlProperties;

    @Bean
    public WebClient orderWebClient() {
        return WebClient.builder()
                .baseUrl(urlProperties.orderUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public WebClient currencyWebClient() {
        return WebClient.builder()
                .baseUrl(urlProperties.currencyUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}