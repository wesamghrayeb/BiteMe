package common;

import java.io.Serializable;

/**
 * @author Bashar Bashir
 * Class for creating PDF file
 *
 */
public class MyFile implements Serializable { 
	
	/**
	 * file description
	 */
	private String Description=null;
	
	/**
	 * File name
	 */
	private String fileName=null;
	
	/**
	 * File size
	 */
	private int size=0;
	
	/**
	 * Array for saving the PDF bytes
	 */
	public  byte[] mybytearray;
	
	
	public void initArray(int size)
	{
		mybytearray = new byte [size];	
	}
	
	/**
	 * @param fileName
	 * Constructor
	 */
	public MyFile( String fileName) {
		this.fileName = fileName;
	}
	
	
	/**
	 * @return File name
	 * Getter for the file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 * Setter for the file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * @return file size
	 * Getter for the file size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 * Setter for the file size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	public byte[] getMybytearray() {
		return mybytearray;
	}
	
	public byte getMybytearray(int i) {
		return mybytearray[i];
	}

	/**
	 * @param mybytearray
	 * Copying the file bytes from array to another array
	 */
	public void setMybytearray(byte[] mybytearray) {
		
		for(int i=0;i<mybytearray.length;i++)
		this.mybytearray[i] = mybytearray[i];
	}

	/**
	 * @return Description
	 * Getter for the description
	 */
	public String getDescription() {
		return Description;
	}

	/**
	 * @param description
	 * Setter for the description
	 */
	public void setDescription(String description) {
		Description = description;
	}	
}