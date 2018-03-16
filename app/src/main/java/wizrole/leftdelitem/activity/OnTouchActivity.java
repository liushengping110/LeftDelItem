package wizrole.leftdelitem.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemStateChangedListener;
import com.yanzhenjie.recyclerview.swipe.widget.ListItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import wizrole.leftdelitem.R;
import wizrole.leftdelitem.adapter.OnTouchAdapter;
import wizrole.leftdelitem.bean.Person;

/**
 * Created by a on 2017/7/27.
 */

public class OnTouchActivity extends AppCompatActivity{

    private OnTouchAdapter mDragAdapter;
    public SwipeMenuRecyclerView recyclerView;
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
        persons=new ArrayList<Person>();
        persons=getList();
        recyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
    }

    public void initData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new ListItemDecoration(Color.BLACK));
        // 触摸拖拽的代码在下面的Adapter中。
        mDragAdapter = new OnTouchAdapter(recyclerView, persons);
        recyclerView.setAdapter(mDragAdapter);

        recyclerView.setLongPressDragEnabled(true); // 开启拖拽。
        recyclerView.setItemViewSwipeEnabled(true); // 开启滑动删除。
    }
    public void setListener(){
        recyclerView.setSwipeItemClickListener(mItemClickListener); // Item点击。
        recyclerView.setOnItemMoveListener(onItemMoveListener);// Item的拖拽/侧滑删除时，手指状态发生变化监听。
        recyclerView.setOnItemStateChangedListener(mOnItemStateChangedListener); // 监听Item上的手指状态。
    }

    /**
     * Item点击监听。
     */
    private SwipeItemClickListener mItemClickListener = new SwipeItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {
            Toast.makeText(OnTouchActivity.this, "第" + position + "个", Toast.LENGTH_SHORT).show();
        }
    };
    /**
     * 监听拖拽和侧滑删除，更新UI和数据源。
     */
    private OnItemMoveListener onItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
            // 不同的ViewType不能拖拽换位置。
            if (srcHolder.getItemViewType() != targetHolder.getItemViewType()) return false;

            int fromPosition = srcHolder.getAdapterPosition();
            int toPosition = targetHolder.getAdapterPosition();

            Collections.swap(persons, fromPosition, toPosition);
            mDragAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;// 返回true表示处理了并可以换位置，返回false表示你没有处理并不能换位置。
        }

        @Override
        public void onItemDismiss(int position) {
            persons.remove(position);
            mDragAdapter.notifyItemRemoved(position);
            Toast.makeText(OnTouchActivity.this, "现在的第" + position + "条被删除。", Toast.LENGTH_SHORT).show();
        }

    };

    /**
     * Item的拖拽/侧滑删除时，手指状态发生变化监听。
     */
    private OnItemStateChangedListener mOnItemStateChangedListener = new OnItemStateChangedListener() {
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState == OnItemStateChangedListener.ACTION_STATE_DRAG) {
//                当前状态（"状态：拖拽");

                // 拖拽的时候背景就透明了，这里我们可以添加一个特殊背景。
                viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(OnTouchActivity.this, R.color.white_pressed));
            } else if (actionState == OnItemStateChangedListener.ACTION_STATE_SWIPE) {
//                当前状态（"状态：滑动删除");
            } else if (actionState == OnItemStateChangedListener.ACTION_STATE_IDLE) {
//                当前状态（"状态：手指松开");
                // 在手松开的时候还原背景。
                ViewCompat.setBackground(viewHolder.itemView, ContextCompat.getDrawable(OnTouchActivity.this, R.drawable.select_white));
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
