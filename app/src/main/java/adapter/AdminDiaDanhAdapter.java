package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tranthithutrang.trave_app.Databases;
import com.tranthithutrang.trave_app.R;
import com.tranthithutrang.trave_app.UpdateDiaDanhActivity;

import java.util.ArrayList;

import models.DiaDanh;

public class AdminDiaDanhAdapter extends BaseAdapter {

     Context context;
     ArrayList<DiaDanh> list;


    public AdminDiaDanhAdapter(Context context, ArrayList<DiaDanh> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {return list.size();}

    @Override
    public DiaDanh getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getIdDiaDanh();
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_admindiadanh, null);
        TextView tvDiaDanh = view.findViewById(R.id.tv_AdminDiaDanhLocation);
        Button btnSua = view.findViewById(R.id.btn_AdminDiaDanhEdit);
        Button btnXoa = view.findViewById(R.id.btn_AdminDiaDanhDelete);
        DiaDanh diaDanh = getItem(position);
        tvDiaDanh.setText(diaDanh.getNameDiaDanh() + "");
        Databases db = new Databases(context);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateDiaDanhActivity.class);
                intent.putExtra("ID",diaDanh.getIdDiaDanh());
                context.startActivity(intent);
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("X??c nh???n x??a");
                builder.setMessage("B???n ch???c ch???n mu???n x??a ?????a ??i???m n??y?");
                builder.setPositiveButton("C??", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int result = db.deleteDiaDanh(diaDanh.getIdDiaDanh());
                        list.clear();
                        if (result == 1) {
                            Toast.makeText(context, "X??a th??nh c??ng", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, "X??a kh??ng th??nh c??ng", Toast.LENGTH_SHORT).show();
                        }
                        list = db.getDiaDanh();
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }
}
