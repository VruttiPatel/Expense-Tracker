package com.expense_tracker.payment.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.expense_tracker.category.entity.CategoryMst;
import com.expense_tracker.payment.entity.PaymentMst;

@Repository
public interface PaymentRepo extends CrudRepository<PaymentMst, Long> {

	Optional<PaymentMst> findByPaymentMode(String paymentMode);

	boolean existsByPaymentModeAndStatusAndCashbookMstCashbookId(String paymentMode, long active, long cashbookId);

	List<PaymentMst> findByCashbookMstCashbookIdAndStatus(long cashbookId, long active);

	Optional<PaymentMst> findByPaymentModeAndStatusAndCashbookMstCashbookId(String trim, long active, long cashbookId);

}
