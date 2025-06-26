package com.raulastete.birthdaycelebrationsminichallenge.party_host_dashboard_fourth

import android.annotation.SuppressLint
import android.os.Build
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.savedstate.SavedState
import com.raulastete.birthdaycelebrationsminichallenge.DeviceMode
import com.raulastete.birthdaycelebrationsminichallenge.R
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.BackgroundColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.BoulderGreyColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.ElectricBlueColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.ForestGreenColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.OnSurfaceColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.SurfaceColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.SurfaceHigherColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.maliFontFamily
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.nunitoFontFamily
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.jvm.java
import kotlin.reflect.typeOf


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun PartyHostDashboardScreen(deviceMode: DeviceMode) {

    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.GUEST_LIST) }

    val customNavSuiteType = when (deviceMode) {
        DeviceMode.PhonePortrait -> NavigationSuiteType.NavigationBar
        else -> NavigationSuiteType.NavigationDrawer
    }

    val myNavigationSuiteItemColors = NavigationSuiteDefaults.itemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            indicatorColor = SurfaceHigherColor
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = SurfaceHigherColor,
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            indicatorColor = SurfaceHigherColor
        )
    )

    NavigationSuiteScaffold(
        containerColor = SurfaceColor,
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            ImageVector.vectorResource(it.icon),
                            contentDescription = it.contentDescription
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it },
                    colors = myNavigationSuiteItemColors
                )
            }
        },
        layoutType = customNavSuiteType,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = White,
            navigationDrawerContainerColor = White,
            navigationRailContainerColor = White
        )
    ) {
        when (currentDestination) {
            AppDestinations.GUEST_LIST -> GuestListDestination(deviceMode = deviceMode)
            AppDestinations.PARTY_TIMELINE -> PartyTimelineDestination(deviceMode = deviceMode)
            AppDestinations.GIFTS -> GiftsDestination()
        }
    }
}

/***
 *
 * GUEST LIST SECTION
 */

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GuestListDestination(
    deviceMode: DeviceMode
) {
    val guests = remember { guestList }
    val guestSelected = rememberSaveable {
        mutableStateOf<GuestItem?>(null)
    }

    when (deviceMode) {
        DeviceMode.TabletLandscape -> ShowGuestListDetail(
            guests,
            guestSelected = guestSelected.value,
            onGuestSelected = { guestSelected.value = it }
        )

        else -> ShowGuestListGraph(
            guests,
            guestSelected = guestSelected.value,
            onGuestSelected = { guestSelected.value = it }
        )
    }
}

