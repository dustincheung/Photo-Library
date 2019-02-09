/**
 * Controller for the user and the users functions
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Album;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class UserController implements Initializable, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FXML ListView<Album> albumList;
	@FXML TextField newAlbumName;
	@FXML Label welcomeLabel;
	
	//current user's album
	static ArrayList<Album> albumsArrayList = AdminController.usersArrayList.get(LoginController.userIndex).albums;
	ObservableList<Album> obslist = FXCollections.observableArrayList(albumsArrayList);
	
	Album selectedAlbum;
	public static int currentAlbumIndex = -1;
	
	
	/**
	 * Allows users to create an album in the users albums list
	 * @param event
	 * @throws IOException
	 */
	public void createAlbum(ActionEvent event) throws IOException{
		Parent adminParent = FXMLLoader.load(getClass().getResource("/view/createAlbum.fxml"));
		Scene adminScene = new Scene(adminParent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
		
//		albumList.setItems(obslist);
	}

	/**
	 * User can delete an album
	 * @param event
	 */
	public void deleteAlbum(ActionEvent event){
		if(selectedAlbum == null){
			Alert alert = new Alert(AlertType.ERROR, "Please select the Album you wish to delete.", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		
		albumsArrayList.remove(selectedAlbum);
		obslist.setAll(albumsArrayList);
	}
	
	/**
	 * User can select and open an album. User is then directed to another window
	 * @param event
	 * @throws IOException
	 */
	public void openAlbum(ActionEvent event) throws IOException{
		if(selectedAlbum == null){
			Alert alert = new Alert(AlertType.ERROR, "Please select the Album you wish to open.", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		
		Parent adminParent = FXMLLoader.load(getClass().getResource("/view/singleAlbum.fxml"));
		Scene adminScene = new Scene(adminParent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
		
	}
	
	/**
	 * Allows user to search for photos in an album.
	 * @param event
	 * @throws IOException
	 */
	public void searchAlbum(ActionEvent event) throws IOException{
		Parent parent = FXMLLoader.load(getClass().getResource("/view/search.fxml"));
		Scene scene = new Scene(parent);
			
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	/**
	 * User can rename album
	 * @param event
	 */
	public void renameAlbum(ActionEvent event){
		if(selectedAlbum == null){
			Alert alert = new Alert(AlertType.ERROR, "Please select the Album you wish to rename.", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		
		String editName = newAlbumName.getText();
		
		for(int i = 0; i < albumsArrayList.size(); i++){
			if(editName.equals(albumsArrayList.get(i).getAlbumName())){
				Alert alert = new Alert(AlertType.ERROR, "This album name is already being used!", ButtonType.OK);
				alert.showAndWait();

				if (alert.getResult() == ButtonType.OK){
					newAlbumName.setText("");
					return;
				}
			}
		}
		
		albumsArrayList.get(albumsArrayList.indexOf(selectedAlbum)).setAlbumName(editName);
		obslist.setAll(albumsArrayList);
	}
	
	/**
	 * User can logout
	 * @param event
	 * @throws IOException
	 */
	public void logout(ActionEvent event) throws IOException {
		AdminController.writeApp();
		Parent loginParent = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
		Scene loginScene = new Scene(loginParent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(loginScene);
		window.show();
	}
	
	/**
	 * User can share album
	 * @param event
	 * @throws IOException
	 */
	public void share(ActionEvent event) throws IOException{
		if(selectedAlbum == null){
			Alert alert = new Alert(AlertType.ERROR, "Please select the Album you wish to share.", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		
		Parent parent = FXMLLoader.load(getClass().getResource("/view/share.fxml"));
		Scene scene = new Scene(parent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	/**
	 * Sets up UI for user layout for the home
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		albumsArrayList = AdminController.usersArrayList.get(LoginController.userIndex).albums;
		obslist = FXCollections.observableArrayList(albumsArrayList);
		
		welcomeLabel.setText("Welcome, " + AdminController.usersArrayList.get(LoginController.userIndex).getUsername());
		selectedAlbum = null;
		
		//fills the listview
//		obslist.setAll(albumsArrayList);
		albumList.setItems(obslist);
		
		//listener
		albumList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Album>() {
		    @Override
		    public void changed(ObservableValue<? extends Album> observable, Album oldValue, Album newValue) {
		    	selectedAlbum = newValue;
		    	currentAlbumIndex = obslist.indexOf(selectedAlbum);
		    }
		});
	}
	
}
