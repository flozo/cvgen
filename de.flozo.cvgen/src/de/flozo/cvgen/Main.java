package de.flozo.cvgen;

import de.flozo.db.AddressDAO;
import de.flozo.db.AddressDAOImpl;
import de.flozo.db.Datasource2;

public class Main {

    public static void main(String[] args) {

//        Datasource.getInstance().open();
//        Datasource.getInstance().getConnection();

//        Datasource2.INSTANCE.getConnection();
        try {

//            System.out.println(Datasource.getInstance().addressById(1));
//            System.out.println(Datasource.getInstance().textStyleById(2));
//            System.out.println(Datasource.getInstance().anchorById(4));
//            System.out.println(Datasource.getInstance().lineStyleById(2));
            AddressDAO addressDAO = new AddressDAOImpl();

            System.out.println(addressDAO.get(1));


//            for (Address address: addressDAO.getAll()) {
//                System.out.println(address);
//            }
        } finally {
            Datasource2.INSTANCE.closeConnection();
        }

    }
}
