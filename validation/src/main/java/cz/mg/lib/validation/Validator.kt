package cz.mg.lib.validation

interface Validator
{
    fun isValid(): Boolean

    fun validate(): Boolean
}