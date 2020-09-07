package com.github.edineipiovesan.chain

import com.github.edineipiovesan.chain.base.ChainBase
import com.github.edineipiovesan.chain.base.Command
import com.github.edineipiovesan.chain.command.*

class ValidationChain(private val context: ValidationContext) :
    ChainBase<Command<ValidationContext>, ValidationContext>() {

    fun execute() {
        addCommand(ListFilesCommand())
        addCommand(MapToValidationProfileCommand())
        addCommand(ExcludeIgnoredProfilesCommand())
        addCommand(ParsePropertiesCommand())
        addCommand(FilterValidationPropertiesCommand())
        addCommand(ApplyDefaultValuesCommand())
        addCommand(ValidatePropertiesCommand())
        super.execute(context)
    }
}
