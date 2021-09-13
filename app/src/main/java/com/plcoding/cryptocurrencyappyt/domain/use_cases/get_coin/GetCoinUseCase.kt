package com.plcoding.cryptocurrencyappyt.domain.use_cases.get_coin

import com.plcoding.cryptocurrencyappyt.common.Resource
import com.plcoding.cryptocurrencyappyt.data.remote.dto.toCoinDetails
import com.plcoding.cryptocurrencyappyt.domain.model.CoinDetails
import com.plcoding.cryptocurrencyappyt.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    operator fun invoke(coinId:String): Flow<Resource<CoinDetails>> = flow {
        try {
            emit(Resource.Loading<CoinDetails>())
            val coins = repository.getCoinDetails(coinId = coinId).toCoinDetails()
            emit(Resource.Success<CoinDetails>(coins))
        } catch (e: HttpException) {
            emit(Resource.Error<CoinDetails>(e.localizedMessage ?: "Something went wrong"))
        } catch (io: IOException) {
            emit(Resource.Error<CoinDetails>( "Something went wrong"))
        }
    }
}