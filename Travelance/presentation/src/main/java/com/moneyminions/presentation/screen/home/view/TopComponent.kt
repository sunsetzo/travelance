package com.moneyminions.presentation.screen.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneyminions.presentation.R
import com.moneyminions.presentation.common.text.CustomTextStyle
import com.moneyminions.presentation.theme.CardLightGray

@Composable
fun TopComponent() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(CardLightGray),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "여행 이름",
                style = CustomTextStyle.regularTitleTextStyle,
            )
            
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = { }, // 상세 정보 페이지로 이동
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_right_arrow),
                    contentDescription = "move detail",
                )
            }
        }
    }
}