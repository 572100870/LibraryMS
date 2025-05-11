package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class BookCopy implements Serializable {
	
	/* all primary attributes */
	private String Barcode;
	private CopyStatus Status;
	private String Location;
	private boolean IsReserved;
	
	/* all references */
	private Book BookBelongs; 
	private List<Loan> LoanedRecord = new LinkedList<Loan>(); 
	private List<Reserve> ReservationRecord = new LinkedList<Reserve>(); 
	
	/* all get and set functions */
	public String getBarcode() {
		return Barcode;
	}	
	
	public void setBarcode(String barcode) {
		this.Barcode = barcode;
	}
	public CopyStatus getStatus() {
		return Status;
	}	
	
	public void setStatus(CopyStatus status) {
		this.Status = status;
	}
	public String getLocation() {
		return Location;
	}	
	
	public void setLocation(String location) {
		this.Location = location;
	}
	public boolean getIsReserved() {
		return IsReserved;
	}	
	
	public void setIsReserved(boolean isreserved) {
		this.IsReserved = isreserved;
	}
	
	/* all functions for reference*/
	public Book getBookBelongs() {
		return BookBelongs;
	}	
	
	public void setBookBelongs(Book book) {
		this.BookBelongs = book;
	}			
	public List<Loan> getLoanedRecord() {
		return LoanedRecord;
	}	
	
	public void addLoanedRecord(Loan loan) {
		this.LoanedRecord.add(loan);
	}
	
	public void deleteLoanedRecord(Loan loan) {
		this.LoanedRecord.remove(loan);
	}
	public List<Reserve> getReservationRecord() {
		return ReservationRecord;
	}	
	
	public void addReservationRecord(Reserve reserve) {
		this.ReservationRecord.add(reserve);
	}
	
	public void deleteReservationRecord(Reserve reserve) {
		this.ReservationRecord.remove(reserve);
	}
	

	/* invarints checking*/
	public boolean BookCopy_BarCodeUnique() {
		
		if (StandardOPs.isUnique(((List<BookCopy>)EntityManager.getAllInstancesOf("BookCopy")), "Barcode")) {
			return true;
		} else {
			return false;
		}
	}
	
	//check all invariants
	public boolean checkAllInvairant() {
		
		if (BookCopy_BarCodeUnique()) {
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
	
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList("BookCopy_BarCodeUnique"));

}
