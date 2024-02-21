package tj.quwatt.quwattapp

import App
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mmk.kmpnotifier.permission.permissionUtil
import moe.tlaster.precompose.lifecycle.PreComposeActivity
import moe.tlaster.precompose.lifecycle.setContent

class MainActivity : PreComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissionUtil by permissionUtil()
        //this will ask permission in Android 13(API Level 33) or above, otherwise permission will be granted.
        permissionUtil.askNotificationPermission()
        setContent {
            MainView()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}