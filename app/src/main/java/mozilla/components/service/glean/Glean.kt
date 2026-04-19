package mozilla.components.service.glean
import android.content.Context
import java.util.Calendar

object Glean {
    @Suppress("UNUSED_PARAMETER")
    fun initialize(applicationContext: Context, configuration: Any? = null, uploadEnabled: Boolean = true, buildInfo: Any? = null) {}
    @JvmStatic fun setUploadEnabled(enabled: Boolean) {}
    @JvmStatic fun registerPings(vararg pings: Any?) {}
}

class BuildInfo(val author: String, val version: String, val date: Calendar)
