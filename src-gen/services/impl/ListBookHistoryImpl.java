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

public class ListBookHistoryImpl implements ListBookHistory, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ListBookHistoryImpl() {
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
	public List<Loan> listBorrowHistory(String uid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get user
		User user = null;
		//no nested iterator --  iterator: any previous:any
		for (User u : (List<User>)EntityManager.getAllInstancesOf("User"))
		{
			if (u.getUserID().equals(uid))
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
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return user.getLoanedBook();
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [uid] 
		//all relevant vars : user
		//all relevant entities : User
	}  
	
	static {opINVRelatedEntity.put("listBorrowHistory", Arrays.asList("User"));}
	 
	@SuppressWarnings("unchecked")
	public List<Loan> listHodingBook(String uid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get user
		User user = null;
		//no nested iterator --  iterator: any previous:any
		for (User u : (List<User>)EntityManager.getAllInstancesOf("User"))
		{
			if (u.getUserID().equals(uid))
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
			List<Loan> tempsl = new LinkedList<>();
			//no nested iterator --  iterator: select
			for (Loan l : user.getLoanedBook())
			{
				if (l.getIsReturned() == false)
				{
					tempsl.add(l);
				} 
			}
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return tempsl;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [uid] 
		//all relevant vars : user
		//all relevant entities : User
	}  
	
	static {opINVRelatedEntity.put("listHodingBook", Arrays.asList("User"));}
	 
	@SuppressWarnings("unchecked")
	public List<BookCopy> listOverDueBook(String uid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get user
		User user = null;
		//no nested iterator --  iterator: any previous:any
		for (User u : (List<User>)EntityManager.getAllInstancesOf("User"))
		{
			if (u.getUserID().equals(uid))
			{
				user = u;
				break;
			}
				
			
		}
		//Get loans
		List<Loan> loans = new LinkedList<>();
		//no nested iterator --  iterator: select previous:select
		for (Loan l : user.getLoanedBook())
		{
			if (l.getIsReturned() == false && l.getOverDueFee() > 0)
			{
				loans.add(l);
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(user) == false && StandardOPs.oclIsundefined(loans) == false) 
		{ 
			/* Logic here */
			List temps = new LinkedList<>();
			//no nested iterator --  iterator: collect
			for (Loan l : loans)
			{
				temps.add(l.getLoanedCopy());
			}
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return temps;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [uid] 
	}  
	
	 
	@SuppressWarnings("unchecked")
	public List<BookCopy> listReservationBook(String uid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get user
		User user = null;
		//no nested iterator --  iterator: any previous:any
		for (User u : (List<User>)EntityManager.getAllInstancesOf("User"))
		{
			if (u.getUserID().equals(uid))
			{
				user = u;
				break;
			}
				
			
		}
		//Get res
		List<Reserve> res = new LinkedList<>();
		res = user.getReservedBook();
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(user) == false && StandardOPs.oclIsundefined(res) == false) 
		{ 
			/* Logic here */
			List temps = new LinkedList<>();
			//no nested iterator --  iterator: collect
			for (Reserve r : res)
			{
				temps.add(r.getReservedCopy());
			}
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return temps;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [uid] 
	}  
	
	 
	@SuppressWarnings("unchecked")
	public List<RecommendBook> listRecommendBook(String uid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get user
		User user = null;
		//no nested iterator --  iterator: any previous:any
		for (User u : (List<User>)EntityManager.getAllInstancesOf("User"))
		{
			if (u.getUserID().equals(uid))
			{
				user = u;
				break;
			}
				
			
		}
		//Get rBooks
		List<RecommendBook> rBooks = new LinkedList<>();
		rBooks = user.getRecommendedBook();
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(user) == false && StandardOPs.oclIsundefined(rBooks) == false) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return rBooks;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [uid] 
	}  
	
	 
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
