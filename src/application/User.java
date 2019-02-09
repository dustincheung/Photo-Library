/**
 * The User class is for a general user such as registered users that the admin has control over. The class also holds the user constructor
 * 
 */

package application;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	public ArrayList<Album> albums;
	

	/**
	 * Creates a user object using user constructor with username as parameter
	 * @param username
	 */
	//User constructor
	public User(String username) {
		this.username = username;
		albums = new ArrayList<Album>();
	}
	
	/**
	 * gets the username
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * sets the username
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * gets the albums arraylist that is contained in each user object
	 * @return
	 */
	public ArrayList<Album> getAlbums() {
		return albums;
	}

	/**
	 * Sets the albums arraylist
	 * @param albums
	 */
	public void setAlbums(ArrayList<Album> albums) {
		this.albums = albums;
	}
	
	/**
	 * Own username toString method to print out user
	 */
	public String toString() {
		return getUsername();
	}

}
