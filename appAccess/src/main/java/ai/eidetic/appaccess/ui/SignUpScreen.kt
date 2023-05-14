package ai.eidetic.appaccess.ui

import ai.eidetic.common.R
import ai.eidetic.common.state.appAccessState.AppAccessViewStore
import ai.eidetic.common.ui.components.Button
import ai.eidetic.common.ui.components.HorizontalSpacer
import ai.eidetic.common.ui.components.TextField
import ai.eidetic.common.ui.components.VerticalSpacer
import ai.eidetic.common.ui.theme.ColorPallet
import ai.eidetic.common.ui.theme.body3
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel

@Composable
fun SignUpScreen(
    onLogIn: () -> Unit,
    viewStore: AppAccessViewStore = getViewModel(),
) {
    val state = viewStore()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            VerticalSpacer(value = 32.dp)

            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = R.drawable.ic_recall),
                contentDescription = "icon"
            )

            VerticalSpacer(value = 32.dp)

            Text(
                text = "Welcome to Recall",
                style = MaterialTheme.typography.h4,
            )

            VerticalSpacer(value = 40.dp)

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp),
                text = "Sign up:",
                style = MaterialTheme.typography.h6,
            )

            VerticalSpacer(value = 20.dp)

            TextField(
                onTextChanged = { email = it },
                label = "Email",
                placeholder = "Enter email..."
            )

            VerticalSpacer(value = 12.dp)

            TextField(
                onTextChanged = { password = it },
                label = "Password",
                placeholder = "Enter password...",
                isPassword = true
            )

            VerticalSpacer(value = 60.dp)

            Button(
                modifier = Modifier.fillMaxWidth(),
                text = "Sign Up",
                onClick = {
                    viewStore.logIn(email = email, password = password)
                    onLogIn()
                }
            )

            VerticalSpacer(value = 24.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Divider(
                    modifier = Modifier.weight(1.0f),
                    color = ColorPallet.neutral400
                )

                HorizontalSpacer(value = 6.dp)

                Text(
                    text = "Or use",
                    style = MaterialTheme.typography.body3,
                )

                HorizontalSpacer(value = 6.dp)

                Divider(
                    modifier = Modifier.weight(1.0f),
                    color = ColorPallet.neutral400
                )
            }

            VerticalSpacer(value = 32.dp)

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(color = ColorPallet.neutral40, CircleShape)
                        .clip(CircleShape)
                ) {
                    Image(
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "google icon"
                    )
                }

                HorizontalSpacer(value = 16.dp)

                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(color = ColorPallet.neutral40, CircleShape)
                        .clip(CircleShape)
                ) {
                    Image(
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_facebook),
                        contentDescription = "facebook icon"
                    )
                }

                HorizontalSpacer(value = 16.dp)

                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(color = ColorPallet.neutral40, CircleShape)
                        .clip(CircleShape)
                ) {
                    Image(
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.Center),
                        painter = painterResource(id = R.drawable.ic_discord),
                        contentDescription = "discord icon"
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
        ) {
            Image(
                modifier = Modifier.width(6000.dp),
                painter = painterResource(id = R.drawable.ic_signup_decor),
                contentDescription = "Decoration",
                contentScale = ContentScale.Crop
            )
        }
    }
}