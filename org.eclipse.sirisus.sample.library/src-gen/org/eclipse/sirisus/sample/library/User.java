/**
 */
package org.eclipse.sirisus.sample.library;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>User</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirisus.sample.library.User#getUser <em>User</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirisus.sample.library.LibraryPackage#getUser()
 * @model
 * @generated
 */
public interface User extends EObject {
	/**
	 * Returns the value of the '<em><b>User</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.sirisus.sample.library.LibraryManagementSystem}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User</em>' containment reference list.
	 * @see org.eclipse.sirisus.sample.library.LibraryPackage#getUser_User()
	 * @model containment="true"
	 * @generated
	 */
	EList<LibraryManagementSystem> getUser();

} // User
