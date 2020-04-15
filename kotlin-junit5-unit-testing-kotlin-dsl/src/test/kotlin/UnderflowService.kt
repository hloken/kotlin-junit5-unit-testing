@file:Suppress("MemberVisibilityCanBePrivate")

package com.rocksolidknowledge.stackunderflow

import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.amshove.kluent.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.lang.Exception

class UnderflowServiceTest {

    @Nested
    inner class UnderflowService {
        val mockQuestionRepository = mockk<IQuestionRepository>()
        var mockUserRepository = mockk<IUserRepository>(relaxUnitFun = true)

        val service = UnderflowService(mockQuestionRepository, mockUserRepository)

        val questionId = 20
        val voterId = 10

        @Test
        fun `should be able to initialise service`() {
            service.shouldNotBeNull()
        }

        @Test
        fun `should be able to vote up question`() {
            val user = User(1, "Alice")
            val question = Question(questionId, user, "title", "question")

            user.changeReputation(3000)

            question.voteUp()
            question.voteUp()

            every { mockQuestionRepository.findQuestion(questionId) } returns question
            every { mockUserRepository.findUser(voterId) } returns user
            every { mockUserRepository.findUser(question.userId) } returns user
            every { mockQuestionRepository.update(question) } just Runs

            val votes = service.voteUpQuestion(questionId, voterId)

            votes `should be` 3
        }

        @Test
        fun `should throw an exception if the question id is invalid`() {
            val user = User(1, "Alice")
            val question = Question(2, user, "title", "question")

            every { mockQuestionRepository.findQuestion(questionId) } throws Exception()

            invoking { service.voteUpQuestion(questionId, voterId) } `should throw` ServiceException::class
        }
    }

    @Nested
    inner class WithAnnotations {

        @MockK
        lateinit var mockQuestionRepository : IQuestionRepository

        @RelaxedMockK
        lateinit var mockUserRepository : IUserRepository

        init {
            MockKAnnotations.init(this)
        }

        val service = UnderflowService(mockQuestionRepository, mockUserRepository)

        val questionId = 20
        val voterId = 10

        @Test
        fun `should be able to vote up question`() {
            val user = User(1, "Alice")
            val question = Question(questionId, user, "title", "question")

            user.changeReputation(3000)

            question.voteUp()
            question.voteUp()

            every { mockQuestionRepository.findQuestion(questionId) } returns question
            every { mockUserRepository.findUser(voterId) } returns user
            every { mockUserRepository.findUser(question.userId) } returns user
            every { mockQuestionRepository.update(question) } just Runs

            val votes = service.voteUpQuestion(questionId, voterId)

            votes `should be` 3
        }
    }

    @Nested
    @ExtendWith(MockKExtension::class)
    inner class WithAnnotationsUsingJUnit {

        @MockK
        lateinit var mockQuestionRepository : IQuestionRepository

        @RelaxedMockK
        lateinit var mockUserRepository : IUserRepository

        val questionId = 20
        val voterId = 10

        @Test
        fun `should be able to vote up question`() {
            val service = UnderflowService(mockQuestionRepository, mockUserRepository)
            val user = User(1, "Alice")
            val question = Question(questionId, user, "title", "question")

            user.changeReputation(3000)

            question.voteUp()
            question.voteUp()

            every { mockQuestionRepository.findQuestion(questionId) } returns question
            every { mockUserRepository.findUser(voterId) } returns user
            every { mockUserRepository.findUser(question.userId) } returns user
            every { mockQuestionRepository.update(question) } just Runs

            val votes = service.voteUpQuestion(questionId, voterId)

            votes `should be` 3
        }
    }
}