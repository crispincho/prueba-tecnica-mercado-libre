package com.challenge.mercadolibre.features.list.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.challenge.mercadolibre.core.service.RetrofitHandler
import com.challenge.mercadolibre.core.service.entities.Item
import com.challenge.mercadolibre.core.service.entities.Search
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

class ListProductsRepositoryTest {

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
    fun `obteniendo los items del la consulta exitosamente`() {
        val search = getFakerSearch()
        val testSingle = Single.just(search)

        mockkObject(RetrofitHandler)
        every { RetrofitHandler.getSeachItems(search.query, search.country) } returns testSingle

        val callback = mockk<ListProductsDataSource.ProductsCallback>()
        every { callback.onSuccess(search.results) } returns Unit
        every { callback.onError(any()) } returns Unit

        ListProductsRepository.getProducts(search.query, search.country, callback)
        verify { callback.onSuccess(search.results) }
    }

    @Test
    fun `fallando en la obtencion de los items`() {
        val message = "Test"
        val testSingle = Single.error<Search>(Throwable(message))

        mockkObject(RetrofitHandler)
        every { RetrofitHandler.getSeachItems(any(), any()) } returns testSingle

        val callback = mockk<ListProductsDataSource.ProductsCallback>()
        every { callback.onSuccess(any()) } returns Unit
        every { callback.onError(message) } returns Unit

        ListProductsRepository.getProducts("", "", callback)
        verify { callback.onError(message) }
    }

    private fun getFakerSearch(): Search {
        val faker = Faker()
        val items = mutableListOf<Item>()
        (0..100).forEach { _ ->
            items.add(getFakerItem())
        }
        return Search(country = faker.worldCup.teams(), query = faker.book.title(), items)
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