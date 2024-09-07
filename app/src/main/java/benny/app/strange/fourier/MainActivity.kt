package benny.app.strange.fourier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import benny.app.strange.fourier.ui.theme.FourierTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FourierTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) { innerPadding ->
                    var amplitude by remember { mutableFloatStateOf(1f) }
                    var period by remember { mutableFloatStateOf(1f) }
                    var phase by remember { mutableFloatStateOf(0f) }

                    val animatedAmplitude by animateFloatAsState(targetValue = amplitude, label = "")
                    val animatedPeriod by animateFloatAsState(targetValue = period, label = "")
                    val animatedPhase by animateFloatAsState(targetValue = phase, label = "")

                    Box(modifier = Modifier.padding(innerPadding)) {
                        Column {
                            SineWave(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                amplitude = animatedAmplitude,
                                period = animatedPeriod,
                                phase = animatedPhase
                            )
                            SineWaveControls(
                                amplitude = amplitude,
                                onAmplitudeChange = { amplitude = it },
                                period = period,
                                onPeriodChange = { period = it },
                                phase = phase,
                                onPhaseChange = { phase = it }
                            )
                        }
                    }
                }
            }
        }
    }
}
