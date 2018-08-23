package fr.hija.gtw.web.websocket;

import com.google.common.collect.EvictingQueue;
import fr.hija.gtw.web.websocket.dto.Ws1MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import java.security.Principal;
import java.time.Instant;
import java.util.Queue;

;

@Controller
public class MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    private static final int MAX_WS1_CACHE_SIZE = 20;
    private static final String WS1_CACHE_NAME="ws1";
    private final SimpMessageSendingOperations messagingTemplate;
    private static int stackindex = 1;
    private final CacheManager cacheManager;

    public MessageService(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;

        MutableConfiguration config = new MutableConfiguration<>();
        CachingProvider cachingProvider = Caching.getCachingProvider();
        this.cacheManager = cachingProvider.getCacheManager();
        Cache cache = cacheManager.createCache(WS1_CACHE_NAME, config);
        cache.put(0,EvictingQueue.create(MAX_WS1_CACHE_SIZE));
        log.info("Created HazelCast cache for WS1");
    }

    private static synchronized int stackIndex() {
        return stackindex++;
    }



    @MessageMapping("/ws1reciever")
    @SendTo("/topic/message/out")
    public Ws1MessageDTO sendMessage(@Payload Ws1MessageDTO message, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
        log.info("Recieved WS message "+message);
        message.setTime(Instant.now());
        Cache<Integer, Queue<Ws1MessageDTO>> cache = this.cacheManager.getCache(WS1_CACHE_NAME);
        Queue q = cache.get(0);
        q.add(message);
        log.info("Added message to queue 1. Queue length : "+ ((Queue)this.cacheManager.getCache(WS1_CACHE_NAME).get(0)).size());
        log.info("Added message to queue 2. Queue length : "+ q.size());
        cache.replace(0, q);
        log.info("Added message to queue 3. Queue length : "+ this.cacheManager.getCache(WS1_CACHE_NAME).get(0));
        return message;
    }


}
