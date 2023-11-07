package com.vasquez.kotlinapp.storage.remote

import com.vasquez.kotlinapp.storage.ListResult
import com.vasquez.kotlinapp.storage.EmployeeDataSource
import com.vasquez.kotlinapp.storage.ObjectResult

/**
 * @author Vasquez Reyna J
 */
class RemoteEmployeeDataSource(private val noteApiClient: NoteApiClient) :
    EmployeeDataSource {

    private val servicesApi by lazy {
        noteApiClient.build()
    }

    override suspend fun employees(): ListResult<EmployeeDTO> {
        return try {
            val response = servicesApi?.employess()
            if (response?.isSuccessful == true) {
                response.body()?.data?.let {
                    ListResult.Success(it)
                } ?: run {
                    ListResult.Failure(Exception("Ocurrió un error"))
                }
            } else {
                ListResult.Failure(Exception("Ocurrió un error"))
            }
        }catch (exception:Exception){
            ListResult.Failure(exception)
        }
    }

    override suspend fun saveEmployee(employeeRaw: EmployeeRaw): ObjectResult<ResponseOfConsult> {
        return try {
            val response = servicesApi?.saveEmployee(employeeRaw)
            if (response?.isSuccessful == true) {
                response.body()?.let {
                    when(it?.msg) {
                        "Emplead@ " + employeeRaw.firstname + " ingresado con éxito" -> ObjectResult.Success(it);
                        "El dni " + employeeRaw.dni +" ya existe" -> ObjectResult.Failure(Exception("El Dni ingresado ya existe"))
                        else -> ObjectResult.Failure(Exception("Ocurrió un error"))
                    }
                } ?: run {
                    ObjectResult.Failure(Exception("Ocurrió un error"))
                }
            } else {
                ObjectResult.Failure(Exception("Ocurrió un error"))
            }
        } catch (exception:Exception){
            ObjectResult.Failure(exception)
        }
    }
    /*
    override suspend fun notesByUser(
        userId: String
    ):ListResult<NoteDTO> {
        return try {
            val response = servicesApi?.notesByUser(userId)
            if (response?.isSuccessful == true) {
                response.body()?.data?.let {
                    ListResult.Success(it)
                } ?: run {
                    ListResult.Failure(Exception("Ocurrió un error"))
                }
            } else {
                ListResult.Failure(Exception("Ocurrió un error"))
            }
        }catch (exception:Exception){
            ListResult.Failure(exception)
        }
    }*/




}