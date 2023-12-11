package tj.yakroh.yakrohapp

import android.app.Application
import core.domain.util.DeprecatedStrings
import core.di.initKoin
import org.koin.android.ext.koin.androidContext

class YakrohApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DeprecatedStrings.context = this
        initKoin {
            androidContext(this@YakrohApp)
        }
    }
}