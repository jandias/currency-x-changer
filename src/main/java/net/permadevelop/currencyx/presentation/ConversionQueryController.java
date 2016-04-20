package net.permadevelop.currencyx.presentation;

import net.permadevelop.currencyx.domain.ConversionQuery;
import net.permadevelop.currencyx.service.ConversionQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping("query")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class ConversionQueryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConversionQueryController.class);

    @Inject
    private ConversionQueryService service;


    @RequestMapping(method = RequestMethod.GET)
    public Collection<ConversionQuery> allQueries(Principal principal) {
        return service.lastTen(principal.getName());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ConversionQuery> saveQuery(Principal principal, @RequestBody ConversionQuery query) {
        try {
            ConversionQuery savedQuery = service.save(principal.getName(), query);
            LOGGER.info("Saved query with id {} for user {} ", savedQuery.getId(), principal.getName());
            return new ResponseEntity<>(savedQuery, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.warn("Failed query save.", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
