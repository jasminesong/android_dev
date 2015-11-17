package ruoyun.brandeis.edu.mymaps;

/**
 * Created by reallifejasmine on 11/16/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class MySqliteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ParkLocation";

    public MySqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create  table
        String CREATE_LOCATION_TABLE = "CREATE TABLE locations ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "lat TEXT , "+
                "long TEXT )";

        // create table
        db.execSQL(CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS locations");

        // create fresh locations table
        this.onCreate(db);
    }


    // Locations table name
    private static final String TABLE_LOCATIONS = "locations";

    // Locations Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_LAT = "lat";
    private static final String KEY_LONG = "long";

    private static final String[] COLUMNS = {KEY_ID,KEY_LAT,KEY_LONG};


    public void addLocation(LatLng latLng){
        //for logging
        Log.d("addLocation", latLng.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_LAT, latLng.latitude); // get lat
        values.put(KEY_LONG, latLng.longitude); // get long

        // 3. insert
        db.insert(TABLE_LOCATIONS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values


        //4.get
        // build query
        Cursor cursor =
                db.query(TABLE_LOCATIONS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(1) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();



        //log
        long numRows = DatabaseUtils.queryNumEntries(db,TABLE_LOCATIONS);

        Log.d("getBook",String.valueOf(numRows));







        // 4. close
        db.close();


    }
}


