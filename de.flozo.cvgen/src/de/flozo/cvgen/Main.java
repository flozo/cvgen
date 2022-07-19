package de.flozo.cvgen;

import de.flozo.common.appearance.PredefinedLineWidth;
import de.flozo.db.Datasource2;
import de.flozo.db.PredefinedLineWidthDAO;
import de.flozo.db.PredefinedLineWidthDAOImpl;

public class Main {

    public static void main(String[] args) {


        try {

//            AddressDAO addressDAO = new AddressDAOImpl();
//
//            addressDAO.showMetadata();
//            System.out.println(addressDAO.getCount());
//            System.out.println(addressDAO.get(1));
//
//
//            for (Address address : addressDAO.getAll()) {
//                System.out.println(address);
//            }

//            Address address = addressDAO.get(4);
//            System.out.println(address);
//            addressDAO.delete(address);


//            Address address = new Address(0,"new person", "Prof.","John", "", "Smith", "Main street", "1", "23570", "City", "Country",
//                    "1357924680", "09876", "address@mail.com", "www.test.org");
//            addressDAO.add(address);

            PredefinedLineWidthDAO dashPatternDAO = new PredefinedLineWidthDAOImpl();
//            Length newLength = new Length(0, "my_length", 50.55, new LengthUnit(4, "centimeter", "cm"));
//            lengthDAO.add(newLength);
//            Length myLength = lengthDAO.get("my_length");
//            lengthDAO.delete(myLength);
            for (PredefinedLineWidth dashPattern : dashPatternDAO.getAll()) {
                System.out.println(dashPattern);
            }


        } finally {
            Datasource2.INSTANCE.closeConnection();
        }

    }
}
