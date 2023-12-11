package ui.services


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import user.domain.UserInteractor

private const val chanel_name = "tj.ham_safar.app.android"
private const val chanel_id = "notification_channel"
private const val request_code = 0
private const val array_of_pattern: Long = 2000

//@AndroidEntryPoint
//class HamSafarPushNotificationService : FirebaseMessagingService() {
//    val coroutineScope = CoroutineScope(Dispatchers.IO)
//
//    @Inject
//    lateinit var userInteractor: UserInteractor
//
//    override fun onMessageReceived(message: RemoteMessage) {
//        message.notification?.let { mes ->
//            Log.e("Notify", "Test-> ${mes.title} \n ${mes.body} \n ${message.data}")
//            generateNotification(mes.title ?: "", mes.body ?: "")
//        }
//    }
//
//    private fun generateNotification(title: String, body: String) {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        val pendingIntent =
//            PendingIntent.getActivity(this, request_code, intent, PendingIntent.FLAG_MUTABLE)
//
//        val builder: NotificationCompat.Builder =
//            NotificationCompat.Builder(applicationContext, chanel_id)
//                .setSmallIcon(R.drawable.ic_yakroh)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setVibrate(
//                    longArrayOf(
//                        array_of_pattern,
//                        array_of_pattern,
//                        array_of_pattern,
//                        array_of_pattern
//                    )
//                )
//                .setOnlyAlertOnce(true)
//                .setContentIntent(pendingIntent)
//
//        val notificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val notificationChannel =
//                NotificationChannel(chanel_id, chanel_name, NotificationManager.IMPORTANCE_HIGH)
//            notificationManager.createNotificationChannel(notificationChannel)
//        }
//        notificationManager.notify(0, builder.build())
//    }
//
//    override fun onNewToken(token: String) {
//        super.onNewToken(token)
//        coroutineScope.launch {
//            userInteractor.saveNotificationToken(token)
//        }
//        Log.e("Hello", "FCM_Token-> \n $token")
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        coroutineScope.cancel()
//    }
//}