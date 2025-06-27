package com.raulastete.birthdaycelebrationsminichallenge.gift_memory_match_fifth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raulastete.birthdaycelebrationsminichallenge.R
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.BackgroundColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.CaramelBrownColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.CherryRedColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.ElectricBlueColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.ForestGreenColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.LeafGreenColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.OnSurfaceColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.SuccessColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.SurfaceColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.SurfaceHigherColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.TangerineColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.maliFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val matches: Int = 0,
    val isPlaying: Boolean = false,
    val cardListState: List<CardUi> = cardList.shuffled(),
    val lastCardFlipped: CardUi? = null,
    val isFlipping: Boolean = false,
    val showEndGameDialog: Boolean = false
)

class GiftMemoryMatchViewModel : ViewModel() {

    private var _uiState = MutableStateFlow<UiState>(UiState())
    val uiState = _uiState.asStateFlow()

    fun startGame() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isPlaying = true,
                    matches = 0,
                    cardListState = uiState.value.cardListState.map { it.copy(inFront = true) }
                )
            }
            delay(3000)
            _uiState.update {
                it.copy(
                    cardListState = uiState.value.cardListState.map { it.copy(inFront = false) }
                )
            }
        }
    }

    fun resetGame() {
        _uiState.update {
            UiState()
        }
    }

    fun onFlip(index: Int) {
        val card = uiState.value.cardListState[index]

        if (uiState.value.isPlaying && !card.inFront) {

            val lastCard = uiState.value.lastCardFlipped

            val carListTransformed = uiState.value.cardListState.mapIndexed { index, item ->
                if (item == card) {
                    item.copy(inFront = true)
                } else item
            }

            _uiState.update {
                it.copy(
                    cardListState = carListTransformed,
                    isFlipping = true
                )
            }

            if (lastCard == null) {
                _uiState.update {
                    it.copy(
                        lastCardFlipped = card,
                        isFlipping = false
                    )
                }
            } else {

                if (lastCard.isMatch(card)) {
                    _uiState.update {
                        it.copy(
                            matches = uiState.value.matches + 1,
                            lastCardFlipped = null,
                            isFlipping = false
                        )
                    }
                } else {
                    viewModelScope.launch {

                        val cardListRestored =
                            uiState.value.cardListState.mapIndexed { index, item ->
                                if (item.text == card.text || item.text == lastCard.text) {
                                    item.copy(inFront = false)
                                } else item
                            }

                        delay(2000)
                        _uiState.update {
                            it.copy(
                                cardListState = cardListRestored,
                                lastCardFlipped = null,
                                isFlipping = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun showEndGameDialog(){
        _uiState.update {
            it.copy(
                showEndGameDialog = true
            )
        }
    }
}

@Composable
fun GiftMemoryMatch(
    viewModel: GiftMemoryMatchViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.matches) {
        if (uiState.matches == 6) {
            viewModel.showEndGameDialog()
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.countdown_decor),
            contentDescription = null
        )

        Column(
            modifier = Modifier.align(Alignment.Center),
        ) {

            AnimatedVisibility(visible = uiState.isPlaying) {
                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "${uiState.matches} of 6 matches found",
                        textAlign = TextAlign.Center,
                        fontFamily = maliFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp,
                        color = SurfaceColor
                    )

                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        (1..6).forEach { index ->
                            Box(
                                Modifier
                                    .size(width = 60.dp, height = 8.dp)
                                    .background(
                                        color = if (index <= uiState.matches) SuccessColor else White.copy(
                                            alpha = 0.15f
                                        ), shape = RoundedCornerShape(8.dp)
                                    )
                            )
                        }
                    }
                }
            }


            Spacer(Modifier.height(40.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                itemsIndexed(uiState.cardListState) { index, card ->
                    MemoryCard(
                        card,
                        flippable = uiState.isPlaying && uiState.isFlipping.not(),
                        onFlip = {
                            viewModel.onFlip(index)
                        }
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = uiState.isPlaying.not(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 60.dp),
        ) {
            Button(
                onClick = {
                    viewModel.startGame()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = SurfaceHigherColor,
                    contentColor = OnSurfaceColor
                ),
                contentPadding = PaddingValues(horizontal = 28.dp, vertical = 8.dp),
                shape = RoundedCornerShape(100.dp)
            ) {
                Text(
                    "Start",
                    fontFamily = maliFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )
            }
        }
    }

    if(uiState.showEndGameDialog){
        Dialog(onDismissRequest = { viewModel.resetGame() }) {
            FinishGameDialog {
                viewModel.resetGame()
            }
        }
    }
}

@Composable
fun FinishGameDialog(
    onTryAgain: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        color = SurfaceColor
    ) {
        Box(
            modifier = Modifier
                .height(IntrinsicSize.Min),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize(),
                imageVector = ImageVector.vectorResource(R.drawable.birthday_card_decor),
                contentDescription = null
            )

            Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.weight(1f))

                Text(
                    "Party on!",
                    fontFamily = maliFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                    lineHeight = 43.sp
                )

                Text(
                    "All gifts matched",
                    fontFamily = maliFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 21.sp,
                    lineHeight = 28.sp
                )

                Spacer(Modifier.height(32.dp))

                Button(
                    onClick = {
                        onTryAgain()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SurfaceHigherColor,
                        contentColor = OnSurfaceColor
                    ),
                    contentPadding = PaddingValues(horizontal = 28.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(100.dp)
                ) {
                    Text(
                        "Try again!",
                        fontFamily = maliFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    )
                }

                Spacer(Modifier.weight(1f))

            }
        }
    }
}

@Composable
fun MemoryCard(
    cardUi: CardUi,
    flippable: Boolean,
    onFlip: (Boolean) -> Unit,
) {

    FlippedCard(
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(12.dp)),
        flippable = flippable,
        onFlip = onFlip,
        flipped = cardUi.inFront,
        front = {
            Image(
                modifier = Modifier.fillMaxSize(),
                imageVector = ImageVector.vectorResource(R.drawable.memory_card_background),
                contentDescription = null
            )
        },
        back = {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(SurfaceColor)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .border(
                            width = 4.dp,
                            color = cardUi.color,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(72.dp),
                        imageVector = ImageVector.vectorResource(cardUi.icon),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )

                    Text(
                        cardUi.text,
                        fontFamily = maliFontFamily,
                        fontWeight = if (cardUi.isPersonCard) FontWeight.Bold else FontWeight.Medium,
                        fontSize = if (cardUi.isPersonCard) 24.sp else 14.sp
                    )
                }
            }
        }
    )
}

