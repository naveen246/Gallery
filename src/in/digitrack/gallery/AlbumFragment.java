package in.digitrack.gallery;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AlbumFragment extends Fragment {
	private Album mAlbum;
	private EditText mTitleField;
	private Button mDateButton;
	
	public static final String EXTRA_ALBUM_ID = "in.digitrack.gallery.album_id";
	public static final String DIALOG_DATE = "date";
	public static final int REQUEST_DATE = 0;
	
	public void updateDate() {
		DateFormat df = DateFormat.getDateTimeInstance();
		mDateButton.setText(df.format(mAlbum.getDate()));
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode != Activity.RESULT_OK)
			return;
		if(requestCode == REQUEST_DATE) {
			Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			mAlbum.setDate(date);
			updateDate();
		}
	}
	
	public static AlbumFragment newInstance(UUID albumId) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_ALBUM_ID, albumId);
		AlbumFragment fragment = new AlbumFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UUID albumId = (UUID)getArguments().getSerializable(EXTRA_ALBUM_ID);
		mAlbum = Albums.get(getActivity()).getAlbum(albumId);
		getActivity().setTitle(mAlbum.getTitle());
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_album, container, false);
		
		if(NavUtils.getParentActivityName(getActivity()) != null) {
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		mTitleField = (EditText)v.findViewById(R.id.album_title);
		mDateButton = (Button)v.findViewById(R.id.album_date);
		
		mTitleField.setText(mAlbum.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {
			}
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence c, int start, int before, int count) {
				mAlbum.setTitle(c.toString());
			}
		});
		
		mDateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				FragmentManager fm = getActivity().getFragmentManager();
				DatePickerFragment dialog = DatePickerFragment.newInstance(mAlbum.getDate());
				dialog.setTargetFragment(AlbumFragment.this, REQUEST_DATE);
				dialog.show(fm, DIALOG_DATE);
			}
		});
		updateDate();
		return v;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case android.R.id.home:
			if(NavUtils.getParentActivityName(getActivity()) != null) {
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		default :
			return super.onOptionsItemSelected(item);
		}
	}
}
