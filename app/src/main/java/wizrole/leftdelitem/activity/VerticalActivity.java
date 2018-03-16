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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import wizrole.leftdelitem.bean.Person;


/**
 * 竖向的菜单。
 */
public class VerticalActivity extends AppCompatActivity implements SwipeItemClickListener {

    public  VerticalAdapter mAdapter;
    private SwipeMenuRecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    public List<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        mRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener);
    }

    /**
     * 菜单创建器。
     */
    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.qishi);

            /**
             * 设置竖向菜单的注意点：
             * 1. SwipeMenu.setOrientation(SwipeMenu.VERTICAL);这个是控制Item中的Menu的方向的。
             * 2. SwipeMenuItem.setHeight(0).setWeight(1); // 高度为0，权重为1，和LinearLayout一样。
             * 3. 菜单高度是和Item Content的高度一样的，你可以设置Item的padding和margin。
             */

            swipeLeftMenu.setOrientation(SwipeMenu.VERTICAL);
            swipeRightMenu.setOrientation(SwipeMenu.VERTICAL);
            // 添加左侧的，如果不添加，则左侧不会出现菜单。
            {
                SwipeMenuItem addItem = new SwipeMenuItem(VerticalActivity.this)
                        .setBackground(R.drawable.selector_green)
                        .setImage(R.drawable.ic_action_add)
                        .setWidth(width)
                        .setHeight(0)
                        .setWeight(1);
                swipeLeftMenu.addMenuItem(addItem); // 添加菜单到左侧。

                SwipeMenuItem closeItem = new SwipeMenuItem(VerticalActivity.this)
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.drawable.ic_action_close)
                        .setWidth(width)
                        .setHeight(0)
                        .setWeight(1);
                swipeLeftMenu.addMenuItem(closeItem); // 添加菜单到左侧。
            }

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(VerticalActivity.this)
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.drawable.ic_action_delete)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(0)
                        .setWeight(1);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

                SwipeMenuItem addItem = new SwipeMenuItem(VerticalActivity.this)
                        .setBackground(R.drawable.selector_green)
                        .setText("添加")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(0)
                        .setWeight(1);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
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
                Toast.makeText(VerticalActivity.this, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
                switch (menuPosition){
                    case 0:
                        break;
                    case 1:
                        break;
                }

            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(VerticalActivity.this, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
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

    public VerticalAdapter getAdapter() {
        // 这里只是让Item的高度变高一点，竖向排布的菜单看起来就好看些。
        if (mAdapter == null)
            mAdapter = new VerticalAdapter(getList());
        return mAdapter;
    }


    /**
     * 获取RecyclerView的布局管理器。
     */
    protected RecyclerView.LayoutManager getLayoutManager() {
        if (mLayoutManager == null)
            mLayoutManager = new LinearLayoutManager(this);
        return mLayoutManager;
    }

    public List<Person> getList(){
        for (int i=0;i<30;i++){
            Person person=new Person();
            person.setName("张三"+i);
            persons.add(person);
            person=null;
        }
        return persons;
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Person person=persons.get(position);
        Toast.makeText(VerticalActivity.this,person.getName(),Toast.LENGTH_SHORT).show();
    }

    private static class VerticalAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<Person> mDataList;

        public VerticalAdapter(List<Person> dataList) {
            mDataList = dataList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_vertical, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setData(mDataList.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return mDataList == null ? 0 : mDataList.size();
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTv1;

        public ViewHolder(View itemView) {
            super(itemView);

            mTv1 = (TextView) itemView.findViewById(R.id.tv_title);
        }

        void setData(String data) {
            mTv1.setText(data);
        }
    }

}
