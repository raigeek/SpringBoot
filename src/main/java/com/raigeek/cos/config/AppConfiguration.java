package com.raigeek.cos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class AppConfiguration implements ApplicationListener<ApplicationStartedEvent> {

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */

    @Autowired
    private CacheManager cacheManager;

    @Override
    public void onApplicationEvent(final ApplicationStartedEvent event) {

        // here your code ...
        Cache cache = this.cacheManager.getCache("labels");
        cache.put("PahlaLable", "UskiValue");
        cache.put("A","B");
        System.out.println("Cache Ki Value: " + cache.get("PahlaLable").toString());
        System.out.println("Cache Ki Value: " + cache.getName());

        return;
    }

} // class