package dich.luong.dong.com.call.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dich.luong.dong.com.call.R;
import dich.luong.dong.com.call.model.MessageModel;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private ArrayList<MessageModel> messageModels;
    private OnItemClickliserner onItemClickliserner;
    private Context context;

    public MessageAdapter(ArrayList<MessageModel> messageModels,Context context) {
        this.messageModels = messageModels;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        MessageModel messageModel = messageModels.get(position);
        myViewHolder.textViewPhone.setText(messageModel.getPhone());
        myViewHolder.textViewTime.setText(messageModel.getTime());
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPhone;
        TextView textViewTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPhone = itemView.findViewById(R.id.tv_phone);
            textViewTime = itemView.findViewById(R.id.tv_time);
        }
    }

    public interface OnItemClickliserner {
        void onClickItem(int position);
    }
}
