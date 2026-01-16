import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.softcomchallengejunior.data.remote.dto.CategoryDto

@Composable
fun CategoryItem(
    category: CategoryDto,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val mainColor = Color(android.graphics.Color.parseColor(category.colorHex ?: "#FF4081"))
    val backgroundColor = if (isSelected) mainColor.copy(alpha = 0.3f) else mainColor.copy(alpha = 0.12f)

    Column(
        modifier = Modifier
            .width(100.dp)
            .height(58.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .background(backgroundColor, RoundedCornerShape(16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // RENDERIZADOR DE SVG
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(category.icon)
                .decoderFactory(SvgDecoder.Factory()) // Isso permite ler SVGs
                .build(),
            contentDescription = category.name,
            modifier = Modifier.size(20.dp),
            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(mainColor) // Pinta o SVG com a cor do banco!
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = category.name,
            color = mainColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}