package com.barefeet.stampid_compose.utils


object MyApiKey {
    init {
        System.loadLibrary("stampid_compose")
    }

    external fun apiKey(): String
}