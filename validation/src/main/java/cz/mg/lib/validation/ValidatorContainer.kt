package cz.mg.lib.validation

class ValidatorContainer(
    private val validators: Iterable<Validator>
)
{
    fun isValid() = validators.all { it.isValid() }

    fun validate() = validators
        .map { it.validate() }
        .all({ it })
}