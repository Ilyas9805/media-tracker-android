package edu.metrostate.ics342.mediatracker.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.metrostate.ics342.mediatracker.R
import edu.metrostate.ics342.mediatracker.theme.OnPrimaryContainer
import edu.metrostate.ics342.mediatracker.theme.Primary
import edu.metrostate.ics342.mediatracker.theme.PrimaryContainer

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var displayName     by remember { mutableStateOf("") }
    var username        by remember { mutableStateOf("") }
    var email           by remember { mutableStateOf("") }
    var password        by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage    by remember { mutableStateOf<String?>(null) }

    val focusManager = LocalFocusManager.current

    fun attemptRegister() {
        focusManager.clearFocus()
        when {
            displayName.isBlank() || email.isBlank() || username.isBlank() ||
                    password.isBlank()    || confirmPassword.isBlank() -> {
                errorMessage = "Please fill in all fields."
            }
            password != confirmPassword -> {
                errorMessage = "Passwords do not match."
            }
            else -> onRegisterSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // ── Icon ──────────────────────────────────────────────────────────────
        Image(
            painter            = painterResource(R.drawable.smart_display),
            contentDescription = "Application Icon",
            modifier = Modifier
                .size(64.dp)
                .background(PrimaryContainer, RoundedCornerShape(12.dp))
                .padding(12.dp),
            colorFilter = ColorFilter.tint(Primary)
        )

        Spacer(Modifier.height(16.dp))

        // ── Heading ───────────────────────────────────────────────────────────
        Text(
            text       = "Create Account",
            style      = MaterialTheme.typography.headlineMedium,
            color      = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text  = "Join the community",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(32.dp))

        // ── Fields ────────────────────────────────────────────────────────────
        OutlinedTextField(
            value           = displayName,
            onValueChange   = { displayName = it; errorMessage = null },
            label           = { Text("Display Name") },
            singleLine      = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value           = username,
            onValueChange   = { username = it; errorMessage = null },
            label           = { Text("Username") },
            singleLine      = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value           = email,
            onValueChange   = { email = it; errorMessage = null },
            label           = { Text("Email") },
            singleLine      = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction    = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value                = password,
            onValueChange        = { password = it; errorMessage = null },
            label                = { Text("Password") },
            singleLine           = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction    = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value                = confirmPassword,
            onValueChange        = { confirmPassword = it; errorMessage = null },
            label                = { Text("Confirm Password") },
            singleLine           = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction    = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { attemptRegister() }
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMessage != null) {
            Spacer(Modifier.height(8.dp))
            Text(
                text  = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(24.dp))

        // ── Sign Up button ────────────────────────────────────────────────────
        Button(
            onClick  = { attemptRegister() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Sign Up")
        }

        Spacer(Modifier.height(16.dp))

        // ── Already have an account? ──────────────────────────────────────────
        TextButton(onClick = onNavigateToLogin) {
            Text("Already have an account? Log In")
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun RegisterScreenPreview() {
    RegisterScreen({}, {})
}