package com.fazalulabid.axel_machinetextcompose.presentation.main_activity

import com.fazalulabid.axel_machinetextcompose.domain.model.Account

data class MainState(
    val isSplashLoading: Boolean = true,
    val account: Account? = null,
)
