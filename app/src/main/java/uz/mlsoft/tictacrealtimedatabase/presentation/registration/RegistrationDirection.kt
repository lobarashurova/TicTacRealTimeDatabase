package uz.mlsoft.tictacrealtimedatabase.presentation.registration

import uz.mlsoft.tictacrealtimedatabase.navigation.AppNavigator
import uz.mlsoft.tictacrealtimedatabase.presentation.user_screen.UsersScreen
import javax.inject.Inject

interface RegistrationDirection {
    suspend fun moveToUserScreen()
}

class RegistrationDirectionsImpl @Inject constructor(
    private val appNavigator: AppNavigator
) :RegistrationDirection{
    override suspend fun moveToUserScreen() {
        appNavigator.openScreenWithoutSavingToStack(UsersScreen())
    }

}