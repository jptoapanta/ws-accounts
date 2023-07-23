package ec.edu.espe.banquito.accounts.repository;

import ec.edu.espe.banquito.accounts.controller.res.AccountResDto;
import ec.edu.espe.banquito.accounts.model.Account;
import ec.edu.espe.banquito.accounts.service.mapper.AccountMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountMapper accountMapper;
    @Test
    public void GenerateAccount(){

        Account account=Account.builder()
                .codeInternalAccount("XXX")
                .codeInternationalAccount("YYYYY")
                .accountHolderType(Account.AccountHolderType.CUS)
                .accountHolderCode("1721229399")
                .name("Cuenta corriente AAAAAA")
                .totalBalance(BigDecimal.valueOf(522.36))
                .availableBalance(BigDecimal.valueOf(23.62))
                .blockedBalance(BigDecimal.valueOf(1000))
                .state(Account.State.ACT)
                .lastInterestCalculationDate(new Date())
                .allowOverdraft(true)
                .maxOverdraft(BigDecimal.valueOf(652.36))
                .closedDate(new Date())
                .interestRate(BigDecimal.valueOf(5.2))
                .activationDate(new Date())
                .clientUk("49019afb-40bc-4860-ab7f-76e59ece1ce8")

                .createdBy("rpfreire1")
                .createdAt(new Date())
                .valid(true)
                .build();
        this.accountRepository.save(account);
        List<AccountResDto> accountList=this.accountMapper.toRes(this.accountRepository.findByClientUk(account.getClientUk()));
        System.out.println(accountList);
        if(accountList.isEmpty()){

            throw new RuntimeException("Client does not have accounts");
        }

    }

}