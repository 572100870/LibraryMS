/**
 */
package org.eclipse.sirisus.sample.library;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Manager</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirisus.sample.library.LibraryManager#getLibraryManager <em>Library Manager</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirisus.sample.library.LibraryPackage#getLibraryManager()
 * @model
 * @generated
 */
public interface LibraryManager extends EObject {
	/**
	 * Returns the value of the '<em><b>Library Manager</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.sirisus.sample.library.LibraryManagementSystem}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Library Manager</em>' containment reference list.
	 * @see org.eclipse.sirisus.sample.library.LibraryPackage#getLibraryManager_LibraryManager()
	 * @model containment="true"
	 * @generated
	 */
	EList<LibraryManagementSystem> getLibraryManager();

} // LibraryManager
