package com.pupuseriajenny.ordenes;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl, final String authToken) {
        // Crear un TrustManager que acepte todos los certificados (sin validación)
        TrustManager[] trustAllCertificates = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }
                }
        };

        // Instalar el TrustManager que acepta todos los certificados
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());
        } catch (NoSuchAlgorithmException | java.security.KeyManagementException e) {
            e.printStackTrace();
        }

        // Crear un cliente OkHttp que use el SSLContext que acepta todos los certificados
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCertificates[0]);
        httpClient.hostnameVerifier((hostname, session) -> true); // Aceptar cualquier host

        // Agregar el interceptor para el token de autenticación
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", "Bearer " + authToken)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        // Crear Retrofit con el cliente personalizado
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
