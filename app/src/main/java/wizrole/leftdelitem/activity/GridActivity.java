package wizrole.leftdelitem.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by a on 2017/7/27.
 */

public class GridActivity extends ListActivity {

    private GridLayoutManager mGridLayoutManager;
    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        if (mGridLayoutManager == null)
            mGridLayoutManager = new GridLayoutManager(this, 2);
        return mGridLayoutManager;
    }
}
