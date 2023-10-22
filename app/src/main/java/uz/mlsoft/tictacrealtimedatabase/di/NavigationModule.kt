package uz.mlsoft.tictacrealtimedatabase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.mlsoft.tictacrealtimedatabase.navigation.AppNavigator
import uz.mlsoft.tictacrealtimedatabase.navigation.NavigationDispatcher
import uz.mlsoft.tictacrealtimedatabase.navigation.NavigationHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @[Binds Singleton]
    fun bindNavigationHandler(impl: NavigationDispatcher): NavigationHandler


    @[Binds Singleton]
    fun bindAppNavigator(impl: NavigationDispatcher): AppNavigator

}