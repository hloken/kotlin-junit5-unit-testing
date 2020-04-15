package com.rocksolidknowledge.stackunderflow

import org.amshove.kluent.*
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
            invoking { Question(1, user, "", "question") } `should throw` QuestionException::class
        }

        @Test
        fun `throw an exception if the question is empty`() {
            invoking { Question(1, user, "title", "") } `should throw` QuestionException::class
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
            invoking { Question(1, user, title, question) } `should throw` QuestionException::class
        }
    }

    @Nested
    inner class answers {

        val user = User(1, "Alice")
        val question = Question(1, user, "title", "question")

        @Test
        fun `should have no answer`() {
            question.answers.shouldBeEmpty()
        }

        @Test
        fun `should have an answer`() {
            val answer = Answer(1, user, "answer")
            question.addAnswer(answer)

            question.answers.shouldNotBeEmpty()
        }

        @Test
        fun `should contain an answer`() {
            val answer = Answer(1, user, "answer")
            question.addAnswer(answer)

            question.answers `should contain` answer
        }

        @Test
        fun `should contain an answer if two are added`() {
            val answer1 = Answer(1, user, "answer")
            question.addAnswer(answer1)
            val answer2 = Answer(2, user, "answer")
            question.addAnswer(answer2)

            question.answers `should contain` answer1
        }

        @Test
        fun `should not contain an answer that was not added`() {
            val answer1 = Answer(1, user, "answer")
            question.addAnswer(answer1)
            val answer2 = Answer(2, user, "answer")

            question.answers `should not contain` answer2
        }
    }
}