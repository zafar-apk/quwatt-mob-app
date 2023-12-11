package core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

class AndroidDataStore() {
    fun getDataStore(context: Context): DataStore<Preferences> =
        createDataStore(
            producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
        )
}
