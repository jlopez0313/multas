package com.jonnathan.multas;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jonnathan.multas.Clases.Personas;
import com.jonnathan.multas.Clases.PersonasInterface;
import com.jonnathan.multas.Clases.Servicios;
import com.jonnathan.multas.Clases.Vehiculos;
import com.jonnathan.multas.Clases.VehiculosInterface;
import com.jonnathan.multas.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehiculosFormFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  EditText placa;
  String strPlaca;
  Button btn, btnBack;
  View rootView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    rootView = inflater.inflate(R.layout.fragment_vehiculos_form, container, false);

    btnBack = (Button) rootView.findViewById(R.id.btnRegresar);
    btnBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Fragment newFragment = new VehiculosFragment();
        android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
      }
    });

    btn = (Button) rootView.findViewById(R.id.btnVehiculos);
    btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        placa = (EditText)rootView.findViewById(R.id.txtPlaca);

        attemptFields();
      }
    });

    return rootView;
  }

  private void attemptFields()
  {
    placa.setError(null);

    strPlaca = placa.getText().toString();

    boolean cancel = false;
    View focusView = null;

    if(TextUtils.isEmpty(strPlaca)){
      placa.setError("Este Campo es Requerido");
      focusView = placa;
      cancel = true;
    }

    if(cancel)
    {
      focusView.requestFocus();
    }else{
      rootView.findViewById(R.id.loader).setVisibility(View.VISIBLE);
      sendForm();
    }
  } // attemptFields

  private void sendForm()
  {
    VehiculosInterface servicio = new Servicios().Servicios().create(VehiculosInterface.class);
    final Call<Vehiculos> created = servicio.store(placa.getText().toString().toUpperCase());
    created.enqueue(new Callback<Vehiculos>() {
      @Override
      public void onResponse(Call<Vehiculos> call, Response<Vehiculos> response) {
        rootView.findViewById(R.id.loader).setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Información Registrada Exitosamente", Toast.LENGTH_LONG).show();
      }

      @Override
      public void onFailure(Call<Vehiculos> call, Throwable t) {
        rootView.findViewById(R.id.loader).setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Error: " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
      }
    });
  }

}
