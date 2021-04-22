package com.goutam.routefinder.roomhelper.repository

sealed class Result<out Long> {
    data class Success(val rowId: Long) : Result<Long>()
    data class Failure(val failure: Long) : Result<Long>()
}