@Composable
fun ShowGuestListGraph(
    guests: List<GuestItem>,
    guestSelected: GuestItem?,
    onGuestSelected: (GuestItem) -> Unit
) {
    val navController = rememberNavController()

    LaunchedEffect(guestSelected) {
        if (guestSelected != null) {
            navController.navigate(GuestListGraph.Detail(guestSelected)) {
                launchSingleTop = true
            }
        }
    }

    NavHost(navController = navController, startDestination = GuestListGraph.List) {
        composable<GuestListGraph.List> {
            GuestListContent(guests = guests) {
                onGuestSelected(it)
                navController.navigate(GuestListGraph.Detail(it))
            }
        }

        composable<GuestListGraph.Detail>(
            typeMap = mapOf(
                typeOf<GuestItem>() to GuestItemNavType
            )
        ) {
            val arguments = it.toRoute<GuestListGraph.Detail>()
            GuestDetailContent(
                guestItem = arguments.guestItem,
                isNotTabletLandscape = true,
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ShowGuestListDetail(
    guests: List<GuestItem>,
    guestSelected: GuestItem?,
    onGuestSelected: (GuestItem) -> Unit
) {
    val scope = rememberCoroutineScope()

    BoxWithConstraints {
        val availableWidthForPane = maxWidth.div(2)
        val windowAdaptiveInfo = currentWindowAdaptiveInfo()
        val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<GuestItem>(
            scaffoldDirective = calculatePaneScaffoldDirective(windowAdaptiveInfo).copy(
                horizontalPartitionSpacerSize = 8.dp,
                defaultPanePreferredWidth = availableWidthForPane,
            ),
        )

        LaunchedEffect(guestSelected) {
            if (guestSelected != null) {
                scope.launch {
                    scaffoldNavigator.navigateTo(
                        ListDetailPaneScaffoldRole.Detail,
                        guestSelected
                    )
                }
            }
        }

        NavigableListDetailPaneScaffold(
            navigator = scaffoldNavigator,
            listPane = {
                AnimatedPane(modifier = Modifier.fillMaxWidth()) {
                    GuestListContent(
                        guests = guests,
                        onItemClick = { guestItem ->
                            onGuestSelected(guestItem)
                            scope.launch {
                                scaffoldNavigator.navigateTo(
                                    ListDetailPaneScaffoldRole.Detail,
                                    guestItem
                                )
                            }
                        }
                    )
                }
            },
            detailPane = {
                AnimatedPane {
                    scaffoldNavigator.currentDestination?.contentKey?.let {
                        GuestDetailContent(
                            guestItem = it,
                            isNotTabletLandscape = false,
                            onBack = {
                                scope.launch {
                                    scaffoldNavigator.navigateBack()
                                }
                            }
                        )
                    }
                }
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun GuestListContent(
    guests: List<GuestItem>,
    onItemClick: (GuestItem) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Transparent
                ),
                title = {
                    Text(
                        "Party Host Dashboard",
                        fontFamily = nunitoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 23.sp
                    )
                }
            )
        },
        containerColor = SurfaceColor
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.decor_dashboard),
                contentDescription = null
            )
            LazyColumn(
                Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(White, RoundedCornerShape(12.dp))
                    .padding(vertical = 8.dp)
            ) {
                items(guests) {
                    GuestItemCard(
                        guestItem = it,
                        clickable = true,
                        onClick = {
                            onItemClick(it)
                        }
                    )
                }

            }
        }
    }
}

@Composable
fun GuestItemCard(guestItem: GuestItem, clickable: Boolean, onClick: (() -> Unit)? = null) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = clickable) { onClick?.let { it() } }
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = guestItem.name,
            fontFamily = nunitoFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 19.sp,
            color = OnSurfaceColor
        )
        Text(
            text = when (guestItem.state) {
                GuessState.ATTENDING -> "ATTENDING"
                GuessState.NOT_COMING -> "NOT COMING"
                GuessState.MAYBE -> "MAYBE"
            },
            color = when (guestItem.state) {
                GuessState.ATTENDING -> ForestGreenColor
                GuessState.NOT_COMING -> BoulderGreyColor
                GuessState.MAYBE -> ElectricBlueColor
            },
            fontFamily = maliFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuestDetailContent(
    guestItem: GuestItem,
    isNotTabletLandscape: Boolean,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isNotTabletLandscape) "Guest" else "",
                        fontFamily = nunitoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 23.sp
                    )
                },
                navigationIcon = {
                    if (isNotTabletLandscape) {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = null
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Transparent
                )
            )
        },
        containerColor = SurfaceColor
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.decor_dashboard),
                contentDescription = null
            )

            Card(
                Modifier
                    .align(Alignment.TopCenter)
                    .then(
                        if (isNotTabletLandscape) {
                            Modifier
                        } else Modifier.fillMaxSize()
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = White
                )
            ) {
                Column(Modifier.padding(vertical = 10.dp)) {
                    GuestItemCard(
                        guestItem = guestItem,
                        clickable = false
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        "GIFT",
                        fontFamily = maliFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = OnSurfaceColor.copy(alpha = 0.8f),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Text(
                        guestItem.gift,
                        fontFamily = nunitoFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 19.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                }
            }
        }
    }
}

