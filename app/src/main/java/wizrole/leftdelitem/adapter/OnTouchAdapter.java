package wizrole.leftdelitem.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

import wizrole.leftdelitem.R;
import wizrole.leftdelitem.bean.Person;

/**
 * Created by a on 2017/7/27.
 */

public class OnTouchAdapter extends RecyclerView.Adapter<OnTouchAdapter.ViewHolder>{

    private SwipeMenuRecyclerView mMenuRecyclerView;
    private List<Person> titles;

    public OnTouchAdapter(SwipeMenuRecyclerView menuRecyclerView, List<Person> titles) {
        this.mMenuRecyclerView = menuRecyclerView;
        this.titles = titles;
    }

    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drag_touch, parent, false));
        viewHolder.mMenuRecyclerView = mMenuRecyclerView;
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(titles.get(position).getName());
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

        TextView tvTitle;
        SwipeMenuRecyclerView mMenuRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            itemView.findViewById(R.id.iv_touch).setOnTouchListener(this);
        }

        public void setData(String title) {
            this.tvTitle.setText(title);
        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    mMenuRecyclerView.startDrag(this);
                    break;
                }
            }
            return false;
        }
    }

}
