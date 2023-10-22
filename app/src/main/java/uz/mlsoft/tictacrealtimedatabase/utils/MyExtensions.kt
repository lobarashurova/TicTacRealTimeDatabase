package uz.mlsoft.tictacrealtimedatabase.utils

import timber.log.Timber

fun myTimber(message: String) {
    Timber.tag("TTT").d(message)
}