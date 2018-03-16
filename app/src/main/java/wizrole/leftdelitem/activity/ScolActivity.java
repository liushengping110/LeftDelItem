package wizrole.leftdelitem.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import wizrole.leftdelitem.PopupWindowPotting;
import wizrole.leftdelitem.R;
import wizrole.leftdelitem.ScreenUtil;
import wizrole.leftdelitem.adapter.ListSAdapter;
import wizrole.leftdelitem.bean.Person;

/**
 * Created by a on 2017/8/11.
 */

public class ScolActivity extends AppCompatActivity{

    public ListView list_view;
    public TextView text;
//    private Button toTopBtn;// 返回顶部的按钮
    private boolean scrollFlag = false;// 标记是否滑动
    private int lastVisibleItemPosition = 0;// 标记上次滑动位置
    public ImageView img;
    public View view;
    public ScrollView scrollView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        view=LayoutInflater.from(ScolActivity.this).inflate(R.layout.activity_test,null);
        scrollView=(ScrollView)findViewById(R.id.scrollView);
         img=(ImageView)findViewById(R.id.img);
         text=(TextView)findViewById(R.id.text);
        list_view=(ListView)findViewById(R.id.list_view);
        scrollView.smoothScrollTo(0,0);
        List<Person> persons=new ArrayList<Person>();
        for (int i=0;i<100;i++){
            Person person=new Person();
            person.setName("张三"+i);
            persons.add(person);
            person=null;
        }
        ListSAdapter adapter=new ListSAdapter(persons,this);
        list_view.setAdapter(adapter);
        setListener();
    }

    public PopupWindowPotting popupWindowPotting;
    public void show(){
        if(popupWindowPotting==null){
            popupWindowPotting=new PopupWindowPotting(this) {
                @Override
                protected int getLayout() {
                    return R.layout.pop;
                }

                @Override
                protected void initUI() {

                }

                @Override
                protected void setListener() {

                }
            };
        }
        popupWindowPotting.Show(view);
    }
    public void hide(){
        if(popupWindowPotting!=null){
            popupWindowPotting.Hide();
        }
    }


    public void setListener() {
        list_view.setOnScrollListener(new AbsListView.OnScrollListener() {
            private SparseArray recordSp = new SparseArray(0);
            private int mCurrentfirstVisibleItem = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mCurrentfirstVisibleItem = firstVisibleItem;
                View firstView = view.getChildAt(0);
                if (null != firstView) {
                    ItemRecod itemRecord = (ItemRecod) recordSp.get(firstVisibleItem);
                    if (null == itemRecord) {
                        itemRecord = new ItemRecod();
                    }
                    itemRecord.height = firstView.getHeight();
                    itemRecord.top = firstView.getTop();
                    recordSp.append(firstVisibleItem, itemRecord);
                    int h = getScrollY();//滚动距离
                    if(h>10){
                        show();
                    }else{
                        hide();
                    }
                    //在此进行你需要的操作//TODO
                }
            }

            private int getScrollY() {
                int height = 0;
                for (int i = 0; i < mCurrentfirstVisibleItem; i++) {
                    ItemRecod itemRecod = (ItemRecod) recordSp.get(i);
                    height += itemRecod.height;
                }
                ItemRecod itemRecod = (ItemRecod) recordSp.get(mCurrentfirstVisibleItem);
                if (null == itemRecod) {
                    itemRecod = new ItemRecod();
                }
                return height - itemRecod.top;
            }

            class ItemRecod {
                int height = 0;
                int top = 0;
            }
        });
    }
    /**
     * 滚动ListView到指定位置
     *
     * @param pos
     */
    private void setListViewPos(int pos) {
        if (android.os.Build.VERSION.SDK_INT >= 8) {
            list_view.smoothScrollToPosition(pos);
        } else {
            list_view.setSelection(pos);
        }
    }
    /**
     * 动态设置ListView的高度
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
        ListAdapter listSAdapter = listView.getAdapter();
        if (listSAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listSAdapter.getCount(); i++) {
            View listItem = listSAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listSAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
