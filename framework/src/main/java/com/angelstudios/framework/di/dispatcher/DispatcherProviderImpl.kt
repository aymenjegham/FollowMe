package com.angelstudios.framework.di.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {

    override val main: CoroutineDispatcher = Dispatchers.Main

    override val mainImmediate: CoroutineDispatcher = Dispatchers.Main.immediate

    override val default: CoroutineDispatcher = Dispatchers.Default

    override val io: CoroutineDispatcher = Dispatchers.IO

    override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}
