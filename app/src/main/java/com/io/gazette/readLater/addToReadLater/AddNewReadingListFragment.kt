package com.io.gazette.readLater.addToReadLater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.io.gazette.R
import com.io.gazette.common.ui.components.CloseScreenButton
import com.io.gazette.common.ui.components.GazetteButton
import com.io.gazette.common.ui.components.ScreenTitleText
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddNewReadingListFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModels<AddToReadLaterViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_reading_list, container, false).apply {
            findViewById<ComposeView>(R.id.add_new_reading_list_compose_view).apply {
                setViewCompositionStrategy(
                    ViewCompositionStrategy.DisposeOnLifecycleDestroyed(
                        viewLifecycleOwner
                    )
                )

                setContent {
                    CreateNewReadingListScreenContent(
                        onReadingListTitleChanged = { newTitle ->
                            viewModel.onEvent(
                                ReadLaterListScreenEvent.NewReadLaterListTitleChanged(
                                    newTitle
                                )
                            )
                        },
                        onCreateListButtonClicked = {
                            viewModel.onEvent(ReadLaterListScreenEvent.CreateNewReadLaterList)
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
        onCloseScreenButtonClicked: () -> Unit
    ) {

        var readingListTitle by rememberSaveable {
            mutableStateOf("")
        }

        Surface(modifier = Modifier.padding(bottom = 20.dp)) {

            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                ) {

                    CloseScreenButton {
                        onCloseScreenButtonClicked.invoke()
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    ScreenTitleText(title = "Create List", modifier = Modifier.padding(top = 20.dp))
                }

                Spacer(modifier = Modifier.height(40.dp))

                OutlinedTextField(
                    value = readingListTitle,
                    onValueChange = { newTitle ->
                        readingListTitle = newTitle
                        onReadingListTitleChanged.invoke(newTitle)
                    },
                    label = {
                        Text(text = "Title")
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .align(Alignment.CenterHorizontally)
                )


                Spacer(modifier = Modifier.height(40.dp))


                GazetteButton(
                    buttonTitle = "Create",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { onCreateListButtonClicked.invoke() }
                )

            }


        }
    }


    @Composable
    @Preview(device = "id:pixel_7", showSystemUi = true, showBackground = true)
    fun CreateNewReadingListScreenPreview() {
        CreateNewReadingListScreenContent(
            onReadingListTitleChanged = {},
            onCreateListButtonClicked = {},
            onCloseScreenButtonClicked = {})
    }


}




