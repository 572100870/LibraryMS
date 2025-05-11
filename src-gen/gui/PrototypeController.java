package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TabPane;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Tooltip;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import java.time.LocalDate;
import java.util.LinkedList;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import gui.supportclass.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;
import services.*;
import services.impl.*;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Method;

import entities.*;

public class PrototypeController implements Initializable {


	DateTimeFormatter dateformatter;
	 
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		librarymanagementsystemsystem_service = ServiceManager.createLibraryManagementSystemSystem();
		listbookhistory_service = ServiceManager.createListBookHistory();
		searchbook_service = ServiceManager.createSearchBook();
		manageusercrudservice_service = ServiceManager.createManageUserCRUDService();
		managebookcrudservice_service = ServiceManager.createManageBookCRUDService();
		managesubjectcrudservice_service = ServiceManager.createManageSubjectCRUDService();
		managebookcopycrudservice_service = ServiceManager.createManageBookCopyCRUDService();
		managelibrariancrudservice_service = ServiceManager.createManageLibrarianCRUDService();
		thirdpartyservices_service = ServiceManager.createThirdPartyServices();
		searchbookservice_service = ServiceManager.createSearchBookService();
		listbookhistoryservice_service = ServiceManager.createListBookHistoryService();
		registeraccountservice_service = ServiceManager.createRegisterAccountService();
		loginaccountservice_service = ServiceManager.createLogInAccountService();
		resetpasswordservice_service = ServiceManager.createResetPasswordService();
		listrecommendbookservice_service = ServiceManager.createListRecommendBookService();
		deleteaccountservice_service = ServiceManager.createDeleteAccountService();
		recommendbookservice_service = ServiceManager.createRecommendBookService();
				
		this.dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
	   	 //prepare data for contract
	   	 prepareData();
	   	 
	   	 //generate invariant panel
	   	 genereateInvairantPanel();
	   	 
		 //Actor Threeview Binding
		 actorTreeViewBinding();
		 
		 //Generate
		 generatOperationPane();
		 genereateOpInvariantPanel();
		 
		 //prilimariry data
		 try {
			DataFitService.fit();
		 } catch (PreconditionException e) {
			// TODO Auto-generated catch block
		 	e.printStackTrace();
		 }
		 
		 //generate class statistic
		 classStatisicBingding();
		 
		 //generate object statistic
		 generateObjectTable();
		 
		 //genereate association statistic
		 associationStatisicBingding();

