package com.moneyminions.data.datasource.remote.mypage

import com.moneyminions.data.model.login.response.MemberInfoResponse
import com.moneyminions.data.service.BusinessService

class MyPageDataSourceImpl(
    private val businessService: BusinessService
): MyPageDataSource {
    override suspend fun getMemberInfo(): MemberInfoResponse {
        return businessService.getMemberInfo()
    }
}