package wizrole.leftdelitem.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import wizrole.leftdelitem.R;
import wizrole.leftdelitem.adapter.MainAdapter;
import wizrole.leftdelitem.bean.Person;

/**
 * Created by a on 2017/7/28.
 */

public class StickyActivity extends AppCompatActivity{

    public  TabLayout tabLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky);
        /**
         * 注意：
         * 1. 要给需要sticky的View设置tab属性：android:tag="sticky";
         * 2. 也可以Java动态设置：view.setTag("sticky");
         * 3. 如果这个sticky的View是可点击的，那么tag为：android:tag="sticky-nonconstant"或者view.setTag("sticky-nonconstant");
         */
        List<Person> persons=new ArrayList<Person>();
        for(int i=0;i<50;i++){
            Person person=new Person();
            person.setName("张三"+i);
            persons.add(person);
            person=null;
        }
        RecyclerView list_view=(RecyclerView)findViewById(R.id.list_view);
        list_view.setLayoutManager(new LinearLayoutManager(this));
        MainAdapter adapter=new MainAdapter(persons);
        list_view.setAdapter(adapter);
    }
}
