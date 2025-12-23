package com.example.a1c_reference_book.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.a1c_reference_book.database.Category

fun getIconForCategory(iconName: String): ImageVector {
    return when (iconName) {
        "account_balance" -> Icons.Default.AccountBalance
        "store" -> Icons.Default.ShoppingCart
        "people" -> Icons.Default.Person
        "help_outline" -> Icons.Default.Info
        else -> Icons.Default.Star
    }
}

@Composable
fun CategoryCard(
    category: Category,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = getIconForCategory(category.icon),
                contentDescription = category.name,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = category.name,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start
            )
        }
    }
}
