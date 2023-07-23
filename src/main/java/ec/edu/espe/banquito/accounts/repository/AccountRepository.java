package ec.edu.espe.banquito.accounts.repository;

import ec.edu.espe.banquito.accounts.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer> {
    @Query("Select ac from Account  ac " +
            "where (ac.valid=true) and ac.id=:accountId")
    Optional<Account>findValidById(Integer accountId);

    @Query("SELECT  ac from Account ac " +
            "where (ac.valid=true) and ac.clientUk=:clientUK")
    List<Account>findByClientUk(String clientUK);

    @Query("Select ac from Account  ac " +
            "where (ac.valid=true) and ac.uniqueKey=:accountUK")
    Optional<Account>findValidByUK(String accountUK);

    @Query("Select ac from Account  ac " +
            "where (ac.valid=true) and ac.codeInternalAccount=:internalAccountCode")
    Optional<Account>findValidByCodeInternalAccount(String internalAccountCode);

}
