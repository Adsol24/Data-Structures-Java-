import java.util.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFrame;

public class Graph_dfs {
    private Map<Character, List<Character>> adjacencyList;

    public Graph_dfs(Scanner scanner) {
        adjacencyList = new HashMap<>();
        char currentVertex = 'A';

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split(" ");
            List<Character> neighbors = new ArrayList<>();

            for (String token : tokens) {
                char neighbor = token.charAt(0);
                neighbors.add(neighbor);
            }

            adjacencyList.put(currentVertex, neighbors);
            currentVertex++;
        }
    }

    public void display() {
        System.out.println("Graph:");
        for (char vertex : adjacencyList.keySet()) {
            System.out.print("Vertex " + vertex + " == > ");
            List<Character> neighbors = adjacencyList.get(vertex);
            for (int i = 0; i < neighbors.size(); i++) {
                System.out.print(neighbors.get(i));
                if (i < neighbors.size() - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void dfs(char startVertex) {
        Set<Character> visited = new HashSet<>();
        dfsRecursive(startVertex, visited);
    }

    private void dfsRecursive(char vertex, Set<Character> visited) {
        visited.add(vertex);
        System.out.print(vertex + " ");

        List<Character> neighbors = adjacencyList.get(vertex);
        if (neighbors != null) {
            for (char neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    dfsRecursive(neighbor, visited);
                }
            }
        }
    }

    public static void main(String[] args) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            Scanner scan = null;
            try {
                scan = new Scanner(fileChooser.getSelectedFile());
            } catch (FileNotFoundException e) {
                System.err.println("File not Found - exiting");
                System.exit(1);
            }

            System.out.println("file = " + fileChooser.getSelectedFile().getName());
            System.out.println("scan = " + scan);
            Graph_dfs graph = new Graph_dfs(scan);
            graph.display();

            // Perform DFS from a starting vertex (e.g., 'A')
            char startVertex = 'H';
            System.out.print("DFS starting from vertex " + startVertex + ": ");
            graph.dfs(startVertex);
        }
    }
}