		 //set listener 
		 setListeners();
	}
	
	/**
	 * deepCopyforTreeItem (Actor Generation)
	 */
	TreeItem<String> deepCopyTree(TreeItem<String> item) {
		    TreeItem<String> copy = new TreeItem<String>(item.getValue());
		    for (TreeItem<String> child : item.getChildren()) {
		        copy.getChildren().add(deepCopyTree(child));
		    }
		    return copy;
	}
	
	/**
	 * check all invariant and update invariant panel
	 */
	public void invairantPanelUpdate() {
		
		try {
			
			for (Entry<String, Label> inv : entity_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String entityName = invt[0];
				for (Object o : EntityManager.getAllInstancesOf(entityName)) {				
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}				
			}
			
			for (Entry<String, Label> inv : service_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String serviceName = invt[0];
				for (Object o : ServiceManager.getAllInstancesOf(serviceName)) {				
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	/**
	 * check op invariant and update op invariant panel
	 */		
	public void opInvairantPanelUpdate() {
		
		try {
			
			for (Entry<String, Label> inv : op_entity_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String entityName = invt[0];
				for (Object o : EntityManager.getAllInstancesOf(entityName)) {
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}
			}
			
			for (Entry<String, Label> inv : op_service_invariants_label_map.entrySet()) {
				String invname = inv.getKey();
				String[] invt = invname.split("_");
				String serviceName = invt[0];
				for (Object o : ServiceManager.getAllInstancesOf(serviceName)) {
					 Method m = o.getClass().getMethod(invname);
					 if ((boolean)m.invoke(o) == false) {
						 inv.getValue().setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #af0c27 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
						 break;
					 }
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* 
	*	generate op invariant panel 
	*/
	public void genereateOpInvariantPanel() {
		
		opInvariantPanel = new HashMap<String, VBox>();
		op_entity_invariants_label_map = new LinkedHashMap<String, Label>();
		op_service_invariants_label_map = new LinkedHashMap<String, Label>();
		
		VBox v;
		List<String> entities;
		v = new VBox();
		
		//entities invariants
		entities = SearchBookImpl.opINVRelatedEntity.get("registerAccount");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("registerAccount" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SearchBook")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("registerAccount", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SearchBookImpl.opINVRelatedEntity.get("searchBookByBarCode");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("searchBookByBarCode" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SearchBook")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("searchBookByBarCode", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SearchBookImpl.opINVRelatedEntity.get("searchBookByTitle");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("searchBookByTitle" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SearchBook")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("searchBookByTitle", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SearchBookImpl.opINVRelatedEntity.get("searchBookByAuthor");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("searchBookByAuthor" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SearchBook")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("searchBookByAuthor", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SearchBookImpl.opINVRelatedEntity.get("searchBookByISBN");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("searchBookByISBN" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SearchBook")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("searchBookByISBN", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SearchBookImpl.opINVRelatedEntity.get("searchBookBySubject");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("searchBookBySubject" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SearchBook")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("searchBookBySubject", v);
		
		v = new VBox();
		
		//entities invariants
		entities = LibraryManagementSystemSystemImpl.opINVRelatedEntity.get("makeReservation");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("makeReservation" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("LibraryManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("makeReservation", v);
		
		v = new VBox();
		
		//entities invariants
		entities = LibraryManagementSystemSystemImpl.opINVRelatedEntity.get("cancelReservation");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("cancelReservation" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("LibraryManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("cancelReservation", v);
		
		v = new VBox();
		
		//entities invariants
		entities = LibraryManagementSystemSystemImpl.opINVRelatedEntity.get("borrowBook");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("borrowBook" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("LibraryManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("borrowBook", v);
		
		v = new VBox();
		
		//entities invariants
		entities = LibraryManagementSystemSystemImpl.opINVRelatedEntity.get("returnBook");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("returnBook" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("LibraryManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("returnBook", v);
		
		v = new VBox();
		
		//entities invariants
		entities = LibraryManagementSystemSystemImpl.opINVRelatedEntity.get("renewBook");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("renewBook" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("LibraryManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("renewBook", v);
		
		v = new VBox();
		
		//entities invariants
		entities = LibraryManagementSystemSystemImpl.opINVRelatedEntity.get("payOverDueFee");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("payOverDueFee" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("LibraryManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("payOverDueFee", v);
		
		v = new VBox();
		
		//entities invariants
		entities = LibraryManagementSystemSystemImpl.opINVRelatedEntity.get("dueSoonNotification");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("dueSoonNotification" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("LibraryManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("dueSoonNotification", v);
		
		v = new VBox();
		
		//entities invariants
		entities = LibraryManagementSystemSystemImpl.opINVRelatedEntity.get("checkOverDueandComputeOverDueFee");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("checkOverDueandComputeOverDueFee" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("LibraryManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("checkOverDueandComputeOverDueFee", v);
		
		v = new VBox();
		
		//entities invariants
		entities = LibraryManagementSystemSystemImpl.opINVRelatedEntity.get("countDownSuspensionDay");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("countDownSuspensionDay" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("LibraryManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("countDownSuspensionDay", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ListBookHistoryImpl.opINVRelatedEntity.get("listBorrowHistory");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("listBorrowHistory" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ListBookHistory")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("listBorrowHistory", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ListBookHistoryImpl.opINVRelatedEntity.get("listHodingBook");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("listHodingBook" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ListBookHistory")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("listHodingBook", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ListBookHistoryImpl.opINVRelatedEntity.get("listOverDueBook");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("listOverDueBook" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ListBookHistory")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("listOverDueBook", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ListBookHistoryImpl.opINVRelatedEntity.get("listReservationBook");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("listReservationBook" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ListBookHistory")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("listReservationBook", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ListBookHistoryImpl.opINVRelatedEntity.get("listRecommendBook");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("listRecommendBook" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ListBookHistory")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("listRecommendBook", v);
		
		v = new VBox();
		
		//entities invariants
		entities = LibraryManagementSystemSystemImpl.opINVRelatedEntity.get("recommendBook");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("recommendBook" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("LibraryManagementSystemSystem")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("recommendBook", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageUserCRUDServiceImpl.opINVRelatedEntity.get("createStudent");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createStudent" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageUserCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createStudent", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageUserCRUDServiceImpl.opINVRelatedEntity.get("modifyStudent");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifyStudent" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageUserCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifyStudent", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageUserCRUDServiceImpl.opINVRelatedEntity.get("createFaculty");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createFaculty" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageUserCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createFaculty", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageUserCRUDServiceImpl.opINVRelatedEntity.get("modifyFaculty");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifyFaculty" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageUserCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifyFaculty", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ThirdPartyServicesImpl.opINVRelatedEntity.get("sendNotificationEmail");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("sendNotificationEmail" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ThirdPartyServices")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("sendNotificationEmail", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageUserCRUDServiceImpl.opINVRelatedEntity.get("createUser");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createUser" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageUserCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createUser", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageUserCRUDServiceImpl.opINVRelatedEntity.get("queryUser");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("queryUser" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageUserCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("queryUser", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageUserCRUDServiceImpl.opINVRelatedEntity.get("modifyUser");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifyUser" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageUserCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifyUser", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageUserCRUDServiceImpl.opINVRelatedEntity.get("deleteUser");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteUser" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageUserCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteUser", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageBookCRUDServiceImpl.opINVRelatedEntity.get("createBook");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createBook" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageBookCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createBook", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageBookCRUDServiceImpl.opINVRelatedEntity.get("queryBook");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("queryBook" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageBookCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("queryBook", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageBookCRUDServiceImpl.opINVRelatedEntity.get("modifyBook");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifyBook" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageBookCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifyBook", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageBookCRUDServiceImpl.opINVRelatedEntity.get("deleteBook");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteBook" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageBookCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteBook", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageSubjectCRUDServiceImpl.opINVRelatedEntity.get("createSubject");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createSubject" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageSubjectCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createSubject", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageSubjectCRUDServiceImpl.opINVRelatedEntity.get("querySubject");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("querySubject" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageSubjectCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("querySubject", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageSubjectCRUDServiceImpl.opINVRelatedEntity.get("modifySubject");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifySubject" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageSubjectCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifySubject", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageSubjectCRUDServiceImpl.opINVRelatedEntity.get("deleteSubject");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteSubject" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageSubjectCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteSubject", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageBookCopyCRUDServiceImpl.opINVRelatedEntity.get("addBookCopy");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("addBookCopy" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageBookCopyCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("addBookCopy", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageBookCopyCRUDServiceImpl.opINVRelatedEntity.get("queryBookCopy");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("queryBookCopy" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageBookCopyCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("queryBookCopy", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageBookCopyCRUDServiceImpl.opINVRelatedEntity.get("modifyBookCopy");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifyBookCopy" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageBookCopyCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifyBookCopy", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageBookCopyCRUDServiceImpl.opINVRelatedEntity.get("deleteBookCopy");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteBookCopy" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageBookCopyCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteBookCopy", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageLibrarianCRUDServiceImpl.opINVRelatedEntity.get("createLibrarian");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("createLibrarian" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageLibrarianCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("createLibrarian", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageLibrarianCRUDServiceImpl.opINVRelatedEntity.get("queryLibrarian");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("queryLibrarian" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageLibrarianCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("queryLibrarian", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageLibrarianCRUDServiceImpl.opINVRelatedEntity.get("modifyLibrarian");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("modifyLibrarian" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageLibrarianCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("modifyLibrarian", v);
		
		v = new VBox();
		
		//entities invariants
		entities = ManageLibrarianCRUDServiceImpl.opINVRelatedEntity.get("deleteLibrarian");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("deleteLibrarian" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("ManageLibrarianCRUDService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("deleteLibrarian", v);
		
		v = new VBox();
		
		//entities invariants
		entities = LogInAccountServiceImpl.opINVRelatedEntity.get("inputAccount");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("inputAccount" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("LogInAccountService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("inputAccount", v);
		
		v = new VBox();
		
		//entities invariants
		entities = LogInAccountServiceImpl.opINVRelatedEntity.get("inputPassword");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("inputPassword" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("LogInAccountService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("inputPassword", v);
		
		v = new VBox();
		
		//entities invariants
		entities = LogInAccountServiceImpl.opINVRelatedEntity.get("inputAccount1");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("inputAccount1" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("LogInAccountService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("inputAccount1", v);
		
		v = new VBox();
		
		//entities invariants
		entities = LogInAccountServiceImpl.opINVRelatedEntity.get("inputPasswordNew");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("inputPasswordNew" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("LogInAccountService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("inputPasswordNew", v);
		
		v = new VBox();
		
		//entities invariants
		entities = LogInAccountServiceImpl.opINVRelatedEntity.get("inputPasswordNew");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("inputPasswordNew" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("LogInAccountService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("inputPasswordNew", v);
		
		v = new VBox();
		
		//entities invariants
		entities = RegisterAccountServiceImpl.opINVRelatedEntity.get("ensurePassword1");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("ensurePassword1" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("RegisterAccountService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("ensurePassword1", v);
		
		v = new VBox();
		
		//entities invariants
		entities = RegisterAccountServiceImpl.opINVRelatedEntity.get("inputPassword1");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("inputPassword1" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("RegisterAccountService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("inputPassword1", v);
		
		v = new VBox();
		
		//entities invariants
		entities = LogInAccountServiceImpl.opINVRelatedEntity.get("inputCard1");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("inputCard1" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("LogInAccountService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("inputCard1", v);
		
		v = new VBox();
		
		//entities invariants
		entities = LogInAccountServiceImpl.opINVRelatedEntity.get("inputPassword2");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("inputPassword2" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("LogInAccountService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("inputPassword2", v);
		
		v = new VBox();
		
		//entities invariants
		entities = LogInAccountServiceImpl.opINVRelatedEntity.get("inputVerificationcode");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("inputVerificationcode" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("LogInAccountService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("inputVerificationcode", v);
		
		v = new VBox();
		
		//entities invariants
		entities = DeleteAccountServiceImpl.opINVRelatedEntity.get("inputCard12");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("inputCard12" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("DeleteAccountService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("inputCard12", v);
		
		v = new VBox();
		
		//entities invariants
		entities = DeleteAccountServiceImpl.opINVRelatedEntity.get("inputPassword11");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("inputPassword11" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("DeleteAccountService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("inputPassword11", v);
		
		v = new VBox();
		
		//entities invariants
		entities = DeleteAccountServiceImpl.opINVRelatedEntity.get("ensureDeleteAccount");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("ensureDeleteAccount" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("DeleteAccountService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("ensureDeleteAccount", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SearchBookServiceImpl.opINVRelatedEntity.get("inputBookInformation");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("inputBookInformation" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SearchBookService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("inputBookInformation", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SearchBookServiceImpl.opINVRelatedEntity.get("inputCardID");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("inputCardID" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SearchBookService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("inputCardID", v);
		
		v = new VBox();
		
		//entities invariants
		entities = SearchBookServiceImpl.opINVRelatedEntity.get("exitStytem");
		if (entities != null) {
			for (String opRelatedEntities : entities) {
				for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
					
					String invname = inv.getKey();
					String[] invt = invname.split("_");
					String entityName = invt[0];
		
					if (opRelatedEntities.equals(entityName)) {
						Label l = new Label(invname);
						l.setStyle("-fx-max-width: Infinity;" + 
								"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
							    "-fx-padding: 6px;" +
							    "-fx-border-color: black;");
						Tooltip tp = new Tooltip();
						tp.setText(inv.getValue());
						l.setTooltip(tp);
						
						op_entity_invariants_label_map.put(invname, l);
						
						v.getChildren().add(l);
					}
				}
			}
		} else {
			Label l = new Label("exitStytem" + " is no related invariants");
			l.setPadding(new Insets(8, 8, 8, 8));
			v.getChildren().add(l);
		}
		
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			String invname = inv.getKey();
			String[] invt = invname.split("_");
			String serviceName = invt[0];
			
			if (serviceName.equals("SearchBookService")) {
				Label l = new Label(invname);
				l.setStyle("-fx-max-width: Infinity;" + 
						   "-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
						   "-fx-padding: 6px;" +
						   "-fx-border-color: black;");
				Tooltip tp = new Tooltip();
				tp.setText(inv.getValue());
				l.setTooltip(tp);
				
				op_entity_invariants_label_map.put(invname, l);
				
				v.getChildren().add(l);
			}
		}
		opInvariantPanel.put("exitStytem", v);
		
		
	}
	
	
	/*
	*  generate invariant panel
	*/
	public void genereateInvairantPanel() {
		
		service_invariants_label_map = new LinkedHashMap<String, Label>();
		entity_invariants_label_map = new LinkedHashMap<String, Label>();
		
		//entity_invariants_map
		VBox v = new VBox();
		//service invariants
		for (Entry<String, String> inv : service_invariants_map.entrySet()) {
			
			Label l = new Label(inv.getKey());
			l.setStyle("-fx-max-width: Infinity;" + 
					"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
				    "-fx-padding: 6px;" +
				    "-fx-border-color: black;");
			
			Tooltip tp = new Tooltip();
			tp.setText(inv.getValue());
			l.setTooltip(tp);
			
			service_invariants_label_map.put(inv.getKey(), l);
			v.getChildren().add(l);
			
		}
		//entity invariants
		for (Entry<String, String> inv : entity_invariants_map.entrySet()) {
			
			String INVname = inv.getKey();
			Label l = new Label(INVname);
			if (INVname.contains("AssociationInvariants")) {
				l.setStyle("-fx-max-width: Infinity;" + 
					"-fx-background-color: linear-gradient(to right, #099b17 0%, #F0FFFF 100%);" +
				    "-fx-padding: 6px;" +
				    "-fx-border-color: black;");
			} else {
				l.setStyle("-fx-max-width: Infinity;" + 
									"-fx-background-color: linear-gradient(to right, #7FFF00 0%, #F0FFFF 100%);" +
								    "-fx-padding: 6px;" +
								    "-fx-border-color: black;");
			}	
			Tooltip tp = new Tooltip();
			tp.setText(inv.getValue());
			l.setTooltip(tp);
			
			entity_invariants_label_map.put(inv.getKey(), l);
			v.getChildren().add(l);
			
		}
		ScrollPane scrollPane = new ScrollPane(v);
		scrollPane.setFitToWidth(true);
		all_invariant_pane.setMaxHeight(850);
		
		all_invariant_pane.setContent(scrollPane);
	}	
	
	
	
	/* 
	*	mainPane add listener
	*/
	public void setListeners() {
		 mainPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			 
			 	if (newTab.getText().equals("System State")) {
			 		System.out.println("refresh all");
			 		refreshAll();
			 	}
		    
		    });
	}
	
	
	//checking all invariants
	public void checkAllInvariants() {
		
		invairantPanelUpdate();
	
	}	
	
	//refresh all
	public void refreshAll() {
		
		invairantPanelUpdate();
		classStatisticUpdate();
		generateObjectTable();
	}
	
	
	//update association
	public void updateAssociation(String className) {
		
		for (AssociationInfo assoc : allassociationData.get(className)) {
			assoc.computeAssociationNumber();
		}
		
	}
	
	public void updateAssociation(String className, int index) {
		
		for (AssociationInfo assoc : allassociationData.get(className)) {
			assoc.computeAssociationNumber(index);
		}
		
	}	
	
	public void generateObjectTable() {
		
		allObjectTables = new LinkedHashMap<String, TableView>();
		
		TableView<Map<String, String>> tableUser = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableUser_UserID = new TableColumn<Map<String, String>, String>("UserID");
		tableUser_UserID.setMinWidth("UserID".length()*10);
		tableUser_UserID.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("UserID"));
		    }
		});	
		tableUser.getColumns().add(tableUser_UserID);
		TableColumn<Map<String, String>, String> tableUser_Name = new TableColumn<Map<String, String>, String>("Name");
		tableUser_Name.setMinWidth("Name".length()*10);
		tableUser_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
		    }
		});	
		tableUser.getColumns().add(tableUser_Name);
		TableColumn<Map<String, String>, String> tableUser_Sex = new TableColumn<Map<String, String>, String>("Sex");
		tableUser_Sex.setMinWidth("Sex".length()*10);
		tableUser_Sex.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Sex"));
		    }
		});	
		tableUser.getColumns().add(tableUser_Sex);
		TableColumn<Map<String, String>, String> tableUser_Password = new TableColumn<Map<String, String>, String>("Password");
		tableUser_Password.setMinWidth("Password".length()*10);
		tableUser_Password.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Password"));
		    }
		});	
		tableUser.getColumns().add(tableUser_Password);
		TableColumn<Map<String, String>, String> tableUser_Email = new TableColumn<Map<String, String>, String>("Email");
		tableUser_Email.setMinWidth("Email".length()*10);
		tableUser_Email.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Email"));
		    }
		});	
		tableUser.getColumns().add(tableUser_Email);
		TableColumn<Map<String, String>, String> tableUser_Faculty = new TableColumn<Map<String, String>, String>("Faculty");
		tableUser_Faculty.setMinWidth("Faculty".length()*10);
		tableUser_Faculty.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Faculty"));
		    }
		});	
		tableUser.getColumns().add(tableUser_Faculty);
		TableColumn<Map<String, String>, String> tableUser_LoanedNumber = new TableColumn<Map<String, String>, String>("LoanedNumber");
		tableUser_LoanedNumber.setMinWidth("LoanedNumber".length()*10);
		tableUser_LoanedNumber.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("LoanedNumber"));
		    }
		});	
		tableUser.getColumns().add(tableUser_LoanedNumber);
		TableColumn<Map<String, String>, String> tableUser_BorrowStatus = new TableColumn<Map<String, String>, String>("BorrowStatus");
		tableUser_BorrowStatus.setMinWidth("BorrowStatus".length()*10);
		tableUser_BorrowStatus.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("BorrowStatus"));
		    }
		});	
		tableUser.getColumns().add(tableUser_BorrowStatus);
		TableColumn<Map<String, String>, String> tableUser_SuspensionDays = new TableColumn<Map<String, String>, String>("SuspensionDays");
		tableUser_SuspensionDays.setMinWidth("SuspensionDays".length()*10);
		tableUser_SuspensionDays.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("SuspensionDays"));
		    }
		});	
		tableUser.getColumns().add(tableUser_SuspensionDays);
		TableColumn<Map<String, String>, String> tableUser_OverDueFee = new TableColumn<Map<String, String>, String>("OverDueFee");
		tableUser_OverDueFee.setMinWidth("OverDueFee".length()*10);
		tableUser_OverDueFee.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("OverDueFee"));
		    }
		});	
		tableUser.getColumns().add(tableUser_OverDueFee);
		
		//table data
		ObservableList<Map<String, String>> dataUser = FXCollections.observableArrayList();
		List<User> rsUser = EntityManager.getAllInstancesOf("User");
		for (User r : rsUser) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getUserID() != null)
				unit.put("UserID", String.valueOf(r.getUserID()));
			else
				unit.put("UserID", "");
			if (r.getName() != null)
				unit.put("Name", String.valueOf(r.getName()));
			else
				unit.put("Name", "");
			unit.put("Sex", String.valueOf(r.getSex()));
			if (r.getPassword() != null)
				unit.put("Password", String.valueOf(r.getPassword()));
			else
				unit.put("Password", "");
			if (r.getEmail() != null)
				unit.put("Email", String.valueOf(r.getEmail()));
			else
				unit.put("Email", "");
			if (r.getFaculty() != null)
				unit.put("Faculty", String.valueOf(r.getFaculty()));
			else
				unit.put("Faculty", "");
			unit.put("LoanedNumber", String.valueOf(r.getLoanedNumber()));
			unit.put("BorrowStatus", String.valueOf(r.getBorrowStatus()));
			unit.put("SuspensionDays", String.valueOf(r.getSuspensionDays()));
			unit.put("OverDueFee", String.valueOf(r.getOverDueFee()));

			dataUser.add(unit);
		}
		
		tableUser.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableUser.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("User", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableUser.setItems(dataUser);
		allObjectTables.put("User", tableUser);
		
		TableView<Map<String, String>> tableStudent = new TableView<Map<String, String>>();

		//super entity attribute column
		TableColumn<Map<String, String>, String> tableStudent_UserID = new TableColumn<Map<String, String>, String>("UserID");
		tableStudent_UserID.setMinWidth("UserID".length()*10);
		tableStudent_UserID.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("UserID"));
		    }
		});	
		tableStudent.getColumns().add(tableStudent_UserID);
		TableColumn<Map<String, String>, String> tableStudent_Name = new TableColumn<Map<String, String>, String>("Name");
		tableStudent_Name.setMinWidth("Name".length()*10);
		tableStudent_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
		    }
		});	
		tableStudent.getColumns().add(tableStudent_Name);
		TableColumn<Map<String, String>, String> tableStudent_Sex = new TableColumn<Map<String, String>, String>("Sex");
		tableStudent_Sex.setMinWidth("Sex".length()*10);
		tableStudent_Sex.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Sex"));
		    }
		});	
		tableStudent.getColumns().add(tableStudent_Sex);
		TableColumn<Map<String, String>, String> tableStudent_Password = new TableColumn<Map<String, String>, String>("Password");
		tableStudent_Password.setMinWidth("Password".length()*10);
		tableStudent_Password.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Password"));
		    }
		});	
		tableStudent.getColumns().add(tableStudent_Password);
		TableColumn<Map<String, String>, String> tableStudent_Email = new TableColumn<Map<String, String>, String>("Email");
		tableStudent_Email.setMinWidth("Email".length()*10);
		tableStudent_Email.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Email"));
		    }
		});	
		tableStudent.getColumns().add(tableStudent_Email);
		TableColumn<Map<String, String>, String> tableStudent_Faculty = new TableColumn<Map<String, String>, String>("Faculty");
		tableStudent_Faculty.setMinWidth("Faculty".length()*10);
		tableStudent_Faculty.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Faculty"));
		    }
		});	
		tableStudent.getColumns().add(tableStudent_Faculty);
		TableColumn<Map<String, String>, String> tableStudent_LoanedNumber = new TableColumn<Map<String, String>, String>("LoanedNumber");
		tableStudent_LoanedNumber.setMinWidth("LoanedNumber".length()*10);
		tableStudent_LoanedNumber.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("LoanedNumber"));
		    }
		});	
		tableStudent.getColumns().add(tableStudent_LoanedNumber);
		TableColumn<Map<String, String>, String> tableStudent_BorrowStatus = new TableColumn<Map<String, String>, String>("BorrowStatus");
		tableStudent_BorrowStatus.setMinWidth("BorrowStatus".length()*10);
		tableStudent_BorrowStatus.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("BorrowStatus"));
		    }
		});	
		tableStudent.getColumns().add(tableStudent_BorrowStatus);
		TableColumn<Map<String, String>, String> tableStudent_SuspensionDays = new TableColumn<Map<String, String>, String>("SuspensionDays");
		tableStudent_SuspensionDays.setMinWidth("SuspensionDays".length()*10);
		tableStudent_SuspensionDays.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("SuspensionDays"));
		    }
		});	
		tableStudent.getColumns().add(tableStudent_SuspensionDays);
		TableColumn<Map<String, String>, String> tableStudent_OverDueFee = new TableColumn<Map<String, String>, String>("OverDueFee");
		tableStudent_OverDueFee.setMinWidth("OverDueFee".length()*10);
		tableStudent_OverDueFee.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("OverDueFee"));
		    }
		});	
		tableStudent.getColumns().add(tableStudent_OverDueFee);
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableStudent_Major = new TableColumn<Map<String, String>, String>("Major");
		tableStudent_Major.setMinWidth("Major".length()*10);
		tableStudent_Major.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Major"));
		    }
		});	
		tableStudent.getColumns().add(tableStudent_Major);
		TableColumn<Map<String, String>, String> tableStudent_Programme = new TableColumn<Map<String, String>, String>("Programme");
		tableStudent_Programme.setMinWidth("Programme".length()*10);
		tableStudent_Programme.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Programme"));
		    }
		});	
		tableStudent.getColumns().add(tableStudent_Programme);
		TableColumn<Map<String, String>, String> tableStudent_RegistrationStatus = new TableColumn<Map<String, String>, String>("RegistrationStatus");
		tableStudent_RegistrationStatus.setMinWidth("RegistrationStatus".length()*10);
		tableStudent_RegistrationStatus.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("RegistrationStatus"));
		    }
		});	
		tableStudent.getColumns().add(tableStudent_RegistrationStatus);
		
		//table data
		ObservableList<Map<String, String>> dataStudent = FXCollections.observableArrayList();
		List<Student> rsStudent = EntityManager.getAllInstancesOf("Student");
		for (Student r : rsStudent) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			if (r.getUserID() != null)
				unit.put("UserID", String.valueOf(r.getUserID()));
			else
				unit.put("UserID", "");
			if (r.getName() != null)
				unit.put("Name", String.valueOf(r.getName()));
			else
				unit.put("Name", "");
			unit.put("Sex", String.valueOf(r.getSex()));
			if (r.getPassword() != null)
				unit.put("Password", String.valueOf(r.getPassword()));
			else
				unit.put("Password", "");
			if (r.getEmail() != null)
				unit.put("Email", String.valueOf(r.getEmail()));
			else
				unit.put("Email", "");
			if (r.getFaculty() != null)
				unit.put("Faculty", String.valueOf(r.getFaculty()));
			else
				unit.put("Faculty", "");
			unit.put("LoanedNumber", String.valueOf(r.getLoanedNumber()));
			unit.put("BorrowStatus", String.valueOf(r.getBorrowStatus()));
			unit.put("SuspensionDays", String.valueOf(r.getSuspensionDays()));
			unit.put("OverDueFee", String.valueOf(r.getOverDueFee()));
			
			if (r.getMajor() != null)
				unit.put("Major", String.valueOf(r.getMajor()));
			else
				unit.put("Major", "");
			unit.put("Programme", String.valueOf(r.getProgramme()));
			unit.put("RegistrationStatus", String.valueOf(r.getRegistrationStatus()));

			dataStudent.add(unit);
		}
		
		tableStudent.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableStudent.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Student", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableStudent.setItems(dataStudent);
		allObjectTables.put("Student", tableStudent);
		
		TableView<Map<String, String>> tableFaculty = new TableView<Map<String, String>>();

		//super entity attribute column
		TableColumn<Map<String, String>, String> tableFaculty_UserID = new TableColumn<Map<String, String>, String>("UserID");
		tableFaculty_UserID.setMinWidth("UserID".length()*10);
		tableFaculty_UserID.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("UserID"));
		    }
		});	
		tableFaculty.getColumns().add(tableFaculty_UserID);
		TableColumn<Map<String, String>, String> tableFaculty_Name = new TableColumn<Map<String, String>, String>("Name");
		tableFaculty_Name.setMinWidth("Name".length()*10);
		tableFaculty_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
		    }
		});	
		tableFaculty.getColumns().add(tableFaculty_Name);
		TableColumn<Map<String, String>, String> tableFaculty_Sex = new TableColumn<Map<String, String>, String>("Sex");
		tableFaculty_Sex.setMinWidth("Sex".length()*10);
		tableFaculty_Sex.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Sex"));
		    }
		});	
		tableFaculty.getColumns().add(tableFaculty_Sex);
		TableColumn<Map<String, String>, String> tableFaculty_Password = new TableColumn<Map<String, String>, String>("Password");
		tableFaculty_Password.setMinWidth("Password".length()*10);
		tableFaculty_Password.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Password"));
		    }
		});	
		tableFaculty.getColumns().add(tableFaculty_Password);
		TableColumn<Map<String, String>, String> tableFaculty_Email = new TableColumn<Map<String, String>, String>("Email");
		tableFaculty_Email.setMinWidth("Email".length()*10);
		tableFaculty_Email.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Email"));
		    }
		});	
		tableFaculty.getColumns().add(tableFaculty_Email);
		TableColumn<Map<String, String>, String> tableFaculty_Faculty = new TableColumn<Map<String, String>, String>("Faculty");
		tableFaculty_Faculty.setMinWidth("Faculty".length()*10);
		tableFaculty_Faculty.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Faculty"));
		    }
		});	
		tableFaculty.getColumns().add(tableFaculty_Faculty);
		TableColumn<Map<String, String>, String> tableFaculty_LoanedNumber = new TableColumn<Map<String, String>, String>("LoanedNumber");
		tableFaculty_LoanedNumber.setMinWidth("LoanedNumber".length()*10);
		tableFaculty_LoanedNumber.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("LoanedNumber"));
		    }
		});	
		tableFaculty.getColumns().add(tableFaculty_LoanedNumber);
		TableColumn<Map<String, String>, String> tableFaculty_BorrowStatus = new TableColumn<Map<String, String>, String>("BorrowStatus");
		tableFaculty_BorrowStatus.setMinWidth("BorrowStatus".length()*10);
		tableFaculty_BorrowStatus.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("BorrowStatus"));
		    }
		});	
		tableFaculty.getColumns().add(tableFaculty_BorrowStatus);
		TableColumn<Map<String, String>, String> tableFaculty_SuspensionDays = new TableColumn<Map<String, String>, String>("SuspensionDays");
		tableFaculty_SuspensionDays.setMinWidth("SuspensionDays".length()*10);
		tableFaculty_SuspensionDays.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("SuspensionDays"));
		    }
		});	
		tableFaculty.getColumns().add(tableFaculty_SuspensionDays);
		TableColumn<Map<String, String>, String> tableFaculty_OverDueFee = new TableColumn<Map<String, String>, String>("OverDueFee");
		tableFaculty_OverDueFee.setMinWidth("OverDueFee".length()*10);
		tableFaculty_OverDueFee.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("OverDueFee"));
		    }
		});	
		tableFaculty.getColumns().add(tableFaculty_OverDueFee);
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableFaculty_Position = new TableColumn<Map<String, String>, String>("Position");
		tableFaculty_Position.setMinWidth("Position".length()*10);
		tableFaculty_Position.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Position"));
		    }
		});	
		tableFaculty.getColumns().add(tableFaculty_Position);
		TableColumn<Map<String, String>, String> tableFaculty_Status = new TableColumn<Map<String, String>, String>("Status");
		tableFaculty_Status.setMinWidth("Status".length()*10);
		tableFaculty_Status.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Status"));
		    }
		});	
		tableFaculty.getColumns().add(tableFaculty_Status);
		
		//table data
		ObservableList<Map<String, String>> dataFaculty = FXCollections.observableArrayList();
		List<Faculty> rsFaculty = EntityManager.getAllInstancesOf("Faculty");
		for (Faculty r : rsFaculty) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			if (r.getUserID() != null)
				unit.put("UserID", String.valueOf(r.getUserID()));
			else
				unit.put("UserID", "");
			if (r.getName() != null)
				unit.put("Name", String.valueOf(r.getName()));
			else
				unit.put("Name", "");
			unit.put("Sex", String.valueOf(r.getSex()));
			if (r.getPassword() != null)
				unit.put("Password", String.valueOf(r.getPassword()));
			else
				unit.put("Password", "");
			if (r.getEmail() != null)
				unit.put("Email", String.valueOf(r.getEmail()));
			else
				unit.put("Email", "");
			if (r.getFaculty() != null)
				unit.put("Faculty", String.valueOf(r.getFaculty()));
			else
				unit.put("Faculty", "");
			unit.put("LoanedNumber", String.valueOf(r.getLoanedNumber()));
			unit.put("BorrowStatus", String.valueOf(r.getBorrowStatus()));
			unit.put("SuspensionDays", String.valueOf(r.getSuspensionDays()));
			unit.put("OverDueFee", String.valueOf(r.getOverDueFee()));
			
			unit.put("Position", String.valueOf(r.getPosition()));
			unit.put("Status", String.valueOf(r.getStatus()));

			dataFaculty.add(unit);
		}
		
		tableFaculty.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableFaculty.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Faculty", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableFaculty.setItems(dataFaculty);
		allObjectTables.put("Faculty", tableFaculty);
		
		TableView<Map<String, String>> tableBook = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableBook_CallNo = new TableColumn<Map<String, String>, String>("CallNo");
		tableBook_CallNo.setMinWidth("CallNo".length()*10);
		tableBook_CallNo.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("CallNo"));
		    }
		});	
		tableBook.getColumns().add(tableBook_CallNo);
		TableColumn<Map<String, String>, String> tableBook_Title = new TableColumn<Map<String, String>, String>("Title");
		tableBook_Title.setMinWidth("Title".length()*10);
		tableBook_Title.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Title"));
		    }
		});	
		tableBook.getColumns().add(tableBook_Title);
		TableColumn<Map<String, String>, String> tableBook_Edition = new TableColumn<Map<String, String>, String>("Edition");
		tableBook_Edition.setMinWidth("Edition".length()*10);
		tableBook_Edition.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Edition"));
		    }
		});	
		tableBook.getColumns().add(tableBook_Edition);
		TableColumn<Map<String, String>, String> tableBook_Author = new TableColumn<Map<String, String>, String>("Author");
		tableBook_Author.setMinWidth("Author".length()*10);
		tableBook_Author.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Author"));
		    }
		});	
		tableBook.getColumns().add(tableBook_Author);
		TableColumn<Map<String, String>, String> tableBook_Publisher = new TableColumn<Map<String, String>, String>("Publisher");
		tableBook_Publisher.setMinWidth("Publisher".length()*10);
		tableBook_Publisher.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Publisher"));
		    }
		});	
		tableBook.getColumns().add(tableBook_Publisher);
		TableColumn<Map<String, String>, String> tableBook_Description = new TableColumn<Map<String, String>, String>("Description");
		tableBook_Description.setMinWidth("Description".length()*10);
		tableBook_Description.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Description"));
		    }
		});	
		tableBook.getColumns().add(tableBook_Description);
		TableColumn<Map<String, String>, String> tableBook_ISBn = new TableColumn<Map<String, String>, String>("ISBn");
		tableBook_ISBn.setMinWidth("ISBn".length()*10);
		tableBook_ISBn.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("ISBn"));
		    }
		});	
		tableBook.getColumns().add(tableBook_ISBn);
		TableColumn<Map<String, String>, String> tableBook_CopyNum = new TableColumn<Map<String, String>, String>("CopyNum");
		tableBook_CopyNum.setMinWidth("CopyNum".length()*10);
		tableBook_CopyNum.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("CopyNum"));
		    }
		});	
		tableBook.getColumns().add(tableBook_CopyNum);
		
		//table data
		ObservableList<Map<String, String>> dataBook = FXCollections.observableArrayList();
		List<Book> rsBook = EntityManager.getAllInstancesOf("Book");
		for (Book r : rsBook) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getCallNo() != null)
				unit.put("CallNo", String.valueOf(r.getCallNo()));
			else
				unit.put("CallNo", "");
			if (r.getTitle() != null)
				unit.put("Title", String.valueOf(r.getTitle()));
			else
				unit.put("Title", "");
			if (r.getEdition() != null)
				unit.put("Edition", String.valueOf(r.getEdition()));
			else
				unit.put("Edition", "");
			if (r.getAuthor() != null)
				unit.put("Author", String.valueOf(r.getAuthor()));
			else
				unit.put("Author", "");
			if (r.getPublisher() != null)
				unit.put("Publisher", String.valueOf(r.getPublisher()));
			else
				unit.put("Publisher", "");
			if (r.getDescription() != null)
				unit.put("Description", String.valueOf(r.getDescription()));
			else
				unit.put("Description", "");
			if (r.getISBn() != null)
				unit.put("ISBn", String.valueOf(r.getISBn()));
			else
				unit.put("ISBn", "");
			unit.put("CopyNum", String.valueOf(r.getCopyNum()));

			dataBook.add(unit);
		}
		
		tableBook.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableBook.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Book", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableBook.setItems(dataBook);
		allObjectTables.put("Book", tableBook);
		
		TableView<Map<String, String>> tableSubject = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableSubject_Name = new TableColumn<Map<String, String>, String>("Name");
		tableSubject_Name.setMinWidth("Name".length()*10);
		tableSubject_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
		    }
		});	
		tableSubject.getColumns().add(tableSubject_Name);
		
		//table data
		ObservableList<Map<String, String>> dataSubject = FXCollections.observableArrayList();
		List<Subject> rsSubject = EntityManager.getAllInstancesOf("Subject");
		for (Subject r : rsSubject) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getName() != null)
				unit.put("Name", String.valueOf(r.getName()));
			else
				unit.put("Name", "");

			dataSubject.add(unit);
		}
		
		tableSubject.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableSubject.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Subject", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableSubject.setItems(dataSubject);
		allObjectTables.put("Subject", tableSubject);
		
		TableView<Map<String, String>> tableBookCopy = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableBookCopy_Barcode = new TableColumn<Map<String, String>, String>("Barcode");
		tableBookCopy_Barcode.setMinWidth("Barcode".length()*10);
		tableBookCopy_Barcode.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Barcode"));
		    }
		});	
		tableBookCopy.getColumns().add(tableBookCopy_Barcode);
		TableColumn<Map<String, String>, String> tableBookCopy_Status = new TableColumn<Map<String, String>, String>("Status");
		tableBookCopy_Status.setMinWidth("Status".length()*10);
		tableBookCopy_Status.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Status"));
		    }
		});	
		tableBookCopy.getColumns().add(tableBookCopy_Status);
		TableColumn<Map<String, String>, String> tableBookCopy_Location = new TableColumn<Map<String, String>, String>("Location");
		tableBookCopy_Location.setMinWidth("Location".length()*10);
		tableBookCopy_Location.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Location"));
		    }
		});	
		tableBookCopy.getColumns().add(tableBookCopy_Location);
		TableColumn<Map<String, String>, String> tableBookCopy_IsReserved = new TableColumn<Map<String, String>, String>("IsReserved");
		tableBookCopy_IsReserved.setMinWidth("IsReserved".length()*10);
		tableBookCopy_IsReserved.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("IsReserved"));
		    }
		});	
		tableBookCopy.getColumns().add(tableBookCopy_IsReserved);
		
		//table data
		ObservableList<Map<String, String>> dataBookCopy = FXCollections.observableArrayList();
		List<BookCopy> rsBookCopy = EntityManager.getAllInstancesOf("BookCopy");
		for (BookCopy r : rsBookCopy) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getBarcode() != null)
				unit.put("Barcode", String.valueOf(r.getBarcode()));
			else
				unit.put("Barcode", "");
			unit.put("Status", String.valueOf(r.getStatus()));
			if (r.getLocation() != null)
				unit.put("Location", String.valueOf(r.getLocation()));
			else
				unit.put("Location", "");
			unit.put("IsReserved", String.valueOf(r.getIsReserved()));

			dataBookCopy.add(unit);
		}
		
		tableBookCopy.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableBookCopy.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("BookCopy", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableBookCopy.setItems(dataBookCopy);
		allObjectTables.put("BookCopy", tableBookCopy);
		
		TableView<Map<String, String>> tableLoan = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableLoan_LoanDate = new TableColumn<Map<String, String>, String>("LoanDate");
		tableLoan_LoanDate.setMinWidth("LoanDate".length()*10);
		tableLoan_LoanDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("LoanDate"));
		    }
		});	
		tableLoan.getColumns().add(tableLoan_LoanDate);
		TableColumn<Map<String, String>, String> tableLoan_RenewDate = new TableColumn<Map<String, String>, String>("RenewDate");
		tableLoan_RenewDate.setMinWidth("RenewDate".length()*10);
		tableLoan_RenewDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("RenewDate"));
		    }
		});	
		tableLoan.getColumns().add(tableLoan_RenewDate);
		TableColumn<Map<String, String>, String> tableLoan_DueDate = new TableColumn<Map<String, String>, String>("DueDate");
		tableLoan_DueDate.setMinWidth("DueDate".length()*10);
		tableLoan_DueDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("DueDate"));
		    }
		});	
		tableLoan.getColumns().add(tableLoan_DueDate);
		TableColumn<Map<String, String>, String> tableLoan_ReturnDate = new TableColumn<Map<String, String>, String>("ReturnDate");
		tableLoan_ReturnDate.setMinWidth("ReturnDate".length()*10);
		tableLoan_ReturnDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("ReturnDate"));
		    }
		});	
		tableLoan.getColumns().add(tableLoan_ReturnDate);
		TableColumn<Map<String, String>, String> tableLoan_RenewedTimes = new TableColumn<Map<String, String>, String>("RenewedTimes");
		tableLoan_RenewedTimes.setMinWidth("RenewedTimes".length()*10);
		tableLoan_RenewedTimes.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("RenewedTimes"));
		    }
		});	
		tableLoan.getColumns().add(tableLoan_RenewedTimes);
		TableColumn<Map<String, String>, String> tableLoan_IsReturned = new TableColumn<Map<String, String>, String>("IsReturned");
		tableLoan_IsReturned.setMinWidth("IsReturned".length()*10);
		tableLoan_IsReturned.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("IsReturned"));
		    }
		});	
		tableLoan.getColumns().add(tableLoan_IsReturned);
		TableColumn<Map<String, String>, String> tableLoan_OverDueFee = new TableColumn<Map<String, String>, String>("OverDueFee");
		tableLoan_OverDueFee.setMinWidth("OverDueFee".length()*10);
		tableLoan_OverDueFee.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("OverDueFee"));
		    }
		});	
		tableLoan.getColumns().add(tableLoan_OverDueFee);
		TableColumn<Map<String, String>, String> tableLoan_OverDue3Days = new TableColumn<Map<String, String>, String>("OverDue3Days");
		tableLoan_OverDue3Days.setMinWidth("OverDue3Days".length()*10);
		tableLoan_OverDue3Days.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("OverDue3Days"));
		    }
		});	
		tableLoan.getColumns().add(tableLoan_OverDue3Days);
		TableColumn<Map<String, String>, String> tableLoan_OverDue10Days = new TableColumn<Map<String, String>, String>("OverDue10Days");
		tableLoan_OverDue10Days.setMinWidth("OverDue10Days".length()*10);
		tableLoan_OverDue10Days.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("OverDue10Days"));
		    }
		});	
		tableLoan.getColumns().add(tableLoan_OverDue10Days);
		TableColumn<Map<String, String>, String> tableLoan_OverDue17Days = new TableColumn<Map<String, String>, String>("OverDue17Days");
		tableLoan_OverDue17Days.setMinWidth("OverDue17Days".length()*10);
		tableLoan_OverDue17Days.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("OverDue17Days"));
		    }
		});	
		tableLoan.getColumns().add(tableLoan_OverDue17Days);
		TableColumn<Map<String, String>, String> tableLoan_OverDue31Days = new TableColumn<Map<String, String>, String>("OverDue31Days");
		tableLoan_OverDue31Days.setMinWidth("OverDue31Days".length()*10);
		tableLoan_OverDue31Days.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("OverDue31Days"));
		    }
		});	
		tableLoan.getColumns().add(tableLoan_OverDue31Days);
		
		//table data
		ObservableList<Map<String, String>> dataLoan = FXCollections.observableArrayList();
		List<Loan> rsLoan = EntityManager.getAllInstancesOf("Loan");
		for (Loan r : rsLoan) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getLoanDate() != null)
				unit.put("LoanDate", r.getLoanDate().format(dateformatter));
			else
				unit.put("LoanDate", "");
			if (r.getRenewDate() != null)
				unit.put("RenewDate", r.getRenewDate().format(dateformatter));
			else
				unit.put("RenewDate", "");
			if (r.getDueDate() != null)
				unit.put("DueDate", r.getDueDate().format(dateformatter));
			else
				unit.put("DueDate", "");
			if (r.getReturnDate() != null)
				unit.put("ReturnDate", r.getReturnDate().format(dateformatter));
			else
				unit.put("ReturnDate", "");
			unit.put("RenewedTimes", String.valueOf(r.getRenewedTimes()));
			unit.put("IsReturned", String.valueOf(r.getIsReturned()));
			unit.put("OverDueFee", String.valueOf(r.getOverDueFee()));
			unit.put("OverDue3Days", String.valueOf(r.getOverDue3Days()));
			unit.put("OverDue10Days", String.valueOf(r.getOverDue10Days()));
			unit.put("OverDue17Days", String.valueOf(r.getOverDue17Days()));
			unit.put("OverDue31Days", String.valueOf(r.getOverDue31Days()));

			dataLoan.add(unit);
		}
		
		tableLoan.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableLoan.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Loan", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableLoan.setItems(dataLoan);
		allObjectTables.put("Loan", tableLoan);
		
		TableView<Map<String, String>> tableReserve = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableReserve_ReserveDate = new TableColumn<Map<String, String>, String>("ReserveDate");
		tableReserve_ReserveDate.setMinWidth("ReserveDate".length()*10);
		tableReserve_ReserveDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("ReserveDate"));
		    }
		});	
		tableReserve.getColumns().add(tableReserve_ReserveDate);
		TableColumn<Map<String, String>, String> tableReserve_IsReserveClosed = new TableColumn<Map<String, String>, String>("IsReserveClosed");
		tableReserve_IsReserveClosed.setMinWidth("IsReserveClosed".length()*10);
		tableReserve_IsReserveClosed.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("IsReserveClosed"));
		    }
		});	
		tableReserve.getColumns().add(tableReserve_IsReserveClosed);
		
		//table data
		ObservableList<Map<String, String>> dataReserve = FXCollections.observableArrayList();
		List<Reserve> rsReserve = EntityManager.getAllInstancesOf("Reserve");
		for (Reserve r : rsReserve) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getReserveDate() != null)
				unit.put("ReserveDate", r.getReserveDate().format(dateformatter));
			else
				unit.put("ReserveDate", "");
			unit.put("IsReserveClosed", String.valueOf(r.getIsReserveClosed()));

			dataReserve.add(unit);
		}
		
		tableReserve.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableReserve.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Reserve", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableReserve.setItems(dataReserve);
		allObjectTables.put("Reserve", tableReserve);
		
		TableView<Map<String, String>> tableRecommendBook = new TableView<Map<String, String>>();

		//super entity attribute column
		TableColumn<Map<String, String>, String> tableRecommendBook_CallNo = new TableColumn<Map<String, String>, String>("CallNo");
		tableRecommendBook_CallNo.setMinWidth("CallNo".length()*10);
		tableRecommendBook_CallNo.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("CallNo"));
		    }
		});	
		tableRecommendBook.getColumns().add(tableRecommendBook_CallNo);
		TableColumn<Map<String, String>, String> tableRecommendBook_Title = new TableColumn<Map<String, String>, String>("Title");
		tableRecommendBook_Title.setMinWidth("Title".length()*10);
		tableRecommendBook_Title.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Title"));
		    }
		});	
		tableRecommendBook.getColumns().add(tableRecommendBook_Title);
		TableColumn<Map<String, String>, String> tableRecommendBook_Edition = new TableColumn<Map<String, String>, String>("Edition");
		tableRecommendBook_Edition.setMinWidth("Edition".length()*10);
		tableRecommendBook_Edition.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Edition"));
		    }
		});	
		tableRecommendBook.getColumns().add(tableRecommendBook_Edition);
		TableColumn<Map<String, String>, String> tableRecommendBook_Author = new TableColumn<Map<String, String>, String>("Author");
		tableRecommendBook_Author.setMinWidth("Author".length()*10);
		tableRecommendBook_Author.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Author"));
		    }
		});	
		tableRecommendBook.getColumns().add(tableRecommendBook_Author);
		TableColumn<Map<String, String>, String> tableRecommendBook_Publisher = new TableColumn<Map<String, String>, String>("Publisher");
		tableRecommendBook_Publisher.setMinWidth("Publisher".length()*10);
		tableRecommendBook_Publisher.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Publisher"));
		    }
		});	
		tableRecommendBook.getColumns().add(tableRecommendBook_Publisher);
		TableColumn<Map<String, String>, String> tableRecommendBook_Description = new TableColumn<Map<String, String>, String>("Description");
		tableRecommendBook_Description.setMinWidth("Description".length()*10);
		tableRecommendBook_Description.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Description"));
		    }
		});	
		tableRecommendBook.getColumns().add(tableRecommendBook_Description);
		TableColumn<Map<String, String>, String> tableRecommendBook_ISBn = new TableColumn<Map<String, String>, String>("ISBn");
		tableRecommendBook_ISBn.setMinWidth("ISBn".length()*10);
		tableRecommendBook_ISBn.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("ISBn"));
		    }
		});	
		tableRecommendBook.getColumns().add(tableRecommendBook_ISBn);
		TableColumn<Map<String, String>, String> tableRecommendBook_CopyNum = new TableColumn<Map<String, String>, String>("CopyNum");
		tableRecommendBook_CopyNum.setMinWidth("CopyNum".length()*10);
		tableRecommendBook_CopyNum.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("CopyNum"));
		    }
		});	
		tableRecommendBook.getColumns().add(tableRecommendBook_CopyNum);
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableRecommendBook_RecommendDate = new TableColumn<Map<String, String>, String>("RecommendDate");
		tableRecommendBook_RecommendDate.setMinWidth("RecommendDate".length()*10);
		tableRecommendBook_RecommendDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("RecommendDate"));
		    }
		});	
		tableRecommendBook.getColumns().add(tableRecommendBook_RecommendDate);
		
		//table data
		ObservableList<Map<String, String>> dataRecommendBook = FXCollections.observableArrayList();
		List<RecommendBook> rsRecommendBook = EntityManager.getAllInstancesOf("RecommendBook");
		for (RecommendBook r : rsRecommendBook) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			if (r.getCallNo() != null)
				unit.put("CallNo", String.valueOf(r.getCallNo()));
			else
				unit.put("CallNo", "");
			if (r.getTitle() != null)
				unit.put("Title", String.valueOf(r.getTitle()));
			else
				unit.put("Title", "");
			if (r.getEdition() != null)
				unit.put("Edition", String.valueOf(r.getEdition()));
			else
				unit.put("Edition", "");
			if (r.getAuthor() != null)
				unit.put("Author", String.valueOf(r.getAuthor()));
			else
				unit.put("Author", "");
			if (r.getPublisher() != null)
				unit.put("Publisher", String.valueOf(r.getPublisher()));
			else
				unit.put("Publisher", "");
			if (r.getDescription() != null)
				unit.put("Description", String.valueOf(r.getDescription()));
			else
				unit.put("Description", "");
			if (r.getISBn() != null)
				unit.put("ISBn", String.valueOf(r.getISBn()));
			else
				unit.put("ISBn", "");
			unit.put("CopyNum", String.valueOf(r.getCopyNum()));
			
			if (r.getRecommendDate() != null)
				unit.put("RecommendDate", r.getRecommendDate().format(dateformatter));
			else
				unit.put("RecommendDate", "");

			dataRecommendBook.add(unit);
		}
		
		tableRecommendBook.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableRecommendBook.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("RecommendBook", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableRecommendBook.setItems(dataRecommendBook);
		allObjectTables.put("RecommendBook", tableRecommendBook);
		
		TableView<Map<String, String>> tableAdministrator = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableAdministrator_AdminID = new TableColumn<Map<String, String>, String>("AdminID");
		tableAdministrator_AdminID.setMinWidth("AdminID".length()*10);
		tableAdministrator_AdminID.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("AdminID"));
		    }
		});	
		tableAdministrator.getColumns().add(tableAdministrator_AdminID);
		TableColumn<Map<String, String>, String> tableAdministrator_UserName = new TableColumn<Map<String, String>, String>("UserName");
		tableAdministrator_UserName.setMinWidth("UserName".length()*10);
		tableAdministrator_UserName.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("UserName"));
		    }
		});	
		tableAdministrator.getColumns().add(tableAdministrator_UserName);
		TableColumn<Map<String, String>, String> tableAdministrator_Password = new TableColumn<Map<String, String>, String>("Password");
		tableAdministrator_Password.setMinWidth("Password".length()*10);
		tableAdministrator_Password.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Password"));
		    }
		});	
		tableAdministrator.getColumns().add(tableAdministrator_Password);
		
		//table data
		ObservableList<Map<String, String>> dataAdministrator = FXCollections.observableArrayList();
		List<Administrator> rsAdministrator = EntityManager.getAllInstancesOf("Administrator");
		for (Administrator r : rsAdministrator) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getAdminID() != null)
				unit.put("AdminID", String.valueOf(r.getAdminID()));
			else
				unit.put("AdminID", "");
			if (r.getUserName() != null)
				unit.put("UserName", String.valueOf(r.getUserName()));
			else
				unit.put("UserName", "");
			if (r.getPassword() != null)
				unit.put("Password", String.valueOf(r.getPassword()));
			else
				unit.put("Password", "");

			dataAdministrator.add(unit);
		}
		
		tableAdministrator.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableAdministrator.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Administrator", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableAdministrator.setItems(dataAdministrator);
		allObjectTables.put("Administrator", tableAdministrator);
		
		TableView<Map<String, String>> tableLibrarian = new TableView<Map<String, String>>();

		//super entity attribute column
						
		//attributes table column
		TableColumn<Map<String, String>, String> tableLibrarian_LibrarianID = new TableColumn<Map<String, String>, String>("LibrarianID");
		tableLibrarian_LibrarianID.setMinWidth("LibrarianID".length()*10);
		tableLibrarian_LibrarianID.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("LibrarianID"));
		    }
		});	
		tableLibrarian.getColumns().add(tableLibrarian_LibrarianID);
		TableColumn<Map<String, String>, String> tableLibrarian_Name = new TableColumn<Map<String, String>, String>("Name");
		tableLibrarian_Name.setMinWidth("Name".length()*10);
		tableLibrarian_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
		    }
		});	
		tableLibrarian.getColumns().add(tableLibrarian_Name);
		TableColumn<Map<String, String>, String> tableLibrarian_Password = new TableColumn<Map<String, String>, String>("Password");
		tableLibrarian_Password.setMinWidth("Password".length()*10);
		tableLibrarian_Password.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
			@Override
		    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
		        return new ReadOnlyStringWrapper(data.getValue().get("Password"));
		    }
		});	
		tableLibrarian.getColumns().add(tableLibrarian_Password);
		
		//table data
		ObservableList<Map<String, String>> dataLibrarian = FXCollections.observableArrayList();
		List<Librarian> rsLibrarian = EntityManager.getAllInstancesOf("Librarian");
		for (Librarian r : rsLibrarian) {
			//table entry
			Map<String, String> unit = new HashMap<String, String>();
			
			if (r.getLibrarianID() != null)
				unit.put("LibrarianID", String.valueOf(r.getLibrarianID()));
			else
				unit.put("LibrarianID", "");
			if (r.getName() != null)
				unit.put("Name", String.valueOf(r.getName()));
			else
				unit.put("Name", "");
			if (r.getPassword() != null)
				unit.put("Password", String.valueOf(r.getPassword()));
			else
				unit.put("Password", "");

			dataLibrarian.add(unit);
		}
		
		tableLibrarian.getSelectionModel().selectedIndexProperty().addListener(
							 (observable, oldValue, newValue) ->  { 
							 										 //get selected index
							 										 objectindex = tableLibrarian.getSelectionModel().getSelectedIndex();
							 			 				 			 System.out.println("select: " + objectindex);

							 			 				 			 //update association object information
							 			 				 			 if (objectindex != -1)
										 			 					 updateAssociation("Librarian", objectindex);
							 			 				 			 
							 			 				 		  });
		
		tableLibrarian.setItems(dataLibrarian);
		allObjectTables.put("Librarian", tableLibrarian);
		

		
	}
	
	/* 
	* update all object tables with sub dataset
	*/ 
	public void updateUserTable(List<User> rsUser) {
			ObservableList<Map<String, String>> dataUser = FXCollections.observableArrayList();
			for (User r : rsUser) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getUserID() != null)
					unit.put("UserID", String.valueOf(r.getUserID()));
				else
					unit.put("UserID", "");
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				unit.put("Sex", String.valueOf(r.getSex()));
				if (r.getPassword() != null)
					unit.put("Password", String.valueOf(r.getPassword()));
				else
					unit.put("Password", "");
				if (r.getEmail() != null)
					unit.put("Email", String.valueOf(r.getEmail()));
				else
					unit.put("Email", "");
				if (r.getFaculty() != null)
					unit.put("Faculty", String.valueOf(r.getFaculty()));
				else
					unit.put("Faculty", "");
				unit.put("LoanedNumber", String.valueOf(r.getLoanedNumber()));
				unit.put("BorrowStatus", String.valueOf(r.getBorrowStatus()));
				unit.put("SuspensionDays", String.valueOf(r.getSuspensionDays()));
				unit.put("OverDueFee", String.valueOf(r.getOverDueFee()));
				dataUser.add(unit);
			}
			
			allObjectTables.get("User").setItems(dataUser);
	}
	public void updateStudentTable(List<Student> rsStudent) {
			ObservableList<Map<String, String>> dataStudent = FXCollections.observableArrayList();
			for (Student r : rsStudent) {
				Map<String, String> unit = new HashMap<String, String>();
				
				if (r.getUserID() != null)
					unit.put("UserID", String.valueOf(r.getUserID()));
				else
					unit.put("UserID", "");
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				unit.put("Sex", String.valueOf(r.getSex()));
				if (r.getPassword() != null)
					unit.put("Password", String.valueOf(r.getPassword()));
				else
					unit.put("Password", "");
				if (r.getEmail() != null)
					unit.put("Email", String.valueOf(r.getEmail()));
				else
					unit.put("Email", "");
				if (r.getFaculty() != null)
					unit.put("Faculty", String.valueOf(r.getFaculty()));
				else
					unit.put("Faculty", "");
				unit.put("LoanedNumber", String.valueOf(r.getLoanedNumber()));
				unit.put("BorrowStatus", String.valueOf(r.getBorrowStatus()));
				unit.put("SuspensionDays", String.valueOf(r.getSuspensionDays()));
				unit.put("OverDueFee", String.valueOf(r.getOverDueFee()));
				
				if (r.getMajor() != null)
					unit.put("Major", String.valueOf(r.getMajor()));
				else
					unit.put("Major", "");
				unit.put("Programme", String.valueOf(r.getProgramme()));
				unit.put("RegistrationStatus", String.valueOf(r.getRegistrationStatus()));
				dataStudent.add(unit);
			}
			
			allObjectTables.get("Student").setItems(dataStudent);
	}
	public void updateFacultyTable(List<Faculty> rsFaculty) {
			ObservableList<Map<String, String>> dataFaculty = FXCollections.observableArrayList();
			for (Faculty r : rsFaculty) {
				Map<String, String> unit = new HashMap<String, String>();
				
				if (r.getUserID() != null)
					unit.put("UserID", String.valueOf(r.getUserID()));
				else
					unit.put("UserID", "");
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				unit.put("Sex", String.valueOf(r.getSex()));
				if (r.getPassword() != null)
					unit.put("Password", String.valueOf(r.getPassword()));
				else
					unit.put("Password", "");
				if (r.getEmail() != null)
					unit.put("Email", String.valueOf(r.getEmail()));
				else
					unit.put("Email", "");
				if (r.getFaculty() != null)
					unit.put("Faculty", String.valueOf(r.getFaculty()));
				else
					unit.put("Faculty", "");
				unit.put("LoanedNumber", String.valueOf(r.getLoanedNumber()));
				unit.put("BorrowStatus", String.valueOf(r.getBorrowStatus()));
				unit.put("SuspensionDays", String.valueOf(r.getSuspensionDays()));
				unit.put("OverDueFee", String.valueOf(r.getOverDueFee()));
				
				unit.put("Position", String.valueOf(r.getPosition()));
				unit.put("Status", String.valueOf(r.getStatus()));
				dataFaculty.add(unit);
			}
			
			allObjectTables.get("Faculty").setItems(dataFaculty);
	}
	public void updateBookTable(List<Book> rsBook) {
			ObservableList<Map<String, String>> dataBook = FXCollections.observableArrayList();
			for (Book r : rsBook) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getCallNo() != null)
					unit.put("CallNo", String.valueOf(r.getCallNo()));
				else
					unit.put("CallNo", "");
				if (r.getTitle() != null)
					unit.put("Title", String.valueOf(r.getTitle()));
				else
					unit.put("Title", "");
				if (r.getEdition() != null)
					unit.put("Edition", String.valueOf(r.getEdition()));
				else
					unit.put("Edition", "");
				if (r.getAuthor() != null)
					unit.put("Author", String.valueOf(r.getAuthor()));
				else
					unit.put("Author", "");
				if (r.getPublisher() != null)
					unit.put("Publisher", String.valueOf(r.getPublisher()));
				else
					unit.put("Publisher", "");
				if (r.getDescription() != null)
					unit.put("Description", String.valueOf(r.getDescription()));
				else
					unit.put("Description", "");
				if (r.getISBn() != null)
					unit.put("ISBn", String.valueOf(r.getISBn()));
				else
					unit.put("ISBn", "");
				unit.put("CopyNum", String.valueOf(r.getCopyNum()));
				dataBook.add(unit);
			}
			
			allObjectTables.get("Book").setItems(dataBook);
	}
	public void updateSubjectTable(List<Subject> rsSubject) {
			ObservableList<Map<String, String>> dataSubject = FXCollections.observableArrayList();
			for (Subject r : rsSubject) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				dataSubject.add(unit);
			}
			
			allObjectTables.get("Subject").setItems(dataSubject);
	}
	public void updateBookCopyTable(List<BookCopy> rsBookCopy) {
			ObservableList<Map<String, String>> dataBookCopy = FXCollections.observableArrayList();
			for (BookCopy r : rsBookCopy) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getBarcode() != null)
					unit.put("Barcode", String.valueOf(r.getBarcode()));
				else
					unit.put("Barcode", "");
				unit.put("Status", String.valueOf(r.getStatus()));
				if (r.getLocation() != null)
					unit.put("Location", String.valueOf(r.getLocation()));
				else
					unit.put("Location", "");
				unit.put("IsReserved", String.valueOf(r.getIsReserved()));
				dataBookCopy.add(unit);
			}
			
			allObjectTables.get("BookCopy").setItems(dataBookCopy);
	}
	public void updateLoanTable(List<Loan> rsLoan) {
			ObservableList<Map<String, String>> dataLoan = FXCollections.observableArrayList();
			for (Loan r : rsLoan) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getLoanDate() != null)
					unit.put("LoanDate", r.getLoanDate().format(dateformatter));
				else
					unit.put("LoanDate", "");
				if (r.getRenewDate() != null)
					unit.put("RenewDate", r.getRenewDate().format(dateformatter));
				else
					unit.put("RenewDate", "");
				if (r.getDueDate() != null)
					unit.put("DueDate", r.getDueDate().format(dateformatter));
				else
					unit.put("DueDate", "");
				if (r.getReturnDate() != null)
					unit.put("ReturnDate", r.getReturnDate().format(dateformatter));
				else
					unit.put("ReturnDate", "");
				unit.put("RenewedTimes", String.valueOf(r.getRenewedTimes()));
				unit.put("IsReturned", String.valueOf(r.getIsReturned()));
				unit.put("OverDueFee", String.valueOf(r.getOverDueFee()));
				unit.put("OverDue3Days", String.valueOf(r.getOverDue3Days()));
				unit.put("OverDue10Days", String.valueOf(r.getOverDue10Days()));
				unit.put("OverDue17Days", String.valueOf(r.getOverDue17Days()));
				unit.put("OverDue31Days", String.valueOf(r.getOverDue31Days()));
				dataLoan.add(unit);
			}
			
			allObjectTables.get("Loan").setItems(dataLoan);
	}
	public void updateReserveTable(List<Reserve> rsReserve) {
			ObservableList<Map<String, String>> dataReserve = FXCollections.observableArrayList();
			for (Reserve r : rsReserve) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getReserveDate() != null)
					unit.put("ReserveDate", r.getReserveDate().format(dateformatter));
				else
					unit.put("ReserveDate", "");
				unit.put("IsReserveClosed", String.valueOf(r.getIsReserveClosed()));
				dataReserve.add(unit);
			}
			
			allObjectTables.get("Reserve").setItems(dataReserve);
	}
	public void updateRecommendBookTable(List<RecommendBook> rsRecommendBook) {
			ObservableList<Map<String, String>> dataRecommendBook = FXCollections.observableArrayList();
			for (RecommendBook r : rsRecommendBook) {
				Map<String, String> unit = new HashMap<String, String>();
				
				if (r.getCallNo() != null)
					unit.put("CallNo", String.valueOf(r.getCallNo()));
				else
					unit.put("CallNo", "");
				if (r.getTitle() != null)
					unit.put("Title", String.valueOf(r.getTitle()));
				else
					unit.put("Title", "");
				if (r.getEdition() != null)
					unit.put("Edition", String.valueOf(r.getEdition()));
				else
					unit.put("Edition", "");
				if (r.getAuthor() != null)
					unit.put("Author", String.valueOf(r.getAuthor()));
				else
					unit.put("Author", "");
				if (r.getPublisher() != null)
					unit.put("Publisher", String.valueOf(r.getPublisher()));
				else
					unit.put("Publisher", "");
				if (r.getDescription() != null)
					unit.put("Description", String.valueOf(r.getDescription()));
				else
					unit.put("Description", "");
				if (r.getISBn() != null)
					unit.put("ISBn", String.valueOf(r.getISBn()));
				else
					unit.put("ISBn", "");
				unit.put("CopyNum", String.valueOf(r.getCopyNum()));
				
				if (r.getRecommendDate() != null)
					unit.put("RecommendDate", r.getRecommendDate().format(dateformatter));
				else
					unit.put("RecommendDate", "");
				dataRecommendBook.add(unit);
			}
			
			allObjectTables.get("RecommendBook").setItems(dataRecommendBook);
	}
	public void updateAdministratorTable(List<Administrator> rsAdministrator) {
			ObservableList<Map<String, String>> dataAdministrator = FXCollections.observableArrayList();
			for (Administrator r : rsAdministrator) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getAdminID() != null)
					unit.put("AdminID", String.valueOf(r.getAdminID()));
				else
					unit.put("AdminID", "");
				if (r.getUserName() != null)
					unit.put("UserName", String.valueOf(r.getUserName()));
				else
					unit.put("UserName", "");
				if (r.getPassword() != null)
					unit.put("Password", String.valueOf(r.getPassword()));
				else
					unit.put("Password", "");
				dataAdministrator.add(unit);
			}
			
			allObjectTables.get("Administrator").setItems(dataAdministrator);
	}
	public void updateLibrarianTable(List<Librarian> rsLibrarian) {
			ObservableList<Map<String, String>> dataLibrarian = FXCollections.observableArrayList();
			for (Librarian r : rsLibrarian) {
				Map<String, String> unit = new HashMap<String, String>();
				
				
				if (r.getLibrarianID() != null)
					unit.put("LibrarianID", String.valueOf(r.getLibrarianID()));
				else
					unit.put("LibrarianID", "");
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				if (r.getPassword() != null)
					unit.put("Password", String.valueOf(r.getPassword()));
				else
					unit.put("Password", "");
				dataLibrarian.add(unit);
			}
			
			allObjectTables.get("Librarian").setItems(dataLibrarian);
	}
	
	/* 
	* update all object tables
	*/ 
	public void updateUserTable() {
			ObservableList<Map<String, String>> dataUser = FXCollections.observableArrayList();
			List<User> rsUser = EntityManager.getAllInstancesOf("User");
			for (User r : rsUser) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getUserID() != null)
					unit.put("UserID", String.valueOf(r.getUserID()));
				else
					unit.put("UserID", "");
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				unit.put("Sex", String.valueOf(r.getSex()));
				if (r.getPassword() != null)
					unit.put("Password", String.valueOf(r.getPassword()));
				else
					unit.put("Password", "");
				if (r.getEmail() != null)
					unit.put("Email", String.valueOf(r.getEmail()));
				else
					unit.put("Email", "");
				if (r.getFaculty() != null)
					unit.put("Faculty", String.valueOf(r.getFaculty()));
				else
					unit.put("Faculty", "");
				unit.put("LoanedNumber", String.valueOf(r.getLoanedNumber()));
				unit.put("BorrowStatus", String.valueOf(r.getBorrowStatus()));
				unit.put("SuspensionDays", String.valueOf(r.getSuspensionDays()));
				unit.put("OverDueFee", String.valueOf(r.getOverDueFee()));
				dataUser.add(unit);
			}
			
			allObjectTables.get("User").setItems(dataUser);
	}
	public void updateStudentTable() {
			ObservableList<Map<String, String>> dataStudent = FXCollections.observableArrayList();
			List<Student> rsStudent = EntityManager.getAllInstancesOf("Student");
			for (Student r : rsStudent) {
				Map<String, String> unit = new HashMap<String, String>();

				if (r.getUserID() != null)
					unit.put("UserID", String.valueOf(r.getUserID()));
				else
					unit.put("UserID", "");
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				unit.put("Sex", String.valueOf(r.getSex()));
				if (r.getPassword() != null)
					unit.put("Password", String.valueOf(r.getPassword()));
				else
					unit.put("Password", "");
				if (r.getEmail() != null)
					unit.put("Email", String.valueOf(r.getEmail()));
				else
					unit.put("Email", "");
				if (r.getFaculty() != null)
					unit.put("Faculty", String.valueOf(r.getFaculty()));
				else
					unit.put("Faculty", "");
				unit.put("LoanedNumber", String.valueOf(r.getLoanedNumber()));
				unit.put("BorrowStatus", String.valueOf(r.getBorrowStatus()));
				unit.put("SuspensionDays", String.valueOf(r.getSuspensionDays()));
				unit.put("OverDueFee", String.valueOf(r.getOverDueFee()));

				if (r.getMajor() != null)
					unit.put("Major", String.valueOf(r.getMajor()));
				else
					unit.put("Major", "");
				unit.put("Programme", String.valueOf(r.getProgramme()));
				unit.put("RegistrationStatus", String.valueOf(r.getRegistrationStatus()));
				dataStudent.add(unit);
			}
			
			allObjectTables.get("Student").setItems(dataStudent);
	}
	public void updateFacultyTable() {
			ObservableList<Map<String, String>> dataFaculty = FXCollections.observableArrayList();
			List<Faculty> rsFaculty = EntityManager.getAllInstancesOf("Faculty");
			for (Faculty r : rsFaculty) {
				Map<String, String> unit = new HashMap<String, String>();

				if (r.getUserID() != null)
					unit.put("UserID", String.valueOf(r.getUserID()));
				else
					unit.put("UserID", "");
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				unit.put("Sex", String.valueOf(r.getSex()));
				if (r.getPassword() != null)
					unit.put("Password", String.valueOf(r.getPassword()));
				else
					unit.put("Password", "");
				if (r.getEmail() != null)
					unit.put("Email", String.valueOf(r.getEmail()));
				else
					unit.put("Email", "");
				if (r.getFaculty() != null)
					unit.put("Faculty", String.valueOf(r.getFaculty()));
				else
					unit.put("Faculty", "");
				unit.put("LoanedNumber", String.valueOf(r.getLoanedNumber()));
				unit.put("BorrowStatus", String.valueOf(r.getBorrowStatus()));
				unit.put("SuspensionDays", String.valueOf(r.getSuspensionDays()));
				unit.put("OverDueFee", String.valueOf(r.getOverDueFee()));

				unit.put("Position", String.valueOf(r.getPosition()));
				unit.put("Status", String.valueOf(r.getStatus()));
				dataFaculty.add(unit);
			}
			
			allObjectTables.get("Faculty").setItems(dataFaculty);
	}
	public void updateBookTable() {
			ObservableList<Map<String, String>> dataBook = FXCollections.observableArrayList();
			List<Book> rsBook = EntityManager.getAllInstancesOf("Book");
			for (Book r : rsBook) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getCallNo() != null)
					unit.put("CallNo", String.valueOf(r.getCallNo()));
				else
					unit.put("CallNo", "");
				if (r.getTitle() != null)
					unit.put("Title", String.valueOf(r.getTitle()));
				else
					unit.put("Title", "");
				if (r.getEdition() != null)
					unit.put("Edition", String.valueOf(r.getEdition()));
				else
					unit.put("Edition", "");
				if (r.getAuthor() != null)
					unit.put("Author", String.valueOf(r.getAuthor()));
				else
					unit.put("Author", "");
				if (r.getPublisher() != null)
					unit.put("Publisher", String.valueOf(r.getPublisher()));
				else
					unit.put("Publisher", "");
				if (r.getDescription() != null)
					unit.put("Description", String.valueOf(r.getDescription()));
				else
					unit.put("Description", "");
				if (r.getISBn() != null)
					unit.put("ISBn", String.valueOf(r.getISBn()));
				else
					unit.put("ISBn", "");
				unit.put("CopyNum", String.valueOf(r.getCopyNum()));
				dataBook.add(unit);
			}
			
			allObjectTables.get("Book").setItems(dataBook);
	}
	public void updateSubjectTable() {
			ObservableList<Map<String, String>> dataSubject = FXCollections.observableArrayList();
			List<Subject> rsSubject = EntityManager.getAllInstancesOf("Subject");
			for (Subject r : rsSubject) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				dataSubject.add(unit);
			}
			
			allObjectTables.get("Subject").setItems(dataSubject);
	}
	public void updateBookCopyTable() {
			ObservableList<Map<String, String>> dataBookCopy = FXCollections.observableArrayList();
			List<BookCopy> rsBookCopy = EntityManager.getAllInstancesOf("BookCopy");
			for (BookCopy r : rsBookCopy) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getBarcode() != null)
					unit.put("Barcode", String.valueOf(r.getBarcode()));
				else
					unit.put("Barcode", "");
				unit.put("Status", String.valueOf(r.getStatus()));
				if (r.getLocation() != null)
					unit.put("Location", String.valueOf(r.getLocation()));
				else
					unit.put("Location", "");
				unit.put("IsReserved", String.valueOf(r.getIsReserved()));
				dataBookCopy.add(unit);
			}
			
			allObjectTables.get("BookCopy").setItems(dataBookCopy);
	}
	public void updateLoanTable() {
			ObservableList<Map<String, String>> dataLoan = FXCollections.observableArrayList();
			List<Loan> rsLoan = EntityManager.getAllInstancesOf("Loan");
			for (Loan r : rsLoan) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getLoanDate() != null)
					unit.put("LoanDate", r.getLoanDate().format(dateformatter));
				else
					unit.put("LoanDate", "");
				if (r.getRenewDate() != null)
					unit.put("RenewDate", r.getRenewDate().format(dateformatter));
				else
					unit.put("RenewDate", "");
				if (r.getDueDate() != null)
					unit.put("DueDate", r.getDueDate().format(dateformatter));
				else
					unit.put("DueDate", "");
				if (r.getReturnDate() != null)
					unit.put("ReturnDate", r.getReturnDate().format(dateformatter));
				else
					unit.put("ReturnDate", "");
				unit.put("RenewedTimes", String.valueOf(r.getRenewedTimes()));
				unit.put("IsReturned", String.valueOf(r.getIsReturned()));
				unit.put("OverDueFee", String.valueOf(r.getOverDueFee()));
				unit.put("OverDue3Days", String.valueOf(r.getOverDue3Days()));
				unit.put("OverDue10Days", String.valueOf(r.getOverDue10Days()));
				unit.put("OverDue17Days", String.valueOf(r.getOverDue17Days()));
				unit.put("OverDue31Days", String.valueOf(r.getOverDue31Days()));
				dataLoan.add(unit);
			}
			
			allObjectTables.get("Loan").setItems(dataLoan);
	}
	public void updateReserveTable() {
			ObservableList<Map<String, String>> dataReserve = FXCollections.observableArrayList();
			List<Reserve> rsReserve = EntityManager.getAllInstancesOf("Reserve");
			for (Reserve r : rsReserve) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getReserveDate() != null)
					unit.put("ReserveDate", r.getReserveDate().format(dateformatter));
				else
					unit.put("ReserveDate", "");
				unit.put("IsReserveClosed", String.valueOf(r.getIsReserveClosed()));
				dataReserve.add(unit);
			}
			
			allObjectTables.get("Reserve").setItems(dataReserve);
	}
	public void updateRecommendBookTable() {
			ObservableList<Map<String, String>> dataRecommendBook = FXCollections.observableArrayList();
			List<RecommendBook> rsRecommendBook = EntityManager.getAllInstancesOf("RecommendBook");
			for (RecommendBook r : rsRecommendBook) {
				Map<String, String> unit = new HashMap<String, String>();

				if (r.getCallNo() != null)
					unit.put("CallNo", String.valueOf(r.getCallNo()));
				else
					unit.put("CallNo", "");
				if (r.getTitle() != null)
					unit.put("Title", String.valueOf(r.getTitle()));
				else
					unit.put("Title", "");
				if (r.getEdition() != null)
					unit.put("Edition", String.valueOf(r.getEdition()));
				else
					unit.put("Edition", "");
				if (r.getAuthor() != null)
					unit.put("Author", String.valueOf(r.getAuthor()));
				else
					unit.put("Author", "");
				if (r.getPublisher() != null)
					unit.put("Publisher", String.valueOf(r.getPublisher()));
				else
					unit.put("Publisher", "");
				if (r.getDescription() != null)
					unit.put("Description", String.valueOf(r.getDescription()));
				else
					unit.put("Description", "");
				if (r.getISBn() != null)
					unit.put("ISBn", String.valueOf(r.getISBn()));
				else
					unit.put("ISBn", "");
				unit.put("CopyNum", String.valueOf(r.getCopyNum()));

				if (r.getRecommendDate() != null)
					unit.put("RecommendDate", r.getRecommendDate().format(dateformatter));
				else
					unit.put("RecommendDate", "");
				dataRecommendBook.add(unit);
			}
			
			allObjectTables.get("RecommendBook").setItems(dataRecommendBook);
	}
	public void updateAdministratorTable() {
			ObservableList<Map<String, String>> dataAdministrator = FXCollections.observableArrayList();
			List<Administrator> rsAdministrator = EntityManager.getAllInstancesOf("Administrator");
			for (Administrator r : rsAdministrator) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getAdminID() != null)
					unit.put("AdminID", String.valueOf(r.getAdminID()));
				else
					unit.put("AdminID", "");
				if (r.getUserName() != null)
					unit.put("UserName", String.valueOf(r.getUserName()));
				else
					unit.put("UserName", "");
				if (r.getPassword() != null)
					unit.put("Password", String.valueOf(r.getPassword()));
				else
					unit.put("Password", "");
				dataAdministrator.add(unit);
			}
			
			allObjectTables.get("Administrator").setItems(dataAdministrator);
	}
	public void updateLibrarianTable() {
			ObservableList<Map<String, String>> dataLibrarian = FXCollections.observableArrayList();
			List<Librarian> rsLibrarian = EntityManager.getAllInstancesOf("Librarian");
			for (Librarian r : rsLibrarian) {
				Map<String, String> unit = new HashMap<String, String>();


				if (r.getLibrarianID() != null)
					unit.put("LibrarianID", String.valueOf(r.getLibrarianID()));
				else
					unit.put("LibrarianID", "");
				if (r.getName() != null)
					unit.put("Name", String.valueOf(r.getName()));
				else
					unit.put("Name", "");
				if (r.getPassword() != null)
					unit.put("Password", String.valueOf(r.getPassword()));
				else
					unit.put("Password", "");
				dataLibrarian.add(unit);
			}
			
			allObjectTables.get("Librarian").setItems(dataLibrarian);
	}
	
	public void classStatisicBingding() {
	
		 classInfodata = FXCollections.observableArrayList();
	 	 user = new ClassInfo("User", EntityManager.getAllInstancesOf("User").size());
	 	 classInfodata.add(user);
	 	 student = new ClassInfo("Student", EntityManager.getAllInstancesOf("Student").size());
	 	 classInfodata.add(student);
	 	 faculty = new ClassInfo("Faculty", EntityManager.getAllInstancesOf("Faculty").size());
	 	 classInfodata.add(faculty);
	 	 book = new ClassInfo("Book", EntityManager.getAllInstancesOf("Book").size());
	 	 classInfodata.add(book);
	 	 subject = new ClassInfo("Subject", EntityManager.getAllInstancesOf("Subject").size());
	 	 classInfodata.add(subject);
	 	 bookcopy = new ClassInfo("BookCopy", EntityManager.getAllInstancesOf("BookCopy").size());
	 	 classInfodata.add(bookcopy);
	 	 loan = new ClassInfo("Loan", EntityManager.getAllInstancesOf("Loan").size());
	 	 classInfodata.add(loan);
	 	 reserve = new ClassInfo("Reserve", EntityManager.getAllInstancesOf("Reserve").size());
	 	 classInfodata.add(reserve);
	 	 recommendbook = new ClassInfo("RecommendBook", EntityManager.getAllInstancesOf("RecommendBook").size());
	 	 classInfodata.add(recommendbook);
	 	 administrator = new ClassInfo("Administrator", EntityManager.getAllInstancesOf("Administrator").size());
	 	 classInfodata.add(administrator);
	 	 librarian = new ClassInfo("Librarian", EntityManager.getAllInstancesOf("Librarian").size());
	 	 classInfodata.add(librarian);
	 	 
		 class_statisic.setItems(classInfodata);
		 
		 //Class Statisic Binding
		 class_statisic.getSelectionModel().selectedItemProperty().addListener(
				 (observable, oldValue, newValue) ->  { 
				 										 //no selected object in table
				 										 objectindex = -1;
				 										 
				 										 //get lastest data, reflect updateTableData method
				 										 try {
												 			 Method updateob = this.getClass().getMethod("update" + newValue.getName() + "Table", null);
												 			 updateob.invoke(this);			 
												 		 } catch (Exception e) {
												 		 	 e.printStackTrace();
												 		 }		 										 
				 	
				 										 //show object table
				 			 				 			 TableView obs = allObjectTables.get(newValue.getName());
				 			 				 			 if (obs != null) {
				 			 				 				object_statics.setContent(obs);
				 			 				 				object_statics.setText("All Objects " + newValue.getName() + ":");
				 			 				 			 }
				 			 				 			 
				 			 				 			 //update association information
							 			 				 updateAssociation(newValue.getName());
				 			 				 			 
				 			 				 			 //show association information
				 			 				 			 ObservableList<AssociationInfo> asso = allassociationData.get(newValue.getName());
				 			 				 			 if (asso != null) {
				 			 				 			 	association_statisic.setItems(asso);
				 			 				 			 }
				 			 				 		  });
	}
	
	public void classStatisticUpdate() {
	 	 user.setNumber(EntityManager.getAllInstancesOf("User").size());
	 	 student.setNumber(EntityManager.getAllInstancesOf("Student").size());
	 	 faculty.setNumber(EntityManager.getAllInstancesOf("Faculty").size());
	 	 book.setNumber(EntityManager.getAllInstancesOf("Book").size());
	 	 subject.setNumber(EntityManager.getAllInstancesOf("Subject").size());
	 	 bookcopy.setNumber(EntityManager.getAllInstancesOf("BookCopy").size());
	 	 loan.setNumber(EntityManager.getAllInstancesOf("Loan").size());
	 	 reserve.setNumber(EntityManager.getAllInstancesOf("Reserve").size());
	 	 recommendbook.setNumber(EntityManager.getAllInstancesOf("RecommendBook").size());
	 	 administrator.setNumber(EntityManager.getAllInstancesOf("Administrator").size());
	 	 librarian.setNumber(EntityManager.getAllInstancesOf("Librarian").size());
		
	}
	
	/**
	 * association binding
	 */
	public void associationStatisicBingding() {
		
		allassociationData = new HashMap<String, ObservableList<AssociationInfo>>();
		
		ObservableList<AssociationInfo> User_association_data = FXCollections.observableArrayList();
		AssociationInfo User_associatition_LoanedBook = new AssociationInfo("User", "Loan", "LoanedBook", true);
		User_association_data.add(User_associatition_LoanedBook);
		AssociationInfo User_associatition_ReservedBook = new AssociationInfo("User", "Reserve", "ReservedBook", true);
		User_association_data.add(User_associatition_ReservedBook);
		AssociationInfo User_associatition_RecommendedBook = new AssociationInfo("User", "RecommendBook", "RecommendedBook", true);
		User_association_data.add(User_associatition_RecommendedBook);
		
		allassociationData.put("User", User_association_data);
		
		ObservableList<AssociationInfo> Student_association_data = FXCollections.observableArrayList();
		
		allassociationData.put("Student", Student_association_data);
		
		ObservableList<AssociationInfo> Faculty_association_data = FXCollections.observableArrayList();
		
		allassociationData.put("Faculty", Faculty_association_data);
		
		ObservableList<AssociationInfo> Book_association_data = FXCollections.observableArrayList();
		AssociationInfo Book_associatition_Copys = new AssociationInfo("Book", "BookCopy", "Copys", true);
		Book_association_data.add(Book_associatition_Copys);
		AssociationInfo Book_associatition_Subject = new AssociationInfo("Book", "Subject", "Subject", true);
		Book_association_data.add(Book_associatition_Subject);
		
		allassociationData.put("Book", Book_association_data);
		
		ObservableList<AssociationInfo> Subject_association_data = FXCollections.observableArrayList();
		AssociationInfo Subject_associatition_SuperSubject = new AssociationInfo("Subject", "Subject", "SuperSubject", false);
		Subject_association_data.add(Subject_associatition_SuperSubject);
		AssociationInfo Subject_associatition_SubSubject = new AssociationInfo("Subject", "Subject", "SubSubject", true);
		Subject_association_data.add(Subject_associatition_SubSubject);
		
		allassociationData.put("Subject", Subject_association_data);
		
		ObservableList<AssociationInfo> BookCopy_association_data = FXCollections.observableArrayList();
		AssociationInfo BookCopy_associatition_BookBelongs = new AssociationInfo("BookCopy", "Book", "BookBelongs", false);
		BookCopy_association_data.add(BookCopy_associatition_BookBelongs);
		AssociationInfo BookCopy_associatition_LoanedRecord = new AssociationInfo("BookCopy", "Loan", "LoanedRecord", true);
		BookCopy_association_data.add(BookCopy_associatition_LoanedRecord);
		AssociationInfo BookCopy_associatition_ReservationRecord = new AssociationInfo("BookCopy", "Reserve", "ReservationRecord", true);
		BookCopy_association_data.add(BookCopy_associatition_ReservationRecord);
		
		allassociationData.put("BookCopy", BookCopy_association_data);
		
		ObservableList<AssociationInfo> Loan_association_data = FXCollections.observableArrayList();
		AssociationInfo Loan_associatition_LoanedUser = new AssociationInfo("Loan", "User", "LoanedUser", false);
		Loan_association_data.add(Loan_associatition_LoanedUser);
		AssociationInfo Loan_associatition_LoanedCopy = new AssociationInfo("Loan", "BookCopy", "LoanedCopy", false);
		Loan_association_data.add(Loan_associatition_LoanedCopy);
		AssociationInfo Loan_associatition_LoanLibrarian = new AssociationInfo("Loan", "Librarian", "LoanLibrarian", false);
		Loan_association_data.add(Loan_associatition_LoanLibrarian);
		AssociationInfo Loan_associatition_ReturnLibrarian = new AssociationInfo("Loan", "Librarian", "ReturnLibrarian", false);
		Loan_association_data.add(Loan_associatition_ReturnLibrarian);
		
		allassociationData.put("Loan", Loan_association_data);
		
		ObservableList<AssociationInfo> Reserve_association_data = FXCollections.observableArrayList();
		AssociationInfo Reserve_associatition_ReservedCopy = new AssociationInfo("Reserve", "BookCopy", "ReservedCopy", false);
		Reserve_association_data.add(Reserve_associatition_ReservedCopy);
		AssociationInfo Reserve_associatition_ReservedUser = new AssociationInfo("Reserve", "User", "ReservedUser", false);
		Reserve_association_data.add(Reserve_associatition_ReservedUser);
		
		allassociationData.put("Reserve", Reserve_association_data);
		
		ObservableList<AssociationInfo> RecommendBook_association_data = FXCollections.observableArrayList();
		AssociationInfo RecommendBook_associatition_RecommendUser = new AssociationInfo("RecommendBook", "User", "RecommendUser", false);
		RecommendBook_association_data.add(RecommendBook_associatition_RecommendUser);
		
		allassociationData.put("RecommendBook", RecommendBook_association_data);
		
		ObservableList<AssociationInfo> Administrator_association_data = FXCollections.observableArrayList();
		
		allassociationData.put("Administrator", Administrator_association_data);
		
		ObservableList<AssociationInfo> Librarian_association_data = FXCollections.observableArrayList();
		
		allassociationData.put("Librarian", Librarian_association_data);
		
		
		association_statisic.getSelectionModel().selectedItemProperty().addListener(
			    (observable, oldValue, newValue) ->  { 
	
							 		if (newValue != null) {
							 			 try {
							 			 	 if (newValue.getNumber() != 0) {
								 				 //choose object or not
								 				 if (objectindex != -1) {
									 				 Class[] cArg = new Class[1];
									 				 cArg[0] = List.class;
									 				 //reflect updateTableData method
										 			 Method updateob = this.getClass().getMethod("update" + newValue.getTargetClass() + "Table", cArg);
										 			 //find choosen object
										 			 Object selectedob = EntityManager.getAllInstancesOf(newValue.getSourceClass()).get(objectindex);
										 			 //reflect find association method
										 			 Method getAssociatedObject = selectedob.getClass().getMethod("get" + newValue.getAssociationName());
										 			 List r = new LinkedList();
										 			 //one or mulity?
										 			 if (newValue.getIsMultiple() == true) {
											 			 
											 			r = (List) getAssociatedObject.invoke(selectedob);
										 			 }
										 			 else {
										 				r.add(getAssociatedObject.invoke(selectedob));
										 			 }
										 			 //invoke update method
										 			 updateob.invoke(this, r);
										 			  
										 			 
								 				 }
												 //bind updated data to GUI
					 				 			 TableView obs = allObjectTables.get(newValue.getTargetClass());
					 				 			 if (obs != null) {
					 				 				object_statics.setContent(obs);
					 				 				object_statics.setText("Targets Objects " + newValue.getTargetClass() + ":");
					 				 			 }
					 				 		 }
							 			 }
							 			 catch (Exception e) {
							 				 e.printStackTrace();
							 			 }
							 		}
					 		  });
		
	}	
	
	

    //prepare data for contract
	public void prepareData() {
		
		//definition map
		definitions_map = new HashMap<String, String>();
		definitions_map.put("registerAccount", "existingUser = User.allInstance()->any(u:User | u.UserID = userID or u.Email = email)\r\n");
		definitions_map.put("makeReservation", "user:User = User.allInstance()->any(u:User | u.UserID = uid)\ncopy:BookCopy = BookCopy.allInstance()->any(bc:BookCopy | bc.Barcode = barcode)\r\n");
		definitions_map.put("cancelReservation", "user:User = User.allInstance()->any(u:User | u.UserID = uid)\ncopy:BookCopy = BookCopy.allInstance()->any(bc:BookCopy | bc.Barcode = barcode)\nres:Reserve = Reserve.allInstance()->any(r:Reserve | r.ReservedCopy = copy and r.ReservedUser = user)\r\n");
		definitions_map.put("borrowBook", "user:User = User.allInstance()->any(u:User | u.UserID = uid)\nstu:Student = Student.allInstance()->any(s:Student | s.UserID = uid)\nfac:Faculty = Faculty.allInstance()->any(f:Faculty | f.UserID = uid)\ncopy:BookCopy = BookCopy.allInstance()->any(bc:BookCopy | bc.Barcode = barcode)\nres:Reserve = Reserve.allInstance()->any(r:Reserve | r.ReservedCopy = copy and r.ReservedUser = user and r.IsReserveClosed = false)\r\n");
		definitions_map.put("returnBook", "copy:BookCopy = BookCopy.allInstance()->any(bc:BookCopy | bc.Barcode = barcode and bc.Status = CopyStatus :: LOANED)\nloan:Loan = Loan.allInstance()->any(l:Loan | l.LoanedCopy = copy and l.IsReturned = false)\nloans:Set(Loan) = Loan.allInstance()->select(l:Loan | l.LoanedUser = loan.LoanedUser and l.IsReturned = false and l.DueDate.isAfter(Today))\nres:Reserve = copy.ReservationRecord->any(r:Reserve | r.ReservedCopy = copy)\r\n");
		definitions_map.put("renewBook", "user:User = User.allInstance()->any(u:User | u.UserID = uid)\nstu:Student = Student.allInstance()->any(s:Student | s.UserID = uid)\nfac:Faculty = Faculty.allInstance()->any(f:Faculty | f.UserID = uid)\ncopy:BookCopy = BookCopy.allInstance()->any(bc:BookCopy | bc.Barcode = barcode and bc.Status = CopyStatus :: LOANED)\nloan:Loan = Loan.allInstance()->any(l:Loan | l.LoanedUser = user and l.LoanedCopy = copy)\r\n");
		definitions_map.put("payOverDueFee", "user:User = User.allInstance()->any(u:User | u.UserID = uid)\nloans:Set(Loan) = Loan.allInstance()->select(l:Loan | l.LoanedUser = user and l.DueDate.isBefore(Today) and l.IsReturned = true and l.OverDueFee > 0)\r\n");
		definitions_map.put("dueSoonNotification", "users:Set(User) = User.allInstance()->select(user:User | user.LoanedBook->exists(loan:Loan | loan.IsReturned = false and Today.After(3).isAfter(loan.DueDate)))\r\n");
		definitions_map.put("checkOverDueandComputeOverDueFee", "loans:Set(Loan) = Loan.allInstance()->select(loan:Loan | loan.IsReturned = false and loan.DueDate.isBefore(Today))\r\n");
		definitions_map.put("countDownSuspensionDay", "users:Set(User) = User.allInstance()->select(u:User | u.SuspensionDays > 0)\r\n");
		definitions_map.put("listBorrowHistory", "user:User = User.allInstance()->any(u:User | u.UserID = uid)\r\n");
		definitions_map.put("listHodingBook", "user:User = User.allInstance()->any(u:User | u.UserID = uid)\r\n");
		definitions_map.put("listOverDueBook", "user:User = User.allInstance()->any(u:User | u.UserID = uid)\nloans:Set(Loan) = user.LoanedBook->select(l:Loan | l.IsReturned = false and l.OverDueFee > 0)\r\n");
		definitions_map.put("listReservationBook", "user:User = User.allInstance()->any(u:User | u.UserID = uid)\nres:Set(Reserve) = user.ReservedBook\r\n");
		definitions_map.put("listRecommendBook", "user:User = User.allInstance()->any(u:User | u.UserID = uid)\nrBooks:Set(RecommendBook) = user.RecommendedBook\r\n");
		definitions_map.put("recommendBook", "user:User = User.allInstance()->any(u:User | u.UserID = uid)\nrb:RecommendBook = RecommendBook.allInstance()->any(r:RecommendBook | r.CallNo = callNo)\r\n");
		definitions_map.put("createStudent", "user:Student = Student.allInstance()->any(u:Student | u.UserID = userID)\r\n");
		definitions_map.put("modifyStudent", "user:Student = Student.allInstance()->any(u:Student | u.UserID = userID)\r\n");
		definitions_map.put("createFaculty", "user:Faculty = Faculty.allInstance()->any(u:Faculty | u.UserID = userID)\r\n");
		definitions_map.put("modifyFaculty", "user:Faculty = Faculty.allInstance()->any(u:Faculty | u.UserID = userID)\r\n");
		definitions_map.put("createUser", "user:User = User.allInstance()->any(use:User | use.UserID = userid)\r\n");
		definitions_map.put("queryUser", "user:User = User.allInstance()->any(use:User | use.UserID = userid)\r\n");
		definitions_map.put("modifyUser", "user:User = User.allInstance()->any(use:User | use.UserID = userid)\r\n");
		definitions_map.put("deleteUser", "user:User = User.allInstance()->any(use:User | use.UserID = userid)\r\n");
		definitions_map.put("createBook", "book:Book = Book.allInstance()->any(boo:Book | boo.CallNo = callno)\r\n");
		definitions_map.put("queryBook", "book:Book = Book.allInstance()->any(boo:Book | boo.CallNo = callno)\r\n");
		definitions_map.put("modifyBook", "book:Book = Book.allInstance()->any(boo:Book | boo.CallNo = callno)\r\n");
		definitions_map.put("deleteBook", "book:Book = Book.allInstance()->any(boo:Book | boo.CallNo = callno)\r\n");
		definitions_map.put("createSubject", "subject:Subject = Subject.allInstance()->any(sub:Subject | sub.Name = name)\r\n");
		definitions_map.put("querySubject", "subject:Subject = Subject.allInstance()->any(sub:Subject | sub.Name = name)\r\n");
		definitions_map.put("modifySubject", "subject:Subject = Subject.allInstance()->any(sub:Subject | sub.Name = name)\r\n");
		definitions_map.put("deleteSubject", "subject:Subject = Subject.allInstance()->any(sub:Subject | sub.Name = name)\r\n");
		definitions_map.put("addBookCopy", "book:Book = Book.allInstance()->any(b:Book | b.CallNo = callNo)\nbc:BookCopy = book.Copys->any(c:BookCopy | c.Barcode = barcode)\r\n");
		definitions_map.put("queryBookCopy", "bookcopy:BookCopy = BookCopy.allInstance()->any(boo:BookCopy | boo.Barcode = barcode)\r\n");
		definitions_map.put("modifyBookCopy", "bookcopy:BookCopy = BookCopy.allInstance()->any(boo:BookCopy | boo.Barcode = barcode)\r\n");
		definitions_map.put("deleteBookCopy", "bookcopy:BookCopy = BookCopy.allInstance()->any(boo:BookCopy | boo.Barcode = barcode)\r\n");
		definitions_map.put("createLibrarian", "librarian:Librarian = Librarian.allInstance()->any(lib:Librarian | lib.LibrarianID = librarianid)\r\n");
		definitions_map.put("queryLibrarian", "librarian:Librarian = Librarian.allInstance()->any(lib:Librarian | lib.LibrarianID = librarianid)\r\n");
		definitions_map.put("modifyLibrarian", "librarian:Librarian = Librarian.allInstance()->any(lib:Librarian | lib.LibrarianID = librarianid)\r\n");
		definitions_map.put("deleteLibrarian", "librarian:Librarian = Librarian.allInstance()->any(lib:Librarian | lib.LibrarianID = librarianid)\r\n");
		
		//precondition map
		preconditions_map = new HashMap<String, String>();
		preconditions_map.put("registerAccount", "userID.size() >= 6");
		preconditions_map.put("searchBookByBarCode", "barcode.oclIsTypeOf(String)");
		preconditions_map.put("searchBookByTitle", "title <> \"\"");
		preconditions_map.put("searchBookByAuthor", "authorname <> \"\"");
		preconditions_map.put("searchBookByISBN", "iSBNnumber.oclIsTypeOf(String)");
		preconditions_map.put("searchBookBySubject", "subject.oclIsTypeOf(String)");
		preconditions_map.put("makeReservation", "user.oclIsUndefined() = false and\ncopy.oclIsUndefined() = false and\ncopy.Status = CopyStatus::LOANED and\ncopy.IsReserved = false\n");
		preconditions_map.put("cancelReservation", "user.oclIsUndefined() = false and\ncopy.oclIsUndefined() = false and\ncopy.Status = CopyStatus::LOANED and\ncopy.IsReserved = true and\nres.oclIsUndefined() = false and\nres.IsReserveClosed = false\n");
		preconditions_map.put("borrowBook", "user.oclIsUndefined() = false and\ncopy.oclIsUndefined() = false and\nuser.BorrowStatus = BorrowStatus::NORMAL and\nuser.SuspensionDays = 0 and\nif\nuser.oclIsTypeOf(Student)\nthen\nif\nstu.Programme = Programme::BACHELOR\nthen\nstu.LoanedNumber < 20\nelse\nif\nstu.Programme = Programme::MASTER\nthen\nstu.LoanedNumber < 40\nelse\nstu.LoanedNumber < 60\nendif\nendif\nelse\nfac.LoanedNumber < 60\nendif and\n(copy.Status = CopyStatus::AVAILABLE or\n(copy.Status = CopyStatus::ONHOLDSHELF and\ncopy.IsReserved = true and\nres.oclIsUndefined() = false and\nres.IsReserveClosed = false\n)\n)\n");
		preconditions_map.put("returnBook", "copy.oclIsUndefined() = false and\nloan.oclIsUndefined() = false\n");
		preconditions_map.put("renewBook", "user.BorrowStatus = BorrowStatus::NORMAL and\nuser.oclIsUndefined() = false and\ncopy.oclIsUndefined() = false and\nloan.oclIsUndefined() = false and\ncopy.IsReserved = false and\nloan.DueDate.isAfter(Today) and\nif\nuser.oclIsTypeOf(Student)\nthen\nloan.RenewedTimes < 3\nelse\nloan.RenewedTimes < 6\nendif and\nloan.OverDueFee = 0\n");
		preconditions_map.put("payOverDueFee", "user.oclIsUndefined() = false and\nloans.notEmpty() and\nfee >= user.OverDueFee\n");
		preconditions_map.put("dueSoonNotification", "true");
		preconditions_map.put("checkOverDueandComputeOverDueFee", "true");
		preconditions_map.put("countDownSuspensionDay", "true");
		preconditions_map.put("listBorrowHistory", "user.oclIsUndefined() = false");
		preconditions_map.put("listHodingBook", "user.oclIsUndefined() = false");
		preconditions_map.put("listOverDueBook", "user.oclIsUndefined() = false and\nloans.oclIsUndefined() = false\n");
		preconditions_map.put("listReservationBook", "user.oclIsUndefined() = false and\nres.oclIsUndefined() = false\n");
		preconditions_map.put("listRecommendBook", "user.oclIsUndefined() = false and\nrBooks.oclIsUndefined() = false\n");
		preconditions_map.put("recommendBook", "user.oclIsUndefined() = false and\nrb.oclIsUndefined() = true\n");
		preconditions_map.put("createStudent", "user.oclIsUndefined() = true");
		preconditions_map.put("modifyStudent", "user.oclIsUndefined() = false");
		preconditions_map.put("createFaculty", "user.oclIsUndefined() = true");
		preconditions_map.put("modifyFaculty", "user.oclIsUndefined() = false");
		preconditions_map.put("sendNotificationEmail", "email <> \"\"");
		preconditions_map.put("createUser", "user.oclIsUndefined() = true");
		preconditions_map.put("queryUser", "user.oclIsUndefined() = false");
		preconditions_map.put("modifyUser", "user.oclIsUndefined() = false");
		preconditions_map.put("deleteUser", "user.oclIsUndefined() = false and\nUser.allInstance()->includes(user)\n");
		preconditions_map.put("createBook", "book.oclIsUndefined() = true");
		preconditions_map.put("queryBook", "book.oclIsUndefined() = false");
		preconditions_map.put("modifyBook", "book.oclIsUndefined() = false");
		preconditions_map.put("deleteBook", "book.oclIsUndefined() = false and\nBook.allInstance()->includes(book)\n");
		preconditions_map.put("createSubject", "subject.oclIsUndefined() = true");
		preconditions_map.put("querySubject", "subject.oclIsUndefined() = false");
		preconditions_map.put("modifySubject", "subject.oclIsUndefined() = false");
		preconditions_map.put("deleteSubject", "subject.oclIsUndefined() = false and\nSubject.allInstance()->includes(subject)\n");
		preconditions_map.put("addBookCopy", "book.oclIsUndefined() = false and\nbc.oclIsUndefined() = true\n");
		preconditions_map.put("queryBookCopy", "bookcopy.oclIsUndefined() = false");
		preconditions_map.put("modifyBookCopy", "bookcopy.oclIsUndefined() = false");
		preconditions_map.put("deleteBookCopy", "bookcopy.oclIsUndefined() = false and\nBookCopy.allInstance()->includes(bookcopy)\n");
		preconditions_map.put("createLibrarian", "librarian.oclIsUndefined() = true");
		preconditions_map.put("queryLibrarian", "librarian.oclIsUndefined() = false");
		preconditions_map.put("modifyLibrarian", "librarian.oclIsUndefined() = false");
		preconditions_map.put("deleteLibrarian", "librarian.oclIsUndefined() = false and\nLibrarian.allInstance()->includes(librarian)\n");
		preconditions_map.put("inputAccount", "true");
		preconditions_map.put("inputPassword", "true");
		preconditions_map.put("inputAccount1", "true");
		preconditions_map.put("inputPasswordNew", "true");
		preconditions_map.put("inputPasswordNew", "true");
		preconditions_map.put("ensurePassword1", "true");
		preconditions_map.put("inputPassword1", "true");
		preconditions_map.put("inputCard1", "true");
		preconditions_map.put("inputPassword2", "true");
		preconditions_map.put("inputVerificationcode", "true");
		preconditions_map.put("inputCard12", "true");
		preconditions_map.put("inputPassword11", "true");
		preconditions_map.put("ensureDeleteAccount", "true");
		preconditions_map.put("inputBookInformation", "true");
		preconditions_map.put("inputCardID", "true");
		preconditions_map.put("exitStytem", "true");
		
		//postcondition map
		postconditions_map = new HashMap<String, String>();
		postconditions_map.put("registerAccount", "let newUser:User innewUser.oclIsNew() and\nnewUser.UserID = userID and\nnewUser.Email = email and\nnewUser.PasswordHash = encrypt(password) and\nnewUser.RegistrationDate = Today and\nnewUser.AccountStatus = AccountStatus::ACTIVE and\nnewUser.FailedLoginAttempts = 0 and\nUser.allInstance()->includes(newUser) and\nsendVerificationEmail(email) and\nresult = true\n");
		postconditions_map.put("searchBookByBarCode", "result = Book.allInstance()->select(book:Book | book.Copys->exists(c:BookCopy | c.Barcode = barcode))");
		postconditions_map.put("searchBookByTitle", "result = Book.allInstance()->select(book:Book | book.Title = title)");
		postconditions_map.put("searchBookByAuthor", "result = Book.allInstance()->select(book:Book | book.Author = authorname)");
		postconditions_map.put("searchBookByISBN", "result = Book.allInstance()->select(book:Book | book.ISBn = iSBNnumber)");
		postconditions_map.put("searchBookBySubject", "result = Book.allInstance()->select(book:Book | book.Subject->exists(s:Subject | s.Name = subject))");
		postconditions_map.put("makeReservation", "let res:Reserve inres.oclIsNew() and\ncopy.IsReserved = true and\nres.IsReserveClosed = false and\nres.ReserveDate.isEqual(Today) and\nres.ReservedUser = user and\nres.ReservedCopy = copy and\nuser.ReservedBook->includes(res) and\ncopy.ReservationRecord->includes(res) and\nReserve.allInstance()->includes(res) and\nresult = true\n");
		postconditions_map.put("cancelReservation", "copy.IsReserved = false and\nres.IsReserveClosed = true and\nresult = true\n");
		postconditions_map.put("borrowBook", "let loan:Loan inloan.oclIsNew() and\nloan.LoanedUser = user and\nloan.LoanedCopy = copy and\nloan.IsReturned = false and\nloan.LoanDate = Today and\nuser.LoanedNumber = user.LoanedNumber@pre + 1 and\nuser.LoanedBook->includes(loan) and\ncopy.LoanedRecord->includes(loan) and\nif\nuser.oclIsTypeOf(Student)\nthen\nloan.DueDate = Today.After(30)\nelse\nloan.DueDate = Today.After(60)\nendif and\nif\ncopy.Status@pre = CopyStatus::ONHOLDSHELF\nthen\ncopy.IsReserved = false and\nres.IsReserveClosed = true\nendif and\ncopy.Status = CopyStatus::LOANED and\nloan.OverDue3Days = false and\nloan.OverDue10Days = false and\nloan.OverDue17Days = false and\nloan.OverDue31Days = false and\nLoan.allInstance()->includes(loan) and\nresult = true\n");
		postconditions_map.put("returnBook", "loan.LoanedUser.LoanedNumber = loan.LoanedUser.LoanedNumber@pre - 1 and\nloan.IsReturned = true and\nloan.ReturnDate = Today and\nif\ncopy.IsReserved = true\nthen\ncopy.Status = CopyStatus::ONHOLDSHELF and\nsendNotificationEmail(res.ReservedUser.Email)\nelse\ncopy.Status = CopyStatus::AVAILABLE\nendif and\nresult = true\n");
		postconditions_map.put("renewBook", "loan.RenewedTimes = loan.RenewedTimes@pre + 1 and\nloan.RenewDate = Today and\nif\nuser.oclIsTypeOf(Student)\nthen\nif\nstu.Programme = Programme::BACHELOR\nthen\nloan.DueDate = loan.DueDate@pre.After(20)\nelse\nif\nstu.Programme = Programme::MASTER\nthen\nloan.DueDate = loan.DueDate@pre.After(40)\nelse\nloan.DueDate = loan.DueDate@pre.After(60)\nendif\nendif\nelse\nloan.DueDate = loan.DueDate@pre.After(60)\nendif and\nresult = true\n");
		postconditions_map.put("payOverDueFee", "user.OverDueFee = 0 and\nloans->forAll(l:Loan |\nl.OverDueFee = 0)\nand\nresult = true\n");
		postconditions_map.put("dueSoonNotification", "users->forAll(u:User |\nsendNotificationEmail(u.Email))\n");
		postconditions_map.put("checkOverDueandComputeOverDueFee", "loans->forAll(loan:Loan |\nloan.IsReturned = false and\nif\nToday.Before(3).isAfter(loan.DueDate) and\nloan.OverDue3Days = false\nthen\nloan.LoanedUser.BorrowStatus = BorrowStatus::SUSPEND and\nsendNotificationEmail(loan.LoanedUser.Email) and\nloan.OverDue3Days = true\nendif and\nif\nToday.Before(10).isAfter(loan.DueDate) and\nloan.OverDue10Days = false\nthen\nloan.LoanedUser.SuspensionDays = loan.LoanedUser.SuspensionDays@pre + 14 and\nsendNotificationEmail(loan.LoanedUser.Email) and\nloan.OverDue10Days = true\nendif and\nif\nToday.Before(17).isAfter(loan.DueDate) and\nloan.OverDue17Days = false\nthen\nloan.LoanedUser.SuspensionDays = loan.LoanedUser.SuspensionDays@pre + 30 and\nsendNotificationEmail(loan.LoanedUser.Email) and\nloan.OverDue17Days = true\nendif and\nif\nToday.Before(31).isAfter(loan.DueDate) and\nloan.OverDue31Days = false\nthen\nloan.OverDueFee = 60 and\nsendNotificationEmail(loan.LoanedUser.Email) and\nloan.OverDue31Days = true and\nloan.LoanedUser.OverDueFee = loan.LoanedUser.OverDueFee@pre + loan.OverDueFee\nendif\n)\n");
		postconditions_map.put("countDownSuspensionDay", "users->forAll(u:User |\nu.SuspensionDays = u.SuspensionDays@pre - 1 and\nif\nu.BorrowStatus = BorrowStatus::SUSPEND and\nu.OverDueFee = 0 and\nu.SuspensionDays = 0\nthen\nu.BorrowStatus = BorrowStatus::NORMAL\nendif\n)\n");
		postconditions_map.put("listBorrowHistory", "result = user.LoanedBook");
		postconditions_map.put("listHodingBook", "result = user.LoanedBook->select(l:Loan | l.IsReturned = false)");
		postconditions_map.put("listOverDueBook", "result = loans->collect(l:Loan | l.LoanedCopy)");
		postconditions_map.put("listReservationBook", "result = res->collect(r:Reserve | r.ReservedCopy)");
		postconditions_map.put("listRecommendBook", "result = rBooks");
		postconditions_map.put("recommendBook", "let r:RecommendBook inr.oclIsNew() and\nr.CallNo = callNo and\nr.Title = title and\nr.Edition = edition and\nr.Author = author and\nr.Publisher = publisher and\nr.Description = description and\nr.ISBn = isbn and\nr.RecommendDate = Today and\nr.RecommendUser = user and\nuser.RecommendedBook->includes(r) and\nRecommendBook.allInstance()->includes(r) and\nresult = true\n");
		postconditions_map.put("createStudent", "let u:Student inu.oclIsNew() and\nu.UserID = userID and\nu.Name = name and\nu.Email = email and\nu.Password = password and\nu.Sex = sex and\nu.Faculty = faculty and\nu.LoanedNumber = 0 and\nu.BorrowStatus = BorrowStatus::NORMAL and\nu.SuspensionDays = 0 and\nu.OverDueFee = 0 and\nu.Major = major and\nu.Programme = programme and\nu.RegistrationStatus = registrationStatus and\nUser.allInstance()->includes(u) and\nStudent.allInstance()->includes(u) and\nresult = true\n");
		postconditions_map.put("modifyStudent", "user.UserID = userID and\nuser.Name = name and\nuser.Email = email and\nuser.Password = password and\nuser.Sex = sex and\nuser.Faculty = faculty and\nuser.LoanedNumber = 0 and\nuser.BorrowStatus = BorrowStatus::NORMAL and\nuser.SuspensionDays = 0 and\nuser.OverDueFee = 0 and\nuser.Major = major and\nuser.Programme = programme and\nuser.RegistrationStatus = registrationStatus and\nresult = true\n");
		postconditions_map.put("createFaculty", "let u:Faculty inu.oclIsNew() and\nu.UserID = userID and\nu.Name = name and\nu.Email = email and\nu.Password = password and\nu.Sex = sex and\nu.Faculty = faculty and\nu.LoanedNumber = 0 and\nu.BorrowStatus = BorrowStatus::NORMAL and\nu.SuspensionDays = 0 and\nu.OverDueFee = 0 and\nu.Position = position and\nu.Status = status and\nUser.allInstance()->includes(u) and\nFaculty.allInstance()->includes(u) and\nresult = true\n");
		postconditions_map.put("modifyFaculty", "user.UserID = userID and\nuser.Name = name and\nuser.Email = email and\nuser.Password = password and\nuser.Sex = sex and\nuser.Faculty = faculty and\nuser.LoanedNumber = 0 and\nuser.BorrowStatus = BorrowStatus::NORMAL and\nuser.SuspensionDays = 0 and\nuser.OverDueFee = 0 and\nuser.Position = position and\nuser.Status = status and\nresult = true\n");
		postconditions_map.put("sendNotificationEmail", "result = true");
		postconditions_map.put("createUser", "let use:User inuse.oclIsNew() and\nuse.UserID = userid and\nuse.Name = name and\nuse.Sex = sex and\nuse.Password = password and\nuse.Email = email and\nuse.Faculty = faculty and\nuse.LoanedNumber = loanednumber and\nuse.BorrowStatus = borrowstatus and\nuse.SuspensionDays = suspensiondays and\nuse.OverDueFee = overduefee and\nUser.allInstance()->includes(use) and\nresult = true\n");
		postconditions_map.put("queryUser", "result = user");
		postconditions_map.put("modifyUser", "user.UserID = userid and\nuser.Name = name and\nuser.Sex = sex and\nuser.Password = password and\nuser.Email = email and\nuser.Faculty = faculty and\nuser.LoanedNumber = loanednumber and\nuser.BorrowStatus = borrowstatus and\nuser.SuspensionDays = suspensiondays and\nuser.OverDueFee = overduefee and\nresult = true\n");
		postconditions_map.put("deleteUser", "User.allInstance()->excludes(user) and\nresult = true\n");
		postconditions_map.put("createBook", "let boo:Book inboo.oclIsNew() and\nboo.CallNo = callno and\nboo.Title = title and\nboo.Edition = edition and\nboo.Author = author and\nboo.Publisher = publisher and\nboo.Description = description and\nboo.ISBn = isbn and\nboo.CopyNum = copynum and\nBook.allInstance()->includes(boo) and\nresult = true\n");
		postconditions_map.put("queryBook", "result = book");
		postconditions_map.put("modifyBook", "book.CallNo = callno and\nbook.Title = title and\nbook.Edition = edition and\nbook.Author = author and\nbook.Publisher = publisher and\nbook.Description = description and\nbook.ISBn = isbn and\nbook.CopyNum = copynum and\nresult = true\n");
		postconditions_map.put("deleteBook", "Book.allInstance()->excludes(book) and\nresult = true\n");
		postconditions_map.put("createSubject", "let sub:Subject insub.oclIsNew() and\nsub.Name = name and\nSubject.allInstance()->includes(sub) and\nresult = true\n");
		postconditions_map.put("querySubject", "result = subject");
		postconditions_map.put("modifySubject", "subject.Name = name and\nresult = true\n");
		postconditions_map.put("deleteSubject", "Subject.allInstance()->excludes(subject) and\nresult = true\n");
		postconditions_map.put("addBookCopy", "let copy:BookCopy incopy.oclIsNew() and\ncopy.Barcode = barcode and\ncopy.Status = CopyStatus::AVAILABLE and\ncopy.Location = location and\ncopy.IsReserved = false and\nbook.Copys->includes(copy) and\ncopy.BookBelongs = book and\nbook.CopyNum = book.CopyNum@pre + 1 and\nBookCopy.allInstance()->includes(copy) and\nresult = true\n");
		postconditions_map.put("queryBookCopy", "result = bookcopy");
		postconditions_map.put("modifyBookCopy", "bookcopy.Barcode = barcode and\nbookcopy.Status = status and\nbookcopy.Location = location and\nbookcopy.IsReserved = isreserved and\nresult = true\n");
		postconditions_map.put("deleteBookCopy", "BookCopy.allInstance()->excludes(bookcopy) and\nresult = true\n");
		postconditions_map.put("createLibrarian", "let lib:Librarian inlib.oclIsNew() and\nlib.LibrarianID = librarianid and\nlib.Name = name and\nlib.Password = password and\nLibrarian.allInstance()->includes(lib) and\nresult = true\n");
		postconditions_map.put("queryLibrarian", "result = librarian");
		postconditions_map.put("modifyLibrarian", "librarian.LibrarianID = librarianid and\nlibrarian.Name = name and\nlibrarian.Password = password and\nresult = true\n");
		postconditions_map.put("deleteLibrarian", "Librarian.allInstance()->excludes(librarian) and\nresult = true\n");
		postconditions_map.put("inputAccount", "true");
		postconditions_map.put("inputPassword", "true");
		postconditions_map.put("inputAccount1", "true");
		postconditions_map.put("inputPasswordNew", "true");
		postconditions_map.put("inputPasswordNew", "true");
		postconditions_map.put("ensurePassword1", "true");
		postconditions_map.put("inputPassword1", "true");
		postconditions_map.put("inputCard1", "true");
		postconditions_map.put("inputPassword2", "true");
		postconditions_map.put("inputVerificationcode", "true");
		postconditions_map.put("inputCard12", "true");
		postconditions_map.put("inputPassword11", "true");
		postconditions_map.put("ensureDeleteAccount", "true");
		postconditions_map.put("inputBookInformation", "true");
		postconditions_map.put("inputCardID", "true");
		postconditions_map.put("exitStytem", "true");
		
		//service invariants map
		service_invariants_map = new LinkedHashMap<String, String>();
		
		//entity invariants map
		entity_invariants_map = new LinkedHashMap<String, String>();
		entity_invariants_map.put("User_UniqueUserID"," User . allInstance() -> isUnique ( u : User | u . UserID )");
		entity_invariants_map.put("User_OverDueFeeGreatThanEqualZero"," OverDueFee >= 0");
		entity_invariants_map.put("User_LoanedNumberGreatThanEqualZero"," LoanedNumber >= 0");
		entity_invariants_map.put("User_SuspensionDaysGreatThanEqualZero"," SuspensionDays >= 0");
		entity_invariants_map.put("Student_StudentLoanLessThanEqualTwelve"," Student . allInstance() -> forAll ( stu : Student | stu . LoanedNumber <= 12 )");
		entity_invariants_map.put("Student_StudentLoanedBookAssociationInvariants"," super . LoanedBook . size() <= 12");
		entity_invariants_map.put("Faculty_FacultyLoanLessthanEqualTwentyFour"," Student . allInstance() -> forAll ( stu : Student | stu . LoanedNumber <= 24 )");
		entity_invariants_map.put("Faculty_FacultyLoanedBookAssociationInvariants"," super . LoanedBook . size() <= 24");
		entity_invariants_map.put("Book_BookCallNoUnique"," Book . allInstance() -> isUnique ( b : Book | b . CallNo )");
		entity_invariants_map.put("Book_BookISBNUnique"," Book . allInstance() -> isUnique ( b : Book | b . ISBn )");
		entity_invariants_map.put("Book_BookCopyNumGreatThanEqualZero"," CopyNum >= 0");
		entity_invariants_map.put("BookCopy_BarCodeUnique"," BookCopy . allInstance() -> isUnique ( bc : BookCopy | bc . Barcode )");
		entity_invariants_map.put("Loan_OverDueFeeGreatThanEqualZero"," OverDueFee >= 0");
		entity_invariants_map.put("Loan_RenewedTimesLessThanEqualSix","  RenewedTimes >=0 and RenewedTimes <= 6");
		entity_invariants_map.put("Loan_LoanOverDueFeeGreatThanEqualZero"," OverDueFee >= 0");
		entity_invariants_map.put("Loan_RenewDataAfterLoanDate","if ( RenewDate . oclIsUndefined() = false ) then RenewDate . isAfter ( LoanDate ) endif");
		entity_invariants_map.put("Loan_DueDateAfterLoanDate"," DueDate . isAfter ( LoanDate )");
		entity_invariants_map.put("Loan_ReturnDateAfterORSameLoanDate","if ( ReturnDate . oclIsUndefined() = false ) then ReturnDate . isAfter ( LoanDate ) or ReturnDate . isEqual ( LoanDate ) endif");
		entity_invariants_map.put("Loan_DueDateAfterORSameRenewDate","if ( RenewDate . oclIsUndefined() = false ) then DueDate . isAfter ( RenewDate ) or DueDate . isEqual ( RenewDate ) endif");
		entity_invariants_map.put("Loan_ReturnDateSameORAfterRenewDate","if ( RenewDate . oclIsUndefined() = false and ReturnDate . oclIsUndefined() = false ) then ReturnDate . isAfter ( RenewDate ) or ReturnDate . isEqual ( RenewDate ) endif");
		entity_invariants_map.put("RecommendBook_BookCallNoUnique"," Book . allInstance() -> isUnique ( b : Book | b . CallNo )");
		entity_invariants_map.put("RecommendBook_BookISBNUnique"," Book . allInstance() -> isUnique ( b : Book | b . ISBn )");
		entity_invariants_map.put("RecommendBook_BookCopyNumGreatThanEqualZero"," super . CopyNum >= 0");
		entity_invariants_map.put("Administrator_AdministratorIDUnique"," Administrator . allInstance() -> isUnique ( a : Administrator | a . AdminID )");
		entity_invariants_map.put("Librarian_LibrarianIDUnique"," Librarian . allInstance() -> isUnique ( l : Librarian | l . LibrarianID )");
		
	}
	
	public void generatOperationPane() {
		
		 operationPanels = new LinkedHashMap<String, GridPane>();
		
		 // ==================== GridPane_registerAccount ====================
		 GridPane registerAccount = new GridPane();
		 registerAccount.setHgap(4);
		 registerAccount.setVgap(6);
		 registerAccount.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> registerAccount_content = registerAccount.getChildren();
		 Label registerAccount_userID_label = new Label("userID:");
		 registerAccount_userID_label.setMinWidth(Region.USE_PREF_SIZE);
		 registerAccount_content.add(registerAccount_userID_label);
		 GridPane.setConstraints(registerAccount_userID_label, 0, 0);
		 
		 registerAccount_userID_t = new TextField();
		 registerAccount_content.add(registerAccount_userID_t);
		 registerAccount_userID_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(registerAccount_userID_t, 1, 0);
		 Label registerAccount_email_label = new Label("email:");
		 registerAccount_email_label.setMinWidth(Region.USE_PREF_SIZE);
		 registerAccount_content.add(registerAccount_email_label);
		 GridPane.setConstraints(registerAccount_email_label, 0, 1);
		 
		 registerAccount_email_t = new TextField();
		 registerAccount_content.add(registerAccount_email_t);
		 registerAccount_email_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(registerAccount_email_t, 1, 1);
		 Label registerAccount_password_label = new Label("password:");
		 registerAccount_password_label.setMinWidth(Region.USE_PREF_SIZE);
		 registerAccount_content.add(registerAccount_password_label);
		 GridPane.setConstraints(registerAccount_password_label, 0, 2);
		 
		 registerAccount_password_t = new TextField();
		 registerAccount_content.add(registerAccount_password_t);
		 registerAccount_password_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(registerAccount_password_t, 1, 2);
		 operationPanels.put("registerAccount", registerAccount);
		 
		 // ==================== GridPane_searchBookByBarCode ====================
		 GridPane searchBookByBarCode = new GridPane();
		 searchBookByBarCode.setHgap(4);
		 searchBookByBarCode.setVgap(6);
		 searchBookByBarCode.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> searchBookByBarCode_content = searchBookByBarCode.getChildren();
		 Label searchBookByBarCode_barcode_label = new Label("barcode:");
		 searchBookByBarCode_barcode_label.setMinWidth(Region.USE_PREF_SIZE);
		 searchBookByBarCode_content.add(searchBookByBarCode_barcode_label);
		 GridPane.setConstraints(searchBookByBarCode_barcode_label, 0, 0);
		 
		 searchBookByBarCode_barcode_t = new TextField();
		 searchBookByBarCode_content.add(searchBookByBarCode_barcode_t);
		 searchBookByBarCode_barcode_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(searchBookByBarCode_barcode_t, 1, 0);
		 operationPanels.put("searchBookByBarCode", searchBookByBarCode);
		 
		 // ==================== GridPane_searchBookByTitle ====================
		 GridPane searchBookByTitle = new GridPane();
		 searchBookByTitle.setHgap(4);
		 searchBookByTitle.setVgap(6);
		 searchBookByTitle.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> searchBookByTitle_content = searchBookByTitle.getChildren();
		 Label searchBookByTitle_title_label = new Label("title:");
		 searchBookByTitle_title_label.setMinWidth(Region.USE_PREF_SIZE);
		 searchBookByTitle_content.add(searchBookByTitle_title_label);
		 GridPane.setConstraints(searchBookByTitle_title_label, 0, 0);
		 
		 searchBookByTitle_title_t = new TextField();
		 searchBookByTitle_content.add(searchBookByTitle_title_t);
		 searchBookByTitle_title_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(searchBookByTitle_title_t, 1, 0);
		 operationPanels.put("searchBookByTitle", searchBookByTitle);
		 
		 // ==================== GridPane_searchBookByAuthor ====================
		 GridPane searchBookByAuthor = new GridPane();
		 searchBookByAuthor.setHgap(4);
		 searchBookByAuthor.setVgap(6);
		 searchBookByAuthor.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> searchBookByAuthor_content = searchBookByAuthor.getChildren();
		 Label searchBookByAuthor_authorname_label = new Label("authorname:");
		 searchBookByAuthor_authorname_label.setMinWidth(Region.USE_PREF_SIZE);
		 searchBookByAuthor_content.add(searchBookByAuthor_authorname_label);
		 GridPane.setConstraints(searchBookByAuthor_authorname_label, 0, 0);
		 
		 searchBookByAuthor_authorname_t = new TextField();
		 searchBookByAuthor_content.add(searchBookByAuthor_authorname_t);
		 searchBookByAuthor_authorname_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(searchBookByAuthor_authorname_t, 1, 0);
		 operationPanels.put("searchBookByAuthor", searchBookByAuthor);
		 
		 // ==================== GridPane_searchBookByISBN ====================
		 GridPane searchBookByISBN = new GridPane();
		 searchBookByISBN.setHgap(4);
		 searchBookByISBN.setVgap(6);
		 searchBookByISBN.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> searchBookByISBN_content = searchBookByISBN.getChildren();
		 Label searchBookByISBN_iSBNnumber_label = new Label("iSBNnumber:");
		 searchBookByISBN_iSBNnumber_label.setMinWidth(Region.USE_PREF_SIZE);
		 searchBookByISBN_content.add(searchBookByISBN_iSBNnumber_label);
		 GridPane.setConstraints(searchBookByISBN_iSBNnumber_label, 0, 0);
		 
		 searchBookByISBN_iSBNnumber_t = new TextField();
		 searchBookByISBN_content.add(searchBookByISBN_iSBNnumber_t);
		 searchBookByISBN_iSBNnumber_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(searchBookByISBN_iSBNnumber_t, 1, 0);
		 operationPanels.put("searchBookByISBN", searchBookByISBN);
		 
		 // ==================== GridPane_searchBookBySubject ====================
		 GridPane searchBookBySubject = new GridPane();
		 searchBookBySubject.setHgap(4);
		 searchBookBySubject.setVgap(6);
		 searchBookBySubject.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> searchBookBySubject_content = searchBookBySubject.getChildren();
		 Label searchBookBySubject_subject_label = new Label("subject:");
		 searchBookBySubject_subject_label.setMinWidth(Region.USE_PREF_SIZE);
		 searchBookBySubject_content.add(searchBookBySubject_subject_label);
		 GridPane.setConstraints(searchBookBySubject_subject_label, 0, 0);
		 
		 searchBookBySubject_subject_t = new TextField();
		 searchBookBySubject_content.add(searchBookBySubject_subject_t);
		 searchBookBySubject_subject_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(searchBookBySubject_subject_t, 1, 0);
		 operationPanels.put("searchBookBySubject", searchBookBySubject);
		 
		 // ==================== GridPane_makeReservation ====================
		 GridPane makeReservation = new GridPane();
		 makeReservation.setHgap(4);
		 makeReservation.setVgap(6);
		 makeReservation.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> makeReservation_content = makeReservation.getChildren();
		 Label makeReservation_uid_label = new Label("uid:");
		 makeReservation_uid_label.setMinWidth(Region.USE_PREF_SIZE);
		 makeReservation_content.add(makeReservation_uid_label);
		 GridPane.setConstraints(makeReservation_uid_label, 0, 0);
		 
		 makeReservation_uid_t = new TextField();
		 makeReservation_content.add(makeReservation_uid_t);
		 makeReservation_uid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(makeReservation_uid_t, 1, 0);
		 Label makeReservation_barcode_label = new Label("barcode:");
		 makeReservation_barcode_label.setMinWidth(Region.USE_PREF_SIZE);
		 makeReservation_content.add(makeReservation_barcode_label);
		 GridPane.setConstraints(makeReservation_barcode_label, 0, 1);
		 
		 makeReservation_barcode_t = new TextField();
		 makeReservation_content.add(makeReservation_barcode_t);
		 makeReservation_barcode_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(makeReservation_barcode_t, 1, 1);
		 operationPanels.put("makeReservation", makeReservation);
		 
		 // ==================== GridPane_cancelReservation ====================
		 GridPane cancelReservation = new GridPane();
		 cancelReservation.setHgap(4);
		 cancelReservation.setVgap(6);
		 cancelReservation.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> cancelReservation_content = cancelReservation.getChildren();
		 Label cancelReservation_uid_label = new Label("uid:");
		 cancelReservation_uid_label.setMinWidth(Region.USE_PREF_SIZE);
		 cancelReservation_content.add(cancelReservation_uid_label);
		 GridPane.setConstraints(cancelReservation_uid_label, 0, 0);
		 
		 cancelReservation_uid_t = new TextField();
		 cancelReservation_content.add(cancelReservation_uid_t);
		 cancelReservation_uid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(cancelReservation_uid_t, 1, 0);
		 Label cancelReservation_barcode_label = new Label("barcode:");
		 cancelReservation_barcode_label.setMinWidth(Region.USE_PREF_SIZE);
		 cancelReservation_content.add(cancelReservation_barcode_label);
		 GridPane.setConstraints(cancelReservation_barcode_label, 0, 1);
		 
		 cancelReservation_barcode_t = new TextField();
		 cancelReservation_content.add(cancelReservation_barcode_t);
		 cancelReservation_barcode_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(cancelReservation_barcode_t, 1, 1);
		 operationPanels.put("cancelReservation", cancelReservation);
		 
		 // ==================== GridPane_borrowBook ====================
		 GridPane borrowBook = new GridPane();
		 borrowBook.setHgap(4);
		 borrowBook.setVgap(6);
		 borrowBook.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> borrowBook_content = borrowBook.getChildren();
		 Label borrowBook_uid_label = new Label("uid:");
		 borrowBook_uid_label.setMinWidth(Region.USE_PREF_SIZE);
		 borrowBook_content.add(borrowBook_uid_label);
		 GridPane.setConstraints(borrowBook_uid_label, 0, 0);
		 
		 borrowBook_uid_t = new TextField();
		 borrowBook_content.add(borrowBook_uid_t);
		 borrowBook_uid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(borrowBook_uid_t, 1, 0);
		 Label borrowBook_barcode_label = new Label("barcode:");
		 borrowBook_barcode_label.setMinWidth(Region.USE_PREF_SIZE);
		 borrowBook_content.add(borrowBook_barcode_label);
		 GridPane.setConstraints(borrowBook_barcode_label, 0, 1);
		 
		 borrowBook_barcode_t = new TextField();
		 borrowBook_content.add(borrowBook_barcode_t);
		 borrowBook_barcode_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(borrowBook_barcode_t, 1, 1);
		 operationPanels.put("borrowBook", borrowBook);
		 
		 // ==================== GridPane_returnBook ====================
		 GridPane returnBook = new GridPane();
		 returnBook.setHgap(4);
		 returnBook.setVgap(6);
		 returnBook.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> returnBook_content = returnBook.getChildren();
		 Label returnBook_barcode_label = new Label("barcode:");
		 returnBook_barcode_label.setMinWidth(Region.USE_PREF_SIZE);
		 returnBook_content.add(returnBook_barcode_label);
		 GridPane.setConstraints(returnBook_barcode_label, 0, 0);
		 
		 returnBook_barcode_t = new TextField();
		 returnBook_content.add(returnBook_barcode_t);
		 returnBook_barcode_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(returnBook_barcode_t, 1, 0);
		 operationPanels.put("returnBook", returnBook);
		 
		 // ==================== GridPane_renewBook ====================
		 GridPane renewBook = new GridPane();
		 renewBook.setHgap(4);
		 renewBook.setVgap(6);
		 renewBook.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> renewBook_content = renewBook.getChildren();
		 Label renewBook_uid_label = new Label("uid:");
		 renewBook_uid_label.setMinWidth(Region.USE_PREF_SIZE);
		 renewBook_content.add(renewBook_uid_label);
		 GridPane.setConstraints(renewBook_uid_label, 0, 0);
		 
		 renewBook_uid_t = new TextField();
		 renewBook_content.add(renewBook_uid_t);
		 renewBook_uid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(renewBook_uid_t, 1, 0);
		 Label renewBook_barcode_label = new Label("barcode:");
		 renewBook_barcode_label.setMinWidth(Region.USE_PREF_SIZE);
		 renewBook_content.add(renewBook_barcode_label);
		 GridPane.setConstraints(renewBook_barcode_label, 0, 1);
		 
		 renewBook_barcode_t = new TextField();
		 renewBook_content.add(renewBook_barcode_t);
		 renewBook_barcode_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(renewBook_barcode_t, 1, 1);
		 operationPanels.put("renewBook", renewBook);
		 
		 // ==================== GridPane_payOverDueFee ====================
		 GridPane payOverDueFee = new GridPane();
		 payOverDueFee.setHgap(4);
		 payOverDueFee.setVgap(6);
		 payOverDueFee.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> payOverDueFee_content = payOverDueFee.getChildren();
		 Label payOverDueFee_uid_label = new Label("uid:");
		 payOverDueFee_uid_label.setMinWidth(Region.USE_PREF_SIZE);
		 payOverDueFee_content.add(payOverDueFee_uid_label);
		 GridPane.setConstraints(payOverDueFee_uid_label, 0, 0);
		 
		 payOverDueFee_uid_t = new TextField();
		 payOverDueFee_content.add(payOverDueFee_uid_t);
		 payOverDueFee_uid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(payOverDueFee_uid_t, 1, 0);
		 Label payOverDueFee_fee_label = new Label("fee:");
		 payOverDueFee_fee_label.setMinWidth(Region.USE_PREF_SIZE);
		 payOverDueFee_content.add(payOverDueFee_fee_label);
		 GridPane.setConstraints(payOverDueFee_fee_label, 0, 1);
		 
		 payOverDueFee_fee_t = new TextField();
		 payOverDueFee_content.add(payOverDueFee_fee_t);
		 payOverDueFee_fee_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(payOverDueFee_fee_t, 1, 1);
		 operationPanels.put("payOverDueFee", payOverDueFee);
		 
		 // ==================== GridPane_dueSoonNotification ====================
		 GridPane dueSoonNotification = new GridPane();
		 dueSoonNotification.setHgap(4);
		 dueSoonNotification.setVgap(6);
		 dueSoonNotification.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> dueSoonNotification_content = dueSoonNotification.getChildren();
		 Label dueSoonNotification_label = new Label("This operation is no intput parameters..");
		 dueSoonNotification_label.setMinWidth(Region.USE_PREF_SIZE);
		 dueSoonNotification_content.add(dueSoonNotification_label);
		 GridPane.setConstraints(dueSoonNotification_label, 0, 0);
		 operationPanels.put("dueSoonNotification", dueSoonNotification);
		 
		 // ==================== GridPane_checkOverDueandComputeOverDueFee ====================
		 GridPane checkOverDueandComputeOverDueFee = new GridPane();
		 checkOverDueandComputeOverDueFee.setHgap(4);
		 checkOverDueandComputeOverDueFee.setVgap(6);
		 checkOverDueandComputeOverDueFee.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> checkOverDueandComputeOverDueFee_content = checkOverDueandComputeOverDueFee.getChildren();
		 Label checkOverDueandComputeOverDueFee_label = new Label("This operation is no intput parameters..");
		 checkOverDueandComputeOverDueFee_label.setMinWidth(Region.USE_PREF_SIZE);
		 checkOverDueandComputeOverDueFee_content.add(checkOverDueandComputeOverDueFee_label);
		 GridPane.setConstraints(checkOverDueandComputeOverDueFee_label, 0, 0);
		 operationPanels.put("checkOverDueandComputeOverDueFee", checkOverDueandComputeOverDueFee);
		 
		 // ==================== GridPane_countDownSuspensionDay ====================
		 GridPane countDownSuspensionDay = new GridPane();
		 countDownSuspensionDay.setHgap(4);
		 countDownSuspensionDay.setVgap(6);
		 countDownSuspensionDay.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> countDownSuspensionDay_content = countDownSuspensionDay.getChildren();
		 Label countDownSuspensionDay_label = new Label("This operation is no intput parameters..");
		 countDownSuspensionDay_label.setMinWidth(Region.USE_PREF_SIZE);
		 countDownSuspensionDay_content.add(countDownSuspensionDay_label);
		 GridPane.setConstraints(countDownSuspensionDay_label, 0, 0);
		 operationPanels.put("countDownSuspensionDay", countDownSuspensionDay);
		 
		 // ==================== GridPane_listBorrowHistory ====================
		 GridPane listBorrowHistory = new GridPane();
		 listBorrowHistory.setHgap(4);
		 listBorrowHistory.setVgap(6);
		 listBorrowHistory.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> listBorrowHistory_content = listBorrowHistory.getChildren();
		 Label listBorrowHistory_uid_label = new Label("uid:");
		 listBorrowHistory_uid_label.setMinWidth(Region.USE_PREF_SIZE);
		 listBorrowHistory_content.add(listBorrowHistory_uid_label);
		 GridPane.setConstraints(listBorrowHistory_uid_label, 0, 0);
		 
		 listBorrowHistory_uid_t = new TextField();
		 listBorrowHistory_content.add(listBorrowHistory_uid_t);
		 listBorrowHistory_uid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(listBorrowHistory_uid_t, 1, 0);
		 operationPanels.put("listBorrowHistory", listBorrowHistory);
		 
		 // ==================== GridPane_listHodingBook ====================
		 GridPane listHodingBook = new GridPane();
		 listHodingBook.setHgap(4);
		 listHodingBook.setVgap(6);
		 listHodingBook.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> listHodingBook_content = listHodingBook.getChildren();
		 Label listHodingBook_uid_label = new Label("uid:");
		 listHodingBook_uid_label.setMinWidth(Region.USE_PREF_SIZE);
		 listHodingBook_content.add(listHodingBook_uid_label);
		 GridPane.setConstraints(listHodingBook_uid_label, 0, 0);
		 
		 listHodingBook_uid_t = new TextField();
		 listHodingBook_content.add(listHodingBook_uid_t);
		 listHodingBook_uid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(listHodingBook_uid_t, 1, 0);
		 operationPanels.put("listHodingBook", listHodingBook);
		 
		 // ==================== GridPane_listOverDueBook ====================
		 GridPane listOverDueBook = new GridPane();
		 listOverDueBook.setHgap(4);
		 listOverDueBook.setVgap(6);
		 listOverDueBook.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> listOverDueBook_content = listOverDueBook.getChildren();
		 Label listOverDueBook_uid_label = new Label("uid:");
		 listOverDueBook_uid_label.setMinWidth(Region.USE_PREF_SIZE);
		 listOverDueBook_content.add(listOverDueBook_uid_label);
		 GridPane.setConstraints(listOverDueBook_uid_label, 0, 0);
		 
		 listOverDueBook_uid_t = new TextField();
		 listOverDueBook_content.add(listOverDueBook_uid_t);
		 listOverDueBook_uid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(listOverDueBook_uid_t, 1, 0);
		 operationPanels.put("listOverDueBook", listOverDueBook);
		 
		 // ==================== GridPane_listReservationBook ====================
		 GridPane listReservationBook = new GridPane();
		 listReservationBook.setHgap(4);
		 listReservationBook.setVgap(6);
		 listReservationBook.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> listReservationBook_content = listReservationBook.getChildren();
		 Label listReservationBook_uid_label = new Label("uid:");
		 listReservationBook_uid_label.setMinWidth(Region.USE_PREF_SIZE);
		 listReservationBook_content.add(listReservationBook_uid_label);
		 GridPane.setConstraints(listReservationBook_uid_label, 0, 0);
		 
		 listReservationBook_uid_t = new TextField();
		 listReservationBook_content.add(listReservationBook_uid_t);
		 listReservationBook_uid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(listReservationBook_uid_t, 1, 0);
		 operationPanels.put("listReservationBook", listReservationBook);
		 
		 // ==================== GridPane_listRecommendBook ====================
		 GridPane listRecommendBook = new GridPane();
		 listRecommendBook.setHgap(4);
		 listRecommendBook.setVgap(6);
		 listRecommendBook.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> listRecommendBook_content = listRecommendBook.getChildren();
		 Label listRecommendBook_uid_label = new Label("uid:");
		 listRecommendBook_uid_label.setMinWidth(Region.USE_PREF_SIZE);
		 listRecommendBook_content.add(listRecommendBook_uid_label);
		 GridPane.setConstraints(listRecommendBook_uid_label, 0, 0);
		 
		 listRecommendBook_uid_t = new TextField();
		 listRecommendBook_content.add(listRecommendBook_uid_t);
		 listRecommendBook_uid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(listRecommendBook_uid_t, 1, 0);
		 operationPanels.put("listRecommendBook", listRecommendBook);
		 
		 // ==================== GridPane_recommendBook ====================
		 GridPane recommendBook = new GridPane();
		 recommendBook.setHgap(4);
		 recommendBook.setVgap(6);
		 recommendBook.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> recommendBook_content = recommendBook.getChildren();
		 Label recommendBook_uid_label = new Label("uid:");
		 recommendBook_uid_label.setMinWidth(Region.USE_PREF_SIZE);
		 recommendBook_content.add(recommendBook_uid_label);
		 GridPane.setConstraints(recommendBook_uid_label, 0, 0);
		 
		 recommendBook_uid_t = new TextField();
		 recommendBook_content.add(recommendBook_uid_t);
		 recommendBook_uid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(recommendBook_uid_t, 1, 0);
		 Label recommendBook_callNo_label = new Label("callNo:");
		 recommendBook_callNo_label.setMinWidth(Region.USE_PREF_SIZE);
		 recommendBook_content.add(recommendBook_callNo_label);
		 GridPane.setConstraints(recommendBook_callNo_label, 0, 1);
		 
		 recommendBook_callNo_t = new TextField();
		 recommendBook_content.add(recommendBook_callNo_t);
		 recommendBook_callNo_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(recommendBook_callNo_t, 1, 1);
		 Label recommendBook_title_label = new Label("title:");
		 recommendBook_title_label.setMinWidth(Region.USE_PREF_SIZE);
		 recommendBook_content.add(recommendBook_title_label);
		 GridPane.setConstraints(recommendBook_title_label, 0, 2);
		 
		 recommendBook_title_t = new TextField();
		 recommendBook_content.add(recommendBook_title_t);
		 recommendBook_title_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(recommendBook_title_t, 1, 2);
		 Label recommendBook_edition_label = new Label("edition:");
		 recommendBook_edition_label.setMinWidth(Region.USE_PREF_SIZE);
		 recommendBook_content.add(recommendBook_edition_label);
		 GridPane.setConstraints(recommendBook_edition_label, 0, 3);
		 
		 recommendBook_edition_t = new TextField();
		 recommendBook_content.add(recommendBook_edition_t);
		 recommendBook_edition_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(recommendBook_edition_t, 1, 3);
		 Label recommendBook_author_label = new Label("author:");
		 recommendBook_author_label.setMinWidth(Region.USE_PREF_SIZE);
		 recommendBook_content.add(recommendBook_author_label);
		 GridPane.setConstraints(recommendBook_author_label, 0, 4);
		 
		 recommendBook_author_t = new TextField();
		 recommendBook_content.add(recommendBook_author_t);
		 recommendBook_author_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(recommendBook_author_t, 1, 4);
		 Label recommendBook_publisher_label = new Label("publisher:");
		 recommendBook_publisher_label.setMinWidth(Region.USE_PREF_SIZE);
		 recommendBook_content.add(recommendBook_publisher_label);
		 GridPane.setConstraints(recommendBook_publisher_label, 0, 5);
		 
		 recommendBook_publisher_t = new TextField();
		 recommendBook_content.add(recommendBook_publisher_t);
		 recommendBook_publisher_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(recommendBook_publisher_t, 1, 5);
		 Label recommendBook_description_label = new Label("description:");
		 recommendBook_description_label.setMinWidth(Region.USE_PREF_SIZE);
		 recommendBook_content.add(recommendBook_description_label);
		 GridPane.setConstraints(recommendBook_description_label, 0, 6);
		 
		 recommendBook_description_t = new TextField();
		 recommendBook_content.add(recommendBook_description_t);
		 recommendBook_description_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(recommendBook_description_t, 1, 6);
		 Label recommendBook_isbn_label = new Label("isbn:");
		 recommendBook_isbn_label.setMinWidth(Region.USE_PREF_SIZE);
		 recommendBook_content.add(recommendBook_isbn_label);
		 GridPane.setConstraints(recommendBook_isbn_label, 0, 7);
		 
		 recommendBook_isbn_t = new TextField();
		 recommendBook_content.add(recommendBook_isbn_t);
		 recommendBook_isbn_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(recommendBook_isbn_t, 1, 7);
		 operationPanels.put("recommendBook", recommendBook);
		 
		 // ==================== GridPane_createStudent ====================
		 GridPane createStudent = new GridPane();
		 createStudent.setHgap(4);
		 createStudent.setVgap(6);
		 createStudent.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createStudent_content = createStudent.getChildren();
		 Label createStudent_userID_label = new Label("userID:");
		 createStudent_userID_label.setMinWidth(Region.USE_PREF_SIZE);
		 createStudent_content.add(createStudent_userID_label);
		 GridPane.setConstraints(createStudent_userID_label, 0, 0);
		 
		 createStudent_userID_t = new TextField();
		 createStudent_content.add(createStudent_userID_t);
		 createStudent_userID_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createStudent_userID_t, 1, 0);
		 Label createStudent_name_label = new Label("name:");
		 createStudent_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 createStudent_content.add(createStudent_name_label);
		 GridPane.setConstraints(createStudent_name_label, 0, 1);
		 
		 createStudent_name_t = new TextField();
		 createStudent_content.add(createStudent_name_t);
		 createStudent_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createStudent_name_t, 1, 1);
		 Label createStudent_sex_label = new Label("sex:");
		 createStudent_sex_label.setMinWidth(Region.USE_PREF_SIZE);
		 createStudent_content.add(createStudent_sex_label);
		 GridPane.setConstraints(createStudent_sex_label, 0, 2);
		 
		 createStudent_sex_cb = new ChoiceBox();
