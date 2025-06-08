/**
 */
package org.eclipse.sirisus.sample.library.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.sirisus.sample.library.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class LibraryFactoryImpl extends EFactoryImpl implements LibraryFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LibraryFactory init() {
		try {
			LibraryFactory theLibraryFactory = (LibraryFactory) EPackage.Registry.INSTANCE
					.getEFactory(LibraryPackage.eNS_URI);
			if (theLibraryFactory != null) {
				return theLibraryFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new LibraryFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibraryFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case LibraryPackage.LIBRARY_MANAGEMENT_SYSTEM:
			return createLibraryManagementSystem();
		case LibraryPackage.USER:
			return createUser();
		case LibraryPackage.SCHEDULER:
			return createScheduler();
		case LibraryPackage.THIRD_PART_SYSTEM:
			return createThirdPartSystem();
		case LibraryPackage.LIBRARIAN_NEW:
			return createLibrarianNew();
		case LibraryPackage.LIBRARY_MANAGER:
			return createLibraryManager();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibraryManagementSystem createLibraryManagementSystem() {
		LibraryManagementSystemImpl libraryManagementSystem = new LibraryManagementSystemImpl();
		return libraryManagementSystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public User createUser() {
		UserImpl user = new UserImpl();
		return user;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Scheduler createScheduler() {
		SchedulerImpl scheduler = new SchedulerImpl();
		return scheduler;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThirdPartSystem createThirdPartSystem() {
		ThirdPartSystemImpl thirdPartSystem = new ThirdPartSystemImpl();
		return thirdPartSystem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibrarianNew createLibrarianNew() {
		LibrarianNewImpl librarianNew = new LibrarianNewImpl();
		return librarianNew;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibraryManager createLibraryManager() {
		LibraryManagerImpl libraryManager = new LibraryManagerImpl();
		return libraryManager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LibraryPackage getLibraryPackage() {
		return (LibraryPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static LibraryPackage getPackage() {
		return LibraryPackage.eINSTANCE;
	}

} //LibraryFactoryImpl
