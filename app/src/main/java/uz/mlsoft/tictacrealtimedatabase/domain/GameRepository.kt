package uz.mlsoft.tictacrealtimedatabase.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import uz.mlsoft.tictacrealtimedatabase.data.common.GameCommonData
import uz.mlsoft.tictacrealtimedatabase.data.common.UserCommonData

interface GameRepository {
    val usersListFlow: StateFlow<List<UserCommonData>>
    val gamesFlow: StateFlow<GameCommonData?>
    val competitionFlow:Flow<String?>
    fun addUsers(name: String): Flow<Result<Unit>>
    fun connectingUsers(pairId: String, pairName: String): Flow<Result<Unit>>
    fun updatingContainer(gameData: GameCommonData, newContainer: String): Flow<Result<Unit>>
    fun deleteMySelf()
}