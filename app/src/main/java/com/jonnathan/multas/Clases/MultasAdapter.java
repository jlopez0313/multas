package com.jonnathan.multas.Clases;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jonnathan.multas.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MultasAdapter extends BaseAdapter
{
  Context context;
  List<Multas> listaMultas;
  View vista;

  public MultasAdapter(Context context, List<Multas> lista) {
    this.context = context;
    this.listaMultas = lista;
  }

  @Override
  public int getCount() {
    return listaMultas.size();
  }

  @Override
  public Object getItem(int i)
  {
    return listaMultas.get(i);
  }

  @Override
  public long getItemId(int i) {
    return listaMultas.get(i).getId();
  }

  @Override
  public View getView(final int i, View view, ViewGroup viewGroup) {
    vista = view;
    LayoutInflater inflater = LayoutInflater.from(context);
    vista = inflater.inflate(R.layout.multa_item, null);
    ImageView edit = (ImageView) vista.findViewById(R.id.edit);
    ImageView trash = (ImageView) vista.findViewById(R.id.trash);

    TextView nombres = (TextView) vista.findViewById(R.id.nombres);
    TextView documento = (TextView) vista.findViewById(R.id.txtDocumento);
    TextView placa = (TextView) vista.findViewById(R.id.txtPlaca);
    TextView codigo = (TextView) vista.findViewById(R.id.txtCodigo);
    TextView valor = (TextView) vista.findViewById(R.id.txtValor);
    TextView estado = (TextView) vista.findViewById(R.id.txtEstado);

    Picasso.with(context).load(R.drawable.ic_edit).into(edit);
    Picasso.with(context).load(R.drawable.ic_trash).into(trash);

    nombres.setText( listaMultas.get(i).getPersona().getNombres() + " " + listaMultas.get(i).getPersona().getApellidos() );
    documento.setText( Html.fromHtml( Integer.toString( listaMultas.get(i).getPersona().getDocumento()) ) );
    placa.setText( Html.fromHtml( listaMultas.get(i).getVehiculo().getPlaca() ) );
    codigo.setText( Html.fromHtml( listaMultas.get(i).getCodigo() ) );
    valor.setText( Html.fromHtml( Integer.toString( listaMultas.get(i).getValor() ) ) );
    estado.setText( Html.fromHtml( Character.toString(listaMultas.get(i).getEstado() ) ) );

    edit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

      }
    });

    trash.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        trashMulta(i, listaMultas.get(i).getId());
      }
    });

    return vista;
  }

  private void trashMulta(final int i, final int id) {
    AlertDialog.Builder builder = new AlertDialog.Builder(vista.getRootView().getContext());
    builder.setCancelable(true);
    builder.setTitle("Confirmar Acción");
    builder.setMessage("¿Realmente desea Eliminar esta Multa?");
    builder.setPositiveButton("Sí",
      new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          vista.getRootView().findViewById(R.id.loader).setVisibility(View.VISIBLE);
          MultasInterface servicio = new Servicios().Servicios().create(MultasInterface.class);

          final Call<Multas> lista = servicio.destroy(id);

          lista.enqueue(new Callback<Multas>() {
            @Override
            public void onResponse(Call<Multas> call, Response<Multas> response) {

              vista.getRootView().findViewById(R.id.loader).setVisibility(View.GONE);
              Toast.makeText(vista.getRootView().getContext(), "Información Eliminada Exitosamente", Toast.LENGTH_LONG).show();
              listaMultas.remove(i);
              notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Multas> call, Throwable t) {
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
