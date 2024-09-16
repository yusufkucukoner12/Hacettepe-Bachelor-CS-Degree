import java.util.*;

public class MissionSynthesis {

    private final List<MolecularStructure> humanStructures; 
    private final ArrayList<MolecularStructure> diffStructures; 

    public MissionSynthesis(List<MolecularStructure> humanStructures, ArrayList<MolecularStructure> diffStructures) {
        this.humanStructures = humanStructures;
        this.diffStructures = diffStructures;
    }

    public List<Bond> synthesizeSerum() {
        List<Bond> serum = new ArrayList<>();

        ArrayList<Molecule> selectedHumanMolecules = selectLowestBondStrengthMolecules(humanStructures);
        ArrayList<Molecule> selectedVitalesMolecules = selectLowestBondStrengthMolecules(diffStructures);

        ArrayList<Bond> edges = new ArrayList<>();

        for (int i = 0; i < selectedHumanMolecules.size(); i++) {
            for (int j = i + 1; j < selectedHumanMolecules.size(); j++) {
                double bondStrength = calculateBondStrength(selectedHumanMolecules.get(i), selectedHumanMolecules.get(j));
                edges.add(new Bond(selectedHumanMolecules.get(i), selectedHumanMolecules.get(j), bondStrength));
            }
            Molecule humanMolecule = selectedHumanMolecules.get(i);
            for (Molecule vitalesMolecule : selectedVitalesMolecules) {
                double bondStrength = calculateBondStrength(humanMolecule, vitalesMolecule);
                edges.add(new Bond(vitalesMolecule, humanMolecule, bondStrength));
            }
        }
        for (int i = 0; i < selectedVitalesMolecules.size(); i++) {
            for (int j = i + 1; j < selectedVitalesMolecules.size(); j++) {
                double bondStrength = calculateBondStrength(selectedVitalesMolecules.get(i), selectedVitalesMolecules.get(j));
                edges.add(new Bond(selectedVitalesMolecules.get(j), selectedVitalesMolecules.get(i), bondStrength));
            }
        }
        Collections.sort(edges, Comparator.comparingDouble(Bond::getWeight));

        Set<Molecule> visited = new HashSet<>();
        for (Bond edge : edges) {
            if (!visited.contains(edge.getFrom()) || !visited.contains(edge.getTo())) {
                serum.add(edge);
                visited.add(edge.getFrom());
                visited.add(edge.getTo());
            }
        }

        return serum;
    }

    private ArrayList<Molecule> selectLowestBondStrengthMolecules(List<MolecularStructure> structures) {
        ArrayList<Molecule> selectedMolecules = new ArrayList<>();
        for (MolecularStructure structure : structures) {
            Molecule lowestBondMolecule = structure.getMoleculeWithWeakestBondStrength();
            selectedMolecules.add(lowestBondMolecule);
        }
        return selectedMolecules;
    }

    private double calculateBondStrength(Molecule molecule1, Molecule molecule2) {
        return (molecule1.getBondStrength() + molecule2.getBondStrength()) / 2.0;
    }

    public void printSynthesis(List<Bond> serum) {
        double totalBondStrength = 0.0;


        System.out.print("Typical human molecules selected for synthesis: [");
        for (MolecularStructure structure : humanStructures) {
            System.out.print(structure.getMoleculeWithWeakestBondStrength().getId() + ", ");
        }
        System.out.println("\b\b]");

        System.out.print("Vitales molecules selected for synthesis: [");
        for (MolecularStructure structure : diffStructures) {
            System.out.print(structure.getMoleculeWithWeakestBondStrength().getId() + ", ");
        }
        System.out.println("\b\b]");

        System.out.println("Synthesizing the serum...");
        for (Bond bond : serum) {

            int from = Integer.parseInt(bond.getFrom().getId().substring(1));
            int to = Integer.parseInt(bond.getTo().getId().substring(1));
            from = Math.min(from, to);
            to = Math.max(Integer.parseInt(bond.getFrom().getId().substring(1)), Integer.parseInt(bond.getTo().getId().substring(1)));
            System.out.printf("Forming a bond between M" + from+" - M"+to+" with strength %.2f\n", bond.getWeight());
            totalBondStrength += bond.getWeight();
        }

        System.out.printf("The total serum bond strength is %.2f\n", totalBondStrength);

    }

}