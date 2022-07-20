package de.flozo.cvgen;

import de.flozo.db.Datasource2;
import de.flozo.db.TextStyleDAO;
import de.flozo.db.TextStyleDAOImpl;

import java.sql.Connection;


public class Main {

    public static void main(String[] args) {


//    Datasource2.INSTANCE.getConnection();

        Datasource2 datasource2 = Datasource2.INSTANCE;
        Connection connection = datasource2.getConnection();

        try {


            TextStyleDAO textStyleDAO = new TextStyleDAOImpl(datasource2, connection);
            System.out.println(textStyleDAO.get("backaddress"));
//            Position position = positionDAO.get("new position");
//            System.out.println(position);

//            LengthDAO lengthDAO = new LengthDAOImpl(datasource2, connection);
//            System.out.println(lengthDAO.get("marks_x"));
//            Length newLength = lengthDAO.get("sender_width");
//            position.setLengthX(newLength);


//            positionDAO.update(position);"

//            Position newPosition = positionDAO.get("sender5");
//            newPosition.setName("sender5");


//            newPosition.setLengthY(newLength);
//
//            positionDAO.update(newPosition);


        } finally {
            datasource2.closeConnection();
        }

    }
}
