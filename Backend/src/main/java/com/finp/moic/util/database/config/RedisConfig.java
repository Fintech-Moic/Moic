package com.finp.moic.util.database.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * RedisTemplate 2개 선언
     * 서버 2개로 분리하여
     * 1. 전체 데이터가 저장되있는 RedisTemplate1
     * 2. Refresh Token만 저장되있는 RedisTemplate2
     *
     * 테스트 : redis1, redis2 값 저장 삭제 조회 가능 확인 완료
     */

    @Value("${spring.redis1.host}")
    private String REDIS1_HOST;

    @Value("${spring.redis1.port}")
    private int REDIS1_PORT;

    @Value("${spring.redis1.password}")
    private String REDIS1_PW;


    @Value("${spring.redis2.host}")
    private String REDIS2_HOST;

    @Value("${spring.redis2.port}")
    private int REDIS2_PORT;

    @Value("${spring.redis2.password}")
    private String REDIS2_PW;
    @Primary
    @Bean(name = "redisConnectionFactory1")
    public RedisConnectionFactory redisConnectionFactory1() {
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
        connectionFactory.setHostName(REDIS1_HOST); // 첫 번째 Redis 서버 호스트
        connectionFactory.setPort(REDIS1_PORT); // 첫 번째 Redis 서버 포트
        connectionFactory.setPassword(REDIS1_PW);
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }

    @Bean(name = "redisConnectionFactory2")
    public RedisConnectionFactory redisConnectionFactory2() {
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
        connectionFactory.setHostName(REDIS2_HOST); // 두 번째 Redis 서버 호스트
        connectionFactory.setPort(REDIS2_PORT); // 두 번째 Redis 서버 포트
        connectionFactory.setPassword(REDIS2_PW);
        connectionFactory.afterPropertiesSet();
        return connectionFactory;
    }

    @Primary
    @Bean(name = "redisTemplate1")
    public RedisTemplate<String, Object> redisTemplate1(@Qualifier("redisConnectionFactory1") RedisConnectionFactory redisConnectionFactory1) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory1);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper()));
        // 다른 설정을 추가할 수 있습니다.
        return redisTemplate;
    }

    @Bean(name = "redisTemplate2")
    public RedisTemplate<String, String> redisTemplate2(@Qualifier("redisConnectionFactory2") RedisConnectionFactory redisConnectionFactory2) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory2);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        // 다른 설정을 추가할 수 있습니다.
        return redisTemplate;
    }

    /**
     * TimeSTAMP 형태를 쓰지 않고
     * LocalDateTime 형식을 사용하기 위해
     * ObjectMapper 커스텀화
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModules(new JavaTimeModule(), new Jdk8Module());
        return mapper;
    }
}
