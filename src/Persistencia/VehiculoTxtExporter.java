
package Persistencia;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import models.Vehiculo;

public class VehiculoTxtExporter {
      public void exportar(List<Vehiculo> lista, String ruta, String titulo) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {

            writer.write("===== " + titulo + " =====");
            writer.newLine();
            writer.write("Fecha: " + LocalDate.now());
            writer.newLine();
            writer.write("Cantidad total: " + lista.size());
            writer.newLine();
            writer.newLine();

            for (Vehiculo v : lista) {
                writer.write(v.toString());
                writer.newLine();
            }

            System.out.println("Archivo TXT exportado correctamente.");

        } catch (IOException e) {
            e.printStackTrace();
        }
      }
}
