package com.pupuseriajenny.ordenes.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pupuseriajenny.ordenes.DTOs.LoginModel;
import com.pupuseriajenny.ordenes.DTOs.LoginResponse;
import com.pupuseriajenny.ordenes.R;
import com.pupuseriajenny.ordenes.RetrofitClient;
import com.pupuseriajenny.ordenes.data.service.AuthService;

import io.github.cdimascio.dotenv.Dotenv;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {



    // Obtener la URL base desde el archivo .env


    // Cambia esto a la URL de tu API
    private AuthService authService;
    private EditText edUser, edPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String baseUrl = getString(R.string.base_url);
        // Inicializar componentes de la interfaz
        edUser = findViewById(R.id.edUsuario);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.loginButton);

        // Crear instancia del servicio de autenticación
        authService = RetrofitClient.getClient(baseUrl,null).create(AuthService.class);
        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        String token = prefs.getString("jwt_token", null);
if(token != null){Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
    startActivity(intent);}
     // Configurar el botón de inicio de sesión
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Obtener valores de los campos de texto
                    String username = edUser.getText().toString().trim();
                    String password = edPassword.getText().toString().trim();

                    // Validar entrada de usuario
                    if (username.isEmpty() || password.isEmpty()) {
                        Toast.makeText(LoginActivity.this, "Por favor, ingrese usuario y contraseña", Toast.LENGTH_SHORT).show();
                    } else {
                        loginUser(username, password); // Llamar al método de inicio de sesión
                    }
                } catch (Exception e) {
                    Log.e("LoginError", "Error en el proceso de inicio de sesión: " + e.getMessage());
                    Toast.makeText(LoginActivity.this, "Ocurrió un error. Intente nuevamente.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginUser(String username, String password) {
        try {
            LoginModel loginModel = new LoginModel(username, password);
            Call<LoginResponse> call = authService.login(loginModel);

            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            String token = response.body().getToken();
                            Log.d("AuthToken", "Token recibido: " + token);
                            Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                            // Aquí podrías guardar el token para futuras solicitudes, por ejemplo en SharedPreferences
                            SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("jwt_token", token);
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);

                        } else {
                            // Mostrar mensaje de error si las credenciales son incorrectas
                            Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                            Log.e("LoginError", "Error en la respuesta: " + response.message());
                        }
                    } catch (Exception e) {
                        Log.e("LoginError", "Error al procesar la respuesta: " + e.getMessage());
                        Toast.makeText(LoginActivity.this, "Error al procesar la respuesta. Intente nuevamente.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    try {
                        // Manejo de error de conexión o fallo en la llamada
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("LoginError", "Fallo en la llamada: " + t.getMessage());
                    } catch (Exception e) {
                        Log.e("LoginError", "Error en el manejo de fallºa: " + e.getMessage());
                        Toast.makeText(LoginActivity.this, "Error inesperado. Intente nuevamente.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            Log.e("LoginError", "Error al iniciar sesión: " + e.getMessage());
            Toast.makeText(LoginActivity.this, "Error en la solicitud. Intente nuevamente.", Toast.LENGTH_SHORT).show();
        }
    }
}
