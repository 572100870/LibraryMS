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

public class ManageLibrarianCRUDServiceImpl implements ManageLibrarianCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageLibrarianCRUDServiceImpl() {
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
	public boolean createLibrarian(String librarianid, String name, String password) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get librarian
		Librarian librarian = null;
		//no nested iterator --  iterator: any previous:any
		for (Librarian lib : (List<Librarian>)EntityManager.getAllInstancesOf("Librarian"))
		{
			if (lib.getLibrarianID().equals(librarianid))
			{
				librarian = lib;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(librarian) == true) 
		{ 
			/* Logic here */
			Librarian lib = null;
			lib = (Librarian) EntityManager.createObject("Librarian");
			lib.setLibrarianID(librarianid);
			lib.setName(name);
			lib.setPassword(password);
			EntityManager.addObject("Librarian", lib);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			lib.getLibrarianID() == librarianid
			 && 
			lib.getName() == name
			 && 
			lib.getPassword() == password
			 && 
			StandardOPs.includes(((List<Librarian>)EntityManager.getAllInstancesOf("Librarian")), lib)
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
		//string parameters: [librarianid, name, password] 
		//all relevant vars : lib
		//all relevant entities : Librarian
	}  
	
	static {opINVRelatedEntity.put("createLibrarian", Arrays.asList("Librarian"));}
	 
	@SuppressWarnings("unchecked")
	public Librarian queryLibrarian(String librarianid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get librarian
		Librarian librarian = null;
		//no nested iterator --  iterator: any previous:any
		for (Librarian lib : (List<Librarian>)EntityManager.getAllInstancesOf("Librarian"))
		{
			if (lib.getLibrarianID().equals(librarianid))
			{
				librarian = lib;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(librarian) == false) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return librarian;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [librarianid] 
	}  
	
	 
	@SuppressWarnings("unchecked")
	public boolean modifyLibrarian(String librarianid, String name, String password) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get librarian
		Librarian librarian = null;
		//no nested iterator --  iterator: any previous:any
		for (Librarian lib : (List<Librarian>)EntityManager.getAllInstancesOf("Librarian"))
		{
			if (lib.getLibrarianID().equals(librarianid))
			{
				librarian = lib;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(librarian) == false) 
		{ 
			/* Logic here */
			librarian.setLibrarianID(librarianid);
			librarian.setName(name);
			librarian.setPassword(password);
			
			
			refresh();
			// post-condition checking
			if (!(librarian.getLibrarianID() == librarianid
			 && 
			librarian.getName() == name
			 && 
			librarian.getPassword() == password
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
		//string parameters: [librarianid, name, password] 
		//all relevant vars : librarian
		//all relevant entities : Librarian
	}  
	
	static {opINVRelatedEntity.put("modifyLibrarian", Arrays.asList("Librarian"));}
	 
	@SuppressWarnings("unchecked")
	public boolean deleteLibrarian(String librarianid) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get librarian
		Librarian librarian = null;
		//no nested iterator --  iterator: any previous:any
		for (Librarian lib : (List<Librarian>)EntityManager.getAllInstancesOf("Librarian"))
		{
			if (lib.getLibrarianID().equals(librarianid))
			{
				librarian = lib;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(librarian) == false && StandardOPs.includes(((List<Librarian>)EntityManager.getAllInstancesOf("Librarian")), librarian)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("Librarian", librarian);
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<Librarian>)EntityManager.getAllInstancesOf("Librarian")), librarian)
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
		//string parameters: [librarianid] 
		//all relevant vars : librarian
		//all relevant entities : Librarian
	}  
	
	static {opINVRelatedEntity.put("deleteLibrarian", Arrays.asList("Librarian"));}
	 
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