createStudent_sex_cb.getItems().add("M");
createStudent_sex_cb.getItems().add("F");
		 createStudent_sex_cb.getSelectionModel().selectFirst();
		 createStudent_content.add(createStudent_sex_cb);
		 GridPane.setConstraints(createStudent_sex_cb, 1, 2);
		 Label createStudent_password_label = new Label("password:");
		 createStudent_password_label.setMinWidth(Region.USE_PREF_SIZE);
		 createStudent_content.add(createStudent_password_label);
		 GridPane.setConstraints(createStudent_password_label, 0, 3);
		 
		 createStudent_password_t = new TextField();
		 createStudent_content.add(createStudent_password_t);
		 createStudent_password_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createStudent_password_t, 1, 3);
		 Label createStudent_email_label = new Label("email:");
		 createStudent_email_label.setMinWidth(Region.USE_PREF_SIZE);
		 createStudent_content.add(createStudent_email_label);
		 GridPane.setConstraints(createStudent_email_label, 0, 4);
		 
		 createStudent_email_t = new TextField();
		 createStudent_content.add(createStudent_email_t);
		 createStudent_email_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createStudent_email_t, 1, 4);
		 Label createStudent_faculty_label = new Label("faculty:");
		 createStudent_faculty_label.setMinWidth(Region.USE_PREF_SIZE);
		 createStudent_content.add(createStudent_faculty_label);
		 GridPane.setConstraints(createStudent_faculty_label, 0, 5);
		 
		 createStudent_faculty_t = new TextField();
		 createStudent_content.add(createStudent_faculty_t);
		 createStudent_faculty_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createStudent_faculty_t, 1, 5);
		 Label createStudent_major_label = new Label("major:");
		 createStudent_major_label.setMinWidth(Region.USE_PREF_SIZE);
		 createStudent_content.add(createStudent_major_label);
		 GridPane.setConstraints(createStudent_major_label, 0, 6);
		 
		 createStudent_major_t = new TextField();
		 createStudent_content.add(createStudent_major_t);
		 createStudent_major_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createStudent_major_t, 1, 6);
		 Label createStudent_programme_label = new Label("programme:");
		 createStudent_programme_label.setMinWidth(Region.USE_PREF_SIZE);
		 createStudent_content.add(createStudent_programme_label);
		 GridPane.setConstraints(createStudent_programme_label, 0, 7);
		 
		 createStudent_programme_cb = new ChoiceBox();
