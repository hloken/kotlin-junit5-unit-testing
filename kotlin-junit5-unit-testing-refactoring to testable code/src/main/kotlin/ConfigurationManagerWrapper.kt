package com.knowledgespike.stackunderflow

interface Configuration {
    operator fun get(key: String) : String
}

class MapConfiguration: Configuration {
    val configurationManager: ConfigurationManager = ConfigurationManager.configurationManager

    override fun get(key: String): String {
        return configurationManager[key]
    }
}