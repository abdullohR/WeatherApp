package com.rasulovabdullokh.weatherapp.core.helpers

data class Resource<out T>(
    val data: T? = null,
    val message: String? = null,
    val code: Int? = null,
    val status: Status? = null,
) {

    enum class Status {
        SUCCESS,
        ERROR,
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(data = data, status = Status.SUCCESS)
        }

        fun <T> error(message: String?, code: Int): Resource<T> {
            return Resource(message = message, status = Status.ERROR, code = code)
        }

    }

}