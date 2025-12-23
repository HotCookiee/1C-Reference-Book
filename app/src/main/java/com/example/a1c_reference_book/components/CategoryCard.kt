package com.example.a1c_reference_book.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.a1c_reference_book.database.Category

@Composable
fun CategoryCard(
    category: Category,
    onClick: () -> Unit
) {
    val (gradient, icon) = getCategoryStyle(category.id)

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(brush = gradient)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.9f),
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
        }
    }
}

private fun getCategoryStyle(categoryId: Int): Pair<Brush, ImageVector> {
    return when (categoryId) {
        1 -> Pair(
            Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFF1E88E5),
                    Color(0xFF1565C0)
                )
            ),
            Icons.Default.AccountBalance
        )
        2 -> Pair(
            Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFFE53935),
                    Color(0xFFC62828)
                )
            ),
            Icons.Default.ShoppingCart
        )
        3 -> Pair(
            Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFFFB8C00),
                    Color(0xFFF57C00)
                )
            ),
            Icons.Default.Person
        )
        4 -> Pair(
            Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFF43A047),
                    Color(0xFF2E7D32)
                )
            ),
            Icons.Default.Info
        )
        else -> Pair(
            Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFF757575),
                    Color(0xFF616161)
                )
            ),
            Icons.Default.Star
        )
    }
}
