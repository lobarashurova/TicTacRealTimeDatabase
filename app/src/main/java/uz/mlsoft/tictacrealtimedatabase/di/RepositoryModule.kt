package uz.mlsoft.tictacrealtimedatabase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.mlsoft.tictacrealtimedatabase.domain.GameRepository
import uz.mlsoft.tictacrealtimedatabase.domain.impl.GameRepositoryImpl
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @[Binds Singleton]
    fun bindGameRepository(impl: GameRepositoryImpl): GameRepository
}