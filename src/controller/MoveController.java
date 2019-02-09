/**
 * This is the controller that moves individual photos to another album
 */

package controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Album;
import application.Photo;
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

public class MoveController implements Initializable, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FXML ListView<Album> albumList;
	ArrayList<Album> listOfAlbums = AdminController.usersArrayList.get(LoginController.userIndex).albums;
	ObservableList<Album> obslistAlbums = FXCollections.observableArrayList(listOfAlbums);
	
	Album selectedAlbum;
	public static int moveToAlbumIndex = -1;
	
	public void cancelMove(ActionEvent event) throws IOException{
		Parent parent = FXMLLoader.load(getClass().getResource("/view/singleAlbum.fxml"));
		Scene scene = new Scene(parent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	/**
	 * Moves the selected photo object to another album while deleting that photo object from the current album
	 * @param event
	 * @throws IOException
	 */
	public void confirmMove(ActionEvent event) throws IOException{
		//current photo chosen to be moved
		Photo photoMove = UserController.albumsArrayList.get(UserController.currentAlbumIndex).photos.get(SingleAlbumController.photoIndex);
		
		//move to new selected album
		UserController.albumsArrayList.get(moveToAlbumIndex).photos.add(photoMove);
		
		//delete from current album
		UserController.albumsArrayList.get(UserController.currentAlbumIndex).photos.remove(photoMove);
		
		Parent parent = FXMLLoader.load(getClass().getResource("/view/singleAlbum.fxml"));
		Scene scene = new Scene(parent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	/**
	 * Implements a listener for albums to move photo to
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//fills the listview
//		obslist.setAll(albumsArrayList);
		albumList.setItems(obslistAlbums);
		
		//listener
		albumList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Album>() {
		    @Override
		    public void changed(ObservableValue<? extends Album> observable, Album oldValue, Album newValue) {
		    	selectedAlbum = newValue;
		    	moveToAlbumIndex = obslistAlbums.indexOf(selectedAlbum);
		    	if(moveToAlbumIndex == UserController.currentAlbumIndex){
		    		Alert alert = new Alert(AlertType.ERROR, "Can not move to current album.", ButtonType.OK);
					alert.showAndWait();

					if (alert.getResult() == ButtonType.OK) {
					    return;
					}
		    	}
		    }
		});
		
	}

}
