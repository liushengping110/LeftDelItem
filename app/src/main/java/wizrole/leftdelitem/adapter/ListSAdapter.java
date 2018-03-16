package wizrole.leftdelitem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import wizrole.leftdelitem.R;
import wizrole.leftdelitem.bean.Person;

/**
 * Created by a on 2017/8/11.
 */

public class ListSAdapter extends BaseAdapter{
    public Context context;
    public List<Person> list;
    public ListSAdapter(List<Person> persons, Context context){
        this.context=context;
        this.list=persons;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class Holder{
        TextView textView;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder=null;
        if(view==null){
            holder=new Holder();
            view= LayoutInflater.from(context).inflate(R.layout.item_menu_main,null);
            holder.textView=(TextView)view.findViewById(R.id.tv_title);
            view.setTag(holder);
        }else{
            holder=(Holder)view.getTag();
        }
        Person person=list.get(i);
        holder.textView.setText(person.getName());
        return view;
    }
}
