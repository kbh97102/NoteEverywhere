package com.arakene.noteeverywhere

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform