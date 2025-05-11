package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface DeleteAccountService {

	/* all system operations of the use case*/
	boolean inputCard12(String cardID) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean inputPassword11(String password) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean ensureDeleteAccount() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
