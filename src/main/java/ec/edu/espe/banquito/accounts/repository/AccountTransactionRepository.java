package ec.edu.espe.banquito.accounts.repository;

import ec.edu.espe.banquito.accounts.model.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction,Integer> {
    @Query("select act from AccountTransaction act " +
            "where (act.valid=true) and act.account.clientUk=:clientUK")
    List<AccountTransaction> findValidByAccountClientUkOrderByBookingDateDesc(String clientUK);


}
