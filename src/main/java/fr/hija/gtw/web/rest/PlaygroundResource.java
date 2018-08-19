package fr.hija.gtw.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.hija.gtw.security.oauth2.OAuth2CookieHelper;
import fr.hija.gtw.service.PlaygroundService;
import fr.hija.gtw.web.rest.vm.*;
import io.swagger.models.Swagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * REST controller for managing Gateway configuration.
 */
@RestController
@RequestMapping("/api/playground")
public class PlaygroundResource {

    private final Logger log = LoggerFactory.getLogger(PlaygroundResource.class);

    private final PlaygroundService playgroundService;
    private final RouteLocator routeLocator;
    private final DiscoveryClient discoveryClient;

    public PlaygroundResource(PlaygroundService playgroundService, RouteLocator routeLocator, DiscoveryClient discoveryClient) {
        this.routeLocator = routeLocator;
        this.discoveryClient = discoveryClient;
        this.playgroundService = playgroundService;
    }

    private String getUserId(HttpServletRequest request) {

        log.error("Trying to build user id ");
        log.error("Authentification is " + (SecurityContextHolder.getContext().getAuthentication()!=null ? "not " : "" )+ "null");
        if (SecurityContextHolder.getContext().getAuthentication()!=null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.error("AUTH Name : "+auth.getName());
            log.error("AUTH sessionId : "+ request.getRequestedSessionId());
        } else {
            log.error("UNAUTH REquest principal : "+ request.getUserPrincipal()!=null ? request.getUserPrincipal().getName() : "null");
            log.error("UNAUTH sessionId : "+ request.getRequestedSessionId());

        }
        String login = SecurityContextHolder.getContext().getAuthentication() != null ? SecurityContextHolder.getContext().getAuthentication().getName() : OAuth2CookieHelper.getCookie(request, "XSRF-TOKEN").getValue();
        log.error("PG_GET ID - RETURNING  : "+login);
        return login;
    }


/*    @GetMapping("/cache")
    @Timed
    public ResponseEntity<PlaygroundHTMLVM> getOperationHTML(@RequestBody Map<String, String> params) {
        return null;
    }*/

    @GetMapping("/full")
    @Timed
    public ResponseEntity<PlaygroundHTMLVM> getSessionTemplateHTML(HttpServletRequest request) {
        String userId = this.getUserId(request);
        PlaygroundHTMLVM vm = this.playgroundService.getPGSession(userId);
        return new ResponseEntity<>(vm, HttpStatus.OK);
    }
    @RequestMapping(value = "/full", method = RequestMethod.POST, consumes = MediaType
        .APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> saveSessionTemplateHTML(HttpServletRequest request, @RequestBody PlaygroundHTMLVM html) {
        String userId = this.getUserId(request);
       boolean bool =  this.playgroundService.savePgSession(userId, html);
        return new ResponseEntity<>(bool, HttpStatus.OK);
    }
    @RequestMapping(value = "/op", method = RequestMethod.POST, consumes = MediaType
        .APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> saveOperation(@RequestBody PlaygroundOperationDTO dto) {
       boolean bool = this.playgroundService.saveOperation(dto.getDesc().getOperationName(), dto);
        return new ResponseEntity<>(bool, HttpStatus.OK);
    }
/*
    @PostMapping(path = "/tp", consumes = "application/json")
    @Timed
    public ResponseEntity<?> saveTemplate(@RequestBody PlaygroundJSONTemplateVM vm) {
        boolean bool =   this.playgroundService.save(vm.getOperationName(), dto);
       return new ResponseEntity<>(bool, HttpStatus.OK);
    }
*/


    @PostMapping("/op/name")
    @Timed
    public ResponseEntity<PlaygroundJSONOperationVM> getOperationJSON(@RequestBody Map<String, String> params) {
        PlaygroundJSONOperationVM vm = this.playgroundService.getOperationJSON(params.get("operationName"));
        return new ResponseEntity<>(vm, HttpStatus.OK);
    }
    @GetMapping("/oplist")
    @Timed
    public ResponseEntity<Set<PlaygroundOperationDescVM>> getOperationList() {
        Set<PlaygroundOperationDescVM> vm = this.playgroundService.getOperationList();
        return new ResponseEntity<>(vm, HttpStatus.OK);
    }
    @RequestMapping(value = "/public-api", method = RequestMethod.POST, consumes = MediaType
        .APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> savePublicApi(@RequestBody PublicApiVM dto) {
        boolean bool = this.playgroundService.savePublicApi(dto);
        return new ResponseEntity<>(bool, HttpStatus.OK);
    }

    @GetMapping("/public-api/all")
    @Timed
    public ResponseEntity<Set<PublicApiVM>> getPublicApiList() {
        Set<PublicApiVM> vm = this.playgroundService.getPublicApiList();
        return new ResponseEntity<>(vm, HttpStatus.OK);
    }

    @GetMapping("/registered-ms/all")
    @Timed
    public ResponseEntity<List<Swagger>> getRegisteredMsList() {
        List<Route> routes = routeLocator.getRoutes();
        List<Swagger> vm = this.playgroundService.getRegisteredMsList(routes, discoveryClient);
        return new ResponseEntity<>(vm, HttpStatus.OK);
    }

}
