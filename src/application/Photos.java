/**
 * This is the main class holding the main method
 */

package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import controller.AdminController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

/*
 * TODO: search my multiple tags, uml, javadocs
 */

public class Photos extends Application {
	
	//serialization
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	
	public static boolean stockcheck = false;
	
	AdminController acont = new AdminController();
	
	//serializes
	public static void writeApp(/*AdminController adminApp*/) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(AdminController.usersArrayList);
		oos.close();
	}
	/**
	 * reads dat file to load from previous state
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	//deserialize
	@SuppressWarnings("unchecked")
	public static ArrayList<User> readApp() throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream oos = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		return (ArrayList<User>)oos.readObject();
	}
	
	/**
	 * Identifies stock user and stock images
	 */
	public static void stockPath() {
		Album stockAlbum = AdminController.usersArrayList.get(0).albums.get(0);
		for(int i=0; i<6; i++) {
			File stockfile = stockAlbum.photos.get(i).getImage();
			String str = stockfile.getAbsolutePath();
			String substr = "stockimages";
			String after = str.substring(str.indexOf(substr));
			stockAlbum.photos.get(i).setImage(new File(after));
		}
		stockcheck = true;
	}
	
	/**
	 * Starts the application
	 */
	@Override
	public void start(Stage primaryStage) {
		
		primaryStage.setResizable(false);
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
			GridPane root = (GridPane)loader.load();
//			LoginController controller = loader.getController();
			Scene scene = new Scene(root,800,600);
			
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
	        primaryStage.setOnCloseRequest(e -> {
	        	
	        	try {
					AdminController.writeApp();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
