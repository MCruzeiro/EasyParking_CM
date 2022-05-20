package ipvc.estg.easyparking.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EndPoints {

    @GET("utilizadores")
    fun getUsers(): Call<List<User>>

    @GET("utilizadores/{cod_utilizador}")
    fun getUserById(@Path("cod_utilizador") cod_utilizador: String): Call<User>

    @GET("parques")
    fun getParques(): Call<List<Parque>>

    @GET("parques/{id}")
    fun getParqueById(@Path("id") id: Int): Call<Parque>

}

