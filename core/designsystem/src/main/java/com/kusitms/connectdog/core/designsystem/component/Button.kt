package com.kusitms.connectdog.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Gray5
import com.kusitms.connectdog.core.designsystem.theme.Orange10
import com.kusitms.connectdog.core.designsystem.theme.Orange_40
import com.kusitms.connectdog.core.designsystem.theme.Typography

@Composable
fun ConnectDogBottomButton(
    modifier: Modifier = Modifier,
    height: Int = 56,
    enabledColor: Color = MaterialTheme.colorScheme.primary,
    disabledColor: Color = Orange_40,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    border: BorderStroke = BorderStroke(width = 0.dp, color = MaterialTheme.colorScheme.outline),
    onClick: () -> Unit,
    paddingValues: PaddingValues = PaddingValues(vertical = 16.dp),
    content: String,
    enabled: Boolean = true,
    fontSize: Int = 16,
    radius: Int = 12
) {
    Button(
        onClick = { if (enabled) onClick() },
        contentPadding = paddingValues,
        shape = RoundedCornerShape(radius.dp),
        modifier = modifier
            .height(height.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) enabledColor else disabledColor,
            contentColor = textColor
        ),
        border = border
    ) {
        Text(
            text = content,
            style = Typography.titleSmall,
            color = textColor,
            fontSize = fontSize.sp
        )
    }
}

@Composable
fun ConnectDogIconBottomButton(
    modifier: Modifier = Modifier,
    iconId: Int,
    contentDescription: String,
    color: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit,
    content: String
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 16.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = color, contentColor = textColor)
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = content, color = textColor, style = Typography.titleSmall)
    }
}

@Composable
fun ConnectDogNormalButton(
    content: String,
    color: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit = {},
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    fontSize: Int = 16,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(56.dp),
    enabled: Boolean = true
) {
    ConnectDogBottomButton(
        onClick = onClick,
        content = content,
        enabledColor = color,
        modifier = modifier,
        textColor = textColor,
        fontSize = fontSize,
        enabled = enabled
    )
}

@Composable
fun ConnectDogOutlinedButton(
    width: Int,
    height: Int,
    text: String,
    padding: Int,
    verticalPadding: Int = 4,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    fontColor: Color = MaterialTheme.colorScheme.primary
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .width(width.dp)
            .height(height.dp),
        border = BorderStroke(1.dp, borderColor),
        contentPadding = PaddingValues(
            top = verticalPadding.dp,
            bottom = verticalPadding.dp,
            start = padding.dp,
            end = padding.dp
        )
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = fontColor,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ConnectDogFilledButton(
    width: Int,
    height: Int,
    text: String,
    padding: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    fontColor: Color = MaterialTheme.colorScheme.primary
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(width.dp)
            .height(height.dp),
        contentPadding = PaddingValues(
            top = 1.dp,
            bottom = 1.dp,
            start = padding.dp,
            end = padding.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor)
    ) {
        Text(
            text = text,
            fontSize = 8.sp,
            color = fontColor,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ConnectDogOutlinedButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 1.dp, color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline),
        colors = ButtonDefaults.buttonColors(containerColor = if (isSelected) Orange10 else MaterialTheme.colorScheme.surface)
    ) {
        content()
    }
}

@Composable
fun ConnectDogSecondaryButton(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
    textColor: Color = MaterialTheme.colorScheme.primary,
    borderColor: Color = MaterialTheme.colorScheme.primary,
    @StringRes contentRes: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(vertical = 11.dp),
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, borderColor),
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color, contentColor = textColor)
    ) {
        Text(
            text = stringResource(id = contentRes),
            style = Typography.titleSmall,
            color = textColor,
            fontSize = 12.sp
        )
    }
}

@Composable
fun ConnectDogDialogButton(
    onClick: () -> Unit,
    text: String,
    borderColor: Color = Gray5,
    modifier: Modifier = Modifier,
    textColor: Color = Gray4,
    color: Color = Color.White
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, borderColor),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color, contentColor = textColor)
    ) {
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = text,
            style = Typography.titleSmall,
            color = textColor,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_down_triangle),
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(7.dp))
    }
}

@Preview
@Composable
private fun DialogButtonPreview() {
    ConnectDogTheme {
        ConnectDogDialogButton(onClick = { /*TODO*/ }, text = "날짜/기간 선택 ")
    }
}

@Preview
@Composable
private fun BottomButtonPreview() {
    ConnectDogTheme {
        ConnectDogBottomButton(
            onClick = {},
            content = "간편 회원가입하기",
            modifier = Modifier.size(230.dp, 56.dp)
        )
    }
}

@Preview
@Composable
private fun ConnectDogIconButton() {
    ConnectDogTheme {
        ConnectDogIconBottomButton(
            iconId = R.drawable.ic_left,
            contentDescription = "네이버 로그인",
            onClick = {},
            content = "네이버로 계속하기",
            modifier = Modifier.size(230.dp, 56.dp)
        )
    }
}

@Preview
@Composable
private fun OutlinedButton() {
    ConnectDogTheme {
        ConnectDogOutlinedButton(
            width = 114,
            height = 30,
            text = "프로필 사진 선택",
            padding = 10,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun FilledButton() {
    ConnectDogTheme {
        ConnectDogFilledButton(
            width = 45,
            height = 14,
            text = "프로필 보기",
            padding = 1,
            onClick = {}
        )
    }
}
