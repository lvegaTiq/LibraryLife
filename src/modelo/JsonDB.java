package modelo;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonDB {

    // Método para leer un archivo JSON
    public static JSONArray leer(String ruta) {
        try {
            File file = new File(ruta);
            if (!file.exists()) {
                file.createNewFile();
                escribir(ruta, new JSONArray()); // Crear JSON vacío
            }

            FileReader reader = new FileReader(file);
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            reader.close();
            return (JSONArray) obj;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    // Método para escribir en el archivo JSON
    public static void escribir(String ruta, JSONArray data) {
        try (FileWriter file = new FileWriter(ruta)) {
            file.write(data.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
