package ru.kre4.chad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ru.kre4.chad.ui.theme.ChadTheme
import ru.kre4.chad.view.ChadApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChadTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ChadApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
