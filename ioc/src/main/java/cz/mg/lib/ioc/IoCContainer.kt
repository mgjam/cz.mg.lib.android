package cz.mg.lib.ioc

import java.util.*
import java.util.concurrent.ConcurrentHashMap

// NICETOHAVE: Configuration verification method

@Suppress("UNCHECKED_CAST")
class IoCContainer : Container, RegistrationDependencyResolver {
    private val singletons = HashSet<String>()
    private val singletonMap = ConcurrentHashMap<String, Any>()
    private val decoratorMap = HashMap<String, String>()
    private val factoryMap = HashMap<String, (container: RegistrationDependencyResolver) -> Any>()

    override fun <T : Any> register(clazz: Class<T>) =
        Registration<T> { scope, decoratorKey, factory ->
            val clazzKey = IoCHelper.getKey(clazz)
            val key = getKey(clazzKey, decoratorKey)

            if (scope == Scope.Singleton)
                singletons.add(key)

            if (decoratorKey != null) {
                if (decoratorMap.containsKey(clazzKey)) {
                    decoratorMap[key] = decoratorMap[clazzKey]!!
                } else {
                    decoratorMap[key] = clazzKey
                }
                decoratorMap[clazzKey] = key
            }

            factoryMap[key] = factory
        }

    override fun <T : Any> resolve(clazz: Class<T>): T {
        val clazzKey = IoCHelper.getKey(clazz)
        val key = if (decoratorMap.containsKey(clazzKey)) decoratorMap[clazzKey]!! else clazzKey

        return resolveInternal(key)
    }

    override fun <T : Any, TDecorator : T> resolveDecoratee(clazz: Class<T>, decoratorClazz: Class<TDecorator>): T {
        val clazzKey = IoCHelper.getKey(clazz)
        val decoratorKey = IoCHelper.getKey(decoratorClazz)
        val key = getKey(clazzKey, decoratorKey)

        return resolveInternal(decoratorMap[key] ?: throw Exception("No decoration registered for key [$key]"))
    }

    private fun <T : Any> resolveInternal(key: String) =
        if (singletons.contains(key))
            singletonMap.getOrPut(key) { invokeFactory<T>(key) } as T
        else invokeFactory(key)

    private fun <T : Any> invokeFactory(key: String): T {
        val factory = factoryMap[key] ?: throw Exception("No factory registered for key [$key]")

        return factory.invoke(this) as T
    }

    private fun getKey(clazzKey: String, decoratorKey: String?) =
        clazzKey + if (decoratorKey != null) "_$decoratorKey" else ""
}