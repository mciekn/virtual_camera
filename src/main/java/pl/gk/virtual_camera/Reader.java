package pl.gk.virtual_camera;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {
    public ArrayList<Rectangle3D> readRectanglesData(){
        File rectangleData = new File("src/main/resources/pl/gk/virtual_camera/data.txt");
        ArrayList<Rectangle3D> rectangle3DList = new ArrayList<>();
        try(Scanner rectangleDataScanner = new Scanner(rectangleData)){
            while (rectangleDataScanner.hasNext()){
                Rectangle3D rectangle3D = new Rectangle3D();
                String line = rectangleDataScanner.nextLine().replace("\\s+", "");
                String[] pointsArray = line.split(";");
                ArrayList<Point3D> rectanglePoints = new ArrayList<>();
                for(String pointCoordinatesString: pointsArray){
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
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return rectangle3DList;
    }

}
