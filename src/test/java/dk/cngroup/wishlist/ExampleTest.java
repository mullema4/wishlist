package dk.cngroup.wishlist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExampleTest {

    @Test
    void oneIncrementedByOneShouldBeTwo() {
        int a = 1;

        a++;

        assertEquals(2, a);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 4, 3",
            "11, -5, 6",
            "71, 12, 83"
    })
    void xPlusYShouldBeZ(int x, int y, int z) {
        assertEquals(z, x + y);
    }
}
