package com.jonnathan.multas.Clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Personas
{
  @SerializedName("id")
  @Expose
  private int id;

  private int documento;
  private String nombres;
  private String apellidos;

  public Personas(int id, int documento, String nombres, String apellidos) {
    this.id = id;
    this.documento = documento;
    this.nombres = nombres;
    this.apellidos = apellidos;
  }

  public int getId() { return id; }

  public void setId(int id) { this.id = id; }

  public int getDocumento() { return documento; }

  public void setDocumento(int documento) { this.documento = documento; }

  public String getNombres() { return nombres; }

  public void setNombres(String nombres) { this.nombres = nombres; }

  public String getApellidos() { return apellidos; }

  public void setApellidos(String apellidos) { this.apellidos = apellidos; }

  @Override
  public String toString()
  {
    return "Persona{" +
        "id = " + id  +
        "documento = " + documento +
        "id = " + nombres  +
        "id = " + apellidos  +
      "}";
  }
}
