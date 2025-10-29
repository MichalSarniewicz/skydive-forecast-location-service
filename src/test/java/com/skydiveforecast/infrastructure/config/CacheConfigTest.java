package com.skydiveforecast.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class CacheConfigTest {

    private final CacheConfig cacheConfig = new CacheConfig();

    @Test
    void cacheManager_ShouldCreateRedisCacheManager() {
        // Arrange
        RedisConnectionFactory factory = mock(RedisConnectionFactory.class);

        // Act
        RedisCacheManager cacheManager = cacheConfig.cacheManager(factory);

        // Assert
        assertThat(cacheManager).isNotNull();
    }
}
