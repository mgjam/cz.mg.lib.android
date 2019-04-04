package cz.mg.lib.httpclient

import android.app.Activity
import android.app.Application
import android.os.Bundle
import cz.mg.lib.logging.Logger

internal class ActivityLifecycleForegroundCheckHelper(
    private val logger: Logger
) : Application.ActivityLifecycleCallbacks {
    companion object {
        private const val TAG = "ActivityLifecycleForegroundCheckHelper"
    }

    private var numStarted = 0

    override fun onActivityPaused(activity: Activity?) {}
    override fun onActivityResumed(activity: Activity?) {}
    override fun onActivityDestroyed(activity: Activity?) {}
    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity?) {
        numStarted++
        logger.d(TAG, "Activity [$activity] started. Total count: [$numStarted]")
    }

    override fun onActivityStopped(activity: Activity?) {
        numStarted--

        val status = if (isAppInForeground()) "foreground" else "background"
        logger.d(TAG, "Activity [$activity] stopped. Total count: [$numStarted]. App is in [$status]")
    }

    fun isAppInForeground() = numStarted > 0
}