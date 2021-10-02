package org.mrcmekong

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import io.micronaut.configuration.picocli.PicocliRunner
import io.micronaut.http.HttpRequest.GET
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import jakarta.inject.Inject
import picocli.CommandLine
import picocli.CommandLine.Option
import java.time.LocalDate

@CommandLine.Command(
    name = "mrcapi2csv",
    description = ["Download station location data with the help of the MRC-IS API"],
    mixinStandardHelpOptions = true,
    version = ["mrcapi2csv 0.1"],
    exitCodeOnInvalidInput = 2
)
class ApiToCsvCliCommand : Runnable {

    @Inject
    @field:Client("https://api.mrcmekong.org")
    lateinit var client: HttpClient

    @Inject
    lateinit var objectMapper: ObjectMapper

    @Option(names = ["-v", "--verbose"], description = ["..."])
    private var verbose: Boolean = false

    @Option(names = ["-c", "--country"], description = ["Specify a country code"])
    private var countryCode: String? = null

    @Option(
        names = ["-a", "--apikey"],
        description = ["Please specify your apiKey"],
        required = true
    )
    private var apiKey: String? = null

    override fun run() {

        if (verbose) {
            println("Download station location data with the help of the MRC-IS API")
        }

        val url = if (countryCode != null) {
            "/v1/time-series/location?countryCode=$countryCode"
        } else {
            "/v1/time-series/location"
        }

        // Execute GET request using ApiKey as header
        val json = client.toBlocking().retrieve(GET<List<Location>>(url).header("X-API-Key", apiKey!!))

        // Convert JSON to location list
        val locations = objectMapper.readValue<List<Location>>(json)

        writeToCsv(locations)
    }

    private fun writeToCsv(locations: List<Location>) {
        // Create rows for CSV
        val rows = locations.map {
            listOf(
                it.country,
                it.countryCode,
                it.locationCode,
                it.locationName,
                it.locationIdentifier,
                it.uniqueId,
                it.river,
                it.latitude,
                it.longitude
            )
        }

        val fileName = "MRC-station-locations-${LocalDate.now()}.csv"

        csvWriter().open(fileName) {
            writeRow(
                listOf(
                    "Country",
                    "Country Code",
                    "Location Code",
                    "Location Name",
                    "Location Identifier",
                    "UniqueId",
                    "River",
                    "Latitude",
                    "Longitude"
                )
            )
            writeRows(rows)
        }

        println("Done writing ${locations.size} locations to $fileName")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            PicocliRunner.run(ApiToCsvCliCommand::class.java, *args)
        }
    }
}
