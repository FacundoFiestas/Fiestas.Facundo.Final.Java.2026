package Persistencia;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import models.*;
import enums.TipoCombustible;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class VehiculoJsonDAO {

    private final Gson gson;

    public VehiculoJsonDAO() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

        public void guardar(List<? extends Vehiculo> lista, String ruta) {

        List<Map<String, Object>> listaSerializable = new ArrayList<>();

        for (Vehiculo v : lista) {

            Map<String, Object> map = new HashMap<>();

            map.put("tipo", v.getClass().getSimpleName());
            map.put("marca", v.getMarca());
            map.put("anio", v.getAnio());
            map.put("precio", v.getPrecio());
            map.put("tipoCombustible", v.getTipoCombustible().name());

            if (v instanceof Auto auto) {
                map.put("cantidadPuertas", auto.getCantidadPuertas());
                map.put("esAutomatico", auto.isEsAutomatico());
            }

            if (v instanceof Moto moto) {
                map.put("cilindrada", moto.getCilindrada());
                map.put("tieneFrenosABS", moto.isTieneFrenosABS());
            }

            if (v instanceof Camioneta camioneta) {
                map.put("capacidadCarga", camioneta.getCapacidadCarga());
                map.put("es4x4", camioneta.isEs4x4());
            }

            listaSerializable.add(map);
        }

        try (Writer writer = new FileWriter(ruta)) {
            gson.toJson(listaSerializable, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Vehiculo> recuperar(String ruta) {

        List<Vehiculo> lista = new ArrayList<>();

        try (Reader reader = new FileReader(ruta)) {

            Type tipoLista = new TypeToken<List<Map<String, Object>>>() {}.getType();
            List<Map<String, Object>> listaLeida = gson.fromJson(reader, tipoLista);

            for (Map<String, Object> map : listaLeida) {

                String tipo = map.get("tipo").toString();
                String marca = map.get("marca").toString();
                int anio = ((Double) map.get("anio")).intValue();
                double precio = Double.parseDouble(map.get("precio").toString());

                TipoCombustible tipoCombustible =
                        TipoCombustible.valueOf(map.get("tipoCombustible").toString());

                switch (tipo) {

                    case "Auto":
                       int puertas = ((Double) map.get("cantidadPuertas")).intValue();
                        boolean automatico = Boolean.parseBoolean(map.get("esAutomatico").toString());

                        lista.add(new Auto(
                                puertas,
                                automatico,
                                marca,
                                anio,
                                precio,
                                tipoCombustible
                        ));
                        break;

                    case "Moto":
                        int cilindrada = ((Double) map.get("cilindrada")).intValue();
                        boolean abs = Boolean.parseBoolean(map.get("tieneFrenosABS").toString());

                        lista.add(new Moto(
                                cilindrada,
                                abs,
                                marca,
                                anio,
                                precio,
                                tipoCombustible
                        ));
                        break;

                    case "Camioneta":
                        int capacidad = ((Double) map.get("capacidadCarga")).intValue();
                        boolean es4x4 = Boolean.parseBoolean(map.get("es4x4").toString());

                        lista.add(new Camioneta(
                                capacidad,
                                es4x4,
                                marca,
                                anio,
                                precio,
                                tipoCombustible
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
