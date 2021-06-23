package com.challenge.mercadolibre.features.detail.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.mercadolibre.core.service.RetrofitHandler
import com.challenge.mercadolibre.core.service.entities.Item
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
import kotlin.random.Random

class DetailRepositoryTest {

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
    fun `obteniendo el item exitosamente`() {
        val item = getFakerItem()
        val testSingle = Single.just(item)

        mockkObject(RetrofitHandler)
        every { RetrofitHandler.getItem(item.id) } returns testSingle

        val callback = mockk<DetailDataSource.ProductCallback>()
        every { callback.onSuccess(any()) } returns Unit
        every { callback.onError(any()) } returns Unit

        DetailRepository.getItemDetail(item.id, callback)
        verify { callback.onSuccess(item) }
    }

    @Test
    fun `fallando en la obtencion del item`() {
        val message = "Test"
        val testSingle = Single.error<Item>(Throwable(message))

        mockkObject(RetrofitHandler)
        every { RetrofitHandler.getItem(any()) } returns testSingle

        val callback = mockk<DetailDataSource.ProductCallback>()
        every { callback.onSuccess(any()) } returns Unit
        every { callback.onError(any()) } returns Unit

        DetailRepository.getItemDetail(Faker().code.asin(), callback)
        verify { callback.onError(message) }
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