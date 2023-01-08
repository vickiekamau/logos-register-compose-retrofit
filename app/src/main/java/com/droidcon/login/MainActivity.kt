
package com.droidcon.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droidcon.login.composables.LoginPage
import com.droidcon.login.composables.RegisterPage
import com.droidcon.login.ui.theme.LoginTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginTheme {

                LoginApplication()

            }
        }
    }

    @Composable
    fun LoginApplication() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "login_page"){
            composable("login_page"){ LoginPage(navController = navController)}
            composable("register_page"){ RegisterPage(navController = navController)}

        }

    }
}



