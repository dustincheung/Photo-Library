/**
 * This controller is for an individual album. This displays all the photos in the album and allows for album specific functions
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SingleAlbumController implements Initializable, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@FXML TilePane tilepane;
	
	static ArrayList<Photo> photosList = AdminController.usersArrayList.get(LoginController.userIndex).albums.get(UserController.currentAlbumIndex).photos;
	
	public static int photoIndex = -1;
	
	/**
	 * User can return to last window
	 * @param event
	 * @throws IOException
	 */
	public void goBack(ActionEvent event) throws IOException {
		Parent adminParent = FXMLLoader.load(getClass().getResource("/view/user.fxml"));
		Scene adminScene = new Scene(adminParent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
	}
	
	/**
	 * User can add photos one at a time to the single album
	 * @param event
	 * @throws IOException
	 */
	public void addPhoto(ActionEvent event) throws IOException {
		Parent adminParent = FXMLLoader.load(getClass().getResource("/view/addPhotos.fxml"));
		Scene adminScene = new Scene(adminParent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
	}
	
	/**
	 * User can copy one photo from the current album to another one of the user's albums
	 * @param event
	 * @throws IOException
	 */
	public void copy(ActionEvent event) throws IOException {
		if(photoIndex == -1){
			Alert alert = new Alert(AlertType.ERROR, "Please select the Photo.", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		
		Parent adminParent = FXMLLoader.load(getClass().getResource("/view/copy.fxml"));
		Scene adminScene = new Scene(adminParent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
	}
	
	/**
	 * Display area for an individual photo
	 * @param event
	 * @throws IOException
	 */
	public void displayPhoto(ActionEvent event) throws IOException {
		if(photoIndex == -1){
			Alert alert = new Alert(AlertType.ERROR, "Please select the Photo.", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		SinglePhotoController.selectedPhoto = photosList.get(photoIndex);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/singlePhoto.fxml"));
		Parent adminParent = loader.load();
		
		SinglePhotoController controller = (SinglePhotoController) loader.getController();
//		controller.test();
		Scene adminScene = new Scene(adminParent);
		
		
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
	}
	
	/**
	 * User can delete a photo from album
	 */
	public void delete(){
		if(photosList.size() == 0){
//			System.out.println("error");
			return;
		}
		
		if(photoIndex == -1){
			Alert alert = new Alert(AlertType.ERROR, "Please select the Photo you want to delete.", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		Photo photoRemove = photosList.get(photoIndex);
		photosList.remove(photoRemove);
		tilepane.getChildren().remove(photoIndex);
	}
	
	/**
	 * User can move one photo from current album to another one
	 * @param event
	 * @throws IOException
	 */
	public void move(ActionEvent event) throws IOException{
		if(photoIndex == -1){
			Alert alert = new Alert(AlertType.ERROR, "Please select the Photo.", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		
		Parent parent = FXMLLoader.load(getClass().getResource("/view/move.fxml"));
		Scene scene = new Scene(parent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	/**
	 * User can view a slide show of all the photos in the album
	 * @param event
	 * @throws IOException
	 */
	public void slideshow(ActionEvent event) throws IOException{
		
		if(photosList.size() == 0){
			Alert alert = new Alert(AlertType.ERROR, "There are no photos to show in this album.", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		
		Parent parent = FXMLLoader.load(getClass().getResource("/view/slideshow.fxml"));
		Scene scene = new Scene(parent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	/**
	 * User can add a caption to photos in current album
	 * @param event
	 * @throws IOException
	 */
	public void caption(ActionEvent event) throws IOException{
		if(photoIndex == -1){
			Alert alert = new Alert(AlertType.ERROR, "Please select the Photo.", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		
		Parent parent = FXMLLoader.load(getClass().getResource("/view/editCaption.fxml"));
		Scene scene = new Scene(parent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	/**
	 * User can add and delete tags multiple tags in the same window. Brings user to the different window
	 * @param event
	 * @throws IOException
	 */
	public void addAndDeleteTags(ActionEvent event) throws IOException{
		if(photoIndex == -1){
			Alert alert = new Alert(AlertType.ERROR, "Please select the Photo.", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		SinglePhotoController.selectedPhoto = photosList.get(photoIndex);
		Parent parent = FXMLLoader.load(getClass().getResource("/view/editTags.fxml"));
		Scene scene = new Scene(parent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	/**
	 * Sets up the UI layout for an album like displaying each photo
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		photosList = AdminController.usersArrayList.get(LoginController.userIndex).albums.get(UserController.currentAlbumIndex).photos;
		
		for(int i=0; i<photosList.size(); i++) {
			Image img = new Image("file:"+photosList.get(i).getImage().toString());
			ImageView imgview = new ImageView(img);
			imgview.setFitHeight(200);
			imgview.setFitWidth(200);
			Button label1 = new Button(photosList.get(i).getCaption());
//			Label label1 = new Label(photosList.get(i).getCaption());
			label1.setWrapText(true);
			label1.setGraphic(imgview);
			label1.setContentDisplay(ContentDisplay.TOP);
//			VBox vbox = new VBox();
//			vbox.getChildren().add(label1);
			tilepane.getChildren().add(label1);
			label1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				photoIndex = tilepane.getChildren().indexOf(label1);
		        event.consume();
		    });
		}
	}
}
