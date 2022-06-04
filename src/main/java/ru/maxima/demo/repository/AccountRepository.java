package ru.maxima.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.maxima.demo.model.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository <Account, Long> {

    //В истории нет строки, когда вернули книгу - знаичт она на руках
    @Query(value = "select distinct (a.*) from  ENROLLMENT e " +
            "join ACCOUNT a on a.id = e.ACCOUNT_ID " +
            "where e.action <> 'RETURN'" +
            "and e.PRODUCT_ID = :productId", nativeQuery = true)
    List<Account> getAccounstHoldingBook(Long productId);
}
