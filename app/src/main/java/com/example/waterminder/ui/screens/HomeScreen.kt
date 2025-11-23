package com.example.waterminder.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.waterminder.db.dao.UserDAO
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    userDao: UserDAO
) {

    val scope = rememberCoroutineScope()
    var showLogoutDialog by remember { mutableStateOf(false) }

    var progress by remember { mutableStateOf(0.45f) }
    val goal = 2000
    val intake = (progress * goal).toInt()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFBBDEFB), Color.White)
                )
            ),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            // ---------------- TOP ROW ----------------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "💧 WaterMinder",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0277BD)
                )

                // --- Logout Icon Button (TV Remote Style) ---
                IconButton(
                    onClick = { showLogoutDialog = true },
                ) {
                    Icon(
                        imageVector = Icons.Default.PowerSettingsNew,
                        contentDescription = "Logout",
                        tint = Color(0xFFE57379),
                        modifier = Modifier.size(25.dp)
                    )
                }

            }
            // ------------------------------------------

            Text(
                text = "Stay hydrated, stay healthy!",
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ---------------- PROGRESS CIRCLE ----------------
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = progress,
                    strokeWidth = 12.dp,
                    color = Color(0xFF0288D1),
                    trackColor = Color(0xFFE1F5FE),
                    modifier = Modifier.size(180.dp)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$intake ml",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0277BD)
                    )
                    Text(
                        text = "of $goal ml",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    if (progress < 1f) progress += 0.1f
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0277BD)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Add 200ml", color = Color.White, fontSize = 18.sp)
            }

            TextButton(onClick = { progress = 0f }) {
                Text("Reset", color = Color(0xFF0277BD))
            }

            Spacer(modifier = Modifier.height(40.dp))

            Surface(
                color = Color(0xFFE3F2FD),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Tip of the day 💡",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0277BD)
                    )
                    Text(
                        "Drink a glass of water in the morning!",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }

    // ----------------- LOGOUT CONFIRMATION DIALOG -----------------
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },

            title = { Text("Confirm Logout!") },
            text = { Text("Are you sure you want to log out?") },

            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    scope.launch {
                        userDao.clearUser()
                        navController.navigate("login") {
                            popUpTo(0)
                        }
                    }
                }) {
                    Text("Logout", color = Color.Red)
                }
            },

            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel", color = Color(0xFF0277BD))
                }
            }
        )
    }
}
