/**
 * This is the tag class that holds the Tag object that can be made with the name (attribute) and the value.
 * 
 */

package application;

import java.io.Serializable;

public class Tag implements Serializable{
	/**
	 * serial id
	 * name: tag attribute
	 * value: tag value
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String value;
	
	public Tag(String name, String value){
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return name + ": " + value;
	}
	
	/**
	 * Overrides the equals method when calling .contains on the arraylist of tags
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		
		if(obj == null)
			return false;
		
		if((obj instanceof Tag) == false)
			return false;
		
		Tag objTag = (Tag)obj;
		return this.name.equals(objTag.name) && this.value.equals(objTag.value);
		
	}
}
