package com.expense_tracker.cashbook.entity;

import java.util.Date;
import java.util.List;

import com.expense_tracker.auth.entity.UserMst;
import com.expense_tracker.category.entity.CategoryMst;
import com.expense_tracker.expense.entity.ExpenseMst;
import com.expense_tracker.payment.entity.PaymentMst;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cashbook_mst")
public class CashbookMst {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long cashbookId;

	@ManyToOne
	@JoinColumn(name = "userId")
	@JsonIgnore
	private UserMst userMst;

	@OneToMany(mappedBy = "paymentId")
	@JsonIgnore
	private List<PaymentMst> payment;

	@OneToMany(mappedBy = "expenseId")
	@JsonIgnore
	private List<ExpenseMst> expense;

	@Column(nullable = false)
	private String cashbookName;

	@Column(nullable = false)
	private long status;

	@Column(nullable = false)
	private long createdBy;

	@Column(nullable = false)
	private Date createdDate;

	@Column(nullable = false)
	private String createdByIp;

	private long updatedBy;

	private Date updatedDate;

	private String updatedByIp;

}
