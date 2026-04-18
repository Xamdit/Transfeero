package org.mozilla.fenix.datastore
import java.io.InputStream
import java.io.OutputStream

class SelectedPocketStoriesCategories {
    companion object {
        @JvmStatic fun getDefaultInstance() = SelectedPocketStoriesCategories()
        @JvmStatic fun parseFrom(i: InputStream) = SelectedPocketStoriesCategories()
        @JvmStatic fun newBuilderForType() = Builder()
    }
    class Builder {
        fun build() = SelectedPocketStoriesCategories()
        fun addAllValues(l: List<SelectedPocketStoriesCategory>) = this
        fun clearValues() = this
        fun addValues(v: SelectedPocketStoriesCategory) = this
    }
    fun writeTo(o: OutputStream) {}
    fun newBuilderForType() = Builder()
    val valuesList: List<SelectedPocketStoriesCategory> = emptyList()

    class SelectedPocketStoriesCategory(val name: String = "", val selectionTimestamp: Long = 0) {
        companion object {
            @JvmStatic fun newBuilder() = Builder()
        }
        class Builder {
            var name: String = ""
            var selectionTimestamp: Long = 0
            fun build() = SelectedPocketStoriesCategory(name, selectionTimestamp)
        }
    }
}
