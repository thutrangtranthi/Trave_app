package adapter;

import android.app.Activity;
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

import models.DiaDanh;

public class DiaDanhAdapter extends BaseAdapter {

    private Activity context;
    private int item_layout;
    private ArrayList<DiaDanh> diaDanhs;


    public DiaDanhAdapter(Activity context, int item_layout, ArrayList<DiaDanh> diaDanhs ) {
        this.context = context;
        this.item_layout = item_layout;
        this.diaDanhs = diaDanhs;
    }

    @Override
    public int getCount() { return diaDanhs.size(); }

    @Override
    public Object getItem(int i) { return diaDanhs.get(i); }

    @Override
    public long getItemId(int i) { return 0; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_layout, null);
            viewHolder.placeName = view.findViewById(R.id.place_name);
            viewHolder.countryName = view.findViewById(R.id.country_name);
            viewHolder.placeImage = view.findViewById(R.id.place_image);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        DiaDanh d = diaDanhs.get(i);
        viewHolder.placeName.setText(d.getNameDiaDanh());
        viewHolder.countryName.setText(d.getCity());
        String image = d.getImage_int();
        Glide.with(context)
                .load(image)
                .error(R.drawable.ic_baseline_terrain_24)
                .skipMemoryCache(true)
                .into(viewHolder.placeImage);
        return view;
    }

    private class ViewHolder{
        ImageView placeImage;
        TextView placeName, countryName;
    }

    }


