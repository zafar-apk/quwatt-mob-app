package tj.yakroh.yakrohapp

import android.app.Application
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import core.domain.util.DeprecatedStrings
import core.di.initKoin
import org.koin.android.ext.koin.androidContext

class YakrohApp : Application() {

    override fun onCreate() {
        super.onCreate()

        NotifierManager.initialize(
            configuration = NotificationPlatformConfiguration.Android(
                notificationIconResId = R.drawable.ic_launcher_foreground
            )
        )
        DeprecatedStrings.context = this
        initKoin {
            androidContext(this@YakrohApp)
        }
    }
}