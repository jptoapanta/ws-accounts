package ec.edu.espe.banquito.accounts.controller;

import ec.edu.espe.banquito.accounts.controller.req.AccountTransactionReqDto;
import ec.edu.espe.banquito.accounts.controller.res.AccountResDto;
import ec.edu.espe.banquito.accounts.controller.res.AccountTransactionResDto;
import ec.edu.espe.banquito.accounts.service.AccountService;
import ec.edu.espe.banquito.accounts.service.AccountTransanctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {
    private final AccountService accountService;
    private final AccountTransanctionService accountTransanctionService;

    @GetMapping("/accounts-client/{clientUK}")
    public ResponseEntity<List<AccountResDto>> findAccountsValidByClientUK(
            @PathVariable("clientUK") String clientUK
    ){
        return ResponseEntity.ok(accountService.findAccountsByClientUK(clientUK));
    }

    @GetMapping("/account/{accountUK}")
    public ResponseEntity<AccountResDto> findAccountsValidByAccountUK(
            @PathVariable("accountUK") String accountUK
    ){
        return ResponseEntity.ok(accountService.findAccountByAccountUK(accountUK));
    }

    @GetMapping("/account-internalcode/{accountInternalCode}")
    public ResponseEntity<AccountResDto> findAccountsValidByAccountInternalCode(
            @PathVariable("accountInternalCode") String accountInternalCode
    ){
        return ResponseEntity.ok(accountService.findAccountByInternalCodeAccount(accountInternalCode));
    }

    @PostMapping("/transaction")
    public ResponseEntity<AccountTransactionResDto> transactionAccounts(
            @RequestBody AccountTransactionReqDto accountTransactionReqDto
            ){
        return ResponseEntity.ok(this.accountTransanctionService.bankTransfer(accountTransactionReqDto));
    }

}
