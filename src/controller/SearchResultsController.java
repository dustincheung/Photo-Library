/**
 * Controller to view the search results from search criteria
 */

package controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Album;
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
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class SearchResultsController implements Initializable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FXML TilePane tilepane;
	@FXML TextField resultsName;
	
	ArrayList<Photo> searchResults = SearchController.searchResults;
	
	public void goBack(ActionEvent event) throws IOException {
		Parent adminParent = FXMLLoader.load(getClass().getResource("/view/search.fxml"));
		Scene adminScene = new Scene(adminParent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
	}
	
	/**
	 * Creates an album for the user of the results
	 * @param event
	 * @throws IOException
	 */
	public void makeAlbum(ActionEvent event) throws IOException {
		
		if(resultsName.getText().equals("")){
			Alert alert = new Alert(AlertType.ERROR, "Please specify an album name.", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		//checking if this album name has been used already
		for(int i = 0; i < UserController.albumsArrayList.size(); i++){
			if(UserController.albumsArrayList.get(i).getAlbumName().equals(resultsName.getText())){
				Alert alert = new Alert(AlertType.ERROR, "This Album name is already being used.", ButtonType.OK);
				alert.showAndWait();

				if (alert.getResult() == ButtonType.OK) {
					resultsName.setText("");
				    return;
				}
			}
		}
		Album newAlbum = new Album(resultsName.getText());
		
		newAlbum.photos = SearchController.searchResults;
		UserController.albumsArrayList.add(newAlbum);
		
		Parent adminParent = FXMLLoader.load(getClass().getResource("/view/user.fxml"));
		Scene adminScene = new Scene(adminParent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
	}
	
	/**
	 * Sets up the ui layout of buttons and images
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		for(int i=0; i<searchResults.size(); i++) {
			Image img = new Image("file:"+searchResults.get(i).getImage().toString());
			ImageView imgview = new ImageView(img);
			imgview.setFitHeight(200);
			imgview.setFitWidth(200);
			Label label1 = new Label(searchResults.get(i).getCaption());
			label1.setWrapText(true);
			label1.setGraphic(imgview);
			label1.setContentDisplay(ContentDisplay.TOP);
			tilepane.getChildren().add(label1);
		}
		
	}
	
}
