/**
 * Controller for a slide show
 */

package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import application.Photo;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SlideshowController implements Initializable, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@FXML ImageView photoDisplay;
	
	ArrayList<Photo> photoList = AdminController.usersArrayList.get(LoginController.userIndex).albums.get(UserController.currentAlbumIndex).photos;
	File imagePath;
	String imagePathString;
	Image img;
	int index = 0;
	
	/**
	 * User can go backwards in slideshow
	 */
	public void back(){
		if(index-1 >= 0){
			index--;
			imagePath = photoList.get(index).getImage();
			imagePathString = imagePath.toString(); 
			img = new Image("file:"+imagePathString);
			photoDisplay.setImage(img);
		}
	}
	
	/**
	 * Allows user to go to next photo in slideshow
	 */
	public void next(){
		if(index+1 < photoList.size()) {
			index++;
			imagePath = photoList.get(index).getImage();
			imagePathString = imagePath.toString(); 
			img = new Image("file:"+imagePathString);
			photoDisplay.setImage(img);
		}
	}
	
	/**
	 * Allows user to exit slideshow
	 * @param event
	 * @throws IOException
	 */
	public void exitSlideshow(ActionEvent event) throws IOException{
		Parent parent = FXMLLoader.load(getClass().getResource("/view/singleAlbum.fxml"));
		Scene scene = new Scene(parent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	/**
	 * sets up slideshow when user is first brought to this window
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imagePath = photoList.get(0).getImage();
		imagePathString = imagePath.toString(); 
		img = new Image("file:"+imagePathString);
		photoDisplay.setImage(img);
		index = 0;
	}

}
