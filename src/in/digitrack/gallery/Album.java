package in.digitrack.gallery;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Album {
	private UUID mId;
	private String mTitle;
	private Date mDate;
	
	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_DATE = "date";
	
	public Album() {
		mId = UUID.randomUUID();
		setDate(new Date());
	}
	
	public Album(JSONObject json) throws JSONException {
		mId = UUID.fromString(json.getString(JSON_ID));
		mTitle = json.getString(JSON_TITLE);
		mDate = new Date(json.getLong(JSON_DATE));
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
	
	public JSONObject toJSON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_TITLE, mTitle);
		json.put(JSON_DATE, mDate.getTime());
		return json;
	}
	
}
