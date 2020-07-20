package com.app.crudalumnosession11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText etIdBuscar, etId, etNombre, etApellido;
    Button btnIdBuscar, btnEliminar, btnTodosBuscar, btnAgregar, btnEditar;

    RecyclerView rvAlumnos;
    List<Alumno> listaAlumnos = new ArrayList<>();

    AdaptadorAlumnos adaptadorAlumnos;

    Retrofit retrofit;
    APIRest api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        etIdBuscar = findViewById(R.id.etIdBuscar);
        etId = findViewById(R.id.etId);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        btnIdBuscar = findViewById(R.id.btnIdBuscar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnTodosBuscar = findViewById(R.id.btnTodosBuscar);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnEditar = findViewById(R.id.btnEditar);
        rvAlumnos = findViewById(R.id.rvAlumnos);

        rvAlumnos.setLayoutManager(new GridLayoutManager(this, 1));

        retrofit = new AdaptadorRetrofit().getAdapter();
        api = retrofit.create(APIRest.class);
        getAlumnos(api);

        btnIdBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etIdBuscar.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Inserta un ID Alumno para buscar", Toast.LENGTH_SHORT).show();
                } else {
                    getAlumno(api, etIdBuscar.getText().toString());
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etIdBuscar.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Inserta un ID Alumno para eliminar", Toast.LENGTH_SHORT).show();
                } else {
                    eliminarAlumno(api, etIdBuscar.getText().toString());
                }
            }
        });

        btnTodosBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAlumnos(api);
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etNombre.getText().toString().equals("") || etApellido.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Se deben de llenar los campos", Toast.LENGTH_SHORT).show();
                } else {
                    agregarAlumno(api, etNombre.getText().toString(), etApellido.getText().toString());
                }
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etId.getText().toString().equals("") || etNombre.getText().toString().equals("") || etApellido.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Se deben de llenar los campos", Toast.LENGTH_SHORT).show();
                } else {
                    editarAlumno(api, etId.getText().toString(), etNombre.getText().toString(), etApellido.getText().toString());
                }
            }
        });

    }

    public void getAlumno(final APIRest api, String id) {
        listaAlumnos.clear();
        Call<Alumno> call = api.obtenerAlumno(id);

        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                switch (response.code()) {
                    case 200:
                        listaAlumnos.add(response.body());

                        etIdBuscar.setText("");

                        adaptadorAlumnos = new AdaptadorAlumnos(MainActivity.this, listaAlumnos);
                        rvAlumnos.setAdapter(adaptadorAlumnos);

                        break;
                    case 204:
                        Toast.makeText(MainActivity.this, "No existe ese registro", Toast.LENGTH_SHORT).show();

                        etIdBuscar.setText("");

                        getAlumnos(api);
                        break;

                }
            }

            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {

            }
        });
    }

    public void getAlumnos(APIRest api) {
        listaAlumnos.clear();
        Call<List<Alumno>> call = api.obtenerAlumnos();

        call.enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {
                listaAlumnos = new ArrayList<Alumno>(response.body());

                adaptadorAlumnos = new AdaptadorAlumnos(MainActivity.this, listaAlumnos);
                rvAlumnos.setAdapter(adaptadorAlumnos);

            }

            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {

            }
        });
    }

    public void eliminarAlumno(final APIRest api, String id) {
        listaAlumnos.clear();

        Call<Void> call = api.eliminarAlumno(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 200:
                        Toast.makeText(MainActivity.this, "Se elimino correctamente", Toast.LENGTH_SHORT).show();
                        etIdBuscar.setText("");
                        getAlumnos(api);
                        break;
                    case 204:
                        Toast.makeText(MainActivity.this, "No se elimino el registro", Toast.LENGTH_SHORT).show();
                        etIdBuscar.setText("");
                        break;

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void agregarAlumno(final APIRest api, String nombre, String apellido) {
        listaAlumnos.clear();
        Alumno alumno = new Alumno();
        alumno.setNombre(nombre);
        alumno.setApellido(apellido);

        Call<Void> call = api.agregarAlumno(alumno);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 400:
                        Toast.makeText(MainActivity.this, "Faltaron campos.", Toast.LENGTH_SHORT).show();
                        etNombre.setText("");
                        etApellido.setText("");
                        break;
                    case 200:
                        Toast.makeText(MainActivity.this, "Se inserto correctamente", Toast.LENGTH_SHORT).show();
                        etNombre.setText("");
                        etApellido.setText("");
                        getAlumnos(api);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void editarAlumno(final APIRest api, String id, String nombre, String apellido) {
        listaAlumnos.clear();
        Alumno alumno = new Alumno();
        alumno.setId(id);
        alumno.setNombre(nombre);
        alumno.setApellido(apellido);

        Call<Void> call = api.editarAlumno(alumno);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 400:
                        Toast.makeText(MainActivity.this, "No se puede editar.", Toast.LENGTH_SHORT).show();
                        etId.setText("");
                        etNombre.setText("");
                        etApellido.setText("");
                        break;
                    case 200:
                        Toast.makeText(MainActivity.this, "Se edito correctamente", Toast.LENGTH_SHORT).show();
                        etId.setText("");
                        etNombre.setText("");
                        etApellido.setText("");
                        getAlumnos(api);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

}