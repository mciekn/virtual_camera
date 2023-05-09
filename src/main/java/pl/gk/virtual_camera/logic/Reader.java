package pl.gk.virtual_camera.logic;

import javafx.scene.paint.Color;
import pl.gk.virtual_camera.model.Point3D;
import pl.gk.virtual_camera.model.Rectangle3D;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {
    public static ArrayList<Rectangle3D> readRectanglesData(String pathToFile) {
        File rectangleData = new File(pathToFile);
        ArrayList<Rectangle3D> rectangle3DList = new ArrayList<>();
        try (Scanner rectangleDataScanner = new Scanner(rectangleData)) {
            while (rectangleDataScanner.hasNext()) {
                Rectangle3D rectangle3D = new Rectangle3D();
                String line = rectangleDataScanner.nextLine().replace("\\s+", "");
                String[] pointsArray = line.split(";");
                ArrayList<Point3D> rectanglePoints = new ArrayList<>();
                for (String pointCoordinatesString : pointsArray) {
                    String[] pointCoordinatesArray = pointCoordinatesString.split(",");
                    double x = Double.parseDouble(pointCoordinatesArray[0]);
                    double y = Double.parseDouble(pointCoordinatesArray[1]);
                    double z = Double.parseDouble(pointCoordinatesArray[2]);
                    Point3D point3D = new Point3D(x, y, z);

                    rectanglePoints.add(point3D);
                }
                rectangle3D.setPoint3DList(rectanglePoints);
                rectangle3DList.add(rectangle3D);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return rectangle3DList;
    }

    public static ArrayList<Color> readRectanglesColors(String pathToFile) {
        File rectangleData = new File(pathToFile);
        var rectangle3DList = new ArrayList<Color>();
        try (Scanner rectangleDataScanner = new Scanner(rectangleData)) {
            while (rectangleDataScanner.hasNext()) {
                Rectangle3D rectangle3D = new Rectangle3D();
                String line = rectangleDataScanner.nextLine().replace("\\s+", "");
                String[] pointsArray = line.split(";");
                ArrayList<Point3D> rectanglePoints = new ArrayList<>();
                    String[] pointCoordinatesArray = pointsArray[4].split(",");
                    double x = Integer.parseInt(pointCoordinatesArray[0].trim())/255;
                    double y = Integer.parseInt(pointCoordinatesArray[1].trim())/255;
                    double z = Integer.parseInt(pointCoordinatesArray[2].trim())/255;
                    var wallColor = new Color(x, y, z, 1.);
                    rectangle3DList.add(wallColor);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return rectangle3DList;

    }
}