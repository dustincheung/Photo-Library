/**
 * Controller for searching functions
 */

package controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import application.Album;
import application.Photo;
import application.Tag;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class SearchController implements Initializable, Serializable {
	/**
	 * serioalize
	 */
	private static final long serialVersionUID = 1L;
	@FXML DatePicker startDatePicker;
	@FXML DatePicker endDatePicker;
	@FXML TextField tagNameField;
	@FXML TextField tagValueField;
	@FXML Label displayTags;
	@FXML ComboBox<String> operators;
	
	//@FXML ComboBox<Tag> tagDropdown; need to define a class for tags
	ArrayList<String> searchCriteria = new ArrayList<String>();
	static ArrayList<Photo> searchResults;
	ArrayList<Tag> andCheck = new ArrayList<Tag>();
	ArrayList<Tag> orCheck = new ArrayList<Tag>();
	boolean orLast;
	boolean andLast;
	boolean startsAnd = false;
	boolean startsOr = false;
	boolean startsSingle = false;
	Tag filterTag;
	
	/**
	 * Adds a tag to search criteria. Allows user to search by single or/and multiple tags
	 */
	public void addTagToSearch(){
		String name = tagNameField.getText().toLowerCase();
		String value = tagValueField.getText().toLowerCase();
		filterTag = new Tag(name, value);
		if(name.isEmpty() || value.isEmpty()){
			Alert alert = new Alert(AlertType.ERROR, "Tag field is empty", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		
		if(operators.getValue() == null){
			Alert alert = new Alert(AlertType.ERROR, "Tag field is empty", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		if(operators.getValue().equals("and")){
			andCheck.add(filterTag);
			andLast = true;
			orLast = false;
			
			if(searchCriteria.size() == 0){
				startsAnd = true;
			}
			searchCriteria.add(filterTag.toString() + " AND ");
		}
		
		if(operators.getValue().equals("or")){
			orCheck.add(filterTag);
			orLast = true;
			andLast = false;
			if(searchCriteria.size() == 0){
				startsOr = true;
			}
			searchCriteria.add(filterTag.toString() + " OR ");
		}
		
		//try and make them unable to add anymore tags
		if(operators.getValue().equals("single tag search")){
			filterTag = new Tag(name, value);
			if(searchCriteria.size() == 0){
				startsSingle = true;
			}
			searchCriteria.add(filterTag.toString() + " Single Tag Search");
		}
		displayTags.setText(searchCriteria.toString());
	}
	
	/**
	 * Allows user to search for photos by date range
	 * @param event
	 * @throws IOException
	 */
	public void searchByDate(ActionEvent event) throws IOException {
		
		LocalDate startDate = startDatePicker.getValue();
		LocalDate endDate = endDatePicker.getValue();
		
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		
		start.set(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth());
		end.set(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth());
		
		Date startDATE = start.getTime();
		Date endDATE = end.getTime();
		
		if(endDATE.getTime() < startDATE.getTime())
		{
			Alert alert = new Alert(AlertType.ERROR, "End date can not be before start date", ButtonType.OK);
			alert.showAndWait();

			if (alert.getResult() == ButtonType.OK) {
			    return;
			}
		}
		searchResults = new ArrayList<Photo>();
		for(int i = 0; i < UserController.albumsArrayList.size(); i++){
			for(int j = 0; j < UserController.albumsArrayList.get(i).photos.size(); j++){
				Photo currentPhoto = UserController.albumsArrayList.get(i).photos.get(j);

				Calendar photoDate = currentPhoto.getDateMade();

				if(photoDate.get(Calendar.YEAR) >= start.get(Calendar.YEAR) && photoDate.get(Calendar.YEAR) <= end.get(Calendar.YEAR)){
					if(photoDate.get(Calendar.MONTH) + 1 >= start.get(Calendar.MONTH) && photoDate.get(Calendar.MONTH) + 1 <= end.get(Calendar.MONTH)){
						if(photoDate.get(Calendar.DAY_OF_MONTH) >= start.get(Calendar.DAY_OF_MONTH) && photoDate.get(Calendar.DAY_OF_MONTH) <= end.get(Calendar.DAY_OF_MONTH)){
							searchResults.add(currentPhoto);
						}
					}
				}
			}
		}

		Parent adminParent = FXMLLoader.load(getClass().getResource("/view/searchResults.fxml"));
		Scene adminScene = new Scene(adminParent);

		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
	}

	/**
	 * Implementation to search by tags
	 * @param event
	 * @throws IOException
	 */
	public void searchByTag(ActionEvent event) throws IOException{
//		System.out.println("andCheck: " + andCheck.size());
//		System.out.println("startsAnd " + startsAnd);
//		System.out.println("orCheck: " + orCheck.size());
//		System.out.println("startsOr " + startsOr);
		searchResults = new ArrayList<Photo>();
		for(int m = 0; m < andCheck.size(); m++){
//			System.out.print(andCheck.get(m) + ", ");
		}
//		System.out.println("");
		
		
		for(int i=0; i<UserController.albumsArrayList.size(); i++) { //iterates through each album
			//single tag search
			if(startsSingle == true){
				for(int j=0; j<UserController.albumsArrayList.get(i).photos.size(); j++) { //iterates through each photo
					Photo currentPhoto = UserController.albumsArrayList.get(i).photos.get(j);
					if(currentPhoto.getTags().contains(filterTag)){
							searchResults.add(currentPhoto);
					}
				}
			}
			
			//starts with and
			if(startsAnd == true){
//				System.out.println("in startAnd");
				for(int j=0; j<UserController.albumsArrayList.get(i).photos.size(); j++) { //iterates through each photo
					Photo currentPhoto = UserController.albumsArrayList.get(i).photos.get(j);
					int andSatisfied = 0;
					for(int k = 0; k < andCheck.size(); k++){
						if(currentPhoto.getTags().contains(andCheck.get(k))){
							andSatisfied++;
						}
					}
					if(andSatisfied == andCheck.size()){
						searchResults.add(currentPhoto);
					}
				}
				
				for(int j = 0; j < UserController.albumsArrayList.get(i).photos.size(); j++) { //iterates through each photo
					Photo currentPhoto = UserController.albumsArrayList.get(i).photos.get(j);
					for(int k = 0; k < orCheck.size(); k++){
						if(currentPhoto.getTags().contains(orCheck.get(k))){
							searchResults.add(currentPhoto);
						}
					}
				}
			}
			//starts with or
			if(startsOr == true){
//				System.out.println("in startOr");
				for(int k = 0; k < orCheck.size(); k++){
//					System.out.print(orCheck.get(k) + ", ");
				}
				
				for(int j = 0; j < UserController.albumsArrayList.get(i).photos.size(); j++) { //iterates through each photo
					Photo currentPhoto = UserController.albumsArrayList.get(i).photos.get(j);
					for(int k = 0; k < orCheck.size(); k++){
						if(currentPhoto.getTags().contains(orCheck.get(k))){
							searchResults.add(currentPhoto);
						}
					}
				}
				
//				for(int j=0; j<UserController.albumsArrayList.get(i).photos.size(); j++) { //iterates through each photo
//					Photo currentPhoto = UserController.albumsArrayList.get(i).photos.get(j);
//					int andSatisfied = 0;
//					for(int k = 0; k < andCheck.size(); k++){
//						if(currentPhoto.getTags().contains(andCheck.get(k))){
//							andSatisfied++;
//						}
//					}
//					if(andSatisfied == andCheck.size()  && searchResults.contains(currentPhoto) != true){
//						searchResults.add(currentPhoto);
//					}
//				}
			}
				
				
				
		}
		
		Parent adminParent = FXMLLoader.load(getClass().getResource("/view/searchResults.fxml"));
		Scene adminScene = new Scene(adminParent);

		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();

	}

	/**
	 * Allows user to go back to last window
	 * @param event
	 * @throws IOException
	 */
	public void back(ActionEvent event) throws IOException{
		Parent adminParent = FXMLLoader.load(getClass().getResource("/view/user.fxml"));
		Scene adminScene = new Scene(adminParent);

		//Stage info
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setScene(adminScene);
		window.show();
	}

	/**
	 * Fills combo box of operators for searching
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		operators.getItems().addAll("single tag search", "and", "or");
	}
}
