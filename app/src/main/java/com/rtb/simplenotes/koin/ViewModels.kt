package com.rtb.simplenotes.koin

import com.rtb.simplenotes.baseclasses.BaseViewModel
import com.rtb.simplenotes.ui.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { BaseViewModel() }
    viewModel { MainViewModel(get()) }
}