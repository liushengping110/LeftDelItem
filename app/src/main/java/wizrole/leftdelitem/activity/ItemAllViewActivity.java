package wizrole.leftdelitem.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.GridItemDecoration;
import com.yanzhenjie.recyclerview.swipe.widget.ListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import wizrole.leftdelitem.R;
import wizrole.leftdelitem.adapter.MainAdapter;
import wizrole.leftdelitem.bean.Person;

/**
 * Created by a on 2017/7/28.
 */

public class ItemAllViewActivity extends AppCompatActivity implements SwipeItemClickListener {

    private SwipeMenuRecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    public List<Person> persons;
    private MenuCardAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        initUI();
        initData();
        setListener();
    }

    public void initUI(){
        persons=new ArrayList<Person>();
        persons=getList();
        mRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);//列表布局空间
    }
    public void initData(){
        mRecyclerView.setLayoutManager(getLayoutManager());
        mRecyclerView.addItemDecoration(getItemDecoration());
        mRecyclerView.setAdapter(getAdapter());
    }
    public void setListener(){

    }

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
            return new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    outRect.set(20, 20, 20, 20);
                }
            };
        }
    /**
     * 获取RecyclerView的适配器。
     */
    protected RecyclerView.Adapter getAdapter() {

        return new MenuCardAdapter(getList());
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Person person=persons.get(position);
        Toast.makeText(ItemAllViewActivity.this,person.getName(),Toast.LENGTH_SHORT).show();
    }

    /**
     * 就是这个适配器的Item的Layout需要处理，其实就是自定义Menu啦，一模一样。
     */
    public static class MenuCardAdapter extends RecyclerView.Adapter<DefaultViewHolder> {

        private List<Person> titles;

        public MenuCardAdapter(List<Person> titles) {
            this.titles = titles;
        }

        @Override
        public int getItemCount() {
            return titles == null ? 0 : titles.size();
        }

        @Override
        public DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_card, parent, false));
        }

        @Override
        public void onBindViewHolder(DefaultViewHolder holder, int position) {
            holder.setData(titles.get(position).getName());
        }
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }

        public void setData(String title) {
            this.tvTitle.setText(title);
        }
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
