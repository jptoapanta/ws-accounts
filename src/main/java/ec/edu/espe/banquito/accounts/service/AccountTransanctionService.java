package ec.edu.espe.banquito.accounts.service;

import ec.edu.espe.banquito.accounts.controller.res.AccountTransactionResDto;
import ec.edu.espe.banquito.accounts.model.AccountTransaction;
import ec.edu.espe.banquito.accounts.repository.AccountRepository;
import ec.edu.espe.banquito.accounts.repository.AccountTransactionRepository;
import ec.edu.espe.banquito.accounts.service.mapper.AccountTransactionMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AccountTransanctionService {
    private final AccountTransactionMapper accountTransactionMapper;
    private final AccountTransactionRepository accountTransactionRepository;

    public List<AccountTransactionResDto> findByAccountsTransactionByClientUK(String accountUK){
        List<AccountTransactionResDto> accountTransactionList=this.accountTransactionMapper.toRes(this.accountTransactionRepository.findValidByAccountUniqueKeyOrderByBookingDateDesc(accountUK));
        if(accountTransactionList.isEmpty()){
            log.error("No transactions in this account");
            throw new RuntimeException("No transactions in this account");
        }

        return accountTransactionList;
    }
}
