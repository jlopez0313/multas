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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonasFormFragment extends Fragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  EditText documento, nombres, apellidos;
  String strNombres, strApellidos, strDocumento;
  Button btn, btnBack;
  View rootView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    rootView = inflater.inflate(R.layout.fragment_personas_form, container, false);

    btnBack = (Button) rootView.findViewById(R.id.btnRegresar);
    btnBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Fragment newFragment = new PersonasFragment();
        android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.content_main, newFragment);
        transaction.addToBackStack(null);

        transaction.commit();
      }
    });

    btn = (Button) rootView.findViewById(R.id.btnPersonas);
    btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        documento = (EditText)rootView.findViewById(R.id.txtDocumento);
        nombres = (EditText)rootView.findViewById(R.id.txtNombres);
        apellidos = (EditText)rootView.findViewById(R.id.txtApellidos);

        attemptFields();
      }
    });

    return rootView;
  }

  private void attemptFields()
  {
    documento.setError(null);
    nombres.setError(null);
    apellidos.setError(null);

    strNombres = nombres.getText().toString();
    strApellidos = apellidos.getText().toString();
    strDocumento = documento.getText().toString();

    boolean cancel = false;
    View focusView = null;

    if(TextUtils.isEmpty(strNombres)){
      nombres.setError("Este Campo es Requerido");
      focusView = nombres;
      cancel = true;
    }

    if(TextUtils.isEmpty(strApellidos)){
      apellidos.setError("Este Campo es Requerido");
      focusView = apellidos;
      cancel = true;
    }

    if(TextUtils.isEmpty(strDocumento)){
      documento.setError("Este Campo es Requerido");
      focusView = documento;
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
    PersonasInterface servicio = new Servicios().Servicios().create(PersonasInterface.class);
    final Call<Personas> created = servicio.store(Integer.parseInt(documento.getText().toString()),
                                                  nombres.getText().toString().toUpperCase(),
                                                  apellidos.getText().toString().toUpperCase() );
    created.enqueue(new Callback<Personas>() {
      @Override
      public void onResponse(Call<Personas> call, Response<Personas> response) {
        rootView.findViewById(R.id.loader).setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Información Registrada Exitosamente", Toast.LENGTH_LONG).show();
      }

      @Override
      public void onFailure(Call<Personas> call, Throwable t) {
        rootView.findViewById(R.id.loader).setVisibility(View.GONE);
        System.out.println("Error Interno: " + t.getMessage().toString());
        Toast.makeText(getActivity(), "Error Interno: " + t.toString(), Toast.LENGTH_LONG).show();
      }
    });
  }

}
