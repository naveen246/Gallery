package in.digitrack.gallery;

import java.util.UUID;

import android.app.Fragment;

public class AlbumActivity extends SingleFragmentActivity {
	
	@Override
	public Fragment createFragment() {
		UUID albumId = (UUID)getIntent().getSerializableExtra(AlbumFragment.EXTRA_ALBUM_ID);
		return AlbumFragment.newInstance(albumId);
	}
}
