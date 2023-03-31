package com.yeyou.yeapiclientsdk;

import com.yeyou.yeapiclientsdk.client.YeApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@ConfigurationProperties("yeapi.client")
@Data
public class YeApiClientConfig {
    private String accessKey;
    private String secretKey;

    @Bean
    public YeApiClient yeApiClient(){
        return new YeApiClient(accessKey, secretKey);
    }

}
