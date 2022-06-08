package com.tranthithutrang.trave_app;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import adapter.ExpAdapter;
import models.DiaDanh;
import models.Experience;
import models.Group;
import models.Hotel;
import models.User;
import models.Vehicle;

public class Databases extends SQLiteOpenHelper {
    public static final String DB_NAME = "travel_app_sqlite.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_DIADANH = "diadanh";
    public static final String COLUMN_ID_DIADANH = "ID_DIADANH";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_IMAGE = "IMAGE";
    public static final String COLUMN_IMAGE_INT = "IMAGE_INT";
    public static final String COLUMN_CITY = "CITY";
    public static final String COLUMN_FAVORITE = "FAVORITE";


    public static final String TABLE_VEHICLE = "phuongtien";
    public static final String COLUMN_ID_PT = "ID_PT";

    public static final String TABLE_HOTEL = "nhanghi";
    public static final String COLUMN_PHONE = "PHONE";
    public static final String COLUMN_PRICE = "PRICE";

    public static final String TABLE_USER = "nguoidung";
    public static final String COLUMN_USERNAME = "USERNAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String TABLE_GROUP = "nhom";
    public static final String COLUMN_ID_GROUP = "ID_GROUP";

    public static final String TABLE_EXPERIENCE = "kinhnghiem";
    public static final String COLUMN_DETAIL = "DETAIL";
    public static final String COLUMN_ID = "ID";


    public static String DB_PATH = "/databases/";
    public static ExpAdapter adapter;
    SQLiteDatabase database = null;


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    static Context context;

