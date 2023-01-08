package com.droidcon.login.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.login.R
import com.droidcon.login.ui.theme.PrimaryColor
import com.droidcon.login.ui.theme.SecondaryColor
import com.droidcon.login.ui.theme.SoftRed

@Composable
fun ConfirmPasswordTextField(
    text:String,
    confirmText: String,
    modifier: Modifier = Modifier,
    semanticContentDescription: String = "",
    labelText: String,
    hasError: Boolean = false,
    onTextChanged: (text:String) -> Unit,

    ){
    val focusManager = LocalFocusManager.current
    val showPassword = remember { mutableStateOf(false) }
    val matchError = remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth()
    ){
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .semantics { contentDescription = semanticContentDescription },
            value = confirmText,
            onValueChange = onTextChanged,
            placeholder = {
                Text(
                    text = labelText,
                    color = Color.Gray,
                    fontSize = 16.sp

                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            singleLine = true,
            isError = hasError,
            visualTransformation = if(showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { showPassword.value = !showPassword.value }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_password),
                        contentDescription = "password visibility",
                        tint = if (showPassword.value) Color.Black else Color.Gray

                    )
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = PrimaryColor,
                textColor = Color.Black,
                cursorColor = PrimaryColor
            )

        )

        Spacer(modifier = Modifier.height(8.dp))

        if(confirmText != text){
            Text(
                text = stringResource(id = R.string.error_password_match),
                color = SoftRed,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .semantics { contentDescription = "ConfirmPasswordMessage"}
                        .padding(horizontal = 10.dp)
            )
            matchError.value = true
        }
        else{
            matchError.value = false
        }
    }

}