package cz.mg.lib.helpers

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

object ActivityHelper
{

    fun ensureFragmentAttachedToActivity(
            activity: FragmentActivity,
            containerId: Int,
            fragmentFactory: () -> Fragment)
    {
        val fm = activity.supportFragmentManager
        var fragment: Fragment? = fm.findFragmentById(containerId)

        if (fragment == null)
        {
            fragment = fragmentFactory()

            fm.beginTransaction()
                    .add(containerId, fragment)
                    .commit()
        }
    }
}
