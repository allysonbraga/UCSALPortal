package com.example.ucsalportal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "noticias.db";
	private static final int DATABASE_VERSION = 1;
    
    public DataBase(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);         
    }
  
    @Override
    public void onCreate(SQLiteDatabase db) {    	    	
    	criaTabela(db);
    }
    
    public void criaTabela(SQLiteDatabase db){
	  	StringBuilder create = new StringBuilder(" ");
	  	create.append("CREATE TABLE IF NOT EXISTS tabela_temp ");		
	  	create.append("( id INTEGER PRIMARY KEY AUTOINCREMENT,");
	  	create.append("  disciplina VARCHAR(100),");
	  	create.append("  resultado VARCHAR(100)");
	  	create.append(");");
	  	create.append("TRUNCATE TABLE tabela_temp");
	  	db.execSQL(create.toString());	
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
	  	StringBuilder create = new StringBuilder(" ");
	  	create.append("CREATE TABLE IF NOT EXISTS tabela_temp ");		
	  	create.append("( id INTEGER PRIMARY KEY AUTOINCREMENT,");
	  	create.append("  disciplina VARCHAR(100),");
	  	create.append("  resultado VARCHAR(100)");
	  	create.append(");");
	  	create.append("TRUNCATE TABLE tabela_temp");
	  	db.execSQL(create.toString());	
	}
	
	@Override
	public void onConfigure(SQLiteDatabase db) {
	  	StringBuilder create = new StringBuilder(" ");
	  	create.append("CREATE TABLE IF NOT EXISTS tabela_temp ");		
	  	create.append("( id INTEGER PRIMARY KEY AUTOINCREMENT,");
	  	create.append("  disciplina VARCHAR(100),");
	  	create.append("  resultado VARCHAR(100)");
	  	create.append(");");
	  	create.append("TRUNCATE TABLE tabela_temp");
	  	db.execSQL(create.toString());
	}
}
