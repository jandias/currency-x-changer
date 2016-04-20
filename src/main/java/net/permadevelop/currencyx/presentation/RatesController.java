package net.permadevelop.currencyx.presentation;

import net.permadevelop.currencyx.domain.Rates;
import net.permadevelop.currencyx.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;

@Controller
@RequestMapping("rates")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class RatesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RatesController.class);

    @Inject
    private CurrencyService service;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Rates getRates(@RequestParam("date") String date) {
        return service.rates(date);
    }
}