createStudent_programme_cb.getItems().add("BACHELOR");
createStudent_programme_cb.getItems().add("MASTER");
createStudent_programme_cb.getItems().add("PHD");
		 createStudent_programme_cb.getSelectionModel().selectFirst();
		 createStudent_content.add(createStudent_programme_cb);
		 GridPane.setConstraints(createStudent_programme_cb, 1, 7);
		 Label createStudent_registrationStatus_label = new Label("registrationStatus:");
		 createStudent_registrationStatus_label.setMinWidth(Region.USE_PREF_SIZE);
		 createStudent_content.add(createStudent_registrationStatus_label);
		 GridPane.setConstraints(createStudent_registrationStatus_label, 0, 8);
		 
		 createStudent_registrationStatus_cb = new ChoiceBox();
createStudent_registrationStatus_cb.getItems().add("GRADUATED");
createStudent_registrationStatus_cb.getItems().add("PROGRAMMING");
		 createStudent_registrationStatus_cb.getSelectionModel().selectFirst();
		 createStudent_content.add(createStudent_registrationStatus_cb);
		 GridPane.setConstraints(createStudent_registrationStatus_cb, 1, 8);
		 operationPanels.put("createStudent", createStudent);
		 
		 // ==================== GridPane_modifyStudent ====================
		 GridPane modifyStudent = new GridPane();
		 modifyStudent.setHgap(4);
		 modifyStudent.setVgap(6);
		 modifyStudent.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifyStudent_content = modifyStudent.getChildren();
		 Label modifyStudent_userID_label = new Label("userID:");
		 modifyStudent_userID_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyStudent_content.add(modifyStudent_userID_label);
		 GridPane.setConstraints(modifyStudent_userID_label, 0, 0);
		 
		 modifyStudent_userID_t = new TextField();
		 modifyStudent_content.add(modifyStudent_userID_t);
		 modifyStudent_userID_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyStudent_userID_t, 1, 0);
		 Label modifyStudent_name_label = new Label("name:");
		 modifyStudent_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyStudent_content.add(modifyStudent_name_label);
		 GridPane.setConstraints(modifyStudent_name_label, 0, 1);
		 
		 modifyStudent_name_t = new TextField();
		 modifyStudent_content.add(modifyStudent_name_t);
		 modifyStudent_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyStudent_name_t, 1, 1);
		 Label modifyStudent_sex_label = new Label("sex:");
		 modifyStudent_sex_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyStudent_content.add(modifyStudent_sex_label);
		 GridPane.setConstraints(modifyStudent_sex_label, 0, 2);
		 
		 modifyStudent_sex_cb = new ChoiceBox();
