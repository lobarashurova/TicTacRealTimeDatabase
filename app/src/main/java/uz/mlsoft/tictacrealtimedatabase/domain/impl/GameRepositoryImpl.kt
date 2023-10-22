package uz.mlsoft.tictacrealtimedatabase.domain.impl

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import uz.mlsoft.tictacrealtimedatabase.data.common.GameCommonData
import uz.mlsoft.tictacrealtimedatabase.data.common.UserCommonData
import uz.mlsoft.tictacrealtimedatabase.data.model.GameData
import uz.mlsoft.tictacrealtimedatabase.data.model.PlayerData
import uz.mlsoft.tictacrealtimedatabase.domain.GameRepository
import uz.mlsoft.tictacrealtimedatabase.utils.myTimber
import java.util.UUID
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
) : GameRepository {
    private var playerName = ""
    private var mySelf = ""
    private val gameDbRef = Firebase.database.getReference("ticTac")
    override val gamesFlow = MutableStateFlow<GameCommonData?>(null)
    private val userDbRef = Firebase.database.getReference("gamers")
    override val usersListFlow = MutableStateFlow<List<UserCommonData>>(listOf())
    override val competitionFlow = MutableStateFlow<String?>(null)


    init {
        userDbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val playersList = ArrayList<UserCommonData>()
                myTimber("repository init")
                snapshot.children.forEach { mySnapsot ->
                    val id = mySnapsot.key!!
                    val model = mySnapsot.getValue<PlayerData>()

                    if (model != null && model.pair == mySelf) {
                        competitionFlow.tryEmit(model.name)
                    }
                    if (model != null && id != mySelf && model.pair == "0000") {
                        myTimber("list adding")
                        playersList.add(UserCommonData(id, model.name))
                    }

                }
                usersListFlow.tryEmit(playersList)
                myTimber("repos size:${playersList.size}")
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        gameDbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { mySnapshot ->
                    val id = mySnapshot.key!!
                    val model = mySnapshot.getValue<GameData>()

                    if (model != null && (model.firstUserId == mySelf || model.secondUserId == mySelf)) {
                        gamesFlow.tryEmit(
                            GameCommonData(
                                id,
                                model.firstUserId,
                                model.firstUserName,
                                model.secondUserId,
                                model.secondUserName,
                                model.container,
                                model.hasWay && model.firstUserId == mySelf
                            )
                        )
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun addUsers(name: String): Flow<Result<Unit>> = callbackFlow {
        val playerData = PlayerData(name)
        val id = userDbRef.push().key ?: UUID.randomUUID().toString()
        mySelf = id
        playerName = name
        userDbRef.child(id).setValue(playerData)
            .addOnSuccessListener {
                myTimber("user added ")
                trySend(Result.success(Unit))
            }
            .addOnFailureListener {
                myTimber("user adding is failed")
                trySend(Result.failure(it))
            }

        awaitClose()
    }

    override fun connectingUsers(pairId: String, pairName: String): Flow<Result<Unit>> =
        callbackFlow {
            userDbRef.child(mySelf)
                .setValue(PlayerData(playerName, pairId))
                .addOnSuccessListener {
                    userDbRef.child(pairId).setValue(PlayerData(pairName, mySelf))
                        .addOnSuccessListener {
                            val game = GameData(mySelf, playerName, pairId, pairName)
                            val uuid = userDbRef.push().key ?: UUID.randomUUID().toString()
                            gameDbRef.child(uuid).setValue(game)
                                .addOnSuccessListener {
                                    trySend(Result.success(Unit))
                                }
                                .addOnFailureListener {
                                    trySend(Result.failure(it))
                                }
                        }.addOnFailureListener { trySend(Result.failure(it)) }
                }
                .addOnFailureListener {
                    trySend(Result.failure(it))
                }

            awaitClose()
        }

    override fun updatingContainer(
        gameData: GameCommonData,
        newContainer: String
    ): Flow<Result<Unit>> = callbackFlow {
        gameDbRef.child(gameData.gameId)
            .setValue(
                GameData(
                    gameData.firstUserId,
                    gameData.firstUserName,
                    gameData.secondUserId,
                    gameData.secondUserId,
                    newContainer,
                    !gameData.hasWay

                )
            )
            .addOnSuccessListener { trySend(Result.success(Unit)) }
            .addOnFailureListener { trySend(Result.failure(it)) }
        awaitClose()
    }

    override fun deleteMySelf() {
        myTimber("delete repo")
        userDbRef.child(mySelf).removeValue()
            .addOnSuccessListener {
                myTimber("delete success")
            }
            .addOnFailureListener {
                myTimber("delete failure")
            }
    }

}