package com.dakual;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    private static RedisCacheConfiguration createCacheConfiguration(long timeoutInSeconds) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(timeoutInSeconds));
    }

    @Bean
    public RedisCacheConfiguration cacheConfiguration(CacheProperties properties) {
        return createCacheConfiguration(properties.getTimeout());
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, CacheProperties properties) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        for (Entry<String, Long> cacheNameAndTimeout : properties.getExpirations().entrySet()) {
            cacheConfigurations.put(cacheNameAndTimeout.getKey(), createCacheConfiguration(cacheNameAndTimeout.getValue()));
        }

        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(cacheConfiguration(properties))
                .withInitialCacheConfigurations(cacheConfigurations).build();
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getSimpleName());
                sb.append("#");
                sb.append(method.getName());
                sb.append("#");
                for(Object param : params){
                    sb.append(param.toString());
                    sb.append("#");
                }

                String str = sb.toString();

                return str.substring(0, str.length() - 1);
            }
        };
    }
}
