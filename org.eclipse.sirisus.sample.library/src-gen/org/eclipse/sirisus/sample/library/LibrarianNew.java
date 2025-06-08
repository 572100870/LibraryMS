/**
 */
package org.eclipse.sirisus.sample.library;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Librarian New</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirisus.sample.library.LibrarianNew#getLibraryNew <em>Library New</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirisus.sample.library.LibraryPackage#getLibrarianNew()
 * @model
 * @generated
 */
public interface LibrarianNew extends EObject {
	/**
	 * Returns the value of the '<em><b>Library New</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.sirisus.sample.library.LibraryManagementSystem}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Library New</em>' containment reference list.
	 * @see org.eclipse.sirisus.sample.library.LibraryPackage#getLibrarianNew_LibraryNew()
	 * @model containment="true"
	 * @generated
	 */
	EList<LibraryManagementSystem> getLibraryNew();

} // LibrarianNew
