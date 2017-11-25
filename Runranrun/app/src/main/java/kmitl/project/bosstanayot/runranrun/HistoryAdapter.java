package kmitl.project.bosstanayot.runranrun;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by barjord on 11/22/2017 AD.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private Context context;
    List<HistoryModel> itemList;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HistoryModel listItem = itemList.get(position);
        holder.con_time.setText(listItem.getTime());
        holder.con_distan.setText(listItem.getDistance());
        holder.con_dur.setText(listItem.getDuration());
        holder.con_cal.setText(listItem.getCalories());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ConcludeActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("duration", listItem.getDuration());
                intent.putExtra("count_step", Integer.parseInt(listItem.getCount_step()));//int
                intent.putExtra("distance", Float.parseFloat(listItem.getDistance()));//float
                intent.putExtra("time", listItem.getTime());//String
                intent.putExtra("sec", Integer.parseInt(listItem.getSec()));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
    public HistoryAdapter(List<HistoryModel> itemList, Context context){
        this.itemList = itemList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView con_cal, con_dur, con_distan,con_time;
        public CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            con_cal = itemView.findViewById(R.id.con_cal);
            con_dur = itemView.findViewById(R.id.con_dur);
            con_distan = itemView.findViewById(R.id.con_distan);
            con_time = itemView.findViewById(R.id.con_time);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}
