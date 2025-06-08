/**
 */
package org.eclipse.sirisus.sample.library;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.sirisus.sample.library.LibraryFactory
 * @model kind="package"
 * @generated
 */
public interface LibraryPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "library";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/library";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "library";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	LibraryPackage eINSTANCE = org.eclipse.sirisus.sample.library.impl.LibraryPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.sirisus.sample.library.impl.LibraryManagementSystemImpl <em>Management System</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.sirisus.sample.library.impl.LibraryManagementSystemImpl
	 * @see org.eclipse.sirisus.sample.library.impl.LibraryPackageImpl#getLibraryManagementSystem()
	 * @generated
	 */
	int LIBRARY_MANAGEMENT_SYSTEM = 0;

	/**
	 * The number of structural features of the '<em>Management System</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_MANAGEMENT_SYSTEM_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Management System</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_MANAGEMENT_SYSTEM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.sirisus.sample.library.impl.UserImpl <em>User</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.sirisus.sample.library.impl.UserImpl
	 * @see org.eclipse.sirisus.sample.library.impl.LibraryPackageImpl#getUser()
	 * @generated
	 */
	int USER = 1;

	/**
	 * The feature id for the '<em><b>User</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__USER = 0;

	/**
	 * The number of structural features of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.sirisus.sample.library.impl.SchedulerImpl <em>Scheduler</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.sirisus.sample.library.impl.SchedulerImpl
	 * @see org.eclipse.sirisus.sample.library.impl.LibraryPackageImpl#getScheduler()
	 * @generated
	 */
	int SCHEDULER = 2;

	/**
	 * The feature id for the '<em><b>Schuduler</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULER__SCHUDULER = 0;

	/**
	 * The number of structural features of the '<em>Scheduler</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Scheduler</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCHEDULER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.sirisus.sample.library.impl.ThirdPartSystemImpl <em>Third Part System</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.sirisus.sample.library.impl.ThirdPartSystemImpl
	 * @see org.eclipse.sirisus.sample.library.impl.LibraryPackageImpl#getThirdPartSystem()
	 * @generated
	 */
	int THIRD_PART_SYSTEM = 3;

	/**
	 * The feature id for the '<em><b>Third Part System</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIRD_PART_SYSTEM__THIRD_PART_SYSTEM = 0;

	/**
	 * The number of structural features of the '<em>Third Part System</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIRD_PART_SYSTEM_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Third Part System</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THIRD_PART_SYSTEM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.sirisus.sample.library.impl.LibrarianNewImpl <em>Librarian New</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.sirisus.sample.library.impl.LibrarianNewImpl
	 * @see org.eclipse.sirisus.sample.library.impl.LibraryPackageImpl#getLibrarianNew()
	 * @generated
	 */
	int LIBRARIAN_NEW = 4;

	/**
	 * The feature id for the '<em><b>Library New</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARIAN_NEW__LIBRARY_NEW = 0;

	/**
	 * The number of structural features of the '<em>Librarian New</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARIAN_NEW_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Librarian New</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARIAN_NEW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.sirisus.sample.library.impl.LibraryManagerImpl <em>Manager</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.sirisus.sample.library.impl.LibraryManagerImpl
	 * @see org.eclipse.sirisus.sample.library.impl.LibraryPackageImpl#getLibraryManager()
	 * @generated
	 */
	int LIBRARY_MANAGER = 5;

	/**
	 * The feature id for the '<em><b>Library Manager</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_MANAGER__LIBRARY_MANAGER = 0;

	/**
	 * The number of structural features of the '<em>Manager</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_MANAGER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Manager</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIBRARY_MANAGER_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.sirisus.sample.library.LibraryManagementSystem <em>Management System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Management System</em>'.
	 * @see org.eclipse.sirisus.sample.library.LibraryManagementSystem
	 * @generated
	 */
	EClass getLibraryManagementSystem();

	/**
	 * Returns the meta object for class '{@link org.eclipse.sirisus.sample.library.User <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User</em>'.
	 * @see org.eclipse.sirisus.sample.library.User
	 * @generated
	 */
	EClass getUser();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirisus.sample.library.User#getUser <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>User</em>'.
	 * @see org.eclipse.sirisus.sample.library.User#getUser()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_User();

	/**
	 * Returns the meta object for class '{@link org.eclipse.sirisus.sample.library.Scheduler <em>Scheduler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Scheduler</em>'.
	 * @see org.eclipse.sirisus.sample.library.Scheduler
	 * @generated
	 */
	EClass getScheduler();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirisus.sample.library.Scheduler#getSchuduler <em>Schuduler</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Schuduler</em>'.
	 * @see org.eclipse.sirisus.sample.library.Scheduler#getSchuduler()
	 * @see #getScheduler()
	 * @generated
	 */
	EReference getScheduler_Schuduler();

	/**
	 * Returns the meta object for class '{@link org.eclipse.sirisus.sample.library.ThirdPartSystem <em>Third Part System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Third Part System</em>'.
	 * @see org.eclipse.sirisus.sample.library.ThirdPartSystem
	 * @generated
	 */
	EClass getThirdPartSystem();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirisus.sample.library.ThirdPartSystem#getThirdPartSystem <em>Third Part System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Third Part System</em>'.
	 * @see org.eclipse.sirisus.sample.library.ThirdPartSystem#getThirdPartSystem()
	 * @see #getThirdPartSystem()
	 * @generated
	 */
	EReference getThirdPartSystem_ThirdPartSystem();

	/**
	 * Returns the meta object for class '{@link org.eclipse.sirisus.sample.library.LibrarianNew <em>Librarian New</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Librarian New</em>'.
	 * @see org.eclipse.sirisus.sample.library.LibrarianNew
	 * @generated
	 */
	EClass getLibrarianNew();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirisus.sample.library.LibrarianNew#getLibraryNew <em>Library New</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Library New</em>'.
	 * @see org.eclipse.sirisus.sample.library.LibrarianNew#getLibraryNew()
	 * @see #getLibrarianNew()
	 * @generated
	 */
	EReference getLibrarianNew_LibraryNew();

	/**
	 * Returns the meta object for class '{@link org.eclipse.sirisus.sample.library.LibraryManager <em>Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Manager</em>'.
	 * @see org.eclipse.sirisus.sample.library.LibraryManager
	 * @generated
	 */
	EClass getLibraryManager();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.sirisus.sample.library.LibraryManager#getLibraryManager <em>Library Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Library Manager</em>'.
	 * @see org.eclipse.sirisus.sample.library.LibraryManager#getLibraryManager()
	 * @see #getLibraryManager()
	 * @generated
	 */
	EReference getLibraryManager_LibraryManager();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	LibraryFactory getLibraryFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.sirisus.sample.library.impl.LibraryManagementSystemImpl <em>Management System</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.sirisus.sample.library.impl.LibraryManagementSystemImpl
		 * @see org.eclipse.sirisus.sample.library.impl.LibraryPackageImpl#getLibraryManagementSystem()
		 * @generated
		 */
		EClass LIBRARY_MANAGEMENT_SYSTEM = eINSTANCE.getLibraryManagementSystem();

		/**
		 * The meta object literal for the '{@link org.eclipse.sirisus.sample.library.impl.UserImpl <em>User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.sirisus.sample.library.impl.UserImpl
		 * @see org.eclipse.sirisus.sample.library.impl.LibraryPackageImpl#getUser()
		 * @generated
		 */
		EClass USER = eINSTANCE.getUser();

		/**
		 * The meta object literal for the '<em><b>User</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER__USER = eINSTANCE.getUser_User();

		/**
		 * The meta object literal for the '{@link org.eclipse.sirisus.sample.library.impl.SchedulerImpl <em>Scheduler</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.sirisus.sample.library.impl.SchedulerImpl
		 * @see org.eclipse.sirisus.sample.library.impl.LibraryPackageImpl#getScheduler()
		 * @generated
		 */
		EClass SCHEDULER = eINSTANCE.getScheduler();

		/**
		 * The meta object literal for the '<em><b>Schuduler</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCHEDULER__SCHUDULER = eINSTANCE.getScheduler_Schuduler();

		/**
		 * The meta object literal for the '{@link org.eclipse.sirisus.sample.library.impl.ThirdPartSystemImpl <em>Third Part System</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.sirisus.sample.library.impl.ThirdPartSystemImpl
		 * @see org.eclipse.sirisus.sample.library.impl.LibraryPackageImpl#getThirdPartSystem()
		 * @generated
		 */
		EClass THIRD_PART_SYSTEM = eINSTANCE.getThirdPartSystem();

		/**
		 * The meta object literal for the '<em><b>Third Part System</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference THIRD_PART_SYSTEM__THIRD_PART_SYSTEM = eINSTANCE.getThirdPartSystem_ThirdPartSystem();

		/**
		 * The meta object literal for the '{@link org.eclipse.sirisus.sample.library.impl.LibrarianNewImpl <em>Librarian New</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.sirisus.sample.library.impl.LibrarianNewImpl
		 * @see org.eclipse.sirisus.sample.library.impl.LibraryPackageImpl#getLibrarianNew()
		 * @generated
		 */
		EClass LIBRARIAN_NEW = eINSTANCE.getLibrarianNew();

		/**
		 * The meta object literal for the '<em><b>Library New</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARIAN_NEW__LIBRARY_NEW = eINSTANCE.getLibrarianNew_LibraryNew();

		/**
		 * The meta object literal for the '{@link org.eclipse.sirisus.sample.library.impl.LibraryManagerImpl <em>Manager</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.sirisus.sample.library.impl.LibraryManagerImpl
		 * @see org.eclipse.sirisus.sample.library.impl.LibraryPackageImpl#getLibraryManager()
		 * @generated
		 */
		EClass LIBRARY_MANAGER = eINSTANCE.getLibraryManager();

		/**
		 * The meta object literal for the '<em><b>Library Manager</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIBRARY_MANAGER__LIBRARY_MANAGER = eINSTANCE.getLibraryManager_LibraryManager();

	}

} //LibraryPackage
