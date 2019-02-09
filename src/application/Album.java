/**
 * Album is a object that holds a name and an arraylist of photos
 */
package application;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Album implements Serializable{
	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Album class contains name and arraylist of photos objects
	 */
	private String albumName;
	public ArrayList<Photo> photos;
	
	/**
	 * Album constructor
	 * @param albumName
	 */
	public Album(String albumName /*, ArrayList<Photo> photos*/){
		this.albumName = albumName;
		photos = new ArrayList<Photo>();
	}
	
	/**
	 * gets the album name constructor
	 * @return
	 */
	public String getAlbumName() {
		return albumName;
	}
	
	/**
	 * sets the album name
	 * @param albumName
	 */
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	/**
	 * Album toString method to display album in lists and find the earliest and latest date of the album
	 */
	public String toString(){
		if(photos.size() > 0){
			Calendar earliestDate;
			Calendar latestDate;
			
//			SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
//			String formatted = format1.format(selectedPhoto.getDateMade().getTime());
			
			earliestDate = photos.get(0).getDateMade();
			latestDate = photos.get(0).getDateMade();
			
			for(int i = 0; i < photos.size(); i++)
			{
				Calendar currentDate = photos.get(i).getDateMade();
				
				if(currentDate.get(Calendar.YEAR) < earliestDate.get(Calendar.YEAR)){
					earliestDate = currentDate;
				}else{
					if(currentDate.get(Calendar.YEAR) == earliestDate.get(Calendar.YEAR)){
						if(currentDate.get(Calendar.MONTH) < earliestDate.get(Calendar.MONTH)){
							earliestDate = currentDate;
						}else{
							if(currentDate.get(Calendar.MONTH) == earliestDate.get(Calendar.MONTH)){
								if(currentDate.get(Calendar.DAY_OF_MONTH) <= earliestDate.get(Calendar.DAY_OF_MONTH)){
									earliestDate = currentDate;
								}
							}
						}
					}
				}
				
				if(currentDate.get(Calendar.YEAR) > latestDate.get(Calendar.YEAR)){
					latestDate = currentDate;
				}else{
					if(currentDate.get(Calendar.YEAR) == latestDate.get(Calendar.YEAR)){
						if(currentDate.get(Calendar.MONTH) > latestDate.get(Calendar.MONTH)){
							latestDate = currentDate;
						}else{
							if(currentDate.get(Calendar.MONTH) == latestDate.get(Calendar.MONTH)){
								if(currentDate.get(Calendar.DAY_OF_MONTH) >= latestDate.get(Calendar.DAY_OF_MONTH)){
									latestDate = currentDate;
								}
							}
						}
					}
				}
			}
			SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
			String earlyString = format1.format(earliestDate.getTime());
			String lateString = format1.format(latestDate.getTime());
			
			
			return "Name: " + albumName + "  |  " + "Date: " + earlyString + "  ->  " + lateString + "  |  " + "Number of Photos: " + photos.size();
		}else{
			return "Name: " + albumName + "  |  " + "Number of Photos: " + photos.size();
		}
	}

}
