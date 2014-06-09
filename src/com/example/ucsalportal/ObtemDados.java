package com.example.ucsalportal;

import com.example.ucsalportal.DataBase;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class ObtemDados extends AsyncTask<String, Void, Boolean> {
	String retorno = "";
	DataBase db = null;
	
    public ObtemDados(Context context) {
    	db = new DataBase(context);         
    }	
	
	@Override
	protected Boolean doInBackground(String... params) {
		try{
			//Log.e("TESTE", "URL: " + params[0]);
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(params[0]);                  
	        HttpResponse resposta = httpClient.execute(httpGet);	             
	        
	        HttpEntity httpEntity = resposta.getEntity();
	        retorno = EntityUtils.toString(httpEntity);            
	        //Log.e("TESTE", "retorno: " + retorno);
	        
	        JSONObject object = (JSONObject) new JSONTokener(retorno).nextValue();	               
	        JSONArray dados = object.getJSONArray("retorno");	         
	        
	        SQLiteDatabase sql = db.getWritableDatabase();
			sql.delete("tabela_temp", null, null);
	        
	        for(int i=0;i<dados.length();i++)
	        { 	                	
		    	JSONObject lines = (JSONObject) new JSONTokener(dados.getString(i)).nextValue();
		    	String disciplina = lines.getString("disciplina");
		        String resultado = lines.getString("resultado");              	
	
				try { 			
					ContentValues valor = new ContentValues();
					valor.put("disciplina", disciplina);
					valor.put("resultado", resultado);	
					sql.insert("tabela_temp", null, valor);				
				}catch (Exception e) {
					Log.e("falhou", e.getMessage());;
				}
	        }            

	    } catch (Exception e) {
	       	Log.e("ERRO", "falha ao conectar: " + e.getMessage());
	        return false;
	    }
		return true;
	}
}