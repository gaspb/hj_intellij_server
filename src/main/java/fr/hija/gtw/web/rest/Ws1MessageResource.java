package fr.hija.gtw.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.hija.gtw.web.websocket.dto.Ws1MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import javax.servlet.http.HttpServletRequest;
import java.util.Queue;

/**
 * REST controller for managing Gateway configuration.
 */
@RestController
@RequestMapping("/ws1")
public class Ws1MessageResource {

    private final Logger log = LoggerFactory.getLogger(Ws1MessageResource.class);

    private final RouteLocator routeLocator;

    private final DiscoveryClient discoveryClient;

    public Ws1MessageResource(RouteLocator routeLocator, DiscoveryClient discoveryClient) {
        this.routeLocator = routeLocator;
        this.discoveryClient = discoveryClient;
    }

    /**
     * GET  /cache : get the cached messages for ws1
     *
     * @return the ResponseEntity with status 200 (OK) and with body the ordered list of messages
     */
    @GetMapping("/cache")
    @Timed
    public ResponseEntity<Queue<Ws1MessageDTO>> getCachedWs1(HttpServletRequest request) {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        Cache<Integer, Queue<Ws1MessageDTO>> c =cacheManager.getCache("ws1");
        Queue<Ws1MessageDTO> q = c.get(0);
        log.info("Retrieving cached queue of size "+q.size());
        //Ws1MessageDTO[] arr = (Ws1MessageDTO[]) q.toArray();
        return new ResponseEntity<>(q, HttpStatus.OK);
    }

}
