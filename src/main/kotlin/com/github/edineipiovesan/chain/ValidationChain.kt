package com.github.edineipiovesan.chain

import com.github.edineipiovesan.chain.base.ChainBase
import com.github.edineipiovesan.chain.base.Command
import com.github.edineipiovesan.chain.command.ApplyDefaultValuesCommand
import com.github.edineipiovesan.chain.command.ExcludeIgnoredProfilesCommand
import com.github.edineipiovesan.chain.command.FilterValidationPropertiesCommand
import com.github.edineipiovesan.chain.command.ListFilesCommand
import com.github.edineipiovesan.chain.command.MapToValidationProfileCommand
import com.github.edineipiovesan.chain.command.ParsePropertiesCommand
import com.github.edineipiovesan.chain.command.ValidatePropertiesCommand

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