/***
 *
 * PARTY TIME SECTION
 */

@Composable
fun PartyTimelineDestination(
    deviceMode: DeviceMode
) {
    val events = remember { eventList }
    val eventSelected = rememberSaveable {
        mutableStateOf<EventItem?>(null)
    }

    when (deviceMode) {
        DeviceMode.TabletLandscape -> ShowEventListDetail(
            events,
            eventSelected = eventSelected.value,
            onEventSelected = { eventSelected.value = it }
        )

        else -> ShowEventListGraph(
            events,
            eventSelected = eventSelected.value,
            onEventSelected = { eventSelected.value = it }
        )
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ShowEventListDetail(
    events: List<EventItem>,
    eventSelected: EventItem?,
    onEventSelected: (EventItem) -> Unit
) {

    val scope = rememberCoroutineScope()

    BoxWithConstraints {
        val availableWidthForPane = maxWidth.div(2)
        val windowAdaptiveInfo = currentWindowAdaptiveInfo()
        val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<EventItem>(
            scaffoldDirective = calculatePaneScaffoldDirective(windowAdaptiveInfo).copy(
                horizontalPartitionSpacerSize = 8.dp,
                defaultPanePreferredWidth = availableWidthForPane
            )
        )
        LaunchedEffect(eventSelected) {
            if (eventSelected != null) {
                scope.launch {
                    scaffoldNavigator.navigateTo(
                        ListDetailPaneScaffoldRole.Detail,
                        eventSelected
                    )
                }
            }
        }

        NavigableListDetailPaneScaffold(
            navigator = scaffoldNavigator,
            listPane = {
                AnimatedPane(modifier = Modifier.fillMaxWidth()) {
                    EventListContent(
                        events = events,
                        onItemClick = { eventItem ->
                            onEventSelected(eventItem)
                            scope.launch {
                                scaffoldNavigator.navigateTo(
                                    ListDetailPaneScaffoldRole.Detail,
                                    eventItem
                                )
                            }
                        }
                    )
                }
            },
            detailPane = {
                AnimatedPane {
                    scaffoldNavigator.currentDestination?.contentKey?.let {
                        EventDetailContent(
                            eventItem = it,
                            isNotTabletLandscape = false,
                            onBack = {
                                scope.launch {
                                    scaffoldNavigator.navigateBack()
                                }
                            }
                        )
                    }
                }
            },
        )
    }
}

@Composable
fun ShowEventListGraph(
    events: List<EventItem>,
    eventSelected: EventItem?,
    onEventSelected: (EventItem) -> Unit
) {
    val navController = rememberNavController()

    LaunchedEffect(eventSelected) {
        if (eventSelected != null) {
            navController.navigate(EventListGraph.Detail(eventSelected)) {
                launchSingleTop = true
            }
        }
    }

    NavHost(navController = navController, startDestination = EventListGraph.List) {
        composable<EventListGraph.List> {
            EventListContent(events = events) {
                onEventSelected(it)
                navController.navigate(EventListGraph.Detail(it))
            }
        }

        composable<EventListGraph.Detail>(
            typeMap = mapOf(
                typeOf<EventItem>() to EventItemNavType
            )
        ) {
            val arguments = it.toRoute<EventListGraph.Detail>()
            EventDetailContent(
                eventItem = arguments.eventItem,
                isNotTabletLandscape = true,
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun EventListContent(
    events: List<EventItem>,
    onItemClick: (EventItem) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Transparent
                ),
                title = {
                    Text(
                        "Party Host Dashboard",
                        fontFamily = nunitoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 23.sp
                    )
                }
            )
        },
        containerColor = SurfaceColor
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.decor_dashboard),
                contentDescription = null
            )
            LazyColumn(
                Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(White, RoundedCornerShape(12.dp))
                    .padding(vertical = 8.dp)
            ) {
                items(events) {
                    EventItemCard(
                        eventItem = it,
                        clickable = true,
                        onClick = {
                            onItemClick(it)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EventItemCard(eventItem: EventItem, clickable: Boolean, onClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = clickable) { onClick?.let { it() } }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = eventItem.hour,
            fontFamily = maliFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp,
            color = BackgroundColor
        )
        Text(
            text = eventItem.name,
            color = OnSurfaceColor,
            fontFamily = nunitoFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 19.sp
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailContent(
    eventItem: EventItem,
    isNotTabletLandscape: Boolean,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isNotTabletLandscape) "Event" else "",
                        fontFamily = nunitoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 23.sp
                    )
                },
                navigationIcon = {
                    if (isNotTabletLandscape) {
                        IconButton(onClick = onBack) {
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = null
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Transparent
                )
            )
        },
        containerColor = SurfaceColor
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.decor_dashboard),
                contentDescription = null
            )

            Card(
                Modifier
                    .align(Alignment.TopCenter)
                    .then(
                        if (isNotTabletLandscape) {
                            Modifier
                        } else Modifier.fillMaxSize()
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = White
                )
            ) {
                Column(Modifier.padding(vertical = 10.dp)) {

                    Text(
                        eventItem.hour,
                        fontFamily = maliFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = BackgroundColor,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        eventItem.name,
                        fontFamily = nunitoFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 23.sp,
                        color = OnSurfaceColor,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        eventItem.description,
                        fontFamily = nunitoFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 19.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                }
            }
        }
    }
}


