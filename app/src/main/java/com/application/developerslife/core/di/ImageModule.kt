package com.application.developerslife.core.di

import com.application.developerslife.common.ImageLoaderImpl
import org.koin.dsl.module

/**
 * @autor d.snytko
 */
val imageModule = module {
    factory { ImageLoaderImpl() }
}
