package ipvc.estg.easyparking.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EndPoints {

    @GET("/utilizadores/")
    fun getUsers(): Call<List<User>>

    @GET("/utilizadores/{id}")
    fun getUserById(@Path("id") id: Int): Call<User>

    @GET("/parques/")
    fun getParques(): Call<List<Parque>>

}

