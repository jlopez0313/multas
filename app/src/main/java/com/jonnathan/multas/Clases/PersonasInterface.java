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

public interface PersonasInterface
{
  @GET("personas")
  Call<List<Personas>> getPersonas();

  @POST("personas/show/{id}")
  Call<Personas> getPersona(@Path("id") int documento);


  @POST("personas/documento")
  @FormUrlEncoded
  Call<Personas> porDocumento(@Field("documento") int documento);


  @PUT("personas/store")
  @FormUrlEncoded
  Call<Personas> store(@Field("documento") int documento,
                        @Field("nombres") String nombres,
                        @Field("apellidos") String apellidos
  );

  @PATCH("personas/update")
  @FormUrlEncoded
  Call<Personas> update(@Field("id") int id,
                        @Field("documento") int documento,
                        @Field("nombres") String nombres,
                        @Field("apellidos") String apellidos
                        );

  @DELETE("personas/destroy/{id}")
  Call<Personas> destroy(@Path("id") int id);

}
