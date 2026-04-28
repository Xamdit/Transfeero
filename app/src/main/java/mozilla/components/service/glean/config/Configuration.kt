package mozilla.components.service.glean.config

class Configuration(val channel: String, val httpClient: Any? = null) {
    fun setCustomEndpointIfAvailable(url: String?): Configuration = this
}
