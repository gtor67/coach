package com.example.coachme;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

 /////////////////////////////////////////////////////////////////////
 // Constants & Data
 /////////////////////////////////////////////////////////////////////
 // For logging:
 private static final String TAG = "DBAdapter";
 
 // DB Fields
 public static final String KEY_ROWID = "_id";
 public static final int COL_ROWID = 0;
 /*
  * CHANGE 1:
  */
 // TODO: Setup your fields here:
// public static final String KEY_NAME = "name";
// public static final String KEY_STUDENTNUM = "studentnum";
// public static final String KEY_FAVCOLOUR = "favcolour";
 
 
 public static final String KEY_LEVEL = "level";
 public static final String KEY_TYPE = "type";
 public static final String KEY_FOCUS = "focus";
 public static final String KEY_PROCEDURE = "procedure";
 public static final String KEY_TITLE ="title";
 public static final String KEY_URL = "url";
 
 
 
 // TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
// public static final int COL_NAME = 1;
// public static final int COL_STUDENTNUM = 2;
// public static final int COL_FAVCOLOUR = 3;
 
 public static final int COL_LEVEL = 1;
 public static final int COL_TYPE = 2;
 public static final int COL_FOCUS = 3;
 public static final int COL_PROCEDURE = 4;
 public static final int COL_TITLE = 5;
 public static final int COL_URL = 6;
 
 //public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_NAME, KEY_STUDENTNUM, KEY_FAVCOLOUR};
  public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_LEVEL, KEY_TYPE, KEY_URL};
 
 // DB info: it's name, and the table we are using (just one).
 public static final String DATABASE_NAME = "MyDb";
 public static final String DATABASE_TABLE = "mainTable";
 // Track DB version if a new version of your app changes the format.
 public static final int DATABASE_VERSION = 2; 
 
 private static final String DATABASE_CREATE_SQL = 
   "create table " + DATABASE_TABLE 
   + " (" + KEY_ROWID + " integer primary key autoincrement, "
   
   /*
    * CHANGE 2:
    */
   // TODO: Place your fields here!
   // + KEY_{...} + " {type} not null"
   // - Key is the column name you created above.
   // - {type} is one of: text, integer, real, blob
   //  (http://www.sqlite.org/datatype3.html)
   //  - "not null" means it is a required field (must be given a value).
   // NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
