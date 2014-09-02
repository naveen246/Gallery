
package in.digitrack.gallery;

import java.util.ArrayList;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class AlbumListFragment extends ListFragment {
	
	private ArrayList<Album> mAlbums;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		getActivity().setTitle(R.string.albums_title);
		mAlbums = Albums.get(getActivity()).getAlbums();
		AlbumAdapter adapter = new AlbumAdapter(mAlbums);
		setListAdapter(adapter);
		setEmptyText("No albums");
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		TextView emptyText = new TextView(getActivity()); //(TextView)findViewById(android.R.id.empty);
		emptyText.setText("No albums");
		getListView().setEmptyView(emptyText);
	}
	
	private View noItems() {
		Log.d("albumlistfragment", "noItems");
		View emptyView = getActivity().getLayoutInflater().inflate(R.layout.list_view_empty, null);
		Button newAlbumButton = (Button)emptyView.findViewById(R.id.new_album_button);
		newAlbumButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				createNewAlbum();
			}
		});
		return emptyView;
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
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_album_list, menu);
	}
	
	void createNewAlbum() {
		Album album = new Album();
		Albums.get(getActivity()).addAlbum(album);
		Intent i = new Intent(getActivity(), AlbumActivity.class);
		i.putExtra(AlbumFragment.EXTRA_ALBUM_ID, album.getId());
		startActivityForResult(i, 0);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_item_new_album:
			createNewAlbum();
			return true;
		default :
			return super.onOptionsItemSelected(item);
		}
	}
}