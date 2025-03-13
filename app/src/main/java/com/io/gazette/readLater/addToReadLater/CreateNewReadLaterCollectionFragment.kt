package com.io.gazette.readLater.addToReadLater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.io.gazette.common.ui.components.CloseScreenButton
import com.io.gazette.common.ui.components.GazetteButton
import com.io.gazette.common.ui.components.ScreenTitleText
import com.io.gazette.common.ui.theme.GazetteTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateNewReadLaterCollectionFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels<AddToReadLaterViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                GazetteTheme {
                    CreateNewReadingListScreenContent(
                        onReadingListTitleChanged = { newTitle ->
                            viewModel.onEvent(
                                ReadLaterCollectionScreenEvent.NewReadLaterListTitleChanged(
                                    newTitle
                                )
                            )
                        },
                        onCreateListButtonClicked = {
                            viewModel.onEvent(ReadLaterCollectionScreenEvent.CreateNewReadLaterList)
                            closeFragment()
                        },
                        onCloseScreenButtonClicked = {
                            closeFragment()
                        }
                    )
                }

            }
        }
    }

    private fun closeFragment() {
        findNavController().popBackStack()
    }


    @Composable
    fun CreateNewReadingListScreenContent(
        onReadingListTitleChanged: (title: String) -> Unit,
        onCreateListButtonClicked: () -> Unit,
        onCloseScreenButtonClicked: () -> Unit,
        modifier: Modifier = Modifier
            .fillMaxWidth()
    ) {

        var readingListTitle by rememberSaveable {
            mutableStateOf("")
        }
        Column (modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally){
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.80f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .align(Alignment.Start)
                ) {

                    CloseScreenButton { onCloseScreenButtonClicked.invoke() }
                    Spacer(modifier = Modifier.weight(1f))
                    ScreenTitleText(title = "Create Collection")
                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(40.dp))

                OutlinedTextField(
                    value = readingListTitle,
                    maxLines = 1,
                    onValueChange = { newTitle ->
                        readingListTitle = newTitle
                        onReadingListTitleChanged.invoke(newTitle)
                    },
                    label = {
                        Text(text = "Title")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(40.dp))

                GazetteButton(
                    buttonTitle = "Create",
                    enabled = readingListTitle.isNotBlank(),
                    modifier = Modifier.fillMaxWidth(),

                    onClick = { onCreateListButtonClicked.invoke() }
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }


    @Composable
    @PreviewLightDark
    fun CreateNewReadingListScreenPreview() {
        GazetteTheme {
            CreateNewReadingListScreenContent(
                onReadingListTitleChanged = {},
                onCreateListButtonClicked = {},
                onCloseScreenButtonClicked = {}
            )
        }
    }
}




