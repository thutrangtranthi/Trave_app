package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tranthithutrang.trave_app.DetailsActivity;
import com.tranthithutrang.trave_app.R;

import java.util.ArrayList;

import models.DiaDanh;

public class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.RecentsViewHolder> {


    private  Context context;
    private ArrayList<DiaDanh> diaDanhList;


    public RecentsAdapter(Context context, ArrayList<DiaDanh> diaDanhList) {
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
    public void onBindViewHolder(@NonNull RecentsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.countryName.setText(diaDanhList.get(position).getCity());
        holder.placeName.setText(diaDanhList.get(position).getNameDiaDanh());
        String image = diaDanhList.get(position).getImage_int();
        Glide.with(context)
                .load(image)
                .error(R.drawable.ic_baseline_terrain_24)
                .skipMemoryCache(true)
                .into(holder.placeImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailsActivity.class);
                DiaDanh diaDanh = diaDanhList.get(position);
                i.putExtra("ID", diaDanh.getIdDiaDanh());
                i.putExtra("Image", diaDanh.getImDiaDanh());
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