//   + KEY_NAME + " text not null, "
//   + KEY_STUDENTNUM + " integer not null, "
//   + KEY_FAVCOLOUR + " string not null"
//   
   + KEY_LEVEL + " string not null, "
   + KEY_TYPE + " string not null, "
   + KEY_FOCUS + " string not null, "
   + KEY_PROCEDURE + " string not null, "
   + KEY_TITLE + " string not null UNIQUE, "
   + KEY_URL + " string not null"
   
   // Rest  of creation:
   + ");";
 
 // Context of application who uses us.
 private final Context context;
 
 private DatabaseHelper myDBHelper;
 private SQLiteDatabase db;

 /////////////////////////////////////////////////////////////////////
 // Public methods:
 /////////////////////////////////////////////////////////////////////
 
 public DBAdapter(Context ctx) {
  this.context = ctx;
  myDBHelper = new DatabaseHelper(context);
 }
 
 // Open the database connection.
 public DBAdapter open() {
  db = myDBHelper.getWritableDatabase();
  return this;
 }
 
 // Close the database connection.
 public void close() {
  myDBHelper.close();
 }
 
 // Add a new set of values to the database.
 public long insertRow(String level, String type,String focus, String procedure,String title ,String url) {
  /*
   * CHANGE 3:
   */  
  // TODO: Update data in the row with new fields.
  // TODO: Also change the function's arguments to be what you need!
  // Create row's data:
//  ContentValues initialValues = new ContentValues();
//  initialValues.put(KEY_NAME, name);
//  initialValues.put(KEY_STUDENTNUM, studentNum);
//  initialValues.put(KEY_FAVCOLOUR, favColour);
  
  ContentValues initialValues = new ContentValues();
  initialValues.put(KEY_LEVEL, level);
  initialValues.put(KEY_TYPE, type);
  initialValues.put(KEY_FOCUS, focus);
  initialValues.put(KEY_PROCEDURE, procedure);
  initialValues.put(KEY_TITLE, title);
  initialValues.put(KEY_URL, url);
  
  // Insert it into the database.
  return db.insert(DATABASE_TABLE, null, initialValues);
 }
 
 // Delete a row from the database, by rowId (primary key)
 public boolean deleteRow(long rowId) {
  String where = KEY_ROWID + "=" + rowId;
  return db.delete(DATABASE_TABLE, where, null) != 0;
 }
 
 public void deleteAll() {
  Cursor c = getAllRows();
  long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
  if (c.moveToFirst()) {
   do {
    deleteRow(c.getLong((int) rowId));    
   } while (c.moveToNext());
  }
  c.close();
 }
 
 // Return all data in the database.
 public Cursor getAllRows() {
  String where = null;
  Cursor c =  db.query(true, DATABASE_TABLE, ALL_KEYS, 
       where, null, null, null, null, null);
  if (c != null) {
   c.moveToFirst();
  }
  return c;
 }
 public List<String> listdata(String levelb ,String typeb){
	 String s;
	 if(typeb == "All"){
		  s="SELECT * FROM mainTable  WHERE level='"+levelb+"' ";
	 }else{
	  //String s="SELECT * FROM mainTable WHERE level='"+levelb+"'";
	     s="SELECT * FROM mainTable WHERE level='"+levelb+"' and type='"+typeb+"'";
	 }
	  List<String> data = new ArrayList<String>();
	  String where = KEY_ROWID + "=" + KEY_TYPE;
	  //
	  //Cursor c = db.rawQuery("SELECT * FROM mainTable",null);
	  
	  //try to change the search
	  Cursor c =db.rawQuery(s, null);
	         
	  while (c.moveToNext()) {  
	     //int is the column number from table
	   if(typeb=="All"){
	    data.add(c.getString(1)+" "+ c.getString(2)+" "+c.getString(5));   
	   }else{
	    data.add(c.getString(5));  
	   }
	    
	    }  
	    c.close();  
	    db.close();     
	 return data; 
	 }

 public List<String> listBeginner(String level){
	 List<String> data = new ArrayList<String>();
	 String where = KEY_ROWID + "=" + KEY_TYPE;
	//
	 Cursor c = db.rawQuery("SELECT * FROM mainTable",null);
//	 Cursor c = db.query(DATABASE_TABLE, new String[]{KEY_LEVEL,KEY_TYPE,KEY_URL}, KEY_ROWID + "=?",
	//		 new String[]{String.valueOf(KEY_LEVEL)},null,null,null,null);
					 
	 
	 
	 
	 while (c.moveToNext())
	 {  
		   //int is the column number from table
		 if( (c.getString(1)).equals(level) )
		   data.add(c.getString(0)+" "+ c.getString(1) + " " + c.getString(2));  
	 }  
		  c.close();  
		  db.close();  	  
	return data; 
 }
 
 /*
 public String getColumns(long id)
 {
	 Cursor c = db.rawQuery("SELECT * FROM mainTable",null);
	 while (c.moveToNext())
	 {  
		   //int is the column number from table
		 if( (c.getString(1)).equals(level) )
		   data.add(c.getString(0)+" "+ c.getString(1) + " " + c.getString(2));  
	 }  
		  c.close();  
		  db.close(); 
 }
 */
 
 // Get a specific row (by rowId)
 public Cursor getRow(long rowId) {
  String where = KEY_ROWID + "=" + rowId;
  Cursor c =  db.query(true, DATABASE_TABLE, ALL_KEYS, 
      where, null, null, null, null, null);
  if (c != null) {
   c.moveToFirst();
  }
  return c;
 }
 
 // Change an existing row to be equal to new data.
// public boolean updateRow(long rowId, String name, int studentNum, String favColour) {
   
  public boolean updateRow(long rowId, String level, String type, String focus,String procedure,String title,String url) {
  String where = KEY_ROWID + "=" + rowId;

  /*
   * CHANGE 4:
   */
  // TODO: Update data in the row with new fields.
  // TODO: Also change the function's arguments to be what you need!
  // Create row's data:
  ContentValues newValues = new ContentValues();
  newValues.put(KEY_LEVEL, level);
  newValues.put(KEY_TYPE, type);
  newValues.put(KEY_FOCUS, focus);
  newValues.put(KEY_PROCEDURE, procedure);
  newValues.put(KEY_TITLE, title);
  newValues.put(KEY_URL, url);
  
  // Insert it into the database.
  return db.update(DATABASE_TABLE, newValues, where, null) != 0;
 }
 
 
 
 /////////////////////////////////////////////////////////////////////
 // Private Helper Classes:
 /////////////////////////////////////////////////////////////////////
 
 /**
  * Private class which handles database creation and upgrading.
  * Used to handle low-level database access.
  */
 private static class DatabaseHelper extends SQLiteOpenHelper
 {
  DatabaseHelper(Context context) {
   super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase _db) {
   _db.execSQL(DATABASE_CREATE_SQL);   

  }

  @Override
  public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
   Log.w(TAG, "Upgrading application's database from version " + oldVersion
     + " to " + newVersion + ", which will destroy all old data!");
   
   // Destroy old database:
   _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
   
   // Recreate new database:
   onCreate(_db);
  }
 }

}



