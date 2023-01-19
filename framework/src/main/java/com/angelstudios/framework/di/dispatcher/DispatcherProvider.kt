package com.angelstudios.framework.di.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val main: CoroutineDispatcher

    val mainImmediate : CoroutineDispatcher

    val default: CoroutineDispatcher

    val io: CoroutineDispatcher

    val unconfined: CoroutineDispatcher
}
