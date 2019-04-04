package cz.mg.lib.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.support.v4.net.ConnectivityManagerCompat

fun Context.getConnectivityManager() = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.isDeviceOnline(): Boolean {
    val networkStatus = getNewtorkStatus()

    return networkStatus != NetworkStatus.Offline
}

fun Context.isBackgroundNetworkingEnabled(): Boolean =
    when (getNewtorkStatus()) {
        NetworkStatus.Offline,
        NetworkStatus.MeteredNetworkBackgroundRestricted ->
            false
        NetworkStatus.NonMeteredNetwork,
        NetworkStatus.MeteredNetwork,
        NetworkStatus.MeteredNetworkBackgroundWhitelisted ->
            true
    }

fun Context.getNewtorkStatus(): NetworkStatus {
    val connectivityManager = getConnectivityManager()
    val activeNetwork = connectivityManager.activeNetworkInfo

    if (activeNetwork == null || !activeNetwork.isAvailable)
        return NetworkStatus.Offline

    if (!ConnectivityManagerCompat.isActiveNetworkMetered(connectivityManager))
        return NetworkStatus.NonMeteredNetwork

    val backgroundRestrictionStatus = ConnectivityManagerCompat.getRestrictBackgroundStatus(connectivityManager)

    return when (backgroundRestrictionStatus) {
        ConnectivityManagerCompat.RESTRICT_BACKGROUND_STATUS_DISABLED ->
            NetworkStatus.MeteredNetwork
        ConnectivityManagerCompat.RESTRICT_BACKGROUND_STATUS_ENABLED ->
            NetworkStatus.MeteredNetworkBackgroundRestricted
        ConnectivityManagerCompat.RESTRICT_BACKGROUND_STATUS_WHITELISTED ->
            NetworkStatus.MeteredNetworkBackgroundWhitelisted
        else ->
            NetworkStatus.MeteredNetworkBackgroundRestricted
    }
}

fun Context.getScreenDensity() = ScreenDensity(resources.displayMetrics.density)