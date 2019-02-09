/**
 * Controller for an individual photo
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Photo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SinglePhotoController implements Initializable, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FXML ImageView display;
	@FXML Label captionLabel;
	@FXML Label dateLabel;
	@FXML Label tagsLabel;
	
//	static Photo selectedphoto;
	
	static Photo selectedPhoto = SingleAlbumController.photosList.get(SingleAlbumController.photoIndex);
	
//	public void test(){
//		System.out.println("testing");
//	}
	
	/**
	 * Allows user to return to last window
	 * @param event
	 * @throws IOException
	 */
	public void goBack(ActionEvent event) throws IOException {
		Parent adminParent = FXMLLoader.load(getClass().getResource("/view/singleAlbum.fxml"));
		Scene adminScene = new Scene(adminParent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
	}

	/**
	 * Sets up the UI for users
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 
		File imagePath = selectedPhoto.getImage();
		String imagePathString = imagePath.toString(); 
		Image img = new Image("file:"+imagePathString);
		display.setImage(img);
		
		String captionShown = selectedPhoto.getCaption();
		captionLabel.setText(captionShown);
		
		//formatting date
		SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
		String formatted = format1.format(selectedPhoto.getDateMade().getTime());
		dateLabel.setText(formatted);
		
		String allTags = "";
		for(int i=0; i<selectedPhoto.getTags().size(); i++) {
//			allTags += " (#"+selectedPhoto.getTags().get(i).toString()+")";
			allTags += " ("+selectedPhoto.getTags().get(i).toString()+")";
		}
		
		tagsLabel.setText(allTags);
		
	}

}
