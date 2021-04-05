package com.jhbb.notes.base

import androidx.test.espresso.IdlingRegistry
import androidx.test.platform.app.InstrumentationRegistry
import com.jhbb.notes.common.helper.EspressoIdlingResource
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.test.KoinTest
import java.io.BufferedReader
import java.io.Reader

abstract class BaseUITest : KoinTest {
    private lateinit var mockServer: MockWebServer

    abstract fun mockModules(): List<Module>

    @Before
    fun before() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        mockServer = MockWebServer().apply { this.start() }
        loadKoinModules(mockModules())
    }

    @After
    fun after() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        mockServer.shutdown()
        unloadKoinModules(mockModules())
    }

    fun mockWebServerResponse(fileName: String, responseCode: Int) =
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(getJson(fileName))
        )

    fun getMockWebServerUrl() = mockServer.url("").toString()

    private fun getJson(path: String): String {
        var content: String
        val testContext = InstrumentationRegistry.getInstrumentation().context
        val inputStream = testContext.assets.open(path)
        val reader = BufferedReader(inputStream.reader() as Reader?)
        reader.use { content = it.readText() }
        return content
    }
}