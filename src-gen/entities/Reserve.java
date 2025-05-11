package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Reserve implements Serializable {
	
	/* all primary attributes */
	private LocalDate ReserveDate;
	private boolean IsReserveClosed;
	
	/* all references */
	private BookCopy ReservedCopy; 
	private User ReservedUser; 
	
	/* all get and set functions */
	public LocalDate getReserveDate() {
		return ReserveDate;
	}	
	
	public void setReserveDate(LocalDate reservedate) {
		this.ReserveDate = reservedate;
	}
	public boolean getIsReserveClosed() {
		return IsReserveClosed;
	}	
	
	public void setIsReserveClosed(boolean isreserveclosed) {
		this.IsReserveClosed = isreserveclosed;
	}
	
	/* all functions for reference*/
	public BookCopy getReservedCopy() {
		return ReservedCopy;
	}	
	
	public void setReservedCopy(BookCopy bookcopy) {
		this.ReservedCopy = bookcopy;
	}			
	public User getReservedUser() {
		return ReservedUser;
	}	
	
	public void setReservedUser(User user) {
		this.ReservedUser = user;
	}			
	


}
