package com.example

import io.micronaut.configuration.picocli.PicocliRunner
import io.micronaut.context.ApplicationContext
import io.micronaut.context.env.Environment
import org.mrcmekong.ApiToCsvCliCommand
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class ApiToCsvCliCommandSpec extends Specification {

    @Shared
    @AutoCleanup
    ApplicationContext ctx = ApplicationContext.run(Environment.CLI, Environment.TEST)

    void "test api-to-csv-cli with command line option"() {
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        System.setOut(new PrintStream(baos))

        String[] args = ['-v'] as String[]
        PicocliRunner.run(ApiToCsvCliCommand, ctx, args)

        expect:
        baos.toString().contains('Hi!')
    }
}

