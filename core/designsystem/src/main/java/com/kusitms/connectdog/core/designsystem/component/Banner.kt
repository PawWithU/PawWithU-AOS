package com.kusitms.connectdog.core.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.Gray2

@Composable
fun BannerGuideline(
    onNavigateToReview: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 84.dp)
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                ) {
                    append(stringResource(id = R.string.home_banner_1))
                }
                withStyle(
                    SpanStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(stringResource(id = R.string.home_banner_2))
                }
            },
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.background,
            lineHeight = 20.sp
        )
        Button(
            onClick = onNavigateToReview,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .wrapContentSize()
                .defaultMinSize(minHeight = 22.dp, minWidth = 55.dp)
        ) {
            Text(
                text = stringResource(id = R.string.home_banner_button_guideline),
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun MoveContent(
    onClick: () -> Unit,
    titleRes: Int
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 18.sp
        )
        IconButton(onClick = { onClick() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_right_arrow),
                contentDescription = "move to another screen",
                modifier = Modifier.size(24.dp),
                tint = Gray2
            )
        }
    }
}
