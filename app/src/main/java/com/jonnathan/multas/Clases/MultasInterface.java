package com.jonnathan.multas.Clases;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MultasInterface
{
  @POST("multas")
  Call<List<Multas>> getMultas();

  @POST("multas/{id}")
  Call<Multas> getMulta(@Path("id") int id);

  @POST("multas/store")
  @FormUrlEncoded
  Call<Multas> store(@Field("valor") int valor,
                     @Field("codigo") String codigo,
                     @Field("documento") int documento,
                     @Field("placa") String placa
  );

  @POST("multas/update")
  @FormUrlEncoded
  Call<Multas> update(@Field("id") int id,
                      @Field("codigo") String codigo,
                      @Field("valor") String valor,
                      @Field("documento") int documento,
                      @Field("placa") String placa,
                      @Field("fecha") String fecha,
                      @Field("estado") char estado
  );

  @POST("multas/destroy")
  @FormUrlEncoded
  Call<Multas> destroy(@Field("id") int id);

}
