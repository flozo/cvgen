package de.flozo.cvgen;

import de.flozo.db.*;
import de.flozo.common.dto.appearance.ElementStyle;
import de.flozo.common.dto.content.Address;
import de.flozo.common.dto.content.LetterContent;
import de.flozo.common.dto.latex.DocumentClass;
import de.flozo.common.dto.latex.LatexPackage;
import de.flozo.latex.assembly.PackageList;
import de.flozo.latex.assembly.Preamble;

import java.sql.Connection;
import java.util.List;


public class Main {

    public static void main(String[] args) {


//    Datasource2.INSTANCE.getConnection();

        Datasource2 datasource2 = Datasource2.INSTANCE;
        Connection connection = datasource2.getConnection();

        try {

            LetterContentDAO letterContentDAO = new LetterContentDAOImpl(datasource2, connection);
            LetterContent letterContent = letterContentDAO.get("test");
            System.out.println(letterContent);


            Address receiver = letterContent.getReceiver();
            Address sender = letterContent.getSender();

            ContentElement receiverNameLine = new ContentElement.Builder()
                    .addComponent(receiver.getFirstName())
                    .addComponent(receiver.getLastName())
                    .inlineDelimiter(" ")
                    .build();
            ContentElement receiverStreetLine = new ContentElement.Builder()
                    .addComponent(receiver.getStreet())
                    .addComponent(receiver.getHouseNumber())
                    .inlineDelimiter(" ")
                    .build();
            ContentElement receiverCityLine = new ContentElement.Builder()
                    .addComponent(receiver.getPostalCode())
                    .addComponent(receiver.getCity())
                    .inlineDelimiter(" ")
                    .build();
            ContentElement addressFieldText = new ContentElement.Builder()
                    .addComponent(receiverNameLine.inline())
                    .addComponent(receiverStreetLine.inline())
                    .addComponent(receiverCityLine.inline())
                    .build();
            System.out.println(addressFieldText.multiline());

            ContentElement dateField = new ContentElement.Builder()
                    .addComponent(sender.getCity())
                    .addComponent(letterContent.getDate())
                    .inlineDelimiter(", ")
                    .build();
            System.out.println(dateField.inline());


            ElementStyleDAO elementStyleDAO = new ElementStyleDAOImpl(datasource2, connection);
            ElementStyle senderField = elementStyleDAO.get("sender_field");


//            LatexCommandDAO latexCommandDAO = new LatexCommandDAOImpl(datasource2, connection);
//            LatexCommand documentclass = latexCommandDAO.get("documentclass");
//            LatexCommand usepackage = latexCommandDAO.get("usepackage");
//            LatexCommand usetikzlibrary = latexCommandDAO.get("usetikzlibrary");
//            LatexCommand standaloneenv = latexCommandDAO.get("standaloneenv");
//            LatexCommand node = latexCommandDAO.get("node");
//
//            LatexPackage standard = latexPackageDAO.get("latex_standard");


//            System.out.println(senderField.toString());
//            System.out.println(senderField.getPosition());
//            System.out.println(senderField.getAnchor());

//            AddressDAO senderDAO = new AddressDAOImpl(datasource2, connection);
//            Address sender = senderDAO.get(2);


//            GenericCommand documentclass = Documentclass.createWithOptions(DocumentClassName.STANDALONE, "12pt", "tikz", "multi", "crop");

            DocumentClassDAO documentClassDAO = new DocumentClassDAOImpl(datasource2, connection);
            DocumentClass documentClass = documentClassDAO.getAllIncluded().get(0);

            LatexPackageDAO latexPackageDAO = new LatexPackageDAOImpl(datasource2, connection);
            List<LatexPackage> latexPackages = latexPackageDAO.getAllIncluded();
            PackageList packageList = new PackageList(latexPackages);


            Preamble preamble = Preamble.create(documentClass, packageList);
            for (String line : preamble.getPreambleCode()) {
                System.out.println(line);
            }

//            PackageList packageList = new PackageList(documentclass);
//            packageList.add(PackageName.INPUTENC, "utf8")
//                    .add(PackageName.FONTENC, "T1")
//                    .add(PackageName.BABEL, "german")
//                    .add(PackageName.HYPERXMP)
//                    .add(PackageName.FIRASANS, "sfdefault", "scaled=1.0098")
//                    .add(PackageName.NEWTXSF)
//                    .add(PackageName.FONTAWESOME5)
//                    .add(PackageName.CSQUOTES, "autostyle=true")
//                    .add(PackageName.ENUMITEM)
//                    .add(PackageName.MICROTYPE, "activate={true, nocompatibility}", "final", "tracking=true", "kerning=true", "spacing=true", "factor=1100", "stretch=8", "shrink=8")
//                    .add(PackageName.TIKZ)
//                    .add(PackageName.HYPERREF, "unicode");
//
//            GenericCommand usetikzlibrary = new GenericCommand.Builder(CommandName.USETIKZLIBRARY.getString())
//                    .body(
//                            "positioning",
//                            "math",
//                            "colorbrewer",
//                            "backgrounds",
//                            "matrix")
//                    .bodyTerminator(Delimiter.COMMA)
//                    .build();
//            GenericCommand standaloneenv = new GenericCommand.Builder(CommandName.STANDALONEENV.getString())
//                    .body("tikzpicture")
//                    .build();


        } finally {
            datasource2.closeConnection();
        }

    }
}