/***
 *
 * GIFT SECTION
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GiftsDestination() {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Transparent
                ),
                title = {
                    Text(
                        "Party Host Dashboard",
                        fontFamily = nunitoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 23.sp
                    )
                }
            )
        },
        containerColor = SurfaceColor
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.decor_dashboard),
                contentDescription = null
            )
            LazyColumn(
                Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Transparent, RoundedCornerShape(12.dp))
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(guestList) {
                    GiftCard(
                        guestItem = it
                    )
                }

            }
        }
    }
}

@Composable
fun GiftCard(guestItem: GuestItem) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = White
        )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                guestItem.gift,
                fontFamily = nunitoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp,
                color = OnSurfaceColor
            )
            Text(
                guestItem.name,
                fontFamily = nunitoFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                color = OnSurfaceColor.copy(alpha = 0.8f)
            )
        }
    }
}

/***
 *
 * NAVIGATION
 */

enum class AppDestinations(
    val label: String,
    @DrawableRes val icon: Int,
    val contentDescription: String
) {
    GUEST_LIST("Guest List", R.drawable.guest_icon, "Guest List"),
    PARTY_TIMELINE("Party Timeline", R.drawable.party_icon, "Party Timeline"),
    GIFTS("Gifts", R.drawable.gift_icon, "Gifts"),
}

sealed interface GuestListGraph {
    @Serializable
    object List : GuestListGraph

    @Serializable
    data class Detail(val guestItem: GuestItem) : GuestListGraph
}

sealed interface EventListGraph {
    @Serializable
    object List : EventListGraph

    @Serializable
    data class Detail(val eventItem: EventItem) : EventListGraph
}

val GuestItemNavType = object : NavType<GuestItem>(
    isNullableAllowed = false
) {

    override fun put(
        bundle: SavedState,
        key: String,
        value: GuestItem
    ) {
        bundle.putParcelable(key, value)
    }

    override fun get(
        bundle: SavedState,
        key: String
    ): GuestItem? {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, GuestItem::class.java)
        } else {
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): GuestItem {
        return Json.decodeFromString(value)
    }

    override fun serializeAsValue(value: GuestItem): String {
        return Json.encodeToString(value)
    }

}

val EventItemNavType = object : NavType<EventItem>(
    isNullableAllowed = false
) {

    override fun put(
        bundle: SavedState,
        key: String,
        value: EventItem
    ) {
        bundle.putParcelable(key, value)
    }

    override fun get(
        bundle: SavedState,
        key: String
    ): EventItem? {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, EventItem::class.java)
        } else {
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): EventItem {
        return Json.decodeFromString(value)
    }

    override fun serializeAsValue(value: EventItem): String {
        return Json.encodeToString(value)
    }

}

