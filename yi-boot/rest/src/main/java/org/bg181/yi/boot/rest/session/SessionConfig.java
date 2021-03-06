package org.bg181.yi.boot.rest.session;

import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sam Lu
 * @date 2022/07/02
 */
@Configuration
public class SessionConfig {

    @Bean
    public TomcatContextCustomizer sameSiteCookiesConfig() {
        return context -> {
            // 设置Cookie的SameSite
            final Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();
            cookieProcessor.setSameSiteCookies(SameSiteCookies.STRICT.getValue());
            context.setCookieProcessor(cookieProcessor);
        };
    }

}
