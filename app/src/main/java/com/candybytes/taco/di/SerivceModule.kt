package com.candybytes.taco.di

import android.content.Context
import com.candybytes.taco.R
import com.candybytes.taco.api.TacoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber


@Module
@InstallIn(ActivityComponent::class)
object ServiceModule {

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor(
        object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.i(message)
            }
        }
    ).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideBaseInterceptorOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /**
     * Provides [TacoService] REST API
     */
    @Provides
    fun provideTacoService(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient,
    ): TacoService {
        return Retrofit.Builder()
            .baseUrl(context.getString(R.string.taco_service_host))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TacoService::class.java)
    }
}
