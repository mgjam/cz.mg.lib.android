package cz.mg.lib.ioc

interface RegistrationDependencyResolver {
    fun <T : Any> resolve(clazz: Class<T>): T

    fun <T : Any, TDecorator : T> resolveDecoratee(clazz: Class<T>, decoratorClazz: Class<TDecorator>): T
}