modifyStudent_sex_cb.getItems().add("M");
modifyStudent_sex_cb.getItems().add("F");
		 modifyStudent_sex_cb.getSelectionModel().selectFirst();
		 modifyStudent_content.add(modifyStudent_sex_cb);
		 GridPane.setConstraints(modifyStudent_sex_cb, 1, 2);
		 Label modifyStudent_password_label = new Label("password:");
		 modifyStudent_password_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyStudent_content.add(modifyStudent_password_label);
		 GridPane.setConstraints(modifyStudent_password_label, 0, 3);
		 
		 modifyStudent_password_t = new TextField();
		 modifyStudent_content.add(modifyStudent_password_t);
		 modifyStudent_password_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyStudent_password_t, 1, 3);
		 Label modifyStudent_email_label = new Label("email:");
		 modifyStudent_email_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyStudent_content.add(modifyStudent_email_label);
		 GridPane.setConstraints(modifyStudent_email_label, 0, 4);
		 
		 modifyStudent_email_t = new TextField();
		 modifyStudent_content.add(modifyStudent_email_t);
		 modifyStudent_email_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyStudent_email_t, 1, 4);
		 Label modifyStudent_faculty_label = new Label("faculty:");
		 modifyStudent_faculty_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyStudent_content.add(modifyStudent_faculty_label);
		 GridPane.setConstraints(modifyStudent_faculty_label, 0, 5);
		 
		 modifyStudent_faculty_t = new TextField();
		 modifyStudent_content.add(modifyStudent_faculty_t);
		 modifyStudent_faculty_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyStudent_faculty_t, 1, 5);
		 Label modifyStudent_major_label = new Label("major:");
		 modifyStudent_major_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyStudent_content.add(modifyStudent_major_label);
		 GridPane.setConstraints(modifyStudent_major_label, 0, 6);
		 
		 modifyStudent_major_t = new TextField();
		 modifyStudent_content.add(modifyStudent_major_t);
		 modifyStudent_major_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyStudent_major_t, 1, 6);
		 Label modifyStudent_programme_label = new Label("programme:");
		 modifyStudent_programme_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyStudent_content.add(modifyStudent_programme_label);
		 GridPane.setConstraints(modifyStudent_programme_label, 0, 7);
		 
		 modifyStudent_programme_cb = new ChoiceBox();
modifyStudent_programme_cb.getItems().add("BACHELOR");
modifyStudent_programme_cb.getItems().add("MASTER");
modifyStudent_programme_cb.getItems().add("PHD");
		 modifyStudent_programme_cb.getSelectionModel().selectFirst();
		 modifyStudent_content.add(modifyStudent_programme_cb);
		 GridPane.setConstraints(modifyStudent_programme_cb, 1, 7);
		 Label modifyStudent_registrationStatus_label = new Label("registrationStatus:");
		 modifyStudent_registrationStatus_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyStudent_content.add(modifyStudent_registrationStatus_label);
		 GridPane.setConstraints(modifyStudent_registrationStatus_label, 0, 8);
		 
		 modifyStudent_registrationStatus_cb = new ChoiceBox();
modifyStudent_registrationStatus_cb.getItems().add("GRADUATED");
modifyStudent_registrationStatus_cb.getItems().add("PROGRAMMING");
		 modifyStudent_registrationStatus_cb.getSelectionModel().selectFirst();
		 modifyStudent_content.add(modifyStudent_registrationStatus_cb);
		 GridPane.setConstraints(modifyStudent_registrationStatus_cb, 1, 8);
		 operationPanels.put("modifyStudent", modifyStudent);
		 
		 // ==================== GridPane_createFaculty ====================
		 GridPane createFaculty = new GridPane();
		 createFaculty.setHgap(4);
		 createFaculty.setVgap(6);
		 createFaculty.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createFaculty_content = createFaculty.getChildren();
		 Label createFaculty_userID_label = new Label("userID:");
		 createFaculty_userID_label.setMinWidth(Region.USE_PREF_SIZE);
		 createFaculty_content.add(createFaculty_userID_label);
		 GridPane.setConstraints(createFaculty_userID_label, 0, 0);
		 
		 createFaculty_userID_t = new TextField();
		 createFaculty_content.add(createFaculty_userID_t);
		 createFaculty_userID_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createFaculty_userID_t, 1, 0);
		 Label createFaculty_name_label = new Label("name:");
		 createFaculty_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 createFaculty_content.add(createFaculty_name_label);
		 GridPane.setConstraints(createFaculty_name_label, 0, 1);
		 
		 createFaculty_name_t = new TextField();
		 createFaculty_content.add(createFaculty_name_t);
		 createFaculty_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createFaculty_name_t, 1, 1);
		 Label createFaculty_sex_label = new Label("sex:");
		 createFaculty_sex_label.setMinWidth(Region.USE_PREF_SIZE);
		 createFaculty_content.add(createFaculty_sex_label);
		 GridPane.setConstraints(createFaculty_sex_label, 0, 2);
		 
		 createFaculty_sex_cb = new ChoiceBox();
createFaculty_sex_cb.getItems().add("M");
createFaculty_sex_cb.getItems().add("F");
		 createFaculty_sex_cb.getSelectionModel().selectFirst();
		 createFaculty_content.add(createFaculty_sex_cb);
		 GridPane.setConstraints(createFaculty_sex_cb, 1, 2);
		 Label createFaculty_password_label = new Label("password:");
		 createFaculty_password_label.setMinWidth(Region.USE_PREF_SIZE);
		 createFaculty_content.add(createFaculty_password_label);
		 GridPane.setConstraints(createFaculty_password_label, 0, 3);
		 
		 createFaculty_password_t = new TextField();
		 createFaculty_content.add(createFaculty_password_t);
		 createFaculty_password_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createFaculty_password_t, 1, 3);
		 Label createFaculty_email_label = new Label("email:");
		 createFaculty_email_label.setMinWidth(Region.USE_PREF_SIZE);
		 createFaculty_content.add(createFaculty_email_label);
		 GridPane.setConstraints(createFaculty_email_label, 0, 4);
		 
		 createFaculty_email_t = new TextField();
		 createFaculty_content.add(createFaculty_email_t);
		 createFaculty_email_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createFaculty_email_t, 1, 4);
		 Label createFaculty_faculty_label = new Label("faculty:");
		 createFaculty_faculty_label.setMinWidth(Region.USE_PREF_SIZE);
		 createFaculty_content.add(createFaculty_faculty_label);
		 GridPane.setConstraints(createFaculty_faculty_label, 0, 5);
		 
		 createFaculty_faculty_t = new TextField();
		 createFaculty_content.add(createFaculty_faculty_t);
		 createFaculty_faculty_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createFaculty_faculty_t, 1, 5);
		 Label createFaculty_position_label = new Label("position:");
		 createFaculty_position_label.setMinWidth(Region.USE_PREF_SIZE);
		 createFaculty_content.add(createFaculty_position_label);
		 GridPane.setConstraints(createFaculty_position_label, 0, 6);
		 
		 createFaculty_position_cb = new ChoiceBox();
createFaculty_position_cb.getItems().add("ASSISTANTPROFESSORS");
createFaculty_position_cb.getItems().add("ASSOCIATEPROFESSOR");
createFaculty_position_cb.getItems().add("PROFESSOR");
createFaculty_position_cb.getItems().add("CHAIRPROFESSOR");
		 createFaculty_position_cb.getSelectionModel().selectFirst();
		 createFaculty_content.add(createFaculty_position_cb);
		 GridPane.setConstraints(createFaculty_position_cb, 1, 6);
		 Label createFaculty_status_label = new Label("status:");
		 createFaculty_status_label.setMinWidth(Region.USE_PREF_SIZE);
		 createFaculty_content.add(createFaculty_status_label);
		 GridPane.setConstraints(createFaculty_status_label, 0, 7);
		 
		 createFaculty_status_cb = new ChoiceBox();
createFaculty_status_cb.getItems().add("HASRETIRED");
createFaculty_status_cb.getItems().add("INPOSITION");
		 createFaculty_status_cb.getSelectionModel().selectFirst();
		 createFaculty_content.add(createFaculty_status_cb);
		 GridPane.setConstraints(createFaculty_status_cb, 1, 7);
		 operationPanels.put("createFaculty", createFaculty);
		 
		 // ==================== GridPane_modifyFaculty ====================
		 GridPane modifyFaculty = new GridPane();
		 modifyFaculty.setHgap(4);
		 modifyFaculty.setVgap(6);
		 modifyFaculty.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifyFaculty_content = modifyFaculty.getChildren();
		 Label modifyFaculty_userID_label = new Label("userID:");
		 modifyFaculty_userID_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyFaculty_content.add(modifyFaculty_userID_label);
		 GridPane.setConstraints(modifyFaculty_userID_label, 0, 0);
		 
		 modifyFaculty_userID_t = new TextField();
		 modifyFaculty_content.add(modifyFaculty_userID_t);
		 modifyFaculty_userID_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyFaculty_userID_t, 1, 0);
		 Label modifyFaculty_name_label = new Label("name:");
		 modifyFaculty_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyFaculty_content.add(modifyFaculty_name_label);
		 GridPane.setConstraints(modifyFaculty_name_label, 0, 1);
		 
		 modifyFaculty_name_t = new TextField();
		 modifyFaculty_content.add(modifyFaculty_name_t);
		 modifyFaculty_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyFaculty_name_t, 1, 1);
		 Label modifyFaculty_sex_label = new Label("sex:");
		 modifyFaculty_sex_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyFaculty_content.add(modifyFaculty_sex_label);
		 GridPane.setConstraints(modifyFaculty_sex_label, 0, 2);
		 
		 modifyFaculty_sex_cb = new ChoiceBox();
modifyFaculty_sex_cb.getItems().add("M");
modifyFaculty_sex_cb.getItems().add("F");
		 modifyFaculty_sex_cb.getSelectionModel().selectFirst();
		 modifyFaculty_content.add(modifyFaculty_sex_cb);
		 GridPane.setConstraints(modifyFaculty_sex_cb, 1, 2);
		 Label modifyFaculty_password_label = new Label("password:");
		 modifyFaculty_password_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyFaculty_content.add(modifyFaculty_password_label);
		 GridPane.setConstraints(modifyFaculty_password_label, 0, 3);
		 
		 modifyFaculty_password_t = new TextField();
		 modifyFaculty_content.add(modifyFaculty_password_t);
		 modifyFaculty_password_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyFaculty_password_t, 1, 3);
		 Label modifyFaculty_email_label = new Label("email:");
		 modifyFaculty_email_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyFaculty_content.add(modifyFaculty_email_label);
		 GridPane.setConstraints(modifyFaculty_email_label, 0, 4);
		 
		 modifyFaculty_email_t = new TextField();
		 modifyFaculty_content.add(modifyFaculty_email_t);
		 modifyFaculty_email_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyFaculty_email_t, 1, 4);
		 Label modifyFaculty_faculty_label = new Label("faculty:");
		 modifyFaculty_faculty_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyFaculty_content.add(modifyFaculty_faculty_label);
		 GridPane.setConstraints(modifyFaculty_faculty_label, 0, 5);
		 
		 modifyFaculty_faculty_t = new TextField();
		 modifyFaculty_content.add(modifyFaculty_faculty_t);
		 modifyFaculty_faculty_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyFaculty_faculty_t, 1, 5);
		 Label modifyFaculty_major_label = new Label("major:");
		 modifyFaculty_major_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyFaculty_content.add(modifyFaculty_major_label);
		 GridPane.setConstraints(modifyFaculty_major_label, 0, 6);
		 
		 modifyFaculty_major_t = new TextField();
		 modifyFaculty_content.add(modifyFaculty_major_t);
		 modifyFaculty_major_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyFaculty_major_t, 1, 6);
		 Label modifyFaculty_position_label = new Label("position:");
		 modifyFaculty_position_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyFaculty_content.add(modifyFaculty_position_label);
		 GridPane.setConstraints(modifyFaculty_position_label, 0, 7);
		 
		 modifyFaculty_position_cb = new ChoiceBox();
modifyFaculty_position_cb.getItems().add("ASSISTANTPROFESSORS");
modifyFaculty_position_cb.getItems().add("ASSOCIATEPROFESSOR");
modifyFaculty_position_cb.getItems().add("PROFESSOR");
modifyFaculty_position_cb.getItems().add("CHAIRPROFESSOR");
		 modifyFaculty_position_cb.getSelectionModel().selectFirst();
		 modifyFaculty_content.add(modifyFaculty_position_cb);
		 GridPane.setConstraints(modifyFaculty_position_cb, 1, 7);
		 Label modifyFaculty_status_label = new Label("status:");
		 modifyFaculty_status_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyFaculty_content.add(modifyFaculty_status_label);
		 GridPane.setConstraints(modifyFaculty_status_label, 0, 8);
		 
		 modifyFaculty_status_cb = new ChoiceBox();
modifyFaculty_status_cb.getItems().add("HASRETIRED");
modifyFaculty_status_cb.getItems().add("INPOSITION");
		 modifyFaculty_status_cb.getSelectionModel().selectFirst();
		 modifyFaculty_content.add(modifyFaculty_status_cb);
		 GridPane.setConstraints(modifyFaculty_status_cb, 1, 8);
		 operationPanels.put("modifyFaculty", modifyFaculty);
		 
		 // ==================== GridPane_sendNotificationEmail ====================
		 GridPane sendNotificationEmail = new GridPane();
		 sendNotificationEmail.setHgap(4);
		 sendNotificationEmail.setVgap(6);
		 sendNotificationEmail.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> sendNotificationEmail_content = sendNotificationEmail.getChildren();
		 Label sendNotificationEmail_email_label = new Label("email:");
		 sendNotificationEmail_email_label.setMinWidth(Region.USE_PREF_SIZE);
		 sendNotificationEmail_content.add(sendNotificationEmail_email_label);
		 GridPane.setConstraints(sendNotificationEmail_email_label, 0, 0);
		 
		 sendNotificationEmail_email_t = new TextField();
		 sendNotificationEmail_content.add(sendNotificationEmail_email_t);
		 sendNotificationEmail_email_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(sendNotificationEmail_email_t, 1, 0);
		 operationPanels.put("sendNotificationEmail", sendNotificationEmail);
		 
		 // ==================== GridPane_createUser ====================
		 GridPane createUser = new GridPane();
		 createUser.setHgap(4);
		 createUser.setVgap(6);
		 createUser.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createUser_content = createUser.getChildren();
		 Label createUser_userid_label = new Label("userid:");
		 createUser_userid_label.setMinWidth(Region.USE_PREF_SIZE);
		 createUser_content.add(createUser_userid_label);
		 GridPane.setConstraints(createUser_userid_label, 0, 0);
		 
		 createUser_userid_t = new TextField();
		 createUser_content.add(createUser_userid_t);
		 createUser_userid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createUser_userid_t, 1, 0);
		 Label createUser_name_label = new Label("name:");
		 createUser_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 createUser_content.add(createUser_name_label);
		 GridPane.setConstraints(createUser_name_label, 0, 1);
		 
		 createUser_name_t = new TextField();
		 createUser_content.add(createUser_name_t);
		 createUser_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createUser_name_t, 1, 1);
		 Label createUser_sex_label = new Label("sex:");
		 createUser_sex_label.setMinWidth(Region.USE_PREF_SIZE);
		 createUser_content.add(createUser_sex_label);
		 GridPane.setConstraints(createUser_sex_label, 0, 2);
		 
		 createUser_sex_cb = new ChoiceBox();
createUser_sex_cb.getItems().add("M");
createUser_sex_cb.getItems().add("F");
		 createUser_sex_cb.getSelectionModel().selectFirst();
		 createUser_content.add(createUser_sex_cb);
		 GridPane.setConstraints(createUser_sex_cb, 1, 2);
		 Label createUser_password_label = new Label("password:");
		 createUser_password_label.setMinWidth(Region.USE_PREF_SIZE);
		 createUser_content.add(createUser_password_label);
		 GridPane.setConstraints(createUser_password_label, 0, 3);
		 
		 createUser_password_t = new TextField();
		 createUser_content.add(createUser_password_t);
		 createUser_password_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createUser_password_t, 1, 3);
		 Label createUser_email_label = new Label("email:");
		 createUser_email_label.setMinWidth(Region.USE_PREF_SIZE);
		 createUser_content.add(createUser_email_label);
		 GridPane.setConstraints(createUser_email_label, 0, 4);
		 
		 createUser_email_t = new TextField();
		 createUser_content.add(createUser_email_t);
		 createUser_email_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createUser_email_t, 1, 4);
		 Label createUser_faculty_label = new Label("faculty:");
		 createUser_faculty_label.setMinWidth(Region.USE_PREF_SIZE);
		 createUser_content.add(createUser_faculty_label);
		 GridPane.setConstraints(createUser_faculty_label, 0, 5);
		 
		 createUser_faculty_t = new TextField();
		 createUser_content.add(createUser_faculty_t);
		 createUser_faculty_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createUser_faculty_t, 1, 5);
		 Label createUser_loanednumber_label = new Label("loanednumber:");
		 createUser_loanednumber_label.setMinWidth(Region.USE_PREF_SIZE);
		 createUser_content.add(createUser_loanednumber_label);
		 GridPane.setConstraints(createUser_loanednumber_label, 0, 6);
		 
		 createUser_loanednumber_t = new TextField();
		 createUser_content.add(createUser_loanednumber_t);
		 createUser_loanednumber_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createUser_loanednumber_t, 1, 6);
		 Label createUser_borrowstatus_label = new Label("borrowstatus:");
		 createUser_borrowstatus_label.setMinWidth(Region.USE_PREF_SIZE);
		 createUser_content.add(createUser_borrowstatus_label);
		 GridPane.setConstraints(createUser_borrowstatus_label, 0, 7);
		 
		 createUser_borrowstatus_cb = new ChoiceBox();
createUser_borrowstatus_cb.getItems().add("NORMAL");
createUser_borrowstatus_cb.getItems().add("SUSPEND");
		 createUser_borrowstatus_cb.getSelectionModel().selectFirst();
		 createUser_content.add(createUser_borrowstatus_cb);
		 GridPane.setConstraints(createUser_borrowstatus_cb, 1, 7);
		 Label createUser_suspensiondays_label = new Label("suspensiondays:");
		 createUser_suspensiondays_label.setMinWidth(Region.USE_PREF_SIZE);
		 createUser_content.add(createUser_suspensiondays_label);
		 GridPane.setConstraints(createUser_suspensiondays_label, 0, 8);
		 
		 createUser_suspensiondays_t = new TextField();
		 createUser_content.add(createUser_suspensiondays_t);
		 createUser_suspensiondays_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createUser_suspensiondays_t, 1, 8);
		 Label createUser_overduefee_label = new Label("overduefee:");
		 createUser_overduefee_label.setMinWidth(Region.USE_PREF_SIZE);
		 createUser_content.add(createUser_overduefee_label);
		 GridPane.setConstraints(createUser_overduefee_label, 0, 9);
		 
		 createUser_overduefee_t = new TextField();
		 createUser_content.add(createUser_overduefee_t);
		 createUser_overduefee_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createUser_overduefee_t, 1, 9);
		 operationPanels.put("createUser", createUser);
		 
		 // ==================== GridPane_queryUser ====================
		 GridPane queryUser = new GridPane();
		 queryUser.setHgap(4);
		 queryUser.setVgap(6);
		 queryUser.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> queryUser_content = queryUser.getChildren();
		 Label queryUser_userid_label = new Label("userid:");
		 queryUser_userid_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryUser_content.add(queryUser_userid_label);
		 GridPane.setConstraints(queryUser_userid_label, 0, 0);
		 
		 queryUser_userid_t = new TextField();
		 queryUser_content.add(queryUser_userid_t);
		 queryUser_userid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryUser_userid_t, 1, 0);
		 operationPanels.put("queryUser", queryUser);
		 
		 // ==================== GridPane_modifyUser ====================
		 GridPane modifyUser = new GridPane();
		 modifyUser.setHgap(4);
		 modifyUser.setVgap(6);
		 modifyUser.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifyUser_content = modifyUser.getChildren();
		 Label modifyUser_userid_label = new Label("userid:");
		 modifyUser_userid_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyUser_content.add(modifyUser_userid_label);
		 GridPane.setConstraints(modifyUser_userid_label, 0, 0);
		 
		 modifyUser_userid_t = new TextField();
		 modifyUser_content.add(modifyUser_userid_t);
		 modifyUser_userid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyUser_userid_t, 1, 0);
		 Label modifyUser_name_label = new Label("name:");
		 modifyUser_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyUser_content.add(modifyUser_name_label);
		 GridPane.setConstraints(modifyUser_name_label, 0, 1);
		 
		 modifyUser_name_t = new TextField();
		 modifyUser_content.add(modifyUser_name_t);
		 modifyUser_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyUser_name_t, 1, 1);
		 Label modifyUser_sex_label = new Label("sex:");
		 modifyUser_sex_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyUser_content.add(modifyUser_sex_label);
		 GridPane.setConstraints(modifyUser_sex_label, 0, 2);
		 
		 modifyUser_sex_cb = new ChoiceBox();
modifyUser_sex_cb.getItems().add("M");
modifyUser_sex_cb.getItems().add("F");
		 modifyUser_sex_cb.getSelectionModel().selectFirst();
		 modifyUser_content.add(modifyUser_sex_cb);
		 GridPane.setConstraints(modifyUser_sex_cb, 1, 2);
		 Label modifyUser_password_label = new Label("password:");
		 modifyUser_password_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyUser_content.add(modifyUser_password_label);
		 GridPane.setConstraints(modifyUser_password_label, 0, 3);
		 
		 modifyUser_password_t = new TextField();
		 modifyUser_content.add(modifyUser_password_t);
		 modifyUser_password_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyUser_password_t, 1, 3);
		 Label modifyUser_email_label = new Label("email:");
		 modifyUser_email_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyUser_content.add(modifyUser_email_label);
		 GridPane.setConstraints(modifyUser_email_label, 0, 4);
		 
		 modifyUser_email_t = new TextField();
		 modifyUser_content.add(modifyUser_email_t);
		 modifyUser_email_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyUser_email_t, 1, 4);
		 Label modifyUser_faculty_label = new Label("faculty:");
		 modifyUser_faculty_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyUser_content.add(modifyUser_faculty_label);
		 GridPane.setConstraints(modifyUser_faculty_label, 0, 5);
		 
		 modifyUser_faculty_t = new TextField();
		 modifyUser_content.add(modifyUser_faculty_t);
		 modifyUser_faculty_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyUser_faculty_t, 1, 5);
		 Label modifyUser_loanednumber_label = new Label("loanednumber:");
		 modifyUser_loanednumber_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyUser_content.add(modifyUser_loanednumber_label);
		 GridPane.setConstraints(modifyUser_loanednumber_label, 0, 6);
		 
		 modifyUser_loanednumber_t = new TextField();
		 modifyUser_content.add(modifyUser_loanednumber_t);
		 modifyUser_loanednumber_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyUser_loanednumber_t, 1, 6);
		 Label modifyUser_borrowstatus_label = new Label("borrowstatus:");
		 modifyUser_borrowstatus_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyUser_content.add(modifyUser_borrowstatus_label);
		 GridPane.setConstraints(modifyUser_borrowstatus_label, 0, 7);
		 
		 modifyUser_borrowstatus_cb = new ChoiceBox();
modifyUser_borrowstatus_cb.getItems().add("NORMAL");
modifyUser_borrowstatus_cb.getItems().add("SUSPEND");
		 modifyUser_borrowstatus_cb.getSelectionModel().selectFirst();
		 modifyUser_content.add(modifyUser_borrowstatus_cb);
		 GridPane.setConstraints(modifyUser_borrowstatus_cb, 1, 7);
		 Label modifyUser_suspensiondays_label = new Label("suspensiondays:");
		 modifyUser_suspensiondays_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyUser_content.add(modifyUser_suspensiondays_label);
		 GridPane.setConstraints(modifyUser_suspensiondays_label, 0, 8);
		 
		 modifyUser_suspensiondays_t = new TextField();
		 modifyUser_content.add(modifyUser_suspensiondays_t);
		 modifyUser_suspensiondays_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyUser_suspensiondays_t, 1, 8);
		 Label modifyUser_overduefee_label = new Label("overduefee:");
		 modifyUser_overduefee_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyUser_content.add(modifyUser_overduefee_label);
		 GridPane.setConstraints(modifyUser_overduefee_label, 0, 9);
		 
		 modifyUser_overduefee_t = new TextField();
		 modifyUser_content.add(modifyUser_overduefee_t);
		 modifyUser_overduefee_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyUser_overduefee_t, 1, 9);
		 operationPanels.put("modifyUser", modifyUser);
		 
		 // ==================== GridPane_deleteUser ====================
		 GridPane deleteUser = new GridPane();
		 deleteUser.setHgap(4);
		 deleteUser.setVgap(6);
		 deleteUser.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteUser_content = deleteUser.getChildren();
		 Label deleteUser_userid_label = new Label("userid:");
		 deleteUser_userid_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteUser_content.add(deleteUser_userid_label);
		 GridPane.setConstraints(deleteUser_userid_label, 0, 0);
		 
		 deleteUser_userid_t = new TextField();
		 deleteUser_content.add(deleteUser_userid_t);
		 deleteUser_userid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(deleteUser_userid_t, 1, 0);
		 operationPanels.put("deleteUser", deleteUser);
		 
		 // ==================== GridPane_createBook ====================
		 GridPane createBook = new GridPane();
		 createBook.setHgap(4);
		 createBook.setVgap(6);
		 createBook.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createBook_content = createBook.getChildren();
		 Label createBook_callno_label = new Label("callno:");
		 createBook_callno_label.setMinWidth(Region.USE_PREF_SIZE);
		 createBook_content.add(createBook_callno_label);
		 GridPane.setConstraints(createBook_callno_label, 0, 0);
		 
		 createBook_callno_t = new TextField();
		 createBook_content.add(createBook_callno_t);
		 createBook_callno_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createBook_callno_t, 1, 0);
		 Label createBook_title_label = new Label("title:");
		 createBook_title_label.setMinWidth(Region.USE_PREF_SIZE);
		 createBook_content.add(createBook_title_label);
		 GridPane.setConstraints(createBook_title_label, 0, 1);
		 
		 createBook_title_t = new TextField();
		 createBook_content.add(createBook_title_t);
		 createBook_title_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createBook_title_t, 1, 1);
		 Label createBook_edition_label = new Label("edition:");
		 createBook_edition_label.setMinWidth(Region.USE_PREF_SIZE);
		 createBook_content.add(createBook_edition_label);
		 GridPane.setConstraints(createBook_edition_label, 0, 2);
		 
		 createBook_edition_t = new TextField();
		 createBook_content.add(createBook_edition_t);
		 createBook_edition_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createBook_edition_t, 1, 2);
		 Label createBook_author_label = new Label("author:");
		 createBook_author_label.setMinWidth(Region.USE_PREF_SIZE);
		 createBook_content.add(createBook_author_label);
		 GridPane.setConstraints(createBook_author_label, 0, 3);
		 
		 createBook_author_t = new TextField();
		 createBook_content.add(createBook_author_t);
		 createBook_author_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createBook_author_t, 1, 3);
		 Label createBook_publisher_label = new Label("publisher:");
		 createBook_publisher_label.setMinWidth(Region.USE_PREF_SIZE);
		 createBook_content.add(createBook_publisher_label);
		 GridPane.setConstraints(createBook_publisher_label, 0, 4);
		 
		 createBook_publisher_t = new TextField();
		 createBook_content.add(createBook_publisher_t);
		 createBook_publisher_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createBook_publisher_t, 1, 4);
		 Label createBook_description_label = new Label("description:");
		 createBook_description_label.setMinWidth(Region.USE_PREF_SIZE);
		 createBook_content.add(createBook_description_label);
		 GridPane.setConstraints(createBook_description_label, 0, 5);
		 
		 createBook_description_t = new TextField();
		 createBook_content.add(createBook_description_t);
		 createBook_description_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createBook_description_t, 1, 5);
		 Label createBook_isbn_label = new Label("isbn:");
		 createBook_isbn_label.setMinWidth(Region.USE_PREF_SIZE);
		 createBook_content.add(createBook_isbn_label);
		 GridPane.setConstraints(createBook_isbn_label, 0, 6);
		 
		 createBook_isbn_t = new TextField();
		 createBook_content.add(createBook_isbn_t);
		 createBook_isbn_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createBook_isbn_t, 1, 6);
		 Label createBook_copynum_label = new Label("copynum:");
		 createBook_copynum_label.setMinWidth(Region.USE_PREF_SIZE);
		 createBook_content.add(createBook_copynum_label);
		 GridPane.setConstraints(createBook_copynum_label, 0, 7);
		 
		 createBook_copynum_t = new TextField();
		 createBook_content.add(createBook_copynum_t);
		 createBook_copynum_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createBook_copynum_t, 1, 7);
		 operationPanels.put("createBook", createBook);
		 
		 // ==================== GridPane_queryBook ====================
		 GridPane queryBook = new GridPane();
		 queryBook.setHgap(4);
		 queryBook.setVgap(6);
		 queryBook.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> queryBook_content = queryBook.getChildren();
		 Label queryBook_callno_label = new Label("callno:");
		 queryBook_callno_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryBook_content.add(queryBook_callno_label);
		 GridPane.setConstraints(queryBook_callno_label, 0, 0);
		 
		 queryBook_callno_t = new TextField();
		 queryBook_content.add(queryBook_callno_t);
		 queryBook_callno_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryBook_callno_t, 1, 0);
		 operationPanels.put("queryBook", queryBook);
		 
		 // ==================== GridPane_modifyBook ====================
		 GridPane modifyBook = new GridPane();
		 modifyBook.setHgap(4);
		 modifyBook.setVgap(6);
		 modifyBook.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifyBook_content = modifyBook.getChildren();
		 Label modifyBook_callno_label = new Label("callno:");
		 modifyBook_callno_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBook_content.add(modifyBook_callno_label);
		 GridPane.setConstraints(modifyBook_callno_label, 0, 0);
		 
		 modifyBook_callno_t = new TextField();
		 modifyBook_content.add(modifyBook_callno_t);
		 modifyBook_callno_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBook_callno_t, 1, 0);
		 Label modifyBook_title_label = new Label("title:");
		 modifyBook_title_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBook_content.add(modifyBook_title_label);
		 GridPane.setConstraints(modifyBook_title_label, 0, 1);
		 
		 modifyBook_title_t = new TextField();
		 modifyBook_content.add(modifyBook_title_t);
		 modifyBook_title_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBook_title_t, 1, 1);
		 Label modifyBook_edition_label = new Label("edition:");
		 modifyBook_edition_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBook_content.add(modifyBook_edition_label);
		 GridPane.setConstraints(modifyBook_edition_label, 0, 2);
		 
		 modifyBook_edition_t = new TextField();
		 modifyBook_content.add(modifyBook_edition_t);
		 modifyBook_edition_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBook_edition_t, 1, 2);
		 Label modifyBook_author_label = new Label("author:");
		 modifyBook_author_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBook_content.add(modifyBook_author_label);
		 GridPane.setConstraints(modifyBook_author_label, 0, 3);
		 
		 modifyBook_author_t = new TextField();
		 modifyBook_content.add(modifyBook_author_t);
		 modifyBook_author_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBook_author_t, 1, 3);
		 Label modifyBook_publisher_label = new Label("publisher:");
		 modifyBook_publisher_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBook_content.add(modifyBook_publisher_label);
		 GridPane.setConstraints(modifyBook_publisher_label, 0, 4);
		 
		 modifyBook_publisher_t = new TextField();
		 modifyBook_content.add(modifyBook_publisher_t);
		 modifyBook_publisher_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBook_publisher_t, 1, 4);
		 Label modifyBook_description_label = new Label("description:");
		 modifyBook_description_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBook_content.add(modifyBook_description_label);
		 GridPane.setConstraints(modifyBook_description_label, 0, 5);
		 
		 modifyBook_description_t = new TextField();
		 modifyBook_content.add(modifyBook_description_t);
		 modifyBook_description_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBook_description_t, 1, 5);
		 Label modifyBook_isbn_label = new Label("isbn:");
		 modifyBook_isbn_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBook_content.add(modifyBook_isbn_label);
		 GridPane.setConstraints(modifyBook_isbn_label, 0, 6);
		 
		 modifyBook_isbn_t = new TextField();
		 modifyBook_content.add(modifyBook_isbn_t);
		 modifyBook_isbn_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBook_isbn_t, 1, 6);
		 Label modifyBook_copynum_label = new Label("copynum:");
		 modifyBook_copynum_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBook_content.add(modifyBook_copynum_label);
		 GridPane.setConstraints(modifyBook_copynum_label, 0, 7);
		 
		 modifyBook_copynum_t = new TextField();
		 modifyBook_content.add(modifyBook_copynum_t);
		 modifyBook_copynum_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBook_copynum_t, 1, 7);
		 operationPanels.put("modifyBook", modifyBook);
		 
		 // ==================== GridPane_deleteBook ====================
		 GridPane deleteBook = new GridPane();
		 deleteBook.setHgap(4);
		 deleteBook.setVgap(6);
		 deleteBook.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteBook_content = deleteBook.getChildren();
		 Label deleteBook_callno_label = new Label("callno:");
		 deleteBook_callno_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteBook_content.add(deleteBook_callno_label);
		 GridPane.setConstraints(deleteBook_callno_label, 0, 0);
		 
		 deleteBook_callno_t = new TextField();
		 deleteBook_content.add(deleteBook_callno_t);
		 deleteBook_callno_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(deleteBook_callno_t, 1, 0);
		 operationPanels.put("deleteBook", deleteBook);
		 
		 // ==================== GridPane_createSubject ====================
		 GridPane createSubject = new GridPane();
		 createSubject.setHgap(4);
		 createSubject.setVgap(6);
		 createSubject.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createSubject_content = createSubject.getChildren();
		 Label createSubject_name_label = new Label("name:");
		 createSubject_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 createSubject_content.add(createSubject_name_label);
		 GridPane.setConstraints(createSubject_name_label, 0, 0);
		 
		 createSubject_name_t = new TextField();
		 createSubject_content.add(createSubject_name_t);
		 createSubject_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createSubject_name_t, 1, 0);
		 operationPanels.put("createSubject", createSubject);
		 
		 // ==================== GridPane_querySubject ====================
		 GridPane querySubject = new GridPane();
		 querySubject.setHgap(4);
		 querySubject.setVgap(6);
		 querySubject.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> querySubject_content = querySubject.getChildren();
		 Label querySubject_name_label = new Label("name:");
		 querySubject_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 querySubject_content.add(querySubject_name_label);
		 GridPane.setConstraints(querySubject_name_label, 0, 0);
		 
		 querySubject_name_t = new TextField();
		 querySubject_content.add(querySubject_name_t);
		 querySubject_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(querySubject_name_t, 1, 0);
		 operationPanels.put("querySubject", querySubject);
		 
		 // ==================== GridPane_modifySubject ====================
		 GridPane modifySubject = new GridPane();
		 modifySubject.setHgap(4);
		 modifySubject.setVgap(6);
		 modifySubject.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifySubject_content = modifySubject.getChildren();
		 Label modifySubject_name_label = new Label("name:");
		 modifySubject_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifySubject_content.add(modifySubject_name_label);
		 GridPane.setConstraints(modifySubject_name_label, 0, 0);
		 
		 modifySubject_name_t = new TextField();
		 modifySubject_content.add(modifySubject_name_t);
		 modifySubject_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifySubject_name_t, 1, 0);
		 operationPanels.put("modifySubject", modifySubject);
		 
		 // ==================== GridPane_deleteSubject ====================
		 GridPane deleteSubject = new GridPane();
		 deleteSubject.setHgap(4);
		 deleteSubject.setVgap(6);
		 deleteSubject.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteSubject_content = deleteSubject.getChildren();
		 Label deleteSubject_name_label = new Label("name:");
		 deleteSubject_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteSubject_content.add(deleteSubject_name_label);
		 GridPane.setConstraints(deleteSubject_name_label, 0, 0);
		 
		 deleteSubject_name_t = new TextField();
		 deleteSubject_content.add(deleteSubject_name_t);
		 deleteSubject_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(deleteSubject_name_t, 1, 0);
		 operationPanels.put("deleteSubject", deleteSubject);
		 
		 // ==================== GridPane_addBookCopy ====================
		 GridPane addBookCopy = new GridPane();
		 addBookCopy.setHgap(4);
		 addBookCopy.setVgap(6);
		 addBookCopy.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> addBookCopy_content = addBookCopy.getChildren();
		 Label addBookCopy_callNo_label = new Label("callNo:");
		 addBookCopy_callNo_label.setMinWidth(Region.USE_PREF_SIZE);
		 addBookCopy_content.add(addBookCopy_callNo_label);
		 GridPane.setConstraints(addBookCopy_callNo_label, 0, 0);
		 
		 addBookCopy_callNo_t = new TextField();
		 addBookCopy_content.add(addBookCopy_callNo_t);
		 addBookCopy_callNo_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(addBookCopy_callNo_t, 1, 0);
		 Label addBookCopy_barcode_label = new Label("barcode:");
		 addBookCopy_barcode_label.setMinWidth(Region.USE_PREF_SIZE);
		 addBookCopy_content.add(addBookCopy_barcode_label);
		 GridPane.setConstraints(addBookCopy_barcode_label, 0, 1);
		 
		 addBookCopy_barcode_t = new TextField();
		 addBookCopy_content.add(addBookCopy_barcode_t);
		 addBookCopy_barcode_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(addBookCopy_barcode_t, 1, 1);
		 Label addBookCopy_location_label = new Label("location:");
		 addBookCopy_location_label.setMinWidth(Region.USE_PREF_SIZE);
		 addBookCopy_content.add(addBookCopy_location_label);
		 GridPane.setConstraints(addBookCopy_location_label, 0, 2);
		 
		 addBookCopy_location_t = new TextField();
		 addBookCopy_content.add(addBookCopy_location_t);
		 addBookCopy_location_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(addBookCopy_location_t, 1, 2);
		 operationPanels.put("addBookCopy", addBookCopy);
		 
		 // ==================== GridPane_queryBookCopy ====================
		 GridPane queryBookCopy = new GridPane();
		 queryBookCopy.setHgap(4);
		 queryBookCopy.setVgap(6);
		 queryBookCopy.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> queryBookCopy_content = queryBookCopy.getChildren();
		 Label queryBookCopy_barcode_label = new Label("barcode:");
		 queryBookCopy_barcode_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryBookCopy_content.add(queryBookCopy_barcode_label);
		 GridPane.setConstraints(queryBookCopy_barcode_label, 0, 0);
		 
		 queryBookCopy_barcode_t = new TextField();
		 queryBookCopy_content.add(queryBookCopy_barcode_t);
		 queryBookCopy_barcode_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryBookCopy_barcode_t, 1, 0);
		 operationPanels.put("queryBookCopy", queryBookCopy);
		 
		 // ==================== GridPane_modifyBookCopy ====================
		 GridPane modifyBookCopy = new GridPane();
		 modifyBookCopy.setHgap(4);
		 modifyBookCopy.setVgap(6);
		 modifyBookCopy.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifyBookCopy_content = modifyBookCopy.getChildren();
		 Label modifyBookCopy_barcode_label = new Label("barcode:");
		 modifyBookCopy_barcode_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBookCopy_content.add(modifyBookCopy_barcode_label);
		 GridPane.setConstraints(modifyBookCopy_barcode_label, 0, 0);
		 
		 modifyBookCopy_barcode_t = new TextField();
		 modifyBookCopy_content.add(modifyBookCopy_barcode_t);
		 modifyBookCopy_barcode_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBookCopy_barcode_t, 1, 0);
		 Label modifyBookCopy_status_label = new Label("status:");
		 modifyBookCopy_status_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBookCopy_content.add(modifyBookCopy_status_label);
		 GridPane.setConstraints(modifyBookCopy_status_label, 0, 1);
		 
		 modifyBookCopy_status_cb = new ChoiceBox();
