package com.jonnathan.multas.Clases;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PersonasInterface
{
  @POST("personas")
  Call<List<Personas>> getPersonas();

  @POST("personas/{documento}")
  @FormUrlEncoded
  Call<Personas> getPersona(@Field("documento") int documento);

  @POST("personas/store")
  @FormUrlEncoded
  Call<Personas> store(@Field("documento") int documento,
                        @Field("nombres") String nombres,
                        @Field("apellidos") String apellidos
  );

  @POST("personas/update")
  @FormUrlEncoded
  Call<Personas> update(@Field("id") int id,
                        @Field("documento") int documento,
                        @Field("nombres") String nombres,
                        @Field("apellidos") String apellidos
                        );

  @POST("personas/destroy")
  @FormUrlEncoded
  Call<Personas> destroy(@Field("id") int id);

}
