package com.knowledgespike.stackunderflow

class ConfigurationManager private constructor() {
    var configurationValues: HashMap<String, String>

    operator fun get(key: String): String {
        return configurationValues.getOrDefault(key, "")
    }

    companion object {
        val configurationManager: ConfigurationManager
            get() = ConfigurationManager()
    }

    init {
        configurationValues = java.util.HashMap()
        configurationValues["logDirectory"] = "./logs"
        configurationValues["logBaseName"] = "userlog"
    }
}