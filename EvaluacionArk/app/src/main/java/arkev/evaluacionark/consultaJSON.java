package arkev.evaluacionark;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class consultaJSON extends AppCompatActivity implements View.OnClickListener{

    Button buttonArtDisponible;
    Button buttonArtReservado;
    Button buttonArtExistencia;
    TextView resultado;
    // IP de mi Url
    String IP = "http://serviciowebarkitem.esy.es/";
    // Rutas de los Web Services
    String GETDISPONIBLE = IP + "/obtenerArtDisponible.php";
    String GETEXISTENCIA = IP + "/obtenerArtExistenciaNeta.php";
    String GETRESERVADO= IP + "/obtenerArtReservado.php";

    ObtenerWebService hiloconexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_json);

        buttonArtDisponible = (Button)findViewById(R.id.boton_artDisponible);
        buttonArtReservado = (Button)findViewById(R.id.boton_artReservado);
        buttonArtExistencia = (Button)findViewById(R.id.boton_artExistencia);
        resultado = (TextView)findViewById(R.id.resultado);

        // Listener de los botones

        buttonArtDisponible.setOnClickListener(this);
        buttonArtReservado.setOnClickListener(this);
        buttonArtExistencia.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.boton_artDisponible:

                hiloconexion = new ObtenerWebService();
                hiloconexion.execute(GETDISPONIBLE,"1"); // Parámetros que recibe doInBackground
                break;
            case R.id.boton_artReservado:

                hiloconexion = new ObtenerWebService();
                hiloconexion.execute(GETEXISTENCIA,"2"); // Parámetros que recibe doInBackground
                break;
            case R.id.boton_artExistencia:

                hiloconexion = new ObtenerWebService();
                hiloconexion.execute(GETRESERVADO,"2"); // Parámetros que recibe doInBackground
                break;
            default:
                break;
        }

    }

    public class ObtenerWebService extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve ="";

            if(params[1]=="1"){
                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                    //connection.setHeader("content-type", "application/json");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK){


                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada

                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON



                        if (resultJSON=="1"){      // hay articulos a mostrar
                            JSONArray alumnosJSON = respuestaJSON.getJSONArray("articlos");   // estado es el nombre del campo en el JSON
                            for(int i=0;i<alumnosJSON.length();i++){
                                devuelve = devuelve + alumnosJSON.getJSONObject(i).getString("Empresa") + " " +
                                        alumnosJSON.getJSONObject(i).getString("Articulo") + " " +
                                        alumnosJSON.getJSONObject(i).getString("Almacen") +
                                        alumnosJSON.getJSONObject(i).getString("Disponible") + "\n";

                            }

                        }
                        else if (resultJSON=="2"){
                            devuelve = "No hay Articulos";
                        }


                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return devuelve;
            }

            else if (params[1]=="2"){
                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                    //connection.setHeader("content-type", "application/json");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK){


                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada

                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON



                        if (resultJSON=="1"){      // hay articulos a mostrar
                            JSONArray alumnosJSON = respuestaJSON.getJSONArray("articlos");   // estado es el nombre del campo en el JSON
                            for(int i=0;i<alumnosJSON.length();i++){
                                devuelve = devuelve + alumnosJSON.getJSONObject(i).getString("Empresa") + " " +
                                        alumnosJSON.getJSONObject(i).getString("Articulo") + " " +
                                        alumnosJSON.getJSONObject(i).getString("Almacen") +
                                        alumnosJSON.getJSONObject(i).getString("Moneda") +
                                        alumnosJSON.getJSONObject(i).getString("Existencia") + "\n";

                            }

                        }
                        else if (resultJSON=="2"){
                            devuelve = "No hay Articulos";
                        }


                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return devuelve;

            }
            else if (params[1]=="3"){

                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                    //connection.setHeader("content-type", "application/json");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK){


                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada

                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON



                        if (resultJSON=="1"){      // articulos a mostrar
                            JSONArray alumnosJSON = respuestaJSON.getJSONArray("articlos");   // estado es el nombre del campo en el JSON
                            for(int i=0;i<alumnosJSON.length();i++){
                                devuelve = devuelve + alumnosJSON.getJSONObject(i).getString("Empresa") + " " +
                                        alumnosJSON.getJSONObject(i).getString("Articulo") + " " +
                                        alumnosJSON.getJSONObject(i).getString("Almacen") +
                                        alumnosJSON.getJSONObject(i).getString("Reservado") + "\n";

                            }

                        }
                        else if (resultJSON=="2"){
                            devuelve = "No hay Articulos";
                        }


                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return devuelve;

            }


            return null;
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {
            resultado.setText(s);
            //super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
