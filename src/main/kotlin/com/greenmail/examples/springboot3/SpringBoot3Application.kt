package com.greenmail.examples.springboot3

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
open class SpringBoot3Application

fun main(args: Array<String>) {
    runApplication<SpringBoot3Application>(*args)
}