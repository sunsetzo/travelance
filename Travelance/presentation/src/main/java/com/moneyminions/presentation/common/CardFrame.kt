package com.moneyminions.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.moneyminions.presentation.R
import com.moneyminions.presentation.utils.Constants

@Composable
fun CardFrame(
    name: String,
    number: String,
    idx: Int,
){
    val imagePainter = painterResource(id = Constants.CARD_FRAME_LIST[idx])

    val constraintSet = ConstraintSet{
        val name = createRefFor("name")
        val number = createRefFor("number")
        val company = createRefFor("company")
        val logo = createRefFor("logo")

        constrain(name){
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
        }
        constrain(number){
            start.linkTo(parent.start, margin = 16.dp)
            bottom.linkTo(parent.bottom, margin = 24.dp)
        }
    }

    Box(
        modifier = Modifier
            .aspectRatio(imagePainter.intrinsicSize.width / imagePainter.intrinsicSize.height)
    ) {
        Card(
            modifier = Modifier
                .background(
                    color = Color.Companion.Transparent,
                    shape = RoundedCornerShape(32.dp)
                )
        ) {
            Image(
                painter = imagePainter,
                contentDescription = null, // 이미지의 설명 (accessibility)을 여기서 설정할 수 있습니다.
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(imagePainter.intrinsicSize.width / imagePainter.intrinsicSize.height),

                contentScale = ContentScale.Crop
            )
        }
        ConstraintLayout(
            constraintSet,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = name,
                style = CustomTextStyle.pretendardSemiBold12,
                color = Color.White,
                modifier = Modifier.layoutId("name")
            )
            Text(
                text = formatNumberWithHyphens(number),
                style = CustomTextStyle.pretendardBold12,
                color = Color.White,
                modifier = Modifier.layoutId("number")
            )
        }
    }
}

fun formatNumberWithHyphens(number: String): String {
    val formattedNumber = StringBuilder()
    for (i in number.indices) {
        if (i > 0 && i % 4 == 0) {
            formattedNumber.append('-')
        }
        formattedNumber.append(number[i])
    }
    return formattedNumber.toString()
}

@Preview(showBackground = true)
@Composable
fun CardFramePreview(){
    CardFrame(name = "카드별칭", number = "1234567812345678", idx = 0)
}