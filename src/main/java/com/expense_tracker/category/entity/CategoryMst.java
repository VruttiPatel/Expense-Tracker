package com.expense_tracker.category.entity;

import java.util.Date;
import java.util.List;

import com.expense_tracker.cashbook.entity.CashbookMst;
import com.expense_tracker.expense.entity.ExpenseMst;

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
@Table(name = "category_mst")
public class CategoryMst {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long categoryId;

	@Column(nullable = false)
	private String categoryName;

	@ManyToOne
	@JoinColumn(name = "cashbookId")
	private CashbookMst cashbookMst;

	@OneToMany(mappedBy = "expenseId")
	private List<ExpenseMst> expense;

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