    public Databases(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 1) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/com.tranthithutrang.trave_app/databases/";
        }
        this.context = context;
    }


    public void CopyDataBaseFromAsset() throws IOException {

        InputStream myInput = context.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = getDatabasePath();

        // if the path doesn't exist first, create it
        File f = new File(DB_PATH);
        if (!f.exists())
            f.mkdir();

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    //
    private static String getDatabasePath() {
        return DB_PATH + DB_NAME;
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = context.getDatabasePath(DB_NAME);
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                Toast.makeText(context, "Copying success from Assets folder", Toast.LENGTH_LONG).show();
//                System.out.println("copying success from Assets folder");
            } catch (IOException e) {
                Toast.makeText(context, "Error creating source database", Toast.LENGTH_SHORT).show();
//                throw new RuntimeException("Error creating source database", e);
            }
        }
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    @SuppressLint("Range")
    public ArrayList<User> getUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<User> list = new ArrayList<>();
        String sql = "SELECT * FROM " + Databases.TABLE_USER;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = new User();
            user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID))));
            user.setUsername(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_PASSWORD)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_EMAIL)));
            user.setPhone(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_PHONE)));
            user.setIdGroup(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID_GROUP))));

            list.add(user);
            cursor.moveToNext();
        }
        Log.d("ketqua", String.valueOf(list));
        return list;
    }

    @SuppressLint("Range")
    public User getUserByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        User user = new User();
        String sql = "SELECT * FROM " + Databases.TABLE_USER + " WHERE " + Databases.COLUMN_USERNAME + " = '" + username + "'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID))));
            user.setName(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_NAME)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_USERNAME)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_PASSWORD)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_EMAIL)));
            user.setPhone(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_PHONE)));
            user.setIdGroup(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID_GROUP))));
        }
        return user;
    }

    public int addUser(String name, String username, String password, String email, String phone, int idGroup) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Databases.COLUMN_NAME, name);
        values.put(Databases.COLUMN_USERNAME, username);
        values.put(Databases.COLUMN_PASSWORD, password);
        values.put(Databases.COLUMN_EMAIL, email);
        values.put(Databases.COLUMN_PHONE, phone);
        values.put(Databases.COLUMN_ID_GROUP, idGroup);
        long result = db.insert(Databases.TABLE_USER, null, values);
        if (result == -1) {
            return 0;
        } else {
            return 1;
        }
    }

    public int deleteUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(Databases.TABLE_USER, Databases.COLUMN_ID + " = " + id, null);
        if (result == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public int editUser(String name, String username, String password, String email, String phone, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Databases.COLUMN_USERNAME, username);
        values.put(Databases.COLUMN_PASSWORD, password);
        values.put(Databases.COLUMN_NAME, name);
        values.put(Databases.COLUMN_PHONE, phone);
        values.put(Databases.COLUMN_EMAIL, email);
        values.put(Databases.COLUMN_ID_GROUP, id);
        String sql = "SELECT * FROM " + Databases.TABLE_USER + " WHERE " +
                Databases.COLUMN_USERNAME + " = '" + username + "'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            long result = db.update(Databases.TABLE_USER, values,
                    Databases.COLUMN_USERNAME + " = ?", new String[]{username});
            if (result == -1) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

    public int checkUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + Databases.TABLE_USER + " WHERE " + Databases.COLUMN_USERNAME + " = '" + username + "'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public int checkLogin(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + Databases.TABLE_USER + " WHERE " + Databases.COLUMN_USERNAME + " = '" +
                username + "' AND " + Databases.COLUMN_PASSWORD + " = '" + password + "'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            return 1;
        } else {
            return 0;
        }
    }


    public ArrayList<DiaDanh> getDiaDanh() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<DiaDanh> list = new ArrayList<>();
        String sql = "SELECT * FROM " + Databases.TABLE_DIADANH;
        Cursor cursor = db.rawQuery(sql, null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int idDiaDanh = cursor.getInt(0);
            String nameDiaDanh = cursor.getString(1);
            String imDiaDanh = cursor.getString(2);
            String imageInt = cursor.getString(3);
            String city = cursor.getString(4);
            int favorite = cursor.getInt(5);
            int vehicle = cursor.getInt(6);

            list.add(new DiaDanh(idDiaDanh,nameDiaDanh, imDiaDanh, imageInt,city,favorite, vehicle));
        }
        return list;
    }
    public ArrayList<DiaDanh> getTopDiaDanh() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<DiaDanh> list = new ArrayList<>();
        String sql = "SELECT * FROM " + Databases.TABLE_DIADANH;
        Cursor cursor = db.rawQuery(sql, null);
        for (int i = 0; i < 6; i++) {
            cursor.moveToPosition(i);
            int idDiaDanh = cursor.getInt(0);
            String nameDiaDanh = cursor.getString(1);
            String imDiaDanh = cursor.getString(2);
            String imageInt = cursor.getString(3);
            String city = cursor.getString(4);
            int favorite = cursor.getInt(5);
            int vehicle = cursor.getInt(6);

            list.add(new DiaDanh(idDiaDanh,nameDiaDanh, imDiaDanh, imageInt,city,favorite, vehicle));
        }
        return list;
    }

    @SuppressLint("Range")
    public DiaDanh getDiaDanhById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        DiaDanh diaDanh = new DiaDanh();
        String sql = "SELECT * FROM " + Databases.TABLE_DIADANH + " WHERE " + Databases.COLUMN_ID_DIADANH + " = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            diaDanh.setIdDiaDanh(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID_DIADANH))));
            diaDanh.setNameDiaDanh(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_NAME)));
            diaDanh.setImDiaDanh(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_IMAGE)));
            diaDanh.setCity(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_CITY)));
            diaDanh.setFavotite(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_FAVORITE))));
            diaDanh.setImage_int(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_IMAGE_INT)));
        }
        return diaDanh;
    }


    public long editDiadanh(DiaDanh diaDanh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Databases.COLUMN_IMAGE_INT, diaDanh.getImage_int());
