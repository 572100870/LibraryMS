package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Student extends User  implements Serializable {
	
	/* all primary attributes */
	private String Major;
	private Programme Programme;
	private StudentStatus RegistrationStatus;
	
	/* all references */
	
	/* all get and set functions */
	public String getMajor() {
		return Major;
	}	
	
	public void setMajor(String major) {
		this.Major = major;
	}
	public Programme getProgramme() {
		return Programme;
	}	
	
	public void setProgramme(Programme programme) {
		this.Programme = programme;
	}
	public StudentStatus getRegistrationStatus() {
		return RegistrationStatus;
	}	
	
	public void setRegistrationStatus(StudentStatus registrationstatus) {
		this.RegistrationStatus = registrationstatus;
	}
	
	/* all functions for reference*/
	

	/* invarints checking*/
	public boolean Student_StudentLoanLessThanEqualTwelve() {
		
		for (Student stu : ((List<Student>)EntityManager.getAllInstancesOf("Student"))) {
			if (!(stu.getLoanedNumber() <= 12)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean Student_StudentLoanedBookAssociationInvariants() {
		
		if (StandardOPs.size(super.getLoanedBook()) <= 12) {
			return true;
		} else {
			return false;
		}
	}
	
	//check all invariants
	public boolean checkAllInvairant() {
		
		if (Student_StudentLoanLessThanEqualTwelve() && Student_StudentLoanedBookAssociationInvariants()) {
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
	
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList("Student_StudentLoanLessThanEqualTwelve","Student_StudentLoanedBookAssociationInvariants"));

}
