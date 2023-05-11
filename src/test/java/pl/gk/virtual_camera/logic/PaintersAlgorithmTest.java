package pl.gk.virtual_camera.logic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import pl.gk.virtual_camera.model.Rectangle3D;

import java.util.ArrayList;
import java.util.stream.Stream;

class PaintersAlgorithmTest {
    ArrayList<Rectangle3D> testData;
    Processor processor;
    @BeforeEach
    void setUp() {
        testData = Reader.readRectanglesData("src/test/resources/data.txt");
        processor = new Processor(testData);
        processor.changeTranslation(105, Axis.Z);
        processor.changeTranslation(-105, Axis.Y);
    }

    @AfterEach
    void tearDown() {

    }

    @Disabled("For later use")
    @Test
    void quickSort() {

    }

    @Disabled("For later use")
    @Test
    void sortRectangles() {
    }
    @Disabled("For later use")
    @Test
    void runTests() {
    }
    @Disabled("For later use")
    @Test
    void doShapeRectangleBoundsInterfere() {
        //done
        // given
        var q = processor.projectTo2D(testData.get(0));
        var p = processor.projectTo2D(testData.get(1));

        // when
        var result = PaintersAlgorithm.doShapeRectangleBoundsExcludeInterference(q, p);

        // then
        Assertions.assertThat(result).isFalse();
    }
    @Disabled("For later use")
    @Test
    void doShapesInterfere() {
        // given
        var q = processor.projectTo2D(testData.get(0));
        var p = processor.projectTo2D(testData.get(1));
        // when
        var result = PaintersAlgorithm.doShapesExcludeInterference(q, p);
        // then
        Assertions.assertThat(result).isFalse();
    }
    @Disabled("For later use")
    @Test
    void isOnOppositeSide() {
        //given
        var q = testData.get(0);
        var p = testData.get(1);
        //when
        var result = PaintersAlgorithm.isOnOppositeSide(q,p);
        //then
        Assertions.assertThat(result).isFalse();
    }
    @Disabled("For later use")
    @Test
    void isOnSameSide() {
        //given
        var q = testData.get(0);
        var p = testData.get(1);
        //when
        var result = PaintersAlgorithm.isOnSameSide(q,p);
        //then
        Assertions.assertThat(result).isFalse();
    }
}