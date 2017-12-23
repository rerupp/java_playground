package spring.microservices.web;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("JavaDoc")
@Controller
public class WebAccountsController {

    private final WebAccountsService webAccountsService;

    public WebAccountsController(final WebAccountsService webAccountsService) {
        this.webAccountsService = Objects.requireNonNull(webAccountsService, "The WebAccountsService cannot be null...");
    }

    @InitBinder
    public void initBinder(final WebDataBinder webDataBinder) {
        webDataBinder.setAllowedFields("accountNumber", "searchText");
    }

    @RequestMapping("/accounts")
    public String home() {
        return "index";
    }

    @RequestMapping("/accounts/{accountNumber}")
    public String byNumber(final Model model, @PathVariable("accountNumber") final String accountNumber) {
        model.addAttribute("account", webAccountsService.findByNumber(accountNumber));
        return "account";
    }

    @RequestMapping("/accounts/owner/{partialName}")
    public String ownerSearch(final Model model, @PathVariable("partialName") final String partialName) {
        final List<AccountDTO> accounts = webAccountsService.byOwnerContains(partialName);
        model.addAttribute("search", partialName);
        if (CollectionUtils.isNotEmpty(accounts)) {
            model.addAttribute("accounts", accounts);
        }
        return "accounts";
    }

    @RequestMapping(value = "/accounts/search", method = RequestMethod.GET)
    public String searchForm(final Model model) {
        model.addAttribute("searchCriteria", new SearchCriteria());
        return "accountSearch";
    }

    @RequestMapping(value = "/accounts/dosearch")
    public String search(final Model model, final SearchCriteria searchCriteria, final BindingResult bindingResult) {
        searchCriteria.validate(bindingResult);
        if (bindingResult.hasErrors()) {
            return "accountSearch";
        }
        final String accountNumber = searchCriteria.getAccountNumber();
        return StringUtils.isNotEmpty(accountNumber) ? byNumber(model, accountNumber) : ownerSearch(model, searchCriteria.getSearchText());
    }
}
