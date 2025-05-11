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

public class ManageBookCRUDServiceImpl implements ManageBookCRUDService, Serializable {
	
	
	public static Map<String, List<String>> opINVRelatedEntity = new HashMap<String, List<String>>();
	
	
	ThirdPartyServices services;
			
	public ManageBookCRUDServiceImpl() {
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
	public boolean createBook(String callno, String title, String edition, String author, String publisher, String description, String isbn, int copynum) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get book
		Book book = null;
		//no nested iterator --  iterator: any previous:any
		for (Book boo : (List<Book>)EntityManager.getAllInstancesOf("Book"))
		{
			if (boo.getCallNo().equals(callno))
			{
				book = boo;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(book) == true) 
		{ 
			/* Logic here */
			Book boo = null;
			boo = (Book) EntityManager.createObject("Book");
			boo.setCallNo(callno);
			boo.setTitle(title);
			boo.setEdition(edition);
			boo.setAuthor(author);
			boo.setPublisher(publisher);
			boo.setDescription(description);
			boo.setISBn(isbn);
			boo.setCopyNum(copynum);
			EntityManager.addObject("Book", boo);
			
			
			refresh();
			// post-condition checking
			if (!(true && 
			boo.getCallNo() == callno
			 && 
			boo.getTitle() == title
			 && 
			boo.getEdition() == edition
			 && 
			boo.getAuthor() == author
			 && 
			boo.getPublisher() == publisher
			 && 
			boo.getDescription() == description
			 && 
			boo.getISBn() == isbn
			 && 
			boo.getCopyNum() == copynum
			 && 
			StandardOPs.includes(((List<Book>)EntityManager.getAllInstancesOf("Book")), boo)
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
		//string parameters: [callno, title, edition, author, publisher, description, isbn] 
		//all relevant vars : boo
		//all relevant entities : Book
	}  
	
	static {opINVRelatedEntity.put("createBook", Arrays.asList("Book"));}
	 
	@SuppressWarnings("unchecked")
	public Book queryBook(String callno) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get book
		Book book = null;
		//no nested iterator --  iterator: any previous:any
		for (Book boo : (List<Book>)EntityManager.getAllInstancesOf("Book"))
		{
			if (boo.getCallNo().equals(callno))
			{
				book = boo;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(book) == false) 
		{ 
			/* Logic here */
			
			
			refresh();
			// post-condition checking
			if (!(true)) {
				throw new PostconditionException();
			}
			
			refresh(); return book;
		}
		else
		{
			throw new PreconditionException();
		}
		//string parameters: [callno] 
	}  
	
	 
	@SuppressWarnings("unchecked")
	public boolean modifyBook(String callno, String title, String edition, String author, String publisher, String description, String isbn, int copynum) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get book
		Book book = null;
		//no nested iterator --  iterator: any previous:any
		for (Book boo : (List<Book>)EntityManager.getAllInstancesOf("Book"))
		{
			if (boo.getCallNo().equals(callno))
			{
				book = boo;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(book) == false) 
		{ 
			/* Logic here */
			book.setCallNo(callno);
			book.setTitle(title);
			book.setEdition(edition);
			book.setAuthor(author);
			book.setPublisher(publisher);
			book.setDescription(description);
			book.setISBn(isbn);
			book.setCopyNum(copynum);
			
			
			refresh();
			// post-condition checking
			if (!(book.getCallNo() == callno
			 && 
			book.getTitle() == title
			 && 
			book.getEdition() == edition
			 && 
			book.getAuthor() == author
			 && 
			book.getPublisher() == publisher
			 && 
			book.getDescription() == description
			 && 
			book.getISBn() == isbn
			 && 
			book.getCopyNum() == copynum
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
		//string parameters: [callno, title, edition, author, publisher, description, isbn] 
		//all relevant vars : book
		//all relevant entities : Book
	}  
	
	static {opINVRelatedEntity.put("modifyBook", Arrays.asList("Book"));}
	 
	@SuppressWarnings("unchecked")
	public boolean deleteBook(String callno) throws PreconditionException, PostconditionException, ThirdPartyServiceException {
		
		
		/* Code generated for contract definition */
		//Get book
		Book book = null;
		//no nested iterator --  iterator: any previous:any
		for (Book boo : (List<Book>)EntityManager.getAllInstancesOf("Book"))
		{
			if (boo.getCallNo().equals(callno))
			{
				book = boo;
				break;
			}
				
			
		}
		/* previous state in post-condition*/
 
		/* check precondition */
		if (StandardOPs.oclIsundefined(book) == false && StandardOPs.includes(((List<Book>)EntityManager.getAllInstancesOf("Book")), book)) 
		{ 
			/* Logic here */
			EntityManager.deleteObject("Book", book);
			
			
			refresh();
			// post-condition checking
			if (!(StandardOPs.excludes(((List<Book>)EntityManager.getAllInstancesOf("Book")), book)
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
		//string parameters: [callno] 
		//all relevant vars : book
		//all relevant entities : Book
	}  
	
	static {opINVRelatedEntity.put("deleteBook", Arrays.asList("Book"));}
	 
	
	
	
	/* temp property for controller */
			
	/* all get and set functions for temp property*/
	
	/* invarints checking*/
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList());
			
}
