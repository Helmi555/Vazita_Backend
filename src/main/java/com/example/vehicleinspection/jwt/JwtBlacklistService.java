package com.example.vehicleinspection.jwt;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class JwtBlacklistService {


    @Value("${app.jwt.expiration}")
    private long jwtExpiration;
    private static final String PREFIX = "vazita_blacklisted_token:";

    private  final RedisTemplate<String, String> redisTemplate;

    public JwtBlacklistService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void blacklistToken(String token) {
        redisTemplate.opsForValue().set(PREFIX + token, "blacklisted", jwtExpiration, TimeUnit.MILLISECONDS);
    }

    public boolean isTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + token));
    }


}
