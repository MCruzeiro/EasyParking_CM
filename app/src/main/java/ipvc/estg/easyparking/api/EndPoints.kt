package ipvc.estg.easyparking.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {

    @GET("utilizadores")
    fun getUsers(): Call<List<User>>

    @GET("utilizadores/{cod_utilizador}")
    fun getUserById(@Path("cod_utilizador") cod_utilizador: String): Call<User>

    @GET("parques")
    fun getParques(): Call<List<Parque>>

    @GET("parques/{id}")
    fun getParqueById(@Path("id") id : Int): Call<Parque>

    @GET("parques/1")
    fun getParque1(): Call<List<Parque>>

    @GET("parques/2")
    fun getParque2(): Call<List<Parque>>

    @GET("parques/3")
    fun getParque3(): Call<List<Parque>>

    @GET("parques/4")
    fun getParque4(): Call<List<Parque>>

    @GET("parques/5")
    fun getParque5(): Call<List<Parque>>

    @GET("parques/6")
    fun getParque6(): Call<List<Parque>>

    @GET("parques/7")
    fun getParque7(): Call<List<Parque>>

    @GET("parques/8")
    fun getParque8(): Call<List<Parque>>

    @FormUrlEncoded
    @POST("utilizador_post")
    fun userRegister(@Field("email") email: String?,@Field("nome") nome: String?,@Field("password") pass: String?): Call<User>


}

