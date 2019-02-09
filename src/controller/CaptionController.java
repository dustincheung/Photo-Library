/**
 * This class controls all the caption functions that a single photo has
 * 
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

import application.Photo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CaptionController implements Initializable, Serializable{
	
	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1L;
	@FXML TextField caption;
	
	/**
	 * Lets user add a caption to a photo
	 * @param event
	 * @throws IOException
	 */
	public void confirmCaption(ActionEvent event) throws IOException{
		String photoCaption = caption.getText();
		Photo selectedPhoto = SingleAlbumController.photosList.get(SingleAlbumController.photoIndex);
		selectedPhoto.setCaption(photoCaption);
		
		Parent parent = FXMLLoader.load(getClass().getResource("/view/singleAlbum.fxml"));
		Scene scene = new Scene(parent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	/**
	 * Allows user to cancel out of creating a caption
	 * @param event
	 * @throws IOException
	 */
	public void cancelCaption(ActionEvent event) throws IOException{
		Parent parent = FXMLLoader.load(getClass().getResource("/view/singleAlbum.fxml"));
		Scene scene = new Scene(parent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
