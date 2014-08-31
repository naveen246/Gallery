
package in.digitrack.gallery;

import java.util.ArrayList;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class AlbumListFragment extends ListFragment {
	
	private ArrayList<Album> mAlbums;
	private String TAG = "AlbumListFragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.albums_title);
		mAlbums = Albums.get(getActivity()).getAlbums();
		
		AlbumAdapter adapter = new AlbumAdapter(mAlbums);
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Album a = ((AlbumAdapter)getListAdapter()).getItem(position);

		Intent i = new Intent(getActivity(), AlbumActivity.class);
		i.putExtra(AlbumFragment.EXTRA_ALBUM_ID, a.getId());
		startActivity(i);
	}
	
	private class AlbumAdapter extends ArrayAdapter<Album> {
		public AlbumAdapter(ArrayList<Album> albums) {
			super(getActivity(), 0, albums);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_album, null);
			}
			
			Album a = getItem(position);
			
			TextView titleTextView = (TextView)convertView.findViewById(R.id.album_list_item_titleTextView);
			titleTextView.setText(a.getTitle());
			TextView dateTextView = (TextView)convertView.findViewById(R.id.album_list_item_dateTextView);
			dateTextView.setText(a.getDate().toString());
			
			return convertView;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		((AlbumAdapter)getListAdapter()).notifyDataSetChanged();
	}
}