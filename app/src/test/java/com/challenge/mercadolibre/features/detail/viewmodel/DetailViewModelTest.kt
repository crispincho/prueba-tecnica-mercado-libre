package com.challenge.mercadolibre.features.detail.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.mercadolibre.core.service.RetrofitHandler
import com.challenge.mercadolibre.core.service.entities.Item
import com.challenge.mercadolibre.core.service.entities.Pictures
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
import kotlin.random.Random

class DetailViewModelTest {

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
    fun `fallando en la obtencion de los paises`() {
        val testSingle = Single.error<Item>(Throwable())

        mockkObject(RetrofitHandler)
        every { RetrofitHandler.getItem(any()) } returns testSingle

        val viewModel = DetailViewModel(application)
        viewModel.getDetail(Faker().code.asin())

        assertTrue(viewModel.tittle.value.isNullOrEmpty())
        assertTrue(viewModel.condition.value.isNullOrEmpty())
        assertTrue(viewModel.soldQuantity.value.isNullOrEmpty())
        assertTrue(viewModel.price.value.isNullOrEmpty())
        assertTrue(viewModel.totalPictures.value == null)
        assertFalse(viewModel.loading.value!!)
        assertTrue(viewModel.noConnection.value!!)
    }

    private fun getFakerItem(): Item {
        val faker = Faker()
        return Item(
            id = faker.code.asin(),
            tittle = faker.chiquito.sentences(),
            price = (1..1000000).random().toDouble(),
            thumbnail = faker.address.buildingNumber(),
            soldQuantity = (1..1000000).random(),
            condition = if (Random.nextBoolean()) "new" else "used",
            pictures = listOf()
        )
    }
}