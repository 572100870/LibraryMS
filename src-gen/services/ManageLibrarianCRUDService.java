package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface ManageLibrarianCRUDService {

	/* all system operations of the use case*/
	boolean createLibrarian(String librarianid, String name, String password) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	Librarian queryLibrarian(String librarianid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyLibrarian(String librarianid, String name, String password) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteLibrarian(String librarianid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
