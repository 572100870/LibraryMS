package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Administrator implements Serializable {
	
	/* all primary attributes */
	private String AdminID;
	private String UserName;
	private String Password;
	
	/* all references */
	
	/* all get and set functions */
	public String getAdminID() {
		return AdminID;
	}	
	
	public void setAdminID(String adminid) {
		this.AdminID = adminid;
	}
	public String getUserName() {
		return UserName;
	}	
	
	public void setUserName(String username) {
		this.UserName = username;
	}
	public String getPassword() {
		return Password;
	}	
	
	public void setPassword(String password) {
		this.Password = password;
	}
	
	/* all functions for reference*/
	

	/* invarints checking*/
	public boolean Administrator_AdministratorIDUnique() {
		
		if (StandardOPs.isUnique(((List<Administrator>)EntityManager.getAllInstancesOf("Administrator")), "AdminID")) {
			return true;
		} else {
			return false;
		}
	}
	
	//check all invariants
	public boolean checkAllInvairant() {
		
		if (Administrator_AdministratorIDUnique()) {
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
	
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList("Administrator_AdministratorIDUnique"));

}
