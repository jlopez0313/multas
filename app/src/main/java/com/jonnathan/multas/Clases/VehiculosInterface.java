package com.jonnathan.multas.Clases;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VehiculosInterface
{
  @POST("vehiculos")
  Call<List<Vehiculos>> getVehiculos();

  @POST("vehiculos/{id}")
  Call<Vehiculos> getVehiculo(@Path("id") int id);

  @POST("vehiculos/store")
  @FormUrlEncoded
  Call<Vehiculos> store(@Field("placa") String placa);

  @POST("vehiculos/update")
  @FormUrlEncoded
  Call<Vehiculos> update(@Field("id") int id,
                         @Field("placa") String placa
  );

  @POST("vehiculos/destroy")
  @FormUrlEncoded
  Call<Vehiculos> destroy(@Field("id") int id);

}