//		Log.d("image", diaDanh.getImDiaDanh());
        Log.d("editD", String.valueOf(db.update(Databases.TABLE_DIADANH, values, Databases.COLUMN_ID_DIADANH + " =?", new String[]{String.valueOf(diaDanh.getIdDiaDanh())})));
        long ketqua = db.update(Databases.TABLE_DIADANH, values, Databases.COLUMN_ID_DIADANH + " =?", new String[]{String.valueOf(diaDanh.getIdDiaDanh())});

        Log.v("InsertDatabase", "Ket qua :" + ketqua);

        return ketqua;
    }


    public int editDiaDanh(int id, String name, String image,String image_int, String city, int favorite, int id_pt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Databases.COLUMN_ID_DIADANH, id);
        values.put(Databases.COLUMN_NAME, name);
        values.put(Databases.COLUMN_IMAGE, image);
        values.put(Databases.COLUMN_IMAGE_INT, image_int);
        values.put(Databases.COLUMN_CITY, city);
        values.put(Databases.COLUMN_FAVORITE, favorite);
        values.put(Databases.COLUMN_ID_PT, id_pt);
        String sql = "SELECT * FROM " + Databases.TABLE_DIADANH + " WHERE " + Databases.COLUMN_ID_DIADANH + " = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            long result = db.update(Databases.TABLE_DIADANH, values,
                    Databases.COLUMN_ID_DIADANH + " = ?", new String[]{String.valueOf(id)});
            if (result == -1) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }


    public int addDiaDanh(String name, String image, String image_int, String city, int favorite, int id_pt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Databases.COLUMN_NAME, name);
        values.put(Databases.COLUMN_IMAGE, image);
        values.put(Databases.COLUMN_IMAGE_INT, image_int);
        values.put(Databases.COLUMN_CITY, city);
        values.put(Databases.COLUMN_FAVORITE, favorite);
        values.put(Databases.COLUMN_ID_PT, id_pt);
        long result = db.insert(Databases.TABLE_DIADANH, null, values);
        if (result == -1) {
            return 0;
        } else {
            return 1;
        }
    }

    public int deleteDiaDanh(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(Databases.TABLE_DIADANH, Databases.COLUMN_ID_DIADANH + " = " + id, null);
        if (result == 0) {
            return 0;
        } else {
            return 1;
        }
    }


    //chuc nang favorite dia danh
    public int editFavorite(DiaDanh diaDanh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Databases.COLUMN_FAVORITE, diaDanh.getFavotite());
//		Log.d("image", diaDanh.getImDiaDanh());
        Log.d("--editFavotite", String.valueOf(db.update(Databases.TABLE_DIADANH, values, Databases.COLUMN_ID_DIADANH + " =?", new String[]{String.valueOf(diaDanh.getIdDiaDanh())})));
        return db.update(Databases.TABLE_DIADANH, values, Databases.COLUMN_ID_DIADANH + " =?", new String[]{String.valueOf(diaDanh.getIdDiaDanh())});
    }

    public ArrayList<DiaDanh> getDiaDanhID(int regions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<DiaDanh> list = new ArrayList<>();
        String sql = "SELECT * FROM " + Databases.TABLE_DIADANH + " WHERE " + " = " + regions + " ORDER BY " + COLUMN_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int idDiaDanh = cursor.getInt(0);
            String nameDiaDanh = cursor.getString(1);
            String imDiaDanh = cursor.getString(2);
            String imageInt = cursor.getString(3);
            String city = cursor.getString(4);
            int favorite = cursor.getInt(5);
            int vehicle = cursor.getInt(6);

            list.add(new DiaDanh(idDiaDanh,nameDiaDanh, imDiaDanh, imageInt,city,favorite, vehicle));
        }
        return list;
    }

    @SuppressLint("Range")
    public ArrayList<DiaDanh> getFavorite() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<DiaDanh> list = new ArrayList<>();
        String sql = "SELECT * FROM " + Databases.TABLE_DIADANH + " WHERE " + Databases.COLUMN_FAVORITE + " = 1 " + " ORDER BY " + COLUMN_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DiaDanh diaDanh = new DiaDanh(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6));

            list.add(diaDanh);
            cursor.moveToNext();
        }
        Log.d("ketqua", String.valueOf(list));
        return list;
    }

    @SuppressLint("Range")
    public ArrayList<DiaDanh> searchFavorite(String s, int favorite) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<DiaDanh> list = new ArrayList<>();
        String sql = "SELECT " + COLUMN_ID_DIADANH + ", " + COLUMN_NAME + ", " + COLUMN_IMAGE + ", " + COLUMN_CITY + ", " + COLUMN_FAVORITE
                + " FROM " + TABLE_DIADANH + " WHERE " + COLUMN_NAME + " LIKE '%" + s + "%'" + " AND " + COLUMN_FAVORITE + " = " + favorite;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DiaDanh diaDanh = new DiaDanh();
            diaDanh.setIdDiaDanh(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID_DIADANH))));
            diaDanh.setNameDiaDanh(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_NAME)));
            diaDanh.setImDiaDanh(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_IMAGE)));
            diaDanh.setImage_int(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_IMAGE_INT)));
            diaDanh.setFavotite(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_FAVORITE))));
            diaDanh.setCity(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_CITY)));


            list.add(diaDanh);
            cursor.moveToNext();
        }
        Log.d("ketqua", String.valueOf(list));
        return list;
    }

    @SuppressLint("Range")
    public DiaDanh getDiaDanhDetail(int idDiaDanh) {
        SQLiteDatabase db = this.getWritableDatabase();
        DiaDanh diaDanh = new DiaDanh();
        String sql = "SELECT * FROM " + Databases.TABLE_DIADANH + " WHERE " + Databases.COLUMN_ID_DIADANH + " = " + idDiaDanh;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            diaDanh.setIdDiaDanh(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID_DIADANH))));
            diaDanh.setNameDiaDanh(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_NAME)));
            diaDanh.setImDiaDanh(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_IMAGE)));
            diaDanh.setImage_int(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_IMAGE_INT)));
            diaDanh.setCity(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_CITY)));
            diaDanh.setFavotite(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_FAVORITE))));


        }
        Log.d("ketqua", String.valueOf(diaDanh));
        return diaDanh;
    }

    @SuppressLint("Range")
    public ArrayList<DiaDanh> searchDiaDanh(String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<DiaDanh> list = new ArrayList<>();
//		String sql = "SELECT * FROM " + Databases.TABLE_DIADANH;
        String sql = "SELECT " + COLUMN_ID_DIADANH + ", " + COLUMN_NAME + ", " + COLUMN_IMAGE + ", " + COLUMN_CITY + ", " + COLUMN_FAVORITE + ", " + COLUMN_IMAGE_INT
                + " FROM " + TABLE_DIADANH + " WHERE " + COLUMN_NAME + " LIKE '%" + s + "%'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DiaDanh diaDanh = new DiaDanh(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getInt(6));
            diaDanh.setIdDiaDanh(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID_DIADANH))));
            diaDanh.setNameDiaDanh(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_NAME)));
            diaDanh.setImDiaDanh(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_IMAGE)));
            diaDanh.setImage_int(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_IMAGE_INT)));
            diaDanh.setFavotite(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_FAVORITE))));
            diaDanh.setCity(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_CITY)));


            list.add(diaDanh);
            cursor.moveToNext();
        }
        Log.d("ketqua", String.valueOf(list));
        return list;
    }


