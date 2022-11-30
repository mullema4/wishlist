package dk.cngroup.wishlist

import spock.lang.Specification

// MUST extend Specification
class ExampleSpec extends Specification {

    // standard Spock test
    def '1 incremented by 1 should be 2'() {
        given:
        def a = 1

        when:
        a++

        then:
        a == 2
    }

    // parameterized test
    def '#x plus #y should be #z'() {
        expect:
        x + y == z

        where:
        x  | y  || z
        -1 | 4  || 3
        11 | -5 || 6
        71 | 12 || 83
    }

}