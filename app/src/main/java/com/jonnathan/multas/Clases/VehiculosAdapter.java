package com.jonnathan.multas.Clases;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jonnathan.multas.R;
import com.jonnathan.multas.VehiculosFormFragment;
import com.jonnathan.multas.VehiculosFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehiculosAdapter extends BaseAdapter
{
  Context context;
  List<Vehiculos> listaVehiculos;
  View vista;
  VehiculosFragment myFragment;

  public VehiculosAdapter(Context context, List<Vehiculos> lista, VehiculosFragment myFragment) {
    this.context = context;
    this.listaVehiculos = lista;
    this.myFragment = myFragment;
  }

  @Override
  public int getCount() {
    return listaVehiculos.size();
  }

  @Override
  public Object getItem(int i)
  {
    return listaVehiculos.get(i);
  }

  @Override
  public long getItemId(int i) {
    return listaVehiculos.get(i).getId();
  }

  @Override
  public View getView(final int i, View view, ViewGroup viewGroup) {
    vista = view;
    LayoutInflater inflater = LayoutInflater.from(context);
    vista = inflater.inflate(R.layout.vehiculo_item, null);

    TextView placa = (TextView) vista.findViewById(R.id.placa);
    ImageView edit = (ImageView) vista.findViewById(R.id.edit);
    ImageView trash = (ImageView) vista.findViewById(R.id.trash);

    placa.setText( listaVehiculos.get(i).getPlaca() );
    Picasso.with(context).load(R.drawable.ic_edit).into(edit);
    Picasso.with(context).load(R.drawable.ic_trash).into(trash);

    edit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

      }
    });

    trash.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        trashVehiculo(i, listaVehiculos.get(i).getId());
      }
    });

    return vista;
  }


  public void trashVehiculo(final int i, final int id)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(vista.getRootView().getContext());
    builder.setCancelable(true);
    builder.setTitle("Confirmar Acción");
    builder.setMessage("¿Realmente desea Eliminar este Vehiculo?");
    builder.setPositiveButton("Sí",
      new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          vista.getRootView().findViewById(R.id.loader).setVisibility(View.VISIBLE);
          VehiculosInterface servicio = new Servicios().Servicios().create(VehiculosInterface.class);

          final Call<Vehiculos> lista = servicio.destroy(id);

          lista.enqueue(new Callback<Vehiculos>() {
            @Override
            public void onResponse(Call<Vehiculos> call, Response<Vehiculos> response) {

              vista.getRootView().findViewById(R.id.loader).setVisibility(View.GONE);
              Toast.makeText(vista.getRootView().getContext(), "Información Eliminada Exitosamente", Toast.LENGTH_LONG).show();
              listaVehiculos.remove(i);
              notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Vehiculos> call, Throwable t) {
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
