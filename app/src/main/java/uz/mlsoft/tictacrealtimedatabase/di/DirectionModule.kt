package uz.mlsoft.tictacrealtimedatabase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.mlsoft.tictacrealtimedatabase.presentation.home.HomeDirections
import uz.mlsoft.tictacrealtimedatabase.presentation.home.HomeDirectionsImpl
import uz.mlsoft.tictacrealtimedatabase.presentation.registration.RegistrationDirection
import uz.mlsoft.tictacrealtimedatabase.presentation.registration.RegistrationDirectionsImpl
import uz.mlsoft.tictacrealtimedatabase.presentation.user_screen.UsersDirection
import uz.mlsoft.tictacrealtimedatabase.presentation.user_screen.UsersDirectionImpl


@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {

    @[Binds]
    fun bindUsersScreenDirection(impl: UsersDirectionImpl): UsersDirection

    @[Binds]
    fun bindRegistrationDirection(impl: RegistrationDirectionsImpl): RegistrationDirection

    @[Binds]
    fun bindHomeDirection(impl: HomeDirectionsImpl): HomeDirections
}