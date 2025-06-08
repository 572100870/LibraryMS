/**
 */
package org.eclipse.sirisus.sample.library;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Scheduler</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirisus.sample.library.Scheduler#getSchuduler <em>Schuduler</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirisus.sample.library.LibraryPackage#getScheduler()
 * @model
 * @generated
 */
public interface Scheduler extends EObject {
	/**
	 * Returns the value of the '<em><b>Schuduler</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.sirisus.sample.library.LibraryManagementSystem}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schuduler</em>' containment reference list.
	 * @see org.eclipse.sirisus.sample.library.LibraryPackage#getScheduler_Schuduler()
	 * @model containment="true"
	 * @generated
	 */
	EList<LibraryManagementSystem> getSchuduler();

} // Scheduler
