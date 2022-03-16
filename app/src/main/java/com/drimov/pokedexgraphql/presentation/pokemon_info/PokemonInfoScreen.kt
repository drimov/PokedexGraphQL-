package com.drimov.pokedexgraphql.presentation.pokemon_info

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.drimov.pokedexgraphql.R
import com.drimov.pokedexgraphql.domain.model.Abilities
import com.drimov.pokedexgraphql.domain.model.Pokemon
import com.drimov.pokedexgraphql.domain.model.PokemonSpecies
import com.drimov.pokedexgraphql.presentation.ui.theme.Blue700
import com.drimov.pokedexgraphql.presentation.ui.theme.Grey200
import com.drimov.pokedexgraphql.presentation.ui.theme.Red200
import com.drimov.pokedexgraphql.util.*
import com.drimov.pokedexgraphql.util.Constants.footToMeter
import com.drimov.pokedexgraphql.util.Constants.lbsToKg
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun PokemonInfoScreen(
    onPopBackStack: () -> Unit,
    viewModel: PokemonInfoViewModel = hiltViewModel()
) {

    val language = viewModel.language.value

    val color = Color.White
    val fontSize = 18.sp

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                else -> Unit
            }
        }
    }
    val state = viewModel.state.value
    HandleState(
        modifier = Modifier,
        state = state,
        viewModel = viewModel,
        color = color,
        fontSize = fontSize,
        defaultLanguage = language
    )
//    PokemonDetails(
//        modifier = Modifier,
//        viewModel = viewModel,
//        color = color,
//        fontSize = fontSize,
//        language = language
//    )

//    when (viewModel.state.value.isLoading) {
//        true -> {
//            Box(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                CircularProgressIndicator(
//                    modifier = Modifier.align(Center)
//                )
//            }
//        }
//        false -> {
//
//            PokemonDetails(
//                modifier = Modifier,
//                viewModel = viewModel,
//                color = color,
//                fontSize = fontSize,
//                language = language
//            )
//        }
//    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun PokemonDetails(
    modifier: Modifier,
    viewModel: PokemonInfoViewModel,
    color: Color,
    fontSize: TextUnit,
    language: Int
) {
    val dominantType = 0
    val titles: List<String> = listOf(
        stringResource(id = R.string.about),
        stringResource(id = R.string.stats),
        stringResource(id = R.string.others)
    )
    val backgroundColor =
        typeToColor(viewModel.state.value.pokemonSpecies?.pokemons?.get(0)!!.types[dominantType])
    val scroll = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .verticalScroll(scroll, true),

        ) {
        HeaderInfo(
            modifier = modifier,
            viewModel = viewModel,
            color = color,
            fontSize = fontSize
        )
        PagersContent(
            modifier = modifier,
            pages = titles,
            pokemonSpecies = viewModel.state.value.pokemonSpecies!!,
            color = color,
            fontSize = fontSize,
            language = language
        )
    }
}

// -------- HEADER -------- //
@Composable
fun HeaderInfo(
    modifier: Modifier,
    viewModel: PokemonInfoViewModel,
    color: Color,
    fontSize: TextUnit
) {
    val pokemon = viewModel.state.value.pokemonSpecies!!.pokemons[0]
    val pokemonSpecies = viewModel.state.value.pokemonSpecies!!

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = modifier.weight(0.3f)
        ) {
            //arrow
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_keyboard_backspace_24),
                contentDescription = stringResource(id = R.string.arrow_back),
                modifier = modifier
                    .scale(1.5f)
                    .clickable {
                        viewModel.onEvent(PokemonInfoEvent.OnBackPressed)
                    },
                tint = color
            )
            HeaderPokemonNameAndNumber(
                modifier = modifier,
                color = color,
                pokemonSpecies = pokemonSpecies,
                fontSize = fontSize
            )
            HeaderTypeImg(modifier = modifier, pokemon = pokemon)
        }
        HeaderTypeText(
            modifier = modifier,
            color = color,
            pokemonSpecies = pokemonSpecies
        )
    }
    HeaderPokemonImg(modifier = modifier, pokemonSpecies = pokemonSpecies)
}

@Composable
fun HeaderPokemonNameAndNumber(
    modifier: Modifier,
    color: Color,
    pokemonSpecies: PokemonSpecies?,
    fontSize: TextUnit
) {
    Text(
        text = numberToIndex(pokemonSpecies!!.id),
        modifier = modifier
            .alpha(0.60f),
        color = color,
        fontWeight = FontWeight.ExtraBold,
        fontSize = fontSize
    )

    Text(
        text = pokemonSpecies.names[0].name.ucFirst(),
        modifier = modifier,
        color = color,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 25.sp
    )
}

