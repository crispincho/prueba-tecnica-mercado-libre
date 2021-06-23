package com.challenge.mercadolibre.features.home.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.mercadolibre.core.service.RetrofitHandler
import com.challenge.mercadolibre.core.service.entities.Site
import io.github.serpro69.kfaker.Faker
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

class HomeViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var application: Application

    @Before
    internal fun setUp() {
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker({ it.run() }, false)
            }

        }
        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }

        application = mockk()
    }

    @Test
    fun `obteniendo los paises exitosamente`() {
        val countiresNumber = 100
        val countriesList = getFakerCountries(countiresNumber)
        val testSingle = Single.just(countriesList)

        mockkObject(RetrofitHandler)
        every { RetrofitHandler.getSites() } returns testSingle


        val viewModel = HomeViewModel(application)
        viewModel.getCountries()

        assertFalse(viewModel.sites.value.isNullOrEmpty())
        assertEquals(countiresNumber, viewModel.sites.value?.size)
        assertFalse(viewModel.loading.value!!)
        assertFalse(viewModel.noConnection.value!!)
    }

    @Test
    fun `fallando en la obtencion de los paises`() {
        val testSingle = Single.error<List<Site>>(Throwable())

        mockkObject(RetrofitHandler)
        every { RetrofitHandler.getSites() } returns testSingle

        val viewModel = HomeViewModel(application)
        viewModel.getCountries()

        assertTrue(viewModel.sites.value.isNullOrEmpty())
        assertFalse(viewModel.loading.value!!)
        assertTrue(viewModel.noConnection.value!!)
    }

    @Test
    fun `organizando la lista de paises por orden alfabetico`() {
        val countiresNumber = 50
        val countriesList = HomeViewModel(application).sortSites(getFakerCountries(countiresNumber))
        countriesList.forEachIndexed { index, site ->
            var grade = 0
            while (true) {
                if (index == countriesList.size - 1 || site.name == countriesList[index + 1].name || site.name[grade] < countriesList[index + 1].name[grade])
                    break
                else if (site.name[grade] == countriesList[index + 1].name[grade])
                    grade++
                else
                    assert(false)
            }
        }
    }

    private fun getFakerCountries(countiresNumber: Int): List<Site> {
        val countries = mutableListOf<Site>()
        val faker = Faker()
        (1..countiresNumber).forEach { _ ->
            countries.add(
                Site(
                    id = faker.code.unique.asin(),
                    name = faker.worldCup.teams(),
                    defaultCurrencyId = faker.code.asin()
                )
            )
        }
        return countries
    }

}