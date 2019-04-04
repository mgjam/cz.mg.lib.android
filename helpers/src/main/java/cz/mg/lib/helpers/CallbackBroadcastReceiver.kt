package cz.mg.lib.helpers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CallbackBroadcastReceiver(
    private val onReceiveCallback: (Intent) -> Unit
) : BroadcastReceiver()
{
    override fun onReceive(context: Context?, intent: Intent?) =
        if (intent != null) onReceiveCallback(intent)
        else {}
}