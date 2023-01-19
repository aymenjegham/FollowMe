package com.angelstudios.framework.global.helper


sealed class Navigation {


    data class Back(val ShouldFinish: Boolean) : Navigation()

}