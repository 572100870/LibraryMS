package services;

import entities.*;  
import java.util.List;
import java.time.LocalDate;


public interface ManageUserCRUDService {

	/* all system operations of the use case*/
	boolean createUser(String userid, String name, Sex sex, String password, String email, String faculty, int loanednumber, BorrowStatus borrowstatus, int suspensiondays, float overduefee) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	User queryUser(String userid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyUser(String userid, String name, Sex sex, String password, String email, String faculty, int loanednumber, BorrowStatus borrowstatus, int suspensiondays, float overduefee) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean deleteUser(String userid) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean createStudent(String userID, String name, Sex sex, String password, String email, String faculty, String major, Programme programme, StudentStatus registrationStatus) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean createFaculty(String userID, String name, Sex sex, String password, String email, String faculty, FacultyPosition position, FacultyStatus status) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyStudent(String userID, String name, Sex sex, String password, String email, String faculty, String major, Programme programme, StudentStatus registrationStatus) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	boolean modifyFaculty(String userID, String name, Sex sex, String password, String email, String faculty, String major, FacultyPosition position, FacultyStatus status) throws PreconditionException, PostconditionException, ThirdPartyServiceException;
	
	/* all get and set functions for temp property*/
	
	/* all get and set functions for temp property*/
	
	/* invariant checking function */
}
