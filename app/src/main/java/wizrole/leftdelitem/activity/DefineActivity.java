/*
 * Copyright 2016 Yan Zhenjie
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
import android.os.PersistableBundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.SwipeSwitch;
import com.yanzhenjie.recyclerview.swipe.widget.GridItemDecoration;
import com.yanzhenjie.recyclerview.swipe.widget.ListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import wizrole.leftdelitem.R;
import wizrole.leftdelitem.bean.Person;

/**
 * 利用SwipeMenuLayout自定义菜单。
 */
public class DefineActivity extends AppCompatActivity implements SwipeItemClickListener {

    private SwipeSwitch mSwipeSwitch;
    private RecyclerView.Adapter mAdapter;
    public Button btnLeft;
    public Button btnRight;
    private SwipeMenuRecyclerView mRecyclerView;
    public List<Person> persons;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define);
        initUI();
        initData();
        setListener();

    }
    public void initUI(){
        mSwipeSwitch = (SwipeSwitch) findViewById(R.id.swipe_layout);//顶部自定义布局
        mRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);//列表布局空间
        btnLeft = (Button) findViewById(R.id.left_view);
        btnRight = (Button) findViewById(R.id.right_view);

        persons=new ArrayList<Person>();
        persons=getList();
    }


    public void initData(){
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.addItemDecoration(getItemDecoration());
        mRecyclerView.setAdapter(getAdapter());
    }

    public void  setListener(){
        btnLeft.setOnClickListener(xOnClickListener);
        btnRight.setOnClickListener(xOnClickListener);
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


            // 添加左侧的，如果不添加，则左侧不会出现菜单。
            {
                SwipeMenuItem addItem = new SwipeMenuItem(DefineActivity.this)
                        .setBackground(R.drawable.selector_green)
                        .setImage(R.drawable.ic_action_add)
                        .setWidth(width)
                        .setHeight(height);
                swipeLeftMenu.addMenuItem(addItem); // 添加菜单到左侧。

                SwipeMenuItem closeItem = new SwipeMenuItem(DefineActivity.this)
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.drawable.ic_action_close)
                        .setWidth(width)
                        .setHeight(height);
                swipeLeftMenu.addMenuItem(closeItem); // 添加菜单到左侧。
            }

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(DefineActivity.this)
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.drawable.ic_action_delete)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

                SwipeMenuItem addItem = new SwipeMenuItem(DefineActivity.this)
                        .setBackground(R.drawable.selector_green)
                        .setText("添加")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
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
                Toast.makeText(DefineActivity.this, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
                switch (menuPosition){
                    case 0:
                        break;
                    case 1:
                        break;
                }

            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(DefineActivity.this, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
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

    public RecyclerView.Adapter getAdapter() {
        if (mAdapter == null)
            mAdapter = new DefineAdapter();
        return mAdapter;
    }

    /**
     * 就是这个适配器的Item的Layout需要处理，其实和CardView的方式一模一样。
     */
    private static class DefineAdapter extends RecyclerView.Adapter<ViewHolder> {

        DefineAdapter() {
        }

        @Override
        public int getItemCount() {
            return 100;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_define, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button mLeftBtn, mMiddleBtn, mRightBtn;

        ViewHolder(View itemView) {
            super(itemView);

            mLeftBtn = (Button) itemView.findViewById(R.id.left_view);
            mMiddleBtn = (Button) itemView.findViewById(R.id.btn_start);
            mRightBtn = (Button) itemView.findViewById(R.id.right_view);
            mLeftBtn.setOnClickListener(this);
            mMiddleBtn.setOnClickListener(this);
            mRightBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.left_view: {
                    Toast.makeText(v.getContext(), "我是第" + getAdapterPosition() + "个Item的左边的Button", Toast.LENGTH_SHORT).show();
                    break;
                }
                case R.id.btn_start: {
                    Toast.makeText(v.getContext(), "我是第" + getAdapterPosition() + "个Item的中间的Button", Toast.LENGTH_SHORT).show();
                    break;
                }
                case R.id.right_view: {
                    Toast.makeText(v.getContext(), "我是第" + getAdapterPosition() + "个Item的右边的Button", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    }

    /**
     * 一般的点击事件。
     */
    private View.OnClickListener xOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.left_view) {
                mSwipeSwitch.smoothCloseMenu();// 关闭菜单。
                Toast.makeText(DefineActivity.this, "我是左面的", Toast.LENGTH_SHORT).show();
            } else if (v.getId() == R.id.right_view) {
                mSwipeSwitch.smoothCloseMenu();// 关闭菜单。
                Toast.makeText(DefineActivity.this, "我是右面的", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onItemClick(View itemView, int position) {
        Toast.makeText(this, "第" + position + "个", Toast.LENGTH_SHORT).show();
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
}