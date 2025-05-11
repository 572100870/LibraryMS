package entities;

import services.impl.StandardOPs;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.io.Serializable;
import java.lang.reflect.Method;

public class Book implements Serializable {
	
	/* all primary attributes */
	private String CallNo;
	private String Title;
	private String Edition;
	private String Author;
	private String Publisher;
	private String Description;
	private String ISBn;
	private int CopyNum;
	
	/* all references */
	private List<BookCopy> Copys = new LinkedList<BookCopy>(); 
	private List<Subject> Subject = new LinkedList<Subject>(); 
	
	/* all get and set functions */
	public String getCallNo() {
		return CallNo;
	}	
	
	public void setCallNo(String callno) {
		this.CallNo = callno;
	}
	public String getTitle() {
		return Title;
	}	
	
	public void setTitle(String title) {
		this.Title = title;
	}
	public String getEdition() {
		return Edition;
	}	
	
	public void setEdition(String edition) {
		this.Edition = edition;
	}
	public String getAuthor() {
		return Author;
	}	
	
	public void setAuthor(String author) {
		this.Author = author;
	}
	public String getPublisher() {
		return Publisher;
	}	
	
	public void setPublisher(String publisher) {
		this.Publisher = publisher;
	}
	public String getDescription() {
		return Description;
	}	
	
	public void setDescription(String description) {
		this.Description = description;
	}
	public String getISBn() {
		return ISBn;
	}	
	
	public void setISBn(String isbn) {
		this.ISBn = isbn;
	}
	public int getCopyNum() {
		return CopyNum;
	}	
	
	public void setCopyNum(int copynum) {
		this.CopyNum = copynum;
	}
	
	/* all functions for reference*/
	public List<BookCopy> getCopys() {
		return Copys;
	}	
	
	public void addCopys(BookCopy bookcopy) {
		this.Copys.add(bookcopy);
	}
	
	public void deleteCopys(BookCopy bookcopy) {
		this.Copys.remove(bookcopy);
	}
	public List<Subject> getSubject() {
		return Subject;
	}	
	
	public void addSubject(Subject subject) {
		this.Subject.add(subject);
	}
	
	public void deleteSubject(Subject subject) {
		this.Subject.remove(subject);
	}
	

	/* invarints checking*/
	public boolean Book_BookCallNoUnique() {
		
		if (StandardOPs.isUnique(((List<Book>)EntityManager.getAllInstancesOf("Book")), "CallNo")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean Book_BookISBNUnique() {
		
		if (StandardOPs.isUnique(((List<Book>)EntityManager.getAllInstancesOf("Book")), "ISBn")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean Book_BookCopyNumGreatThanEqualZero() {
		
		if (CopyNum >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	//check all invariants
	public boolean checkAllInvairant() {
		
		if (Book_BookCallNoUnique() && Book_BookISBNUnique() && Book_BookCopyNumGreatThanEqualZero()) {
			return true;
		} else {
			return false;
		}
	}	
	
	//check invariant by inv name
	public boolean checkInvariant(String INVname) {
		
		try {
			Method m = this.getClass().getDeclaredMethod(INVname);
			return (boolean) m.invoke(this);
					
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	
	}	
	
	public final static ArrayList<String> allInvariantCheckingFunction = new ArrayList<String>(Arrays.asList("Book_BookCallNoUnique","Book_BookISBNUnique","Book_BookCopyNumGreatThanEqualZero"));

}
