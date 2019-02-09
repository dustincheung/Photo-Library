/**
 * This controller holds all the functions the admin can do
 */

package controller;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AdminController implements Initializable, Serializable {
	/**
	 * serial id for saving states
	 */
	private static final long serialVersionUID = 1L;
	@FXML Button listUsers;
	@FXML Button create;
	@FXML Button delete;
	@FXML Button logOut;
	@FXML ListView<User> userList;
	@FXML TextField userTextField;
    
	static ObservableList<User> obslist = FXCollections.observableArrayList();
	public static ArrayList<User> usersArrayList; //arraylist of users
	
	User selectedUser;
	
	//serialization
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	
	/**
	 * Creates an instance of the admin controller for serialization purposes
	 */
	public AdminController() { 
		usersArrayList = new ArrayList<User>(); 
	} 
	
	/**
	 * This button displays all the users
	 * @param event: button click
	 */
	public void viewUsers(ActionEvent event) {
		userList.setItems(obslist);
	}
	
	/**
	 * Writes into the dat file
	 * @throws IOException
	 */
	//serializes
	public static void writeApp(/*AdminController adminApp*/) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(usersArrayList);
		oos.close();
	}
	
	/**
	 * Loads from the dat file all the users into the admin controller object
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	//deserialize
	@SuppressWarnings("unchecked")
	public static ArrayList<User> readApp() throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream oos = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		return (ArrayList<User>)oos.readObject();
	}
	
	/**
	 * Allows admin to create a new object of users
	 * @param event: button click
	 * @throws IOException
	 */
	public void createUser(ActionEvent event) throws IOException {
		User newUser = new User(userTextField.getText());
		obslist.add(newUser);
		//calls serialize method
		usersArrayList.add(newUser);
		writeApp();
	}
	
	/**
	 * Admin can choose users to be deleted
	 * @param event: mouseclick
	 * @throws IOException
	 */
	public void deleteUser(ActionEvent event) throws IOException {
		User temp = selectedUser;
		obslist.remove(selectedUser);
		
		//serialize
		usersArrayList.remove(temp);
		writeApp();
		userList.refresh();
	}
	

	/**
	 * Allows admin to logout while saving the state like the list of users
	 * @param event: button click
	 * @throws IOException
	 */
	public void logout(ActionEvent event) throws IOException {
		Parent adminParent = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
		Scene adminScene = new Scene(adminParent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
	}
	
	/**
	 * Allows the admin to see the current users from a previous time running this program
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			usersArrayList = readApp();
			obslist.setAll(usersArrayList);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
		userList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
		    @Override
		    public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
		    	selectedUser = newValue;
		    }
		});
		
	}
	
}
