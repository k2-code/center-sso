package com.center.sso.config.redis;//package com.philisense.auth.config.redis;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import java.time.Duration;
//
///**
// * @描述： redis配置
// */
//@Configuration
//@EnableCaching
//public class RedisConfig extends CachingConfigurerSupport {
//
//    Logger logger = LoggerFactory.getLogger(RedisConfig.class.getName());
//
//    /**
//     * @return
//     */
//    @Bean
//    public KeyGenerator simpleKey() {
//        return (target, method, params) -> {
//            StringBuilder sb = new StringBuilder();
//            for (Object obj : params) {
//                sb.append(obj.toString());
//            }
//            logger.info(sb.toString());
//            return sb.toString();
//        };
//    }
//
//
////    @Bean
////    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
////        RedisCacheManager manager = new RedisCacheManager(redisTemplate);
////        manager.setDefaultExpiration(3600);//1小时
////        return manager;
////    }
//
//    /**
//     * json序列化
//     *
//     * @return
//     */
//    @Bean
//    public RedisSerializer<Object> jackson2JsonRedisSerializer() {
//        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
//        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        serializer.setObjectMapper(mapper);
//        return serializer;
//    }
//
//    /**
//     * 管理缓存
//     *
//     * @return
//     */
//    //缓存管理器
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory factory) {
//        // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
//
//        // 设置缓存的默认过期时间，也是使用Duration设置
//        config = config.entryTtl(Duration.ofMinutes(1))
//                // 设置 key为string序列化
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                // 设置value为json序列化
//                //    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer() ))
//                // 不缓存空值
//                .disableCachingNullValues();
//        // 使用自定义的缓存配置初始化一个cacheManager
//        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
//                // 一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
////                .initialCacheNames(cacheNames)
////                .withInitialCacheConfigurations(configMap)
//                .build();
//        return cacheManager;
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        //StringRedisTemplate的构造方法中默认设置了stringSerializer
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        //set key serializer
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        template.setKeySerializer(stringRedisSerializer);
//        template.setHashKeySerializer(stringRedisSerializer);
//        template.setDefaultSerializer(jackson2JsonRedisSerializer());
//        template.setConnectionFactory(factory);
//        template.afterPropertiesSet();
//        return template;
//    }
//
//}
