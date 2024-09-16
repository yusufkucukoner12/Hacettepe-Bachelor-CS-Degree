import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Class representing the mission of Genesis
public class MissionGenesis {

    private MolecularData molecularDataHuman; 
    private MolecularData molecularDataVitales; 

    public MolecularData getMolecularDataHuman() {
        return molecularDataHuman;
    }

    public MolecularData getMolecularDataVitales() {
        return molecularDataVitales;
    }

    
    public void readXML(String filename) {
        try {
            File inputFile = new File(filename);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(inputFile);
            doc.getDocumentElement().normalize();
            molecularDataHuman = processMolecularData(doc.getElementsByTagName("HumanMolecularData"));
            molecularDataVitales = processMolecularData(doc.getElementsByTagName("VitalesMolecularData"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MolecularData processMolecularData(NodeList structureNodes) {
        List<Molecule> molecules = new ArrayList<>();
        for (int i = 0; i < structureNodes.getLength(); i++) {
            Element ekement = (Element) structureNodes.item(i);
            NodeList nodes = ekement.getElementsByTagName("Molecule");
            for (int j = 0; j < nodes.getLength(); j++) {
                Element element = (Element) nodes.item(j);
                String id = element.getElementsByTagName("ID").item(0).getTextContent();
                int strength = Integer.parseInt(element.getElementsByTagName("BondStrength").item(0).getTextContent());
                NodeList bondNodes = element.getElementsByTagName("MoleculeID");
                List<String> bondid = new ArrayList<>();
                for (int k = 0; k < bondNodes.getLength(); k++) {
                    bondid.add(bondNodes.item(k).getTextContent());
                }
                molecules.add(new Molecule(id, strength, bondid));
            }
        }
        return new MolecularData(molecules);
    }
}
