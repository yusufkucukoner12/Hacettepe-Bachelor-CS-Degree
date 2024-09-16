import java.util.*;

public class MolecularData {

    private final List<Molecule> molecules;


    public MolecularData(List<Molecule> molecules) {
        this.molecules = molecules;
    }

    public List<Molecule> getMolecules() {
        return molecules;
    }


    public List<MolecularStructure> identifyMolecularStructures() {
        List<List<Molecule>> connectedComponents = findConnectedComponents();
        List<MolecularStructure> structures = new ArrayList<>();

        for (List<Molecule> component : connectedComponents) {
            MolecularStructure structure = new MolecularStructure();
            for (Molecule molecule : component) {
                structure.addMolecule(molecule);
            }
            structures.add(structure);
        }

        return structures;
    }
    private List<List<Molecule>> findConnectedComponents() {
        List<List<Molecule>> connectedComponents = new ArrayList<>();
        Map<Molecule, Boolean> visited = new HashMap<>();
        
        for (Molecule molecule : molecules) {
            if (!visited.containsKey(molecule) || !visited.get(molecule)) {
                List<Molecule> connectedComponent = new ArrayList<>();
                dfs(molecule, visited, connectedComponent);
                connectedComponents.add(connectedComponent);
            }
        }
    
        return connectedComponents;
    }
    
    private void dfs(Molecule molecule, Map<Molecule, Boolean> visited, List<Molecule> connectedComponent) {
        visited.put(molecule, true);
        connectedComponent.add(findMoleculeByID(molecule.getId()));
    
        for (String bondID : molecule.getBonds()) {
            Molecule bondedMolecule = findMoleculeByID(bondID);
            if (bondedMolecule != null && (!visited.containsKey(bondedMolecule) || !visited.get(bondedMolecule))) {
                dfs(bondedMolecule, visited, connectedComponent);
            }
        }
    
        for (Molecule m : molecules) {
            if (m.getBonds().contains(molecule.getId()) && (!visited.containsKey(m) || !visited.get(m))) {
                dfs(m, visited, connectedComponent);
            }
        }
    }

    public static ArrayList<MolecularStructure> getVitalesAnomaly(List<MolecularStructure> sourceStructures, List<MolecularStructure> targetStructures) {
        ArrayList<MolecularStructure> anomalyList = new ArrayList<>(targetStructures);
        anomalyList.removeAll(sourceStructures);
        return anomalyList;
    }
    private Molecule findMoleculeByID(String moleculeID) {
        for (Molecule molecule : molecules) {
            if (molecule.getId().equals(moleculeID)) {
                return molecule;
            }
        }
        return null;
    }

    
    public void printVitalesAnomaly(List<MolecularStructure> molecularStructures) {
        System.out.println("Molecular structures unique to Vitales individuals:");
        for (MolecularStructure structure : molecularStructures) {
            System.out.println(structure);
        }
    }

    
    public void printMolecularStructures(List<MolecularStructure> molecularStructures, String species) {
        System.out.println(molecularStructures.size() +" molecular structures have been discovered in " + species + "." );
        for (int i = 0; i < molecularStructures.size(); i++) {
            System.out.println("Molecules in Molecular Structure " + (i + 1) + ": " + molecularStructures.get(i));
        }
    }
}
