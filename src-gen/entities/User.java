package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class User implements Serializable {
	
	/* all primary attributes */
	private String UserID;
	private String Name;
	private Sex Sex;
	private String Password;
	private String Email;
	private String Faculty;
	private int LoanedNumber;
	private BorrowStatus BorrowStatus;
	private int SuspensionDays;
	private float OverDueFee;
	
	/* all references */
	private List<Loan> LoanedBook = new LinkedList<Loan>(); 
	private List<Reserve> ReservedBook = new LinkedList<Reserve>(); 
	private List<RecommendBook> RecommendedBook = new LinkedList<RecommendBook>(); 
	
	/* all get and set functions */
	public String getUserID() {
		return UserID;
	}	
	
	public void setUserID(String userid) {
		this.UserID = userid;
	}
	public String getName() {
		return Name;
	}	
	
	public void setName(String name) {
		this.Name = name;
	}
	public Sex getSex() {
		return Sex;
	}	
	
	public void setSex(Sex sex) {
		this.Sex = sex;
	}
	public String getPassword() {
		return Password;
	}	
	
	public void setPassword(String password) {
		this.Password = password;
	}
	public String getEmail() {
		return Email;
	}	
	
	public void setEmail(String email) {
		this.Email = email;
	}
	public String getFaculty() {
		return Faculty;
	}	
	
	public void setFaculty(String faculty) {
		this.Faculty = faculty;
	}
	public int getLoanedNumber() {
		return LoanedNumber;
	}	
	
	public void setLoanedNumber(int loanednumber) {
		this.LoanedNumber = loanednumber;
	}
	public BorrowStatus getBorrowStatus() {
		return BorrowStatus;
	}	
	
	public void setBorrowStatus(BorrowStatus borrowstatus) {
		this.BorrowStatus = borrowstatus;
	}
	public int getSuspensionDays() {
		return SuspensionDays;
	}	
	
	public void setSuspensionDays(int suspensiondays) {
		this.SuspensionDays = suspensiondays;
	}
	public float getOverDueFee() {
		return OverDueFee;
	}	
	
	public void setOverDueFee(float overduefee) {
		this.OverDueFee = overduefee;
	}
	
	/* all functions for reference*/
	public List<Loan> getLoanedBook() {
		return LoanedBook;
	}	
	
	public void addLoanedBook(Loan loan) {
		this.LoanedBook.add(loan);
	}
	
	public void deleteLoanedBook(Loan loan) {
		this.LoanedBook.remove(loan);
	}
	public List<Reserve> getReservedBook() {
		return ReservedBook;
	}	
	
	public void addReservedBook(Reserve reserve) {
		this.ReservedBook.add(reserve);
	}
	
	public void deleteReservedBook(Reserve reserve) {
		this.ReservedBook.remove(reserve);
	}
	public List<RecommendBook> getRecommendedBook() {
		return RecommendedBook;
	}	
	
	public void addRecommendedBook(RecommendBook recommendbook) {
		this.RecommendedBook.add(recommendbook);
	}
	
	public void deleteRecommendedBook(RecommendBook recommendbook) {
		this.RecommendedBook.remove(recommendbook);
	}
	

	/* invarints checking*/
	public boolean User_UniqueUserID() {
		
		if (StandardOPs.isUnique(((List<User>)EntityManager.getAllInstancesOf("User")), "UserID")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean User_OverDueFeeGreatThanEqualZero() {
		
		if (OverDueFee >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean User_LoanedNumberGreatThanEqualZero() {
		
		if (LoanedNumber >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean User_SuspensionDaysGreatThanEqualZero() {
		
		if (SuspensionDays >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	//check all invariants
	public boolean checkAllInvairant() {
		
		if (User_UniqueUserID() && User_OverDueFeeGreatThanEqualZero() && User_LoanedNumberGreatThanEqualZero() && User_SuspensionDaysGreatThanEqualZero()) {
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
	
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList("User_UniqueUserID","User_OverDueFeeGreatThanEqualZero","User_LoanedNumberGreatThanEqualZero","User_SuspensionDaysGreatThanEqualZero"));

}
