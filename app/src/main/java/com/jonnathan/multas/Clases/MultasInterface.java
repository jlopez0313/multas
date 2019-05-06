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

public interface MultasInterface
{
  @GET("multas")
  Call<List<Multas>> getMultas();

  @POST("multas/show/{id}")
  Call<Multas> getMulta(@Path("id") int id);

  @PUT("multas/store")
  @FormUrlEncoded
  Call<Multas> store(@Field("valor") int valor,
                     @Field("codigo") String codigo,
                     @Field("documento") int documento,
                     @Field("placa") String placa
  );

  @PATCH("multas/update")
  @FormUrlEncoded
  Call<Multas> update(@Field("id") int id,
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
