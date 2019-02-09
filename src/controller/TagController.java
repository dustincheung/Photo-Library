/**
 * Controller for tags for a photo
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Album;
import application.Photo;
import application.Tag;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class TagController implements Initializable, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FXML ListView<Tag> tagList;
	@FXML ComboBox<String> tagName;
	@FXML TextField tagValue;
	
	//static ArrayList<Tag> tagsArrayList = SinglePhotoController.selectedPhoto.getTags();
	Photo photoCurrent = UserController.albumsArrayList.get(UserController.currentAlbumIndex).photos.get(SingleAlbumController.photoIndex);
    ArrayList<Tag> tagsArrayList = photoCurrent.getTags();
	ObservableList<Tag> obslist = FXCollections.observableArrayList(tagsArrayList);
	
	Tag selectedTag;
	ArrayList<String> tagChoices = new ArrayList<String>();
	
	/**
	 * Allows user to go back to last state
	 * @param event
	 * @throws IOException
	 */
	public void goBack(ActionEvent event) throws IOException{
		Parent parent = FXMLLoader.load(getClass().getResource("/view/singleAlbum.fxml"));
		Scene scene = new Scene(parent);
		
		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	/**
	 * Allows user to add tags to current list of tags for the selected photo
	 * @param event
	 */
	public void addTag(ActionEvent event) {
		if(!tagChoices.contains(tagName.getValue())) {
			tagChoices.add(tagName.getValue());
			tagName.getItems().add(tagName.getValue());
		}
		
		if(tagName.getValue() == null){
			Alert alert = new Alert(AlertType.ERROR, "Please specify Tag Name.", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		
		if(tagValue.getText().equals("")){
			Alert alert = new Alert(AlertType.ERROR, "Please specify Tag Value.", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		Tag newTag = new Tag(tagName.getValue().toLowerCase(), tagValue.getText().toLowerCase());
		if(tagsArrayList.contains(newTag)){
			Alert alert = new Alert(AlertType.ERROR, "Cannot add duplicate tag.", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		
		tagsArrayList.add(newTag);
		obslist.setAll(tagsArrayList);
	}
	
	/**
	 * Allows user to delete tag
	 * @param event
	 */
	public void deleteTag(ActionEvent event) {
		if(selectedTag == null){
			Alert alert = new Alert(AlertType.ERROR, "Please select the Tag you want to delete.", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		tagsArrayList.remove(selectedTag);
		obslist.setAll(tagsArrayList);
	}

	/**
	 * Sets up display
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tagChoices.add("Location");
		tagChoices.add("Person");
		tagName.getItems().addAll(tagChoices);
		
		tagList.setItems(obslist);
		
		//listener
		tagList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tag>() {
			@Override
			public void changed(ObservableValue<? extends Tag> observable, Tag oldValue, Tag newValue) {
				selectedTag = newValue;
			}
		});
	}
	
}
