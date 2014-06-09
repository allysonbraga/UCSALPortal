package com.example.ucsalportal;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ExibeListagem extends BaseAdapter{
	private LayoutInflater Inflater;
	private List<ArrayLista> lista;
	int resource;

	public ExibeListagem(Context context, int layout, List<ArrayLista> arayLista) {
		lista = arayLista;
		resource = layout;
		Inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public ArrayLista getItem(int posicao) {
		return lista.get(posicao);
	}

	@Override
	public long getItemId(int posicao) {
		return lista.get(posicao).getId();
	}

	@Override
	public View getView(int posicao, View convertView, ViewGroup viewGroup) {
		View view;
		
		if(convertView == null){
			view = Inflater.inflate(resource, null);
		}else{
			view = convertView;
		}
		
		ArrayLista item = lista.get(posicao);

		TextView titulo = (TextView) view.findViewById(R.id.titulo);
		titulo.setText(item.getTitulo());
		
		TextView subtitulo = (TextView) view.findViewById(R.id.subtitulo);
		subtitulo.setText(item.getSubtitulo());

		return view;
	}
}
