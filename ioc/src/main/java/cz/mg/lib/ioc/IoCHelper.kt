package cz.mg.lib.ioc

internal object IoCHelper {
    fun <T : Any> getKey(clazz: Class<T>) = clazz.`package`?.name + "_" + clazz.simpleName
}