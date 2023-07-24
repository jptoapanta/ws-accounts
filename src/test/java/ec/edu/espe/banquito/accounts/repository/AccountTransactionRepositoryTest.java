package ec.edu.espe.banquito.accounts.repository;

import ec.edu.espe.banquito.accounts.controller.res.AccountResDto;
import ec.edu.espe.banquito.accounts.model.Account;
import ec.edu.espe.banquito.accounts.model.AccountTransaction;
import ec.edu.espe.banquito.accounts.service.mapper.AccountTransactionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.rmi.server.UID;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AccountTransactionRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private  AccountTransactionRepository accountTransactionRepository;
    @Autowired
    private AccountTransactionMapper accountTransactionMapper;

    @Test
    public void GenerateTransaction(){
        Account account=this.accountRepository.findValidById(1).orElseThrow(()->{
            return new RuntimeException("Account with id "+1+"not found");
        });
        AccountTransaction accountTransaction=AccountTransaction.builder()
                .uniqueKey(UUID.randomUUID().toString())
                .transactionType(AccountTransaction.TransactionType.WITHDRAWAL)
                .reference("AAAA")
                .ammount(BigDecimal.valueOf(20.00))
                .creditorBankCode("XXXX")
                .creditorAccount("YYYYY")
                .debtorBankCode("DXXXX")
                .debtorAccount("DYYYYY")
                .creationDate(new Date())
                .bookingDate(new Date())
                .valueDate(new Date())
                .applyTax(false)
                .state(AccountTransaction.State.ACT)
                .notes("aassasa")
                .account(account)
                .valid(true)
                .createdBy("rpfreire1")
                .createdAt(new Date())
                .build();

        this.accountTransactionRepository.save(accountTransaction);



    }
}