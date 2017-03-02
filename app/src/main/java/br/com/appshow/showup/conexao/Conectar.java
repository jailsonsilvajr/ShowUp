package br.com.appshow.showup.conexao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jailson on 09/02/17.
 */

public class Conectar {

    public static String url_servidor = "https://showupbr.com/appdroid/showup/php/";

    public static String postDados(String urlUsuario, String parametrosUsuario){

        URL url;
        HttpURLConnection connection = null;

        try{

            url = new URL(urlUsuario);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf8");
            connection.setRequestProperty("Content-Lenght", "" + Integer.toString(parametrosUsuario.getBytes().length));
            connection.setRequestProperty("Content-Languege", "pt-BR");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.writeBytes(parametrosUsuario);
            dataOutputStream.flush();
            dataOutputStream.close();

            InputStream inputStream = connection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String linha;
            StringBuffer resposta = new StringBuffer();

            while((linha = bufferedReader.readLine()) != null){

                resposta.append(linha);
                resposta.append('\r');
            }

            bufferedReader.close();
            return resposta.toString();
        }catch(Exception e){

            return "conexao_erro";
        }finally {

            if(connection != null) connection.disconnect();
        }
    }
}
