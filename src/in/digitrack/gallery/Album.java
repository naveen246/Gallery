package in.digitrack.gallery;

import java.util.Date;
import java.util.UUID;

public class Album {
	private UUID mId;
	private String mTitle;
	private Date mDate;
	
	public Album() {
		mId = UUID.randomUUID();
		setDate(new Date());
	}
	
	public UUID getId() {
		return mId;
	}

	public String getTitle() {
		return mTitle;
	}
	
	public void setTitle(String title) {
		mTitle = title;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		mDate = date;
	}
	
	@Override
	public String toString() {
		return mTitle;
	}
	
}
