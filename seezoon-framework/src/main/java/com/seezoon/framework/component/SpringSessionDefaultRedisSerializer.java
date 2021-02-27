package com.seezoon.framework.component;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

/**
 * 自定义spring session 序列化方式,按文档推荐使用json的方式，实测和spring security不匹配 故不启用
 *
 * @see <a href=
 *      "https://docs.spring.io/spring-session/docs/2.4.2/reference/html5/#custom-redisserializer">custom-redisserialize</a>
 *
 * @author hdf
 */
// @Component
@Deprecated
public class SpringSessionDefaultRedisSerializer extends GenericJackson2JsonRedisSerializer {

}
