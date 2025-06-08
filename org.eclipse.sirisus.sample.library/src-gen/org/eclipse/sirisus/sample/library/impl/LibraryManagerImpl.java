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

import org.eclipse.sirisus.sample.library.LibraryManagementSystem;
import org.eclipse.sirisus.sample.library.LibraryManager;
import org.eclipse.sirisus.sample.library.LibraryPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Manager</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirisus.sample.library.impl.LibraryManagerImpl#getLibraryManager <em>Library Manager</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LibraryManagerImpl extends MinimalEObjectImpl.Container implements LibraryManager {
	/**
	 * The cached value of the '{@link #getLibraryManager() <em>Library Manager</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLibraryManager()
	 * @generated
	 * @ordered
	 */
	protected EList<LibraryManagementSystem> libraryManager;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LibraryManagerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryPackage.Literals.LIBRARY_MANAGER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LibraryManagementSystem> getLibraryManager() {
		if (libraryManager == null) {
			libraryManager = new EObjectContainmentEList<LibraryManagementSystem>(LibraryManagementSystem.class, this,
					LibraryPackage.LIBRARY_MANAGER__LIBRARY_MANAGER);
		}
		return libraryManager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LibraryPackage.LIBRARY_MANAGER__LIBRARY_MANAGER:
			return ((InternalEList<?>) getLibraryManager()).basicRemove(otherEnd, msgs);
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
		case LibraryPackage.LIBRARY_MANAGER__LIBRARY_MANAGER:
			return getLibraryManager();
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
		case LibraryPackage.LIBRARY_MANAGER__LIBRARY_MANAGER:
			getLibraryManager().clear();
			getLibraryManager().addAll((Collection<? extends LibraryManagementSystem>) newValue);
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
		case LibraryPackage.LIBRARY_MANAGER__LIBRARY_MANAGER:
			getLibraryManager().clear();
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
		case LibraryPackage.LIBRARY_MANAGER__LIBRARY_MANAGER:
			return libraryManager != null && !libraryManager.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //LibraryManagerImpl
