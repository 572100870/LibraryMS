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
import org.eclipse.sirisus.sample.library.Scheduler;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Scheduler</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirisus.sample.library.impl.SchedulerImpl#getSchuduler <em>Schuduler</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SchedulerImpl extends MinimalEObjectImpl.Container implements Scheduler {
	/**
	 * The cached value of the '{@link #getSchuduler() <em>Schuduler</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSchuduler()
	 * @generated
	 * @ordered
	 */
	protected EList<LibraryManagementSystem> schuduler;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SchedulerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return LibraryPackage.Literals.SCHEDULER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LibraryManagementSystem> getSchuduler() {
		if (schuduler == null) {
			schuduler = new EObjectContainmentEList<LibraryManagementSystem>(LibraryManagementSystem.class, this,
					LibraryPackage.SCHEDULER__SCHUDULER);
		}
		return schuduler;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case LibraryPackage.SCHEDULER__SCHUDULER:
			return ((InternalEList<?>) getSchuduler()).basicRemove(otherEnd, msgs);
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
		case LibraryPackage.SCHEDULER__SCHUDULER:
			return getSchuduler();
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
		case LibraryPackage.SCHEDULER__SCHUDULER:
			getSchuduler().clear();
			getSchuduler().addAll((Collection<? extends LibraryManagementSystem>) newValue);
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
		case LibraryPackage.SCHEDULER__SCHUDULER:
			getSchuduler().clear();
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
		case LibraryPackage.SCHEDULER__SCHUDULER:
			return schuduler != null && !schuduler.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SchedulerImpl
