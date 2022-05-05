package com.example.weather_task.base.functional

sealed class Either<out LEFT, out RIGHT> {

    class Left<LEFT>(val value: LEFT) : Either<LEFT, Nothing>()
    class Right<RIGHT>(val value: RIGHT) : Either<Nothing, RIGHT>()

    val isRight: Boolean
        get() = this is Right

    val isLeft: Boolean
        get() = this is Left

    inline fun <TYPE> fold(onError: (LEFT) -> TYPE, onSuccess: (RIGHT) -> TYPE): TYPE =
        when (this) {
            is Left -> onError(this.value)
            is Right -> onSuccess(this.value)
        }
}
