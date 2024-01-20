package tj.quwatt.quwattapp.core.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import core.data.local.createDataStore
import core.data.local.dataStoreFileName

class AndroidDataStore() {
    fun getDataStore(context: Context): DataStore<Preferences> =
        createDataStore(
            producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
        )
}
