package cz.mg.lib.ioc

interface Container {
    fun <T : Any> register(clazz: Class<T>): Registration<T>

    fun <T : Any> resolve(clazz: Class<T>): T
}