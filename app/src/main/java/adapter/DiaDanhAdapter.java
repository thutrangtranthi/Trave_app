package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tranthithutrang.trave_app.MainActivity;
import com.tranthithutrang.trave_app.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import models.DiaDanh;

public class DiaDanhAdapter extends BaseAdapter {

    private MainActivity context;
    private int item_layout;
    private ArrayList<DiaDanh> diaDanhs;


    public DiaDanhAdapter(MainActivity context, int item_layout, ArrayList<DiaDanh> diaDanhs ) {
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
        Bitmap bitmap = null;
        try {
            URL urlConnection = new URL(d.getImDiaDanh());
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewHolder.placeImage.setImageBitmap(bitmap);

        return view;
    }

    private class ViewHolder{
        ImageView placeImage;
        TextView placeName, countryName;
    }

    }


