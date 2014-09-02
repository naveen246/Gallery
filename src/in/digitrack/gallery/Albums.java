package in.digitrack.gallery;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

public class Albums {
	private ArrayList<Album> mAlbums;
	private static Albums sAlbums;
	private Context mAppContext;
	
	private Albums(Context context) {
		mAppContext = context;
		mAlbums = new ArrayList<Album>();
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
}
