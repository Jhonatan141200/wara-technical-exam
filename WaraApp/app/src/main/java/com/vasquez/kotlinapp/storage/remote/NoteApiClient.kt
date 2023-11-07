package com.vasquez.kotlinapp.storage.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * @author Vasquez Reyna J
 */
object NoteApiClient {

    private const val URL = "http://10.0.2.2"
    private var servicesApi: ServicesApi? = null

    fun build(): ServicesApi? {
        val builder:Retrofit.Builder = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())

        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor(interceptor())
        }


        val retrofit = builder
            .client(httpClient.build())
            .build()
        servicesApi = retrofit.create(ServicesApi::class.java)
        return servicesApi
    }

    fun instance() = this

    private fun interceptor():HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    interface ServicesApi {

        //@POST("/api/login")
        //fun login(@Body raw: LogInRaw): Call<LogInResponse>

        @POST("/api/user/login")
        suspend fun login(@Body raw: LogInRaw): Response<LogInResponse>

        //notes

        //@GET("/api/employee/all")
        //fun notes(): Call<NotesResponse>

        @GET("/api/employee/all")
        suspend fun employess(): Response<EmployeeResponse>



        @POST("/api/employee/save")
        fun saveEmployee(@Body raw: EmployeeRaw): Response<ResponseOfConsult>

        //@PUT("/api/notes/{id}")
        //fun updateNote(@Path("id") noteId: String?, @Body raw: Any): Call<Any>

        //@DELETE("/api/notes/{id}")
        //fun deleteNote(@Path("id") noteId: String?): Call<Any>

    }
}