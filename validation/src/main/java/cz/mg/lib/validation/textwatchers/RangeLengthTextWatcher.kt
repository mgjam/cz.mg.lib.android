package cz.mg.lib.validation.textwatchers

import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import cz.mg.lib.validation.Validator

class RangeLengthTextWatcher(
    private val control: EditText,
    private val minLength: Int,
    private val maxLength: Int,
    private val view: TextInputLayout?,
    private val errorMessageTemplate: String?
) : TextWatcher, Validator
{

    constructor(control: EditText, minLength: Int, maxLength: Int) : this(control, minLength, maxLength, null, null)

    override fun afterTextChanged(p0: Editable?) { }
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

    override fun isValid() = isValid(control.text)

    override fun validate() = validate(control.text)

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
    {
        validate(s)
    }

    private fun validate(s: CharSequence?): Boolean
    {
        val isValid = isValid(s)

        if (view != null && errorMessageTemplate != null)
            view.error = if (isValid)
                null
            else errorMessageTemplate
                .replace("\$minLength", minLength.toString())
                .replace("\$maxLength", maxLength.toString())

        return isValid
    }

    private fun isValid(s: CharSequence?) = s != null && s.length >= minLength && s.length <= maxLength
}