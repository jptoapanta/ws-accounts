package ec.edu.espe.banquito.accounts.controller;

import ec.edu.espe.banquito.accounts.controller.res.AccountTransactionResDto;
import ec.edu.espe.banquito.accounts.service.AccountTransanctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
@CrossOrigin(origins = "http://localhost:4200")
public class AccountTransactionController {
    private final AccountTransanctionService accountTransanctionService;

    @GetMapping("/history-transaction/{accountUK}")
    public ResponseEntity<List<AccountTransactionResDto>> findTransactionsByClientUK(
        @PathVariable("accountUK") String accountUK
    ){
        return ResponseEntity.ok(accountTransanctionService.findByAccountsTransactionByClientUK(accountUK));
    }


}
