package edu.privatebnk.consultation.rest;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import edu.privatebnk.consultation.persistence.model.Account;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Singleton
@Startup
public class TokenCacheUtil {
    private Cache<UUID, Account> refreshTokens;
    private Cache<String, Long> invalidatedJwt;
    @PostConstruct
    private void initialize() {
        refreshTokens = Caffeine.newBuilder()
                .maximumSize(1000)
                //.refreshAfterWrite(10, TimeUnit.MINUTES)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
        invalidatedJwt = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfter(new Expiry<String, Long>() {
                    @Override
                    public long expireAfterCreate(
                            String key, Long value, long currentTime) {
                        long cacheExp = TimeUnit.SECONDS.toNanos(value - System.currentTimeMillis());
                        System.out.println(cacheExp);
                        return cacheExp;
                        //return jwtUtil.getExpDate(value).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().minusNanos(currentTime).toEpochSecond(ZoneOffset.UTC);
                    }
                    @Override
                    public long expireAfterUpdate(
                            String key, Long value, long currentTime, long currentDuration) {
                        return currentDuration;
                    }
                    @Override
                    public long expireAfterRead(
                            String key, Long value, long currentTime, long currentDuration) {
                        return currentDuration;
                    }
                }).build();
        /*invalidatedJwt = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES).build();*/
    }

    public Cache<UUID, Account> getRefreshTokens() {
        return refreshTokens;
    }

    public Cache<String, Long> getInvalidatedJwt() {
        return invalidatedJwt;
    }
}
