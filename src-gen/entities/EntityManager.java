package entities;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.lang.reflect.Method;
import java.util.Map;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.File;

public class EntityManager {

	private static Map<String, List> AllInstance = new HashMap<String, List>();
	
	private static List<User> UserInstances = new LinkedList<User>();
	private static List<Student> StudentInstances = new LinkedList<Student>();
	private static List<Faculty> FacultyInstances = new LinkedList<Faculty>();
	private static List<Book> BookInstances = new LinkedList<Book>();
	private static List<Subject> SubjectInstances = new LinkedList<Subject>();
	private static List<BookCopy> BookCopyInstances = new LinkedList<BookCopy>();
	private static List<Loan> LoanInstances = new LinkedList<Loan>();
	private static List<Reserve> ReserveInstances = new LinkedList<Reserve>();
	private static List<RecommendBook> RecommendBookInstances = new LinkedList<RecommendBook>();
	private static List<Administrator> AdministratorInstances = new LinkedList<Administrator>();
	private static List<Librarian> LibrarianInstances = new LinkedList<Librarian>();

	
	/* Put instances list into Map */
	static {
		AllInstance.put("User", UserInstances);
		AllInstance.put("Student", StudentInstances);
		AllInstance.put("Faculty", FacultyInstances);
		AllInstance.put("Book", BookInstances);
		AllInstance.put("Subject", SubjectInstances);
		AllInstance.put("BookCopy", BookCopyInstances);
		AllInstance.put("Loan", LoanInstances);
		AllInstance.put("Reserve", ReserveInstances);
		AllInstance.put("RecommendBook", RecommendBookInstances);
		AllInstance.put("Administrator", AdministratorInstances);
		AllInstance.put("Librarian", LibrarianInstances);
	} 
		
