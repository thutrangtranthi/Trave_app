package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tranthithutrang.trave_app.Databases;
import com.tranthithutrang.trave_app.R;
import com.tranthithutrang.trave_app.UpdateAccountActivity;

import java.util.ArrayList;

import models.User;

public class AccountAdapter extends BaseAdapter {

    Context context;
    ArrayList<User> list;

    public AccountAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int i) { return null; }

    @Override
    public long getItemId(int i) { return 0; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_account, null);
        TextView tvAccount = view.findViewById(R.id.tvAccount);
        Button btnSua = view.findViewById(R.id.btn_AccountEdit);
        Button btnXoa = view.findViewById(R.id.btn_AccountDelete);
        User user = list.get(i);
        tvAccount.setText(user.getUsername() + "");
        Databases db = new Databases(context);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateAccountActivity.class);
                intent.putExtra("ID", user.getId());
                intent.putExtra("ID_GROUP", user.getIdGroup());
                intent.putExtra("Password", user.getPassword());
                intent.putExtra("Name", user.getName());
                intent.putExtra("Username", user.getUsername());
                intent.putExtra("Phone", user.getPhone());
                intent.putExtra("Email", user.getEmail());
                context.startActivity(intent);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn chắc chắn muốn xóa Account này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int result = db.deleteUser(user.getId());
                        list.clear();
                        if (result == 1) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                        }
                        list = db.getUser();
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }
}

