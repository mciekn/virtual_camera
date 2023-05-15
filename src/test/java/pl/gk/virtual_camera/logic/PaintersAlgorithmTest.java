package pl.gk.virtual_camera.logic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.gk.virtual_camera.model.Rectangle3D;

import java.util.ArrayList;

class PaintersAlgorithmTest {
    ArrayList<Rectangle3D> testData;
    Processor processor;
    @BeforeEach
    void setUp() {
        testData = Reader.readRectanglesData("src/test/resources/data.txt");
        processor = new Processor(testData);
        processor.changeTranslation(105, Axis.Z);
        processor.changeTranslation(-105, Axis.Y);
        for(Rectangle3D rectangle3D : testData){
            rectangle3D.setProcessor(processor);
        }
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void doShapeRectangleBoundsInterfere() {
        //done
        // given
        var q = processor.projectTo2D(testData.get(0));
        var p = processor.projectTo2D(testData.get(1));


    }
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

    @Test
    void isOnSameSide() {
        //given
        var q = testData.get(0);
        var p = testData.get(1);

        //when
        var result = ObstructionDetector.isPointOnObserverSide(q, p.getCenter());

        //then
        Assertions.assertThat(result).isEqualTo(1);
    }
}