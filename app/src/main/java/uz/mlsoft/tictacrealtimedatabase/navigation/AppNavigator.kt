package uz.mlsoft.tictacrealtimedatabase.navigation

import cafe.adriel.voyager.androidx.AndroidScreen


typealias MyScreen = AndroidScreen

interface AppNavigator {
    suspend fun openScreenSaveStack(screen: MyScreen)
    suspend fun openScreenWithoutSavingToStack(screen: MyScreen)
    suspend fun back()
    suspend fun backUntil(screen: MyScreen)
    suspend fun backToRoot()
}