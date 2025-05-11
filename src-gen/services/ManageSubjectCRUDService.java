package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface ManageSubjectCRUDService {

	/* all system operations of the use case*/
	boolean createSubject(String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	Subject querySubject(String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifySubject(String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteSubject(String name) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
