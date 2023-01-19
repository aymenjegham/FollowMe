package com.angelstudios.core.data.dataSource.helper

interface EmailMatcher {

    fun matches(mail: String): Boolean
}