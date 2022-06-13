package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tranthithutrang.trave_app.R;

import java.util.ArrayList;

import models.Experience;

public class ExpAdapter extends BaseAdapter {

    Context context;
    private ArrayList<Experience> list;


    public ExpAdapter(Context context, ArrayList<Experience> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_experience, null);
        TextView txtName = view.findViewById(R.id.tvExp);
        ImageView imvPic = view.findViewById(R.id.imExp);
        Experience ex = list.get(i);
        txtName.setText(ex.getName());
        Glide.with(context)
                .load(ex.getImage())
                .error(R.drawable.ic_baseline_terrain_24)
                .skipMemoryCache(true)
                .into(imvPic);

        return view;
    }


}

