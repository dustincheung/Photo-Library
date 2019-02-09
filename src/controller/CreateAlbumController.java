/**
 * Controller that allows user to create an album
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

import application.Album;
import application.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateAlbumController implements Initializable, Serializable {
	
	/**
	 * serialize
	 */
	private static final long serialVersionUID = 1L;

	@FXML TextField albumName;
	
	public Album newAlbum;
	
	/**
	 * Allows user to cancel out of adding an album
	 * @param event
	 * @throws IOException
	 */
	public void cancelAction(ActionEvent event) throws IOException {
		Parent adminParent = FXMLLoader.load(getClass().getResource("/view/user.fxml"));
		Scene adminScene = new Scene(adminParent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
	}
	
	public void addPhotos(ActionEvent event) throws IOException {
		String name = albumName.getText();
		newAlbum = new Album(name);
		
		Parent adminParent = FXMLLoader.load(getClass().getResource("/view/addPhotos.fxml"));
		Scene adminScene = new Scene(adminParent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
	}
	
	/**
	 * Creates a new album object and adds the album to 
	 * @param event
	 * @throws IOException
	 */
	//add empty album
	public void createAlbum(ActionEvent event) throws IOException {
		String name = albumName.getText();
		if(name.equals("")){
			Alert alert = new Alert(AlertType.ERROR, "Please specify an album name.", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		//checking if this album name has been used already
		for(int i = 0; i < UserController.albumsArrayList.size(); i++){
			if(UserController.albumsArrayList.get(i).getAlbumName().equals(name)){
				Alert alert = new Alert(AlertType.ERROR, "This Album name is already being used.", ButtonType.OK);
				alert.showAndWait();

				if (alert.getResult() == ButtonType.OK) {
					albumName.setText("");
				    return;
				}
			}
		}
		newAlbum = new Album(name);
		
		//adds new album to array list of albums
		AdminController.usersArrayList.get(LoginController.userIndex).albums.add(newAlbum);
		Parent adminParent = FXMLLoader.load(getClass().getResource("/view/user.fxml"));
		Scene adminScene = new Scene(adminParent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		//might need to add for serializing...
//		
//		try {
//			acont.usersArrayList = AdminController.readApp();
//		} catch (ClassNotFoundException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

}
