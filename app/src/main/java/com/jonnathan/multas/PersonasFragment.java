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

import com.jonnathan.multas.Clases.Personas;
import com.jonnathan.multas.Clases.PersonasAdapter;
import com.jonnathan.multas.Clases.PersonasInterface;
import com.jonnathan.multas.Clases.Servicios;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonasFragment extends Fragment {

  View rootView;
  Context context;
  ListView listaDatos;
  List<Integer> items = new ArrayList<Integer>();
  ArrayList<Personas> miLista = new ArrayList<Personas>();
  Button btnAdd;

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getPersonas();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    rootView = inflater.inflate(R.layout.fragment_personas_list, container, false);

    btnAdd = (Button) rootView.findViewById(R.id.addPersonas);
    btnAdd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Fragment newFragment = new PersonasFormFragment();
        android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
      }
    });

    return rootView;
  }

  private void getPersonas() {
    listaDatos = (ListView) rootView.findViewById(R.id.lstDatos);
    PersonasInterface servicio = new Servicios().Servicios().create(PersonasInterface.class);

    final Call<List<Personas>> lista = servicio.getPersonas();

    lista.enqueue(new Callback<List<Personas>>() {
      @Override
      public void onResponse(Call<List<Personas>> call, Response<List<Personas>> response) {

        getActivity().findViewById(R.id.relativeLayout).setVisibility(View.GONE);

        for(int i = 0; i < response.body().size(); i++){
          items.add( response.body().get(i).getId() );

          miLista.add(new Personas(  response.body().get(i).getId(),
            response.body().get(i).getDocumento(),
            response.body().get(i).getNombres(),
            response.body().get(i).getApellidos() ));
        }

        PersonasAdapter adaptador = new PersonasAdapter(getActivity().getApplicationContext(), miLista);
        listaDatos.setAdapter(adaptador);

        listaDatos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Intent intent = new Intent(getActivity(), DetallePersonaActivity.class);
            // intent.putExtra("id", items.get(position).toString());
            // startActivity(intent);
          }
        });
      }

      @Override
      public void onFailure(Call<List<Personas>> call, Throwable t) {
      }
    });
  }
}