@Composable
fun HeaderTypeImg(modifier: Modifier, pokemon: Pokemon?) {
    // images types
    Row(
        modifier = modifier
    ) {
        pokemon!!.types.forEach {
            val painter = rememberImagePainter(
                data = it.name.urlType()
            )
            Image(
                painter = painter,
                contentDescription = stringResource(id = R.string.type, it.name),
                modifier = modifier
                    .size(40.dp)
                    .padding(end = 12.dp)
            )
        }
    }
}

@Composable
fun HeaderTypeText(
    modifier: Modifier,
    color: Color,
    pokemonSpecies: PokemonSpecies?
) {

    // pokemon type
    Text(
        text = pokemonSpecies!!.names[0].genus!!,
        textAlign = TextAlign.End,
        modifier = modifier
            .padding(top = 32.dp)
            .padding(16.dp),
        color = color
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun HeaderPokemonImg(modifier: Modifier, pokemonSpecies: PokemonSpecies?) {
    Row {
        //image
        val painter = rememberImagePainter(
            data = pokemonSpecies!!.url
        )
        val painterState = painter.state

        Image(
            painter = painter,
            contentDescription = pokemonSpecies.names[0].name,
            modifier = modifier
                .size(210.dp)
                .weight(0.40f),
            alignment = BottomCenter
        )
        if (painterState is ImagePainter.State.Loading) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .size(210.dp)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Center))
            }
        }
    }
}

// -------- PAGERS -------- //
@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagersContent(
    modifier: Modifier,
    pages: List<String>,
    pokemonSpecies: PokemonSpecies,
    color: Color,
    language: Int,
    fontSize: TextUnit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier.pagerTabIndicatorOffset(
                    pagerState = pagerState,
                    tabPositions = tabPositions
                )
            )
            modifier
                .background(Color.White)
                .alpha(0.5f)
        },
        modifier = modifier
            .padding(top = 15.dp),
        backgroundColor = Color.Transparent
    ) {
        pages.forEachIndexed { index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                },
                text = {
                    Text(text = title, color = color)
                },
                selectedContentColor = Color.White
            )
        }
    }
    HorizontalPager(
        count = pages.size,
        state = pagerState,
        verticalAlignment = Alignment.Top,
        reverseLayout = true,
        contentPadding = PaddingValues(8.dp)
    ) { page ->
        when (page) {
            0 -> AboutCard(
                modifier = modifier,
                color = color,
                pokemonSpecies = pokemonSpecies
            )
            1 -> StatsCard(
                modifier = modifier,
                pokemonSpecies = pokemonSpecies,
                color = color,
            )
            2 -> OthersCard(
                modifier = modifier,
                color = color,
                pokemonSpecies = pokemonSpecies,
                fontSize = fontSize,
                language = language
            )
        }
    }
}

// -------- ABOUT -------- //
@Composable
fun AboutCard(
    modifier: Modifier,
    pokemonSpecies: PokemonSpecies,
    color: Color
) {

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Story(
            modifier = modifier,
            color = color,
            pokemonSpecies = pokemonSpecies
        )
        Information(modifier = modifier, pokemon = pokemonSpecies.pokemons[0])
        Breeding(modifier = modifier, color = color, pokemonSpecies = pokemonSpecies)
    }
}

@Composable
fun Story(modifier: Modifier, color: Color, pokemonSpecies: PokemonSpecies) {


    val text = pokemonSpecies.flavorTexts[0].text.replace("\n", "")
    Row {
        Text(
            text = text,
            modifier = modifier
                .padding(10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            color = color
        )
    }
}

@Composable
fun Information(modifier: Modifier, pokemon: Pokemon) {
    //Card
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .shadow(elevation = 16.dp, shape = RoundedCornerShape(15.dp)),
        shape = RoundedCornerShape(15.dp)

    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InformationHeight(modifier = modifier, pokemon = pokemon)
            InformationWeight(modifier = modifier, pokemon = pokemon)
        }
    }
}

@Composable
fun InformationHeight(modifier: Modifier, pokemon: Pokemon) {
    // Height
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.height),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            modifier = modifier.alpha(0.8f),
            color = Grey200
        )
        val ft = stringResource(id = R.string.feet)
        val m = stringResource(id = R.string.meter)
        val calcRef = calWithRef(number = pokemon.height!!.toDouble(), ref = footToMeter)
        Text(text = "${pokemon.height}$ft (${calcRef} $m)")
    }
}