//Vehicle

    @SuppressLint("Range")
    public ArrayList<Vehicle> getVedicle() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Vehicle> list = new ArrayList<>();
        String sql = "SELECT * FROM " + Databases.TABLE_VEHICLE;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Vehicle vehicle = new Vehicle();
            vehicle.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID_PT))));
            vehicle.setName(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_NAME)));

            list.add(vehicle);
            cursor.moveToNext();
        }
        Log.d("ketqua", String.valueOf(list));
        return list;
    }

    @SuppressLint("Range")
    public ArrayList<Group> getGroup() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Group> list = new ArrayList<>();
        String sql = "SELECT * FROM " + Databases.TABLE_GROUP;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Group group = new Group();
            group.setIdGroup(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID_GROUP))));
            group.setName(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_NAME)));

            list.add(group);
            cursor.moveToNext();
        }
        Log.d("ketqua", String.valueOf(list));
        return list;
    }

    @SuppressLint("Range")
    public Group getGroupID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Group group = null;
        String sql = "SELECT * FROM " + Databases.TABLE_GROUP + " WHERE " + Databases.COLUMN_ID_GROUP + " = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            group = new Group(cursor.getInt(0),cursor.getString(1));
        }
        return group;
    }

    @SuppressLint("Range")
    public Vehicle getVedicleID(int id_diadanh) {
        SQLiteDatabase db = this.getWritableDatabase();
        Vehicle vehicle = null;
        String sql = "SELECT * FROM " + Databases.TABLE_VEHICLE + " WHERE " + Databases.COLUMN_ID_PT + " = " + id_diadanh;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            vehicle = new Vehicle(cursor.getInt(0),cursor.getString(1));
        }
        return vehicle;
    }

