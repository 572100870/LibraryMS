package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface SearchBook {

	/* all system operations of the use case*/
	List<Book> searchBookByBarCode(String barcode) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	List<Book> searchBookByTitle(String title) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	List<Book> searchBookByAuthor(String authorname) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	List<Book> searchBookByISBN(String iSBNnumber) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	List<Book> searchBookBySubject(String subject) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
