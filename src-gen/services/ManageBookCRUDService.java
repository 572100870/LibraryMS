package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface ManageBookCRUDService {

	/* all system operations of the use case*/
	boolean createBook(String callno, String title, String edition, String author, String publisher, String description, String isbn, int copynum) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	Book queryBook(String callno) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyBook(String callno, String title, String edition, String author, String publisher, String description, String isbn, int copynum) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteBook(String callno) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
