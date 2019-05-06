package com.jonnathan.multas;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.jonnathan.multas.Clases.Multas;
import com.jonnathan.multas.Clases.MultasAdapter;
import com.jonnathan.multas.Clases.MultasInterface;
import com.jonnathan.multas.Clases.Servicios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MultasFragment extends Fragment {

  View rootView;
  Context context;
  ListView listaDatos;
  List<Integer> items = new ArrayList<Integer>();
  ArrayList<Multas> miLista = new ArrayList<Multas>();
  Button btnAdd;

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getMultas();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    rootView = inflater.inflate(R.layout.fragment_multas_list, container, false);

    btnAdd = (Button) rootView.findViewById(R.id.addMultas);
    btnAdd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Fragment newFragment = new MultasFormFragment();
        android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
      }
    });

    return rootView;
  }

  private void getMultas() {
    listaDatos = (ListView) rootView.findViewById(R.id.lstDatos);
    MultasInterface servicio = new Servicios().Servicios().create(MultasInterface.class);

    final Call<List<Multas>> lista = servicio.getMultas();

    lista.enqueue(new Callback<List<Multas>>() {
      @Override
      public void onResponse(Call<List<Multas>> call, Response<List<Multas>> response) {

        getActivity().findViewById(R.id.relativeLayout).setVisibility(View.GONE);

        for(int i = 0; i < response.body().size(); i++){
          items.add( response.body().get(i).getId() );

          miLista.add(new Multas(  response.body().get(i).getId(),
            response.body().get(i).getVehiculo(),
            response.body().get(i).getPersona(),
            response.body().get(i).getValor(),
            response.body().get(i).getCodigo(),
            response.body().get(i).getEstado()
             ));
        }

        MultasAdapter adaptador = new MultasAdapter(getActivity().getApplicationContext(), miLista);
        listaDatos.setAdapter(adaptador);

        listaDatos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Intent intent = new Intent(getActivity(), DetalleMultaActivity.class);
            // intent.putExtra("id", items.get(position).toString());
            // startActivity(intent);
          }
        });
      }

      @Override
      public void onFailure(Call<List<Multas>> call, Throwable t) {
      }
    });
  }
}
