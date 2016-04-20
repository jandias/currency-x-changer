package net.permadevelop.currencyx.service;

import net.permadevelop.currencyx.domain.Account;
import net.permadevelop.currencyx.domain.ConversionQuery;
import net.permadevelop.currencyx.domain.ConversionQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;

@Service
public class ConversionQueryService {

    @Inject
    private ConversionQueryRepository repository;

    @Inject
    private AccountService accountService;

    @Transactional
    public ConversionQuery save(String userEmail, ConversionQuery query) {
        Account account = accountService.getByEmail(userEmail);
        query.setUser(account);
        repository.save(query);
        return query;
    }

    @Transactional(readOnly = true)
    public Collection<ConversionQuery> lastTen(String userEmail) {
        Account account = accountService.getByEmail(userEmail);
        return repository.findTop10ByUserOrderByCreationTimestampDesc(account);
    }
}
