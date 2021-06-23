package com.challenge.mercadolibre.features.home.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.mercadolibre.core.service.RetrofitHandler
import com.challenge.mercadolibre.core.service.entities.Site
import io.github.serpro69.kfaker.Faker
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit

class HomeRepositoryTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

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
    }

    @Test
    fun `obteniendo los paises exitosamente`() {
        val countiresNumber = 100
        val countriesList = getFakerCountries(countiresNumber)
        val testSingle = Single.just(countriesList)

        mockkObject(RetrofitHandler)
        every { RetrofitHandler.getSites() } returns testSingle

        val callback = mockk<HomeDataSource.SitesCallback>()
        every { callback.onSuccess(any()) } returns Unit
        every { callback.onError(any()) } returns Unit

        HomeRepository.getSites(callback)
        verify { callback.onSuccess(countriesList) }
    }

    @Test
    fun `fallando en la obtencion de los paises`() {
        val message = "Test"
        val testSingle = Single.error<List<Site>>(Throwable(message))

        mockkObject(RetrofitHandler)
        every { RetrofitHandler.getSites() } returns testSingle

        val callback = mockk<HomeDataSource.SitesCallback>()
        every { callback.onSuccess(any()) } returns Unit
        every { callback.onError(any()) } returns Unit

        HomeRepository.getSites(callback)
        verify { callback.onError(message) }
    }
    
    private fun getFakerCountries(@Suppress("SameParameterValue") countiresNumber: Int): List<Site> {
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