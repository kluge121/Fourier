package benny.app.strange.fourier

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import kotlin.math.sin

@Composable
fun SineWave(
    modifier: Modifier = Modifier,
    amplitude: Float = 1f,
    period: Float = 1f,
    phase: Float = 0f
) {
    val path = remember { Path() }

    Canvas(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp, vertical = 32.dp)) {
        val xScale = size.width / 10f
        val yScale = size.height / 10f

        // Draw axis

        drawLine(
            color = Color.White,
            start = Offset(0f, size.height / 2f),
            end = Offset(size.width, size.height / 2f),
            strokeWidth = 2f
        )
        drawLine(
            color = Color.White,
            start = Offset(0f, 0f),
            end = Offset(0f, size.height),
            strokeWidth = 2f
        )

        // Draw labels
        for (i in 0..10) {
            val x = i * xScale
            val y = size.height / 2f
            drawLine(
                color = Color.White,
                start = Offset(x, y - 5f),
                end = Offset(x, y + 5f),
                strokeWidth = 2f
            )
            if (i == 0) continue
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    i.toString(),
                    x,
                    y + 45f,
                    android.graphics.Paint().apply {
                        textSize = 40f
                        color = android.graphics.Color.WHITE
                    }
                )
            }
        }
        for (i in -5..5) {
            val x = 0f
            val y = size.height / 2f - i * yScale
            drawLine(
                color = Color.White,
                start = Offset(x - 5f, y),
                end = Offset(x + 5f, y),
                strokeWidth = 2f
            )
            if (i == 0) continue
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    i.toString(),
                    x - 50f,
                    y + 5f,
                    android.graphics.Paint().apply {
                        textSize = 40f
                        color = android.graphics.Color.WHITE
                    }
                )
            }
        }

        // Draw sine wave
        path.reset()
        path.moveTo(0f, size.height / 2f)

        for (x in 0..size.width.toInt()) {
            val xValue = x / xScale
            val yValue = amplitude * sin(2 * Math.PI * (xValue / period + phase))
            val yCoord = size.height / 2f - (yValue * yScale).toFloat()
            path.lineTo(x.toFloat(), yCoord)
        }

        drawPath(
            path = path,
            color = Color.Yellow,
            style = Stroke(width = 4f)
        )
    }
}

@Composable
fun SineWaveControls(
    amplitude: Float,
    onAmplitudeChange: (Float) -> Unit,
    period: Float,
    onPeriodChange: (Float) -> Unit,
    phase: Float,
    onPhaseChange: (Float) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("amplitude $amplitude", style = MaterialTheme.typography.labelMedium)
        Slider(
            value = amplitude,
            onValueChange = onAmplitudeChange,
            valueRange = 0f..5f,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("period $period", style = MaterialTheme.typography.labelMedium)
        Slider(
            value = period,
            onValueChange = onPeriodChange,
            valueRange = 0.1f..5f,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("phase $phase", style = MaterialTheme.typography.labelMedium)
        Slider(
            value = phase,
            onValueChange = onPhaseChange,
            valueRange = 0f..1f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}