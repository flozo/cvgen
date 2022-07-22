package de.flozo.cvgen;

import de.flozo.dto.appearance.ElementStyle;
import de.flozo.dto.content.Address;
import de.flozo.dto.content.LetterContent;
import de.flozo.db.*;
import de.flozo.latex.core.*;

import java.sql.Connection;


public class Main {

    public static void main(String[] args) {


//    Datasource2.INSTANCE.getConnection();

        Datasource2 datasource2 = Datasource2.INSTANCE;
        Connection connection = datasource2.getConnection();

        try {

            LetterContentDAO letterContentDAO = new LetterContentDAOImpl(datasource2,connection);
            LetterContent letterContent = letterContentDAO.get("test");
            System.out.println(letterContent);


            Address receiver = letterContent.getReceiver();
            ContentElement receiverName = new ContentElement(receiver.getFirstName(), receiver.getLastName());
            ContentElement receiverStreet = new ContentElement(receiver.getStreet(), receiver.getHouseNumber());
            ContentElement receiverCity = new ContentElement(receiver.getPostalCode(), receiver.getCity());

            ContentElement addressFieldText = new ContentElement(receiverName.inline(" "), receiverStreet.inline(" "), receiverCity.inline(" "));
            System.out.println(addressFieldText.multiline());

            ElementStyleDAO elementStyleDAO = new ElementStyleDAOImpl(datasource2, connection);
            ElementStyle senderField = elementStyleDAO.get("sender_field");
//            System.out.println(senderField.toString());
//            System.out.println(senderField.getPosition());
//            System.out.println(senderField.getAnchor());

            AddressDAO senderDAO = new AddressDAOImpl(datasource2, connection);
            Address sender = senderDAO.get(2);


            Command documentclass = Documentclass.createWithOptions(DocumentClassName.STANDALONE, "12pt", "tikz", "multi", "crop");

            PackageList packageList = new PackageList(documentclass);
            packageList.add(PackageName.INPUTENC, "utf8")
                    .add(PackageName.FONTENC, "T1")
                    .add(PackageName.BABEL, "german")
                    .add(PackageName.HYPERXMP)
                    .add(PackageName.FIRASANS, "sfdefault", "scaled=1.0098")
                    .add(PackageName.NEWTXSF)
                    .add(PackageName.FONTAWESOME5)
                    .add(PackageName.CSQUOTES, "autostyle=true")
                    .add(PackageName.ENUMITEM)
                    .add(PackageName.MICROTYPE, "activate={true, nocompatibility}", "final", "tracking=true", "kerning=true", "spacing=true", "factor=1100", "stretch=8", "shrink=8")
                    .add(PackageName.TIKZ)
                    .add(PackageName.HYPERREF, "unicode");

            Command usetikzlibrary = new GenericCommand.Builder(CommandName.USETIKZLIBRARY.getString())
                    .body(
                            "positioning",
                            "math",
                            "colorbrewer",
                            "backgrounds",
                            "matrix")
                    .bodyTerminator(StatementTerminator.COMMA)
                    .build();
            Command standaloneenv = new GenericCommand.Builder(CommandName.STANDALONEENV.getString())
                    .body("tikzpicture")
                    .build();


        } finally {
            datasource2.closeConnection();
        }

    }
}
