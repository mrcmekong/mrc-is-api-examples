package org.mrcmekong

/**
 * Created by Frank Lieber (liefra) on 2021-10-02.
 */
data class Location(

    val uniqueId: String,

    val locationIdentifier: String,

    val locationCode: String,
    val locationName: String,
    val country: String,

    val countryCode: String,

    val shortName: String?,

    val elevation: Int?,
    val elevationUnits: String?,
    val latitude: Double?,
    val longitude: Double?,

    val river: String?,
    val utcOffset: Int?,

    val tags: String?,

    )
