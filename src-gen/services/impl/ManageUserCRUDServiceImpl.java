package services.impl;

import services.*;
import entities.*;
import java.util.List;
import java.util.LinkedList;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.Arrays;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import org.apache.commons.lang3.SerializationUtils;
import java.util.Iterator;

public class ManageUserCRUDServiceImpl implements ManageUserCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageUserCRUDServiceImpl() {
		services = new ThirdPartyServicesImpl();
	}

	
	//Shared variable from system services
	
	/* Shared variable from system services and get()/set() methods */
			
	/* all get and set functions for temp property*/
				
	
	
	/* Generate inject for sharing temp variables between use cases in system service */
	public void refresh() {
		LibraryManagementSystemSystem librarymanagementsystemsystem_service = (LibraryManagementSystemSystem) ServiceManager.getAllInstancesOf("LibraryManagementSystemSystem").get(0);
	}
	
	/* Generate buiness logic according to functional requirement */
	@SuppressWarnings("unchecked")
	public boolean createUser(String userid, String name, Sex sex, String password, String email, String faculty, int loanednumber, BorrowStatus borrowstatus, int suspensiondays, float overduefee) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get user
		User user = null;
		//no nested iterator --  iterator: any previous:any
		for (User use : (List<User>)EntityManager.getAllInstancesOf("User"))
		{
			if (use.getUserID().equals(userid))
			{
				user = use;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(user) == true) 
		{ 
			/* Logic here */
			User use = null;
			use = (User) EntityManager.createObject("User");
			use.setUserID(userid);
			use.setName(name);
			use.setSex(sex);
			use.setPassword(password);
			use.setEmail(email);
			use.setFaculty(faculty);
			use.setLoanedNumber(loanednumber);
			use.setBorrowStatus(borrowstatus);
			use.setSuspensionDays(suspensiondays);
			use.setOverDueFee(overduefee);
			EntityManager.addObject("User", use);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			use.getUserID() == userid
			 && 
			use.getName() == name
			 && 
			use.getSex() == sex
			 && 
			use.getPassword() == password
			 && 
			use.getEmail() == email
			 && 
			use.getFaculty() == faculty
			 && 
			use.getLoanedNumber() == loanednumber
			 && 
			use.getBorrowStatus() == borrowstatus
			 && 
			use.getSuspensionDays() == suspensiondays
			 && 
			use.getOverDueFee() == overduefee
			 && 
			StandardOPs.includes(((List<User>)EntityManager.getAllInstancesOf("User")), use)
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [userid, name, password, email, faculty] 
		//all relevant vars : use
		//all relevant entities : User
	}  
	
	static {opINVRelatedEntity.put("createUser", Arrays.asList("User"));}
	 
	@SuppressWarnings("unchecked")
	public User queryUser(String userid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get user
		User user = null;
		//no nested iterator --  iterator: any previous:any
		for (User use : (List<User>)EntityManager.getAllInstancesOf("User"))
		{
			if (use.getUserID().equals(userid))
			{
				user = use;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(user) == false) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return user;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [userid] 
	}  
	
	 
	@SuppressWarnings("unchecked")
	public boolean modifyUser(String userid, String name, Sex sex, String password, String email, String faculty, int loanednumber, BorrowStatus borrowstatus, int suspensiondays, float overduefee) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get user
		User user = null;
		//no nested iterator --  iterator: any previous:any
		for (User use : (List<User>)EntityManager.getAllInstancesOf("User"))
		{
			if (use.getUserID().equals(userid))
			{
				user = use;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(user) == false) 
		{ 
			/* Logic here */
			user.setUserID(userid);
			user.setName(name);
			user.setSex(sex);
			user.setPassword(password);
			user.setEmail(email);
			user.setFaculty(faculty);
			user.setLoanedNumber(loanednumber);
			user.setBorrowStatus(borrowstatus);
			user.setSuspensionDays(suspensiondays);
			user.setOverDueFee(overduefee);
			
			
			refresh();
			// post-condition checking
			if (!(user.getUserID() == userid
			 && 
			user.getName() == name
			 && 
			user.getSex() == sex
			 && 
			user.getPassword() == password
			 && 
			user.getEmail() == email
			 && 
			user.getFaculty() == faculty
			 && 
			user.getLoanedNumber() == loanednumber
			 && 
			user.getBorrowStatus() == borrowstatus
			 && 
			user.getSuspensionDays() == suspensiondays
			 && 
			user.getOverDueFee() == overduefee
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [userid, name, password, email, faculty] 
		//all relevant vars : user
		//all relevant entities : User
	}  
	
	static {opINVRelatedEntity.put("modifyUser", Arrays.asList("User"));}
	 
	@SuppressWarnings("unchecked")
	public boolean deleteUser(String userid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get user
		User user = null;
		//no nested iterator --  iterator: any previous:any
		for (User use : (List<User>)EntityManager.getAllInstancesOf("User"))
		{
			if (use.getUserID().equals(userid))
			{
				user = use;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(user) == false && StandardOPs.includes(((List<User>)EntityManager.getAllInstancesOf("User")), user)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("User", user);
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<User>)EntityManager.getAllInstancesOf("User")), user)
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [userid] 
		//all relevant vars : user
		//all relevant entities : User
	}  
	
	static {opINVRelatedEntity.put("deleteUser", Arrays.asList("User"));}
	 
	@SuppressWarnings("unchecked")
	public boolean createStudent(String userID, String name, Sex sex, String password, String email, String faculty, String major, Programme programme, StudentStatus registrationStatus) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get user
		Student user = null;
		//no nested iterator --  iterator: any previous:any
		for (Student u : (List<Student>)EntityManager.getAllInstancesOf("Student"))
		{
			if (u.getUserID().equals(userID))
			{
				user = u;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(user) == true) 
		{ 
			/* Logic here */
			Student u = null;
			u = (Student) EntityManager.createObject("Student");
			u.setUserID(userID);
			u.setName(name);
			u.setEmail(email);
			u.setPassword(password);
			u.setSex(sex);
			u.setFaculty(faculty);
			u.setLoanedNumber(0);
			u.setBorrowStatus(BorrowStatus.NORMAL);
			u.setSuspensionDays(0);
			u.setOverDueFee(0);
			u.setMajor(major);
			u.setProgramme(programme);
			u.setRegistrationStatus(registrationStatus);
			EntityManager.addObject("User", u);
			EntityManager.addObject("Student", u);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			u.getUserID() == userID
			 && 
			u.getName() == name
			 && 
			u.getEmail() == email
			 && 
			u.getPassword() == password
			 && 
			u.getSex() == sex
			 && 
			u.getFaculty() == faculty
			 && 
			u.getLoanedNumber() == 0
			 && 
			u.getBorrowStatus() == BorrowStatus.NORMAL
			 && 
			u.getSuspensionDays() == 0
			 && 
			u.getOverDueFee() == 0
			 && 
			u.getMajor() == major
			 && 
			u.getProgramme() == programme
			 && 
			u.getRegistrationStatus() == registrationStatus
			 && 
			StandardOPs.includes(((List<User>)EntityManager.getAllInstancesOf("User")), u)
			 && 
			StandardOPs.includes(((List<Student>)EntityManager.getAllInstancesOf("Student")), u)
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [userID, name, password, email, faculty, major] 
		//all relevant vars : u
		//all relevant entities : Student
	}  
	
	static {opINVRelatedEntity.put("createStudent", Arrays.asList("Student"));}
	 
	@SuppressWarnings("unchecked")
	public boolean createFaculty(String userID, String name, Sex sex, String password, String email, String faculty, FacultyPosition position, FacultyStatus status) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get user
		Faculty user = null;
		//no nested iterator --  iterator: any previous:any
		for (Faculty u : (List<Faculty>)EntityManager.getAllInstancesOf("Faculty"))
		{
			if (u.getUserID().equals(userID))
			{
				user = u;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(user) == true) 
		{ 
			/* Logic here */
			Faculty u = null;
			u = (Faculty) EntityManager.createObject("Faculty");
			u.setUserID(userID);
			u.setName(name);
			u.setEmail(email);
			u.setPassword(password);
			u.setSex(sex);
			u.setFaculty(faculty);
			u.setLoanedNumber(0);
			u.setBorrowStatus(BorrowStatus.NORMAL);
			u.setSuspensionDays(0);
			u.setOverDueFee(0);
			u.setPosition(position);
			u.setStatus(status);
			EntityManager.addObject("User", u);
			EntityManager.addObject("Faculty", u);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			u.getUserID() == userID
			 && 
			u.getName() == name
			 && 
			u.getEmail() == email
			 && 
			u.getPassword() == password
			 && 
			u.getSex() == sex
			 && 
			u.getFaculty() == faculty
			 && 
			u.getLoanedNumber() == 0
			 && 
			u.getBorrowStatus() == BorrowStatus.NORMAL
			 && 
			u.getSuspensionDays() == 0
			 && 
			u.getOverDueFee() == 0
			 && 
			u.getPosition() == position
			 && 
			u.getStatus() == status
			 && 
			StandardOPs.includes(((List<User>)EntityManager.getAllInstancesOf("User")), u)
			 && 
			StandardOPs.includes(((List<Faculty>)EntityManager.getAllInstancesOf("Faculty")), u)
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [userID, name, password, email, faculty] 
		//all relevant vars : u
		//all relevant entities : Faculty
	}  
	
	static {opINVRelatedEntity.put("createFaculty", Arrays.asList("Faculty"));}
	 
	@SuppressWarnings("unchecked")
	public boolean modifyStudent(String userID, String name, Sex sex, String password, String email, String faculty, String major, Programme programme, StudentStatus registrationStatus) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get user
		Student user = null;
		//no nested iterator --  iterator: any previous:any
		for (Student u : (List<Student>)EntityManager.getAllInstancesOf("Student"))
		{
			if (u.getUserID().equals(userID))
			{
				user = u;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(user) == false) 
		{ 
			/* Logic here */
			user.setUserID(userID);
			user.setName(name);
			user.setEmail(email);
			user.setPassword(password);
			user.setSex(sex);
			user.setFaculty(faculty);
			user.setLoanedNumber(0);
			user.setBorrowStatus(BorrowStatus.NORMAL);
			user.setSuspensionDays(0);
			user.setOverDueFee(0);
			user.setMajor(major);
			user.setProgramme(programme);
			user.setRegistrationStatus(registrationStatus);
			
			
			refresh();
			// post-condition checking
			if (!(user.getUserID() == userID
			 && 
			user.getName() == name
			 && 
			user.getEmail() == email
			 && 
			user.getPassword() == password
			 && 
			user.getSex() == sex
			 && 
			user.getFaculty() == faculty
			 && 
			user.getLoanedNumber() == 0
			 && 
			user.getBorrowStatus() == BorrowStatus.NORMAL
			 && 
			user.getSuspensionDays() == 0
			 && 
			user.getOverDueFee() == 0
			 && 
			user.getMajor() == major
			 && 
			user.getProgramme() == programme
			 && 
			user.getRegistrationStatus() == registrationStatus
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [userID, name, password, email, faculty, major] 
		//all relevant vars : user
		//all relevant entities : Student
	}  
	
	static {opINVRelatedEntity.put("modifyStudent", Arrays.asList("Student"));}
	 
	@SuppressWarnings("unchecked")
	public boolean modifyFaculty(String userID, String name, Sex sex, String password, String email, String faculty, String major, FacultyPosition position, FacultyStatus status) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get user
		Faculty user = null;
		//no nested iterator --  iterator: any previous:any
		for (Faculty u : (List<Faculty>)EntityManager.getAllInstancesOf("Faculty"))
		{
			if (u.getUserID().equals(userID))
			{
				user = u;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(user) == false) 
		{ 
			/* Logic here */
			user.setUserID(userID);
			user.setName(name);
			user.setEmail(email);
			user.setPassword(password);
			user.setSex(sex);
			user.setFaculty(faculty);
			user.setLoanedNumber(0);
			user.setBorrowStatus(BorrowStatus.NORMAL);
			user.setSuspensionDays(0);
			user.setOverDueFee(0);
			user.setPosition(position);
			user.setStatus(status);
			
			
			refresh();
			// post-condition checking
			if (!(user.getUserID() == userID
			 && 
			user.getName() == name
			 && 
			user.getEmail() == email
			 && 
			user.getPassword() == password
			 && 
			user.getSex() == sex
			 && 
			user.getFaculty() == faculty
			 && 
			user.getLoanedNumber() == 0
			 && 
			user.getBorrowStatus() == BorrowStatus.NORMAL
			 && 
			user.getSuspensionDays() == 0
			 && 
			user.getOverDueFee() == 0
			 && 
			user.getPosition() == position
			 && 
			user.getStatus() == status
			 && 
			true)) {
				throw new PostconditionException();
			}
			
		
			//return primitive type
			refresh();				
			return true;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [userID, name, password, email, faculty, major] 
		//all relevant vars : user
		//all relevant entities : Faculty
	}  
	
	static {opINVRelatedEntity.put("modifyFaculty", Arrays.asList("Faculty"));}
	 
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