@Composable
fun InformationWeight(modifier: Modifier, pokemon: Pokemon) {
    // Weight
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.weight),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold,
            modifier = modifier.alpha(0.8f),
            color = Grey200
        )
        val calcWeight = calWithRef(pokemon.weight!!.toDouble(), ref = lbsToKg)
        val lbs = stringResource(id = R.string.lbs)
        val kg = stringResource(id = R.string.kg)
        Text(text = "${pokemon.weight} $lbs (${calcWeight} $kg)")
    }
}

@Composable
fun Breeding(modifier: Modifier, color: Color, pokemonSpecies: PokemonSpecies) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            BreedingTitle(modifier = modifier, color = color)
            BreedingObject(
                modifier = modifier,
                color = color,
                pokemonSpecies = pokemonSpecies
            )
        }

    }
}

@Composable
fun BreedingTitle(modifier: Modifier, color: Color) {
    // title
    Row {
        Text(
            text = stringResource(id = R.string.breeding),
            modifier = modifier.padding(4.dp),
            color = color
        )
    }
}

@Composable
fun BreedingObject(modifier: Modifier, color: Color, pokemonSpecies: PokemonSpecies) {
    //object
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        // title object
        Column(modifier = modifier.padding(4.dp)) {
            Text(text = stringResource(id = R.string.egg_groups), color = color)
            Text(text = stringResource(id = R.string.capture_rate), color = color)
        }
        // value object
        Column(modifier = modifier.padding(4.dp)) {
            Row {
                var nbEggGroup = 0
                pokemonSpecies.eggGroups.forEach { eggGroup ->
                    eggGroup.eggs.forEach {
                        Text(text = it.name.ucFirst(), color = color)
                        if (nbEggGroup != (pokemonSpecies.eggGroups.size - 1)) {
                            Text(text = ", ", color = color)
                            nbEggGroup++
                        }
                    }
                }

                if (pokemonSpecies.eggGroups.isEmpty()) {
                    Text(text = stringResource(id = R.string.unknown), color = color)
                }
            }
            Text(
                text = "${pokemonSpecies.captureRate}",
                color = color
            )
        }
    }
}

// -------- STATS -------- //

@Composable
fun StatsCard(modifier: Modifier, pokemonSpecies: PokemonSpecies, color: Color) {
    Row(
        modifier = modifier
            .fillMaxSize(),
    ) {
        val listStats = pokemonSpecies.pokemons[0].stats

        //Property
        Column(modifier = modifier.weight(0.35f)) {
            listStats.forEach { statsName ->
                statsName.stats.forEach {
                    Text(
                        text = it.name.ucFirst(),
                        modifier = modifier.padding(6.dp),
                        color = color,
                        textAlign = TextAlign.Justify
                    )
                }
            }
            Text(
                text = stringResource(id = R.string.total),
                modifier = modifier.padding(6.dp),
                color = color
            )
        }
        // Values
        var totalStat = 0
        Column(modifier = modifier.weight(0.10f)) {
            listStats.forEach {
                Text(
                    text = it.baseStat.toString(),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = color
                )
                totalStat += it.baseStat
            }
            // Total values
            Text(
                text = totalStat.toString(), modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = color
            )
        }
        // Progress Bar
        Column(modifier = modifier.weight(0.5f)) {
            val maxPerStat = 150
            val totalMaxStats = maxPerStat * listStats.size
            listStats.forEachIndexed { index, stats ->
                val baseStat = stats.baseStat / maxPerStat.toFloat()
                val colorBar: Color?

                colorBar = statToColor(index)
                LinearProgressIndicator(
                    progress = baseStat,
                    modifier = modifier.padding(15.dp),
                    color = colorBar
                )
            }
            LinearProgressIndicator(
                progress = totalStat / totalMaxStats.toFloat(),
                modifier = modifier.padding(15.dp),
                color = color
            )
        }
    }
}

