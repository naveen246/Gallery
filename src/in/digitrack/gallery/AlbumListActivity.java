
package in.digitrack.gallery;

import android.app.Fragment;

public class AlbumListActivity extends SingleFragmentActivity {
	
	@Override
	public Fragment createFragment() {
		return new AlbumListFragment();
	}
}
