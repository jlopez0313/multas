package com.jonnathan.multas.Clases;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Servicios
{
  static String baseUrl = "http://betelmotos.com.co/multas/public/api/";

  static Retrofit retrofit = null;

  public Retrofit Servicios()
  {
    if(retrofit == null)
    {
      retrofit = new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    }
    return retrofit;
  }
}
