package in.digitrack.gallery;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class Albums {
	private ArrayList<Album> mAlbums;
	private static Albums sAlbums;
	private Context mAppContext;
	private AlbumJSONSerializer mSerializer;
	
	private static final String FILENAME = "albums.json";
	private static final String TAG = "Albums";
	
	private Albums(Context context) {
		mAppContext = context;
		mSerializer = new AlbumJSONSerializer(mAppContext, FILENAME);
		try {
			mAlbums = mSerializer.loadAlbums();
		} catch (Exception e) {
			mAlbums = new ArrayList<Album>();
			Log.e(TAG, "error loading albums", e);
		} 
	}
	
	public static Albums get(Context c) {
		if(sAlbums == null) {
			sAlbums = new Albums(c.getApplicationContext());
		}
		return sAlbums;
	}
	
	public void addAlbum(Album a) {
		mAlbums.add(a);
	}
	
	public void deleteAlbum(Album a) {
		mAlbums.remove(a);
	}
	
	public ArrayList<Album> getAlbums() {
		return mAlbums;
	}
	
	public Album getAlbum(UUID id) {
		for(Album a : mAlbums) {
			if(a.getId().equals(id))
				return a;
		}
		return null;
	}
	
	public boolean saveAlbums() {
		try {
			mSerializer.saveAlbums(mAlbums);
			Log.d(TAG, "albums saved to file");
			return true;
		} catch (Exception e) {
			Log.e(TAG, "error saving file", e);
			return false;
		}
	}
}
