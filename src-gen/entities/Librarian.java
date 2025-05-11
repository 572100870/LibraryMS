package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Librarian implements Serializable {
	
	/* all primary attributes */
	private String LibrarianID;
	private String Name;
	private String Password;
	
	/* all references */
	
	/* all get and set functions */
	public String getLibrarianID() {
		return LibrarianID;
	}	
	
	public void setLibrarianID(String librarianid) {
		this.LibrarianID = librarianid;
	}
	public String getName() {
		return Name;
	}	
	
	public void setName(String name) {
		this.Name = name;
	}
	public String getPassword() {
		return Password;
	}	
	
	public void setPassword(String password) {
		this.Password = password;
	}
	
	/* all functions for reference*/
	

	/* invarints checking*/
	public boolean Librarian_LibrarianIDUnique() {
		
		if (StandardOPs.isUnique(((List<Librarian>)EntityManager.getAllInstancesOf("Librarian")), "LibrarianID")) {
			return true;
		} else {
			return false;
		}
	}
	
	//check all invariants
	public boolean checkAllInvairant() {
		
		if (Librarian_LibrarianIDUnique()) {
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
	
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList("Librarian_LibrarianIDUnique"));

}
