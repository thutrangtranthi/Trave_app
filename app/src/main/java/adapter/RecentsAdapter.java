package adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tranthithutrang.trave_app.MainActivity;
import com.tranthithutrang.trave_app.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import models.DiaDanh;

public class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.RecentsViewHolder>{

    Context context;
    List<DiaDanh> diaDanhList;

    public RecentsAdapter(Context context, List<DiaDanh> diaDanhList) {
        this.context = context;
        this.diaDanhList = diaDanhList;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recents_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentsViewHolder holder, int position) {

        holder.countryName.setText(diaDanhList.get(position).getCity());
        holder.placeName.setText(diaDanhList.get(position).getNameDiaDanh());
        URL image = null;
        try {
            image = new URL(diaDanhList.get(position).getImDiaDanh());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(image.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.placeImage.setImageBitmap(bitmap);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, MainActivity.class);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return diaDanhList.size();
    }

    public static final class RecentsViewHolder extends RecyclerView.ViewHolder{

        ImageView placeImage;
        TextView placeName, countryName;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name);
            countryName = itemView.findViewById(R.id.country_name);

        }
    }
}
