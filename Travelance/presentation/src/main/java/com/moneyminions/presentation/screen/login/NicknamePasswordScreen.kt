package com.moneyminions.presentation.screen.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.moneyminions.presentation.common.MinionPrimaryButton
import com.moneyminions.presentation.common.TextFieldWithTitle
import com.moneyminions.presentation.common.TopBar
import com.moneyminions.presentation.navigation.Screen
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.MainViewModel
import com.moneyminions.presentation.viewmodel.login.LoginViewModel
import com.moneyminions.presentation.viewmodel.login.NicknamePasswordViewModel
import kotlinx.coroutines.launch

private const val TAG = "NicknamePasswordScreen D210"
@Composable
fun NicknamePasswordScreen(
    navController: NavHostController,
    nicknamePasswordViewModel: NicknamePasswordViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel
){

//    val backStackEntry = navController.currentBackStackEntryAsState()
//    val current = backStackEntry.value?.destination?.route
    //밑에 current!! 넣어봐라

    val coroutineScope = rememberCoroutineScope()
    val joinResultState by loginViewModel.joinResult.collectAsState()
    NetworkResultHandler(
        state = joinResultState,
        errorAction = {
            Log.d(TAG, "join error...")
        },
        successAction = {
            coroutineScope.launch {
                Log.d(TAG, "join success : $it ")
//                loginViewModel.updateRole(it.role)
                loginViewModel.updateJwtToken(it.accessToken, null, it.role)
                Log.d(TAG, "최종 로그인 결과 : ${loginViewModel.memberInfo}")
//                Log.d(TAG, "preference : ${loginViewModel.getJwtToken()} ${loginViewModel.getRole()}")
                navController.navigate(Screen.Main.route)
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            navController = navController,
            title = "프로필 설정"
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.size(16.dp))
                TextFieldWithTitle(
                    title = "닉네임",
                    hint = "닉네임을 입력해주세요",
                    value = nicknamePasswordViewModel.nickname.value,
                    onValueChange = {nicknamePasswordViewModel.setNickname(it)}
                )
                TextFieldWithTitle(
                    title = "비밀번호",
                    hint = "비밀번호를 입력해주세요",
                    value = nicknamePasswordViewModel.password.value,
                    onValueChange = {nicknamePasswordViewModel.setPassword(it)}
                )
                TextFieldWithTitle(
                    title = "비밀번호 확인",
                    hint = "비밀번호를 입력해주세요",
                    value = "",
                    onValueChange = {}
                )
            }
            MinionPrimaryButton(
                content = "완료",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                //TODO VAILD CHECK 해야 함
                loginViewModel.setNickname(nicknamePasswordViewModel.nickname.value)
                loginViewModel.setPassword(nicknamePasswordViewModel.password.value)
                loginViewModel.join()
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun NicknamePasswordScreenPreview(){
//    NicknamePasswordScreen(navController = rememberNavController())
}