modifyBookCopy_status_cb.getItems().add("AVAILABLE");
modifyBookCopy_status_cb.getItems().add("INPROCESSING");
modifyBookCopy_status_cb.getItems().add("LIBUSEONLY");
modifyBookCopy_status_cb.getItems().add("ONHOLDSHELF");
modifyBookCopy_status_cb.getItems().add("LOANED");
		 modifyBookCopy_status_cb.getSelectionModel().selectFirst();
		 modifyBookCopy_content.add(modifyBookCopy_status_cb);
		 GridPane.setConstraints(modifyBookCopy_status_cb, 1, 1);
		 Label modifyBookCopy_location_label = new Label("location:");
		 modifyBookCopy_location_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBookCopy_content.add(modifyBookCopy_location_label);
		 GridPane.setConstraints(modifyBookCopy_location_label, 0, 2);
		 
		 modifyBookCopy_location_t = new TextField();
		 modifyBookCopy_content.add(modifyBookCopy_location_t);
		 modifyBookCopy_location_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBookCopy_location_t, 1, 2);
		 Label modifyBookCopy_isreserved_label = new Label("isreserved:");
		 modifyBookCopy_isreserved_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyBookCopy_content.add(modifyBookCopy_isreserved_label);
		 GridPane.setConstraints(modifyBookCopy_isreserved_label, 0, 3);
		 
		 modifyBookCopy_isreserved_t = new TextField();
		 modifyBookCopy_content.add(modifyBookCopy_isreserved_t);
		 modifyBookCopy_isreserved_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyBookCopy_isreserved_t, 1, 3);
		 operationPanels.put("modifyBookCopy", modifyBookCopy);
		 
		 // ==================== GridPane_deleteBookCopy ====================
		 GridPane deleteBookCopy = new GridPane();
		 deleteBookCopy.setHgap(4);
		 deleteBookCopy.setVgap(6);
		 deleteBookCopy.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteBookCopy_content = deleteBookCopy.getChildren();
		 Label deleteBookCopy_barcode_label = new Label("barcode:");
		 deleteBookCopy_barcode_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteBookCopy_content.add(deleteBookCopy_barcode_label);
		 GridPane.setConstraints(deleteBookCopy_barcode_label, 0, 0);
		 
		 deleteBookCopy_barcode_t = new TextField();
		 deleteBookCopy_content.add(deleteBookCopy_barcode_t);
		 deleteBookCopy_barcode_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(deleteBookCopy_barcode_t, 1, 0);
		 operationPanels.put("deleteBookCopy", deleteBookCopy);
		 
		 // ==================== GridPane_createLibrarian ====================
		 GridPane createLibrarian = new GridPane();
		 createLibrarian.setHgap(4);
		 createLibrarian.setVgap(6);
		 createLibrarian.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> createLibrarian_content = createLibrarian.getChildren();
		 Label createLibrarian_librarianid_label = new Label("librarianid:");
		 createLibrarian_librarianid_label.setMinWidth(Region.USE_PREF_SIZE);
		 createLibrarian_content.add(createLibrarian_librarianid_label);
		 GridPane.setConstraints(createLibrarian_librarianid_label, 0, 0);
		 
		 createLibrarian_librarianid_t = new TextField();
		 createLibrarian_content.add(createLibrarian_librarianid_t);
		 createLibrarian_librarianid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createLibrarian_librarianid_t, 1, 0);
		 Label createLibrarian_name_label = new Label("name:");
		 createLibrarian_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 createLibrarian_content.add(createLibrarian_name_label);
		 GridPane.setConstraints(createLibrarian_name_label, 0, 1);
		 
		 createLibrarian_name_t = new TextField();
		 createLibrarian_content.add(createLibrarian_name_t);
		 createLibrarian_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createLibrarian_name_t, 1, 1);
		 Label createLibrarian_password_label = new Label("password:");
		 createLibrarian_password_label.setMinWidth(Region.USE_PREF_SIZE);
		 createLibrarian_content.add(createLibrarian_password_label);
		 GridPane.setConstraints(createLibrarian_password_label, 0, 2);
		 
		 createLibrarian_password_t = new TextField();
		 createLibrarian_content.add(createLibrarian_password_t);
		 createLibrarian_password_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(createLibrarian_password_t, 1, 2);
		 operationPanels.put("createLibrarian", createLibrarian);
		 
		 // ==================== GridPane_queryLibrarian ====================
		 GridPane queryLibrarian = new GridPane();
		 queryLibrarian.setHgap(4);
		 queryLibrarian.setVgap(6);
		 queryLibrarian.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> queryLibrarian_content = queryLibrarian.getChildren();
		 Label queryLibrarian_librarianid_label = new Label("librarianid:");
		 queryLibrarian_librarianid_label.setMinWidth(Region.USE_PREF_SIZE);
		 queryLibrarian_content.add(queryLibrarian_librarianid_label);
		 GridPane.setConstraints(queryLibrarian_librarianid_label, 0, 0);
		 
		 queryLibrarian_librarianid_t = new TextField();
		 queryLibrarian_content.add(queryLibrarian_librarianid_t);
		 queryLibrarian_librarianid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(queryLibrarian_librarianid_t, 1, 0);
		 operationPanels.put("queryLibrarian", queryLibrarian);
		 
		 // ==================== GridPane_modifyLibrarian ====================
		 GridPane modifyLibrarian = new GridPane();
		 modifyLibrarian.setHgap(4);
		 modifyLibrarian.setVgap(6);
		 modifyLibrarian.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> modifyLibrarian_content = modifyLibrarian.getChildren();
		 Label modifyLibrarian_librarianid_label = new Label("librarianid:");
		 modifyLibrarian_librarianid_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyLibrarian_content.add(modifyLibrarian_librarianid_label);
		 GridPane.setConstraints(modifyLibrarian_librarianid_label, 0, 0);
		 
		 modifyLibrarian_librarianid_t = new TextField();
		 modifyLibrarian_content.add(modifyLibrarian_librarianid_t);
		 modifyLibrarian_librarianid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyLibrarian_librarianid_t, 1, 0);
		 Label modifyLibrarian_name_label = new Label("name:");
		 modifyLibrarian_name_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyLibrarian_content.add(modifyLibrarian_name_label);
		 GridPane.setConstraints(modifyLibrarian_name_label, 0, 1);
		 
		 modifyLibrarian_name_t = new TextField();
		 modifyLibrarian_content.add(modifyLibrarian_name_t);
		 modifyLibrarian_name_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyLibrarian_name_t, 1, 1);
		 Label modifyLibrarian_password_label = new Label("password:");
		 modifyLibrarian_password_label.setMinWidth(Region.USE_PREF_SIZE);
		 modifyLibrarian_content.add(modifyLibrarian_password_label);
		 GridPane.setConstraints(modifyLibrarian_password_label, 0, 2);
		 
		 modifyLibrarian_password_t = new TextField();
		 modifyLibrarian_content.add(modifyLibrarian_password_t);
		 modifyLibrarian_password_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(modifyLibrarian_password_t, 1, 2);
		 operationPanels.put("modifyLibrarian", modifyLibrarian);
		 
		 // ==================== GridPane_deleteLibrarian ====================
		 GridPane deleteLibrarian = new GridPane();
		 deleteLibrarian.setHgap(4);
		 deleteLibrarian.setVgap(6);
		 deleteLibrarian.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> deleteLibrarian_content = deleteLibrarian.getChildren();
		 Label deleteLibrarian_librarianid_label = new Label("librarianid:");
		 deleteLibrarian_librarianid_label.setMinWidth(Region.USE_PREF_SIZE);
		 deleteLibrarian_content.add(deleteLibrarian_librarianid_label);
		 GridPane.setConstraints(deleteLibrarian_librarianid_label, 0, 0);
		 
		 deleteLibrarian_librarianid_t = new TextField();
		 deleteLibrarian_content.add(deleteLibrarian_librarianid_t);
		 deleteLibrarian_librarianid_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(deleteLibrarian_librarianid_t, 1, 0);
		 operationPanels.put("deleteLibrarian", deleteLibrarian);
		 
		 // ==================== GridPane_inputAccount ====================
		 GridPane inputAccount = new GridPane();
		 inputAccount.setHgap(4);
		 inputAccount.setVgap(6);
		 inputAccount.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> inputAccount_content = inputAccount.getChildren();
		 Label inputAccount_label = new Label("This operation is no intput parameters..");
		 inputAccount_label.setMinWidth(Region.USE_PREF_SIZE);
		 inputAccount_content.add(inputAccount_label);
		 GridPane.setConstraints(inputAccount_label, 0, 0);
		 operationPanels.put("inputAccount", inputAccount);
		 
		 // ==================== GridPane_inputPassword ====================
		 GridPane inputPassword = new GridPane();
		 inputPassword.setHgap(4);
		 inputPassword.setVgap(6);
		 inputPassword.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> inputPassword_content = inputPassword.getChildren();
		 Label inputPassword_label = new Label("This operation is no intput parameters..");
		 inputPassword_label.setMinWidth(Region.USE_PREF_SIZE);
		 inputPassword_content.add(inputPassword_label);
		 GridPane.setConstraints(inputPassword_label, 0, 0);
		 operationPanels.put("inputPassword", inputPassword);
		 
		 // ==================== GridPane_inputAccount1 ====================
		 GridPane inputAccount1 = new GridPane();
		 inputAccount1.setHgap(4);
		 inputAccount1.setVgap(6);
		 inputAccount1.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> inputAccount1_content = inputAccount1.getChildren();
		 Label inputAccount1_defaultValue_label = new Label("defaultValue:");
		 inputAccount1_defaultValue_label.setMinWidth(Region.USE_PREF_SIZE);
		 inputAccount1_content.add(inputAccount1_defaultValue_label);
		 GridPane.setConstraints(inputAccount1_defaultValue_label, 0, 0);
		 
		 inputAccount1_defaultValue_t = new TextField();
		 inputAccount1_content.add(inputAccount1_defaultValue_t);
		 inputAccount1_defaultValue_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(inputAccount1_defaultValue_t, 1, 0);
		 operationPanels.put("inputAccount1", inputAccount1);
		 
		 // ==================== GridPane_inputPasswordNew ====================
		 GridPane inputPasswordNew = new GridPane();
		 inputPasswordNew.setHgap(4);
		 inputPasswordNew.setVgap(6);
		 inputPasswordNew.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> inputPasswordNew_content = inputPasswordNew.getChildren();
		 Label inputPasswordNew_defaultValue_label = new Label("defaultValue:");
		 inputPasswordNew_defaultValue_label.setMinWidth(Region.USE_PREF_SIZE);
		 inputPasswordNew_content.add(inputPasswordNew_defaultValue_label);
		 GridPane.setConstraints(inputPasswordNew_defaultValue_label, 0, 0);
		 
		 inputPasswordNew_defaultValue_t = new TextField();
		 inputPasswordNew_content.add(inputPasswordNew_defaultValue_t);
		 inputPasswordNew_defaultValue_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(inputPasswordNew_defaultValue_t, 1, 0);
		 operationPanels.put("inputPasswordNew", inputPasswordNew);
		 
		 // ==================== GridPane_inputPasswordNew ====================
		 GridPane inputPasswordNew11 = new GridPane();
		 inputPasswordNew11.setHgap(4);
		 inputPasswordNew11.setVgap(6);
		 inputPasswordNew11.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> inputPasswordNew_content1 = inputPasswordNew11.getChildren();
		 Label inputPasswordNew_defaultValue_label1 = new Label("defaultValue:");
		 inputPasswordNew_defaultValue_label1.setMinWidth(Region.USE_PREF_SIZE);
		 inputPasswordNew_content1.add(inputPasswordNew_defaultValue_label1);
		 GridPane.setConstraints(inputPasswordNew_defaultValue_label1, 0, 0);
		 
		 inputPasswordNew_defaultValue_t = new TextField();
		 inputPasswordNew_content1.add(inputPasswordNew_defaultValue_t);
		 inputPasswordNew_defaultValue_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(inputPasswordNew_defaultValue_t, 1, 0);
		 operationPanels.put("inputPasswordNew", inputPasswordNew11);
		 
		 // ==================== GridPane_ensurePassword1 ====================
		 GridPane ensurePassword1 = new GridPane();
		 ensurePassword1.setHgap(4);
		 ensurePassword1.setVgap(6);
		 ensurePassword1.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> ensurePassword1_content = ensurePassword1.getChildren();
		 Label ensurePassword1_defaultValue_label = new Label("defaultValue:");
		 ensurePassword1_defaultValue_label.setMinWidth(Region.USE_PREF_SIZE);
		 ensurePassword1_content.add(ensurePassword1_defaultValue_label);
		 GridPane.setConstraints(ensurePassword1_defaultValue_label, 0, 0);
		 
		 ensurePassword1_defaultValue_t = new TextField();
		 ensurePassword1_content.add(ensurePassword1_defaultValue_t);
		 ensurePassword1_defaultValue_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(ensurePassword1_defaultValue_t, 1, 0);
		 operationPanels.put("ensurePassword1", ensurePassword1);
		 
		 // ==================== GridPane_inputPassword1 ====================
		 GridPane inputPassword1 = new GridPane();
		 inputPassword1.setHgap(4);
		 inputPassword1.setVgap(6);
		 inputPassword1.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> inputPassword1_content = inputPassword1.getChildren();
		 Label inputPassword1_defaultValue_label = new Label("defaultValue:");
		 inputPassword1_defaultValue_label.setMinWidth(Region.USE_PREF_SIZE);
		 inputPassword1_content.add(inputPassword1_defaultValue_label);
		 GridPane.setConstraints(inputPassword1_defaultValue_label, 0, 0);
		 
		 inputPassword1_defaultValue_t = new TextField();
		 inputPassword1_content.add(inputPassword1_defaultValue_t);
		 inputPassword1_defaultValue_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(inputPassword1_defaultValue_t, 1, 0);
		 operationPanels.put("inputPassword1", inputPassword1);
		 
		 // ==================== GridPane_inputCard1 ====================
		 GridPane inputCard1 = new GridPane();
		 inputCard1.setHgap(4);
		 inputCard1.setVgap(6);
		 inputCard1.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> inputCard1_content = inputCard1.getChildren();
		 Label inputCard1_cardID_label = new Label("cardID:");
		 inputCard1_cardID_label.setMinWidth(Region.USE_PREF_SIZE);
		 inputCard1_content.add(inputCard1_cardID_label);
		 GridPane.setConstraints(inputCard1_cardID_label, 0, 0);
		 
		 inputCard1_cardID_t = new TextField();
		 inputCard1_content.add(inputCard1_cardID_t);
		 inputCard1_cardID_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(inputCard1_cardID_t, 1, 0);
		 operationPanels.put("inputCard1", inputCard1);
		 
		 // ==================== GridPane_inputPassword2 ====================
		 GridPane inputPassword2 = new GridPane();
		 inputPassword2.setHgap(4);
		 inputPassword2.setVgap(6);
		 inputPassword2.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> inputPassword2_content = inputPassword2.getChildren();
		 Label inputPassword2_password_label = new Label("password:");
		 inputPassword2_password_label.setMinWidth(Region.USE_PREF_SIZE);
		 inputPassword2_content.add(inputPassword2_password_label);
		 GridPane.setConstraints(inputPassword2_password_label, 0, 0);
		 
		 inputPassword2_password_t = new TextField();
		 inputPassword2_content.add(inputPassword2_password_t);
		 inputPassword2_password_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(inputPassword2_password_t, 1, 0);
		 operationPanels.put("inputPassword2", inputPassword2);
		 
		 // ==================== GridPane_inputVerificationcode ====================
		 GridPane inputVerificationcode = new GridPane();
		 inputVerificationcode.setHgap(4);
		 inputVerificationcode.setVgap(6);
		 inputVerificationcode.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> inputVerificationcode_content = inputVerificationcode.getChildren();
		 Label inputVerificationcode_label = new Label("This operation is no intput parameters..");
		 inputVerificationcode_label.setMinWidth(Region.USE_PREF_SIZE);
		 inputVerificationcode_content.add(inputVerificationcode_label);
		 GridPane.setConstraints(inputVerificationcode_label, 0, 0);
		 operationPanels.put("inputVerificationcode", inputVerificationcode);
		 
		 // ==================== GridPane_inputCard12 ====================
		 GridPane inputCard12 = new GridPane();
		 inputCard12.setHgap(4);
		 inputCard12.setVgap(6);
		 inputCard12.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> inputCard12_content = inputCard12.getChildren();
		 Label inputCard12_cardID_label = new Label("cardID:");
		 inputCard12_cardID_label.setMinWidth(Region.USE_PREF_SIZE);
		 inputCard12_content.add(inputCard12_cardID_label);
		 GridPane.setConstraints(inputCard12_cardID_label, 0, 0);
		 
		 inputCard12_cardID_t = new TextField();
		 inputCard12_content.add(inputCard12_cardID_t);
		 inputCard12_cardID_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(inputCard12_cardID_t, 1, 0);
		 operationPanels.put("inputCard12", inputCard12);
		 
		 // ==================== GridPane_inputPassword11 ====================
		 GridPane inputPassword11 = new GridPane();
		 inputPassword11.setHgap(4);
		 inputPassword11.setVgap(6);
		 inputPassword11.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> inputPassword11_content = inputPassword11.getChildren();
		 Label inputPassword11_password_label = new Label("password:");
		 inputPassword11_password_label.setMinWidth(Region.USE_PREF_SIZE);
		 inputPassword11_content.add(inputPassword11_password_label);
		 GridPane.setConstraints(inputPassword11_password_label, 0, 0);
		 
		 inputPassword11_password_t = new TextField();
		 inputPassword11_content.add(inputPassword11_password_t);
		 inputPassword11_password_t.setMinWidth(Region.USE_PREF_SIZE);
		 GridPane.setConstraints(inputPassword11_password_t, 1, 0);
		 operationPanels.put("inputPassword11", inputPassword11);
		 
		 // ==================== GridPane_ensureDeleteAccount ====================
		 GridPane ensureDeleteAccount = new GridPane();
		 ensureDeleteAccount.setHgap(4);
		 ensureDeleteAccount.setVgap(6);
		 ensureDeleteAccount.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> ensureDeleteAccount_content = ensureDeleteAccount.getChildren();
		 Label ensureDeleteAccount_label = new Label("This operation is no intput parameters..");
		 ensureDeleteAccount_label.setMinWidth(Region.USE_PREF_SIZE);
		 ensureDeleteAccount_content.add(ensureDeleteAccount_label);
		 GridPane.setConstraints(ensureDeleteAccount_label, 0, 0);
		 operationPanels.put("ensureDeleteAccount", ensureDeleteAccount);
		 
		 // ==================== GridPane_inputBookInformation ====================
		 GridPane inputBookInformation = new GridPane();
		 inputBookInformation.setHgap(4);
		 inputBookInformation.setVgap(6);
		 inputBookInformation.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> inputBookInformation_content = inputBookInformation.getChildren();
		 Label inputBookInformation_label = new Label("This operation is no intput parameters..");
		 inputBookInformation_label.setMinWidth(Region.USE_PREF_SIZE);
		 inputBookInformation_content.add(inputBookInformation_label);
		 GridPane.setConstraints(inputBookInformation_label, 0, 0);
		 operationPanels.put("inputBookInformation", inputBookInformation);
		 
		 // ==================== GridPane_inputCardID ====================
		 GridPane inputCardID = new GridPane();
		 inputCardID.setHgap(4);
		 inputCardID.setVgap(6);
		 inputCardID.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> inputCardID_content = inputCardID.getChildren();
		 Label inputCardID_label = new Label("This operation is no intput parameters..");
		 inputCardID_label.setMinWidth(Region.USE_PREF_SIZE);
		 inputCardID_content.add(inputCardID_label);
		 GridPane.setConstraints(inputCardID_label, 0, 0);
		 operationPanels.put("inputCardID", inputCardID);
		 
		 // ==================== GridPane_exitStytem ====================
		 GridPane exitStytem = new GridPane();
		 exitStytem.setHgap(4);
		 exitStytem.setVgap(6);
		 exitStytem.setPadding(new Insets(8, 8, 8, 8));
		 
		 ObservableList<Node> exitStytem_content = exitStytem.getChildren();
		 Label exitStytem_label = new Label("This operation is no intput parameters..");
		 exitStytem_label.setMinWidth(Region.USE_PREF_SIZE);
		 exitStytem_content.add(exitStytem_label);
		 GridPane.setConstraints(exitStytem_label, 0, 0);
		 operationPanels.put("exitStytem", exitStytem);
		 
	}	

	public void actorTreeViewBinding() {
		
		 
		
		
		 
		TreeItem<String> treeRootuser = new TreeItem<String>("Root node");
			TreeItem<String> subTreeRoot_searchBook = new TreeItem<String>("searchBook");
			subTreeRoot_searchBook.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("inputBookInformation"),
					 	new TreeItem<String>("inputCardID"),
					 	new TreeItem<String>("exitStytem")
				 		));	
			TreeItem<String> subTreeRoot_listBookHistory = new TreeItem<String>("listBookHistory");
			subTreeRoot_listBookHistory.getChildren().addAll(Arrays.asList(			 		    
				 		));	
			TreeItem<String> subTreeRoot_recommendBook = new TreeItem<String>("recommendBook");
			subTreeRoot_recommendBook.getChildren().addAll(Arrays.asList(			 		    
				 		));	
			TreeItem<String> subTreeRoot_registerAccount = new TreeItem<String>("registerAccount");
			subTreeRoot_registerAccount.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("inputAccount1"),
					 	new TreeItem<String>("inputPassword1"),
					 	new TreeItem<String>("ensurePassword1")
				 		));	
			TreeItem<String> subTreeRoot_logInAccount = new TreeItem<String>("logInAccount");
			subTreeRoot_logInAccount.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("inputAccount1"),
					 	new TreeItem<String>("inputCard1"),
					 	new TreeItem<String>("inputPassword2"),
					 	new TreeItem<String>("inputVerificationcode")
				 		));	
		
		treeRootuser.getChildren().addAll(Arrays.asList(
			subTreeRoot_searchBook,
			subTreeRoot_listBookHistory,
			new TreeItem<String>("makeReservation"),
			subTreeRoot_recommendBook,
			new TreeItem<String>("cancelReservation"),
			subTreeRoot_registerAccount,
			subTreeRoot_logInAccount
					));
		
		treeRootuser.setExpanded(true);

		actor_treeview_user.setShowRoot(false);
		actor_treeview_user.setRoot(treeRootuser);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_user.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootfaculty = new TreeItem<String>("Root node");
		
		treeRootfaculty.getChildren().addAll(Arrays.asList(
					));
		treeRootfaculty.getChildren().addAll(Arrays.asList(
			this.deepCopyTree(subTreeRoot_searchBook),
			this.deepCopyTree(subTreeRoot_listBookHistory),
			new TreeItem<String>("makeReservation"),
			this.deepCopyTree(subTreeRoot_recommendBook),
			new TreeItem<String>("cancelReservation"),
			this.deepCopyTree(subTreeRoot_registerAccount),
			this.deepCopyTree(subTreeRoot_logInAccount)
			));
		
		treeRootfaculty.setExpanded(true);

		actor_treeview_faculty.setShowRoot(false);
		actor_treeview_faculty.setRoot(treeRootfaculty);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_faculty.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootstudent = new TreeItem<String>("Root node");
		
		treeRootstudent.getChildren().addAll(Arrays.asList(
					));
		treeRootstudent.getChildren().addAll(Arrays.asList(
			this.deepCopyTree(subTreeRoot_searchBook),
			this.deepCopyTree(subTreeRoot_listBookHistory),
			new TreeItem<String>("makeReservation"),
			this.deepCopyTree(subTreeRoot_recommendBook),
			new TreeItem<String>("cancelReservation"),
			this.deepCopyTree(subTreeRoot_registerAccount),
			this.deepCopyTree(subTreeRoot_logInAccount)
			));
		
		treeRootstudent.setExpanded(true);

		actor_treeview_student.setShowRoot(false);
		actor_treeview_student.setRoot(treeRootstudent);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_student.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootadministrator = new TreeItem<String>("Root node");
		
		treeRootadministrator.getChildren().addAll(Arrays.asList(
					));
		
		treeRootadministrator.setExpanded(true);

		actor_treeview_administrator.setShowRoot(false);
		actor_treeview_administrator.setRoot(treeRootadministrator);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_administrator.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootlibrarian = new TreeItem<String>("Root node");
		
		treeRootlibrarian.getChildren().addAll(Arrays.asList(
					));
		
		treeRootlibrarian.setExpanded(true);

		actor_treeview_librarian.setShowRoot(false);
		actor_treeview_librarian.setRoot(treeRootlibrarian);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_librarian.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootscheduler = new TreeItem<String>("Root node");
		
		treeRootscheduler.getChildren().addAll(Arrays.asList(
			new TreeItem<String>("checkOverDueandComputeOverDueFee"),
			new TreeItem<String>("dueSoonNotification"),
			new TreeItem<String>("countDownSuspensionDay")
					));
		
		treeRootscheduler.setExpanded(true);

		actor_treeview_scheduler.setShowRoot(false);
		actor_treeview_scheduler.setRoot(treeRootscheduler);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_scheduler.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootthirdpartsystem = new TreeItem<String>("Root node");
		
		treeRootthirdpartsystem.getChildren().addAll(Arrays.asList(
			new TreeItem<String>("sendNotificationEmail")
					));
		
		treeRootthirdpartsystem.setExpanded(true);

		actor_treeview_thirdpartsystem.setShowRoot(false);
		actor_treeview_thirdpartsystem.setRoot(treeRootthirdpartsystem);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_thirdpartsystem.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootlibrariannew = new TreeItem<String>("Root node");
		
		treeRootlibrariannew.getChildren().addAll(Arrays.asList(
			new TreeItem<String>("returnBook"),
			new TreeItem<String>("payOverDueFee"),
			new TreeItem<String>("renewBook"),
			new TreeItem<String>("borrowBook")
					));
		
		treeRootlibrariannew.setExpanded(true);

		actor_treeview_librariannew.setShowRoot(false);
		actor_treeview_librariannew.setRoot(treeRootlibrariannew);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_librariannew.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
		TreeItem<String> treeRootlibrarymanager = new TreeItem<String>("Root node");
			TreeItem<String> subTreeRoot_resetPassword = new TreeItem<String>("resetPassword");
			subTreeRoot_resetPassword.getChildren().addAll(Arrays.asList(			 		    
				 		));	
			TreeItem<String> subTreeRoot_listRecommendBook = new TreeItem<String>("listRecommendBook");
			subTreeRoot_listRecommendBook.getChildren().addAll(Arrays.asList(			 		    
				 		));	
			TreeItem<String> subTreeRoot_deleteAccount = new TreeItem<String>("deleteAccount");
			subTreeRoot_deleteAccount.getChildren().addAll(Arrays.asList(			 		    
					 	new TreeItem<String>("inputCard12"),
					 	new TreeItem<String>("inputPassword11"),
					 	new TreeItem<String>("ensureDeleteAccount")
				 		));	
			TreeItem<String> subTreeRoot_manageBook = new TreeItem<String>("manageBook");
			subTreeRoot_manageBook.getChildren().addAll(Arrays.asList(
				 	new TreeItem<String>("createBook"),
				 	new TreeItem<String>("queryBook"),
				 	new TreeItem<String>("modifyBook"),
				 	new TreeItem<String>("deleteBook")
				 	));
				
			TreeItem<String> subTreeRoot_manageSubject = new TreeItem<String>("manageSubject");
			subTreeRoot_manageSubject.getChildren().addAll(Arrays.asList(
				 	new TreeItem<String>("createSubject"),
				 	new TreeItem<String>("querySubject"),
				 	new TreeItem<String>("modifySubject"),
				 	new TreeItem<String>("deleteSubject")
				 	));
				
			TreeItem<String> subTreeRoot_manageBookCopy = new TreeItem<String>("manageBookCopy");
			subTreeRoot_manageBookCopy.getChildren().addAll(Arrays.asList(
				 	new TreeItem<String>("addBookCopy"),
				 	new TreeItem<String>("queryBookCopy"),
				 	new TreeItem<String>("modifyBookCopy"),
				 	new TreeItem<String>("deleteBookCopy")
				 	));
				
			TreeItem<String> subTreeRoot_manageLibrarian = new TreeItem<String>("manageLibrarian");
			subTreeRoot_manageLibrarian.getChildren().addAll(Arrays.asList(
				 	new TreeItem<String>("createLibrarian"),
				 	new TreeItem<String>("queryLibrarian"),
				 	new TreeItem<String>("modifyLibrarian"),
				 	new TreeItem<String>("deleteLibrarian")
				 	));
				
		
		treeRootlibrarymanager.getChildren().addAll(Arrays.asList(
			subTreeRoot_resetPassword,
			subTreeRoot_listRecommendBook,
			subTreeRoot_deleteAccount,
			subTreeRoot_manageBook,
			subTreeRoot_manageSubject,
			subTreeRoot_manageBookCopy,
			subTreeRoot_manageLibrarian
					));
		
		treeRootlibrarymanager.setExpanded(true);

		actor_treeview_librarymanager.setShowRoot(false);
		actor_treeview_librarymanager.setRoot(treeRootlibrarymanager);
		
		//TreeView click, then open the GridPane for inputing parameters
		actor_treeview_librarymanager.getSelectionModel().selectedItemProperty().addListener(
						 (observable, oldValue, newValue) -> { 
						 								
						 							 //clear the previous return
													 operation_return_pane.setContent(new Label());
													 
						 							 clickedOp = newValue.getValue();
						 							 GridPane op = operationPanels.get(clickedOp);
						 							 VBox vb = opInvariantPanel.get(clickedOp);
						 							 
						 							 //op pannel
						 							 if (op != null) {
						 								 operation_paras.setContent(operationPanels.get(newValue.getValue()));
						 								 
						 								 ObservableList<Node> l = operationPanels.get(newValue.getValue()).getChildren();
						 								 choosenOperation = new LinkedList<TextField>();
						 								 for (Node n : l) {
						 								 	 if (n instanceof TextField) {
						 								 	 	choosenOperation.add((TextField)n);
						 								 	  }
						 								 }
						 								 
						 								 definition.setText(definitions_map.get(newValue.getValue()));
						 								 precondition.setText(preconditions_map.get(newValue.getValue()));
						 								 postcondition.setText(postconditions_map.get(newValue.getValue()));
						 								 
						 						     }
						 							 else {
						 								 Label l = new Label(newValue.getValue() + " is no contract information in requirement model.");
						 								 l.setPadding(new Insets(8, 8, 8, 8));
						 								 operation_paras.setContent(l);
						 							 }	
						 							 
						 							 //op invariants
						 							 if (vb != null) {
						 							 	ScrollPane scrollPane = new ScrollPane(vb);
						 							 	scrollPane.setFitToWidth(true);
						 							 	invariants_panes.setMaxHeight(200); 
						 							 	//all_invariant_pane.setContent(scrollPane);	
						 							 	
						 							 	invariants_panes.setContent(scrollPane);
						 							 } else {
						 							 	 Label l = new Label(newValue.getValue() + " is no related invariants");
						 							     l.setPadding(new Insets(8, 8, 8, 8));
						 							     invariants_panes.setContent(l);
						 							 }
						 							 
						 							 //reset pre- and post-conditions area color
						 							 precondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF ");
						 							 postcondition.setStyle("-fx-background-color:#FFFFFF; -fx-control-inner-background: #FFFFFF");
						 							 //reset condition panel title
						 							 precondition_pane.setText("Precondition");
						 							 postcondition_pane.setText("Postcondition");
						 						} 
						 				);
	}

	/**
	*    Execute Operation
	*/
	@FXML
	public void execute(ActionEvent event) {
		
		switch (clickedOp) {
		case "registerAccount" : registerAccount(); break;
		case "searchBookByBarCode" : searchBookByBarCode(); break;
		case "searchBookByTitle" : searchBookByTitle(); break;
		case "searchBookByAuthor" : searchBookByAuthor(); break;
		case "searchBookByISBN" : searchBookByISBN(); break;
		case "searchBookBySubject" : searchBookBySubject(); break;
		case "makeReservation" : makeReservation(); break;
		case "cancelReservation" : cancelReservation(); break;
		case "borrowBook" : borrowBook(); break;
		case "returnBook" : returnBook(); break;
		case "renewBook" : renewBook(); break;
		case "payOverDueFee" : payOverDueFee(); break;
		case "dueSoonNotification" : dueSoonNotification(); break;
		case "checkOverDueandComputeOverDueFee" : checkOverDueandComputeOverDueFee(); break;
		case "countDownSuspensionDay" : countDownSuspensionDay(); break;
		case "listBorrowHistory" : listBorrowHistory(); break;
		case "listHodingBook" : listHodingBook(); break;
		case "listOverDueBook" : listOverDueBook(); break;
		case "listReservationBook" : listReservationBook(); break;
		case "listRecommendBook" : listRecommendBook(); break;
		case "recommendBook" : recommendBook(); break;
		case "createStudent" : createStudent(); break;
		case "modifyStudent" : modifyStudent(); break;
		case "createFaculty" : createFaculty(); break;
		case "modifyFaculty" : modifyFaculty(); break;
		case "sendNotificationEmail" : sendNotificationEmail(); break;
		case "createUser" : createUser(); break;
		case "queryUser" : queryUser(); break;
		case "modifyUser" : modifyUser(); break;
		case "deleteUser" : deleteUser(); break;
		case "createBook" : createBook(); break;
		case "queryBook" : queryBook(); break;
		case "modifyBook" : modifyBook(); break;
		case "deleteBook" : deleteBook(); break;
		case "createSubject" : createSubject(); break;
		case "querySubject" : querySubject(); break;
		case "modifySubject" : modifySubject(); break;
		case "deleteSubject" : deleteSubject(); break;
		case "addBookCopy" : addBookCopy(); break;
		case "queryBookCopy" : queryBookCopy(); break;
		case "modifyBookCopy" : modifyBookCopy(); break;
		case "deleteBookCopy" : deleteBookCopy(); break;
		case "createLibrarian" : createLibrarian(); break;
		case "queryLibrarian" : queryLibrarian(); break;
		case "modifyLibrarian" : modifyLibrarian(); break;
		case "deleteLibrarian" : deleteLibrarian(); break;
		case "inputAccount" : inputAccount(); break;
		case "inputPassword" : inputPassword(); break;
		case "inputAccount1" : inputAccount1(); break;
		case "inputPasswordNew" : inputPasswordNew1(); break;

		case "ensurePassword1" : ensurePassword1(); break;
		case "inputPassword1" : inputPassword1(); break;
		case "inputCard1" : inputCard1(); break;
		case "inputPassword2" : inputPassword2(); break;
		case "inputVerificationcode" : inputVerificationcode(); break;
		case "inputCard12" : inputCard12(); break;
		case "inputPassword11" : inputPassword11(); break;
		case "ensureDeleteAccount" : ensureDeleteAccount(); break;
		case "inputBookInformation" : inputBookInformation(); break;
		case "inputCardID" : inputCardID(); break;
		case "exitStytem" : exitStytem(); break;
		
		}
		
		System.out.println("execute buttion clicked");
		
		//checking relevant invariants
		opInvairantPanelUpdate();
	}

	/**
	*    Refresh All
	*/		
	@FXML
	public void refresh(ActionEvent event) {
		
		refreshAll();
		System.out.println("refresh all");
	}		
	
	/**
	*    Save All
	*/			
	@FXML
	public void save(ActionEvent event) {
		
		Stage stage = (Stage) mainPane.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save State to File");
		fileChooser.setInitialFileName("*.state");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("RMCode State File", "*.state"));
		
		File file = fileChooser.showSaveDialog(stage);
		
		if (file != null) {
			System.out.println("save state to file " + file.getAbsolutePath());				
			EntityManager.save(file);
		}
	}
	
	/**
	*    Load All
	*/			
	@FXML
	public void load(ActionEvent event) {
		
		Stage stage = (Stage) mainPane.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open State File");
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("RMCode State File", "*.state"));
		
		File file = fileChooser.showOpenDialog(stage);
		
		if (file != null) {
			System.out.println("choose file" + file.getAbsolutePath());
			EntityManager.load(file); 
		}
		
		//refresh GUI after load data
		refreshAll();
	}
	
	
	//precondition unsat dialog
	public void preconditionUnSat() {
		
		Alert alert = new Alert(AlertType.WARNING, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.getDialogPane().setContentText("Precondtion is not satisfied");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait();	
	}
	
	//postcondition unsat dialog
	public void postconditionUnSat() {
		
		Alert alert = new Alert(AlertType.WARNING, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.getDialogPane().setContentText("Postcondtion is not satisfied");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait();	
	}

	public void thirdpartyServiceUnSat() {
		
		Alert alert = new Alert(AlertType.WARNING, "");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainPane.getScene().getWindow());
        alert.getDialogPane().setContentText("third party service is exception");
        alert.getDialogPane().setHeaderText(null);
        alert.showAndWait();	
	}		
	
	
	public void registerAccount() {
		
		System.out.println("execute registerAccount");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: registerAccount in service: SearchBook ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(((Object) searchbook_service).registerAccount(
			registerAccount_userID_t.getText(),
			registerAccount_email_t.getText(),
			registerAccount_password_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void searchBookByBarCode() {
		
		System.out.println("execute searchBookByBarCode");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: searchBookByBarCode in service: SearchBook ");
		
		try {
			//invoke op with parameters
					List<Book> result = searchbook_service.searchBookByBarCode(
					searchBookByBarCode_barcode_t.getText()
					);
				
					//binding result to GUI
					TableView<Map<String, String>> tableBook = new TableView<Map<String, String>>();
					TableColumn<Map<String, String>, String> tableBook_CallNo = new TableColumn<Map<String, String>, String>("CallNo");
					tableBook_CallNo.setMinWidth("CallNo".length()*10);
					tableBook_CallNo.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("CallNo"));
					    }
					});	
					tableBook.getColumns().add(tableBook_CallNo);
					TableColumn<Map<String, String>, String> tableBook_Title = new TableColumn<Map<String, String>, String>("Title");
					tableBook_Title.setMinWidth("Title".length()*10);
					tableBook_Title.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Title"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Title);
					TableColumn<Map<String, String>, String> tableBook_Edition = new TableColumn<Map<String, String>, String>("Edition");
					tableBook_Edition.setMinWidth("Edition".length()*10);
					tableBook_Edition.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Edition"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Edition);
					TableColumn<Map<String, String>, String> tableBook_Author = new TableColumn<Map<String, String>, String>("Author");
					tableBook_Author.setMinWidth("Author".length()*10);
					tableBook_Author.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Author"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Author);
					TableColumn<Map<String, String>, String> tableBook_Publisher = new TableColumn<Map<String, String>, String>("Publisher");
					tableBook_Publisher.setMinWidth("Publisher".length()*10);
					tableBook_Publisher.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Publisher"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Publisher);
					TableColumn<Map<String, String>, String> tableBook_Description = new TableColumn<Map<String, String>, String>("Description");
					tableBook_Description.setMinWidth("Description".length()*10);
					tableBook_Description.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Description"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Description);
					TableColumn<Map<String, String>, String> tableBook_ISBn = new TableColumn<Map<String, String>, String>("ISBn");
					tableBook_ISBn.setMinWidth("ISBn".length()*10);
					tableBook_ISBn.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("ISBn"));
					    }
					});	
					tableBook.getColumns().add(tableBook_ISBn);
					TableColumn<Map<String, String>, String> tableBook_CopyNum = new TableColumn<Map<String, String>, String>("CopyNum");
					tableBook_CopyNum.setMinWidth("CopyNum".length()*10);
					tableBook_CopyNum.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("CopyNum"));
					    }
					});	
					tableBook.getColumns().add(tableBook_CopyNum);
					
					ObservableList<Map<String, String>> dataBook = FXCollections.observableArrayList();
					for (Book r : result) {
						Map<String, String> unit = new HashMap<String, String>();
						if (r.getCallNo() != null)
							unit.put("CallNo", String.valueOf(r.getCallNo()));
						else
							unit.put("CallNo", "");
						if (r.getTitle() != null)
							unit.put("Title", String.valueOf(r.getTitle()));
						else
							unit.put("Title", "");
						if (r.getEdition() != null)
							unit.put("Edition", String.valueOf(r.getEdition()));
						else
							unit.put("Edition", "");
						if (r.getAuthor() != null)
							unit.put("Author", String.valueOf(r.getAuthor()));
						else
							unit.put("Author", "");
						if (r.getPublisher() != null)
							unit.put("Publisher", String.valueOf(r.getPublisher()));
						else
							unit.put("Publisher", "");
						if (r.getDescription() != null)
							unit.put("Description", String.valueOf(r.getDescription()));
						else
							unit.put("Description", "");
						if (r.getISBn() != null)
							unit.put("ISBn", String.valueOf(r.getISBn()));
						else
							unit.put("ISBn", "");
						unit.put("CopyNum", String.valueOf(r.getCopyNum()));
						dataBook.add(unit);
					}
					
					tableBook.setItems(dataBook);
					operation_return_pane.setContent(tableBook);
				
			
			//return type is entity
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void searchBookByTitle() {
		
		System.out.println("execute searchBookByTitle");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: searchBookByTitle in service: SearchBook ");
		
		try {
			//invoke op with parameters
					List<Book> result = searchbook_service.searchBookByTitle(
					searchBookByTitle_title_t.getText()
					);
				
					//binding result to GUI
					TableView<Map<String, String>> tableBook = new TableView<Map<String, String>>();
					TableColumn<Map<String, String>, String> tableBook_CallNo = new TableColumn<Map<String, String>, String>("CallNo");
					tableBook_CallNo.setMinWidth("CallNo".length()*10);
					tableBook_CallNo.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("CallNo"));
					    }
					});	
					tableBook.getColumns().add(tableBook_CallNo);
					TableColumn<Map<String, String>, String> tableBook_Title = new TableColumn<Map<String, String>, String>("Title");
					tableBook_Title.setMinWidth("Title".length()*10);
					tableBook_Title.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Title"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Title);
					TableColumn<Map<String, String>, String> tableBook_Edition = new TableColumn<Map<String, String>, String>("Edition");
					tableBook_Edition.setMinWidth("Edition".length()*10);
					tableBook_Edition.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Edition"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Edition);
					TableColumn<Map<String, String>, String> tableBook_Author = new TableColumn<Map<String, String>, String>("Author");
					tableBook_Author.setMinWidth("Author".length()*10);
					tableBook_Author.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Author"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Author);
					TableColumn<Map<String, String>, String> tableBook_Publisher = new TableColumn<Map<String, String>, String>("Publisher");
					tableBook_Publisher.setMinWidth("Publisher".length()*10);
					tableBook_Publisher.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Publisher"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Publisher);
					TableColumn<Map<String, String>, String> tableBook_Description = new TableColumn<Map<String, String>, String>("Description");
					tableBook_Description.setMinWidth("Description".length()*10);
					tableBook_Description.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Description"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Description);
					TableColumn<Map<String, String>, String> tableBook_ISBn = new TableColumn<Map<String, String>, String>("ISBn");
					tableBook_ISBn.setMinWidth("ISBn".length()*10);
					tableBook_ISBn.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("ISBn"));
					    }
					});	
					tableBook.getColumns().add(tableBook_ISBn);
					TableColumn<Map<String, String>, String> tableBook_CopyNum = new TableColumn<Map<String, String>, String>("CopyNum");
					tableBook_CopyNum.setMinWidth("CopyNum".length()*10);
					tableBook_CopyNum.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("CopyNum"));
					    }
					});	
					tableBook.getColumns().add(tableBook_CopyNum);
					
					ObservableList<Map<String, String>> dataBook = FXCollections.observableArrayList();
					for (Book r : result) {
						Map<String, String> unit = new HashMap<String, String>();
						if (r.getCallNo() != null)
							unit.put("CallNo", String.valueOf(r.getCallNo()));
						else
							unit.put("CallNo", "");
						if (r.getTitle() != null)
							unit.put("Title", String.valueOf(r.getTitle()));
						else
							unit.put("Title", "");
						if (r.getEdition() != null)
							unit.put("Edition", String.valueOf(r.getEdition()));
						else
							unit.put("Edition", "");
						if (r.getAuthor() != null)
							unit.put("Author", String.valueOf(r.getAuthor()));
						else
							unit.put("Author", "");
						if (r.getPublisher() != null)
							unit.put("Publisher", String.valueOf(r.getPublisher()));
						else
							unit.put("Publisher", "");
						if (r.getDescription() != null)
							unit.put("Description", String.valueOf(r.getDescription()));
						else
							unit.put("Description", "");
						if (r.getISBn() != null)
							unit.put("ISBn", String.valueOf(r.getISBn()));
						else
							unit.put("ISBn", "");
						unit.put("CopyNum", String.valueOf(r.getCopyNum()));
						dataBook.add(unit);
					}
					
					tableBook.setItems(dataBook);
					operation_return_pane.setContent(tableBook);
				
			
			//return type is entity
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void searchBookByAuthor() {
		
		System.out.println("execute searchBookByAuthor");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: searchBookByAuthor in service: SearchBook ");
		
		try {
			//invoke op with parameters
					List<Book> result = searchbook_service.searchBookByAuthor(
					searchBookByAuthor_authorname_t.getText()
					);
				
					//binding result to GUI
					TableView<Map<String, String>> tableBook = new TableView<Map<String, String>>();
					TableColumn<Map<String, String>, String> tableBook_CallNo = new TableColumn<Map<String, String>, String>("CallNo");
					tableBook_CallNo.setMinWidth("CallNo".length()*10);
					tableBook_CallNo.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("CallNo"));
					    }
					});	
					tableBook.getColumns().add(tableBook_CallNo);
					TableColumn<Map<String, String>, String> tableBook_Title = new TableColumn<Map<String, String>, String>("Title");
					tableBook_Title.setMinWidth("Title".length()*10);
					tableBook_Title.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Title"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Title);
					TableColumn<Map<String, String>, String> tableBook_Edition = new TableColumn<Map<String, String>, String>("Edition");
					tableBook_Edition.setMinWidth("Edition".length()*10);
					tableBook_Edition.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Edition"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Edition);
					TableColumn<Map<String, String>, String> tableBook_Author = new TableColumn<Map<String, String>, String>("Author");
					tableBook_Author.setMinWidth("Author".length()*10);
					tableBook_Author.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Author"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Author);
					TableColumn<Map<String, String>, String> tableBook_Publisher = new TableColumn<Map<String, String>, String>("Publisher");
					tableBook_Publisher.setMinWidth("Publisher".length()*10);
					tableBook_Publisher.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Publisher"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Publisher);
					TableColumn<Map<String, String>, String> tableBook_Description = new TableColumn<Map<String, String>, String>("Description");
					tableBook_Description.setMinWidth("Description".length()*10);
					tableBook_Description.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Description"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Description);
					TableColumn<Map<String, String>, String> tableBook_ISBn = new TableColumn<Map<String, String>, String>("ISBn");
					tableBook_ISBn.setMinWidth("ISBn".length()*10);
					tableBook_ISBn.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("ISBn"));
					    }
					});	
					tableBook.getColumns().add(tableBook_ISBn);
					TableColumn<Map<String, String>, String> tableBook_CopyNum = new TableColumn<Map<String, String>, String>("CopyNum");
					tableBook_CopyNum.setMinWidth("CopyNum".length()*10);
					tableBook_CopyNum.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("CopyNum"));
					    }
					});	
					tableBook.getColumns().add(tableBook_CopyNum);
					
					ObservableList<Map<String, String>> dataBook = FXCollections.observableArrayList();
					for (Book r : result) {
						Map<String, String> unit = new HashMap<String, String>();
						if (r.getCallNo() != null)
							unit.put("CallNo", String.valueOf(r.getCallNo()));
						else
							unit.put("CallNo", "");
						if (r.getTitle() != null)
							unit.put("Title", String.valueOf(r.getTitle()));
						else
							unit.put("Title", "");
						if (r.getEdition() != null)
							unit.put("Edition", String.valueOf(r.getEdition()));
						else
							unit.put("Edition", "");
						if (r.getAuthor() != null)
							unit.put("Author", String.valueOf(r.getAuthor()));
						else
							unit.put("Author", "");
						if (r.getPublisher() != null)
							unit.put("Publisher", String.valueOf(r.getPublisher()));
						else
							unit.put("Publisher", "");
						if (r.getDescription() != null)
							unit.put("Description", String.valueOf(r.getDescription()));
						else
							unit.put("Description", "");
						if (r.getISBn() != null)
							unit.put("ISBn", String.valueOf(r.getISBn()));
						else
							unit.put("ISBn", "");
						unit.put("CopyNum", String.valueOf(r.getCopyNum()));
						dataBook.add(unit);
					}
					
					tableBook.setItems(dataBook);
					operation_return_pane.setContent(tableBook);
				
			
			//return type is entity
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void searchBookByISBN() {
		
		System.out.println("execute searchBookByISBN");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: searchBookByISBN in service: SearchBook ");
		
		try {
			//invoke op with parameters
					List<Book> result = searchbook_service.searchBookByISBN(
					searchBookByISBN_iSBNnumber_t.getText()
					);
				
					//binding result to GUI
					TableView<Map<String, String>> tableBook = new TableView<Map<String, String>>();
					TableColumn<Map<String, String>, String> tableBook_CallNo = new TableColumn<Map<String, String>, String>("CallNo");
					tableBook_CallNo.setMinWidth("CallNo".length()*10);
					tableBook_CallNo.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("CallNo"));
					    }
					});	
					tableBook.getColumns().add(tableBook_CallNo);
					TableColumn<Map<String, String>, String> tableBook_Title = new TableColumn<Map<String, String>, String>("Title");
					tableBook_Title.setMinWidth("Title".length()*10);
					tableBook_Title.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Title"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Title);
					TableColumn<Map<String, String>, String> tableBook_Edition = new TableColumn<Map<String, String>, String>("Edition");
					tableBook_Edition.setMinWidth("Edition".length()*10);
					tableBook_Edition.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Edition"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Edition);
					TableColumn<Map<String, String>, String> tableBook_Author = new TableColumn<Map<String, String>, String>("Author");
					tableBook_Author.setMinWidth("Author".length()*10);
					tableBook_Author.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Author"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Author);
					TableColumn<Map<String, String>, String> tableBook_Publisher = new TableColumn<Map<String, String>, String>("Publisher");
					tableBook_Publisher.setMinWidth("Publisher".length()*10);
					tableBook_Publisher.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Publisher"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Publisher);
					TableColumn<Map<String, String>, String> tableBook_Description = new TableColumn<Map<String, String>, String>("Description");
					tableBook_Description.setMinWidth("Description".length()*10);
					tableBook_Description.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Description"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Description);
					TableColumn<Map<String, String>, String> tableBook_ISBn = new TableColumn<Map<String, String>, String>("ISBn");
					tableBook_ISBn.setMinWidth("ISBn".length()*10);
					tableBook_ISBn.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("ISBn"));
					    }
					});	
					tableBook.getColumns().add(tableBook_ISBn);
					TableColumn<Map<String, String>, String> tableBook_CopyNum = new TableColumn<Map<String, String>, String>("CopyNum");
					tableBook_CopyNum.setMinWidth("CopyNum".length()*10);
					tableBook_CopyNum.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("CopyNum"));
					    }
					});	
					tableBook.getColumns().add(tableBook_CopyNum);
					
					ObservableList<Map<String, String>> dataBook = FXCollections.observableArrayList();
					for (Book r : result) {
						Map<String, String> unit = new HashMap<String, String>();
						if (r.getCallNo() != null)
							unit.put("CallNo", String.valueOf(r.getCallNo()));
						else
							unit.put("CallNo", "");
						if (r.getTitle() != null)
							unit.put("Title", String.valueOf(r.getTitle()));
						else
							unit.put("Title", "");
						if (r.getEdition() != null)
							unit.put("Edition", String.valueOf(r.getEdition()));
						else
							unit.put("Edition", "");
						if (r.getAuthor() != null)
							unit.put("Author", String.valueOf(r.getAuthor()));
						else
							unit.put("Author", "");
						if (r.getPublisher() != null)
							unit.put("Publisher", String.valueOf(r.getPublisher()));
						else
							unit.put("Publisher", "");
						if (r.getDescription() != null)
							unit.put("Description", String.valueOf(r.getDescription()));
						else
							unit.put("Description", "");
						if (r.getISBn() != null)
							unit.put("ISBn", String.valueOf(r.getISBn()));
						else
							unit.put("ISBn", "");
						unit.put("CopyNum", String.valueOf(r.getCopyNum()));
						dataBook.add(unit);
					}
					
					tableBook.setItems(dataBook);
					operation_return_pane.setContent(tableBook);
				
			
			//return type is entity
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void searchBookBySubject() {
		
		System.out.println("execute searchBookBySubject");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: searchBookBySubject in service: SearchBook ");
		
		try {
			//invoke op with parameters
					List<Book> result = searchbook_service.searchBookBySubject(
					searchBookBySubject_subject_t.getText()
					);
				
					//binding result to GUI
					TableView<Map<String, String>> tableBook = new TableView<Map<String, String>>();
					TableColumn<Map<String, String>, String> tableBook_CallNo = new TableColumn<Map<String, String>, String>("CallNo");
					tableBook_CallNo.setMinWidth("CallNo".length()*10);
					tableBook_CallNo.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("CallNo"));
					    }
					});	
					tableBook.getColumns().add(tableBook_CallNo);
					TableColumn<Map<String, String>, String> tableBook_Title = new TableColumn<Map<String, String>, String>("Title");
					tableBook_Title.setMinWidth("Title".length()*10);
					tableBook_Title.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Title"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Title);
					TableColumn<Map<String, String>, String> tableBook_Edition = new TableColumn<Map<String, String>, String>("Edition");
					tableBook_Edition.setMinWidth("Edition".length()*10);
					tableBook_Edition.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Edition"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Edition);
					TableColumn<Map<String, String>, String> tableBook_Author = new TableColumn<Map<String, String>, String>("Author");
					tableBook_Author.setMinWidth("Author".length()*10);
					tableBook_Author.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Author"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Author);
					TableColumn<Map<String, String>, String> tableBook_Publisher = new TableColumn<Map<String, String>, String>("Publisher");
					tableBook_Publisher.setMinWidth("Publisher".length()*10);
					tableBook_Publisher.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Publisher"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Publisher);
					TableColumn<Map<String, String>, String> tableBook_Description = new TableColumn<Map<String, String>, String>("Description");
					tableBook_Description.setMinWidth("Description".length()*10);
					tableBook_Description.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Description"));
					    }
					});	
					tableBook.getColumns().add(tableBook_Description);
					TableColumn<Map<String, String>, String> tableBook_ISBn = new TableColumn<Map<String, String>, String>("ISBn");
					tableBook_ISBn.setMinWidth("ISBn".length()*10);
					tableBook_ISBn.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("ISBn"));
					    }
					});	
					tableBook.getColumns().add(tableBook_ISBn);
					TableColumn<Map<String, String>, String> tableBook_CopyNum = new TableColumn<Map<String, String>, String>("CopyNum");
					tableBook_CopyNum.setMinWidth("CopyNum".length()*10);
					tableBook_CopyNum.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("CopyNum"));
					    }
					});	
					tableBook.getColumns().add(tableBook_CopyNum);
					
					ObservableList<Map<String, String>> dataBook = FXCollections.observableArrayList();
					for (Book r : result) {
						Map<String, String> unit = new HashMap<String, String>();
						if (r.getCallNo() != null)
							unit.put("CallNo", String.valueOf(r.getCallNo()));
						else
							unit.put("CallNo", "");
						if (r.getTitle() != null)
							unit.put("Title", String.valueOf(r.getTitle()));
						else
							unit.put("Title", "");
						if (r.getEdition() != null)
							unit.put("Edition", String.valueOf(r.getEdition()));
						else
							unit.put("Edition", "");
						if (r.getAuthor() != null)
							unit.put("Author", String.valueOf(r.getAuthor()));
						else
							unit.put("Author", "");
						if (r.getPublisher() != null)
							unit.put("Publisher", String.valueOf(r.getPublisher()));
						else
							unit.put("Publisher", "");
						if (r.getDescription() != null)
							unit.put("Description", String.valueOf(r.getDescription()));
						else
							unit.put("Description", "");
						if (r.getISBn() != null)
							unit.put("ISBn", String.valueOf(r.getISBn()));
						else
							unit.put("ISBn", "");
						unit.put("CopyNum", String.valueOf(r.getCopyNum()));
						dataBook.add(unit);
					}
					
					tableBook.setItems(dataBook);
					operation_return_pane.setContent(tableBook);
				
			
			//return type is entity
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void makeReservation() {
		
		System.out.println("execute makeReservation");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: makeReservation in service: LibraryManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(librarymanagementsystemsystem_service.makeReservation(
			makeReservation_uid_t.getText(),
			makeReservation_barcode_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void cancelReservation() {
		
		System.out.println("execute cancelReservation");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: cancelReservation in service: LibraryManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(librarymanagementsystemsystem_service.cancelReservation(
			cancelReservation_uid_t.getText(),
			cancelReservation_barcode_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void borrowBook() {
		
		System.out.println("execute borrowBook");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: borrowBook in service: LibraryManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(librarymanagementsystemsystem_service.borrowBook(
			borrowBook_uid_t.getText(),
			borrowBook_barcode_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void returnBook() {
		
		System.out.println("execute returnBook");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: returnBook in service: LibraryManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(librarymanagementsystemsystem_service.returnBook(
			returnBook_barcode_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void renewBook() {
		
		System.out.println("execute renewBook");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: renewBook in service: LibraryManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(librarymanagementsystemsystem_service.renewBook(
			renewBook_uid_t.getText(),
			renewBook_barcode_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void payOverDueFee() {
		
		System.out.println("execute payOverDueFee");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: payOverDueFee in service: LibraryManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(librarymanagementsystemsystem_service.payOverDueFee(
			payOverDueFee_uid_t.getText(),
			Float.valueOf(payOverDueFee_fee_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void dueSoonNotification() {
		
		System.out.println("execute dueSoonNotification");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: dueSoonNotification in service: LibraryManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//no return type
			librarymanagementsystemsystem_service.dueSoonNotification(
			);	
			
			Label l = new Label("this operation is no return");
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void checkOverDueandComputeOverDueFee() {
		
		System.out.println("execute checkOverDueandComputeOverDueFee");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: checkOverDueandComputeOverDueFee in service: LibraryManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//no return type
			librarymanagementsystemsystem_service.checkOverDueandComputeOverDueFee(
			);	
			
			Label l = new Label("this operation is no return");
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void countDownSuspensionDay() {
		
		System.out.println("execute countDownSuspensionDay");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: countDownSuspensionDay in service: LibraryManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//no return type
			librarymanagementsystemsystem_service.countDownSuspensionDay(
			);	
			
			Label l = new Label("this operation is no return");
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void listBorrowHistory() {
		
		System.out.println("execute listBorrowHistory");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: listBorrowHistory in service: ListBookHistory ");
		
		try {
			//invoke op with parameters
					List<Loan> result = listbookhistory_service.listBorrowHistory(
					listBorrowHistory_uid_t.getText()
					);
				
					//binding result to GUI
					TableView<Map<String, String>> tableLoan = new TableView<Map<String, String>>();
					TableColumn<Map<String, String>, String> tableLoan_LoanDate = new TableColumn<Map<String, String>, String>("LoanDate");
					tableLoan_LoanDate.setMinWidth("LoanDate".length()*10);
					tableLoan_LoanDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("LoanDate"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_LoanDate);
					TableColumn<Map<String, String>, String> tableLoan_RenewDate = new TableColumn<Map<String, String>, String>("RenewDate");
					tableLoan_RenewDate.setMinWidth("RenewDate".length()*10);
					tableLoan_RenewDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("RenewDate"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_RenewDate);
					TableColumn<Map<String, String>, String> tableLoan_DueDate = new TableColumn<Map<String, String>, String>("DueDate");
					tableLoan_DueDate.setMinWidth("DueDate".length()*10);
					tableLoan_DueDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("DueDate"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_DueDate);
					TableColumn<Map<String, String>, String> tableLoan_ReturnDate = new TableColumn<Map<String, String>, String>("ReturnDate");
					tableLoan_ReturnDate.setMinWidth("ReturnDate".length()*10);
					tableLoan_ReturnDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("ReturnDate"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_ReturnDate);
					TableColumn<Map<String, String>, String> tableLoan_RenewedTimes = new TableColumn<Map<String, String>, String>("RenewedTimes");
					tableLoan_RenewedTimes.setMinWidth("RenewedTimes".length()*10);
					tableLoan_RenewedTimes.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("RenewedTimes"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_RenewedTimes);
					TableColumn<Map<String, String>, String> tableLoan_IsReturned = new TableColumn<Map<String, String>, String>("IsReturned");
					tableLoan_IsReturned.setMinWidth("IsReturned".length()*10);
					tableLoan_IsReturned.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("IsReturned"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_IsReturned);
					TableColumn<Map<String, String>, String> tableLoan_OverDueFee = new TableColumn<Map<String, String>, String>("OverDueFee");
					tableLoan_OverDueFee.setMinWidth("OverDueFee".length()*10);
					tableLoan_OverDueFee.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("OverDueFee"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_OverDueFee);
					TableColumn<Map<String, String>, String> tableLoan_OverDue3Days = new TableColumn<Map<String, String>, String>("OverDue3Days");
					tableLoan_OverDue3Days.setMinWidth("OverDue3Days".length()*10);
					tableLoan_OverDue3Days.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("OverDue3Days"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_OverDue3Days);
					TableColumn<Map<String, String>, String> tableLoan_OverDue10Days = new TableColumn<Map<String, String>, String>("OverDue10Days");
					tableLoan_OverDue10Days.setMinWidth("OverDue10Days".length()*10);
					tableLoan_OverDue10Days.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("OverDue10Days"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_OverDue10Days);
					TableColumn<Map<String, String>, String> tableLoan_OverDue17Days = new TableColumn<Map<String, String>, String>("OverDue17Days");
					tableLoan_OverDue17Days.setMinWidth("OverDue17Days".length()*10);
					tableLoan_OverDue17Days.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("OverDue17Days"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_OverDue17Days);
					TableColumn<Map<String, String>, String> tableLoan_OverDue31Days = new TableColumn<Map<String, String>, String>("OverDue31Days");
					tableLoan_OverDue31Days.setMinWidth("OverDue31Days".length()*10);
					tableLoan_OverDue31Days.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("OverDue31Days"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_OverDue31Days);
					
					ObservableList<Map<String, String>> dataLoan = FXCollections.observableArrayList();
					for (Loan r : result) {
						Map<String, String> unit = new HashMap<String, String>();
						if (r.getLoanDate() != null)
							unit.put("LoanDate", r.getLoanDate().format(dateformatter));
						else
							unit.put("LoanDate", "");
						if (r.getRenewDate() != null)
							unit.put("RenewDate", r.getRenewDate().format(dateformatter));
						else
							unit.put("RenewDate", "");
						if (r.getDueDate() != null)
							unit.put("DueDate", r.getDueDate().format(dateformatter));
						else
							unit.put("DueDate", "");
						if (r.getReturnDate() != null)
							unit.put("ReturnDate", r.getReturnDate().format(dateformatter));
						else
							unit.put("ReturnDate", "");
						unit.put("RenewedTimes", String.valueOf(r.getRenewedTimes()));
						unit.put("IsReturned", String.valueOf(r.getIsReturned()));
						unit.put("OverDueFee", String.valueOf(r.getOverDueFee()));
						unit.put("OverDue3Days", String.valueOf(r.getOverDue3Days()));
						unit.put("OverDue10Days", String.valueOf(r.getOverDue10Days()));
						unit.put("OverDue17Days", String.valueOf(r.getOverDue17Days()));
						unit.put("OverDue31Days", String.valueOf(r.getOverDue31Days()));
						dataLoan.add(unit);
					}
					
					tableLoan.setItems(dataLoan);
					operation_return_pane.setContent(tableLoan);
				
			
			//return type is entity
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void listHodingBook() {
		
		System.out.println("execute listHodingBook");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: listHodingBook in service: ListBookHistory ");
		
		try {
			//invoke op with parameters
					List<Loan> result = listbookhistory_service.listHodingBook(
					listHodingBook_uid_t.getText()
					);
				
					//binding result to GUI
					TableView<Map<String, String>> tableLoan = new TableView<Map<String, String>>();
					TableColumn<Map<String, String>, String> tableLoan_LoanDate = new TableColumn<Map<String, String>, String>("LoanDate");
					tableLoan_LoanDate.setMinWidth("LoanDate".length()*10);
					tableLoan_LoanDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("LoanDate"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_LoanDate);
					TableColumn<Map<String, String>, String> tableLoan_RenewDate = new TableColumn<Map<String, String>, String>("RenewDate");
					tableLoan_RenewDate.setMinWidth("RenewDate".length()*10);
					tableLoan_RenewDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("RenewDate"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_RenewDate);
					TableColumn<Map<String, String>, String> tableLoan_DueDate = new TableColumn<Map<String, String>, String>("DueDate");
					tableLoan_DueDate.setMinWidth("DueDate".length()*10);
					tableLoan_DueDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("DueDate"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_DueDate);
					TableColumn<Map<String, String>, String> tableLoan_ReturnDate = new TableColumn<Map<String, String>, String>("ReturnDate");
					tableLoan_ReturnDate.setMinWidth("ReturnDate".length()*10);
					tableLoan_ReturnDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("ReturnDate"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_ReturnDate);
					TableColumn<Map<String, String>, String> tableLoan_RenewedTimes = new TableColumn<Map<String, String>, String>("RenewedTimes");
					tableLoan_RenewedTimes.setMinWidth("RenewedTimes".length()*10);
					tableLoan_RenewedTimes.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("RenewedTimes"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_RenewedTimes);
					TableColumn<Map<String, String>, String> tableLoan_IsReturned = new TableColumn<Map<String, String>, String>("IsReturned");
					tableLoan_IsReturned.setMinWidth("IsReturned".length()*10);
					tableLoan_IsReturned.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("IsReturned"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_IsReturned);
					TableColumn<Map<String, String>, String> tableLoan_OverDueFee = new TableColumn<Map<String, String>, String>("OverDueFee");
					tableLoan_OverDueFee.setMinWidth("OverDueFee".length()*10);
					tableLoan_OverDueFee.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("OverDueFee"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_OverDueFee);
					TableColumn<Map<String, String>, String> tableLoan_OverDue3Days = new TableColumn<Map<String, String>, String>("OverDue3Days");
					tableLoan_OverDue3Days.setMinWidth("OverDue3Days".length()*10);
					tableLoan_OverDue3Days.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("OverDue3Days"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_OverDue3Days);
					TableColumn<Map<String, String>, String> tableLoan_OverDue10Days = new TableColumn<Map<String, String>, String>("OverDue10Days");
					tableLoan_OverDue10Days.setMinWidth("OverDue10Days".length()*10);
					tableLoan_OverDue10Days.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("OverDue10Days"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_OverDue10Days);
					TableColumn<Map<String, String>, String> tableLoan_OverDue17Days = new TableColumn<Map<String, String>, String>("OverDue17Days");
					tableLoan_OverDue17Days.setMinWidth("OverDue17Days".length()*10);
					tableLoan_OverDue17Days.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("OverDue17Days"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_OverDue17Days);
					TableColumn<Map<String, String>, String> tableLoan_OverDue31Days = new TableColumn<Map<String, String>, String>("OverDue31Days");
					tableLoan_OverDue31Days.setMinWidth("OverDue31Days".length()*10);
					tableLoan_OverDue31Days.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("OverDue31Days"));
					    }
					});	
					tableLoan.getColumns().add(tableLoan_OverDue31Days);
					
					ObservableList<Map<String, String>> dataLoan = FXCollections.observableArrayList();
					for (Loan r : result) {
						Map<String, String> unit = new HashMap<String, String>();
						if (r.getLoanDate() != null)
							unit.put("LoanDate", r.getLoanDate().format(dateformatter));
						else
							unit.put("LoanDate", "");
						if (r.getRenewDate() != null)
							unit.put("RenewDate", r.getRenewDate().format(dateformatter));
						else
							unit.put("RenewDate", "");
						if (r.getDueDate() != null)
							unit.put("DueDate", r.getDueDate().format(dateformatter));
						else
							unit.put("DueDate", "");
						if (r.getReturnDate() != null)
							unit.put("ReturnDate", r.getReturnDate().format(dateformatter));
						else
							unit.put("ReturnDate", "");
						unit.put("RenewedTimes", String.valueOf(r.getRenewedTimes()));
						unit.put("IsReturned", String.valueOf(r.getIsReturned()));
						unit.put("OverDueFee", String.valueOf(r.getOverDueFee()));
						unit.put("OverDue3Days", String.valueOf(r.getOverDue3Days()));
						unit.put("OverDue10Days", String.valueOf(r.getOverDue10Days()));
						unit.put("OverDue17Days", String.valueOf(r.getOverDue17Days()));
						unit.put("OverDue31Days", String.valueOf(r.getOverDue31Days()));
						dataLoan.add(unit);
					}
					
					tableLoan.setItems(dataLoan);
					operation_return_pane.setContent(tableLoan);
				
			
			//return type is entity
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void listOverDueBook() {
		
		System.out.println("execute listOverDueBook");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: listOverDueBook in service: ListBookHistory ");
		
		try {
			//invoke op with parameters
					List<BookCopy> result = listbookhistory_service.listOverDueBook(
					listOverDueBook_uid_t.getText()
					);
				
					//binding result to GUI
					TableView<Map<String, String>> tableBookCopy = new TableView<Map<String, String>>();
					TableColumn<Map<String, String>, String> tableBookCopy_Barcode = new TableColumn<Map<String, String>, String>("Barcode");
					tableBookCopy_Barcode.setMinWidth("Barcode".length()*10);
					tableBookCopy_Barcode.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Barcode"));
					    }
					});	
					tableBookCopy.getColumns().add(tableBookCopy_Barcode);
					TableColumn<Map<String, String>, String> tableBookCopy_Status = new TableColumn<Map<String, String>, String>("Status");
					tableBookCopy_Status.setMinWidth("Status".length()*10);
					tableBookCopy_Status.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Status"));
					    }
					});	
					tableBookCopy.getColumns().add(tableBookCopy_Status);
					TableColumn<Map<String, String>, String> tableBookCopy_Location = new TableColumn<Map<String, String>, String>("Location");
					tableBookCopy_Location.setMinWidth("Location".length()*10);
					tableBookCopy_Location.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Location"));
					    }
					});	
					tableBookCopy.getColumns().add(tableBookCopy_Location);
					TableColumn<Map<String, String>, String> tableBookCopy_IsReserved = new TableColumn<Map<String, String>, String>("IsReserved");
					tableBookCopy_IsReserved.setMinWidth("IsReserved".length()*10);
					tableBookCopy_IsReserved.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("IsReserved"));
					    }
					});	
					tableBookCopy.getColumns().add(tableBookCopy_IsReserved);
					
					ObservableList<Map<String, String>> dataBookCopy = FXCollections.observableArrayList();
					for (BookCopy r : result) {
						Map<String, String> unit = new HashMap<String, String>();
						if (r.getBarcode() != null)
							unit.put("Barcode", String.valueOf(r.getBarcode()));
						else
							unit.put("Barcode", "");
						unit.put("Status", String.valueOf(r.getStatus()));
						if (r.getLocation() != null)
							unit.put("Location", String.valueOf(r.getLocation()));
						else
							unit.put("Location", "");
						unit.put("IsReserved", String.valueOf(r.getIsReserved()));
						dataBookCopy.add(unit);
					}
					
					tableBookCopy.setItems(dataBookCopy);
					operation_return_pane.setContent(tableBookCopy);
				
			
			//return type is entity
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void listReservationBook() {
		
		System.out.println("execute listReservationBook");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: listReservationBook in service: ListBookHistory ");
		
		try {
			//invoke op with parameters
					List<BookCopy> result = listbookhistory_service.listReservationBook(
					listReservationBook_uid_t.getText()
					);
				
					//binding result to GUI
					TableView<Map<String, String>> tableBookCopy = new TableView<Map<String, String>>();
					TableColumn<Map<String, String>, String> tableBookCopy_Barcode = new TableColumn<Map<String, String>, String>("Barcode");
					tableBookCopy_Barcode.setMinWidth("Barcode".length()*10);
					tableBookCopy_Barcode.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Barcode"));
					    }
					});	
					tableBookCopy.getColumns().add(tableBookCopy_Barcode);
					TableColumn<Map<String, String>, String> tableBookCopy_Status = new TableColumn<Map<String, String>, String>("Status");
					tableBookCopy_Status.setMinWidth("Status".length()*10);
					tableBookCopy_Status.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Status"));
					    }
					});	
					tableBookCopy.getColumns().add(tableBookCopy_Status);
					TableColumn<Map<String, String>, String> tableBookCopy_Location = new TableColumn<Map<String, String>, String>("Location");
					tableBookCopy_Location.setMinWidth("Location".length()*10);
					tableBookCopy_Location.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("Location"));
					    }
					});	
					tableBookCopy.getColumns().add(tableBookCopy_Location);
					TableColumn<Map<String, String>, String> tableBookCopy_IsReserved = new TableColumn<Map<String, String>, String>("IsReserved");
					tableBookCopy_IsReserved.setMinWidth("IsReserved".length()*10);
					tableBookCopy_IsReserved.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("IsReserved"));
					    }
					});	
					tableBookCopy.getColumns().add(tableBookCopy_IsReserved);
					
					ObservableList<Map<String, String>> dataBookCopy = FXCollections.observableArrayList();
					for (BookCopy r : result) {
						Map<String, String> unit = new HashMap<String, String>();
						if (r.getBarcode() != null)
							unit.put("Barcode", String.valueOf(r.getBarcode()));
						else
							unit.put("Barcode", "");
						unit.put("Status", String.valueOf(r.getStatus()));
						if (r.getLocation() != null)
							unit.put("Location", String.valueOf(r.getLocation()));
						else
							unit.put("Location", "");
						unit.put("IsReserved", String.valueOf(r.getIsReserved()));
						dataBookCopy.add(unit);
					}
					
					tableBookCopy.setItems(dataBookCopy);
					operation_return_pane.setContent(tableBookCopy);
				
			
			//return type is entity
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void listRecommendBook() {
		
		System.out.println("execute listRecommendBook");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: listRecommendBook in service: ListBookHistory ");
		
		try {
			//invoke op with parameters
					List<RecommendBook> result = listbookhistory_service.listRecommendBook(
					listRecommendBook_uid_t.getText()
					);
				
					//binding result to GUI
					TableView<Map<String, String>> tableRecommendBook = new TableView<Map<String, String>>();
					TableColumn<Map<String, String>, String> tableRecommendBook_RecommendDate = new TableColumn<Map<String, String>, String>("RecommendDate");
					tableRecommendBook_RecommendDate.setMinWidth("RecommendDate".length()*10);
					tableRecommendBook_RecommendDate.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
						@Override
					    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
					        return new ReadOnlyStringWrapper(data.getValue().get("RecommendDate"));
					    }
					});	
					tableRecommendBook.getColumns().add(tableRecommendBook_RecommendDate);
					
					ObservableList<Map<String, String>> dataRecommendBook = FXCollections.observableArrayList();
					for (RecommendBook r : result) {
						Map<String, String> unit = new HashMap<String, String>();
						if (r.getRecommendDate() != null)
							unit.put("RecommendDate", r.getRecommendDate().format(dateformatter));
						else
							unit.put("RecommendDate", "");
						dataRecommendBook.add(unit);
					}
					
					tableRecommendBook.setItems(dataRecommendBook);
					operation_return_pane.setContent(tableRecommendBook);
				
			
			//return type is entity
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void recommendBook() {
		
		System.out.println("execute recommendBook");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: recommendBook in service: LibraryManagementSystemSystem ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(librarymanagementsystemsystem_service.recommendBook(
			recommendBook_uid_t.getText(),
			recommendBook_callNo_t.getText(),
			recommendBook_title_t.getText(),
			recommendBook_edition_t.getText(),
			recommendBook_author_t.getText(),
			recommendBook_publisher_t.getText(),
			recommendBook_description_t.getText(),
			recommendBook_isbn_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createStudent() {
		
		System.out.println("execute createStudent");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createStudent in service: ManageUserCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageusercrudservice_service.createStudent(
			createStudent_userID_t.getText(),
			createStudent_name_t.getText(),
			Sex.valueOf(createStudent_sex_cb.getSelectionModel().getSelectedItem().toString()),
			createStudent_password_t.getText(),
			createStudent_email_t.getText(),
			createStudent_faculty_t.getText(),
			createStudent_major_t.getText(),
			Programme.valueOf(createStudent_programme_cb.getSelectionModel().getSelectedItem().toString()),
			StudentStatus.valueOf(createStudent_registrationStatus_cb.getSelectionModel().getSelectedItem().toString())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifyStudent() {
		
		System.out.println("execute modifyStudent");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifyStudent in service: ManageUserCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageusercrudservice_service.modifyStudent(
			modifyStudent_userID_t.getText(),
			modifyStudent_name_t.getText(),
			Sex.valueOf(modifyStudent_sex_cb.getSelectionModel().getSelectedItem().toString()),
			modifyStudent_password_t.getText(),
			modifyStudent_email_t.getText(),
			modifyStudent_faculty_t.getText(),
			modifyStudent_major_t.getText(),
			Programme.valueOf(modifyStudent_programme_cb.getSelectionModel().getSelectedItem().toString()),
			StudentStatus.valueOf(modifyStudent_registrationStatus_cb.getSelectionModel().getSelectedItem().toString())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createFaculty() {
		
		System.out.println("execute createFaculty");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createFaculty in service: ManageUserCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageusercrudservice_service.createFaculty(
			createFaculty_userID_t.getText(),
			createFaculty_name_t.getText(),
			Sex.valueOf(createFaculty_sex_cb.getSelectionModel().getSelectedItem().toString()),
			createFaculty_password_t.getText(),
			createFaculty_email_t.getText(),
			createFaculty_faculty_t.getText(),
			FacultyPosition.valueOf(createFaculty_position_cb.getSelectionModel().getSelectedItem().toString()),
			FacultyStatus.valueOf(createFaculty_status_cb.getSelectionModel().getSelectedItem().toString())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifyFaculty() {
		
		System.out.println("execute modifyFaculty");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifyFaculty in service: ManageUserCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageusercrudservice_service.modifyFaculty(
			modifyFaculty_userID_t.getText(),
			modifyFaculty_name_t.getText(),
			Sex.valueOf(modifyFaculty_sex_cb.getSelectionModel().getSelectedItem().toString()),
			modifyFaculty_password_t.getText(),
			modifyFaculty_email_t.getText(),
			modifyFaculty_faculty_t.getText(),
			modifyFaculty_major_t.getText(),
			FacultyPosition.valueOf(modifyFaculty_position_cb.getSelectionModel().getSelectedItem().toString()),
			FacultyStatus.valueOf(modifyFaculty_status_cb.getSelectionModel().getSelectedItem().toString())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void sendNotificationEmail() {
		
		System.out.println("execute sendNotificationEmail");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: sendNotificationEmail in service: ThirdPartyServices ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(thirdpartyservices_service.sendNotificationEmail(
			sendNotificationEmail_email_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createUser() {
		
		System.out.println("execute createUser");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createUser in service: ManageUserCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageusercrudservice_service.createUser(
			createUser_userid_t.getText(),
			createUser_name_t.getText(),
			Sex.valueOf(createUser_sex_cb.getSelectionModel().getSelectedItem().toString()),
			createUser_password_t.getText(),
			createUser_email_t.getText(),
			createUser_faculty_t.getText(),
			Integer.valueOf(createUser_loanednumber_t.getText()),
			BorrowStatus.valueOf(createUser_borrowstatus_cb.getSelectionModel().getSelectedItem().toString()),
			Integer.valueOf(createUser_suspensiondays_t.getText()),
			Float.valueOf(createUser_overduefee_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void queryUser() {
		
		System.out.println("execute queryUser");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: queryUser in service: ManageUserCRUDService ");
		
		try {
			//invoke op with parameters
				User r = manageusercrudservice_service.queryUser(
				queryUser_userid_t.getText()
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableUser = new TableView<Map<String, String>>();
				TableColumn<Map<String, String>, String> tableUser_UserID = new TableColumn<Map<String, String>, String>("UserID");
				tableUser_UserID.setMinWidth("UserID".length()*10);
				tableUser_UserID.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("UserID"));
				    }
				});	
				tableUser.getColumns().add(tableUser_UserID);
				TableColumn<Map<String, String>, String> tableUser_Name = new TableColumn<Map<String, String>, String>("Name");
				tableUser_Name.setMinWidth("Name".length()*10);
				tableUser_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
				    }
				});	
				tableUser.getColumns().add(tableUser_Name);
				TableColumn<Map<String, String>, String> tableUser_Sex = new TableColumn<Map<String, String>, String>("Sex");
				tableUser_Sex.setMinWidth("Sex".length()*10);
				tableUser_Sex.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Sex"));
				    }
				});	
				tableUser.getColumns().add(tableUser_Sex);
				TableColumn<Map<String, String>, String> tableUser_Password = new TableColumn<Map<String, String>, String>("Password");
				tableUser_Password.setMinWidth("Password".length()*10);
				tableUser_Password.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Password"));
				    }
				});	
				tableUser.getColumns().add(tableUser_Password);
				TableColumn<Map<String, String>, String> tableUser_Email = new TableColumn<Map<String, String>, String>("Email");
				tableUser_Email.setMinWidth("Email".length()*10);
				tableUser_Email.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Email"));
				    }
				});	
				tableUser.getColumns().add(tableUser_Email);
				TableColumn<Map<String, String>, String> tableUser_Faculty = new TableColumn<Map<String, String>, String>("Faculty");
				tableUser_Faculty.setMinWidth("Faculty".length()*10);
				tableUser_Faculty.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Faculty"));
				    }
				});	
				tableUser.getColumns().add(tableUser_Faculty);
				TableColumn<Map<String, String>, String> tableUser_LoanedNumber = new TableColumn<Map<String, String>, String>("LoanedNumber");
				tableUser_LoanedNumber.setMinWidth("LoanedNumber".length()*10);
				tableUser_LoanedNumber.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("LoanedNumber"));
				    }
				});	
				tableUser.getColumns().add(tableUser_LoanedNumber);
				TableColumn<Map<String, String>, String> tableUser_BorrowStatus = new TableColumn<Map<String, String>, String>("BorrowStatus");
				tableUser_BorrowStatus.setMinWidth("BorrowStatus".length()*10);
				tableUser_BorrowStatus.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("BorrowStatus"));
				    }
				});	
				tableUser.getColumns().add(tableUser_BorrowStatus);
				TableColumn<Map<String, String>, String> tableUser_SuspensionDays = new TableColumn<Map<String, String>, String>("SuspensionDays");
				tableUser_SuspensionDays.setMinWidth("SuspensionDays".length()*10);
				tableUser_SuspensionDays.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("SuspensionDays"));
				    }
				});	
				tableUser.getColumns().add(tableUser_SuspensionDays);
				TableColumn<Map<String, String>, String> tableUser_OverDueFee = new TableColumn<Map<String, String>, String>("OverDueFee");
				tableUser_OverDueFee.setMinWidth("OverDueFee".length()*10);
				tableUser_OverDueFee.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("OverDueFee"));
				    }
				});	
				tableUser.getColumns().add(tableUser_OverDueFee);
				
				ObservableList<Map<String, String>> dataUser = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					if (r.getUserID() != null)
						unit.put("UserID", String.valueOf(r.getUserID()));
					else
						unit.put("UserID", "");
					if (r.getName() != null)
						unit.put("Name", String.valueOf(r.getName()));
					else
						unit.put("Name", "");
					unit.put("Sex", String.valueOf(r.getSex()));
					if (r.getPassword() != null)
						unit.put("Password", String.valueOf(r.getPassword()));
					else
						unit.put("Password", "");
					if (r.getEmail() != null)
						unit.put("Email", String.valueOf(r.getEmail()));
					else
						unit.put("Email", "");
					if (r.getFaculty() != null)
						unit.put("Faculty", String.valueOf(r.getFaculty()));
					else
						unit.put("Faculty", "");
					unit.put("LoanedNumber", String.valueOf(r.getLoanedNumber()));
					unit.put("BorrowStatus", String.valueOf(r.getBorrowStatus()));
					unit.put("SuspensionDays", String.valueOf(r.getSuspensionDays()));
					unit.put("OverDueFee", String.valueOf(r.getOverDueFee()));
					dataUser.add(unit);
				
				
				tableUser.setItems(dataUser);
				operation_return_pane.setContent(tableUser);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifyUser() {
		
		System.out.println("execute modifyUser");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifyUser in service: ManageUserCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageusercrudservice_service.modifyUser(
			modifyUser_userid_t.getText(),
			modifyUser_name_t.getText(),
			Sex.valueOf(modifyUser_sex_cb.getSelectionModel().getSelectedItem().toString()),
			modifyUser_password_t.getText(),
			modifyUser_email_t.getText(),
			modifyUser_faculty_t.getText(),
			Integer.valueOf(modifyUser_loanednumber_t.getText()),
			BorrowStatus.valueOf(modifyUser_borrowstatus_cb.getSelectionModel().getSelectedItem().toString()),
			Integer.valueOf(modifyUser_suspensiondays_t.getText()),
			Float.valueOf(modifyUser_overduefee_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteUser() {
		
		System.out.println("execute deleteUser");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteUser in service: ManageUserCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(manageusercrudservice_service.deleteUser(
			deleteUser_userid_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createBook() {
		
		System.out.println("execute createBook");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createBook in service: ManageBookCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managebookcrudservice_service.createBook(
			createBook_callno_t.getText(),
			createBook_title_t.getText(),
			createBook_edition_t.getText(),
			createBook_author_t.getText(),
			createBook_publisher_t.getText(),
			createBook_description_t.getText(),
			createBook_isbn_t.getText(),
			Integer.valueOf(createBook_copynum_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void queryBook() {
		
		System.out.println("execute queryBook");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: queryBook in service: ManageBookCRUDService ");
		
		try {
			//invoke op with parameters
				Book r = managebookcrudservice_service.queryBook(
				queryBook_callno_t.getText()
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableBook = new TableView<Map<String, String>>();
				TableColumn<Map<String, String>, String> tableBook_CallNo = new TableColumn<Map<String, String>, String>("CallNo");
				tableBook_CallNo.setMinWidth("CallNo".length()*10);
				tableBook_CallNo.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("CallNo"));
				    }
				});	
				tableBook.getColumns().add(tableBook_CallNo);
				TableColumn<Map<String, String>, String> tableBook_Title = new TableColumn<Map<String, String>, String>("Title");
				tableBook_Title.setMinWidth("Title".length()*10);
				tableBook_Title.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Title"));
				    }
				});	
				tableBook.getColumns().add(tableBook_Title);
				TableColumn<Map<String, String>, String> tableBook_Edition = new TableColumn<Map<String, String>, String>("Edition");
				tableBook_Edition.setMinWidth("Edition".length()*10);
				tableBook_Edition.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Edition"));
				    }
				});	
				tableBook.getColumns().add(tableBook_Edition);
				TableColumn<Map<String, String>, String> tableBook_Author = new TableColumn<Map<String, String>, String>("Author");
				tableBook_Author.setMinWidth("Author".length()*10);
				tableBook_Author.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Author"));
				    }
				});	
				tableBook.getColumns().add(tableBook_Author);
				TableColumn<Map<String, String>, String> tableBook_Publisher = new TableColumn<Map<String, String>, String>("Publisher");
				tableBook_Publisher.setMinWidth("Publisher".length()*10);
				tableBook_Publisher.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Publisher"));
				    }
				});	
				tableBook.getColumns().add(tableBook_Publisher);
				TableColumn<Map<String, String>, String> tableBook_Description = new TableColumn<Map<String, String>, String>("Description");
				tableBook_Description.setMinWidth("Description".length()*10);
				tableBook_Description.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Description"));
				    }
				});	
				tableBook.getColumns().add(tableBook_Description);
				TableColumn<Map<String, String>, String> tableBook_ISBn = new TableColumn<Map<String, String>, String>("ISBn");
				tableBook_ISBn.setMinWidth("ISBn".length()*10);
				tableBook_ISBn.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("ISBn"));
				    }
				});	
				tableBook.getColumns().add(tableBook_ISBn);
				TableColumn<Map<String, String>, String> tableBook_CopyNum = new TableColumn<Map<String, String>, String>("CopyNum");
				tableBook_CopyNum.setMinWidth("CopyNum".length()*10);
				tableBook_CopyNum.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("CopyNum"));
				    }
				});	
				tableBook.getColumns().add(tableBook_CopyNum);
				
				ObservableList<Map<String, String>> dataBook = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					if (r.getCallNo() != null)
						unit.put("CallNo", String.valueOf(r.getCallNo()));
					else
						unit.put("CallNo", "");
					if (r.getTitle() != null)
						unit.put("Title", String.valueOf(r.getTitle()));
					else
						unit.put("Title", "");
					if (r.getEdition() != null)
						unit.put("Edition", String.valueOf(r.getEdition()));
					else
						unit.put("Edition", "");
					if (r.getAuthor() != null)
						unit.put("Author", String.valueOf(r.getAuthor()));
					else
						unit.put("Author", "");
					if (r.getPublisher() != null)
						unit.put("Publisher", String.valueOf(r.getPublisher()));
					else
						unit.put("Publisher", "");
					if (r.getDescription() != null)
						unit.put("Description", String.valueOf(r.getDescription()));
					else
						unit.put("Description", "");
					if (r.getISBn() != null)
						unit.put("ISBn", String.valueOf(r.getISBn()));
					else
						unit.put("ISBn", "");
					unit.put("CopyNum", String.valueOf(r.getCopyNum()));
					dataBook.add(unit);
				
				
				tableBook.setItems(dataBook);
				operation_return_pane.setContent(tableBook);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifyBook() {
		
		System.out.println("execute modifyBook");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifyBook in service: ManageBookCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managebookcrudservice_service.modifyBook(
			modifyBook_callno_t.getText(),
			modifyBook_title_t.getText(),
			modifyBook_edition_t.getText(),
			modifyBook_author_t.getText(),
			modifyBook_publisher_t.getText(),
			modifyBook_description_t.getText(),
			modifyBook_isbn_t.getText(),
			Integer.valueOf(modifyBook_copynum_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteBook() {
		
		System.out.println("execute deleteBook");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteBook in service: ManageBookCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managebookcrudservice_service.deleteBook(
			deleteBook_callno_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createSubject() {
		
		System.out.println("execute createSubject");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createSubject in service: ManageSubjectCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managesubjectcrudservice_service.createSubject(
			createSubject_name_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void querySubject() {
		
		System.out.println("execute querySubject");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: querySubject in service: ManageSubjectCRUDService ");
		
		try {
			//invoke op with parameters
				Subject r = managesubjectcrudservice_service.querySubject(
				querySubject_name_t.getText()
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableSubject = new TableView<Map<String, String>>();
				TableColumn<Map<String, String>, String> tableSubject_Name = new TableColumn<Map<String, String>, String>("Name");
				tableSubject_Name.setMinWidth("Name".length()*10);
				tableSubject_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
				    }
				});	
				tableSubject.getColumns().add(tableSubject_Name);
				
				ObservableList<Map<String, String>> dataSubject = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					if (r.getName() != null)
						unit.put("Name", String.valueOf(r.getName()));
					else
						unit.put("Name", "");
					dataSubject.add(unit);
				
				
				tableSubject.setItems(dataSubject);
				operation_return_pane.setContent(tableSubject);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifySubject() {
		
		System.out.println("execute modifySubject");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifySubject in service: ManageSubjectCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managesubjectcrudservice_service.modifySubject(
			modifySubject_name_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteSubject() {
		
		System.out.println("execute deleteSubject");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteSubject in service: ManageSubjectCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managesubjectcrudservice_service.deleteSubject(
			deleteSubject_name_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void addBookCopy() {
		
		System.out.println("execute addBookCopy");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: addBookCopy in service: ManageBookCopyCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managebookcopycrudservice_service.addBookCopy(
			addBookCopy_callNo_t.getText(),
			addBookCopy_barcode_t.getText(),
			addBookCopy_location_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void queryBookCopy() {
		
		System.out.println("execute queryBookCopy");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: queryBookCopy in service: ManageBookCopyCRUDService ");
		
		try {
			//invoke op with parameters
				BookCopy r = managebookcopycrudservice_service.queryBookCopy(
				queryBookCopy_barcode_t.getText()
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableBookCopy = new TableView<Map<String, String>>();
				TableColumn<Map<String, String>, String> tableBookCopy_Barcode = new TableColumn<Map<String, String>, String>("Barcode");
				tableBookCopy_Barcode.setMinWidth("Barcode".length()*10);
				tableBookCopy_Barcode.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Barcode"));
				    }
				});	
				tableBookCopy.getColumns().add(tableBookCopy_Barcode);
				TableColumn<Map<String, String>, String> tableBookCopy_Status = new TableColumn<Map<String, String>, String>("Status");
				tableBookCopy_Status.setMinWidth("Status".length()*10);
				tableBookCopy_Status.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Status"));
				    }
				});	
				tableBookCopy.getColumns().add(tableBookCopy_Status);
				TableColumn<Map<String, String>, String> tableBookCopy_Location = new TableColumn<Map<String, String>, String>("Location");
				tableBookCopy_Location.setMinWidth("Location".length()*10);
				tableBookCopy_Location.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Location"));
				    }
				});	
				tableBookCopy.getColumns().add(tableBookCopy_Location);
				TableColumn<Map<String, String>, String> tableBookCopy_IsReserved = new TableColumn<Map<String, String>, String>("IsReserved");
				tableBookCopy_IsReserved.setMinWidth("IsReserved".length()*10);
				tableBookCopy_IsReserved.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("IsReserved"));
				    }
				});	
				tableBookCopy.getColumns().add(tableBookCopy_IsReserved);
				
				ObservableList<Map<String, String>> dataBookCopy = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					if (r.getBarcode() != null)
						unit.put("Barcode", String.valueOf(r.getBarcode()));
					else
						unit.put("Barcode", "");
					unit.put("Status", String.valueOf(r.getStatus()));
					if (r.getLocation() != null)
						unit.put("Location", String.valueOf(r.getLocation()));
					else
						unit.put("Location", "");
					unit.put("IsReserved", String.valueOf(r.getIsReserved()));
					dataBookCopy.add(unit);
				
				
				tableBookCopy.setItems(dataBookCopy);
				operation_return_pane.setContent(tableBookCopy);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifyBookCopy() {
		
		System.out.println("execute modifyBookCopy");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifyBookCopy in service: ManageBookCopyCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managebookcopycrudservice_service.modifyBookCopy(
			modifyBookCopy_barcode_t.getText(),
			CopyStatus.valueOf(modifyBookCopy_status_cb.getSelectionModel().getSelectedItem().toString()),
			modifyBookCopy_location_t.getText(),
			Boolean.valueOf(modifyBookCopy_isreserved_t.getText())
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteBookCopy() {
		
		System.out.println("execute deleteBookCopy");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteBookCopy in service: ManageBookCopyCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managebookcopycrudservice_service.deleteBookCopy(
			deleteBookCopy_barcode_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void createLibrarian() {
		
		System.out.println("execute createLibrarian");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: createLibrarian in service: ManageLibrarianCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managelibrariancrudservice_service.createLibrarian(
			createLibrarian_librarianid_t.getText(),
			createLibrarian_name_t.getText(),
			createLibrarian_password_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void queryLibrarian() {
		
		System.out.println("execute queryLibrarian");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: queryLibrarian in service: ManageLibrarianCRUDService ");
		
		try {
			//invoke op with parameters
				Librarian r = managelibrariancrudservice_service.queryLibrarian(
				queryLibrarian_librarianid_t.getText()
				);
			
				//binding result to GUI
				TableView<Map<String, String>> tableLibrarian = new TableView<Map<String, String>>();
				TableColumn<Map<String, String>, String> tableLibrarian_LibrarianID = new TableColumn<Map<String, String>, String>("LibrarianID");
				tableLibrarian_LibrarianID.setMinWidth("LibrarianID".length()*10);
				tableLibrarian_LibrarianID.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("LibrarianID"));
				    }
				});	
				tableLibrarian.getColumns().add(tableLibrarian_LibrarianID);
				TableColumn<Map<String, String>, String> tableLibrarian_Name = new TableColumn<Map<String, String>, String>("Name");
				tableLibrarian_Name.setMinWidth("Name".length()*10);
				tableLibrarian_Name.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Name"));
				    }
				});	
				tableLibrarian.getColumns().add(tableLibrarian_Name);
				TableColumn<Map<String, String>, String> tableLibrarian_Password = new TableColumn<Map<String, String>, String>("Password");
				tableLibrarian_Password.setMinWidth("Password".length()*10);
				tableLibrarian_Password.setCellValueFactory(new Callback<CellDataFeatures<Map<String, String>, String>, ObservableValue<String>>() {	   
					@Override
				    public ObservableValue<String> call(CellDataFeatures<Map<String, String>, String> data) {
				        return new ReadOnlyStringWrapper(data.getValue().get("Password"));
				    }
				});	
				tableLibrarian.getColumns().add(tableLibrarian_Password);
				
				ObservableList<Map<String, String>> dataLibrarian = FXCollections.observableArrayList();
				
					Map<String, String> unit = new HashMap<String, String>();
					if (r.getLibrarianID() != null)
						unit.put("LibrarianID", String.valueOf(r.getLibrarianID()));
					else
						unit.put("LibrarianID", "");
					if (r.getName() != null)
						unit.put("Name", String.valueOf(r.getName()));
					else
						unit.put("Name", "");
					if (r.getPassword() != null)
						unit.put("Password", String.valueOf(r.getPassword()));
					else
						unit.put("Password", "");
					dataLibrarian.add(unit);
				
				
				tableLibrarian.setItems(dataLibrarian);
				operation_return_pane.setContent(tableLibrarian);					
					
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void modifyLibrarian() {
		
		System.out.println("execute modifyLibrarian");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: modifyLibrarian in service: ManageLibrarianCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managelibrariancrudservice_service.modifyLibrarian(
			modifyLibrarian_librarianid_t.getText(),
			modifyLibrarian_name_t.getText(),
			modifyLibrarian_password_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void deleteLibrarian() {
		
		System.out.println("execute deleteLibrarian");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: deleteLibrarian in service: ManageLibrarianCRUDService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(managelibrariancrudservice_service.deleteLibrarian(
			deleteLibrarian_librarianid_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void inputAccount() {
		
		System.out.println("execute inputAccount");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: inputAccount in service: LogInAccountService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(loginaccountservice_service.inputAccount(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void inputPassword() {
		
		System.out.println("execute inputPassword");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: inputPassword in service: LogInAccountService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(loginaccountservice_service.inputPassword(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void inputAccount1() {
		
		System.out.println("execute inputAccount1");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: inputAccount1 in service: LogInAccountService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(loginaccountservice_service.inputAccount1(
			inputAccount1_defaultValue_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void inputPasswordNew1() {
		
		System.out.println("execute inputPasswordNew");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: inputPasswordNew in service: LogInAccountService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(loginaccountservice_service.inputPasswordNew(
			inputPasswordNew_defaultValue_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void inputPasswordNew() {
		
		System.out.println("execute inputPasswordNew");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: inputPasswordNew in service: LogInAccountService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(loginaccountservice_service.inputPasswordNew(
			inputPasswordNew_defaultValue_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void ensurePassword1() {
		
		System.out.println("execute ensurePassword1");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: ensurePassword1 in service: RegisterAccountService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(registeraccountservice_service.ensurePassword1(
			ensurePassword1_defaultValue_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void inputPassword1() {
		
		System.out.println("execute inputPassword1");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: inputPassword1 in service: RegisterAccountService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(registeraccountservice_service.inputPassword1(
			inputPassword1_defaultValue_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void inputCard1() {
		
		System.out.println("execute inputCard1");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: inputCard1 in service: LogInAccountService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(loginaccountservice_service.inputCard1(
			inputCard1_cardID_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void inputPassword2() {
		
		System.out.println("execute inputPassword2");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: inputPassword2 in service: LogInAccountService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(loginaccountservice_service.inputPassword2(
			inputPassword2_password_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void inputVerificationcode() {
		
		System.out.println("execute inputVerificationcode");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: inputVerificationcode in service: LogInAccountService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(loginaccountservice_service.inputVerificationcode(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void inputCard12() {
		
		System.out.println("execute inputCard12");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: inputCard12 in service: DeleteAccountService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(deleteaccountservice_service.inputCard12(
			inputCard12_cardID_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void inputPassword11() {
		
		System.out.println("execute inputPassword11");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: inputPassword11 in service: DeleteAccountService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(deleteaccountservice_service.inputPassword11(
			inputPassword11_password_t.getText()
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void ensureDeleteAccount() {
		
		System.out.println("execute ensureDeleteAccount");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: ensureDeleteAccount in service: DeleteAccountService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(deleteaccountservice_service.ensureDeleteAccount(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void inputBookInformation() {
		
		System.out.println("execute inputBookInformation");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: inputBookInformation in service: SearchBookService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(searchbookservice_service.inputBookInformation(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void inputCardID() {
		
		System.out.println("execute inputCardID");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: inputCardID in service: SearchBookService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(searchbookservice_service.inputCardID(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}
	public void exitStytem() {
		
		System.out.println("execute exitStytem");
		String time = String.format("%1$tH:%1$tM:%1$tS", System.currentTimeMillis());
		log.appendText(time + " -- execute operation: exitStytem in service: SearchBookService ");
		
		try {
			//invoke op with parameters
			//return value is primitive type, bind result to label.
			String result = String.valueOf(searchbookservice_service.exitStytem(
			));	
			Label l = new Label(result);
			l.setPadding(new Insets(8, 8, 8, 8));
			operation_return_pane.setContent(l);
		    log.appendText(" -- success!\n");
		    //set pre- and post-conditions text area color
		    precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #7CFC00");
		    //contract evaluation result
		    precondition_pane.setText("Precondition: True");
		    postcondition_pane.setText("Postcondition: True");
		    
		    
		} catch (PreconditionException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (PostconditionException e) {
			log.appendText(" -- failed!\n");
			postcondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");	
			postcondition_pane.setText("Postcondition: False");
			postconditionUnSat();
			
		} catch (NumberFormatException e) {
			log.appendText(" -- failed!\n");
			precondition.setStyle("-fx-background-color:#000000; -fx-control-inner-background: #FF0000");
			precondition_pane.setText("Precondition: False");	
			preconditionUnSat();
			
		} catch (Exception e ) {		
			if (e instanceof ThirdPartyServiceException)
				thirdpartyServiceUnSat();
		}
	}




	//select object index
	int objectindex;
	
	@FXML
	TabPane mainPane;

	@FXML
	TextArea log;
	
	@FXML
	TreeView<String> actor_treeview_user;
	@FXML
	TreeView<String> actor_treeview_faculty;
	@FXML
	TreeView<String> actor_treeview_student;
	@FXML
	TreeView<String> actor_treeview_administrator;
	@FXML
	TreeView<String> actor_treeview_librarian;
	@FXML
	TreeView<String> actor_treeview_scheduler;
	@FXML
	TreeView<String> actor_treeview_thirdpartsystem;
	@FXML
	TreeView<String> actor_treeview_librariannew;
	@FXML
	TreeView<String> actor_treeview_librarymanager;
	


	@FXML
	TextArea definition;
	@FXML
	TextArea precondition;
	@FXML
	TextArea postcondition;
	@FXML
	TextArea invariants;
	
	@FXML
	TitledPane precondition_pane;
	@FXML
	TitledPane postcondition_pane;
	
	//chosen operation textfields
	List<TextField> choosenOperation;
	String clickedOp;
		
	@FXML
	TableView<ClassInfo> class_statisic;
	@FXML
	TableView<AssociationInfo> association_statisic;
	
	Map<String, ObservableList<AssociationInfo>> allassociationData;
	ObservableList<ClassInfo> classInfodata;
	
	LibraryManagementSystemSystem librarymanagementsystemsystem_service;
	ListBookHistory listbookhistory_service;
	SearchBook searchbook_service;
	ManageUserCRUDService manageusercrudservice_service;
	ManageBookCRUDService managebookcrudservice_service;
	ManageSubjectCRUDService managesubjectcrudservice_service;
	ManageBookCopyCRUDService managebookcopycrudservice_service;
	ManageLibrarianCRUDService managelibrariancrudservice_service;
	ThirdPartyServices thirdpartyservices_service;
	SearchBookService searchbookservice_service;
	ListBookHistoryService listbookhistoryservice_service;
	RegisterAccountService registeraccountservice_service;
	LogInAccountService loginaccountservice_service;
	ResetPasswordService resetpasswordservice_service;
	ListRecommendBookService listrecommendbookservice_service;
	DeleteAccountService deleteaccountservice_service;
	RecommendBookService recommendbookservice_service;
	
	ClassInfo user;
	ClassInfo student;
	ClassInfo faculty;
	ClassInfo book;
	ClassInfo subject;
	ClassInfo bookcopy;
	ClassInfo loan;
	ClassInfo reserve;
	ClassInfo recommendbook;
	ClassInfo administrator;
	ClassInfo librarian;
		
	@FXML
	TitledPane object_statics;
	Map<String, TableView> allObjectTables;
	
	@FXML
	TitledPane operation_paras;
	
	@FXML
	TitledPane operation_return_pane;
	
	@FXML 
	TitledPane all_invariant_pane;
	
	@FXML
	TitledPane invariants_panes;
	
	Map<String, GridPane> operationPanels;
	Map<String, VBox> opInvariantPanel;
	
	//all textfiled or eumntity
	TextField registerAccount_userID_t;
	TextField registerAccount_email_t;
	TextField registerAccount_password_t;
	TextField searchBookByBarCode_barcode_t;
	TextField searchBookByTitle_title_t;
	TextField searchBookByAuthor_authorname_t;
	TextField searchBookByISBN_iSBNnumber_t;
	TextField searchBookBySubject_subject_t;
	TextField makeReservation_uid_t;
	TextField makeReservation_barcode_t;
	TextField cancelReservation_uid_t;
	TextField cancelReservation_barcode_t;
	TextField borrowBook_uid_t;
	TextField borrowBook_barcode_t;
	TextField returnBook_barcode_t;
	TextField renewBook_uid_t;
	TextField renewBook_barcode_t;
	TextField payOverDueFee_uid_t;
	TextField payOverDueFee_fee_t;
	TextField listBorrowHistory_uid_t;
	TextField listHodingBook_uid_t;
	TextField listOverDueBook_uid_t;
	TextField listReservationBook_uid_t;
	TextField listRecommendBook_uid_t;
	TextField recommendBook_uid_t;
	TextField recommendBook_callNo_t;
	TextField recommendBook_title_t;
	TextField recommendBook_edition_t;
	TextField recommendBook_author_t;
	TextField recommendBook_publisher_t;
	TextField recommendBook_description_t;
	TextField recommendBook_isbn_t;
	TextField createStudent_userID_t;
	TextField createStudent_name_t;
	ChoiceBox createStudent_sex_cb;
	TextField createStudent_password_t;
	TextField createStudent_email_t;
	TextField createStudent_faculty_t;
	TextField createStudent_major_t;
	ChoiceBox createStudent_programme_cb;
	ChoiceBox createStudent_registrationStatus_cb;
	TextField modifyStudent_userID_t;
	TextField modifyStudent_name_t;
	ChoiceBox modifyStudent_sex_cb;
	TextField modifyStudent_password_t;
	TextField modifyStudent_email_t;
	TextField modifyStudent_faculty_t;
	TextField modifyStudent_major_t;
	ChoiceBox modifyStudent_programme_cb;
	ChoiceBox modifyStudent_registrationStatus_cb;
	TextField createFaculty_userID_t;
	TextField createFaculty_name_t;
	ChoiceBox createFaculty_sex_cb;
	TextField createFaculty_password_t;
	TextField createFaculty_email_t;
	TextField createFaculty_faculty_t;
	ChoiceBox createFaculty_position_cb;
	ChoiceBox createFaculty_status_cb;
	TextField modifyFaculty_userID_t;
	TextField modifyFaculty_name_t;
	ChoiceBox modifyFaculty_sex_cb;
	TextField modifyFaculty_password_t;
	TextField modifyFaculty_email_t;
	TextField modifyFaculty_faculty_t;
	TextField modifyFaculty_major_t;
	ChoiceBox modifyFaculty_position_cb;
	ChoiceBox modifyFaculty_status_cb;
	TextField sendNotificationEmail_email_t;
	TextField createUser_userid_t;
	TextField createUser_name_t;
	ChoiceBox createUser_sex_cb;
	TextField createUser_password_t;
	TextField createUser_email_t;
	TextField createUser_faculty_t;
	TextField createUser_loanednumber_t;
	ChoiceBox createUser_borrowstatus_cb;
	TextField createUser_suspensiondays_t;
	TextField createUser_overduefee_t;
	TextField queryUser_userid_t;
	TextField modifyUser_userid_t;
	TextField modifyUser_name_t;
	ChoiceBox modifyUser_sex_cb;
	TextField modifyUser_password_t;
	TextField modifyUser_email_t;
	TextField modifyUser_faculty_t;
	TextField modifyUser_loanednumber_t;
	ChoiceBox modifyUser_borrowstatus_cb;
	TextField modifyUser_suspensiondays_t;
	TextField modifyUser_overduefee_t;
	TextField deleteUser_userid_t;
	TextField createBook_callno_t;
	TextField createBook_title_t;
	TextField createBook_edition_t;
	TextField createBook_author_t;
	TextField createBook_publisher_t;
	TextField createBook_description_t;
	TextField createBook_isbn_t;
	TextField createBook_copynum_t;
	TextField queryBook_callno_t;
	TextField modifyBook_callno_t;
	TextField modifyBook_title_t;
	TextField modifyBook_edition_t;
	TextField modifyBook_author_t;
	TextField modifyBook_publisher_t;
	TextField modifyBook_description_t;
	TextField modifyBook_isbn_t;
	TextField modifyBook_copynum_t;
	TextField deleteBook_callno_t;
	TextField createSubject_name_t;
	TextField querySubject_name_t;
	TextField modifySubject_name_t;
	TextField deleteSubject_name_t;
	TextField addBookCopy_callNo_t;
	TextField addBookCopy_barcode_t;
	TextField addBookCopy_location_t;
	TextField queryBookCopy_barcode_t;
	TextField modifyBookCopy_barcode_t;
	ChoiceBox modifyBookCopy_status_cb;
	TextField modifyBookCopy_location_t;
	TextField modifyBookCopy_isreserved_t;
	TextField deleteBookCopy_barcode_t;
	TextField createLibrarian_librarianid_t;
	TextField createLibrarian_name_t;
	TextField createLibrarian_password_t;
	TextField queryLibrarian_librarianid_t;
	TextField modifyLibrarian_librarianid_t;
	TextField modifyLibrarian_name_t;
	TextField modifyLibrarian_password_t;
	TextField deleteLibrarian_librarianid_t;
	TextField inputAccount1_defaultValue_t;
	TextField inputPasswordNew_defaultValue_t;
	TextField ensurePassword1_defaultValue_t;
	TextField inputPassword1_defaultValue_t;
	TextField inputCard1_cardID_t;
	TextField inputPassword2_password_t;
	TextField inputCard12_cardID_t;
	TextField inputPassword11_password_t;
	
	HashMap<String, String> definitions_map;
	HashMap<String, String> preconditions_map;
	HashMap<String, String> postconditions_map;
	HashMap<String, String> invariants_map;
	LinkedHashMap<String, String> service_invariants_map;
	LinkedHashMap<String, String> entity_invariants_map;
	LinkedHashMap<String, Label> service_invariants_label_map;
	LinkedHashMap<String, Label> entity_invariants_label_map;
	LinkedHashMap<String, Label> op_entity_invariants_label_map;
	LinkedHashMap<String, Label> op_service_invariants_label_map;
	

	
}
