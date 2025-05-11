package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface ManageBookCopyCRUDService {

	/* all system operations of the use case*/
	boolean addBookCopy(String callNo, String barcode, String location) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	BookCopy queryBookCopy(String barcode) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyBookCopy(String barcode, CopyStatus status, String location, boolean isreserved) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteBookCopy(String barcode) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