/***
 *
 * DATA
 */

@Parcelize
@Serializable
data class GuestItem(
    val name: String,
    val state: GuessState,
    val gift: String
) : Parcelable

enum class GuessState {
    ATTENDING, NOT_COMING, MAYBE
}

@Parcelize
@Serializable
data class EventItem(
    val name: String,
    val hour: String,
    val description: String
) : Parcelable

val guestList = listOf(
    GuestItem("Alice Bennett", GuessState.ATTENDING, "Personalized journal"),
    GuestItem("Daniel Cho", GuessState.NOT_COMING, "Handcrafted ceramic mug"),
    GuestItem("Zoë Linde", GuessState.ATTENDING, "Belgian chocolate sampler"),
    GuestItem("Mr. Whiskers", GuessState.MAYBE, "Catnip toy & treats"),
    GuestItem("Ava Rodriguez", GuessState.ATTENDING, "Photo frame with fairy lights"),
    GuestItem("Chinedu Okwudili", GuessState.NOT_COMING, "Classic novel: “Things Fall Apart”"),
    GuestItem("Lily Thompson", GuessState.ATTENDING, "Watercolor painting set"),
    GuestItem("James “Jimmyˮ Kwon", GuessState.ATTENDING, "Bluetooth speaker"),
    GuestItem("Priya Mehra", GuessState.MAYBE, "Gift card to local bakery"),
    GuestItem("Gabriella De La Fuente", GuessState.ATTENDING, "Custom charm bracelet")
)

val eventList = listOf(
    EventItem(
        name = "Venue Setup",
        hour = "08:00",
        description = "Kicking off the day bright and early! We'll be on-site transforming the space into a birthday wonderland. Decorations, music check, and all the final touches to make it magical."
    ),
    EventItem(
        name = "Welcome & Guest Arrival",
        hour = "09:00",
        description = "The doors open and the party officially begins! Guests will be warmly welcomed. Time to find your friends, grab a drink, and feel the excitement build."
    ),
    EventItem(
        name = "Cake Cutting Ceremony",
        hour = "10:00",
        description = "A highlight of any birthday! Gather around as we present the cake, sing a joyful 'Happy Birthday', and make a special wish before the first slice is cut. Don't miss this sweet moment!"
    ),
    EventItem(
        name = "Party Games Begin",
        hour = "11:00",
        description = "Get ready for some fun and laughter! We've planned a series of engaging party games suitable for everyone. A great chance to team up, compete, and win some cool prizes."
    ),
    EventItem(
        name = "Face Painting & Photos",
        hour = "12:00",
        description = "Unleash your inner artist or get transformed with creative face painting! Strike a pose at our photo booth with fun props to capture the memories of the day."
    ),
    EventItem(
        name = "Dinner is Served",
        hour = "13:00",
        description = "Time to recharge with a delicious spread! A variety of tasty dishes will be served to please every palate. Enjoy the food and the good company around you."
    ),
    EventItem(
        name = "Speeches & Toasts",
        hour = "14:00",
        description = "A moment for heartfelt words and shared memories. We'll have some short speeches and invite guests to raise a toast to the birthday star and the wonderful occasion."
    ),
    EventItem(
        name = "Dance Floor Opens",
        hour = "15:00",
        description = "Let the music take over! The DJ will be spinning tunes, and the dance floor is all yours. Show off your moves, or just enjoy the rhythm and energy of the party."
    ),
    EventItem(
        name = "Thank You & Goodbyes",
        hour = "16:00",
        description = "As the celebration comes to a close, we want to express our sincere gratitude to everyone for making this day so special. We'll be saying our goodbyes and wishing everyone a safe journey home."
    )
)