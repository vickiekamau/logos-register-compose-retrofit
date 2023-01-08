package com.droidcon.login.composables


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.droidcon.login.R
import com.droidcon.login.ui.theme.PrimaryColor
import com.droidcon.login.ui.theme.SecondaryColor

@Composable
fun LoginPage(navController:NavController) {

    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }


    Box(
    modifier = Modifier
    .fillMaxSize(), contentAlignment = Alignment.BottomCenter
    ) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.TopCenter
    ) {

        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(id = R.drawable.mobile_login),
            contentScale = ContentScale.Crop,
            contentDescription = "Login image"
        )
    }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(SecondaryColor)
                .padding(1.dp)
        ) {
            Text(
                text = "Sign In",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier
                    .padding(vertical = 20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = emailValue.value,
                onValueChange = { emailValue.value = it },
                label = { Text(text = "Enter Email Address") },
                placeholder = { Text(text = "Email Address") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )

            PasswordTextField(text = passwordValue.value, labelText = "Enter Password", onTextChanged = { passwordValue.value = it })

            Button(
                onClick = { inputValidation(emailValue.value,passwordValue.value,navController) },

                colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp)
            ) {
                Text(text = "Sign In", color = Color.White)
            }

            Spacer(modifier = Modifier.padding(top = 5.dp))

            TextButton(
                onClick = { navController.navigate("register_page") },

                ) {
                Text(text = "Create Account", color = PrimaryColor)
            }
        }
    }
}

fun inputValidation(email: String, password: String, navController: NavController) {

    Log.d("email", email)
    Log.d("pass", password)
    if (password != null) {
        navController.navigate("register_page")
    }


}







