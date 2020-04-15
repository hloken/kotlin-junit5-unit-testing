package com.rocksolidknowledge.stackunderflow

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource

@Suppress("ClassName")

class QuestionTest {

    private val user = User(1, "Alice")

    @Nested
    inner class `constructor should` {

        @Test
        fun `throw an exception if the 'title' is empty`() {
            Assertions.assertThrows(QuestionException::class.java) {
                Question(1, user, "", "question")
            }
        }

        @Test
        fun `throw an exception if the question is empty`() {
            Assertions.assertThrows(QuestionException::class.java) {
                Question(1, user, "title", "")
            }
        }

        @Test
        fun `not throw an exception if the question is valid`() {
            Assertions.assertDoesNotThrow {
                Question(1, user, "title", "question")
            }
        }

        @ParameterizedTest
        @CsvSource(
            "' ', question",
            "'', question",
            "title, ' '",
            "title, ''")
        fun `throw and exception if title or question is invalid`(title: String, question: String) {
            Assertions.assertThrows(QuestionException::class.java) {
                Question(1, user, title, question)
            }
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class `constructor should with method source` {

        @Suppress("unused")
        fun titlesAndQuestions() = listOf(
            Arguments.of("", "question"),
            Arguments.of(" ", "question"),
            Arguments.of("title", ""),
            Arguments.of("title", " ")
        )

        @ParameterizedTest
        @MethodSource("titlesAndQuestions")
        fun `throw an exception if title or question is invalid`(title: String, question: String) {
            Assertions.assertThrows(QuestionException::class.java) {
                Question(1, user, title, question)
            }
        }
    }

    @Nested
    @KotlinParameterizedTests
    inner class `constructor should with method source and annotation` {

        @Suppress("unused")
        fun titlesAndQuestions() = listOf(
            Arguments.of("", "question"),
            Arguments.of(" ", "question"),
            Arguments.of("title", ""),
            Arguments.of("title", " ")
        )

        @ParameterizedTest
        @MethodSource("titlesAndQuestions")
        fun `throw an exception if title or question is invalid`(title: String, question: String) {
            Assertions.assertThrows(QuestionException::class.java) {
                Question(1, user, title, question)
            }
        }
    }
}