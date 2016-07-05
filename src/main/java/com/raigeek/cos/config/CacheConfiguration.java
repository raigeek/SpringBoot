package com.raigeek.cos.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PreDestroy;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(CacheConfiguration.class);


    @Autowired
    private Environment env;

    private net.sf.ehcache.CacheManager cacheManager;

    @PreDestroy
    public void destroy() {
        cacheManager.shutdown();
    }



    @Bean
    public CacheManager cacheManager() {
            return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
            EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
            cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
            cmfb.setShared(true);
            return cmfb;
    }

}
