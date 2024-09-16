import java.io.File;
import java.io.Serializable;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class UrbanInfrastructureDevelopment implements Serializable {
    static final long serialVersionUID = 88L;

    /**
     * Given a list of Project objects, prints the schedule of each of them.
     * Uses getEarliestSchedule() and printSchedule() methods of the current project to print its schedule.
     * @param projectList a list of Project objects
     */
    public void printSchedule(List<Project> projectList) {
        for(Project p : projectList){
            p.printSchedule(p.getEarliestSchedule());
        }
    }

    /**
     * TODO: Parse the input XML file and return a list of Project objects
     *
     * @param filename the input XML file
     * @return a list of Project objects
     */
    
     public List<Project> readXML(String filename) {
        List<Project> projectList = new ArrayList<>();
        try {
            File inputFile = new File(filename);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(inputFile);
            doc.getDocumentElement().normalize();
            projectList = processProjects(doc.getElementsByTagName("Project"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return projectList;
    }
    

    private List<Project> processProjects(NodeList projectNodes) {
        List<Project> projects = new ArrayList<>();
        for (int i = 0; i < projectNodes.getLength(); i++) {
            Element projectElement = (Element) projectNodes.item(i);
            String projectName = projectElement.getElementsByTagName("Name").item(0).getTextContent();
            NodeList taskNodes = projectElement.getElementsByTagName("Task");
            List<Task> tasks = new ArrayList<>();
            for (int j = 0; j < taskNodes.getLength(); j++) {
                Element taskElement = (Element) taskNodes.item(j);
                int taskId = Integer.parseInt(taskElement.getElementsByTagName("TaskID").item(0).getTextContent());
                String description = taskElement.getElementsByTagName("Description").item(0).getTextContent();
                int duration = Integer.parseInt(taskElement.getElementsByTagName("Duration").item(0).getTextContent());
                List<Integer> dependencies = new ArrayList<>();
                NodeList dependencyNodes = taskElement.getElementsByTagName("DependsOnTaskID");
                for (int k = 0; k < dependencyNodes.getLength(); k++) {
                    dependencies.add(Integer.parseInt(dependencyNodes.item(k).getTextContent()));
                }
                tasks.add(new Task(taskId, description, duration, dependencies));
            }
            projects.add(new Project(projectName, tasks));
        }
        return projects;
    }
}
