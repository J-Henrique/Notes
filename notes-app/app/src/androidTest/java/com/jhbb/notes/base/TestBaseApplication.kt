package com.jhbb.notes.base

import com.jhbb.notes.common.base.BaseApplication
import org.koin.core.module.Module

class TestBaseApplication : BaseApplication() {
    override fun getDIModules() = listOf<Module>()
}