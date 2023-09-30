package com.moneyminions.domain.usecase.mypage

import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CardDto
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.repository.mypage.MyPageRepository
import javax.inject.Inject

class DeleteCardUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository
) {
    suspend operator fun invoke(deleteCard: CardDto): NetworkResult<CommonResultDto>{
        return myPageRepository.deleteCard(deleteCard)
    }
}