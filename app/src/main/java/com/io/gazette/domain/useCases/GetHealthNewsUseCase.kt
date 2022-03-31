package com.io.gazette.domain.useCases

import com.io.gazette.Failure
import com.io.gazette.Loading
import com.io.gazette.RequestResult
import com.io.gazette.Success
import com.io.gazette.data.api.mappers.toDomainNewsItem
import com.io.gazette.data.repositories.NytRepository
import com.io.gazette.domain.models.NewsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GetHealthNewsUseCase(private val nytRepository: NytRepository) {
    operator fun invoke(): Flow<RequestResult<List<NewsItem>>> = flow {
        try {
            emit(Loading)
            val newsHealthList =
                nytRepository.getHealthNews().results?.map { it.toDomainNewsItem() }
            emit(Success(newsHealthList.orEmpty()))
        } catch (error: HttpException) {
            emit(Failure(errorMessage = error.message() ?: "An Unexpected Error Occurred"))

        } catch (error: IOException) {
            emit(
                Failure(
                    errorMessage = error.localizedMessage
                        ?: "An Unexpected Error Occurred, could not reach server"
                )
            )
        }
    }
}