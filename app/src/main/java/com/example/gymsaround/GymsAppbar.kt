import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.gymsaround.R

import com.example.gymsaround.ui.theme.GymsAroundTheme

@ExperimentalMaterial3Api
@Composable
fun GymsAppBar(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.titleLarge,
    color: Color,
    showBackButton: Boolean = true,
    onBackButtonClicked: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                stringResource(title),
                style = titleStyle,
            )
        },
        modifier = modifier.background(color),
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackButtonClicked) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_arrow)
                    )
                }
            }
        }
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun PreviewRepoAppBar() {
    GymsAroundTheme {
        GymsAppBar(title = R.string.general_screen, showBackButton = false, color = colorResource(
            id = R.color.white
        )) {

        }
    }
}