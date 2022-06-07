package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.tranthithutrang.trave_app.MainActivity;
import com.tranthithutrang.trave_app.R;

import java.util.ArrayList;

import models.User;

public class LoginAdapter extends BaseAdapter {

    private ArrayList<User> users;
    private int item_layout;
    private MainActivity context;

    public LoginAdapter() {
        this.users = users;
        this.item_layout = item_layout;
        this.context = context;
    }

    @Override
    public int getCount() { return 0; }

    @Override
    public Object getItem(int i) { return null; }

    @Override
    public long getItemId(int i) { return 0; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(item_layout, null);
            viewHolder.edtLoginUsername = view.findViewById(R.id.edtLoginUsername);
            viewHolder.edtLoginPassword = view.findViewById(R.id.edtLoginPassword);
            viewHolder.btnLoginLogin = view.findViewById(R.id.btnLoginLogin);
            viewHolder.btnLoginSignup = view.findViewById(R.id.btnLoginSignup);
            view.setTag(viewHolder);
        }else {
            viewHolder = (LoginAdapter.ViewHolder) view.getTag();
        }

        User u = users.get(i);
        viewHolder.edtLoginUsername.setText(u.getUsername());
        viewHolder.edtLoginPassword.setText(u.getPassword());

        return view;
    }

    private  class  ViewHolder{
        Button btnLoginLogin, btnLoginSignup;
        EditText edtLoginUsername, edtLoginPassword;
    }
}

