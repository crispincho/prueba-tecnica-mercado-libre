package com.challenge.mercadolibre.core.utilities

import org.junit.Assert.*
import org.junit.Test

class UtilitiesTest {

    @Test
    fun `formato de moneda con valores enteros menores a 1000 debe retornar valores sin puntos ni coma`() {
        (1..100).forEach { _ ->
            val price = (0..999).random().toString()
            val expected = "\$ $price"
            assertEquals(
                "no cumplio con el formato requerido: $ ###",
                expected,
                moneyFormat(price.toDouble())
            )
        }
    }

    @Test
    fun `formato de moneda con valores enteros menores a 1000000 a mayores a 1000 debe retornar valores con un punto de separacion despues de los primeros 3 digitos`() {
        (1..100).forEach { _ ->
            val price = (1000..999999).random().toString()
            val expected = "\$ ${
                price.substring(
                    0,
                    price.length - 3
                )
            }.${price.substring(price.length - 3, price.length)}"
            assertEquals(
                "no cumplio con el formato requerido: $ ###.###",
                expected,
                moneyFormat(price.toDouble())
            )
        }
    }

    @Test
    fun `formato de moneda con valores enteros mayores a 1000000 debe retornar valores con 2 puntos de separacion despues de 3 y 6 digitos`() {
        (1..100).forEach { _ ->
            val price = (1000000..999999999).random().toString()
            val expected = "\$ ${price.substring(0, price.length - 6)}.${
                price.substring(
                    price.length - 6,
                    price.length - 3
                )
            }.${price.substring(price.length - 3, price.length)}"
            assertEquals(
                "no cumplio con el formato requerido: $ ###.###.###",
                expected,
                moneyFormat(price.toDouble())
            )
        }
    }

    @Test
    fun `formato de moneda con valores decimales menores a 1000 debe retornar valores sin puntos de separacion y la separacion con coma de decimales mostrando 2 decimales`() {
        (1..100).forEach { _ ->
            var price = "0"
            while (price.last() == '0')
                price = ((1..99999).random().toDouble() / 100).toString()
            val intPrice = price.split(".")[0]
            val decimalPrice = price.split(".")[1]
            val expected = "\$ $intPrice,$decimalPrice"
            assertEquals(
                "no cumplio con el formato requerido: $ ###,##",
                expected,
                moneyFormat(price.toDouble())
            )
        }
    }

    @Test
    fun `formato de moneda con valores decimales menores a 1000000 a mayores a 1000 debe retornar valores sin puntos de separacion y la separacion con coma de decimales mostrando 2 decimales`() {
        (1..100).forEach { _ ->
            var price = "0"
            while (price.last() == '0')
                price = ((100000..9999999).random().toDouble() / 100).toString()
            val intPrice = price.split(".")[0]
            val decimalPrice = price.split(".")[1]

            val expected = "\$ ${
                intPrice.substring(
                    0,
                    intPrice.length - 3
                )
            }.${intPrice.substring(intPrice.length - 3, intPrice.length)}," + decimalPrice
            assertEquals(
                "no cumplio con el formato requerido: $ ###.###,##",
                expected,
                moneyFormat(price.toDouble())
            )
        }
    }

    @Test
    fun `formato de moneda con valores enteros mayores a 1000000 debe retornar valores sin puntos de separacion y la separacion con coma de decimales mostrando 2 decimales`() {
        (1..100).forEach { _ ->
            var price = "0"
            while (price.last() == '0')
                price = "${(1000000..999999999).random()}.${(1..99).random()}"
            val intPrice = price.split(".")[0]
            val decimalPrice = price.split(".")[1]
            val expected = "\$ ${intPrice.substring(0, intPrice.length - 6)}.${
                intPrice.substring(
                    intPrice.length - 6,
                    intPrice.length - 3
                )
            }.${intPrice.substring(intPrice.length - 3, intPrice.length)}," + decimalPrice
            assertEquals(
                "no cumplio con el formato requerido: $ ###.###.###,##",
                expected,
                moneyFormat(price.toDouble())
            )
        }
    }

}