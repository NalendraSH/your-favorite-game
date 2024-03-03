package id.nanz.yourfavoritegame.core.di

import androidx.room.Room
import id.nanz.yourfavoritegame.core.BuildConfig
import id.nanz.yourfavoritegame.core.data.GameRepositoryImpl
import id.nanz.yourfavoritegame.core.data.source.local.LocalDataSource
import id.nanz.yourfavoritegame.core.data.source.local.room.GameDatabase
import id.nanz.yourfavoritegame.core.data.source.remote.RemoteDataSource
import id.nanz.yourfavoritegame.core.data.source.remote.network.ApiService
import id.nanz.yourfavoritegame.core.domain.repository.GameRepository
import id.nanz.yourfavoritegame.core.utils.AppExecutors
import kotlinx.coroutines.Dispatchers
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<GameDatabase>().gameDao() }
    single {
        val passphrase = SQLiteDatabase.getBytes(BuildConfig.ROOM_PW.toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            GameDatabase::class.java, "game-local.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        )
        val certificatePinner = CertificatePinner.Builder()
            .add(BuildConfig.HOST, BuildConfig.CERT_PINNER_1)
            .add(BuildConfig.HOST, BuildConfig.CERT_PINNER_2)
            .add(BuildConfig.HOST, BuildConfig.CERT_PINNER_3)
            .build()
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
    factory { Dispatchers.IO }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get(), get()) }
    factory { AppExecutors() }
    single<GameRepository> { GameRepositoryImpl(get(), get(), get()) }
}