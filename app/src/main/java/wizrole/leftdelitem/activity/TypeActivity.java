package wizrole.leftdelitem.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.GridItemDecoration;
import com.yanzhenjie.recyclerview.swipe.widget.ListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import wizrole.leftdelitem.R;
import wizrole.leftdelitem.adapter.MainAdapter;
import wizrole.leftdelitem.bean.Person;

/**
 * Created by a on 2017/7/27.
 */

public class TypeActivity extends AppCompatActivity implements SwipeItemClickListener {

    private SwipeMenuRecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        initUI();
        initData();
        setListener();

    }

    public void initUI(){
        mRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        persons=new ArrayList<Person>();
        persons=getList();
    }
    public void initData(){
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.addItemDecoration(getItemDecoration());
        mRecyclerView.setAdapter(getAdapter());
    }
    public void setListener(){
        // RecyclerView的Item的点击事件。
        mRecyclerView.setSwipeItemClickListener(this);
        mRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        mRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
    }
    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.qishi);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 根据ViewType来决定哪一个item该如何添加菜单。
            // 这里模拟业务，实际开发根据自己的业务计算。
            if (viewType % 3 == 0) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(TypeActivity.this)
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.drawable.ic_action_delete)
                        .setText("删除")
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

                SwipeMenuItem closeItem = new SwipeMenuItem(TypeActivity.this)
                        .setBackground(R.drawable.selector_purple)
                        .setImage(R.drawable.ic_action_close)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(closeItem); // 添加菜单到右侧。

                SwipeMenuItem addItem = new SwipeMenuItem(TypeActivity.this)
                        .setBackground(R.drawable.selector_green)
                        .setText("添加")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
            } else if (viewType % 2 == 0) {
                SwipeMenuItem closeItem = new SwipeMenuItem(TypeActivity.this)
                        .setBackground(R.drawable.selector_purple)
                        .setImage(R.drawable.ic_action_close)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(closeItem); // 添加菜单到右侧。

                SwipeMenuItem addItem = new SwipeMenuItem(TypeActivity.this)
                        .setBackground(R.drawable.selector_green)
                        .setText("添加")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
            } else {
                SwipeMenuItem addItem = new SwipeMenuItem(TypeActivity.this)
                        .setBackground(R.drawable.selector_green)
                        .setImage(R.drawable.ic_action_add)
                        .setWidth(width)
                        .setHeight(height);
                swipeLeftMenu.addMenuItem(addItem); // 添加菜单到左侧。

                SwipeMenuItem closeItem = new SwipeMenuItem(TypeActivity.this)
                        .setBackground(R.drawable.selector_red)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeLeftMenu.addMenuItem(closeItem); // 添加菜单到左侧。
            }


        }
    };
    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(TypeActivity.this, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
                switch (menuPosition){
                    case 0:
                        break;
                    case 1:
                        break;
                }

            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(TypeActivity.this, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
                switch (menuPosition){
                    case 0:
                        break;
                    case 1:
                        break;
                }
            }
        }
    };

    /**
     * 获取RecyclerView的布局管理器。
     */
    protected RecyclerView.LayoutManager getLayoutManager() {
        if (mLayoutManager == null)
            mLayoutManager = new LinearLayoutManager(this);
        return mLayoutManager;
    }

    /**
     * 获取RecyclerView的Item分割线。
     */
    protected RecyclerView.ItemDecoration getItemDecoration() {
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return new GridItemDecoration(ContextCompat.getColor(this, R.color.blue));
        } else {
            return new ListItemDecoration(ContextCompat.getColor(this, R.color.blue));
        }
    }

    /**
     * 获取RecyclerView的适配器。
     */
    protected RecyclerView.Adapter getAdapter() {
        if (mAdapter == null)
            mAdapter = new MainAdapter(getList());
        return mAdapter;
    }

    public List<Person> persons;
    public List<Person> getList(){
        for (int i = 0; i < 100; i++) {
            Person person=new Person();
            if (i % 3 == 0) {
                person.setName("我右侧有3个菜单");
            } else if (i % 2 == 0) {
                person.setName("我右侧有2个菜单");
            } else {
                person.setName("我左侧有2个菜单");
            }
            persons.add(person);
            person=null;
        }
        return persons;
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Person person=persons.get(position);
        Toast.makeText(TypeActivity.this,person.getName(),Toast.LENGTH_SHORT).show();
    }
}
