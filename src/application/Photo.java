/**
 * Photo class holds the constructor for the photo object using the name and caption to create an object
 * The photo class also has methods specific to only the photo object
 */

package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;

public class Photo implements Serializable{
	/**
	 * Serial ID
	 */
	private static final long serialVersionUID = 1L;
	private String caption;
	private File image;
	private ArrayList<Tag> tags;
	private Calendar dateMade;

	/**
	 * Photo constructor
	 * @param image
	 * @param caption
	 */
	public Photo(File image, String caption) {
		this.image = image;
		this.caption = caption;
		tags = new ArrayList<Tag>();
		dateMade = Calendar.getInstance();
		dateMade.set(Calendar.MILLISECOND,0);
	}
	
	/**
	 * Getter to get caption
	 * @return
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * Sets caption
	 * @param caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * Getter for file
	 * @return
	 */
	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public ArrayList<Tag> getTags() {
		return tags;
	}

	public void setTag(ArrayList<Tag> tags) {
		this.tags = tags;
	}
	
	public Calendar getDateMade() {
		return dateMade;
	}

	public void setDateMade(Calendar dateMade) {
		this.dateMade = dateMade;
	}
	
	public BufferedImage bImage(File fImage) throws IOException {
		BufferedImage sourceimage = ImageIO.read(fImage);
		return sourceimage;
	}
}
