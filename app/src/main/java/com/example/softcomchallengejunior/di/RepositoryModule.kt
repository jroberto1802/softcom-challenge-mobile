package com.example.softcomchallengejunior.di

import com.example.softcomchallengejunior.data.repository.CartRepositoryImpl
import com.example.softcomchallengejunior.data.repository.ProductRepositoryImpl
import com.example.softcomchallengejunior.domain.repository.CartRepository
import com.example.softcomchallengejunior.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCartRepository(
        cartRepositoryImpl: CartRepositoryImpl
    ): CartRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository
    // Se você tiver o ProductRepository, ele também deve estar aqui:
    // @Binds
    // @Singleton
    // abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository
}