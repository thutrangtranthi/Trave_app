package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import models.Experience;

public class ExpAdapter extends BaseAdapter {
    private ArrayList<Experience> list;
    Experience experience;
    Context context;

    public ExpAdapter(Context context, ArrayList<Experience> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }


}
