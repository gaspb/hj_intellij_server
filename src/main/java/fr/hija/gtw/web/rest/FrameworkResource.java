package fr.hija.gtw.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.config.JHipsterProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST controller for managing Gateway configuration.
 */
@RestController
@RequestMapping("/api/fwk")
public class FrameworkResource {

    private final Logger log = LoggerFactory.getLogger(FrameworkResource.class);

    private final RouteLocator routeLocator;

    private final DiscoveryClient discoveryClient;
    private final JHipsterProperties jHipsterProperties;

    public FrameworkResource(RouteLocator routeLocator, DiscoveryClient discoveryClient, JHipsterProperties jHipsterProperties) {
        this.routeLocator = routeLocator;
        this.discoveryClient = discoveryClient;
        this.jHipsterProperties = jHipsterProperties;
    }

    /**
     * GET  /routes : get the active routes.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the list of routes
     */
    @GetMapping("/endpoints")
    @Timed
    public ResponseEntity<Map> endpoints() {
        return new ResponseEntity<>( jHipsterProperties.getGateway().getAuthorizedMicroservicesEndpoints(), HttpStatus.OK);
    }
}
