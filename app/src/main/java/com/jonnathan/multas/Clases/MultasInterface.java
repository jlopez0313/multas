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
import retrofit2.http.Query;

public interface MultasInterface
{
  @GET("multas")
  Call<List<Multas>> getMultas();

  @POST("multas/show/{id}")
  Call<Multas> getMulta(@Path("id") int id);

  @POST("multas/store")
  @FormUrlEncoded
  Call<Multas> store(@Field("valor") int valor,
                     @Field("codigo") String codigo,
                     @Field("documento") int documento,
                     @Field("nombres") String nombres,
                     @Field("apellidos") String apellidos,
                     @Field("placa") String placa
  );

  @PUT("multas/update/{id}")
  @FormUrlEncoded
  Call<Multas> update(@Path("id") int id,
                      @Field("codigo") String codigo,
                      @Field("valor") String valor,
                      @Field("documento") int documento,
                      @Field("placa") String placa,
                      @Field("fecha") String fecha,
                      @Field("estado") char estado
  );

  @DELETE("multas/destroy/{id}")
  Call<Multas> destroy(@Path("id") int id);

}
