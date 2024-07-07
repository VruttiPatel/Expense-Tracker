package com.expense_tracker.expense.entity;

import java.util.Date;

import com.expense_tracker.cashbook.entity.CashbookMst;
import com.expense_tracker.category.entity.CategoryMst;
import com.expense_tracker.payment.entity.PaymentMst;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expense_mst")
public class ExpenseMst {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long expenseId;

	@ManyToOne
	@JoinColumn(name = "cashbookId")
	private CashbookMst cashbookMst;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	private CategoryMst categoryMst;

	@ManyToOne
	@JoinColumn(name = "paymentId")
	private PaymentMst paymentMst;

	@Column(nullable = false)
	private Double amount;

	@Column(nullable = false)
	private Date entryDateTime;

	@Column(nullable = false)
	private long entryType;

	@Column(nullable = false)
	private long status;

	private String remarks;

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