	/* Save State */
	public static void save(File file) {
		
		try {
			
			ObjectOutputStream stateSave = new ObjectOutputStream(new FileOutputStream(file));
			
			stateSave.writeObject(UserInstances);
			stateSave.writeObject(StudentInstances);
			stateSave.writeObject(FacultyInstances);
			stateSave.writeObject(BookInstances);
			stateSave.writeObject(SubjectInstances);
			stateSave.writeObject(BookCopyInstances);
			stateSave.writeObject(LoanInstances);
			stateSave.writeObject(ReserveInstances);
			stateSave.writeObject(RecommendBookInstances);
			stateSave.writeObject(AdministratorInstances);
			stateSave.writeObject(LibrarianInstances);
			
			stateSave.close();
					
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/* Load State */
	public static void load(File file) {
		
		try {
			
			ObjectInputStream stateLoad = new ObjectInputStream(new FileInputStream(file));
			
			try {
				
				UserInstances =  (List<User>) stateLoad.readObject();
				AllInstance.put("User", UserInstances);
				StudentInstances =  (List<Student>) stateLoad.readObject();
				AllInstance.put("Student", StudentInstances);
				FacultyInstances =  (List<Faculty>) stateLoad.readObject();
				AllInstance.put("Faculty", FacultyInstances);
				BookInstances =  (List<Book>) stateLoad.readObject();
				AllInstance.put("Book", BookInstances);
				SubjectInstances =  (List<Subject>) stateLoad.readObject();
				AllInstance.put("Subject", SubjectInstances);
				BookCopyInstances =  (List<BookCopy>) stateLoad.readObject();
				AllInstance.put("BookCopy", BookCopyInstances);
				LoanInstances =  (List<Loan>) stateLoad.readObject();
				AllInstance.put("Loan", LoanInstances);
				ReserveInstances =  (List<Reserve>) stateLoad.readObject();
				AllInstance.put("Reserve", ReserveInstances);
				RecommendBookInstances =  (List<RecommendBook>) stateLoad.readObject();
				AllInstance.put("RecommendBook", RecommendBookInstances);
				AdministratorInstances =  (List<Administrator>) stateLoad.readObject();
				AllInstance.put("Administrator", AdministratorInstances);
				LibrarianInstances =  (List<Librarian>) stateLoad.readObject();
				AllInstance.put("Librarian", LibrarianInstances);
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	/* create object */  
	public static Object createObject(String Classifer) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method createObjectMethod = c.getDeclaredMethod("create" + Classifer + "Object");
			return createObjectMethod.invoke(c);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/* add object */  
	public static Object addObject(String Classifer, Object ob) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method addObjectMethod = c.getDeclaredMethod("add" + Classifer + "Object", Class.forName("entities." + Classifer));
			return  (boolean) addObjectMethod.invoke(c, ob);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}	
	
	/* add objects */  
	public static Object addObjects(String Classifer, List obs) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method addObjectsMethod = c.getDeclaredMethod("add" + Classifer + "Objects", Class.forName("java.util.List"));
			return  (boolean) addObjectsMethod.invoke(c, obs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/* Release object */
	public static boolean deleteObject(String Classifer, Object ob) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method deleteObjectMethod = c.getDeclaredMethod("delete" + Classifer + "Object", Class.forName("entities." + Classifer));
			return  (boolean) deleteObjectMethod.invoke(c, ob);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/* Release objects */
	public static boolean deleteObjects(String Classifer, List obs) {
		try
		{
			Class c = Class.forName("entities.EntityManager");
			Method deleteObjectMethod = c.getDeclaredMethod("delete" + Classifer + "Objects", Class.forName("java.util.List"));
			return  (boolean) deleteObjectMethod.invoke(c, obs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}		 	
	
	 /* Get all objects belongs to same class */
	public static List getAllInstancesOf(String ClassName) {
			 return AllInstance.get(ClassName);
	}	

   /* Sub-create object */
	public static User createUserObject() {
		User o = new User();
		return o;
	}
	
	public static boolean addUserObject(User o) {
		return UserInstances.add(o);
	}
	
	public static boolean addUserObjects(List<User> os) {
		return UserInstances.addAll(os);
	}
	
	public static boolean deleteUserObject(User o) {
		return UserInstances.remove(o);
	}
	
	public static boolean deleteUserObjects(List<User> os) {
		return UserInstances.removeAll(os);
	}
	public static Student createStudentObject() {
		Student o = new Student();
		return o;
	}
	
	public static boolean addStudentObject(Student o) {
		return StudentInstances.add(o);
	}
	
	public static boolean addStudentObjects(List<Student> os) {
		return StudentInstances.addAll(os);
	}
	
	public static boolean deleteStudentObject(Student o) {
		return StudentInstances.remove(o);
	}
	
	public static boolean deleteStudentObjects(List<Student> os) {
		return StudentInstances.removeAll(os);
	}
	public static Faculty createFacultyObject() {
		Faculty o = new Faculty();
		return o;
	}
	
	public static boolean addFacultyObject(Faculty o) {
		return FacultyInstances.add(o);
	}
	
	public static boolean addFacultyObjects(List<Faculty> os) {
		return FacultyInstances.addAll(os);
	}
	
	public static boolean deleteFacultyObject(Faculty o) {
		return FacultyInstances.remove(o);
	}
	
	public static boolean deleteFacultyObjects(List<Faculty> os) {
		return FacultyInstances.removeAll(os);
	}
	public static Book createBookObject() {
		Book o = new Book();
		return o;
	}
	
	public static boolean addBookObject(Book o) {
		return BookInstances.add(o);
	}
	
	public static boolean addBookObjects(List<Book> os) {
		return BookInstances.addAll(os);
	}
	
	public static boolean deleteBookObject(Book o) {
		return BookInstances.remove(o);
	}
	
	public static boolean deleteBookObjects(List<Book> os) {
		return BookInstances.removeAll(os);
	}
	public static Subject createSubjectObject() {
		Subject o = new Subject();
		return o;
	}
	
	public static boolean addSubjectObject(Subject o) {
		return SubjectInstances.add(o);
	}
	
	public static boolean addSubjectObjects(List<Subject> os) {
		return SubjectInstances.addAll(os);
	}
	
	public static boolean deleteSubjectObject(Subject o) {
		return SubjectInstances.remove(o);
	}
	
	public static boolean deleteSubjectObjects(List<Subject> os) {
		return SubjectInstances.removeAll(os);
	}
	public static BookCopy createBookCopyObject() {
		BookCopy o = new BookCopy();
		return o;
	}
	
	public static boolean addBookCopyObject(BookCopy o) {
		return BookCopyInstances.add(o);
	}
	
	public static boolean addBookCopyObjects(List<BookCopy> os) {
		return BookCopyInstances.addAll(os);
	}
	
	public static boolean deleteBookCopyObject(BookCopy o) {
		return BookCopyInstances.remove(o);
	}
	
	public static boolean deleteBookCopyObjects(List<BookCopy> os) {
		return BookCopyInstances.removeAll(os);
	}
	public static Loan createLoanObject() {
		Loan o = new Loan();
		return o;
	}
	
	public static boolean addLoanObject(Loan o) {
		return LoanInstances.add(o);
	}
	
	public static boolean addLoanObjects(List<Loan> os) {
		return LoanInstances.addAll(os);
	}
	
	public static boolean deleteLoanObject(Loan o) {
		return LoanInstances.remove(o);
	}
	
	public static boolean deleteLoanObjects(List<Loan> os) {
		return LoanInstances.removeAll(os);
	}
	public static Reserve createReserveObject() {
		Reserve o = new Reserve();
		return o;
	}
	
	public static boolean addReserveObject(Reserve o) {
		return ReserveInstances.add(o);
	}
	
	public static boolean addReserveObjects(List<Reserve> os) {
		return ReserveInstances.addAll(os);
	}
	
	public static boolean deleteReserveObject(Reserve o) {
		return ReserveInstances.remove(o);
	}
	
	public static boolean deleteReserveObjects(List<Reserve> os) {
		return ReserveInstances.removeAll(os);
	}
	public static RecommendBook createRecommendBookObject() {
		RecommendBook o = new RecommendBook();
		return o;
	}
	
	public static boolean addRecommendBookObject(RecommendBook o) {
		return RecommendBookInstances.add(o);
	}
	
	public static boolean addRecommendBookObjects(List<RecommendBook> os) {
		return RecommendBookInstances.addAll(os);
	}
	
	public static boolean deleteRecommendBookObject(RecommendBook o) {
		return RecommendBookInstances.remove(o);
	}
	
	public static boolean deleteRecommendBookObjects(List<RecommendBook> os) {
		return RecommendBookInstances.removeAll(os);
	}
	public static Administrator createAdministratorObject() {
		Administrator o = new Administrator();
		return o;
	}
	
	public static boolean addAdministratorObject(Administrator o) {
		return AdministratorInstances.add(o);
	}
	
	public static boolean addAdministratorObjects(List<Administrator> os) {
		return AdministratorInstances.addAll(os);
	}
	
	public static boolean deleteAdministratorObject(Administrator o) {
		return AdministratorInstances.remove(o);
	}
	
	public static boolean deleteAdministratorObjects(List<Administrator> os) {
		return AdministratorInstances.removeAll(os);
	}
	public static Librarian createLibrarianObject() {
		Librarian o = new Librarian();
		return o;
	}
	
	public static boolean addLibrarianObject(Librarian o) {
		return LibrarianInstances.add(o);
	}
	
	public static boolean addLibrarianObjects(List<Librarian> os) {
		return LibrarianInstances.addAll(os);
	}
	
	public static boolean deleteLibrarianObject(Librarian o) {
		return LibrarianInstances.remove(o);
	}
	
	public static boolean deleteLibrarianObjects(List<Librarian> os) {
		return LibrarianInstances.removeAll(os);
	}
  
}

