import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UserTest {

    private val user = User(1, "Alice")

    @Test
    fun shouldBeAbleToIncreaseReputation() {
        user.changeReputation(10)

        Assertions.assertEquals(10, user.reputation)
    }

    @Test
    fun shouldBeAbleToDecreaseReputation() {
        user.changeReputation(10)
        user.changeReputation(-5)

        Assertions.assertEquals(5, user.reputation)
    }
}