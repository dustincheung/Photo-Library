/**
 * This is the first controller that the user is brought to. Determines which page the user should go to
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

import application.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable, Serializable {
	
	private static final long serialVersionUID = 1L;
	@FXML Label title;
	@FXML TextField username;
	@FXML Label wrongLabel;
	@FXML Button login;
	
	/**
	 * Login controller needs the admin object to get access to list of registered users
	 */
	AdminController acont = new AdminController();
	public static int userIndex = -1;
	
//	public int userIndex = -1;
//	
//	public int getUserIndex() {
//		return userIndex;
//	}
//
//	public void setUserIndex(int userIndex) {
//		this.userIndex = userIndex;
//	}

	/**
	 * Logs in the user and moves user to home page of user depending on registered users vs. admin
	 * @param event
	 * @throws IOException
	 */
	public void loginUser(ActionEvent event) throws IOException {
		//log in as admin
		if(username.getText().trim().equals("admin")){
			Parent adminParent = FXMLLoader.load(getClass().getResource("/view/admin.fxml"));
			Scene adminScene = new Scene(adminParent);
			
			//Stage info
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setScene(adminScene);
			window.show();
		}
		
		//log in as user
		else {
			//checks if user is registered
			for(int i=0; i<AdminController.usersArrayList.size(); i++) {
				if(username.getText().trim().equals(AdminController.usersArrayList.get(i).getUsername())) {
					userIndex = i;
					
					Parent userParent = FXMLLoader.load(getClass().getResource("/view/user.fxml"));
					Scene userScene = new Scene(userParent);
					
					//Stage info
					Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
					window.setScene(userScene);
					window.show();
				}
			}
			//if user is not in system have a message 
			wrongLabel.setText("Sorry, this is not a Registered User!");
		}
	}
	
	/**
	 * Initializes the users list
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//creates an admin to access userlist
//		AdminController acont = new AdminController();
		try {
			acont.usersArrayList = AdminController.readApp();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			if(Photos.stockcheck == false)
				Photos.stockPath();
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
	}

}
