package com.jonnathan.multas.Clases;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface VehiculosInterface
{
  @GET("vehiculos")
  Call<List<Vehiculos>> getVehiculos();

  @POST("vehiculos/show/{id}")
  Call<Vehiculos> show(@Path("id") int id);

  @POST("vehiculos/store")
  @FormUrlEncoded
  Call<Vehiculos> store(@Field("placa") String placa);

  @PUT("vehiculos/update/{id}")
  @FormUrlEncoded
  Call<Vehiculos> update(@Path("id") int id,
                         @Field("placa") String placa
  );

  @DELETE("vehiculos/destroy/{id}")
  Call<Vehiculos> destroy(@Path("id") int id);

}