//    @SuppressLint("Range")
//    public Vehicle getVedicleDetail(int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String sql = "SELECT * FROM " + Databases.TABLE_VEHICLE + " WHERE " + Databases.COLUMN_ID_PT + " = " + id;
//        Cursor cursor = db.rawQuery(sql, null);
//        Vehicle vehicle = new Vehicle();
//        if (cursor.moveToFirst()) {
//            vehicle.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID_PT))));
//            vehicle.setName(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_NAME)));
//        }
//        return vehicle;
//    }


    public int editVehicle(Vehicle vehicle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Databases.COLUMN_NAME, vehicle.getName());
        Log.d("name", vehicle.getName());
        Log.d("editV", String.valueOf(db.update(Databases.TABLE_VEHICLE, values, Databases.COLUMN_ID_PT + " =?", new String[]{String.valueOf(vehicle.getId())})));
        return db.update(Databases.TABLE_VEHICLE,values,Databases.COLUMN_ID + " =?", new String[]{String.valueOf(vehicle.getId())});
    }

    //Hotel

    @SuppressLint("Range")
    public ArrayList<Hotel> getHotel() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Hotel> list = new ArrayList<>();
        String sql = "SELECT * FROM " + Databases.TABLE_HOTEL;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Hotel hotel = new Hotel();
            hotel.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID))));
            hotel.setImage(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_IMAGE)));
            hotel.setName(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_NAME)));
            hotel.setPhone(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_PHONE)));
            hotel.setPrice(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_PRICE)));
            hotel.setIdDiaDanh(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID_DIADANH))));

            list.add(hotel);
            cursor.moveToNext();
        }
        Log.d("ketqua", String.valueOf(list));
        return list;
    }

    @SuppressLint("Range")
    public ArrayList<Hotel> getHotelID(int id_diadanh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Hotel> list = new ArrayList<>();
        String sql = "SELECT * FROM " + Databases.TABLE_HOTEL + " WHERE " + Databases.COLUMN_ID_DIADANH + " = " + id_diadanh;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Hotel hotel = new Hotel();
            hotel.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID))));
            hotel.setImage(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_IMAGE)));
            hotel.setName(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_NAME)));
            hotel.setPhone(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_PHONE)));
            hotel.setPrice(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_PRICE)));
            hotel.setIdDiaDanh(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID_DIADANH))));

            list.add(hotel);
            cursor.moveToNext();
        }
        Log.d("ketqua", String.valueOf(list));
        return list;
    }

    @SuppressLint("Range")
    public Hotel getHotelDetail(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + Databases.TABLE_HOTEL + " WHERE " + id;//Databases.COLUMN_ID + " = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        Hotel hotel = new Hotel();
        if (cursor.moveToFirst()) {
            hotel.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID))));
            hotel.setImage(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_IMAGE)));
            hotel.setName(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_NAME)));
            hotel.setPhone(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_PHONE)));
            hotel.setPrice(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_PRICE)));
            hotel.setIdDiaDanh(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID_DIADANH))));
        }
        return hotel;
    }

    public int editHotel(Hotel hotel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Databases.COLUMN_IMAGE, hotel.getImage());
        Log.d("image", hotel.getImage());
        Log.d("editH", String.valueOf(db.update(Databases.TABLE_HOTEL, values, Databases.COLUMN_ID + " =?", new String[]{String.valueOf(hotel.getId())})));
        return db.update(Databases.TABLE_HOTEL,values,Databases.COLUMN_ID + " =?", new String[]{String.valueOf(hotel.getId())});
    }

    @SuppressLint("Range")
    public ArrayList<Hotel> searchHotel(String s, int id_diadanh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Hotel> list = new ArrayList<>();
        String sql = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_IMAGE + ", " + COLUMN_PHONE + ", " + COLUMN_ID_DIADANH + ", " + COLUMN_PRICE + ", " + COLUMN_ID_DIADANH
                + " FROM " + TABLE_HOTEL + " WHERE " + COLUMN_NAME + " LIKE '%" + s + "%'" + " AND " + COLUMN_ID_DIADANH + " = " + id_diadanh;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Hotel hotel = new Hotel();
            hotel.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID))));
            hotel.setImage(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_IMAGE)));
            hotel.setName(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_NAME)));
            hotel.setPhone(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_PHONE)));
            hotel.setPrice(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_PRICE)));
            hotel.setIdDiaDanh(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_ID_DIADANH))));

            list.add(hotel);
            cursor.moveToNext();
        }
        Log.d("ketqua", String.valueOf(list));
        return list;
    }

    @SuppressLint("Range")
    public ArrayList<Experience> getExperience() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Experience> list = new ArrayList<>();
        String sql = "SELECT * FROM " + Databases.TABLE_EXPERIENCE;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Experience experience = new Experience();
            experience.setId(cursor.getInt(cursor.getColumnIndex(Databases.COLUMN_ID)));
            experience.setName(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_NAME)));
            experience.setImage(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_IMAGE)));
            experience.setDetail(cursor.getString(cursor.getColumnIndex(Databases.COLUMN_DETAIL)));


            list.add(experience);
            cursor.moveToNext();
        }
        Log.d("ketqua", String.valueOf(list));
        return list;
    }
}
