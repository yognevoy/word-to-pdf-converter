package com.yognevoy.wordtopdfconverter.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yognevoy.wordtopdfconverter.R
import com.yognevoy.wordtopdfconverter.ui.theme.WordToPDFConverterTheme
import com.yognevoy.wordtopdfconverter.utils.FileUtils
import com.yognevoy.wordtopdfconverter.viewmodel.FileData
import com.yognevoy.wordtopdfconverter.viewmodel.HomeViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    val context = LocalContext.current
    val selectedFile by viewModel.selectedFile.observeAsState()
    val isConverting by viewModel.isConverting.observeAsState(false)
    val conversionCompleted by viewModel.conversionCompleted.observeAsState(false)

    val pickFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val fileName = it.lastPathSegment ?: context.getString(R.string.file_name_empty)
            val fileSize = FileUtils.getFileSize(context, it)
            viewModel.updateSelectedFile(FileData(fileName, fileSize))
        }
    }

    WordToPDFConverterTheme {
        LaunchedEffect(conversionCompleted) {
            if (conversionCompleted) {
                viewModel.resetConversionStatus()
                navController.navigate("file_list_screen") {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (selectedFile == null) {
                    Image(
                        painter = painterResource(R.drawable.ic_convert),
                        contentDescription = stringResource(R.string.pick_file_icon),
                        modifier = Modifier
                            .size(140.dp)
                            .padding(bottom = 16.dp)
                    )
                    Text(
                        text = stringResource(R.string.pick_file_convert),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                            fontSize = 16.sp
                        )
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                                MaterialTheme.shapes.medium
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = selectedFile!!.name,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = stringResource(R.string.file_size, selectedFile!!.size / 1024),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                if (selectedFile == null) {
                    Button(onClick = { pickFileLauncher.launch("application/msword") }) {
                        Text(text = stringResource(R.string.pick_file))
                    }
                } else {
                    Button(onClick = { viewModel.startConversion() }) {
                        Text(text = stringResource(R.string.convert_file))
                    }
                }

                if (isConverting) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.convert_process),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