// -------- OTHERS -------- //
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun OthersCard(
    modifier: Modifier,
    pokemonSpecies: PokemonSpecies,
    color: Color,
    fontSize: TextUnit,
    language: Int
) {
    val fontWTitle = FontWeight.Bold
    val fontWContent = FontWeight.Normal

    val abilities = pokemonSpecies.pokemons[0].abilities
    val habitat = pokemonSpecies.habitats
    val growthRate = pokemonSpecies.growthRate
    val shape = pokemonSpecies.shapes
    val baseHappiness = pokemonSpecies.baseHappiness

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val listHabitat = mutableMapOf<Int, String>()
        val listGrowthRate = mutableMapOf<Int, String>()
        val listShapes = mutableMapOf<Int, String>()

        habitat?.forEach {
            listHabitat[it.id] = it.name
        }
        growthRate.forEach {
            listGrowthRate[it.id] = it.description
        }
        shape?.forEach {
            listShapes[it.id!!] = it.name!!
        }
        val habitatName = rightTranslate(listHabitat, language)
        val growthRateName = rightTranslate(listGrowthRate, language)
        val shapeName = rightTranslate(listShapes, language)

        Log.d("habitat", "$habitatName")
        val listContent = listOf<String>(
            baseHappiness.toString(),
            if(habitatName.isNullOrEmpty()) stringResource(id = R.string.unknown) else habitatName,
            growthRateName!!,
            if(shapeName.isNullOrEmpty()) stringResource(id = R.string.unknown) else shapeName,
        )
        val listTitles = listOf(
            stringResource(id = R.string.base_happiness),
            stringResource(id = R.string.habitat),
            stringResource(id = R.string.grow_rate),
            stringResource(id = R.string.shape),
            stringResource(id = R.string.abilities)
        )
        Row(
            modifier = modifier
                .fillMaxSize(), horizontalArrangement = Arrangement.SpaceAround
        ) {
            OtherTitles(
                modifier = modifier,
                listTitles = listTitles,
                color = color,
                fontSize = fontSize,
                fontWeight = fontWTitle
            )
            OtherContents(
                modifier = modifier,
                listContents = listContent,
                color = color,
                fontSize = fontSize,
                fontWeight = fontWContent,
                abilities = abilities
            )
        }
    }
}

@Composable
fun OtherTitles(
    modifier: Modifier,
    listTitles: List<String>,
    color: Color,
    fontSize: TextUnit,
    fontWeight: FontWeight
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(0.4f)
    ) {
        listTitles.forEachIndexed { index, title ->
            Text(
                text = title,
                color = color,
                fontSize = fontSize,
                fontWeight = fontWeight
            )
            if (index < listTitles.size - 1) {
                Spacer(modifier = modifier.padding(4.dp))
            }
        }
    }
}

@Composable
fun OtherContents(
    modifier: Modifier,
    listContents: List<String>,
    color: Color,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    abilities: List<Abilities>,
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(0.65f)
    ) {
        listContents.forEachIndexed { index, content ->
            Text(
                text = content.ucFirst(),
                color = color,
                fontSize = fontSize,
                fontWeight = fontWeight
            )
            if (index < listContents.size) {
                Spacer(modifier = modifier.padding(4.dp))
            }
        }
        ListAbilities(
            modifier = modifier,
            abilities = abilities,
            color = color,
            fontSize = fontSize
        )
    }
}

@Composable
fun ListAbilities(
    modifier: Modifier,
    abilities: List<Abilities?>?,
    color: Color,
    fontSize: TextUnit
) {
    Row(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column {
            abilities?.forEach { ability ->
                ability?.abilities?.forEach {
                    Text(
                        text = it.name.ucFirst(),
                        color = color,
                        fontSize = fontSize,
                        textAlign = TextAlign.Start
                    )
                }

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun HandleState(
    modifier: Modifier,
    state: PokemonInfoState,
    viewModel: PokemonInfoViewModel,
    color: Color,
    fontSize: TextUnit,
    defaultLanguage: Int
) {
    if (state.isLoading) {
        Box(
            contentAlignment = Center,
            modifier = modifier
                .fillMaxSize()
                .background(Blue700)
        ) {
            CircularProgressIndicator()
        }
    }
    if (!state.isLoading && !state.isError) {
        PokemonDetails(
            modifier = modifier,
            viewModel = viewModel,
            color = color,
            fontSize = fontSize,
            language = defaultLanguage
        )
    }
    if (state.isError) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Blue700)
                .wrapContentSize(Center)
        ) {
            var message = ""
            when (state.message) {
                Constants.httpExceptionErr -> message =
                    stringResource(id = R.string.http_exception_err)
                Constants.ioExceptionErr -> message = stringResource(id = R.string.io_exception_err)

            }
            Text(
                text = message,
                color = Red200,
                fontSize = 18.sp,
                modifier = modifier.padding(vertical = 8.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = modifier.padding(8.dp))
            Box(modifier = modifier.align(Alignment.CenterHorizontally)) {
                Button(
                    onClick = { viewModel.onEvent(PokemonInfoEvent.OnReload(language = viewModel.language.value)) }
                ) {
                    Text(stringResource(id = R.string.retry))
                }
            }
        }
    }
}