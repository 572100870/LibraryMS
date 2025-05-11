package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface ListBookHistory {

	/* all system operations of the use case*/
	List<Loan> listBorrowHistory(String uid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	List<Loan> listHodingBook(String uid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	List<BookCopy> listOverDueBook(String uid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	List<BookCopy> listReservationBook(String uid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	List<RecommendBook> listRecommendBook(String uid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
