package in.digitrack.gallery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;

public class AlbumJSONSerializer {
	
	private Context mContext;
	private String mFilename;
	
	public AlbumJSONSerializer(Context c, String file) {
		mContext = c;
		mFilename = file;
	}
	
	public void saveAlbums(ArrayList<Album> albums) throws JSONException, IOException {
		JSONArray array = new JSONArray();
		
		for(Album a : albums) {
			array.put(a.toJSON());
		}
		
		//Write the file to disk
		Writer writer = null;
		try {
			OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		} finally {
			if(writer != null)
				writer.close();
		}
	}
	
	public ArrayList<Album> loadAlbums() throws JSONException, IOException {
		ArrayList<Album> albums = new ArrayList<Album>();
		BufferedReader reader = null;
		try {
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null) {
				jsonString.append(line);
			}
			JSONArray array = (JSONArray)new JSONTokener(jsonString.toString()).nextValue();
			for(int i = 0; i < array.length(); i++) {
				albums.add(new Album(array.getJSONObject(i)));
			}
		} catch(Exception e) {
		} finally {
			if(reader != null)
				reader.close();
		}
		return albums;
	}
}
