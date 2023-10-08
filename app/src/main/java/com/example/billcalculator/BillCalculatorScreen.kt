package com.example.billcalculator

import android.app.Activity
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillCalculatorScreen(billCalculatorViewModel: BillCalculatorViewModel = viewModel()) {

    val billAmount by billCalculatorViewModel.billAmount.collectAsStateWithLifecycle()
    val tipPercentage by billCalculatorViewModel.tipPercentage.collectAsStateWithLifecycle()
    val numberOfPeople by billCalculatorViewModel.numberOfPeople.collectAsStateWithLifecycle()
    val roundUp by billCalculatorViewModel.roundUp.collectAsStateWithLifecycle()
    val split by billCalculatorViewModel.split.collectAsStateWithLifecycle()
    val bill = billCalculatorViewModel.calculateBill(
        billAmount = billAmount,
        tipPercentage = tipPercentage,
        numberOfPeople = numberOfPeople,
        roundUp = roundUp,
        split = split
    )
    val context = LocalContext.current
    val view = (LocalContext.current as Activity).window.decorView.rootView
    val summary = "${
        context.getString(
            R.string.bill_amount_summary,
            billAmount
        )
    }\n${
        context.getString(
            R.string.tip_percentage_summary,
            tipPercentage
        )
    }\n${
        context.getString(
            R.string.number_of_people_summary,
            numberOfPeople
        )
    }\n${
        context.getString(
            R.string.bill_total,
            bill
        )
    }\n"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(start = 40.dp, end = 40.dp, top = 24.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextFieldInput(
                value = billAmount,
                onValueChanged = { billCalculatorViewModel.onBillAmountChanged(it) },
                label = R.string.bill_amount,
                leadingIcon = Icons.Default.Money,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )
            TextFieldInput(
                value = tipPercentage,
                onValueChanged = { billCalculatorViewModel.onTipPercentageChanged(it) },
                label = R.string.tip_percentage,
                leadingIcon = Icons.Default.Percent,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
            SwitchableOptions(
                additionalOption = R.string.round_bill,
                checked = roundUp,
                onCheckedChanged = { billCalculatorViewModel.onRoundUpChanged(it) }
            )
            SwitchableOptions(
                additionalOption = R.string.split_bill,
                checked = split,
                onCheckedChanged = { billCalculatorViewModel.onSplitChanged(it) }
            )
            if (split) {
                TextFieldInput(
                    value = numberOfPeople,
                    onValueChanged = { billCalculatorViewModel.onNumberOfPeopleChanged(it) },
                    label = R.string.number_of_people,
                    leadingIcon = Icons.Default.PersonAdd,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    )
                )
            }
            Text(
                text = stringResource(id = R.string.bill_total, bill),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.Start)
            )
            Spacer(modifier = Modifier.weight(1f))
            ShareButton(
                onButtonClick = {
                    shareBillAsText(context, summary)
                },
                buttonText = R.string.share_as_text
            )
            Spacer(modifier = Modifier.height(4.dp))
            ShareButton(
                onButtonClick = {
                    val bitmap = captureBillAsScreenshot(view)
                    shareBillAsScreenshot(context, bitmap)
                },
                buttonText = R.string.share_as_image,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldInput(
    value: String,
    onValueChanged: (String) -> Unit,
    @StringRes label: Int,
    leadingIcon: ImageVector,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        singleLine = true,
        label = { Text(text = stringResource(id = label)) },
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = null) },
        keyboardOptions = keyboardOptions,
        modifier = modifier
            .padding(vertical = 16.dp)
    )
}

@Composable
fun SwitchableOptions(
    @StringRes additionalOption: Int,
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = additionalOption))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChanged,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }

}

@Composable
fun ShareButton(
    onButtonClick: () -> Unit,
    @StringRes buttonText: Int,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onButtonClick,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 12.dp)
        )
        Text(
            text = stringResource(id = buttonText),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(end = 16.dp)
        )
    }
}


