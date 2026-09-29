package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class GradeCalculatorTest {

    @Test
    @DisplayName("90 оноо яг A дүн байх ёстой (хязгаарын тохиолдол)")
    void ninetyIsExactlyA() {
        GradeCalculator calc = new GradeCalculator();      // Arrange
        String grade = calc.letterGrade(90.0);             // Act
        assertEquals("A", grade);                          // Assert
    }

    @ParameterizedTest
    @DisplayName("letterGrade: ердийн утгууд ({0} -> {1})")
    @CsvSource({"95,A", "85,B", "75,C", "65,D", "30,F"})
    void letterGradeTypical(double score, String expected) {
        GradeCalculator calc = new GradeCalculator();      // Arrange
        String grade = calc.letterGrade(score);            // Act
        assertEquals(expected, grade);                     // Assert
    }

    @ParameterizedTest
    @DisplayName("letterGrade: хязгаарын утгууд ({0} -> {1})")
    @CsvSource({"100,A", "90,A", "89.99,B", "80,B", "70,C", "60,D", "59.99,F", "0,F"})
    void letterGradeBoundaries(double score, String expected) {
        GradeCalculator calc = new GradeCalculator();      // Arrange
        String grade = calc.letterGrade(score);            // Act
        assertEquals(expected, grade);                     // Assert
    }

    @ParameterizedTest
    @DisplayName("letterGrade: 0-100-аас гадуурх оноо ({0}) exception шидэх ёстой")
    @ValueSource(doubles = {-1, -0.01, 100.01, 101})
    void letterGradeInvalidThrows(double score) {
        GradeCalculator calc = new GradeCalculator();      // Arrange
        assertThrows(IllegalArgumentException.class,       // Act + Assert
                () -> calc.letterGrade(score));
    }

    @Test
    @DisplayName("totalScore: дээд оноонуудын нийлбэр 100 гарна")
    void totalScoreMaxIs100() {
        GradeCalculator calc = new GradeCalculator();      // Arrange
        double total = calc.totalScore(10, 40, 10, 10, 30); // Act
        assertEquals(100.0, total, 0.0001);                // Assert
    }

    @Test
    @DisplayName("totalScore: сөрөг ирц (att = -5) exception шидэх ёстой")
    void totalScoreNegativeAttendanceThrows() {
        GradeCalculator calc = new GradeCalculator();      // Arrange
        assertThrows(IllegalArgumentException.class,       // Act + Assert
                () -> calc.totalScore(-5, 40, 10, 10, 30));
    }

    @Test
    @DisplayName("totalScore: лаб 41 (дээд хязгаараас хэтэрсэн) exception шидэх ёстой")
    void totalScoreLabOverMaxThrows() {
        GradeCalculator calc = new GradeCalculator();      // Arrange
        assertThrows(IllegalArgumentException.class,       // Act + Assert
                () -> calc.totalScore(10, 41, 10, 10, 30));
    }

    @Test
    @DisplayName("totalScore: аль ч оноо дээд хязгаараасаа хэтэрвэл exception шидэх ёстой")
    void totalScoreAnyOverMaxThrows() {
        GradeCalculator calc = new GradeCalculator();      // Arrange
        assertAll(                                         // Act + Assert
                () -> assertThrows(IllegalArgumentException.class, () -> calc.totalScore(11, 40, 10, 10, 30)),
                () -> assertThrows(IllegalArgumentException.class, () -> calc.totalScore(10, 40, 11, 10, 30)),
                () -> assertThrows(IllegalArgumentException.class, () -> calc.totalScore(10, 40, 10, 11, 30)),
                () -> assertThrows(IllegalArgumentException.class, () -> calc.totalScore(10, 40, 10, 10, 31)),
                () -> assertThrows(IllegalArgumentException.class, () -> calc.totalScore(10, -1, 10, 10, 30))
        );
    }

    @ParameterizedTest
    @DisplayName("totalScore: олон утгын нийлбэр зөв гарах ёстой")
    @CsvSource({"10,40,10,10,30,100", "0,0,0,0,0,0", "5,20,5,5,15,50"})
    void totalScoreSums(double att, double lab, double q1, double q2, double exam, double expected) {
        GradeCalculator calc = new GradeCalculator();      // Arrange
        double total = calc.totalScore(att, lab, q1, q2, exam); // Act
        assertEquals(expected, total, 0.0001);             // Assert
    }
}
