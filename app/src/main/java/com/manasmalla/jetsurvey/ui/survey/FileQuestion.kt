package com.manasmalla.jetsurvey.ui.survey

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.manasmalla.jetsurvey.R
import com.manasmalla.jetsurvey.ui.theme.JetsurveyTheme

@Composable
fun FileQuestion(modifier: Modifier = Modifier, imageUri: Uri? = null, onImageSelected: (Uri)->Unit = {}) {

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                onImageSelected(it)
            }
        }
    )

    val context = LocalContext.current

    Column(
        modifier = modifier
            .aspectRatio(1f)
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.small
            )
            .clickable {
                cameraLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (imageUri != null) AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUri)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .weight(1f).padding(24.dp).fillMaxSize().aspectRatio(1.2f)
                .wrapContentSize(
                    Alignment.Center
                ).clip(MaterialTheme.shapes.small), contentScale = ContentScale.Crop
        ) else
            Image(
            painterResource(id = R.drawable.ic_selfie_light),
            contentDescription = "Add a selfie",
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .wrapContentSize(
                    Alignment.Center
                )
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                if(imageUri == null) Icons.Rounded.AddAPhoto else Icons.Rounded.Repeat,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                if(imageUri == null) "Add Photo".uppercase() else "Retake Photo".uppercase(),
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun FileQuestionPreview() {
    JetsurveyTheme {
        FileQuestion()
    }
}