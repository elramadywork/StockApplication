package com.example.socketmarket.presentation.company_listings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.socketmarket.domain.model.CompanyListing
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.CompanyInfoScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Destination<RootGraph>(start = true)
@Composable
fun CompanyListingsScreen(
    navigator: DestinationsNavigator,
    viewModel: CompanyListingsViewModel = hiltViewModel()
) {
    val swipeRefreshState = rememberPullToRefreshState()
    val state = viewModel.state
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(
                    CompanyListingsEvent.OnSearchQueryChange(it)
                )
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search...")
            },
            maxLines = 1,
            singleLine = true
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            PullToRefreshLazyColumn(
                items = state.companies,
                content = { i ->
                    val company = state.companies[i]
                    CompanyItem(
                        company = company as CompanyListing,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navigator.navigate(
                                    CompanyInfoScreenDestination(symbol = company.symbol)
                                )
                            }
                            .padding(16.dp)
                    )
                    if(i < state.companies.size) {
                        Divider(modifier = Modifier.padding(
                            horizontal = 16.dp
                        ))
                    }


                },
                isRefreshing = state.isRefreshing,
                onRefresh = {
                    viewModel.onEvent(CompanyListingsEvent.Refresh)
                }
            )


            ///////////////////////////////////

//            LazyColumn(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                items(state.companies.size) { i ->
//                    val company = state.companies[i]
//                    CompanyItem(
//                        company = company,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clickable {
////                                navigator.navigate(
////                                  //  CompanyInfoScreenDestination(company.symbol)
////                                )
//                            }
//                            .padding(16.dp)
//                    )
//                    if(i < state.companies.size) {
//                        Divider(modifier = Modifier.padding(
//                            horizontal = 16.dp
//                        ))
//                    }
//                }
//            }

        }
    }
}