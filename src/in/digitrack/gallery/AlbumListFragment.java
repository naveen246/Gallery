
package in.digitrack.gallery;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


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
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		ListView listView = (ListView)v.findViewById(android.R.id.list);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			
			@Override
			public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
				return false;
			}
			@Override
			public void onDestroyActionMode(ActionMode arg0) {
			}
			
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				MenuInflater inflater = mode.getMenuInflater();
				inflater.inflate(R.menu.album_list_item_context, menu);
				return true;
			}
			
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch(item.getItemId()) {
				case R.id.menu_item_delete_album:
					final AlbumAdapter adapter = (AlbumAdapter) getListAdapter();
					final ArrayList<Album> albumList = new ArrayList<Album>();
					for(int i = 0; i < adapter.getCount(); i++) {
						if(getListView().isItemChecked(i)) {
							albumList.add(adapter.getItem(i));
						}
					}

					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			    	builder.setMessage("Confirm delete?")
			    			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			    	    	    public void onClick(DialogInterface dialog, int which) {			      	
			    	    	    	Albums albums = Albums.get(getActivity());
							        for(Album a : albumList)
							        	albums.deleteAlbum(a);
							        adapter.notifyDataSetChanged();
			    	    	    }
			    	    	})
			    			.setNegativeButton("No", null)						
			    			.show();
					
					mode.finish();
					return true;
				default :
					return false;
				}
			}
			
			@Override
			public void onItemCheckedStateChanged(ActionMode arg0, int arg1, long arg2, boolean arg3) {
			}
		});
		return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		View emptyView = getActivity().getLayoutInflater().inflate(R.layout.list_view_empty, null);
		Button newAlbumButton = (Button)emptyView.findViewById(R.id.new_album_button);
		newAlbumButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				createNewAlbum();
			}
		});
		((ViewGroup) getListView().getParent()).addView(emptyView);
		getListView().setEmptyView(emptyView);
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
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		
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