package com.jonnathan.multas.Clases;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jonnathan.multas.PersonasFormFragment;
import com.jonnathan.multas.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonasAdapter extends BaseAdapter
{
  Context context;
  List<Personas> listaPersonas;
  View vista;

  public PersonasAdapter(Context context, List<Personas> lista) {
    this.context = context;
    this.listaPersonas = lista;
  }

  @Override
  public int getCount() {
    return listaPersonas.size();
  }

  @Override
  public Object getItem(int i)
  {
    return listaPersonas.get(i);
  }

  @Override
  public long getItemId(int i) {
    return listaPersonas.get(i).getId();
  }

  @Override
  public View getView(final int i, View view, ViewGroup viewGroup) {
    vista = view;
    LayoutInflater inflater = LayoutInflater.from(context);
    vista = inflater.inflate(R.layout.persona_item, null);
    ImageView edit = (ImageView) vista.findViewById(R.id.edit);
    ImageView trash = (ImageView) vista.findViewById(R.id.trash);

    TextView nombres = (TextView) vista.findViewById(R.id.nombres);
    TextView documento = (TextView) vista.findViewById(R.id.txtDocumento);
    Picasso.with(context).load(R.drawable.ic_edit).into(edit);
    Picasso.with(context).load(R.drawable.ic_trash).into(trash);

    nombres.setText( listaPersonas.get(i).getNombres() + " " + listaPersonas.get(i).getApellidos() );
    documento.setText( Html.fromHtml( Integer.toString( listaPersonas.get(i).getDocumento()) ) );

    edit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        editPersona(i, listaPersonas.get(i).getId());
      }
    });

    trash.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        
      }
    });

    return vista;
  }

  private void editPersona(int i, int id) {

  }

  private void trashPersona(final int i, final int id) {
    AlertDialog.Builder builder = new AlertDialog.Builder(vista.getRootView().getContext());
    builder.setCancelable(true);
    builder.setTitle("Confirmar Acción");
    builder.setMessage("¿Realmente desea Eliminar esta Persona?");
    builder.setPositiveButton("Sí",
      new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          vista.getRootView().findViewById(R.id.loader).setVisibility(View.VISIBLE);
          PersonasInterface servicio = new Servicios().Servicios().create(PersonasInterface.class);

          final Call<Personas> lista = servicio.destroy(id);

          lista.enqueue(new Callback<Personas>() {
            @Override
            public void onResponse(Call<Personas> call, Response<Personas> response) {

              vista.getRootView().findViewById(R.id.loader).setVisibility(View.GONE);
              Toast.makeText(vista.getRootView().getContext(), "Información Eliminada Exitosamente", Toast.LENGTH_LONG).show();
              listaPersonas.remove(i);
              notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Personas> call, Throwable t) {
            }
          });
        }
      });
    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
      }
    });

    builder.show();
  }
}
