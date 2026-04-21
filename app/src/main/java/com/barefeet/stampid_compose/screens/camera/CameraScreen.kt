package com.barefeet.stampid_compose.screens.camera

import android.Manifest
import android.app.Activity
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.barefeet.stampid_compose.R
import com.barefeet.stampid_compose.utils.CameraManager
import com.barefeet.stampid_compose.utils.clickableSafe
import com.barefeet.stampid_compose.utils.startCrop
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.yalantis.ucrop.UCrop
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    cameraVM: CameraViewModel = hiltViewModel(),
    onNavigateLoading: (Uri) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by cameraVM.uiState.collectAsStateWithLifecycle()
    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )
    val isInteractionEnabled = !uiState.isCapturing  && uiState.isCameraReady
    val ctx = LocalContext.current
    val cameraManager = remember { CameraManager(ctx) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { cameraVM.onEvent(CameraUiEvent.OnImagePicked(it)) }
    }

    val imageCapture = remember {
        ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
    }

    val cropLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val resultUri = UCrop.getOutput(result.data!!)
            resultUri?.let { cameraVM.onEvent(CameraUiEvent.OnImageCropped(it)) }
        } else if (result.resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(result.data!!)
            cameraVM.onEvent(CameraUiEvent.onCameraError(cropError?.message.toString()))
        }
    }


    BackHandler(enabled = uiState.isCapturing) { }

    LaunchedEffect(Unit) {
        cameraVM.effect.collect { effect ->
            when (effect) {
                is CameraUiEffect.CloseScreen -> onBack()

                is CameraUiEffect.LaunchGalleryPicker -> {
                    galleryLauncher.launch("image/*")
                }

                is CameraUiEffect.NavigateToLoading -> {
                    onNavigateLoading(effect.uri)
                }

                is CameraUiEffect.ShowToast -> {
                    Toast.makeText(ctx, effect.message, Toast.LENGTH_SHORT).show()
                }

                is CameraUiEffect.StartCropImage -> {
                    val destinationUri = Uri.fromFile(
                        File(
                            ctx.cacheDir,
                            "CROP_${System.currentTimeMillis()}.jpg"
                        )
                    )
                    startCrop(
                        sourceUri = effect.uri,
                        destinationUri = destinationUri,
                        context = ctx,
                        launcher = cropLauncher
                    )
                }

                is CameraUiEffect.TriggerCapture -> {
                    cameraManager.takePhoto(
                        imageCapture = imageCapture,
                        onImageSaved = { uri ->
                            cameraVM.onEvent(CameraUiEvent.OnImageCaptured(uri))
                        },
                        onError = { error ->
                            cameraVM.onEvent(CameraUiEvent.onCameraError(error.message ?: "Unknown error"))
                        }
                    )
                }
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()){
        CameraContent(
            isPermissionGranted = cameraPermissionState.status.isGranted,
            onEvent = cameraVM::onEvent,
            imageCapture = imageCapture,
            isInteractionEnabled = isInteractionEnabled,
        )

        if(uiState.showSnapTip){
            SnapTipView(
                onDismiss = { cameraVM.onEvent(CameraUiEvent.OnSnaptipToggle) },
            )
        }
    }


    if (!cameraPermissionState.status.isGranted) {
        if (cameraPermissionState.status.shouldShowRationale) {
            AlertDialog(
                onDismissRequest = { /* ... */ },
                title = { Text(stringResource(R.string.camera_permission_title)) },
                text = { Text(stringResource(R.string.camera_permission_rationale)) },
                confirmButton = {
                    Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                        Text(stringResource(R.string.camera_permission_ok))
                    }
                }
            )
        } else {
            LaunchedEffect(Unit) {
                cameraPermissionState.launchPermissionRequest()
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun CameraContent(
    isPermissionGranted: Boolean,
    imageCapture: ImageCapture,
    isInteractionEnabled: Boolean,
    onEvent: (CameraUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ){
        CameraBody(
            modifier = Modifier.weight(1f),
            onEvent = onEvent,
            isPermissionGranted = isPermissionGranted,
            imageCapture = imageCapture,
            isInteractionEnabled = isInteractionEnabled
        )
        CameraOption(
            modifier = Modifier,
            onEvent = onEvent,
            isInteractionEnabled = isInteractionEnabled
        )
    }
}

@Composable
fun CameraBody(
    isPermissionGranted: Boolean,
    imageCapture: ImageCapture,
    isInteractionEnabled: Boolean,
    onEvent: (CameraUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.black))
    ) {
        Row(){
            Image(
                painter = painterResource(id = R.drawable.close_icon3),
                contentDescription = null,
                modifier = Modifier
                    .padding(15.dp)
                    .clickableSafe(
                        enabled = isInteractionEnabled,
                        showRipple = false,
                        onClick = { onEvent(CameraUiEvent.OnCloseClick) }
                    )
            )
        }

        if (isPermissionGranted) {
            CameraPreview(
                modifier = Modifier
                    .padding(bottom = 40.dp),
                imageCapture = imageCapture,
                onCameraReady = { ready ->
                    onEvent(CameraUiEvent.OnCameraReady(ready))
                }
            )
        }
    }
}

@Composable
fun CameraOption(
    onEvent: (CameraUiEvent) -> Unit,
    isInteractionEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(R.color.white))
            .padding(vertical = 20.dp)
        ,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        CommonOptionButtonCamera(
            icon = R.drawable.gallery_icon,
            title = R.string.camera_text1,
            modifier = Modifier
                .clickableSafe(
                    enabled = isInteractionEnabled,
                    showRipple = false,
                    onClick = {  onEvent(CameraUiEvent.OnGalleryClick) }
                )
        )

        Image(
            painter = painterResource(id = R.drawable.take_photo_button),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 50.dp)
                .size(72.dp)
                .clickableSafe(
                    enabled = isInteractionEnabled,
                    onClick = { onEvent(CameraUiEvent.OnCaptureClick) }
                )
        )

        CommonOptionButtonCamera(
            icon = R.drawable.snap_tip_icon,
            title = R.string.camera_text2,
            modifier = Modifier
                .clickableSafe{
                    onEvent(CameraUiEvent.OnSnaptipToggle)
                }
        )
    }
}

@Composable
fun CommonOptionButtonCamera(
    icon: Int,
    title: Int,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ){
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
        )

        Text(
            text = stringResource(title),
            modifier = Modifier
                .padding(top = 10.dp),
            color = colorResource(R.color.black),
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.onest_regular)),
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            )
        )
    }
}

@Preview
@Composable
private fun CommonOptionButtonCameraPrev() {
    CommonOptionButtonCamera(
        icon = R.drawable.gallery_icon,
        title = R.string.camera_text1
    )
}

@Preview
@Composable
private fun CameraOptionPrev() {
    CameraOption(
        modifier = Modifier,
        onEvent = {},
        isInteractionEnabled = true
    )
}

@Preview
@Composable
private fun CameraBodyPrev() {
    CameraBody(
        modifier = Modifier,
        onEvent = {},
        isPermissionGranted = false,
        imageCapture = ImageCapture.Builder().build(),
        isInteractionEnabled = true
    )
}