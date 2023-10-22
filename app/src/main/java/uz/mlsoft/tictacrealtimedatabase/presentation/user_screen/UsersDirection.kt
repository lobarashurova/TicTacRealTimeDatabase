package uz.mlsoft.tictacrealtimedatabase.presentation.user_screen

import uz.mlsoft.tictacrealtimedatabase.navigation.AppNavigator
import uz.mlsoft.tictacrealtimedatabase.presentation.home.HomeScreen
import javax.inject.Inject

interface UsersDirection {
    suspend fun moveToHomeScreen()
}

class UsersDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : UsersDirection {
    override suspend fun moveToHomeScreen() {
        appNavigator.openScreenSaveStack(HomeScreen())
    }

}