@Composable
private fun FlippedCard(
    modifier: Modifier = Modifier,
    front: @Composable () -> Unit,
    back: @Composable () -> Unit,
    flippable: Boolean,
    flipped: Boolean,
    onFlip: (flipped: Boolean) -> Unit
) {

    val rotationYState by animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        animationSpec = tween(durationMillis = 400), label = "rotationY"
    )

    Box(
        modifier = modifier
            .clickable(flippable) {
                onFlip(flipped)
            }
            .graphicsLayer {
                rotationY = rotationYState
                cameraDistance = 12 * density
            },
    ) {
        if (rotationYState <= 90f) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) { front() }
        } else {
            Box(
                Modifier
                    .fillMaxSize()
                    .graphicsLayer { rotationY = 180f },
                contentAlignment = Alignment.Center
            ) { back() }
        }
    }
}

data class CardUi(
    val text: String,
    val color: Color,
    val isPersonCard: Boolean,
    val inFront: Boolean = false,
) {
    val icon = if (isPersonCard) {
        R.drawable.guest
    } else R.drawable.gift

    fun isMatch(cardUi: CardUi): Boolean {
        return this.color == cardUi.color
    }
}


val cardList = listOf<CardUi>(
    CardUi(
        text = "Chip",
        color = CaramelBrownColor,
        isPersonCard = true
    ),
    CardUi(
        text = "Lily",
        color = LeafGreenColor,
        isPersonCard = true
    ),
    CardUi(
        text = "Dot",
        color = CherryRedColor,
        isPersonCard = true
    ),
    CardUi(
        text = "Fin",
        color = TangerineColor,
        isPersonCard = true
    ),
    CardUi(
        text = "Buzz",
        color = ElectricBlueColor,
        isPersonCard = true
    ),
    CardUi(
        text = "Ivy",
        color = ForestGreenColor,
        isPersonCard = true
    ),
    CardUi(
        text = "Cookie tin",
        color = CaramelBrownColor,
        isPersonCard = false
    ),
    CardUi(
        text = "Watering Can",
        color = LeafGreenColor,
        isPersonCard = false
    ),
    CardUi(
        text = "Toy drone",
        color = ElectricBlueColor,
        isPersonCard = false
    ),
    CardUi(
        text = "Polka mug",
        color = CherryRedColor,
        isPersonCard = false
    ),
    CardUi(
        text = "Hanging plant",
        color = ForestGreenColor,
        isPersonCard = false
    ),
    CardUi(
        text = "Goldfish plush",
        color = TangerineColor,
        isPersonCard = false
    )
)

