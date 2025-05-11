package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface LibraryManagementSystemSystem {

	/* all system operations of the use case*/
	boolean recommendBook(String uid, String callNo, String title, String edition, String author, String publisher, String description, String isbn) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	BookCopy queryBookCopy(String barcode) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean addBookCopy(String callNo, String barcode, String location) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteBookCopy(String barcode) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean makeReservation(String uid, String barcode) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean cancelReservation(String uid, String barcode) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean borrowBook(String uid, String barcode) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean renewBook(String uid, String barcode) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean returnBook(String barcode) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean payOverDueFee(String uid, float fee) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	void checkOverDueandComputeOverDueFee() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	void dueSoonNotification() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	void countDownSuspensionDay() throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	
	/* invariant checking function */
}
