package in.cycorax.data.model;

import java.io.Serializable;

/**
 * Data Model class which stores information related to new message added.
 * 
 * @author Virendra
 * 
 */

public class SMSDataModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3856612436930734978L;

	private int id;

	public SMSDataModel(int id, String title, String message) {
		super();
		this.id = id;
		this.title = title;
		this.message = message;
	}

	public SMSDataModel() {
		// TODO Auto-generated constructor stub
	}

	private String title;

	private String message;

	private String dateAndTime;

	private String numbersForSMS;
	private String time;
	private String date;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumbersForSMS() {
		return numbersForSMS;
	}

	public void setNumbersForSMS(String numbersForSMS) {
		this.numbersForSMS = numbersForSMS;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
