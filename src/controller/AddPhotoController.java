/**
 * This controller is to add photos from the users file system to the photos arraylist in an album
 * 
 */

package controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import application.Photo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AddPhotoController implements Initializable, Serializable {
	
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;
	@FXML TextField date;
	@FXML TextField caption;
	
	ArrayList<Photo> photosList = AdminController.usersArrayList.get(LoginController.userIndex).albums.get(UserController.currentAlbumIndex).photos;
	File selectedFile;
	
	/**
	 * Allows user to choose image from file system
	 * 
	 * @param event: button click
	 * @throws IOException
	 */
	public void choosePhoto(ActionEvent event) throws IOException{
		//file chooser
		FileChooser fc = new FileChooser();
		selectedFile = fc.showOpenDialog(null);
		
	}
	
	/**
	 * Inserts a newly created photo object into the arraylist of photos
	 * @param event: button click
	 * @throws IOException
	 */
	public void addPhoto(ActionEvent event) throws IOException{
		if(selectedFile == null){
			Alert alert = new Alert(AlertType.ERROR, "Please select a photo using the file chooser!", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		
		//creates new photo
		String photoCaption = caption.getText();
		Photo newPhoto = new Photo(selectedFile, photoCaption);
		
		//adds a photo to the arraylist of photos
		photosList.add(newPhoto);
		
		//changes scene back to album view of photos
		Parent parent = FXMLLoader.load(getClass().getResource("/view/singleAlbum.fxml"));
		Scene scene = new Scene(parent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	/**
	 * Allows the user to cancel the action and back out of the add photos window
	 * @param event
	 * @throws IOException
	 */
	public void cancel(ActionEvent event) throws IOException{
		Parent parent = FXMLLoader.load(getClass().getResource("/view/singleAlbum.fxml"));
		Scene scene = new Scene(parent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}

}
