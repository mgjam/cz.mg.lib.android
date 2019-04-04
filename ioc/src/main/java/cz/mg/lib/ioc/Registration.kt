package cz.mg.lib.ioc

class Registration<T : Any>(
    private val commitCallback: (Scope, String?, (RegistrationDependencyResolver) -> T) -> Unit
) {
    private var scope: Scope = Scope.Transient
    private var decoratorKey: String? = null
    private lateinit var factory: (RegistrationDependencyResolver) -> T

    fun usingScope(scope: Scope): Registration<T> {
        this.scope = scope

        return this
    }

    fun <TDecorator : T> asDecorator(clazz: Class<TDecorator>): Registration<T> {
        decoratorKey = IoCHelper.getKey(clazz)

        return this
    }

    fun usingFactory(factory: (RegistrationDependencyResolver) -> T): Registration<T> {
        this.factory = factory

        return this
    }

    fun commit() = commitCallback(scope, decoratorKey, factory)
}