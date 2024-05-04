package com.example.shopping_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import com.example.shopping_list.ui.theme.Shopping_ListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Shopping_ListTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background)
                {
                    Background()
                      Shopping()
                }
            }
        }
    }
}

@Composable
fun Background()
{
    Image(painter =painterResource(id = R.drawable.list),
        contentDescription = null,
        modifier=Modifier.aspectRatio(1.0f,true).alpha(0.4f))
}



//alert dialog which is supposed to alert or display something it is a composable which pop up on the screen but
//we use it to modify,retrieve store the data



