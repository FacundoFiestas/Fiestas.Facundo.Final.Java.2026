package Persistencia;

import models.*;
import enums.TipoCombustible;

import java.io.*;
import java.util.*;

public class VehiculoCsvDAO {

    public void guardar(List<Vehiculo> lista, String ruta) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(ruta))) {

            
            writer.println("tipo,marca,anio,precio,tipoCombustible," +
                    "cantidadPuertas,esAutomatico," +
                    "cilindrada,tieneFrenosABS," +
                    "capacidadCarga,es4x4");

            for (Vehiculo v : lista) {

                String tipo = v.getClass().getSimpleName();

                String marca = v.getMarca();
                int anio = v.getAnio();
                double precio = v.getPrecio();
                String combustible = v.getTipoCombustible().name();

                String cantidadPuertas = "";
                String esAutomatico = "";
                String cilindrada = "";
                String tieneFrenosABS = "";
                String capacidadCarga = "";
                String es4x4 = "";

                if (v instanceof Auto auto) {
                    cantidadPuertas = String.valueOf(auto.getCantidadPuertas());
                    esAutomatico = String.valueOf(auto.isEsAutomatico());
                }

                if (v instanceof Moto moto) {
                    cilindrada = String.valueOf(moto.getCilindrada());
                    tieneFrenosABS = String.valueOf(moto.isTieneFrenosABS());
                }

                if (v instanceof Camioneta camioneta) {
                    capacidadCarga = String.valueOf(camioneta.getCapacidadCarga());
                    es4x4 = String.valueOf(camioneta.isEs4x4());
                }

                writer.println(String.join(",",
                        tipo,
                        marca,
                        String.valueOf(anio),
                        String.valueOf(precio),
                        combustible,
                        cantidadPuertas,
                        esAutomatico,
                        cilindrada,
                        tieneFrenosABS,
                        capacidadCarga,
                        es4x4
                ));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  
    public List<Vehiculo> recuperar(String ruta) {

        List<Vehiculo> lista = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ruta))) {

            String linea;
            reader.readLine(); // saltar encabezado

            while ((linea = reader.readLine()) != null) {

                String[] partes = linea.split(",");

                String tipo = partes[0];
                String marca = partes[1];
                int anio = Integer.parseInt(partes[2]);
                double precio = Double.parseDouble(partes[3]);
                TipoCombustible combustible = TipoCombustible.valueOf(partes[4]);

                switch (tipo) {

                    case "Auto":
                        int puertas = Integer.parseInt(partes[5]);
                        boolean automatico = Boolean.parseBoolean(partes[6]);

                        lista.add(new Auto(
                                puertas,
                                automatico,
                                marca,
                                anio,
                                precio,
                                combustible
                        ));
                        break;

                    case "Moto":
                        int cilindrada = Integer.parseInt(partes[7]);
                        boolean abs = Boolean.parseBoolean(partes[8]);

                        lista.add(new Moto(
                                cilindrada,
                                abs,
                                marca,
                                anio,
                                precio,
                                combustible
                        ));
                        break;

                    case "Camioneta":
                        int capacidad = Integer.parseInt(partes[9]);
                        boolean es4x4 = Boolean.parseBoolean(partes[10]);

                        lista.add(new Camioneta(
                                capacidad,
                                es4x4,
                                marca,
                                anio,
                                precio,
                                combustible
                        ));
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
