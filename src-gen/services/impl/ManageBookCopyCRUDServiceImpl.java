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

public class ManageBookCopyCRUDServiceImpl implements ManageBookCopyCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageBookCopyCRUDServiceImpl() {
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
	public boolean addBookCopy(String callNo, String barcode, String location) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get book
		Book book = null;
		//no nested iterator --  iterator: any previous:any
		for (Book b : (List<Book>)EntityManager.getAllInstancesOf("Book"))
		{
			if (b.getCallNo().equals(callNo))
			{
				book = b;
				break;
			}
				
			
		}
		//Get bc
		BookCopy bc = null;
		//no nested iterator --  iterator: any previous:any
		for (BookCopy c : book.getCopys())
		{
			if (c.getBarcode().equals(barcode))
			{
				bc = c;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
		/* service reference */
		/* service temp attribute */
		/* objects in definition */  
		Book Pre_book = SerializationUtils.clone(book);
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(book) == false && StandardOPs.oclIsundefined(bc) == true) 
		{ 
			/* Logic here */
			BookCopy copy = null;
			copy = (BookCopy) EntityManager.createObject("BookCopy");
			copy.setBarcode(barcode);
			copy.setStatus(CopyStatus.AVAILABLE);
			copy.setLocation(location);
			copy.setIsReserved(false);
			book.addCopys(copy);
			copy.setBookBelongs(book);
			book.setCopyNum(book.getCopyNum()+1);
			EntityManager.addObject("BookCopy", copy);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			copy.getBarcode() == barcode
			 && 
			copy.getStatus() == CopyStatus.AVAILABLE
			 && 
			copy.getLocation() == location
			 && 
			copy.getIsReserved() == false
			 && 
			StandardOPs.includes(book.getCopys(), copy)
			 && 
			copy.getBookBelongs() == book
			 && 
			book.getCopyNum() == Pre_book.getCopyNum()+1
			 && 
			StandardOPs.includes(((List<BookCopy>)EntityManager.getAllInstancesOf("BookCopy")), copy)
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
		//string parameters: [callNo, barcode, location] 
		//all relevant vars : book copy
		//all relevant entities : Book BookCopy
	}  
	
	static {opINVRelatedEntity.put("addBookCopy", Arrays.asList("Book","BookCopy"));}
	 
	@SuppressWarnings("unchecked")
	public BookCopy queryBookCopy(String barcode) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get bookcopy
		BookCopy bookcopy = null;
		//no nested iterator --  iterator: any previous:any
		for (BookCopy boo : (List<BookCopy>)EntityManager.getAllInstancesOf("BookCopy"))
		{
			if (boo.getBarcode().equals(barcode))
			{
				bookcopy = boo;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(bookcopy) == false) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return bookcopy;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [barcode] 
	}  
	
	 
	@SuppressWarnings("unchecked")
	public boolean modifyBookCopy(String barcode, CopyStatus status, String location, boolean isreserved) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get bookcopy
		BookCopy bookcopy = null;
		//no nested iterator --  iterator: any previous:any
		for (BookCopy boo : (List<BookCopy>)EntityManager.getAllInstancesOf("BookCopy"))
		{
			if (boo.getBarcode().equals(barcode))
			{
				bookcopy = boo;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(bookcopy) == false) 
		{ 
			/* Logic here */
			bookcopy.setBarcode(barcode);
			bookcopy.setStatus(status);
			bookcopy.setLocation(location);
			bookcopy.setIsReserved(isreserved);
			
			
			refresh();
			// post-condition checking
			if (!(bookcopy.getBarcode() == barcode
			 && 
			bookcopy.getStatus() == status
			 && 
			bookcopy.getLocation() == location
			 && 
			bookcopy.getIsReserved() == isreserved
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
		//string parameters: [barcode, location] 
		//all relevant vars : bookcopy
		//all relevant entities : BookCopy
	}  
	
	static {opINVRelatedEntity.put("modifyBookCopy", Arrays.asList("BookCopy"));}
	 
	@SuppressWarnings("unchecked")
	public boolean deleteBookCopy(String barcode) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get bookcopy
		BookCopy bookcopy = null;
		//no nested iterator --  iterator: any previous:any
		for (BookCopy boo : (List<BookCopy>)EntityManager.getAllInstancesOf("BookCopy"))
		{
			if (boo.getBarcode().equals(barcode))
			{
				bookcopy = boo;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(bookcopy) == false && StandardOPs.includes(((List<BookCopy>)EntityManager.getAllInstancesOf("BookCopy")), bookcopy)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("BookCopy", bookcopy);
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<BookCopy>)EntityManager.getAllInstancesOf("BookCopy")), bookcopy)
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
		//string parameters: [barcode] 
		//all relevant vars : bookcopy
		//all relevant entities : BookCopy
	}  
	
	static {opINVRelatedEntity.put("deleteBookCopy", Arrays.asList("BookCopy"));}
	 
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
