package Modelo;

import java.io.*; //para guardar en .txt
import java.time.LocalDateTime; //fecha y hora
import java.time.format.DateTimeFormatter; //formato de fecha y hora
import java.util.ArrayList; //lista dinámica
import java.util.List; //interfaz lista 

public class PersistenciaPuntajes {

    private static final String ARCHIVO = "puntajes.txt";
    private static final int MAX_PUNTAJES = 5;
    private static final DateTimeFormatter FORMATO = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Guarda un nuevo puntaje y mantiene solo el top 5
    public static void guardarPuntaje(int puntos) {
        List<String[]> puntajes = cargarPuntajes();

        String fecha = LocalDateTime.now().format(FORMATO);
        puntajes.add(new String[]{String.valueOf(puntos), fecha});

        // Ordenar de mayor a menor
        puntajes.sort((a, b) -> Integer.parseInt(b[0]) - Integer.parseInt(a[0]));

        // Mantener solo los 5 mejores
        if (puntajes.size() > MAX_PUNTAJES) {
            puntajes = puntajes.subList(0, MAX_PUNTAJES);
        }

        // Escribir al archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (String[] entrada : puntajes) {
                writer.write(entrada[0] + "," + entrada[1]);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error guardando puntaje: " + e.getMessage());
        }
    }

    // Carga los puntajes desde el archivo
    public static List<String[]> cargarPuntajes() {
        List<String[]> puntajes = new ArrayList<>();
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) return puntajes;

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    puntajes.add(partes);
                }
            }
        } catch (IOException e) {
            System.err.println("Error cargando puntajes: " + e.getMessage());
        }

        return puntajes;
    }
}
