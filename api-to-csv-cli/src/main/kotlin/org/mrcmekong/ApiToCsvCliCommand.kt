package org.mrcmekong

import io.micronaut.configuration.picocli.PicocliRunner

import picocli.CommandLine.Command
import picocli.CommandLine.Option

@Command(
    name = "api-to-csv-cli", description = ["..."],
    mixinStandardHelpOptions = true
)
class ApiToCsvCliCommand : Runnable {

    @Option(names = ["-v", "--verbose"], description = ["..."])
    private var verbose: Boolean = false

    override fun run() {
        // business logic here
        if (verbose) {
            println("Hi!")
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            PicocliRunner.run(ApiToCsvCliCommand::class.java, *args)
        }
    }
}
