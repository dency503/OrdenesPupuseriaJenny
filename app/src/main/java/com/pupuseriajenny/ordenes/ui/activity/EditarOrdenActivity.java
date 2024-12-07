package com.pupuseriajenny.ordenes.ui.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pupuseriajenny.ordenes.ApiService;
import com.pupuseriajenny.ordenes.R;
import com.pupuseriajenny.ordenes.RetrofitClient;
import com.pupuseriajenny.ordenes.data.model.Orden;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarOrdenActivity extends AppCompatActivity {
    private ApiService apiService;
    private EditText edtCliente, edtFecha, edtComentario;
    private Spinner spinnerTipo, spinnerEstado;
    private Button btnGuardar;
    private int idOrden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_orden);

        // Obtener los datos pasados por Intent
        idOrden = getIntent().getIntExtra("idOrden", -1);
        String clienteOrden = getIntent().getStringExtra("clienteOrden");
        String fechaOrden = getIntent().getStringExtra("fechaOrden");
        String tipoOrden = getIntent().getStringExtra("tipoOrden");
        String estadoOrden = getIntent().getStringExtra("estadoOrden");
        String comentarioOrden = getIntent().getStringExtra("comentarioOrden");

        // Asignar los valores a los campos de la UI
        edtCliente = findViewById(R.id.edtCliente);
        edtFecha = findViewById(R.id.edtFecha);
        edtComentario = findViewById(R.id.edtComentario);

        // Spinners para Tipo y Estado de la Orden
        spinnerTipo = findViewById(R.id.spinnerTipoOrden);
        spinnerEstado = findViewById(R.id.spinnerEstadoOrden);

        // Configurar los Spinners con los valores de strings.xml
        ArrayAdapter<CharSequence> adapterTipo = ArrayAdapter.createFromResource(this,
                R.array.tipo_orden, android.R.layout.simple_spinner_item);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapterTipo);

        ArrayAdapter<CharSequence> adapterEstado = ArrayAdapter.createFromResource(this,
                R.array.estado_orden, android.R.layout.simple_spinner_item);
        adapterEstado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapterEstado);

        // Establecer los valores seleccionados en los Spinners
        setSpinnerSelection(spinnerTipo, tipoOrden, R.array.tipo_orden);
        setSpinnerSelection(spinnerEstado, estadoOrden, R.array.estado_orden);

        edtCliente.setText(clienteOrden);
        edtComentario.setText(comentarioOrden);

        // Cargar la fecha inicial en el EditText
        edtFecha.setText(fechaOrden);

        // Configurar el DatePickerDialog al hacer clic en el icono del calendario
        ImageView imgCalendar = findViewById(R.id.imgCalendar);
        imgCalendar.setOnClickListener(v -> showDatePickerDialog());

        // Botón para guardar los cambios
        btnGuardar = findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(v -> {
            // Recoger los datos editados por el usuario
            String nuevoCliente = edtCliente.getText().toString();
            String nuevaFecha = edtFecha.getText().toString();
            String nuevoTipo = spinnerTipo.getSelectedItem().toString();
            String nuevoEstado = spinnerEstado.getSelectedItem().toString();
            String nuevoComentario = edtComentario.getText().toString();

            // Validaciones antes de continuar
            if (validarCampos(nuevoCliente, nuevaFecha, nuevoTipo, nuevoEstado)) {
                // Si todo está correcto, mostrar el AlertDialog de confirmación
                mostrarDialogoConfirmacion(nuevoCliente, nuevaFecha, nuevoTipo, nuevoEstado, nuevoComentario);
            }
        });
    }
    // Método para validar los campos
    private boolean validarCampos(String cliente, String fecha, String tipo, String estado) {
        if (cliente.isEmpty()) {
            edtCliente.setError("El cliente es obligatorio");
            return false;
        }
        if (fecha.isEmpty()) {
            edtFecha.setError("La fecha es obligatoria");
            return false;
        }
        if (tipo.equals("Seleccionar tipo")) {
            Toast.makeText(this, "Selecciona un tipo de orden", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (estado.equals("Seleccionar estado")) {
            Toast.makeText(this, "Selecciona un estado de orden", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Método para mostrar el AlertDialog de confirmación
    private void mostrarDialogoConfirmacion(String cliente, String fecha, String tipo, String estado, String comentario) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar cambios")
                .setMessage("¿Estás seguro de que deseas guardar los cambios?")
                .setPositiveButton("Guardar", (dialog, which) -> {
                    // Si el usuario confirma, se actualiza la orden en la API
                    actualizarOrden(idOrden, cliente, fecha, tipo, estado, comentario);
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    // Si el usuario cancela, no hace nada
                    dialog.dismiss();
                })
                .show();
    }

    // Método para establecer la selección en un Spinner
    private void setSpinnerSelection(Spinner spinner, String value, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinner.getAdapter();
        int position = adapter.getPosition(value); // Obtener la posición de valor en el array
        spinner.setSelection(position); // Establecer la selección
    }

    // Método para mostrar el DatePickerDialog
    // Método para actualizar la orden en la API

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Crear el objeto Calendar con la fecha seleccionada
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(selectedYear, selectedMonth, selectedDay);

                    // Formatear la fecha en el formato requerido: dd/MM/yyyy HH:mm:ss
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                    String fechaSeleccionada = sdf.format(selectedDate.getTime());

                    // Establecer la fecha en el EditText
                    edtFecha.setText(fechaSeleccionada);
                }, year, month, day);

        datePickerDialog.show();
    }

    private void actualizarOrden(int idOrden, String cliente, String fecha, String tipo, String estado, String comentario) {
        // Crear el objeto Orden con los nuevos datos
        Orden ordenActualizada = new Orden();
        ordenActualizada.setIdOrden(idOrden);
        ordenActualizada.setClienteOrden(cliente);
        ordenActualizada.setFechaOrden(fecha);
        ordenActualizada.setTipoOrden(tipo);
        ordenActualizada.setEstadoOrden(estado);
        ordenActualizada.setComentarioOrden(comentario);

        // Llamada a la API para actualizar la orden
        apiService.actualizarEstadoOrden(idOrden, ordenActualizada).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    showToast("Orden actualizada correctamente");
                    finish(); // Volver a la actividad anterior (HistorialActivity)
                } else {
                    showToast("Error al actualizar la orden");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showToast("Error de red: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}





