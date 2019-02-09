/**
 * This controller allows users to share entire albums with other users as well. (Our cool extra credit feature)
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Album;
import application.Photo;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ShareController implements Initializable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@FXML ListView<User> userListView;
	
	ArrayList<User> listOfUsers = AdminController.usersArrayList;
	ObservableList<User> obslistUsers = FXCollections.observableArrayList(listOfUsers);
	
	User selectedUser;
	public static int shareToUserIndex = -1;
	
	Album selected = AdminController.usersArrayList.get(LoginController.userIndex).albums.get(UserController.currentAlbumIndex);
	
	
	/**
	 * Allows user to confirm sharing the album
	 * @param event
	 * @throws IOException
	 */
	public void confirmShare(ActionEvent event) throws IOException{
		for(int i = 0; i < AdminController.usersArrayList.get(shareToUserIndex).albums.size(); i++){
			if(AdminController.usersArrayList.get(shareToUserIndex).albums.get(i).getAlbumName().equals(selected.getAlbumName()))
			{
		    	Alert alert = new Alert(AlertType.ERROR, "Can not share b/c of duplicate album name.", ButtonType.OK);
				alert.showAndWait();

				if (alert.getResult() == ButtonType.OK) {
					return;
				}
		    }
		}
	
		
		AdminController.usersArrayList.get(shareToUserIndex).albums.add(selected);
		
		
		Parent parent = FXMLLoader.load(getClass().getResource("/view/user.fxml"));
		Scene scene = new Scene(parent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
		Alert alert = new Alert(AlertType.CONFIRMATION, "YOU HAVE SUCCESSFULLY SHARED THIS ALBUM!", ButtonType.OK);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.OK) {
			return;
		}
	}
	
	/**
	 * Allows user to back out of action
	 * @param event
	 * @throws IOException
	 */
	public void cancelShare(ActionEvent event) throws IOException{
		Parent parent = FXMLLoader.load(getClass().getResource("/view/user.fxml"));
		Scene scene = new Scene(parent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	/**
	 * Set ups of layout such as lists and listeners
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
//		System.out.println(UserController.currentAlbumIndex);
		
		userListView.setItems(obslistUsers);
		
		//listener
		userListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
		    @Override
		    public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
		    	selectedUser = newValue;
		    	shareToUserIndex = obslistUsers.indexOf(selectedUser);
		    	if(shareToUserIndex == LoginController.userIndex){
		    		Alert alert = new Alert(AlertType.ERROR, "Can not share to current user.", ButtonType.OK);
					alert.showAndWait();

					if (alert.getResult() == ButtonType.OK) {
					    return;
					}
		    	}
		    }
		});
	}

}
