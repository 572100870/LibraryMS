/**
 */
package org.eclipse.sirisus.sample.library;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Third Part System</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirisus.sample.library.ThirdPartSystem#getThirdPartSystem <em>Third Part System</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirisus.sample.library.LibraryPackage#getThirdPartSystem()
 * @model
 * @generated
 */
public interface ThirdPartSystem extends EObject {
	/**
	 * Returns the value of the '<em><b>Third Part System</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.sirisus.sample.library.LibraryManagementSystem}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Third Part System</em>' containment reference list.
	 * @see org.eclipse.sirisus.sample.library.LibraryPackage#getThirdPartSystem_ThirdPartSystem()
	 * @model containment="true"
	 * @generated
	 */
	EList<LibraryManagementSystem> getThirdPartSystem();

} // ThirdPartSystem
