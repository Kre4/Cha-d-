package ru.kre4.chad.model

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.flow
import ru.kre4.chad.api.ChadApiFactory

@Stable
class ChannelsViewModel : ViewModel() {

    private val api = ChadApiFactory.clientInstance

    val channels = flow {
        emit(api.getChannels())
    }
}