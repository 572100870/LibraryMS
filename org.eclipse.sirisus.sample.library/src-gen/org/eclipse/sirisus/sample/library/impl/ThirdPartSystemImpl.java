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
import org.eclipse.sirisus.sample.library.LibraryPackage;
import org.eclipse.sirisus.sample.library.ThirdPartSystem;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Third Part System</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirisus.sample.library.impl.ThirdPartSystemImpl#getThirdPartSystem <em>Third Part System</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ThirdPartSystemImpl extends MinimalEObjectImpl.Container implements ThirdPartSystem {
	/**
	 * The cached value of the '{@link #getThirdPartSystem() <em>Third Part System</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getThirdPartSystem()
	 * @generated
	 * @ordered
	 */
	protected EList<LibraryManagementSystem> thirdPartSystem;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ThirdPartSystemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryPackage.Literals.THIRD_PART_SYSTEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LibraryManagementSystem> getThirdPartSystem() {
		if (thirdPartSystem == null) {
			thirdPartSystem = new EObjectContainmentEList<LibraryManagementSystem>(LibraryManagementSystem.class, this,
					LibraryPackage.THIRD_PART_SYSTEM__THIRD_PART_SYSTEM);
		}
		return thirdPartSystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LibraryPackage.THIRD_PART_SYSTEM__THIRD_PART_SYSTEM:
			return ((InternalEList<?>) getThirdPartSystem()).basicRemove(otherEnd, msgs);
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
		case LibraryPackage.THIRD_PART_SYSTEM__THIRD_PART_SYSTEM:
			return getThirdPartSystem();
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
		case LibraryPackage.THIRD_PART_SYSTEM__THIRD_PART_SYSTEM:
			getThirdPartSystem().clear();
			getThirdPartSystem().addAll((Collection<? extends LibraryManagementSystem>) newValue);
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
		case LibraryPackage.THIRD_PART_SYSTEM__THIRD_PART_SYSTEM:
			getThirdPartSystem().clear();
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
		case LibraryPackage.THIRD_PART_SYSTEM__THIRD_PART_SYSTEM:
			return thirdPartSystem != null && !thirdPartSystem.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ThirdPartSystemImpl
