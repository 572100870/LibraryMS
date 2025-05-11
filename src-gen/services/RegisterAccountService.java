package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface RegisterAccountService {

	/* all system operations of the use case*/
	boolean inputAccount1(String defaultValue) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean inputPassword1(String defaultValue) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean ensurePassword1(String defaultValue) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
