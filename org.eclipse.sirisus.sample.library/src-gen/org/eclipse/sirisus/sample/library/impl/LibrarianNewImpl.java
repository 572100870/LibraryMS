/**
 */
package org.eclipse.sirisus.sample.library.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.sirisus.sample.library.LibrarianNew;
import org.eclipse.sirisus.sample.library.LibraryManagementSystem;
import org.eclipse.sirisus.sample.library.LibraryPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Librarian New</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirisus.sample.library.impl.LibrarianNewImpl#getLibraryNew <em>Library New</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LibrarianNewImpl extends MinimalEObjectImpl.Container implements LibrarianNew {
	/**
	 * The cached value of the '{@link #getLibraryNew() <em>Library New</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLibraryNew()
	 * @generated
	 * @ordered
	 */
	protected EList<LibraryManagementSystem> libraryNew;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LibrarianNewImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryPackage.Literals.LIBRARIAN_NEW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LibraryManagementSystem> getLibraryNew() {
		if (libraryNew == null) {
			libraryNew = new EObjectContainmentEList<LibraryManagementSystem>(LibraryManagementSystem.class, this,
					LibraryPackage.LIBRARIAN_NEW__LIBRARY_NEW);
		}
		return libraryNew;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LibraryPackage.LIBRARIAN_NEW__LIBRARY_NEW:
			return ((InternalEList<?>) getLibraryNew()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case LibraryPackage.LIBRARIAN_NEW__LIBRARY_NEW:
			return getLibraryNew();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case LibraryPackage.LIBRARIAN_NEW__LIBRARY_NEW:
			getLibraryNew().clear();
			getLibraryNew().addAll((Collection<? extends LibraryManagementSystem>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case LibraryPackage.LIBRARIAN_NEW__LIBRARY_NEW:
			getLibraryNew().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case LibraryPackage.LIBRARIAN_NEW__LIBRARY_NEW:
			return libraryNew != null && !libraryNew.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //LibrarianNewImpl
