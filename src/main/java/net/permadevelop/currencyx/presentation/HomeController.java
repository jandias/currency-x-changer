package net.permadevelop.currencyx.presentation;

import net.permadevelop.currencyx.service.CurrencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.security.Principal;

@Controller
public class HomeController {

    private final CurrencyService currencyService;

    @Inject
    public HomeController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Principal principal, Model model) {
        if (principal == null) {
            return "home/homeNotSignedIn";
        }
        model.addAttribute("currencies", currencyService.allCurrencies());
        model.addAttribute("rates", currencyService.rates());
        return "home/home";
    }
}
