package com.example.projectmanager.util

class LiveDataResult<T>(val data: T?, val error: Throwable?) {
    companion object {
        fun <T> success(data: T): LiveDataResult<T> {
            return LiveDataResult(data, null)
        }

        fun <T> error(error: Throwable): LiveDataResult<T> {
            return LiveDataResult(null, error)
        }
    }
}