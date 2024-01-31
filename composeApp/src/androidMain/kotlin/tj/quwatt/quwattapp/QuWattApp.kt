package tj.quwatt.quwattapp

import android.app.Application
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import core.di.initKoin
import core.domain.util.DeprecatedStrings
import org.koin.android.ext.koin.androidContext

class QuWattApp : Application() {

    override fun onCreate() {
        super.onCreate()

        NotifierManager.initialize(
            configuration = NotificationPlatformConfiguration.Android(
                notificationIconResId = R.drawable.ic_launcher_foreground
            )
        )
        DeprecatedStrings.context = this
        initKoin {
            androidContext(this@QuWattApp)
        }
    }
}