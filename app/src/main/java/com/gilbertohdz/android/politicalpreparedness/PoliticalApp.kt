package com.gilbertohdz.android.politicalpreparedness

import android.app.Application
import com.gilbertohdz.android.politicalpreparedness.election.ElectionsViewModel
import com.gilbertohdz.android.politicalpreparedness.election.VoterInfoViewModel
import com.gilbertohdz.android.politicalpreparedness.representative.RepresentativeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PoliticalApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val mainModule = module {

            //Declare a ViewModel - be later inject into Fragment with dedicated injector using by viewModel()
            viewModel {
                ElectionsViewModel(
                        get()
                )
            }
            viewModel {
                VoterInfoViewModel(
                        get()
                )
            }
            viewModel {
                RepresentativeViewModel(
                        get()
                )
            }
        }

        startKoin {
            androidContext(this@PoliticalApp)
            modules(mainModule)
        }

    }
}