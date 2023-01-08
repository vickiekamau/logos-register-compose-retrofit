package com.droidcon.login.composables

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.droidcon.login.R
import com.droidcon.login.model.User
import com.droidcon.login.response.Status
import com.droidcon.login.ui.theme.PrimaryColor
import com.droidcon.login.ui.theme.SecondaryColor
import com.droidcon.login.utils.SweetAlerts
import com.droidcon.login.utils.SweetAlerts.sweetAlertDialog

@Composable
fun RegisterPage(navController: NavController,
                 registerViewModel: RegisterViewModel = hiltViewModel()){
    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val confirmPasswordValue = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }
    val confirmPasswordVisibility = remember { mutableStateOf(false) }
    val isError = remember { mutableStateOf(false) }
    val context = LocalContext.current



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
                text = "Create Account",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier
                    .padding(vertical = 20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = emailValue.value,
                onValueChange = { emailValue.value = it  },
                label = { Text(text = "Enter Email Address") },
                placeholder = { Text(text = "Email Address") },
                singleLine = true,
                isError = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                )
            if (isError.value){
                Text(
                    text = "Email Address cannot be Empty",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            PasswordTextField(text = passwordValue.value, labelText = "Enter Password", onTextChanged = { passwordValue.value = it })

            ConfirmPasswordTextField(text = passwordValue.value, confirmText = confirmPasswordValue.value, labelText = "Confirm Password", onTextChanged = {confirmPasswordValue.value = it })

            Button(
                onClick = { inputValidation(emailValue.value,passwordValue.value,navController,registerViewModel,context) },

                colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                enabled = passwordValue.value == confirmPasswordValue.value
            ) {
                Text(text = "Sign Up", color = Color.White)

            }

            Spacer(modifier = Modifier.padding(top = 5.dp))

            TextButton(
                onClick = { navController.navigate("login_page") },

                ) {
                Text(text = "Create Account", color = PrimaryColor)
            }
        }
    }
}




fun inputValidation(email: String, password: String, navController: NavController, registerViewModel: RegisterViewModel,context: Context) {


    val user = User(email,password)

    //viewModel.saveMember(saveMember)
    registerViewModel.saveUserData(user)


    //registerViewModel.savedUserStatus.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
    registerViewModel.savedUserStatus.observeForever{
        when (it.status) {
            Status.SUCCESS -> {
                success(
                    context,
                    "Success", "Member ${it.data} Saved Successfully",
                    dismiss = {
                        navController.navigate("login_page")

                    })


            }

            Status.LOADING -> {
                loading(context, "Loading")
                //inputValidation()
            }
            Status.ERROR -> {

                error(context, "Ooops", it.message.toString(),
                    dismiss = { sweetAlertDialog?.dismiss() }
                )
            }

            else -> {

            }
        }
    }
}

private fun success(context: Context, title: String, msg: String, dismiss: (() -> Unit)) {
    SweetAlerts.success(
        context = context,
        title = title,
        msg = msg,
        dismiss = dismiss
    )
}

private fun error(context: Context, title: String, msg: String, dismiss: (() -> Unit)) {
    SweetAlerts.error(
        context = context,
        title = title,
        msg = msg,
        dismiss = dismiss
    )
}

private fun loading(context: Context, msg: String) {

    SweetAlerts.loading(
        context = context,
        msg = msg
    )
}