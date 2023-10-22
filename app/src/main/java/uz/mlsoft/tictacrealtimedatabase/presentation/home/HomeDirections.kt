package uz.mlsoft.tictacrealtimedatabase.presentation.home

import uz.mlsoft.tictacrealtimedatabase.navigation.AppNavigator
import javax.inject.Inject

interface HomeDirections {
    suspend fun back()
}

class HomeDirectionsImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : HomeDirections {
    override suspend fun back() {
        appNavigator.back()
    }

}