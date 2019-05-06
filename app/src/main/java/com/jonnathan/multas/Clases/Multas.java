package com.jonnathan.multas.Clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Multas
{
  @SerializedName("id")
  @Expose
  private int id;

  private Vehiculos vehiculo;
  private Personas persona;
  private int valor;
  private String codigo;
  private char estado;
  private String fecha;

  public Multas(int id, Vehiculos vehiculo, Personas persona, int valor, String codigo, char estado) {
    this.id = id;
    this.vehiculo = vehiculo;
    this.persona = persona;
    this.valor = valor;
    this.codigo = codigo;
    this.estado = estado;
  }

  public int getId() { return id; }

  public void setId(int id) { this.id = id; }

  public Vehiculos getVehiculo() { return vehiculo; }

  public void setVehiculo(Vehiculos vehiculo) { this.vehiculo = vehiculo; }

  public Personas getPersona() { return persona; }

  public void setPersona(Personas persona) { this.persona = persona; }

  public int getValor() { return valor; }

  public void setValor(int valor) { this.valor = valor; }

  public String getCodigo() { return codigo; }

  public void setCodigo(String codigo) { this.codigo = codigo; }

  public char getEstado() { return estado; }

  public void setEstado(char estado) { this.estado = estado; }

  public String getFecha() { return fecha; }

  public void setFecha(String fecha) { this.fecha = fecha; }

  @Override
  public String toString()
  {
    return "Multa{" +
      "id = " + id  +
      "codigo = " + codigo  +
      "valor = " + valor  +
      "fecha = " + fecha  +
      "Persona = " + persona.getNombres() + " " + persona.getApellidos() +
      "Vehiculo = " + vehiculo.getPlaca() +
      "}";
  }
}
