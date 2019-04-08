package dich.luong.dong.com.call;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

import dich.luong.dong.com.call.adapter.MessageAdapter;
import dich.luong.dong.com.call.model.MessageModel;
import dich.luong.dong.com.call.utils.DBManager;
import dich.luong.dong.com.call.utils.DividerItemDecoration;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private ArrayList<MessageModel> messageModels;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initViews();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.rcv_history);
        dbManager = new DBManager(this);
        messageModels = dbManager.getAllSave();
        Collections.reverse(messageModels);
        messageAdapter = new MessageAdapter(messageModels, this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, R.drawable.divider));
        recyclerView.setAdapter(messageAdapter);
    }
}
