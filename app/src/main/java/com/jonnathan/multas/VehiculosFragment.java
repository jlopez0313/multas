package com.jonnathan.multas;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.jonnathan.multas.Clases.Servicios;
import com.jonnathan.multas.Clases.Vehiculos;
import com.jonnathan.multas.Clases.VehiculosAdapter;
import com.jonnathan.multas.Clases.VehiculosInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehiculosFragment extends Fragment {

  View rootView;
  Context context;
  ListView listaDatos;
  List<Integer> items = new ArrayList<Integer>();
  ArrayList<Vehiculos> miLista = new ArrayList<Vehiculos>();
  Button btnAdd;

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getVehiculos();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    rootView = inflater.inflate(R.layout.fragment_vehiculos_list, container, false);

    btnAdd = (Button) rootView.findViewById(R.id.addVehiculos);
    btnAdd.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Fragment newFragment = new VehiculosFormFragment();
        android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
      }
    });

    return rootView;
  }

  private void getVehiculos() {
    listaDatos = (ListView) rootView.findViewById(R.id.lstDatos);
    VehiculosInterface servicio = new Servicios().Servicios().create(VehiculosInterface.class);

    final Call<List<Vehiculos>> lista = servicio.getVehiculos();

    lista.enqueue(new Callback<List<Vehiculos>>() {
      @Override
      public void onResponse(Call<List<Vehiculos>> call, Response<List<Vehiculos>> response) {

        getActivity().findViewById(R.id.relativeLayout).setVisibility(View.GONE);

        for(int i = 0; i < response.body().size(); i++){
          items.add( response.body().get(i).getId() );

          miLista.add(new Vehiculos(  response.body().get(i).getId(),
            response.body().get(i).getPlaca() ));
        }

        VehiculosAdapter adaptador = new VehiculosAdapter(getActivity().getApplicationContext(), miLista, new VehiculosFragment());
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
      public void onFailure(Call<List<Vehiculos>> call, Throwable t) {
      }
    });
  }

}
