package com.app.crudalumnosession11;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface APIRest {
    String URL_PHP="API.php";

    @GET(URL_PHP)
    Call<List<Alumno>> obtenerAlumnos();

    @GET(URL_PHP)
    Call<Alumno> obtenerAlumno(
            @Query("id") String id
    );

    @POST(URL_PHP)
    Call<Void> agregarAlumno(
            @Body Alumno alumno
    );

    @PUT(URL_PHP)
    Call<Void> editarAlumno(
            @Body Alumno alumno
    );

    @DELETE(URL_PHP)
    Call<Void> eliminarAlumno(
            @Query("id") String id
    );


}
