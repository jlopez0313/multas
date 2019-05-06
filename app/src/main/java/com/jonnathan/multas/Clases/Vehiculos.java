package com.jonnathan.multas.Clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Vehiculos
{
  @SerializedName("id")
  @Expose
  private int id;

  private String placa;

  public Vehiculos(int id, String placa) {
    this.id = id;
    this.placa = placa;
  }

  public int getId() { return id; }

  public void setId(int id) { this.id = id; }

  public String getPlaca() { return placa; }

  public void setPlaca(String placa) { this.placa = placa; }

  @Override
  public String toString()
  {
    return "Vehiculo{" +
      "id = " + id  +
      "placa = " + placa  +
      "}";
  }
}
