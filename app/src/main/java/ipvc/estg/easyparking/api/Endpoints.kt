package ipvc.estg.easyparking.api

import ipvc.estg.easyparking.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Endpoints {

    @FormUrlEncoded
    @POST("utilizador_post")
    fun createUser(
        @Field("nome") nome:String,
        @Field("email") email:String,
        @Field("pass") pass:String
    )


    @FormUrlEncoded
    @POST("utilizador_login")
    fun createUser(
        @Field("email") email:String,
        @Field("pass") pass:String
    ):Call<LoginResponse>

}