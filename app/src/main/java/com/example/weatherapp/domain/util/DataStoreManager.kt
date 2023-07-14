package com.example.weatherapp.domain.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


const val LOCATION_DATASTORE ="location_datastore"
private val Context.dataStore : DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(LOCATION_DATASTORE)

class DataStoreManager (private val context: Context) {

    companion object {
        val LATITUDE = doublePreferencesKey("LATITUDE")
        val LONGITUDE = doublePreferencesKey("LONGITUDE")
    }

    suspend fun saveToDataStore(locationDetail: LocationDetail) {
        context.dataStore.edit {
            it[LATITUDE] = locationDetail.latitude
            it[LONGITUDE] = locationDetail.longitude

        }
    }

    fun getFromDataStore() = context.dataStore.data.map {
        LocationDetail(
            latitude = it[LATITUDE] ?: 41.015137,
            longitude = it[LONGITUDE] ?: 28.979530
        )
    }

    suspend fun clearDataStore() = context.dataStore.edit {
        it.clear()
    }

}

data class LocationDetail(
    val latitude: Double ,
    val longitude : Double
)


/*
class DataStoreManager(
    private val context : Context
){

    companion object{

        private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("location")
        val LATITUDE = stringPreferencesKey("LATITUDE")
        val LONGITUDE = stringPreferencesKey("LONGITUDE")
    }

    //to get Email
    val getFromDataStore: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[LATITUDE] ?: ""
            preferences[LONGITUDE] ?: ""
        }

    //to save email to datastore
    suspend fun saveToDataStore(lat: String, lon: String){
        context.dataStore.edit { preferences ->
            preferences[LATITUDE] = lat
            preferences[LONGITUDE] = lon
        }
    }
}
*/
