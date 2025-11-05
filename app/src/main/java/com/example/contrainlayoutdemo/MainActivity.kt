package com.example.contrainlayoutdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.contrainlayoutdemo.ui.theme.ContrainLayoutDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContrainLayoutDemoTheme {


                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    Ingredients(modifier = modifier)
}



@Composable
fun Ingredients(modifier: Modifier){
    ConstraintLayout() {
        val (txtIngredients, imgArrow) = createRefs()
        Text(text = "Ingredients",
            style = TextStyle(fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                lineHeight = 44.sp),
            color = Color(0xFFFB7D8A),
            modifier = Modifier.constrainAs(txtIngredients){
                start.linkTo(parent.start, margin = 16.dp)
                top.linkTo(parent.top)
            }
        )

        Image(imageVector = Icons.Outlined.KeyboardArrowRight,
            contentDescription = null)


    }
}
@Preview(
    showBackground = true,
    showSystemUi = true, // 👈 Hiển thị khung điện thoại
)
@Composable
fun DefaultPreview(){
    HomeScreen()
}

@Composable
fun Ingredient(@DrawableRes icon: Int,
               value: Int,
               unit: String?,
               name: String,
               modifier: Modifier){
    val backgroundColor = Color(0xFFFEF9E4)
    val borderColor = Color(0xFFFBE897).copy(alpha = 0.75f)
    ConstraintLayout(
        modifier = modifier
            .background(color = backgroundColor, shape = CircleShape)
            .border(BorderStroke(1.dp, borderColor))
    ) {
        val horizontalGuideLine50 = createGuidelineFromTop(0.5f)
        val imgIcon = createRef()
        Image(
            painter = androidx.compose.ui.res.painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.constrainAs(imgIcon){
                top.linkTo(parent.top)
                bottom.linkTo(horizontalGuideLine50)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.fillToConstraints
            },
            contentScale = ContentScale.FillHeight
        )
        val verticalGuideLine50 = createGuidelineFromStart(0.5f)
        val (txtValue, txtUnit, txtName) = createRefs()
        //show value 
        val textColor = Color(0xFFFB7D8A)
        Text(text = value.toString(),
            style = TextStyle(fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                lineHeight = 14.sp),
                color = textColor,
            modifier = Modifier.constrainAs(txtValue){
                top.linkTo(horizontalGuideLine50, margin = 2.dp)
                end.linkTo(verticalGuideLine50, margin = 2.dp)
            }
        )
        //show unit
        if(unit != null){
            Text(text = unit,
                style = TextStyle(fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    lineHeight = 14.sp),
                color = textColor,
                modifier = Modifier.constrainAs(txtUnit){
                    top.linkTo(txtValue.bottom, margin = (-8).dp)
                    end.linkTo(txtValue.end)
                }
            )
        }
        //show name
        val endGuideLine20 = createGuidelineFromEnd(0.2f)
        val bottomBarrier = createBottomBarrier(txtValue, txtUnit)
        Text(text = name,
            style = TextStyle(fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 14.sp),
            color = Color(0xFF1E2742),
            modifier = Modifier.constrainAs(txtName){
                start.linkTo(verticalGuideLine50, margin = 2.dp)
                bottom.linkTo(bottomBarrier)
                end.linkTo(endGuideLine20)
                width = Dimension.fillToConstraints
            },
            maxLines = 2,
            textAlign = TextAlign.Justify
        )
    }
}
@Preview (showBackground = true)
@Composable
fun IngredientPreview(){
    Row{
        Ingredient(icon = R.drawable.ic_lemon,
            value = 30,
            unit = "ml",
            name = "Lemon Juice",
            modifier = Modifier.size(150.dp))
    }

}
