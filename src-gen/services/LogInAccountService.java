package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface LogInAccountService {

	/* all system operations of the use case*/
	boolean inputAccount1(String defaultValue) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean inputCard1(String cardID) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean inputPassword2(String password) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean inputVerificationcode() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	char[] inputPassword();
	char[] inputPasswordNew(String text);
	char[] inputAccount();
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
