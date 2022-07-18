package de.flozo.cvgen;

import de.flozo.common.content.Address;
import de.flozo.db.*;

public class Main {

    public static void main(String[] args) {


        try {

            AddressDAO addressDAO = new AddressDAOImpl();

            addressDAO.showMetadata();
            System.out.println(addressDAO.getCount());
            System.out.println(addressDAO.get(1));



            for (Address address : addressDAO.getAll()) {
                System.out.println(address);
            }

//            Address address = addressDAO.get(4);
//            System.out.println(address);
//            addressDAO.delete(address);


//            Address address = new Address(0,"new person", "Prof.","John", "", "Smith", "Main street", "1", "23570", "City", "Country",
//                    "1357924680", "09876", "address@mail.com", "www.test.org");
//            addressDAO.add(address);
            Address address1 = addressDAO.get(3);
            address1.setLastName("Fisher");
            addressDAO.update(address1);
            AnchorDAO anchorDAO = new AnchorDAOImpl();
            System.out.println(anchorDAO.get("center"));


        } finally {
            Datasource2.INSTANCE.closeConnection();
        }

    }
}
