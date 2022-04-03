package com.example.composesfo.view

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composesfo.R
import com.example.composesfo.ui.theme.AllButton

@Composable
fun HomeScreen(
    navController: NavController
) {
    val sectionState = remember { mutableStateOf(BottomBarSection.Home) }
    val navItems = BottomBarSection.values().toList()

    Scaffold(
        bottomBar =
        {
            BottomBar(
                items = navItems,
                currentSection = sectionState.value,
                onSectionSelected = { sectionState.value = it }
            )
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        Crossfade(
            modifier = modifier,
            targetState = sectionState.value
        ) { section ->
            when (section) {
                BottomBarSection.Home -> MenuScreen()
                BottomBarSection.Cart -> CartScreen()
                BottomBarSection.Wallet -> WalletScreen()
                BottomBarSection.Profile -> ProfileScreen(navController)
            }
        }
    }
}

@Composable
private fun BottomBar(
    items: List<BottomBarSection>,
    currentSection: BottomBarSection,
    onSectionSelected: (BottomBarSection) -> Unit
) {
    BottomNavigation(
        modifier = Modifier.height(50.dp),
        backgroundColor = MaterialTheme.colors.background,
        contentColor = contentColorFor(MaterialTheme.colors.background)
    ) {
        items.forEach { section ->
            val selected = section == currentSection
            val iconRes = if (selected) section.selectionIcon else section.icon

            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = iconRes),
                        modifier = Modifier.size(24.dp),
                        contentDescription = "Bottom nav icons"
                    )
                },
                selected = selected,
                unselectedContentColor = Color.Gray,
                selectedContentColor = AllButton,
                onClick = { onSectionSelected(section) },
                alwaysShowLabel = false
            )
        }
    }
}

private enum class BottomBarSection(
    val icon: Int,
    val selectionIcon: Int,
) {
    Home(R.drawable.ic_menu_book, R.drawable.ic_menu_book),
    Cart(R.drawable.ic_shopping_cart, R.drawable.ic_shopping_cart),
    Wallet(R.drawable.ic_wallet, R.drawable.ic_wallet),
    Profile(R.drawable.ic_profile, R.drawable.ic_profile)
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}