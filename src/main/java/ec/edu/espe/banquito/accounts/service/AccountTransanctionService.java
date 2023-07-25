package ec.edu.espe.banquito.accounts.service;

import ec.edu.espe.banquito.accounts.controller.req.AccountReqDto;
import ec.edu.espe.banquito.accounts.controller.req.AccountTransactionReqDto;
import ec.edu.espe.banquito.accounts.controller.res.AccountTransactionResDto;
import ec.edu.espe.banquito.accounts.model.Account;
import ec.edu.espe.banquito.accounts.model.AccountTransaction;
import ec.edu.espe.banquito.accounts.repository.AccountRepository;
import ec.edu.espe.banquito.accounts.repository.AccountTransactionRepository;
import ec.edu.espe.banquito.accounts.service.mapper.AccountTransactionMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AccountTransanctionService {
    private final AccountTransactionMapper accountTransactionMapper;
    private final AccountTransactionRepository accountTransactionRepository;
    private final AccountRepository accountRepository;

    public List<AccountTransactionResDto> findByAccountsTransactionByClientUK(String accountUK){
        List<AccountTransactionResDto> accountTransactionList=this.accountTransactionMapper.toRes(this.accountTransactionRepository.findValidByAccountUniqueKeyOrderByBookingDateDesc(accountUK));
        if(accountTransactionList.isEmpty()){
            log.error("No transactions in this account");
            throw new RuntimeException("No transactions in this account");
        }

        return accountTransactionList;
    }

    public AccountTransactionResDto bankTransfer(AccountTransactionReqDto accountTransactionReqDto){
        AccountTransaction accountTransaction=new AccountTransaction();
        Optional<Account> accountDebtorTmp=this.accountRepository.findValidByCodeInternalAccount(accountTransactionReqDto.getDebtorAccount());
        switch (accountTransactionReqDto.getTransactionType()){
            case "TRANSFER":
                Optional<Account> accountCredtorTmp=this.accountRepository.findValidByCodeInternalAccount(accountTransactionReqDto.getCreditorAccount());
                BigDecimal ammountTmp=accountTransactionReqDto.getAmmount();
                if(accountDebtorTmp.isPresent() && accountCredtorTmp.isPresent()){
                    AccountTransaction accountTransactionDebtor=AccountTransaction.builder()
                            .uniqueKey(UUID.randomUUID().toString())
                            .transactionType(AccountTransaction.TransactionType.TRANSFER)
                            .reference(accountTransactionReqDto.getReference())
                            .ammount(ammountTmp.multiply(BigDecimal.valueOf(-1)))
                            .creditorAccount(accountTransactionReqDto.getCreditorAccount())
                            .creditorBankCode(accountTransactionReqDto.getCreditorBankCode())
                            .debtorAccount(accountTransactionReqDto.getDebtorAccount())
                            .debtorBankCode(accountTransactionReqDto.getDebtorBankCode())
                            .creationDate(new Date())
                            .bookingDate(new Date())
                            .valueDate(new Date())
                            .applyTax(false)
                            .state(AccountTransaction.State.ACT)
                            .notes(accountTransactionReqDto.getNotes())
                            .account(accountDebtorTmp.get())
                            .valid(true)
                            .build();

                    //BigDecimal ammountTemp=accountTransactionReqDto.getAmmount();

                    AccountTransaction accountTransactionCredtor=AccountTransaction.builder()
                            .uniqueKey(UUID.randomUUID().toString())
                            .transactionType(AccountTransaction.TransactionType.TRANSFER)
                            .reference(accountTransactionReqDto.getReference())
                            .ammount(ammountTmp)
                            .creditorAccount(accountTransactionReqDto.getCreditorAccount())
                            .creditorBankCode(accountTransactionReqDto.getCreditorBankCode())
                            .debtorAccount(accountTransactionReqDto.getDebtorBankCode())
                            .debtorBankCode(accountTransactionReqDto.getDebtorAccount())
                            .creationDate(new Date())
                            .bookingDate(new Date())
                            .valueDate(new Date())
                            .applyTax(false)
                            .state(AccountTransaction.State.ACT)
                            .notes(accountTransactionReqDto.getNotes())
                            .account(accountCredtorTmp.get())
                            .valid(true)
                            .build();

                    BigDecimal ammountDebtorTemp=accountTransactionDebtor.getAmmount();
                    BigDecimal resultDebtor=ammountDebtorTemp.subtract(ammountTmp);
                    accountDebtorTmp.get().setAvailableBalance(resultDebtor);

                    BigDecimal ammountCredtorTemp=accountTransactionCredtor.getAmmount();
                    BigDecimal resultCredtor=ammountCredtorTemp.add(ammountTmp);
                    accountCredtorTmp.get().setAvailableBalance(resultCredtor);

                    this.accountTransactionRepository.save(accountTransactionDebtor);
                    this.accountTransactionRepository.save(accountTransactionCredtor);
                    this.accountRepository.save(accountCredtorTmp.get());
                    this.accountRepository.save(accountDebtorTmp.get());

                }


        }
        return  this.accountTransactionMapper.toRes(accountTransaction);
    }
}
