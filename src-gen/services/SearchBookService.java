package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface SearchBookService {

	/* all system operations of the use case*/
	boolean inputBookInformation() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean inputCardID() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean exitStytem() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
