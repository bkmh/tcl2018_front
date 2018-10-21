package blockChainTCL.babychain.RestApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;

public class RestApi {

    public String GET(String requestUrl, String... strings) {
        String result;

        String encodedUrl;
        Properties prop = new Properties();

        if(strings.length > 0) {
            for(int i=0; i<strings.length; i+=2) {
                prop.setProperty(strings[i], strings[i+1]);
            }

            encodedUrl = requestUrl + "?" + encodeString(prop);
        } else {
            encodedUrl = requestUrl;
        }

        try {
            URL url = new URL(encodedUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setUseCaches(false);
            httpURLConnection.connect();

            int responseStatusCode = httpURLConnection.getResponseCode();

            InputStream inputStream;
            if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            } else {
                inputStream = httpURLConnection.getErrorStream();
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            httpURLConnection.disconnect();

            result = sb.toString().trim();
        } catch (Exception e) {
            result = e.toString();
        }

        return result;
    }

    public static String encodeString(Properties params) {
        StringBuffer sb = new StringBuffer(256);
        Enumeration names = params.propertyNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = params.getProperty(name);
            sb.append(URLEncoder.encode(name) + "=" + URLEncoder.encode(value) );

            if (names.hasMoreElements()) sb.append("&");
        }
        return sb.toString();
    }

    public String POST(String requestUrl, String... strings) {
        String result;

        Properties prop = new Properties();

        if(strings.length > 0) {
            for (int i = 0; i < strings.length; i += 2) {
                prop.setProperty(strings[i], strings[i + 1]);
            }
        }

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            httpURLConnection.connect();

            OutputStream outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

            outputStreamWriter.write(encodeJSON(prop).toString());
            outputStreamWriter.flush();
            outputStreamWriter.close();

            int responseStatusCode = httpURLConnection.getResponseCode();

            InputStream inputStream;
            if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            } else {
                inputStream = httpURLConnection.getErrorStream();
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();
            httpURLConnection.disconnect();

            result = sb.toString().trim();
        } catch (Exception e) {
            result = e.toString();

        }

        return result;
    }

    public static JSONObject encodeJSON(Properties params) {
        JSONObject json = new JSONObject();
        Enumeration names = params.propertyNames();

        try {
            while (names.hasMoreElements()) {
                String name = (String) names.nextElement();
                String value = params.getProperty(name);

                json.accumulate(URLEncoder.encode(name), URLEncoder.encode(value));

            }
            return json;
        } catch (JSONException e) {
            json = null;
        }

        return json;
    }

}
