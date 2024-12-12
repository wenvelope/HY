
import com.hys.hy.auth.repository.AuthRepository
import com.hys.hy.auth.usecase.UseCase
import com.hys.hy.preference.AppPreference

interface RegisterUseCase : UseCase<RegisterUseCase.Input, RegisterUseCase.Output> {
    data class Input(
        val email: String,
        val password: String
    )

    data class Output(
        val isSuccess: Boolean,
        val errorMessage: String = ""
    )

    suspend fun createOfflineAccount()
}

class RegisterUseCaseImpl(
    private val authRepository: AuthRepository,
    private val appPreference: AppPreference
) : RegisterUseCase {

    override suspend fun createOfflineAccount() {
        appPreference.apply {
            setNotFirstUse()
            setOfflineModeEnabled(true)
            setUserId("test")
        }
    }

    override suspend fun execute(input: RegisterUseCase.Input): RegisterUseCase.Output {
        val result = authRepository.register(input.email, input.password)
        println(result)
        result.fold(
            onSuccess = {
                // save token to preference
                appPreference.setUserTokenValue(it.token)
                // save user id to preference
                appPreference.setUserId(it.userId)
                // set offline mode to false
                appPreference.setOfflineModeEnabled(false)
                // set first use to false
                appPreference.setNotFirstUse()

                return RegisterUseCase.Output(isSuccess = true)
            },
            onFailure = {
                return RegisterUseCase.Output(
                    isSuccess = false, errorMessage = it.message ?: "unknown error"
                )
            }
        )
    }
}