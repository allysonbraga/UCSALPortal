package com.example.ucsalportal;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;

import com.example.ucsalportal.ObtemDados;
import com.example.ucsalportal.ArrayLista;
import com.example.ucsalportal.ExibeListagem;

public class MainActivity extends ActionBarActivity implements ActionBar.OnNavigationListener {
	static ListView lista;
	static List<ArrayLista> itens = new ArrayList<ArrayLista>();
	ExibeListagem arrayAdapter = new ExibeListagem(this, R.layout.itens_listagem, itens);
	
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		lista = (ListView) findViewById(R.id.lista);
		lista.setAdapter(arrayAdapter); 

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                new ArrayAdapter<String>(
                        actionBar.getThemedContext(),
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new String[] {
                                getString(R.string.title_section1),
                                getString(R.string.title_section2),
                        }),
                this);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getSupportActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getSupportActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	//Intent intent= new Intent(this, LoginActivity.class);
        	//startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
        return true;
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        	
        	int tipoSelecao = getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            
            DataBase db = new DataBase(this.getActivity());
     	    SQLiteDatabase sql = db.getReadableDatabase();
     	    
    		ProgressDialog progresso;
    		progresso = ProgressDialog.show(this.getActivity(), "conectando","Por Favor Aguarde...");  

    		
            if(tipoSelecao == 1){          	
            	ObtemDados conn = new ObtemDados(getActivity());
            	conn.execute("http://www.twon.com.br/testes/ws/getDados.asp?tipo=notas");
	       		Cursor cursor = sql.rawQuery("select id, disciplina, resultado from tabela_temp", null);
	       		       		
	       		while(cursor.moveToNext()){	       				       			
	       			int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
	       			String disciplina = cursor.getString(cursor.getColumnIndex("disciplina"));
	       			String resultado = cursor.getString(cursor.getColumnIndex("resultado"));
	       			
	       			Log.e("CONSULTA", "id: " + id);
	       			Log.e("CONSULTA", "disciplina: " + disciplina);
	       			Log.e("CONSULTA", "resultado: " + resultado);	       			

	       			itens.add(new ArrayLista(id, disciplina, resultado));
	       			Toast.makeText(this.getActivity(), "notas carregada", Toast.LENGTH_SHORT).show();
	       		}               	                
            }else if(tipoSelecao == 2){
            	ObtemDados conn = new ObtemDados(getActivity());
            	conn.execute("http://www.twon.com.br/testes/ws/getDados.asp?tipo=faltas");
	       		Cursor cursor = sql.rawQuery("select id, disciplina, resultado from tabela_temp", null);
	       		
	       		while(cursor.moveToNext()){	       				       			
	       			int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
	       			String disciplina = cursor.getString(cursor.getColumnIndex("disciplina"));
	       			String resultado = cursor.getString(cursor.getColumnIndex("resultado"));
	       			
	       			Log.e("CONSULTA", "id: " + id);
	       			Log.e("CONSULTA", "disciplina: " + disciplina);
	       			Log.e("CONSULTA", "subtitulo: " + resultado);	       			

	       			itens.add(new ArrayLista(id, disciplina, resultado));
	       			Toast.makeText(this.getActivity(), "notas carregada", Toast.LENGTH_SHORT).show();
	       		} 
            	Toast.makeText(this.getActivity(), "faltas carregada", Toast.LENGTH_SHORT).show();
            }
           
            progresso.dismiss();
            return rootView;
        }
    }

    
}