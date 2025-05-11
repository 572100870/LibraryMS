package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Loan implements Serializable {
	
	/* all primary attributes */
	private LocalDate LoanDate;
	private LocalDate RenewDate;
	private LocalDate DueDate;
	private LocalDate ReturnDate;
	private int RenewedTimes;
	private boolean IsReturned;
	private float OverDueFee;
	private boolean OverDue3Days;
	private boolean OverDue10Days;
	private boolean OverDue17Days;
	private boolean OverDue31Days;
	
	/* all references */
	private User LoanedUser; 
	private BookCopy LoanedCopy; 
	private Librarian LoanLibrarian; 
	private Librarian ReturnLibrarian; 
	
	/* all get and set functions */
	public LocalDate getLoanDate() {
		return LoanDate;
	}	
	
	public void setLoanDate(LocalDate loandate) {
		this.LoanDate = loandate;
	}
	public LocalDate getRenewDate() {
		return RenewDate;
	}	
	
	public void setRenewDate(LocalDate renewdate) {
		this.RenewDate = renewdate;
	}
	public LocalDate getDueDate() {
		return DueDate;
	}	
	
	public void setDueDate(LocalDate duedate) {
		this.DueDate = duedate;
	}
	public LocalDate getReturnDate() {
		return ReturnDate;
	}	
	
	public void setReturnDate(LocalDate returndate) {
		this.ReturnDate = returndate;
	}
	public int getRenewedTimes() {
		return RenewedTimes;
	}	
	
	public void setRenewedTimes(int renewedtimes) {
		this.RenewedTimes = renewedtimes;
	}
	public boolean getIsReturned() {
		return IsReturned;
	}	
	
	public void setIsReturned(boolean isreturned) {
		this.IsReturned = isreturned;
	}
	public float getOverDueFee() {
		return OverDueFee;
	}	
	
	public void setOverDueFee(float overduefee) {
		this.OverDueFee = overduefee;
	}
	public boolean getOverDue3Days() {
		return OverDue3Days;
	}	
	
	public void setOverDue3Days(boolean overdue3days) {
		this.OverDue3Days = overdue3days;
	}
	public boolean getOverDue10Days() {
		return OverDue10Days;
	}	
	
	public void setOverDue10Days(boolean overdue10days) {
		this.OverDue10Days = overdue10days;
	}
	public boolean getOverDue17Days() {
		return OverDue17Days;
	}	
	
	public void setOverDue17Days(boolean overdue17days) {
		this.OverDue17Days = overdue17days;
	}
	public boolean getOverDue31Days() {
		return OverDue31Days;
	}	
	
	public void setOverDue31Days(boolean overdue31days) {
		this.OverDue31Days = overdue31days;
	}
	
	/* all functions for reference*/
	public User getLoanedUser() {
		return LoanedUser;
	}	
	
	public void setLoanedUser(User user) {
		this.LoanedUser = user;
	}			
	public BookCopy getLoanedCopy() {
		return LoanedCopy;
	}	
	
	public void setLoanedCopy(BookCopy bookcopy) {
		this.LoanedCopy = bookcopy;
	}			
	public Librarian getLoanLibrarian() {
		return LoanLibrarian;
	}	
	
	public void setLoanLibrarian(Librarian librarian) {
		this.LoanLibrarian = librarian;
	}			
	public Librarian getReturnLibrarian() {
		return ReturnLibrarian;
	}	
	
	public void setReturnLibrarian(Librarian librarian) {
		this.ReturnLibrarian = librarian;
	}			
	

	/* invarints checking*/
	public boolean Loan_OverDueFeeGreatThanEqualZero() {
		
		if (OverDueFee >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean Loan_RenewedTimesLessThanEqualSix() {
		
		if (RenewedTimes >= 0 && RenewedTimes <= 6) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean Loan_LoanOverDueFeeGreatThanEqualZero() {
		
		if (OverDueFee >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean Loan_RenewDataAfterLoanDate() {
		
		if (((StandardOPs.oclIsundefined(RenewDate) == false) ? RenewDate.isAfter(LoanDate) : true)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean Loan_DueDateAfterLoanDate() {
		
		if (DueDate.isAfter(LoanDate)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean Loan_ReturnDateAfterORSameLoanDate() {
		
		if (((StandardOPs.oclIsundefined(ReturnDate) == false) ? ReturnDate.isAfter(LoanDate) || ReturnDate.isEqual(LoanDate) : true)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean Loan_DueDateAfterORSameRenewDate() {
		
		if (((StandardOPs.oclIsundefined(RenewDate) == false) ? DueDate.isAfter(RenewDate) || DueDate.isEqual(RenewDate) : true)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean Loan_ReturnDateSameORAfterRenewDate() {
		
		if (((StandardOPs.oclIsundefined(RenewDate) == false && StandardOPs.oclIsundefined(ReturnDate) == false) ? ReturnDate.isAfter(RenewDate) || ReturnDate.isEqual(RenewDate) : true)) {
			return true;
		} else {
			return false;
		}
	}
	
	//check all invariants
	public boolean checkAllInvairant() {
		
		if (Loan_OverDueFeeGreatThanEqualZero() && Loan_RenewedTimesLessThanEqualSix() && Loan_LoanOverDueFeeGreatThanEqualZero() && Loan_RenewDataAfterLoanDate() && Loan_DueDateAfterLoanDate() && Loan_ReturnDateAfterORSameLoanDate() && Loan_DueDateAfterORSameRenewDate() && Loan_ReturnDateSameORAfterRenewDate()) {
			return true;
		} else {
			return false;
		}
	}	
	
	//check invariant by inv name
	public boolean checkInvariant(String INVname) {
		
		try {
			Method m = this.getClass().getDeclaredMethod(INVname);
			return (boolean) m.invoke(this);
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	
	}	
	
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList("Loan_OverDueFeeGreatThanEqualZero","Loan_RenewedTimesLessThanEqualSix","Loan_LoanOverDueFeeGreatThanEqualZero","Loan_RenewDataAfterLoanDate","Loan_DueDateAfterLoanDate","Loan_ReturnDateAfterORSameLoanDate","Loan_DueDateAfterORSameRenewDate","Loan_ReturnDateSameORAfterRenewDate"));

}
