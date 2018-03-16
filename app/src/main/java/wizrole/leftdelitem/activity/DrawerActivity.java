/*
 * Copyright 2017 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wizrole.leftdelitem.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
 * 和DrawerLayout结合使用。
 */
public class DrawerActivity extends AppCompatActivity implements SwipeItemClickListener {


    private SwipeMenuRecyclerView mMenuRecyclerView;
    public List<Person> persons;
    public Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        initUI();
        initData();
        setListener();
    }

    public void initUI(){
        persons=new ArrayList<Person>();
        persons=getList();
        mMenuRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }
    public void initData(){
        mMenuRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        mMenuRecyclerView.setLayoutManager(getLayoutManager());
        mMenuRecyclerView.addItemDecoration(getItemDecoration());
        mMenuRecyclerView.setAdapter(getAdapter());
    }
    public void setListener(){
        // RecyclerView的Item的点击事件。
        mMenuRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
        mMenuRecyclerView.setSwipeItemClickListener(this);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Toast.makeText(this, "第" + position + "个", Toast.LENGTH_SHORT).show();
    }
    /**
     * 获取RecyclerView的布局管理器。
     */
    private RecyclerView.LayoutManager mLayoutManager;
    protected RecyclerView.LayoutManager getLayoutManager() {
        if (mLayoutManager == null)
            mLayoutManager = new LinearLayoutManager(this);
        return mLayoutManager;
    }

    /**
     * 获取RecyclerView的适配器。
     */
    private RecyclerView.Adapter mAdapter;
    protected RecyclerView.Adapter getAdapter() {
        if (mAdapter == null)
            mAdapter = new MainAdapter(getList());
        return mAdapter;
    }


    /**
     * 获取RecyclerView的Item分割线。
     */
    protected RecyclerView.ItemDecoration getItemDecoration() {
        RecyclerView.LayoutManager layoutManager = mMenuRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return new GridItemDecoration(ContextCompat.getColor(this, R.color.divider_color));
        } else {
            return new ListItemDecoration(ContextCompat.getColor(this, R.color.divider_color));
        }
    }

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.qishi);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 因为左边要DrawerLayout的侧滑，所以不添加左侧菜单，添加后DrawerLayout将滑不动，但是菜单可以滑动。

            // 只添加Item右侧的菜单。
            {
                SwipeMenuItem closeItem = new SwipeMenuItem(DrawerActivity.this)
                        .setBackground(R.drawable.selector_purple)
                        .setImage(R.drawable.ic_action_close)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(closeItem); // 添加菜单到左侧。

                SwipeMenuItem addItem = new SwipeMenuItem(DrawerActivity.this)
                        .setBackground(R.drawable.selector_green)
                        .setText("添加")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到左侧。
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
                Toast.makeText(DrawerActivity.this, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(DrawerActivity.this, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }

    };

    /**数据源*/
    public List<Person> getList(){
        for (int i=0;i<30;i++){
            Person person=new Person();
            person.setName("张三"+i);
            persons.add(person);
            person=null;
        }
        return persons;
    }
}
