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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.jonnathan.multas.Clases.Multas;
import com.jonnathan.multas.Clases.MultasInterface;
import com.jonnathan.multas.Clases.Personas;
import com.jonnathan.multas.Clases.PersonasInterface;
import com.jonnathan.multas.Clases.Servicios;
import com.jonnathan.multas.Clases.Vehiculos;
import com.jonnathan.multas.Clases.VehiculosInterface;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MultasFormFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  EditText documento, nombres, apellidos, placa, codigo, valor, estado;
  String strDocumento, strNombres, strApellidos, strPlaca, strCodigo, strValor, strEstado;
  LinearLayout layoutPersonas;
  Spinner estados;
  Button btn;
  View rootView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    rootView = inflater.inflate(R.layout.fragment_multas_form, container, false);

    layoutPersonas = (LinearLayout) rootView.findViewById(R.id.layoutPersonas);
    documento = (EditText) rootView.findViewById(R.id.txtDocumento);
    nombres = (EditText) rootView.findViewById(R.id.txtNombres);
    apellidos = (EditText) rootView.findViewById(R.id.txtApellidos);
    placa = (EditText) rootView.findViewById(R.id.txtPlaca);
    codigo = (EditText) rootView.findViewById(R.id.txtCodigo);
    valor = (EditText) rootView.findViewById(R.id.txtValor);
    estados =(Spinner)rootView.findViewById(R.id.estados);

    String[] spinnerArray = new String[3];
    HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();

    spinnerMap.put(0,"P");
    spinnerMap.put(1,"F");
    spinnerMap.put(2,"C");

    spinnerArray[0] = "Pendiente";
    spinnerArray[1] = "Pagada";
    spinnerArray[2] = "Cobro Coactivo";


    ArrayAdapter<String> adapter =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, spinnerArray);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    estados.setAdapter(adapter);



    documento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus)
        {
          if(!documento.getText().toString().equals(""))
          {
            PersonasInterface servicio = new Servicios().Servicios().create(PersonasInterface.class);
            final Call<Personas> persona = servicio.getPersona(Integer.parseInt(documento.getText().toString()));

            persona.enqueue(new Callback<Personas>() {
              @Override
              public void onResponse(Call<Personas> call, Response<Personas> response) {
                layoutPersonas.setVisibility(View.VISIBLE);
                nombres.setText(response.body().getNombres());
                apellidos.setText(response.body().getApellidos());
              }

              @Override
              public void onFailure(Call<Personas> call, Throwable t) {
                rootView.findViewById(R.id.loader).setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error: " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
              }
            });
          }else{
            layoutPersonas.setVisibility(View.GONE);
            nombres.setText("");
            apellidos.setText("");
          }
        }

      }
    });


    btn = (Button) rootView.findViewById(R.id.btnMultas);
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
    boolean cancel = false;
    View focusView = null;

    documento.setError(null);
    strDocumento = documento.getText().toString();

    if(TextUtils.isEmpty(strDocumento)){
      placa.setError("Este Campo es Requerido");
      focusView = documento;
      cancel = true;
    }


    nombres.setError(null);
    strNombres = nombres.getText().toString();

    if(TextUtils.isEmpty(strNombres)){
      placa.setError("Este Campo es Requerido");
      focusView = nombres;
      cancel = true;
    }


    apellidos.setError(null);
    strApellidos = apellidos.getText().toString();

    if(TextUtils.isEmpty(strApellidos)){
      placa.setError("Este Campo es Requerido");
      focusView = apellidos;
      cancel = true;
    }

    placa.setError(null);
    strPlaca = placa.getText().toString();

    if(TextUtils.isEmpty(strPlaca)){
      placa.setError("Este Campo es Requerido");
      focusView = placa;
      cancel = true;
    }


    codigo.setError(null);
    strCodigo = codigo.getText().toString();

    if(TextUtils.isEmpty(strCodigo)){
      codigo.setError("Este Campo es Requerido");
      focusView = codigo;
      cancel = true;
    }


    valor.setError(null);
    strValor = valor.getText().toString();

    if(TextUtils.isEmpty(strValor)){
      valor.setError("Este Campo es Requerido");
      focusView = valor;
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
    MultasInterface servicio = new Servicios().Servicios().create(MultasInterface.class);
    final Call<Multas> created = servicio.store(
        Integer.parseInt(valor.getText().toString()),
        codigo.getText().toString(),
        Integer.parseInt(documento.getText().toString()),
        placa.getText().toString()
    );

    created.enqueue(new Callback<Multas>() {
      @Override
      public void onResponse(Call<Multas> call, Response<Multas> response) {
        rootView.findViewById(R.id.loader).setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Información Registrada Exitosamente", Toast.LENGTH_LONG).show();
      }

      @Override
      public void onFailure(Call<Multas> call, Throwable t) {
        rootView.findViewById(R.id.loader).setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Error: " + t.getMessage().toString(), Toast.LENGTH_LONG).show();
      }
    });
  }

}
